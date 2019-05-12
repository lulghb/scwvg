package com.scwvg.sys.content.service;

import com.scwvg.sys.content.entitys.TAttachment;
import com.scwvg.sys.content.mapper.TAttachmentMapper;
import com.scwvg.sys.content.repository.TAttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @project: baigle-util
 * @author: chen.baihoo
 * @date: 2019/1/20 18:25
 * @Description: 附件接口实现服务类
 * version 0.1
 */
@Service("tAttachmentService")
public class TAttachmentService {

    @Resource
    private TAttachmentMapper tAttachmentMapper;
    @Resource
    private TAttachmentRepository tAttachmentRepository;

    /**
     * @Description: 新增单个附件信息
     * @Param: [TAttachment attachment]
     * @return: void
     * @Author: chen.baihoo
     * @Date: 2019/1/20
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(TAttachment attachment) {
        tAttachmentMapper.insert(attachment);
    }

    /**
     * @Description: 新增多个附件信息
     * @Param: [TAttachment attachment]
     * @return: void
     * @Author: chen.baihoo
     * @Date: 2019/1/20
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(TAttachment[] attachments) {
        Arrays.stream(attachments).forEach(e->{
            tAttachmentMapper.insert(e);
        });
    }

    /**
     * @Description: 在指定范围查询附件信息
     * @Param: String[]
     * @return: List<TAttachment>
     * @Author: chen.baihoo
     * @Date: 2019/1/20
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<TAttachment> findByFileIdsIn(String[] fileIds) {
        return tAttachmentMapper.findByFileIdsIn(fileIds);
    }
}
