package com.xwh.frame.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xwh.frame.R;
import com.xwh.frame.base.BaseActivity;
import com.xwh.frame.mvp.presenter.SplashPresenter;
import com.xwh.frame.mvp.view.ISplashView;

public class SplashActivity extends BaseActivity<ISplashView,SplashPresenter> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initWindow();
        super.onCreate(savedInstanceState);
    }

    private void initWindow() {
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏显示
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
    }

    @Override
    protected SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected int initLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.checkApk();
    }
}
