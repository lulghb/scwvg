package com.scwvg.system.log.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

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

import com.scwvg.system.log.domain.WvgLoginLog;
import com.scwvg.system.log.repository.LoginLogRepository;
import com.scwvg.system.log.service.LoginLogService;
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
public class LoginLogServiceImpl implements LoginLogService {
	
	@Autowired
    private LoginLogRepository loginLogRepository;

	@Override
	public void save(WvgLoginLog loginLog) {
        
		UserDetails userDetails = SecurityContextHolder.getUserDetails();
        if(userDetails != null) {
        	HttpServletRequest request = RequestHolder.getHttpServletRequest();
        	loginLog.setUsername(userDetails.getUsername());
            loginLog.setLoginIp(getRequestIP(request));
            loginLog.setLoginSession(request.getRequestedSessionId());
            
            loginLogRepository.save(loginLog);
        } else {
        	log.info("用户未登录");
        }
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
	public Page<WvgLoginLog> queryAll(WvgLoginLog loginLog, Pageable pageable) {
		return loginLogRepository.findAll(new Spec(loginLog), PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
	}
	
	class Spec implements Specification<WvgLoginLog> {

		private static final long serialVersionUID = 1953652405086636039L;
		
		private WvgLoginLog loginLog;

        public Spec(WvgLoginLog loginLog){
            this.loginLog = loginLog;
        }

        @Override
        public Predicate toPredicate(Root<WvgLoginLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();


            if(!ObjectUtils.isEmpty(loginLog.getUsername())){
                list.add(cb.like(root.get("username").as(String.class),"%"+loginLog.getUsername()+"%"));
            }
            
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }

	@Override
	public WvgLoginLog get(Long id) {
		return loginLogRepository.findById(id).get();
	}

}
