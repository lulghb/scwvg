package com.scwvg.zgy.commons.thymeleaf.dataset;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description: 数据字典类
 * @author: chen.baihoo
 * @date: 2019/1/19 17:27
 * version 0.1
 */
@Data
@Entity(name = "t_dictionary")
public class TDictionary implements Serializable {

    //编号
    @Id
    @Column(name="id")
    private  Long id;

    //数据字典类型代码
    @Column(name="dict_type_code")
    private  String dictTypeCode;

    //数据字段类型名称
    @Column(name="dict_type_name")
    private  String dictTypeName;

    //数据字典类型项代码
    @Column(name="dict_id")
    private  String dictId;

    //数据字典类型项名称
    @Column(name="dict_name")
    private  String dictName;

    //数据字典父类型代码
    @Column(name="pdict_type_code")
    private  String pdictTypeCode;

    //数据字典父类型名称
    @Column(name="pdict_type_name")
    private  String pdictTypeName;

    //数据字典父类型项代码
    @Column(name="pdict_id")
    private  String pdictId;

    //数据字典父类型项名称
    @Column(name="pdict_name")
    private  String pdictName;

    //排序
    @Column(name="sort")
    private  long sort;

    //是否启用
    @Column(name="ENABLE")
    private  String enable = "y";

    //是否删除
    @Column(name="delete_flag")
    private  int deleteFlag = 0;

}
