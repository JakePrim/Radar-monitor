package com.prim.base_lib.log;

import androidx.annotation.NonNull;

import com.prim.base_lib.log.printer.LogPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private List<LogPrinter> printerList = new ArrayList<>();

    private LogManager(LogConfig config, LogPrinter[] printers) {
        this.config = config;
        this.printerList.addAll(Arrays.asList(printers));
    }

    public static LogManager getInstance() {
        return instance;
    }

    /**
     * 初始化
     *
     * @param config
     */
    public static void init(@NonNull LogConfig config, LogPrinter... printers) {
        instance = new LogManager(config, printers);
    }

    /**
     * 获取日志配置
     *
     * @return
     */
    public LogConfig getConfig() {
        return config;
    }

    public List<LogPrinter> getPrinters() {
        return printerList;
    }

    public void addPrinter(LogPrinter printer) {
        printerList.add(printer);
    }

    public void removePrinter(LogPrinter printer) {
        if (printerList != null) {
            printerList.remove(printer);
        }
    }
}
