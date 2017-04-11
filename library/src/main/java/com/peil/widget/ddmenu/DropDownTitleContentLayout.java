package com.peil.widget.ddmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


import com.peil.widget.ddmenu.base.IDropDownTabContentBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoperun01 on 2017/2/22.
 */

public class DropDownTitleContentLayout extends FrameLayout {

    private static final String TAG = DropDownTitleContentLayout.class
            .getSimpleName();

    public interface OnDDTitleContentListener {
        void onContentClosed();
    }

    private static final int TAB_SELECTED_NORMAL = -1;
    //最后选中的tab id
    private int mLastSelectedTabId = TAB_SELECTED_NORMAL;

    //views 集合
    private ArrayList<IDropDownTabContentBase> mItemViews;
    //回调接口
    protected OnDDTitleContentListener mOnDDTitleContentListener = null;


    public DropDownTitleContentLayout(Context context) {
        super(context);
        initView();
    }

    public DropDownTitleContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //在执行一个 Layout 动画时开启或关闭子控件的绘制缓存。默认情况下，绘制缓存是开启的，但是这将阻止嵌套 Layout 动画的正常执行。对于嵌套动画，你必须禁用这个缓存。
        setAnimationCacheEnabled(false);
        mItemViews = new ArrayList<IDropDownTabContentBase>();
    }

    /**
     * add item Views
     *
     * @param itemViews itemViews
     */
    public void addItemViews(List<IDropDownTabContentBase> itemViews) {
        reset();

        if (itemViews != null && !itemViews.isEmpty()) {
            mItemViews.addAll(itemViews);
        }

        int size = mItemViews.size();
        for (int i = 0; i < size; i++) {
            IDropDownTabContentBase v = mItemViews.get(i);
//            v.setTabId(i);
            v.setOnDDTitleContentListener(mOnDDTitleContentListener);

            // 加入
            FrameLayout.LayoutParams svParam = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            addView(v.getTabView(), svParam);
        }
    }

    /**
     * 切换tab
     *
     * @param newTabId
     */
    public void updateItemViews(int newTabId) {
        Log.d(TAG, "updateItemViews lastTab = " + mLastSelectedTabId + " , newTab=" + newTabId);
        //== -1
        if (TAB_SELECTED_NORMAL == newTabId) {
            //收起
            //对当前显示的tab进行状态切换
            IDropDownTabContentBase tabView = getTabItemView(mLastSelectedTabId);
            if (tabView != null) {
                //点击当前选中的，只能是收起
                tabView.setTabSelected(false);
                //还原标志位
                mLastSelectedTabId = TAB_SELECTED_NORMAL;
                setVisibility(View.GONE);
            }
        } else if (mLastSelectedTabId == newTabId) {
            //对当前显示的tab进行状态切换
            IDropDownTabContentBase tabView = getTabItemView(newTabId);
            if (tabView != null) {
                //点击当前选中的，只能是收起
                tabView.setTabSelected(false);
                //还原标志位
                mLastSelectedTabId = TAB_SELECTED_NORMAL;
                setVisibility(View.GONE);
            }
        } else {
            //需要更新的是新tab,遍历更新所有item的状态
            for (IDropDownTabContentBase item : mItemViews) {
                item.setTabSelected(item.getTabId() == newTabId);
            }
            mLastSelectedTabId = newTabId;
            //
            setVisibility(View.VISIBLE);
        }
    }

    /**
     * 强制让item恢复到默认(未选中)状态
     */
    public void forceSetUnselectedState() {
        //遍历item，修改状态为未选中
        for (IDropDownTabContentBase titleBase : mItemViews) {
            //如果是选择状态，就切换到未选中状态
            if (titleBase.isTabSelected()) {
                titleBase.setTabSelected(false);
            }
        }
        setVisibility(View.GONE);
        //还原标志位
        mLastSelectedTabId = TAB_SELECTED_NORMAL;
    }

    /**
     * 强制重置
     */
    public void forceResetItems() {
        //遍历item- reset
        for (IDropDownTabContentBase titleBase : mItemViews) {
            titleBase.reset();
        }

        //还原标志位
        mLastSelectedTabId = TAB_SELECTED_NORMAL;
    }


    /**
     * 根据 tabID 拿到 tabView 的引用
     *
     * @param tabId
     * @return
     */
    private IDropDownTabContentBase getTabItemView(int tabId) {
        for (IDropDownTabContentBase v : mItemViews) {
            if (tabId == v.getTabId()) {
                return v;
            }
        }
        return null;
    }

    /**
     * 重置
     */
    private void reset() {
        //重置子View的集合
        if (mItemViews != null && mItemViews.size() > 0) {
            mItemViews.clear();
        }
        //删除绑定的子View
        removeAllViews();
        //置为初始状态
        mLastSelectedTabId = TAB_SELECTED_NORMAL;
    }

    public int getLastSelectedTabId() {
        return mLastSelectedTabId;
    }

    public void setOnDDTitleContentListener(OnDDTitleContentListener listener) {
        this.mOnDDTitleContentListener = listener;
    }
}
