package com.gc.ay.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gc.ay.R;
import com.gc.ay.bean.Block;
import com.gc.ay.util.Consts;

/**
 * @author Leong
 *
 */
public class BlockView extends ScrollView implements OnClickListener {

	private int count;
	private List<Block> blocks;
	private int blockWidth;
	private LinearLayout superLayout;// 包裹所有磁贴
	private OnClickListener onClickListener;

	public BlockView(Context context) {
		super(context);
		// TODO
	}

	public BlockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO
	}

	public BlockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO
	}

	/**
	 * @param picoList
	 *            图标列表
	 * @param pcolorList
	 *            磁贴背景颜色列表
	 * @param pnameList
	 *            磁贴名称列表
	 */
	public void init(List<Block> pBlocks) {
		blocks = pBlocks;
		count = pBlocks.size();
		blockWidth = Consts.Width / 2 - 2 * Consts.BLOCK_SPACE;
		initSuperLayout();
		initBlocks();
	}

	/**
	 * 初始化父布局
	 */
	private void initSuperLayout() {
		superLayout = new LinearLayout(getContext());
		AbsListView.LayoutParams _params = new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT,
				AbsListView.LayoutParams.WRAP_CONTENT);
		superLayout.setOrientation(LinearLayout.VERTICAL);
		addView(superLayout, _params);
	}

	/**
	 * 遍历初始化磁贴
	 */
	private void initBlocks() {
		LinearLayout _HorLayout = null;
		for (int i = 0; i < count; i++) {
			if (i % 2 == 0) {
				_HorLayout = getHorizontalLayout();
			}
			addBlock(i, _HorLayout);
		}
		if (count % 2 != 0) {
			// 奇数个磁贴，再加一个空磁贴
			addBlock(-1, _HorLayout);
		}
	}

	/**
	 * 生成、插入磁贴
	 */
	private void addBlock(int pIndex, LinearLayout pLayout) {
		LinearLayout.LayoutParams _params = new LinearLayout.LayoutParams(0,
				LinearLayout.LayoutParams.MATCH_PARENT);
		_params.weight = 1;
		_params.bottomMargin = Consts.BLOCK_SPACE;
		_params.rightMargin = Consts.BLOCK_SPACE;
		_params.leftMargin = Consts.BLOCK_SPACE;
		_params.topMargin = Consts.BLOCK_SPACE;
		LinearLayout _layout = new LinearLayout(getContext());
		if (pIndex != -1) {
			_layout.setBackgroundColor(getContext().getResources().getColor(
					blocks.get(pIndex).getColor()));
			if (blocks.get(pIndex).getIco() != -1) {
				addICO(pIndex, _layout);
			}
			addName(pIndex, _layout);
			_layout.setId(blocks.get(pIndex).getId());
		} else {
			_layout.setId(-1);
			_layout.setBackgroundColor(getContext().getResources().getColor(
					blocks.get(0).getColor()));
		}
		_layout.setOrientation(LinearLayout.VERTICAL);// 设置垂直布局
		_layout.setOnClickListener(this);
		_layout.setGravity(Gravity.CENTER);
		pLayout.addView(_layout, _params);

	}

	/**
	 * 向磁贴中增加图标
	 */
	private void addICO(int pIndex, LinearLayout pLayout) {
		ImageView _img = new ImageView(getContext());
		LinearLayout.LayoutParams _params = new LinearLayout.LayoutParams(
				blockWidth / 2, blockWidth / 2);
		_params.gravity = Gravity.CENTER;
		_img.setImageResource(blocks.get(pIndex).getIco());
		pLayout.addView(_img, _params);
	}

	/**
	 * 向磁贴中增加名称
	 */
	private void addName(int pIndex, LinearLayout pLayout) {
		TextView _name = new TextView(getContext());
		LinearLayout.LayoutParams _params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		_params.gravity = Gravity.CENTER;
		_name.setText(blocks.get(pIndex).getName());
		_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		_name.setTextColor(getContext().getResources().getColor(R.color.white));
		pLayout.addView(_name, _params);
	}

	/**
	 * 生成每行布局
	 */
	private LinearLayout getHorizontalLayout() {
		LinearLayout _layout = new LinearLayout(getContext());
		LinearLayout.LayoutParams _params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, Consts.BLOCK_HEIGHT);
		_layout.setOrientation(LinearLayout.HORIZONTAL);
		superLayout.addView(_layout, _params);
		return _layout;
	}

	@Override
	public void onClick(View v) {
		// TODO
		if (onClickListener != null) {
			onClickListener.onClick(v);
		}
	}

	@Override
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

}
