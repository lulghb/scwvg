package com.scwvg.system.log.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.scwvg.system.log.domain.WvgOperationLog;
import com.scwvg.system.log.repository.LogRepository;
import com.scwvg.system.log.service.LogService;
import com.scwvg.system.utils.RequestHolder;
import com.scwvg.system.utils.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:16
 * @desc: 
**/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class LogServiceImpl implements LogService {
	
	@Autowired
    private LogRepository logRepository;

	@Override
	public void save(ProceedingJoinPoint joinPoint, WvgOperationLog operationLog) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
		
		Object[] argValues = joinPoint.getArgs();
		String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		String params = null;
		// 用户名
        String username = "";
        if(argValues != null){
        	params = "{";
            for (int i = 0; i < argValues.length; i++) {
                params += " " + argNames[i] + ": " + argValues[i];
            }
            params += "}";
        }
        if(!"login".equals(signature.getName())){
            UserDetails userDetails = SecurityContextHolder.getUserDetails();
            if(userDetails != null) {
            	username = userDetails.getUsername();
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(argValues[0]));
                username = jsonObject.get("user").toString();
            }catch (Exception e){
                e.printStackTrace();
                log.error("", e);
            }
        }
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        
        operationLog.setMethodName(methodName);
        operationLog.setUsername(username);
        operationLog.setRequestIp(getRequestIP(request));
        operationLog.setRequestUri(request.getRequestURI());
        operationLog.setRequestParams(params);
        
        logRepository.save(operationLog);
        
	}
	
	public static String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

	@Override
	public Page<WvgOperationLog> queryAll(WvgOperationLog log, Pageable pageable) {
		return logRepository.findAll(new Spec(log), PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
	}
	
	class Spec implements Specification<WvgOperationLog> {

		private static final long serialVersionUID = 1953652405086636039L;
		
		private WvgOperationLog operationLog;

        public Spec(WvgOperationLog operationLog){
            this.operationLog = operationLog;
        }

        @Override
        public Predicate toPredicate(Root<WvgOperationLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();


            if(!ObjectUtils.isEmpty(operationLog.getUsername())){
                list.add(cb.like(root.get("username").as(String.class),"%"+operationLog.getUsername()+"%"));
            }

            if (!ObjectUtils.isEmpty(operationLog.getRequestUri())) {
                list.add(cb.equal(root.get("requestUri").as(String.class), operationLog.getRequestUri()));
            }
            
            if(operationLog.getBeginTime() != null) {
            	list.add(cb.greaterThan(root.get("beginTime").as(Timestamp.class), operationLog.getBeginTime()));
            }
            
            if(operationLog.getEndTime() != null) {
            	list.add(cb.lessThan(root.get("endTime").as(Timestamp.class), operationLog.getEndTime()));
            }
            
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }

	@Override
	public WvgOperationLog get(Long id) {
		return logRepository.findById(id).get();
	}

}
