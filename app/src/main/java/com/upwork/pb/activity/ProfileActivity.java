package com.upwork.pb.activity;

import android.content.Intent;
import android.os.Bundle;

import com.upwork.pb.R;
import com.upwork.pb.app.App;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == App.getDataManager().getUser()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        setTitle(R.string.ac_pr_title);
    }
}
