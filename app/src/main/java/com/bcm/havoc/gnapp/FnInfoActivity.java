package com.bcm.havoc.gnapp;

import android.os.Bundle;

import com.bcm.havoc.gnapp.Base.ui.BaseActivity;

public class FnInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fn_info);

//    initDb();
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        setTitle("蜂农信息");
        setBackBtn();


    }

}
