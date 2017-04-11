package com.peil.widget.ddmenu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.peil.widget.ddmenu.base.DDTabTitleBaseView;
import com.peil.widget.ddmenu.base.IDropDownTabTitleBase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hoperun01 on 2017/2/22.
 */

public class DropDownTitleLayout extends LinearLayout {

    private static final String TAG = DropDownTitleLayout.class.getSimpleName();

    public interface OnDDTitleListener {

        /**
         * item 改变事件
         *
         * @param tabId 改变的tabID
         */
        void OnItemSelectChanged(int tabId);
    }

    //默认
    private static final int TAB_SELECTED_NORMAL = -1;
    //最后选中的tabid
    private int mLastSelectedTabId = TAB_SELECTED_NORMAL;
    //Item view 集合
    private ArrayList<IDropDownTabTitleBase> mItemViews;
    //是否显示间隔的开关
    private boolean mShowSpaceLine = true;
    /* 水平间距 */
    private int mHorSpace = 0;
    //回调接口
    private OnDDTitleListener mOnTitleListener = null;

    /**
     * 点击事件
     */
    private DDTabTitleBaseView.OnDropTabViewClickListener mItemOnClickListener = new DDTabTitleBaseView.OnDropTabViewClickListener() {

        @Override
        public void onClick(int tabId) {
            updateItemViews(tabId);
        }
    };

    public DropDownTitleLayout(Context context) {
        super(context);
        initView();
    }

    public DropDownTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        mHorSpace = getResources().getDimensionPixelSize(R.dimen.padding_2);
        mItemViews = new ArrayList<IDropDownTabTitleBase>();
    }

    /**
     * add item Views
     *
     * @param itemViews     itemViews
     * @param spaceDrawable 分隔线的drawable
     */
    public void addItemViews(List<IDropDownTabTitleBase> itemViews, Drawable spaceDrawable) {
        reset();

        if (itemViews != null && !itemViews.isEmpty()) {
            mItemViews.addAll(itemViews);
        }

        int size = mItemViews.size();
        for (int i = 0; i < size; i++) {
            IDropDownTabTitleBase v = mItemViews.get(i);
//            v.setTabId(i);
            v.setOnItemClickedListener(mItemOnClickListener);
            v.getTabView().setLayoutParams(new LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            // 加入
            addView(v.getTabView());

            //间隔线开关 && 最后一个不加分隔线
            if (mShowSpaceLine && i != (size - 1)) {
                View spaceView = newSapceView();
                //木有配间隔线，就给个默认的
                if (spaceDrawable == null) {
                    //给个默认的分隔线
                    spaceDrawable = new ColorDrawable(getResources().getColor(R.color.lightgray));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    spaceView.setBackground(spaceDrawable);
                } else {
                    spaceView.setBackgroundDrawable(spaceDrawable);
                }
                addView(spaceView);
            }
        }
    }

    /**
     * 更新Item的状态
     */
    public void updateItemViews(int newTabId) {
        Log.d(TAG, "updateItemViews lastTab = " + mLastSelectedTabId + " , newTab=" + newTabId);

        //更新子View之前
        onUpdateItemsBefore(true);
        //判断需要更新的是上次选中的tab，还是新的tab
        if (mLastSelectedTabId == newTabId) {
            //需要更新的是上次选中,只更新该tab的状态
            IDropDownTabTitleBase tabItem = getTabItemView(mLastSelectedTabId);
            if (tabItem != null) {
                //点击当前选中的，只会是收起状态
                tabItem.setTabSelected(false);
                //还原标志位
                mLastSelectedTabId = TAB_SELECTED_NORMAL;
            }
        } else {
            //需要更新的是新tab,遍历更新所有item的状态
            for (IDropDownTabTitleBase item : mItemViews) {
                item.setTabSelected(item.getTabId() == newTabId);
            }
            mLastSelectedTabId = newTabId;
        }
        //更新子View之后
        onUpdateItemsBefore(false);

        //通知外部tab变更
        if (mOnTitleListener != null) {
            mOnTitleListener.OnItemSelectChanged(mLastSelectedTabId);
        }
    }

    /**
     * 强制让item恢复到未选中状态
     */
    public void forceSetUnselectedState() {
        //更新子View之前
        onUpdateItemsBefore(true);
        //遍历item，修改状态为未选中
        for (IDropDownTabTitleBase titleBase : mItemViews) {
            //如果是选择状态，就切换到未选中状态
            if (titleBase.isTabSelected()) {
                titleBase.setTabSelected(false);
            }
        }
        //更新子View之后
        onUpdateItemsBefore(false);
        //还原标志位
        mLastSelectedTabId = TAB_SELECTED_NORMAL;
        //通知外部tab变更
//        if (mOnTitleListener != null) {
//            mOnTitleListener.OnItemSelectChanged(mLastSelectedTabId);
//        }
    }

    /**
     * 强制重置
     */
    public void forceResetItems() {
        //更新子View之前
        onUpdateItemsBefore(true);
        //遍历item- reset
        for (IDropDownTabTitleBase titleBase : mItemViews) {
            titleBase.reset();
        }
        //更新子View之后
        onUpdateItemsBefore(false);
        //还原标志位
        mLastSelectedTabId = TAB_SELECTED_NORMAL;
    }


    public void nofityItem(int tabId, String action, Object data) {
        IDropDownTabTitleBase tabItem = getTabItemView(tabId);
        if (tabItem != null) {
            tabItem.notify(action, data);
        }
    }

    /**
     * 更新子View状态之前，比如禁止title点击;之后，允许title点击
     */
    private void onUpdateItemsBefore(boolean before) {
        for (IDropDownTabTitleBase v : mItemViews) {
            v.onUpdateItemsBefore(before);
        }
    }

    private IDropDownTabTitleBase getTabItemView(int tabId) {
        for (IDropDownTabTitleBase v : mItemViews) {
            if (tabId == v.getTabId()) {
                return v;
            }
        }
        return null;
    }

    /**
     * 获取最后选择的tab是否是选择状态
     *
     * @return
     */
    public boolean hasLastTabSelected() {
        IDropDownTabTitleBase tabView = getTabItemView(mLastSelectedTabId);
        if (tabView != null) {
            return tabView.isTabSelected();
        }
        return false;
    }

    /**
     * 重置
     */
    private void reset() {
        //置为初始状态
        mLastSelectedTabId = TAB_SELECTED_NORMAL;
        //重置子View的集合
        if (mItemViews != null && mItemViews.size() > 0) {
            mItemViews.clear();
        }
        //删除绑定的子View
        removeAllViews();
    }

    /**
     * 获取默认间隔线
     *
     * @return
     */
    private View newSapceView() {
        View lineView = new View(getContext());
        LinearLayout.LayoutParams lineParam = new LinearLayout.LayoutParams(
                mHorSpace, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        lineView.setLayoutParams(lineParam);
        return lineView;
    }


    public void setOnTitleListener(OnDDTitleListener listener) {
        mOnTitleListener = listener;
    }

    /**
     * 是否显示分割线，默认显示
     *
     * @param mShowSpaceLine
     */
    public void setShowSpaceLine(boolean mShowSpaceLine) {
        this.mShowSpaceLine = mShowSpaceLine;
    }

    /**
     * 设置分割线的宽度
     *
     * @param horSpace
     */
    public void setHorSpace(int horSpace) {
        this.mHorSpace = horSpace;
    }

    public int getLastSelectedTabId() {
        return mLastSelectedTabId;
    }

}
