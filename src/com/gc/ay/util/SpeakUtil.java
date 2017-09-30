/**   
 * <br/><b>Title: </b>SpeakUtil.java <br/>
 * <p><b>Package: </b>com.gc.ay.util <br/>
 * <p><b>Description: </b>TODO<br/>
 * <p><b>author: </b>Leong<br/>
 * <p><b>date: </b>2015年4月10日 上午9:41:15<br/>
 * <p><b>version: </b>V1.0
 */
package com.gc.ay.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

/**
 * <br/>
 * <b>类名：</b>SpeakUtil<br/>
 * <p>
 * <b>用途：</b><br/>
 * <p>
 * <b>作者：</b>Leong<br/>
 * <p>
 * <b>时间：</b>2015年4月10日 上午9:41:15 <br/>
 * <p>
 */
public class SpeakUtil {
	private TextToSpeech mTs;
	private static SpeakUtil mUtil;

	private SpeakUtil(Context pContext) {
		mTs = new TextToSpeech(pContext, new OnInitListener() {

			@Override
			public void onInit(int pArg0) {
				// TODO

			}
		});
	}

	public void speak(String pText) {
		mTs.stop();
		mTs.speak(pText, TextToSpeech.QUEUE_ADD, null);
	}

	public static void speak(Context pContext, String pText) {
		if (mUtil == null) {
			mUtil = new SpeakUtil(pContext);
		}
		mUtil.speak(pText);
	}
}
