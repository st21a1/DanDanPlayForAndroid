package com.xyoye.dandanplay.utils.net;

import android.app.ProgressDialog;

import com.google.gson.JsonSyntaxException;
import com.xyoye.core.rx.Lifeful;
import com.xyoye.core.utils.TLog;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yzd on 2017/5/15 0015.
 */

public abstract class CommJsonObserver<T extends CommJsonEntity> implements Observer<T> {

    private Lifeful lifeful;
    private ProgressDialog progressDialog;
    private Disposable mDisposable;

    public CommJsonObserver() {
        lifeful = null;
    }

    public CommJsonObserver(Lifeful lifeful) {
        this.lifeful = lifeful;
    }

    public CommJsonObserver(Lifeful lifeful, ProgressDialog progressDialog) {
        this.lifeful = lifeful;
        this.progressDialog = progressDialog;
        progressDialog.setOnCancelListener(dialog -> mDisposable.dispose());
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        TLog.i("onSubscribe");
        if (lifeful != null && !lifeful.isAlive()) {
            d.dispose();
        }
    }

    @Override
    public void onNext(T value) {
        TLog.i("onNext");
        if (lifeful == null || lifeful.isAlive()) {
            if (value.isSuccess()) {
                onSuccess(value);
            } else {
                onError(value.getErrorCode(), value.getErrorMessage());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (lifeful == null || lifeful.isAlive()) {
            onError(-1, getErrorMessage(e));
        }
    }

    @Override
    public void onComplete() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private String getErrorMessage(Throwable e) {
        TLog.e(e.toString());
        if (e instanceof JsonSyntaxException) {
            TLog.i("error", e.toString());
            return "数据异常";
        } else if (e instanceof UnknownHostException) {
            TLog.i("error", e.toString());
            return "网络连接中断";
        } else if (e instanceof SocketTimeoutException) {
            return "服务器繁忙";
        }
        return "服务器繁忙";
    }

    public abstract void onSuccess(T t);

    public abstract void onError(int errorCode, String message);
}
