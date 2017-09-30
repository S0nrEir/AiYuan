package com.gc.ay.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.gc.ay.R;
import com.gc.ay.util.Consts;
import com.gc.ay.view.DialogUtil;
import com.gc.ay.view.GcDialog;
import com.gc.ay.view.GcDialog.GcDialogOnClickListener;

public class CYTXActivity extends SuperActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cytx);
		init();
	}

	@Override
	public void initView() {
		// TODO 初始化View
		demoMethod();
	}

	@Override
	public void initListener() {
		// TODO 初始化事件

	}

	@Override
	public void initData() {
		// TODO 初始化数据

	}

	/**
	 * Demo
	 */
	public void demoMethod() {
		RelativeLayout.LayoutParams _params = new LayoutParams(
				LayoutParams.MATCH_PARENT, Consts.Height / 4);
		Button _btn = new Button(this);
		_btn.setLayoutParams(_params);
		_btn.setOnClickListener(this);
		_btn.setText(R.string.btnMain_CYTX);
		addContentView(_btn, _params);
	}

	private GcDialog demoDialog;

	@Override
	public void onClick(View v) {
		// TODO
		demoDialog = DialogUtil.createConfirmDialog(this, "DemoDialog",
				"确定取消对话框", new GcDialogOnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 确定
						Toast.makeText(getApplicationContext(), "点击了确定",
								Toast.LENGTH_LONG).show();
					}
				});
		demoDialog.show();
	}

}
