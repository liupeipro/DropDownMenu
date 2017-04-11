package com.peil.widget.ddmenu.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.peil.widget.ddmenu.DropDownTitleContentLayout;


/**
 * Created on 2017/2/22.
 */
public abstract class DDTabContentBaseView extends RelativeLayout implements IDropDownTabContentBase {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    //    记录该tab是否被选中
    private boolean mTabSelected = false;
    private static final int TAB_NORMAL = -1;
    /**
     * 创建的时候指定，后期不会变更
     */
    private int mTabId = TAB_NORMAL;
    //显示时是否有动画
    private boolean mShowAnimation = false;
    //隐藏时是否有动画
    private boolean mHideAnimation = false;

    public DDTabContentBaseView(Context context) {
        super(context);
        initView(context);
    }

    public DDTabContentBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

//        初始化子类的view
        initSubView();
    }

    @Override
    public void setTabId(int tabId) {
        mTabId = tabId;
    }

    @Override
    public int getTabId() {
        if (mTabId == TAB_NORMAL) {
            throw new NullPointerException("A TabId Must be specified");
        }

        return mTabId;
    }

    @Override
    public void setTabSelected(boolean selected) {
        mTabSelected = selected;
        if (mTabSelected) {
            onTabSelected();
        } else {
            onTabUnSelected();
        }
    }

    @Override
    public boolean isTabSelected() {
        return mTabSelected;
    }

    /**
     * 初始化子类的view
     */
    protected abstract void initSubView();

    /**
     * tab 选中后的处理
     */
    protected abstract void onTabSelected();

    /**
     * tab 未选中的处理
     */
    protected abstract void onTabUnSelected();

    public boolean hasShowAnimation() {
        return mShowAnimation;
    }

    public void setShowAnimation(boolean showAnimation) {
        this.mShowAnimation = showAnimation;
    }

    public boolean hasHideAnimation() {
        return mHideAnimation;
    }

    public void setHideAnimation(boolean hideAnimation) {
        this.mHideAnimation = hideAnimation;
    }

    protected DropDownTitleContentLayout.OnDDTitleContentListener mOnDDTitleContentListener = null;

    /**
     * 通知父容器关闭该菜单
     */
    protected void onContentClosed() {
        if (mOnDDTitleContentListener != null) {
            mOnDDTitleContentListener.onContentClosed();
        }
    }

    @Override
    public void setOnDDTitleContentListener(DropDownTitleContentLayout.OnDDTitleContentListener listener) {
        mOnDDTitleContentListener = listener;
    }

}
