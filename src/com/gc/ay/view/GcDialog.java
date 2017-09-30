package com.gc.ay.view;

import com.gc.ay.R;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Leong
 *
 */
public class GcDialog extends AlertDialog {

	private LayoutInflater mInflater;
	private View v;
	private Button button1, button2, button3, button4;
	private TextView title;
	private ImageView icon;
	private TextView msg;
	private View customPanel, contentPanel;
	private FrameLayout custom;
	private View buttonPanel;
	private Object tag;
	private boolean clickAfterClose = true;
	private Context mContext;

	public ListView getListView() {
		return mListView;
	}

	public boolean isClickAfterClose() {
		return clickAfterClose;
	}

	public void setClickAfterClose(boolean clickAfterClose) {
		this.clickAfterClose = clickAfterClose;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	/**
	 *
	 * <b>Description: </b>
	 * 
	 * @param pContext
	 * @param pTheme
	 */
	protected GcDialog(Context pContext, int pTheme) {
		super(pContext, pTheme);
		// TODO Auto-generated constructor stub
		mContext = pContext;
		init(pContext);
	}

	@SuppressLint("InflateParams")
	public GcDialog(Context pContext) {
		super(pContext, R.style.Theme_GC_AlertDialog);
		init(pContext);
	}

	@SuppressLint("InflateParams")
	private void init(Context pContext) {
		mContext = pContext;
		mInflater = getLayoutInflater();
		v = mInflater.inflate(R.layout.alert_dialog, null);
		setView(v);
		button1 = (Button) v.findViewById(R.id.button1);
		button2 = (Button) v.findViewById(R.id.button2);
		button3 = (Button) v.findViewById(R.id.button3);
		button4 = (Button) v.findViewById(R.id.button4);
		title = (TextView) v.findViewById(R.id.alertTitle);
		icon = (ImageView) v.findViewById(R.id.icon);
		msg = (TextView) v.findViewById(R.id.message);
		customPanel = (View) v.findViewById(R.id.customPanel);
		contentPanel = (View) v.findViewById(R.id.contentPanel);
		custom = (FrameLayout) v.findViewById(R.id.custom);
		buttonPanel = (View) v.findViewById(R.id.buttonPanel);
	}

	@Override
	public void show() {
		// TODO
		if (!mContext.getClass().getSuperclass().getSuperclass().getName()
				.equals(Activity.class.getName())) {
			// 不属于Activity传来的上下文，就设置成系统对话框
			getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		super.show();
	}

	@Override
	public void setIcon(int resId) {
		// TODO
		icon.setImageResource(resId);
	}

	public GcDialog setTitle(Object pTitle) {
		// TODO
		if (pTitle == null) {
			return this;
		}
		if (pTitle instanceof Integer) {
			title.setText(Integer.parseInt(pTitle.toString()));
		} else {
			title.setText(pTitle.toString());
		}
		return this;
	}

	public GcDialog setMessage(Object message) {
		// TODO
		contentPanel.setVisibility(View.VISIBLE);
		customPanel.setVisibility(View.GONE);
		if (message == null) {
			return this;
		}
		if (message instanceof Integer) {
			msg.setText(Integer.parseInt(message.toString()));
		} else {
			msg.setText(message.toString());
		}
		return this;
	}

	private View customerView;

	public View getCustomerView() {
		return customerView;
	}

	public void setCustomView(View view) {
		customerView = view;
		contentPanel.setVisibility(View.GONE);
		customPanel.setVisibility(View.VISIBLE);
		custom.addView(view);
	}

	private ListView mListView;

	public GcDialog setAdapter(String items[], int pMode) {
		createListView(pMode);
		ArrayAdapter<String> _a = new ArrayAdapter<String>(
				mContext,
				pMode != ListView.CHOICE_MODE_MULTIPLE ? android.R.layout.simple_dropdown_item_1line
						: android.R.layout.simple_list_item_multiple_choice,
				items);
		mListView.setAdapter(_a);
		setCustomView(mListView);
		return this;
	}

	private void createListView(int pMode) {
		mListView = new ListView(mContext);
		mListView.setCacheColorHint(color.transparent);
		mListView.setScrollingCacheEnabled(false);
		if (pMode != -1) {
			mListView.setChoiceMode(pMode);
		}
	}

	public GcDialog setAdapter(ListAdapter pAdapter, int pMode) {
		createListView(pMode);
		mListView.setAdapter(pAdapter);
		setCustomView(mListView);
		return this;
	}

	/**
	 * <b>单选事件</b>
	 * 
	 * @param pClickListener
	 * @return GcDialog
	 */
	public GcDialog setOnItemClickListener(OnItemClickListener pClickListener) {
		if (mListView != null && pClickListener != null) {
			mListView.setOnItemClickListener(pClickListener);
		}
		return this;
	}

	public GcDialog setCheckedItems(final boolean[] pIsChecked) {
		if (pIsChecked != null) {
			for (int i = 0; i < pIsChecked.length; i++) {
				mListView.setItemChecked(i, pIsChecked[i]);
			}
		}
		return this;
	}

	public static final int BUTTON4 = 0x04;
	/**
	 * @Fields isSelectAll : TODO 全选/反选 标识
	 */
	private boolean mIsSelectAll = true;

	/**
	 * <b> Dialog中ListView 的全选/反选</b>
	 */
	public void selecedOrCancelAll() {
		if (mListView != null) {
			int _ChildCount = mListView.getChildCount();
			for (int index = 0; index < _ChildCount; index++) {
				if (mListView.getCheckedItemPositions().get(index) == !mIsSelectAll) {
					mListView.setItemChecked(index, mIsSelectAll);
					continue;
				}
				if (mListView.getCheckedItemPositions().get(index) == mIsSelectAll) {
					mListView.setItemChecked(index, !mIsSelectAll);
					continue;
				}
			}
			if (mIsSelectAll) {
				mIsSelectAll = false;
			} else {
				mIsSelectAll = true;
			}
		}
	}

	public void close() {
		dismiss();
	}

	public boolean[] getCheckedPosition() {
		boolean[] _Selected = null;// 选中状态
		int _Items = 0;// ListView中Item个数
		_Items = mListView.getCount();
		_Selected = new boolean[_Items];
		SparseBooleanArray _TempArray = mListView.getCheckedItemPositions();
		for (int i = 0; i < _Items; i++) {
			if (_TempArray.get(i)) {
				_Selected[i] = true;
			} else {
				_Selected[i] = false;
			}
		}
		return _Selected;
	}

	private void btnSetText(Button pBtn, Object pText) {
		if (pText == null) {
			return;
		}
		if (pText instanceof Integer) {
			pBtn.setText(Integer.parseInt(pText.toString()));
		} else {
			pBtn.setText(pText.toString());
		}
	}

	public GcDialog setButton(int whichButton, Object text,
			final GcDialogOnClickListener listener) {
		// TODO
		buttonPanel.setVisibility(View.VISIBLE);
		switch (whichButton) {
		case AlertDialog.BUTTON_POSITIVE: {
			button1.setVisibility(View.VISIBLE);
			btnSetText(button1, text);
			button1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO
					listener.onClick(GcDialog.this, AlertDialog.BUTTON_POSITIVE);
					if (clickAfterClose) {
						dismiss();
					}
				}
			});
		}
			break;
		case AlertDialog.BUTTON_NEUTRAL: {
			button2.setVisibility(View.VISIBLE);
			btnSetText(button2, text);
			button2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO
					listener.onClick(GcDialog.this, AlertDialog.BUTTON_NEUTRAL);
					if (clickAfterClose) {
						dismiss();
					}
				}
			});
		}
			break;
		case AlertDialog.BUTTON_NEGATIVE: {
			button3.setVisibility(View.VISIBLE);
			btnSetText(button3, text);
			button3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO
					listener.onClick(GcDialog.this, AlertDialog.BUTTON_NEGATIVE);
					if (clickAfterClose) {
						dismiss();
					}
				}
			});
		}
			break;
		case BUTTON4: {
			button4.setVisibility(View.VISIBLE);
			btnSetText(button4, text);
			button4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO
					listener.onClick(GcDialog.this, BUTTON4);
					if (clickAfterClose) {
						dismiss();
					}
				}
			});
		}
			break;
		default:
			break;
		}
		return this;
	}

	public interface GcDialogOnClickListener {
		public void onClick(DialogInterface dialog, int which);
	}
}
