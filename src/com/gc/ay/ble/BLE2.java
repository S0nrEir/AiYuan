package com.gc.ay.ble;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.gc.ay.activity.JSJHActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * <br/>
 * <b>������</b>BLE2<br/>
 * <p>
 * <b>��;��</b><br/>
 * <p>
 * <b>���ߣ�</b>Leong<br/>
 * <p>
 */
public class BLE2 {
	private Context context;
	private BluetoothAdapter adapter;
	private BluetoothDevice device;
	private BluetoothSocket socket;
	private final String UUID_STR = "00001101-0000-1000-8000-00805F9B34FB";
	private InputStream in;
	private OutputStream out;

	public BLE2(Context pContext) {
		context = pContext;
		adapter = BluetoothAdapter.getDefaultAdapter();
		if(!isEanble()){
			open();
		}
		// ע�����Խ��յ����������������豸��receiver
		IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		context.registerReceiver(receiver, mFilter);
		// ע��������ʱ��receiver
		mFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		context.registerReceiver(receiver, mFilter);
		// ����״̬�ı�receiver
		mFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		context.registerReceiver(receiver, mFilter);

	}

	private void disconnectSokcet() {
		if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void destory() {
		context.unregisterReceiver(receiver);
		disconnectSokcet();
	}

	public void searching(BluetoothDevice pDevice) {
		if (state != null) {
			state.searching(pDevice);
		}
	}

	public void searchEnd() {
		if (state != null) {
			state.searchEnd();
		}
	}

	/**
	 * <b>��ʼ����</b>
	 *
	 */
	public void startSearch() {
		Log.i(JSJHActivity.TAG, "BLE2.startSearch()");
		adapter.startDiscovery();
	}

	/**
	 * <b>ֹͣ����</b>
	 *
	 */
	public void stopSearch() {
		adapter.cancelDiscovery();
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO
			String action = intent.getAction();
			// ����Ѿ��������������豸
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				Log.i(JSJHActivity.TAG, "�ҵ����豸");
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				searching(device);
				// �������Ĳ����Ѿ��󶨵������豸
				// if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
				// }
			} else if (action
					.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				// �������
				searchEnd();

			} else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				if (state != null) {
					state.onStateChanged(adapter.getState());
				}
			}
		}
	};

	/**
	 * <b>����״̬</b>
	 * 
	 * @return boolean
	 */
	public boolean isEanble() {
		return adapter.isEnabled();
	}

	/**
	 * <b>������</b>
	 * 
	 * @return boolean
	 */
	public boolean open() {
		return adapter.enable();
	}

	/**
	 * <b>�ر�����</b>
	 * 
	 * @return boolean
	 */
	public boolean close() {
		return adapter.disable();
	}

	/**
	 * <b>�����豸</b>
	 * 
	 * @param pAddr
	 *            xx:xx:xx:xx:xx:xx
	 * @return boolean
	 */
	public boolean connect(String pAddr) {
		disconnectSokcet();
		adapter.startDiscovery();
		device = adapter.getRemoteDevice(pAddr);
		UUID mactekHartModemUuid = UUID.fromString(UUID_STR);
		try {
			adapter.cancelDiscovery();
			if (android.os.Build.VERSION.SDK_INT >= 10) {
				socket = device
						.createInsecureRfcommSocketToServiceRecord(mactekHartModemUuid);
			} else {
				socket = device
						.createRfcommSocketToServiceRecord(mactekHartModemUuid);
			}
			socket.connect();
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void flush() throws IOException {
		out.flush();
	}

	public int read() throws IOException {
		return in.read();
	}

	public OutputStream getOutStream() {
		return out;
	}

	public InputStream getInStream() {
		return in;
	}

	public void read(byte[] pBytes) throws IOException {
		in.read(pBytes);
	}

	public void write(int pInt) throws IOException {
		out.write(pInt);
	}

	public void write(byte[] pBytes) throws IOException {
		out.write(pBytes);
	}

	private BLE2State state;

	public void setState(BLE2State pState) {
		state = pState;
	}
}

abstract class BLE2State {
	public abstract void onStateChanged(int pState);

	public abstract void searchEnd();

	public abstract void searching(BluetoothDevice pDevice);
}
