package com.gc.ay.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.ay.R;
import com.gc.ay.ble.MEDX_JBQ_FA101;
import com.gc.ay.util.Consts;
import com.gc.ay.util.SpeakUtil;

public class JSJHActivity extends Activity {
	MEDX_JBQ_FA101 medx;
	private TextView txt;
	public static final String TAG="主线程回调test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jsjh);
		txt=(TextView)findViewById(R.id.tvJSJH_count);
	}

	private Handler mTestHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.obj!=null)
			txt.setText(msg.obj.toString());
		}
	};
	private boolean state = false;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnJSJH_start:
			initMedx();
			break;
		default:
			break;
		}
	}

	private void initMedx() {
		if (medx == null) {
			medx = new MEDX_JBQ_FA101(this) {

				@Override
				public void onStart() {
					// TODO
				}

				@Override
				public void onSearching(BluetoothDevice pDevice) {
					// TODO 搜索中
					Toast.makeText(context, "找到蓝牙设备", Toast.LENGTH_LONG).show();
					Log.i(TAG, "BLE2Ctrl.onSearching()...");
					if (pDevice.getName().equals(Consts.MEDXING_VSS)) {
						state = true;
						medx.connectDevice(pDevice.getAddress());
						stopSearch();
					}
				}

				@Override
				public void onSearchEnd() {
					// TODO 搜索结束
					if (!state) {
						mTestHandler
								.sendEmptyMessage(MEDX_JBQ_FA101.TEST_NOT_FIND);
					}
					Toast.makeText(context, "没有找到任何蓝牙设备", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onResult(String pResult) {
					// TODO 测量结果
					mTestHandler.sendMessage(mTestHandler.obtainMessage(
							MEDX_JBQ_FA101.TEST_END, pResult));
				}
				
				@Override
				public void onDisconnected() {
					// TODO
					mTestHandler.sendMessage(mTestHandler
							.obtainMessage(MEDX_JBQ_FA101.TEST_DISCON));
				}

				@Override
				public void onConnected(int pResult) {
					// TODO
					if (pResult == MEDX_JBQ_FA101.TEST_READY) {
						mTestHandler.sendEmptyMessage(MEDX_JBQ_FA101.TEST_READY);
						medx.startTest();
					} else {
						mTestHandler.sendEmptyMessage(MEDX_JBQ_FA101.TEST_UNCON);
					}
				}
			};
			medx.search();
			Log.i(TAG, "medx.search");
			SpeakUtil.speak(getApplicationContext(), "搜索中,请打开计步器...");
		}
	}
}