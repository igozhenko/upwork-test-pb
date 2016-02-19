package com.upwork.pb.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.upwork.pb.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ac_lg_title);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
