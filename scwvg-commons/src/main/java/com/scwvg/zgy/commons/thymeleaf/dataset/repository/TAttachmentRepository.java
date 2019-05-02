package com.scwvg.zgy.commons.thymeleaf.dataset.repository;

import com.scwvg.zgy.commons.thymeleaf.dataset.TAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月25日15点56分
 * @Description: TODO 数据字典仓库类
 * version 0.1
 */
@Repository
public interface TAttachmentRepository extends JpaRepository<TAttachment,Double> {
}
