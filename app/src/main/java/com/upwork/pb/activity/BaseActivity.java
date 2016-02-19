package com.upwork.pb.activity;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.upwork.pb.R;
import com.upwork.pb.dialog.ProgressDialogFragment;

abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG_DIALOG_PROGRESS = "tagDialgoProgress";

    public void dismissProgress() {
        ProgressDialogFragment fragment = (ProgressDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DIALOG_PROGRESS);
        if (null == fragment) return;
        fragment.dismissAllowingStateLoss();
    }

    public void showError(CharSequence message, DialogInterface.OnDismissListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dg_error_title)
                .setMessage(message)
                .setPositiveButton(R.string.dg_error_button_positive, null)
                .setOnDismissListener(listener)
                .setCancelable(false)
                .show();

    }

    public void showError(Throwable error) {
        showError(error.getMessage(), null);
    }

    public void showProgress(@Nullable CharSequence message) {
        if (null == message) message = getText(R.string.dg_please_wait_title);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_DIALOG_PROGRESS);
        if (null != fragment && fragment.isVisible()) return;
        ProgressDialogFragment dialogFragment = ProgressDialogFragment.newInstance(message.toString());
        dialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_PROGRESS);
    }

}
