package com.upwork.pb.activity;

import android.content.Intent;
import android.os.Bundle;

import com.upwork.pb.R;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == getUser()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        setTitle(R.string.ac_pr_title);
    }
}
