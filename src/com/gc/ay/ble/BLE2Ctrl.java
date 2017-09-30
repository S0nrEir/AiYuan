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
 * <b>������</b>BLE2Ctrl<br/>
 * <p>
 * <b>��;��</b>BT2.0������<br/>
 * <p>
 * <b>���ߣ�</b>Leong<br/>
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
	private String addr;// �����豸��ַ

	public BLE2Ctrl(Context pContext) {
		context = pContext;
		ble2 = new BLE2(context);
		ble2.setState(state);
	}

	private BLE2State state = new BLE2State() {

		@Override
		public void searching(BluetoothDevice pDevice) {
			// TODO ������.. ����onSearching(Activity����д)
			Log.i(JSJHActivity.TAG, "BLE2State.searching()");
			onSearching(pDevice);
		}

		@Override
		public void searchEnd() {
			// TODO ��������
			onSearchEnd();
		}

		@Override
		public void onStateChanged(int pState) {
			// TODO
			switch (pState) {
			case BluetoothAdapter.STATE_CONNECTED: {
				// ������
				test_state = TEST_READY;
				onConnected(test_state);
			}
				break;
			case BluetoothAdapter.STATE_DISCONNECTED: {
				// �Ͽ�����
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
	 * Fields thread : TODO �����������߳�
	 */
	private GcThread thread = new GcThread(new GcRunnable() {

		@Override
		public void run() {
			// TODO
			BLE2Ctrl.this.run();
		}
	});

	/**
	 * <b>�����豸</b>
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
	 * <b>��ȡ״̬</b>
	 * 
	 * @return int
	 */
	public int getState() {
		return test_state;
	}

	/**
	 * <b>��ʼ����</b>
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
	 * <b>��������</b>
	 */
	public void search() {
		Log.i(JSJHActivity.TAG, "BLE2Ctrl.search()");
		ble2.startSearch();
	}

	public void run() {

	}

	/**
	 * <b>���ӶϿ�</b>
	 */
	public void onDisconnected() {
	}

	/**
	 * <b>���ӽ��</b>
	 */
	public void onConnected(int pResult) {
	}

	/**
	 * <b>��������</b>
	 */
	public void onSearching(BluetoothDevice pDevice) {
	}

	/**
	 * <b>��������</b>
	 */
	public void onSearchEnd() {
	}

	/**
	 * <b>׼��</b>
	 */
	public void onReady() {
	}

	/**
	 * <b>��ʼ����</b>
	 */
	public void onStart() {
	}

	/**
	 * <b>��Ϊֹͣ</b>
	 */
	public void onStop() {
	}

	/**
	 * <b>�������</b>
	 */
	public void onAnalysing() {
	}

	/**
	 * <b>����</b>
	 */
	public void onError(String pMsg) {
	}

	/**
	 * <b>����������</b>
	 */
	public void onReceiving(byte[] pData) {
	}

	/**
	 * <b>�õ����</b>
	 */
	public void onResult(String pResult) {
	}

}
