package com.prim.base_lib.log.formatter;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志格式化接口
 * @time 3/22/21 - 9:42 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public interface LogFormatter<T> {
    String format(T data);
}
