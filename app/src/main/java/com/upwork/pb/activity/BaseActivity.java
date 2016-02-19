package com.upwork.pb.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.upwork.pb.model.User;

abstract class BaseActivity extends AppCompatActivity {
    @Nullable
    protected User getUser() {
        return null;
    }

    protected void setUser(@Nullable User user) {

    }
}
