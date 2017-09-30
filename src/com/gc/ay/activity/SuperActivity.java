package com.gc.ay.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public abstract class SuperActivity extends Activity implements Init {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	public void init() {
		initView();

		initListener();

		initData();
	}
}

interface Init {
	public void init();

	public void initView();

	public void initListener();

	public void initData();
}
