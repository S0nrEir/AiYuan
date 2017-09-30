package com.gc.ay.ble;

import android.content.Context;

/**
 * <br/>
 * <b>类名：</b>MEDX_XDT_H600<br/>
 * <p>
 * <b>用途：</b>美的连快速心电检测仪，型号H600<br/>
 * <p>
 * <b>作者：</b>Leong<br/>
 * <p>
 */
public abstract class MEDX_JBQ_FA101 extends BLE2Ctrl {
	public static final int ERROR = -1;

	public MEDX_JBQ_FA101(Context pContext) {
		super(pContext);
	}

	@Override
	public void run() {
		// TODO
		try {
			char[] _cmdStart = new char[] { 0xff, 0xfe, 0x04, 0xB5, 0x01, 0xB0 };
			for (int i = 0; i < _cmdStart.length; i++) {
				ble2.write(_cmdStart[i]);
			}
			ble2.flush();
			char[] _cmdClear = new char[] { 0xff, 0xfe, 0x04, 0xB5, 0x01, 0xB1 };
			for (int i = 0; i < _cmdClear.length; i++) {
				ble2.write(_cmdClear[i]);
			}
			ble2.flush();
			char[] _cmdQuery = new char[] { 0xff, 0xfe, 0x04, 0xb7, 0x01, 0xB2 };
			go = true;
			while (go) {
				for (int i = 0; i < _cmdQuery.length; i++) {
					ble2.write(_cmdQuery[i]);
				}
				ble2.flush();

				int _cmd = ble2.read();
				switch (_cmd) {
				case 0x65: { 
					//65H+三字节代表长度的数字组合
					int _r1 = ble2.read();
					int _r2 = ble2.read();
					int _r3 = ble2.read();
					onResult(_r1 + "" + _r2 + "" + _r3);//显示结果
				}
					break;

				case 0x61: {
					//回传确认连接
				}
					break;
				default: {
				}
					break;
				}
				ble2.write(0xB7);
			}
		} catch (Exception e) {
			test_state = TEST_UNCON;
			onDisconnected();
		}
	}
}
