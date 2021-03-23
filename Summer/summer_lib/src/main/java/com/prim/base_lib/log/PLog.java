package com.prim.base_lib.log;

import android.util.Log;

import com.prim.base_lib.log.printer.LogPrinter;
import com.prim.base_lib.log.utils.StackTraceUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 打印堆栈信息 模拟控制台
 * @time 3/22/21 - 8:58 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class PLog {
    private static final String P_LOG_PACkAGE;

    static {
        String className = PLog.class.getName();
        P_LOG_PACkAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    public static void v(Object... contents) {
        log(LogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(LogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(LogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(LogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(LogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(LogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(LogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(LogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(LogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(LogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(LogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(LogType.A, tag, contents);
    }

    public static void log(@LogType.TYPE int type, Object... contents) {
        log(type, LogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@LogType.TYPE int type, String tag, Object... contents) {
        log(LogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(LogConfig config, @LogType.TYPE int type, String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (config.includeThread()) {
            //线程信息
            String threadInfo = LogConfig.threadFormatter.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            //堆栈信息
            String stackTrace = LogConfig.stackTraceFormatter.
                    format(StackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(),
                            P_LOG_PACkAGE, config.stackTraceDepth()));
            sb.append(stackTrace).append("\n");
        }
        String body = parseBody(contents, config);
        sb.append(body);
        //获取打印器
        List<LogPrinter> printers = config.printers() != null ?
                Arrays.asList(config.printers()) :
                LogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        //使用打印器打印日志
        for (LogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }
    }

    /**
     * 解析日志内容
     *
     * @param contents
     * @return
     */
    private static String parseBody(Object[] contents, LogConfig config) {
        if (config.injectJsonParser() != null) {
            //当json解析器不等于null的情况下 调用toJson方法 解析为json字符串
            return config.injectJsonParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object content : contents) {
            sb.append(content.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);//删除最后一个分号
        }
        return sb.toString();
    }
}
