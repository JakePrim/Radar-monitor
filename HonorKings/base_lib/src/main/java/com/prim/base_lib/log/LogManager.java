package com.prim.base_lib.log;

import android.support.annotation.NonNull;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志管理类
 * @time 3/22/21 - 9:06 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class LogManager {
    private LogConfig config;
    private static LogManager instance;

    private LogManager(LogConfig config) {
        this.config = config;
    }

    public static LogManager getInstance() {
        return instance;
    }

    /**
     * 初始化
     *
     * @param config
     */
    public static void init(@NonNull LogConfig config) {
        instance = new LogManager(config);
    }

    /**
     * 获取日志配置
     * @return
     */
    public LogConfig getConfig() {
        return config;
    }
}
