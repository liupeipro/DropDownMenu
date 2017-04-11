package com.peil.dropdownmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.peil.widget.ddmenu.base.DDTabContentBaseView;


/**
 * Created on 2017/2/23.
 */

public class DropDownTabContentTextView extends DDTabContentBaseView {
    private TextView textView;

    public DropDownTabContentTextView(Context context) {
        super(context);
    }

    public DropDownTabContentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initSubView() {
        mLayoutInflater.inflate(R.layout.view_dropdownmenu_tabcontent_textview_main, this);


        textView = (TextView) findViewById(R.id.textView);

        textView.setText("空的");
    }

    @Override
    protected void onTabSelected() {

    }

    @Override
    protected void onTabUnSelected() {

    }

    @Override
    public DropDownTabContentTextView getTabView() {
        return this;
    }

    @Override
    public void reset() {

    }
}
