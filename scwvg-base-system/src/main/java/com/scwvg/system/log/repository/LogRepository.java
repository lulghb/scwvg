package com.scwvg.system.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.scwvg.system.log.domain.WvgOperationLog;
/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/20 00:06
 * @desc: 
**/
@Repository
public interface LogRepository extends JpaRepository<WvgOperationLog, Long>, JpaSpecificationExecutor<WvgOperationLog> {

}
