package com.gc.ay.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import com.gc.ay.activity.JSJHActivity;
import com.gc.ay.util.GcThread;
import com.gc.ay.util.GcThread.GcRunnable;

/**
 * <br/>
 * <b>类名：</b>BLE2Ctrl<br/>
 * <p>
 * <b>用途：</b>BT2.0操作类<br/>
 * <p>
 * <b>作者：</b>Leong<br/>
 * <p>
 */
public abstract class BLE2Ctrl {
	public Context context;
	public BLE2 ble2;
	public boolean go;
	public static final int TEST_UNCON = -1, TESTING = 0, TEST_READY = 1,
			TEST_END = 2, TEST_DISCON = 3, TEST_SEARCHING = 4,
			TEST_NOT_FIND = 5;
	public int test_state = TEST_UNCON;
	private String addr;// 蓝牙设备地址

	public BLE2Ctrl(Context pContext) {
		context = pContext;
		ble2 = new BLE2(context);
		ble2.setState(state);
	}

	private BLE2State state = new BLE2State() {

		@Override
		public void searching(BluetoothDevice pDevice) {
			// TODO 搜索中.. 调用onSearching(Activity中重写)
			Log.i(JSJHActivity.TAG, "BLE2State.searching()");
			onSearching(pDevice);
		}

		@Override
		public void searchEnd() {
			// TODO 搜索结束
			onSearchEnd();
		}

		@Override
		public void onStateChanged(int pState) {
			// TODO
			switch (pState) {
			case BluetoothAdapter.STATE_CONNECTED: {
				// 已连接
				test_state = TEST_READY;
				onConnected(test_state);
			}
				break;
			case BluetoothAdapter.STATE_DISCONNECTED: {
				// 断开连接
				test_state = TEST_UNCON;
				onDisconnected();
			}
				break;
			}
		}
	};

	public void stopSearch() {
		ble2.stopSearch();
	}

	/**
	 * Fields thread : TODO 与蓝牙交互线程
	 */
	private GcThread thread = new GcThread(new GcRunnable() {

		@Override
		public void run() {
			// TODO
			BLE2Ctrl.this.run();
		}
	});

	/**
	 * <b>连接设备</b>
	 * 
	 * @param pAddr
	 * 
	 */
	public void connectDevice(String pAddr) {
		addr = pAddr;
		onConnected(test_state = (ble2.connect(addr) ? TEST_READY : TEST_UNCON));
	}

	public void disconnect() {
		ble2.destory();
	}

	public void finish() {
		ble2.destory();
	}

	/**
	 * <b>获取状态</b>
	 * 
	 * @return int
	 */
	public int getState() {
		return test_state;
	}

	/**
	 * <b>开始测量</b>
	 * 
	 * @return boolean
	 */
	public boolean startTest() {
		if (thread != null) {
			thread.stop();
		}
		if (test_state == TEST_READY) {
			thread.start();
		}
		return test_state == TEST_READY;
	}

	public void stop() {
		go = false;
	}

	/**
	 * <b>启动查找</b>
	 */
	public void search() {
		Log.i(JSJHActivity.TAG, "BLE2Ctrl.search()");
		ble2.startSearch();
	}

	public void run() {

	}

	/**
	 * <b>连接断开</b>
	 */
	public void onDisconnected() {
	}

	/**
	 * <b>连接结果</b>
	 */
	public void onConnected(int pResult) {
	}

	/**
	 * <b>正在搜索</b>
	 */
	public void onSearching(BluetoothDevice pDevice) {
	}

	/**
	 * <b>搜索结束</b>
	 */
	public void onSearchEnd() {
	}

	/**
	 * <b>准备</b>
	 */
	public void onReady() {
	}

	/**
	 * <b>开始测量</b>
	 */
	public void onStart() {
	}

	/**
	 * <b>人为停止</b>
	 */
	public void onStop() {
	}

	/**
	 * <b>分析结果</b>
	 */
	public void onAnalysing() {
	}

	/**
	 * <b>错误</b>
	 */
	public void onError(String pMsg) {
	}

	/**
	 * <b>接收数据中</b>
	 */
	public void onReceiving(byte[] pData) {
	}

	/**
	 * <b>得到结果</b>
	 */
	public void onResult(String pResult) {
	}

}
