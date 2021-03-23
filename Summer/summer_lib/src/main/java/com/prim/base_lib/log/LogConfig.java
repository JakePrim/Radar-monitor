package com.prim.base_lib.log;

import com.prim.base_lib.log.formatter.StackTraceFormatter;
import com.prim.base_lib.log.formatter.ThreadFormatter;
import com.prim.base_lib.log.printer.LogPrinter;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志的配置类
 * @time 3/22/21 - 9:05 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public abstract class LogConfig {

    public static int MAX_LEN = 512;
    public static StackTraceFormatter stackTraceFormatter = new StackTraceFormatter();
    public static ThreadFormatter threadFormatter = new ThreadFormatter();

    /**
     * 注入json解析器，有调用者提供
     *
     * @return
     */
    public JsonParse injectJsonParser() {
        return null;
    }

    /**
     * 全局的tag
     *
     * @return
     */
    public String getGlobalTag() {
        return "PLog";
    }

    /**
     * 是否启用日志打印
     *
     * @return 默认启用
     */
    public boolean enable() {
        return true;
    }

    /**
     * 是否包含线程信息
     *
     * @return
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 设置堆栈信息的深度
     *
     * @return
     */
    public int stackTraceDepth() {
        return 5;
    }

    /**
     * 日志打印器
     *
     * @return
     */
    public LogPrinter[] printers() {
        return null;
    }

    /**
     * json序列化接口
     */
    public interface JsonParse {
        String toJson(Object src);
    }
}
