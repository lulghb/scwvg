package com.scwvg.zgy.commons.thymeleaf.dataset.mapper;


import com.scwvg.zgy.commons.thymeleaf.dataset.TAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @project: baigle-util
 * @author: chen.baihoo
 * @date: 2019/1/20 18:23
 * @Description: 附件接口服务类
 * version 0.1
 */
@Mapper
public interface TAttachmentMapper {
	/**
	 * 新增单个附件信息
	 * @param attachment
	 */
	public void insert(@Param("ta")TAttachment attachment);

	/**
	 * 在指定范围查询附件信息
	 * @param fileIds
	 * @return
	 */
	public List<TAttachment> findByFileIdsIn(@Param("fileIds") String[] fileIds);
}
