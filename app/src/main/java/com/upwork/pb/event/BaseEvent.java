package com.upwork.pb.event;

import android.support.annotation.Nullable;

public class BaseEvent {

    private Throwable mError;

    protected BaseEvent() {
    }

    public BaseEvent(Throwable error) {
        mError = error;
    }

    public Throwable getError() {
        return mError;
    }

    @Nullable
    public CharSequence getErrorMessage() {
        return null == mError ? null : mError.getMessage();
    }

    public boolean hasError() {
        return null != mError;
    }
}
