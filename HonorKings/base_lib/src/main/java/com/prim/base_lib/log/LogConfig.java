package com.prim.base_lib.log;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志的配置类
 * @time 3/22/21 - 9:05 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public abstract class LogConfig {
    /**
     * 全局的tag
     * @return
     */
    public String getGlobalTag(){
        return "PLog";
    }

    /**
     * 是否启用日志打印
     * @return 默认启用
     */
    public boolean enable(){
        return true;
    }
}
