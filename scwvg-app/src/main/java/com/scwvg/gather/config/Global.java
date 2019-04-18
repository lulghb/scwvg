package com.scwvg.gather.config;

import java.util.concurrent.BlockingQueue;

import com.scwvg.gather.util.ESClientUtil;

public class Global {

    /**
     * 配置
     */
    public static Context context = null;

    /**
     * 是否退出
     */
    public static boolean isExit = false;

    /**
     * Es客户端连接
     */
    public static ESClientUtil esClientUtil = null;

    /**
     * 队列
     */
    public static BlockingQueue<String> queue = null;

}
