package com.peil.widget.ddmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.peil.widget.ddmenu.base.IDropDownTabContentBase;
import com.peil.widget.ddmenu.base.IDropDownTabTitleBase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hoperun01 on 2017/2/22.
 */
public class DropDownMenu extends RelativeLayout {
    private ListView mListView;
    //    title
    private DropDownTitleLayout mTitleLayout;
    //title content
    private DropDownTitleContentLayout mTitleContentLayout;
    //    content 内容区域
    private FrameLayout mSubContentLayout;

    /**
     * title 事件监听
     */
    private DropDownTitleLayout.OnDDTitleListener mOnTitleListener = new DropDownTitleLayout.OnDDTitleListener() {
        @Override
        public void OnItemSelectChanged(int tabId) {
            //title tab 变更，通知 contentLayout 切换内容
            mTitleContentLayout.updateItemViews(tabId);
        }
    };

    /**
     * title content 事件监听
     */
    private DropDownTitleContentLayout.OnDDTitleContentListener mOnTitleContentListener = new DropDownTitleContentLayout.OnDDTitleContentListener() {
        @Override
        public void onContentClosed() {
            mTitleLayout.updateItemViews(mTitleLayout.getLastSelectedTabId());
        }
    };

    public DropDownMenu(Context context) {
        super(context);
        initView();
    }

    public DropDownMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DropDownMenu(Context context, AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_ddloadmore_listview_main, this);

        //findviews
        mTitleLayout = (DropDownTitleLayout) findViewById(R.id.layout_title);
        mTitleContentLayout = (DropDownTitleContentLayout) findViewById(
                R.id.layout_mask);
        mSubContentLayout = (FrameLayout) findViewById(R.id.layout_content);
        mListView = (ListView) mSubContentLayout
                .findViewById(R.id.view_content_list);

        initListView();

        //init listener
        mTitleLayout.setOnTitleListener(mOnTitleListener);
        mTitleContentLayout.setOnDDTitleContentListener(mOnTitleContentListener);
    }

    private void initListView() {
//        在执行一个 Layout 动画时开启或关闭子控件的绘制缓存。默认情况下，绘制缓存是开启的，但是这将阻止嵌套 Layout 动画的正常执行。对于嵌套动画，你必须禁用这个缓存。
        mListView.setAnimationCacheEnabled(false);
        //关闭滚动条显示
        mListView.setHorizontalScrollBarEnabled(false);
        mListView.setVerticalScrollBarEnabled(false);

        //去除Group的箭头图标
//        mListView.setGroupIndicator(null);
        //去除listview的拖动背景色
        mListView.setCacheColorHint(getResources().getColor(R.color
                .transparent));
        mListView.setScrollingCacheEnabled(false);
        //去除分割线
        mListView.setDivider(null);
        //去除selector
        mListView.setSelector(R.drawable.listSelector);
        //关闭快速滑动
        mListView.setFastScrollEnabled(false);
        //去除上下阴影
        mListView.setVerticalFadingEdgeEnabled(false);
        //去除上下阴影
        mListView.setHorizontalFadingEdgeEnabled(false);
        //设置背景色
        mListView.setBackgroundColor(getResources().getColor(R.color.bg_eaeaea));
        // ViewGroup本身进行处理，不管是否处理成功，都不会分发给ChildView处理
        // mListView.getRefreshableView().setDescendantFocusability(
        // ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//        ViewCompat.setOverScrollMode(mListView, ViewCompat.OVER_SCROLL_NEVER);
    }

    /**
     * 让item恢复到未选中状态
     */
    public void closeMenu() {
        mTitleLayout.forceSetUnselectedState();
        mTitleContentLayout.forceSetUnselectedState();
        //
        mTitleLayout.setVisibility(View.VISIBLE);
        mTitleContentLayout.setVisibility(View.GONE);
        mSubContentLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 设置titleviews
     *
     * @param itemViews
     */
    public void setTitleItemViews(List<IDropDownTabTitleBase> itemViews, Drawable spaceDrawable) {
        mTitleLayout.addItemViews(itemViews, spaceDrawable);
    }

    /**
     * 设置title content views
     *
     * @param itemContentViews
     */
    public void setTitleContentItemViews(List<IDropDownTabContentBase> itemContentViews) {
        mTitleContentLayout.addItemViews(itemContentViews);
    }

    public void notifyTitleItem(int tabId, String action, Object data) {
        mTitleLayout.nofityItem(tabId, action, data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //注：activity层已经消费了该事件，进不到这里来，所以该代码无效
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //消费该事件，收回下拉菜单
            closeMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 判断最后选中的tab状态是否是选中状态
     *
     * @return
     */
    public boolean hasMenuTabSelected() {
        return mTitleLayout.hasLastTabSelected();
    }

    public ListView getLoadMoreListView() {
        return mListView;
    }

}
