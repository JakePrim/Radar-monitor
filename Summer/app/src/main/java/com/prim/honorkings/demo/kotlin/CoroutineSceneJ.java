package com.prim.honorkings.demo.kotlin;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;

/**
 * @author sufulu
 * @version 1.0.0
 * @desc 模拟协程的实现
 * @time 5/5/21 - 9:15 PM
 * @contact sufululove@gmail.com
 * @name Summer
 */
public class CoroutineSceneJ {
    private static final String TAG = "CoroutineSceneJ";

    public static final Object request2(Continuation preCallback) {
        ContinuationImpl request2Callback;
        if (!(preCallback instanceof ContinuationImpl) ||
                ((((ContinuationImpl) preCallback).label & Integer.MIN_VALUE) == 0)) {
            request2Callback = new ContinuationImpl(preCallback) {

                @Override
                Object invokeSuspend(Object resumeResult) {
                    this.result = resumeResult;
                    this.label |= Integer.MIN_VALUE;
                    return request2(this);
                }
            };
        } else {
            request2Callback = (ContinuationImpl) preCallback;
        }

        switch (request2Callback.label) {
            case 0: {
                Object delay = DelayKt.delay(2000, request2Callback);
                if (delay == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
                }
            }
        }

        Log.e(TAG, "request2: compiled");

        return "result from request2";
    }

    public static final Object request1(Continuation preCallback) {
        ContinuationImpl request1Callback;
        if (!(preCallback instanceof ContinuationImpl) ||
                ((((ContinuationImpl) preCallback).label & Integer.MIN_VALUE) == 0)) {
            request1Callback = new ContinuationImpl(preCallback) {

                @Override
                Object invokeSuspend(Object resumeResult) {
                    this.result = resumeResult;
                    this.label |= Integer.MIN_VALUE;
                    return request1(this);
                }
            };
        } else {
            request1Callback = (ContinuationImpl) preCallback;
        }

        switch (request1Callback.label) {
            case 0: {
//                Object delay = DelayKt.delay(2000, request1Callback);
                Object request2 = request2(request1Callback);
                if (request2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
                }
            }
        }

        Log.e(TAG, "request2: compiled");

        return "result from request2";
    }

    static abstract class ContinuationImpl<T> implements Continuation<T> {
        private Continuation preCallback;

        int label;
        Object result;

        public ContinuationImpl(Continuation preCallback) {
            this.preCallback = preCallback;
        }

        @NotNull
        @Override
        public CoroutineContext getContext() {
            return preCallback.getContext();
        }

        //恢复
        @Override
        public void resumeWith(@NotNull Object resumeResult) {
            Object suspend = invokeSuspend(resumeResult);
            //表示又被挂起了
            if (suspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return;
            }
            preCallback.resumeWith(resumeResult);
        }

        abstract Object invokeSuspend(Object resumeResult);
    }
}
