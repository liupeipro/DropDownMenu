package com.peil.widget.ddmenu.base;

import android.view.View;

/**
 * Created on 2017/2/22.
 */
public interface IDropDownTabTitleBase {


    /**
     * 设置tab ID
     *
     * @param tabId
     */
    void setTabId(int tabId);

    /**
     * 获取tab id
     *
     * @return
     */
    int getTabId();

    /**
     * 设置该tab选中
     *
     * @param select 具体的选中状态
     */
    void setTabSelected(boolean select);

    /**
     * 该Tab是否被选中
     *
     * @return
     */
    boolean isTabSelected();

    /**
     * 获取该tabview
     *
     * @return
     */
    View getTabView();

    /**
     * 设置tabview的点击事件
     *
     * @param listener
     */
    void setOnItemClickedListener(
            DDTabTitleBaseView.OnDropTabViewClickListener listener);

    /**
     * 更新子View之前之后需要做的事
     *
     * @param before true=之前；false=之后
     */
    void onUpdateItemsBefore(boolean before);

    /**
     * 重置为初始状态
     */
    void reset();

    void notify(String action, Object data);
}
