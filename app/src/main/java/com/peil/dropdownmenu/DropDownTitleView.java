package com.peil.dropdownmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peil.widget.ddmenu.base.DDTabTitleBaseView;

/**
 * Created by liupei on 2017/2/23.
 */
public class DropDownTitleView extends DDTabTitleBaseView {
    //文字
    private TextView mTvTitle;
    //    右下的图标
    private ImageView mImgArrow;

    public DropDownTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropDownTitleView(Context context) {
        super(context);
    }

    @Override
    public DropDownTitleView getTabView() {
        return this;
    }

    @Override
    protected void initSubView() {
        setBackgroundColor(getResources().getColor(com.peil.widget.ddmenu.R.color.white));

        //初始化
        mTvTitle = new TextView(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT,
                RelativeLayout.TRUE);
        mTvTitle.setLayoutParams(params);
        mTvTitle.setTextColor(getResources().getColor(com.peil.widget.ddmenu.R.color.black));
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(com.peil.widget.ddmenu.R.dimen.font_size_14));

        mImgArrow = new ImageView(mContext);
        mImgArrow.setScaleType(ImageView.ScaleType.FIT_XY);

        RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        imgParams.addRule(RelativeLayout.CENTER_VERTICAL,
                RelativeLayout.TRUE);

        imgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
                RelativeLayout.TRUE);

        int padding_5 = mContext.getResources()
                .getDimensionPixelSize(com.peil.widget.ddmenu.R.dimen.padding_5);

        imgParams.setMargins(0, 0, padding_5, padding_5);
        mImgArrow.setLayoutParams(imgParams);

        addView(mTvTitle);
        addView(mImgArrow);

        // normal 设置未选中
        onTabUnSelected();
    }

    @Override
    protected void onTabSelected() {
        mImgArrow.setBackgroundResource(R.mipmap.drop_down_selected_icon);
        mTvTitle.setTextColor(getResources().getColor(R.color.color_4990e2));
        setBackgroundColor(getResources().getColor(R.color.lightgray));
    }

    @Override
    protected void onTabUnSelected() {
        mImgArrow.setBackgroundResource(R.mipmap.drop_down_unselected_icon);
        mTvTitle.setTextColor(getResources().getColor(R.color.black));
        setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onUpdateItemsBefore(boolean before) {
        //设置title是否可以点击
        //更新item之前，禁掉点击，之后允许点击
        setClickable(!before);
    }

    @Override
    public void reset() {
        //现在没有重置状态，就假装是设置未选中状态吧
        onTabUnSelected();
    }

    @Override
    public void notify(String action, Object data) {
        if (Constant.ACTION_NOTIFY_TEXT.equalsIgnoreCase(action)) {
            //更新Title
            if (data instanceof String) {
                if (mTvTitle != null) {
                    String text = (String) data;
                    mTvTitle.setText(text);
                }
            }
        }

//        if (UPConstant.DROP_MENU_ACTION_UPDATE_TITLE.equalsIgnoreCase(action)) {
//            //更新Title
//            if (data instanceof String) {
//                String text = (String) data;
//                mTvTitle.setText(text);
//            }
//        } else if (UPConstant.DROP_MENU_ACTION_UPDATE_BEFORE.equalsIgnoreCase(action)) {
//            //开启和禁掉tab点击
//            if (data instanceof String) {
//                String before = (String) data;
//
//                boolean hasBefore = false;
//                if (UPConstant.DROP_MENU_ACTION_FLAG_TRUE.equalsIgnoreCase(before)) {
//                    hasBefore = true;
//                }
//
//                onUpdateItemsBefore(hasBefore);
//            }
//        }
    }


}
