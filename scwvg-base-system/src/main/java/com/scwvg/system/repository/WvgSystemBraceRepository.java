package com.scwvg.system.repository;

import com.scwvg.system.entitys.SystemBraceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/23
 * @Explain：
 **/
@Repository
public interface WvgSystemBraceRepository extends JpaRepository<SystemBraceEntity,Long>
{
}
