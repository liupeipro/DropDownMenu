package com.peil.widget.ddmenu.base;

import android.view.View;

import com.peil.widget.ddmenu.DropDownTitleContentLayout;


/**
 * Created on 2017/2/22.
 */
public interface IDropDownTabContentBase {

    /**
     * 设置tab ID，必须在创建的时候指定，不然会报异常
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
     * 获取该tabview
     *
     * @return
     */
    View getTabView();

    /**
     * 该Tab是否被选中
     *
     * @return
     */
    boolean isTabSelected();

    void setTabSelected(boolean selected);

    void setOnDDTitleContentListener(DropDownTitleContentLayout.OnDDTitleContentListener listener);

    void reset();

}
