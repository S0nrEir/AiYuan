package com.gc.ay.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gc.ay.R;

public class WebDemoActivity extends Activity {
	private WebView wv;

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_demo);
		wv = (WebView) findViewById(R.id.wvWebDemo);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setDomStorageEnabled(true);
		wv.loadUrl("file:///android_asset/www/index.html");
		wv.addJavascriptInterface(new JsInterface(), "gc");
	}
	
	public void onClick(View v) {
		wv.loadUrl("javascript:jsMethod('JAVA调用Web JS中的jsMethod方法')");
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO
			Toast.makeText(WebDemoActivity.this, msg.obj.toString(),
					Toast.LENGTH_SHORT).show();
		}
	};

	class JsInterface{
		@JavascriptInterface
		public void toast(String pString) {
			handler.sendMessage(handler.obtainMessage(0, pString));
		}

		@JavascriptInterface
		public void closeActivity() {
			finish();
		}
	}
}
