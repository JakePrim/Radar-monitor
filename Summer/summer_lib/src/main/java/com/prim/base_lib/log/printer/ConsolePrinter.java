package com.prim.base_lib.log.printer;

import androidx.annotation.NonNull;
import android.util.Log;

import com.prim.base_lib.log.LogConfig;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 3/22/21 - 10:02 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class ConsolePrinter implements LogPrinter {
    @Override
    public void print(@NonNull LogConfig config, int level, String tag, @NonNull String printString) {
        int len = printString.length();
        int count = len / LogConfig.MAX_LEN;
        if (count > 0) {
            int index = 0;
            for (int i = 0; i < count; i++) {
                Log.println(level, tag, printString.substring(index, index + LogConfig.MAX_LEN));
                index += LogConfig.MAX_LEN;
            }
            //行数不能够整除 打印剩余的部分
            if (index != len) {
                Log.println(level, tag, printString.substring(index, len));
            }
        } else {
            //不足一行的时候都去打印
            Log.println(level, tag, printString);
        }
    }
}
