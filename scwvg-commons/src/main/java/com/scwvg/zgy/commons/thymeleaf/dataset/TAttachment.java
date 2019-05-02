package com.scwvg.zgy.commons.thymeleaf.dataset;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
/**
 * @project: baigle-util
 * @author: chen.baihoo
 * @date: 2019/1/20 18:20
 * @Description: 附件类
 * version 0.1
 */
@Data
@Entity(name="t_attachment")
public class TAttachment implements Serializable {

    //文件编号
    @Id
    @Column(name="file_id")
    private  Long fileId;

    //显示名称
    @Column(name="file_name")
    private  String fileName;

    //大小
    @Column(name="file_size")
    private  String fileSize;

    //真实名称
    @Column(name="file_reale_name")
    private  String fileRealeName;

    //文件路径
    @Column(name="file_path")
    private  String filePath;

    //后缀
    @Column(name="suffix")
    private  String suffix;

    //上传者
    @Column(name="create_user")
    private  String createUser;

    //修改者
    @Column(name="update_user")
    private  String updateUser;

    //上传时间
    @Column(name="create_date")
    private  Date createDate;

    //修改时间
    @Column(name="update_date")
    private  Date updateDate;

}