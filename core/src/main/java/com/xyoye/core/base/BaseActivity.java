package com.xyoye.core.base;

import android.app.Dialog;
import android.support.v4.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.xyoye.core.R;
import com.xyoye.core.interf.presenter.BasePresenter;
import com.xyoye.core.weight.BaseLoadingDialog;

/**
 * Created by yzd on 2017/2/5 0005.
 */

public abstract class BaseActivity<T extends BasePresenter> extends BaseMvpActivity<T> {

    protected Dialog dialog;

    @Override
    protected int getToolbarColor() {
        return ContextCompat.getColor(this, R.color.theme_color);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getToolbarColor(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog = null;
    }

    @Override
    public void showLoadingDialog() {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new BaseLoadingDialog(this);
            dialog.show();
        }
    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


}
