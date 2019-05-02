package com.scwvg.zgy.system.vo;

import com.scwvg.zgy.system.enums.LayuiCodeEnum;

import java.util.HashMap;

/**
 *
 * @param <K>
 * @param <T>
 */
public class Layui<K , T> extends HashMap<K , T> {


    public static Layui<String , Object> status(int code){

        Layui<String , Object> layui = new Layui<>();
        layui.put("code" , code);
        return  layui;
    }
}
