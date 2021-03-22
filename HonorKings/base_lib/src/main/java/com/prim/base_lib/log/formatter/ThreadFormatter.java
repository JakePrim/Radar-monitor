package com.prim.base_lib.log.formatter;

import android.util.Log;

/**
 * @author prim
 * @version 1.0.0
 * @desc 线程格式化器
 * @time 3/22/21 - 9:43 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class ThreadFormatter implements LogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "Thread:" + data.getName();
    }
}
