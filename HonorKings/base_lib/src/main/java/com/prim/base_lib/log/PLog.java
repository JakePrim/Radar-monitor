package com.prim.base_lib.log;

import android.util.Log;

/**
 * @author prim
 * @version 1.0.0
 * @desc 打印堆栈信息 模拟控制台
 * @time 3/22/21 - 8:58 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class PLog {
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

    private static void log(LogConfig config, @LogType.TYPE int type, String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String body = parseBody(contents);
        sb.append(body);
        Log.println(type, tag, body);
    }

    /**
     * 解析日志内容
     *
     * @param contents
     * @return
     */
    private static String parseBody(Object[] contents) {
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
