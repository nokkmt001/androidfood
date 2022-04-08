package com.example.fooddrink.ui.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewbinding.ViewBinding;

import com.example.fooddrink.R;

import java.util.Objects;

public abstract class BaseTestActivity<T extends ViewBinding> extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_MULTIPLE_PERMISSIONS = 100;
    String[] permissionsMain = {};
    protected T binding;
    Dialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideKeyboard();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        binding = getViewBinding();
        setContentView(binding.getRoot());
        initView();
        initData();
    }

    public abstract T getViewBinding();

    protected void initView() {}

    protected void initData() {}

    public <N extends View> N bind(int id) {
        return findViewById(id);
    }

    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

    protected void alertDialog(String title, String message, String btnPos, String btnNeutral, DialogInterface.OnClickListener ocListener) {
        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setTitle(title);
        db.setMessage(message);
        db.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        if (btnPos != null) db.setPositiveButton(btnPos, ocListener);
        if (btnNeutral != null) db.setNeutralButton(btnNeutral, ocListener);
        db.show();
    }

    protected void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }

    public void checkSelfPermission(String[] permissionsRequired) {
        permissionsMain = permissionsRequired;
        for (String tt : permissionsRequired) {
            if (ActivityCompat.checkSelfPermission(this, tt) != PackageManager.PERMISSION_GRANTED) {
                {
                    ActivityCompat.requestPermissions(this, permissionsRequired, REQUEST_MULTIPLE_PERMISSIONS);
                }
            }
        }
    }

    public void showProgressDialog(boolean isShow) {
        if (isShow) {
            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(R.color.colorTransparent);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.dialog_progressbar_waiting);
            progressDialog.show();
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    protected void showMessage(String error) {
        error = error.isEmpty() ? getString(R.string.error_msg_unknown) : error;
        if (!NetworkUtils.isNetworkConnected(this)) {
            error = getString(R.string.error_msg_no_internet);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_error_msg))
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void showInfo(String error) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_info_msg))
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void showNoResult() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin")
                .setMessage(getResources().getString(R.string.noresult_msg))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onResult(requestCode, resultCode, data);
    }

    protected void onResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_MULTIPLE_PERMISSIONS) {
            //check all permission
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }
            // if 1 permissions not grant break android show error

            for (String ii : permissionsMain) {
                if (!allgranted && ActivityCompat.shouldShowRequestPermissionRationale(this, ii)) {
                    showMessagePermissions();
                }
            }
        }
    }

    private void showMessagePermissions() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_title)
                .setMessage(R.string.permission_detail)
                .setPositiveButton(R.string.action_permission, (dialog, which) -> {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(BaseTestActivity.this, permissionsMain, REQUEST_MULTIPLE_PERMISSIONS);
                })
                .setNegativeButton(R.string.action_cancel, (dialog, which) -> {
                    dialog.cancel();
                    finish();
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

