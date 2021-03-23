package com.prim.base_lib.log;

import androidx.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 3/22/21 - 9:00 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class LogType {

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {

    }

    public final static int V = Log.VERBOSE;
    public final static int D = Log.DEBUG;
    public final static int I = Log.INFO;
    public final static int W = Log.WARN;
    public final static int E = Log.ERROR;
    public final static int A = Log.ASSERT;
}
