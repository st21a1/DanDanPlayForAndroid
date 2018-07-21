package com.xyoye.dandanplay.ui.openMod;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.xyoye.core.base.BaseActivity;
import com.xyoye.core.base.IBaseAppCompatActivity;
import com.xyoye.core.rx.LifefulRunnable;
import com.xyoye.dandanplay.R;
import com.xyoye.dandanplay.mvp.impl.OpenPresenterImpl;
import com.xyoye.dandanplay.mvp.presenter.OpenPresenter;
import com.xyoye.dandanplay.mvp.view.OpenView;
import com.xyoye.dandanplay.ui.mainMod.MainActivity;

import java.io.InputStream;

import butterknife.BindView;

/**
 * Created by YE on 2018/7/15.
 */


public class OpenActivity extends BaseActivity<OpenPresenter> implements OpenView {
    @BindView(R.id.image_iv)
    ImageView imageView;

    @Override
    protected void process(Bundle savedInstanceState) {
        super.process(savedInstanceState);

        Handler handler = getHandler();
        handler.postDelayed(new LifefulRunnable(() -> {
            launchActivity(MainActivity.class);
            this.finish();
        }, this), 3000);
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_open;
    }

    @Override
    public void initView() {
        setBgRes();
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onDestroy() {
        imageView.setImageBitmap(null);
        System.gc();
        super.onDestroy();
    }

    @NonNull
    @Override
    protected OpenPresenter initPresenter() {
        return new OpenPresenterImpl(this, this);
    }

    private void setBgRes() {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = getResources().openRawResource(R.raw.launch);
        Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
        imageView.setImageBitmap(bm);
    }
}