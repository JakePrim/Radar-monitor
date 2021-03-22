package com.prim.base_lib.log.printer;

import android.support.annotation.NonNull;

import com.prim.base_lib.log.LogConfig;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志打印接口
 * @time 3/22/21 - 9:40 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public interface LogPrinter {
    void print(@NonNull LogConfig config, int level, String tag, @NonNull String printString);
}
