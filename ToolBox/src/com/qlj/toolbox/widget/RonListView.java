package com.qlj.toolbox.widget;

import com.qlj.toolbox.util.Logger;
import com.qlj.toolbox.widget.pulltorefresh.ListViewHeader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class RonListView extends ListView implements OnScrollListener {

	private OnScrollListener mScrollListener;
	private RonListViewListener mListViewListener;
	boolean isFirstRow = false;

	public RonListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		super.setOnScrollListener(this);
	}

	public RonListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setOnScrollListener(this);
	}

	public RonListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		super.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Logger.e("RonListView", "scrollState: " + scrollState);

		// 正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
		// 回调顺序如下
		// 第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
		// 第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
		// 第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
		// 当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；
		// 由于用户的操作，屏幕产生惯性滑动时为2

		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}

		// 当滚到最后一行且停止滚动时，执行加载
		if (isFirstRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			isFirstRow = false;
			loadMore();
			Logger.e("RonListView", "scrollState: 请求一次");
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		Logger.e("RonListView", "firstVisibleItem: " + firstVisibleItem);
		Logger.e("RonListView", "visibleItemCount: " + visibleItemCount);
		Logger.e("RonListView", "totalItemCount: " + totalItemCount);

		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}

		// 判断是否滚到最后一行
		if (firstVisibleItem == 0 && totalItemCount > 0) {
			isFirstRow = true;
		}
	}

	float mLastY = -1;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Logger.e("onTouchEvent ", String.valueOf(ev.getRawY()));
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (getFirstVisiblePosition() == 0 && deltaY > 20) {
				// the first item is showing, header has shown or pull down.
			}
			break;
		default:
			// mLastY = -1; // reset
			// if (getFirstVisiblePosition() == 0) {
			// // invoke refresh
			// if (mEnablePullRefresh && mHeaderView.getVisiableHeight() >
			// mHeaderViewHeight) {
			// mPullRefreshing = true;
			// mHeaderView.setState(ListViewHeader.STATE_REFRESHING);
			// if (mListViewListener != null) {
			// mListViewListener.onRefresh();
			// }
			// }
			// resetHeaderHeight();
			// } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
			// // invoke load more.
			// if (mEnablePullLoad && mFooterView.getBottomMargin() >
			// PULL_LOAD_MORE_DELTA) {
			// startLoadMore();
			// }
			// resetFooterHeight();
			// }
			// break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	public void setListViewListener(RonListViewListener l) {
		mListViewListener = l;
	}

	public void startLoad() {
		if (mListViewListener != null) {
			mListViewListener.onRefresh();
		}
	}

	public void loadMore() {
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface RonListViewListener {
		public void onRefresh();

		public void onLoadMore();
	}

}
