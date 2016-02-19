package com.upwork.pb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.upwork.pb.R;
import com.upwork.pb.app.App;
import com.upwork.pb.event.BaseEvent;
import com.upwork.pb.model.User;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

public class LoginActivity extends BaseActivity {
    @NotEmpty(messageId = R.string.er_et_required, order = 1)
    @Bind(R.id.ac_lg_login)
    EditText mLoginView;
    @NotEmpty(messageId = R.string.er_et_required, order = 2)
    @Bind(R.id.ac_lg_password)
    EditText mPasswordView;

    @OnClick(R.id.ac_lg_button_login)
    void onClickButtonLogin() {
        if (!FormValidator.validate(this, new SimpleErrorPopupCallback(this, true))) return;
        showProgress(getText(R.string.ac_lg_dialog_singing_in_title));
        new GitGubLoginTask(mLoginView.getText().toString(), mPasswordView.getText().toString()).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ac_lg_title);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(LoginResultEvent event) {
        try {
            if (event.hasError()) {
                showError(event.getError());
                return;
            }
            App.getDataManager().setUser(event.User);
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        } finally {
            dismissProgress();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private static class GitGubLoginTask extends Thread {
        private final String mLogin;
        private final String mPassword;

        private GitGubLoginTask(String login, String password) {
            mLogin = login;
            mPassword = password;
        }

        @Override
        public void run() {
            try {
                GitHub gitHub = GitHub.connectUsingPassword(mLogin, mPassword);
                if (!gitHub.isCredentialValid()) {
                    throw new Exception(App.getString_(R.string.ac_lg_error_wrong_credentials));
                }
                GHMyself myself = gitHub.getMyself();
                User user = new User();
                user.setLogin(myself.getLogin());
                user.setName(myself.getName());
                user.setAvatarUrl(myself.getAvatarUrl());
                EventBus.getDefault().post(new LoginResultEvent(user));
            } catch (Exception e) {
                EventBus.getDefault().post(new LoginResultEvent(e));
            }
        }
    }

    private static class LoginResultEvent extends BaseEvent {
        public final User User;

        private LoginResultEvent(User user) {
            User = user;
        }

        public LoginResultEvent(Throwable error) {
            super(error);
            User = null;
        }
    }
}
