package com.prim.base_lib.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志的打印信息bean
 * @time 3/22/21 - 11:00 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class LogMo {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
    public long timeMillis;
    public int level;
    public String tag;
    public String log;

    public LogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    /**
     * 拼接好的日志信息
     *
     * @return
     */
    public String flattenedLog() {
        return getFlattened() + "\n" + log;
    }

    //字段拼接
    public String getFlattened() {
        return format(timeMillis) + '|' + level + '|' + tag + '|';
    }

    /**
     * 格式化时间戳
     *
     * @param timeMillis
     * @return
     */
    public String format(long timeMillis) {
        return sdf.format(timeMillis);
    }
}
