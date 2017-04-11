package com.peil.widget.ddmenu.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by liupei on 2017/2/22.
 */

public abstract class DDTabTitleBaseView extends RelativeLayout implements IDropDownTabTitleBase {

    /**
     * 对外公开的接口
     */
    public interface OnDropTabViewClickListener {
        /**
         * 当前tab被单击/选中
         *
         * @param tabId
         */
        void onClick(int tabId);
    }

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    //    记录该tab是否被选中
    private boolean mTabSelected = false;

    private static final int TAB_NORMAL = -1;
    /**
     * 创建的时候指定，后期不会变更
     */
    private int mTabId = TAB_NORMAL;

    //对外接口实例
    private OnDropTabViewClickListener mOnDropTabViewClickListener;


    public DDTabTitleBaseView(Context context) {
        super(context);
        initView(context);
    }

    public DDTabTitleBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    private void initView(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        //初始化子类的view
        initSubView();
        //设置该view为默认状态
        setTabSelected(false);
        //添加点击事件监听
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //回调接口，通知点击事件
                if (mOnDropTabViewClickListener != null) {
                    mOnDropTabViewClickListener.onClick(getTabId());
                }
            }
        });

    }


    @Override
    public boolean isTabSelected() {
        return mTabSelected;
    }

    @Override
    public void setOnItemClickedListener(OnDropTabViewClickListener listener) {
        mOnDropTabViewClickListener = listener;
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
    public void setTabSelected(boolean select) {
        mTabSelected = select;
        if (mTabSelected) {
            onTabSelected();
        } else {
            onTabUnSelected();
        }
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

}
