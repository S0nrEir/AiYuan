package com.gc.ay.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

import com.gc.ay.R;
import com.gc.ay.bean.Block;
import com.gc.ay.util.Consts;
import com.gc.ay.view.BlockView;

public class MainActivity extends SuperActivity {
	private BlockView block;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initPIX();
		init();
	}

	/**
	 * 计算屏幕相关
	 */
	private void initPIX() {
		DisplayMetrics _metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(_metric);
		Consts.Width = _metric.widthPixels; // 屏幕宽度（像素）
		Consts.Height = _metric.heightPixels; // 屏幕高度（像素）
		Rect _frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(_frame);
		Consts.Height -= _frame.top;// 真实高度
		Consts.BLOCK_HEIGHT = Consts.Height / 4 - 5 * Consts.BLOCK_SPACE;// 磁贴高度
	}

	private void initBlock() {
		List<Block> _list = new ArrayList<Block>();
		_list.add(new Block(0, "吃药提醒", R.drawable.ic_launcher,
				R.color.dark_blue));
		_list.add(new Block(1, "跌倒报警", R.drawable.ic_launcher,
				R.color.dark_green));
		_list.add(new Block(2, "WebAppDemo", R.drawable.ic_launcher,
				R.color.orange));
		 _list.add(new Block(3, "A", R.drawable.ic_launcher,
		 R.color.dark_blue));
		 _list.add(new Block(4, "B", R.drawable.ic_launcher, R.color.blue));
		 _list.add(new Block(5, "C", R.drawable.ic_launcher,
		 R.color.dark_green));
		 _list.add(new Block(6, "D", R.drawable.ic_launcher, R.color.red));
		 _list.add(new Block(7, "E", R.drawable.ic_launcher, R.color.green));
		 _list.add(new Block(8, "F", R.drawable.ic_launcher, R.color.orange));
		 _list.add(new Block(9, "G", R.drawable.ic_launcher,
		 R.color.micro_green));
		 _list.add(new Block(10, "H", R.drawable.ic_launcher, R.color.blue));
		block.init(_list);
		block.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case 0: {// TODO 吃药提醒
					startActivity(new Intent(MainActivity.this,
							CYTXActivity.class));
				}
					break;
				case 1: {// TODO 跌倒报警
					startActivity(new Intent(MainActivity.this,
							DDBJActivity.class));
				}
					break;
				case 2: {// TODO WebDemo
					startActivity(new Intent(MainActivity.this,
							JSJHActivity.class));
				}
					break;
				default: {
				}
					break;
				}
			}
		});
	}

	@Override
	public void initView() {
		// TODO 初始化View
		block = (BlockView) findViewById(R.id.blMain);
		initBlock();
	}

	@Override
	public void initListener() {
		// TODO 初始化事件

	}

	@Override
	public void initData() {
		// TODO 初始化数据

	}

}
