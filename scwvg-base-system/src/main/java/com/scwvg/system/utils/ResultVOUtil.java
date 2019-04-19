package com.scwvg.system.utils;

import com.scwvg.system.vo.ResultVO;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 11:03
 * @Description: TODO 通用方法集成
 * version 0.1
 */
public class ResultVOUtil {
    /**
     * 含有参数成功传值
     * @param object
     * @return
     */
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 不含有参数，成功
     * @return
     */
    public static ResultVO success(){
        return success(null);
    }
    public static ResultVO error(Integer code ,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
