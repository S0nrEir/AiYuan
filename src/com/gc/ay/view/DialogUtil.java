package com.gc.ay.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gc.ay.view.GcDialog.GcDialogOnClickListener;

/**
 * @author Leong
 *
 */
public class DialogUtil {
	/**
	 * 创建确认对话框
	 * 
	 * @param pContext
	 * @param pTitle
	 * @param pMsg
	 * @param pListener
	 *            确认按钮事件
	 * @return
	 */
	public static GcDialog createConfirmDialog(Context pContext, Object pTitle,
			Object pMsg, GcDialogOnClickListener pListener) {

		return new GcDialog(pContext)
				.setMessage(pMsg)
				.setTitle(pTitle)
				.setButton(AlertDialog.BUTTON_POSITIVE, "确定", pListener)
				.setButton(AlertDialog.BUTTON_NEUTRAL, "取消",
						new GcDialogOnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
	}

	/**
	 * 创建多按钮对话框
	 * 
	 * @param pContext
	 * @param pTitle
	 *            标题
	 * @param pMsg
	 *            提示信息
	 * @param pBtnNames
	 *            按钮名字数组
	 * @param pListener
	 *            按按钮事件 顺序与按钮名字一致
	 * @return
	 */
	public static GcDialog createConfirmDialog(Context pContext, Object pTitle,
			Object pMsg, Object[] pBtnNames, GcDialogOnClickListener[] pListener) {
		return setButton(
				new GcDialog(pContext).setMessage(pMsg).setTitle(pTitle),
				pBtnNames, pListener);
	}

	private static GcDialog setButton(GcDialog pDialog, Object[] pBtnNames,
			GcDialogOnClickListener[] pListener) {
		for (int i = 0; i < pBtnNames.length; i++) {
			if (i == 0) {
				pDialog.setButton(AlertDialog.BUTTON_POSITIVE, pBtnNames[i],
						pListener[i]);
			} else if (i == 1) {
				pDialog.setButton(AlertDialog.BUTTON_NEUTRAL, pBtnNames[i],
						pListener[i]);
			} else if (i == 2) {
				pDialog.setButton(AlertDialog.BUTTON_NEGATIVE, pBtnNames[i],
						pListener[i]);
			} else {
				pDialog.setButton(GcDialog.BUTTON4, pBtnNames[i], pListener[i]);
			}
		}
		return pDialog;
	}

	/**
	 * 单选对话框
	 */
	public static GcDialog createSingleChooseDialog(Context pContext,
			Object pTitle, String[] pItems, Object[] pBtnNames,
			GcDialogOnClickListener[] pListener,
			OnItemClickListener pItemClickListener) {
		return setButton(new GcDialog(pContext).setAdapter(pItems, -1)
				.setOnItemClickListener(pItemClickListener).setTitle(pTitle),
				pBtnNames, pListener);
	}

	/**
	 * 自定义单选
	 */
	public static GcDialog createCustomSingleChooseDialog(Context pContext,
			Object pTitle, ListAdapter pAdapter, Object[] pBtnNames,
			GcDialogOnClickListener[] pListener,
			OnItemClickListener pItemClickListener) {
		return setButton(new GcDialog(pContext).setAdapter(pAdapter, -1)
				.setOnItemClickListener(pItemClickListener).setTitle(pTitle),
				pBtnNames, pListener);
	}

	/**
	 * 多选
	 */
	public static GcDialog createMutilDialog(Context pContext, Object pTitle,
			String[] pItems, Object[] pBtnNames,
			GcDialogOnClickListener[] pListener, boolean[] pChecked,
			OnItemClickListener pItemClickListener) {
		return setButton(
				new GcDialog(pContext)
						.setAdapter(pItems, ListView.CHOICE_MODE_MULTIPLE)
						.setOnItemClickListener(pItemClickListener)
						.setTitle(pTitle), pBtnNames, pListener)
				.setCheckedItems(pChecked);
	}

	/**
	 * 文本对话框
	 */
	public static GcDialog createEditTextTextDialog(Context pContext,
			Object pTitle, String pDefaultValue, Object[] pBtns,
			GcDialogOnClickListener[] pOnClickListener, int pTextType) {
		final EditText _editText = new EditText(pContext);
		GcDialog mAlertDialog = new GcDialog(pContext);
		setButton(mAlertDialog, pBtns, pOnClickListener);
		mAlertDialog.setTitle(pTitle);
		mAlertDialog.setCustomView(_editText);
		_editText.setText(pDefaultValue);
		_editText.setInputType(pTextType);
		_editText.setSingleLine(true);
		_editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
		return mAlertDialog;
	}

	public static GcDialog createCustomDialog(final Context pContext,
			String pTitle, Object[] pBtns, GcDialogOnClickListener[] pListener,
			View pView) {
		GcDialog mAlertDialog = new GcDialog(pContext);
		mAlertDialog.setCustomView(pView);
		setButton(mAlertDialog, pBtns, pListener);
		return mAlertDialog;
	}

}
