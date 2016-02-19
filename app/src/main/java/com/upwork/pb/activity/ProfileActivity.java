package com.upwork.pb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.upwork.pb.R;
import com.upwork.pb.app.App;
import com.upwork.pb.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {
    @Bind(R.id.ac_pr_avatar)
    ImageView mAvatarView;
    @Bind(R.id.ac_pr_login)
    TextView mLoginView;
    @Bind(R.id.ac_pr_name)
    TextView mNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = App.getDataManager().getUser();
        if (null == user) {
            startLoginActivity();
            return;
        }
        setTitle(R.string.ac_pr_title);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        mNameView.setText(user.getName());
        mLoginView.setText(user.getLogin());
        Glide.with(this)
                .load(user.getAvatarUrl())
                .centerCrop()
                .placeholder(R.drawable.ac_pr_avatar_placeholder)
                .crossFade()
                .into(mAvatarView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ac_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ac_pr_log_out:
                App.getDataManager().setUser(null);
                startLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
