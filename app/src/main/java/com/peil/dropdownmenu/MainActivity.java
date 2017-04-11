package com.peil.dropdownmenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.peil.widget.ddmenu.DropDownMenu;
import com.peil.widget.ddmenu.base.IDropDownTabContentBase;
import com.peil.widget.ddmenu.base.IDropDownTabTitleBase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {



    DropDownMenu dropDownListView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dropDownListView = (DropDownMenu) findViewById(R.id.view_dropdownmenu);


        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.sec_ques_selector));
        dropDownListView.getLoadMoreListView().setAdapter(adapter);

        onInitDDMenuData();
    }

    private void onInitDDMenuData(){
        DropDownTitleView title_normal = new DropDownTitleView(this);
        title_normal.setTabId(Constant.TAB_ID_FOR_FIRST);
        DropDownTitleView title_second = new DropDownTitleView(this);
        title_second.setTabId(Constant.TAB_ID_FOR_SECOND);

        DropDownTabContentTextView content_first = new DropDownTabContentTextView(this);
        content_first.setTabId(Constant.TAB_ID_FOR_FIRST);


        DropDownTabContentTextView content_second = new DropDownTabContentTextView(this);
        content_second.setTabId(Constant.TAB_ID_FOR_SECOND);

        List<IDropDownTabTitleBase> itemViews = new ArrayList<IDropDownTabTitleBase>();
        itemViews.add(title_normal);
        itemViews.add(title_second);

        List<IDropDownTabContentBase> contentViews = new ArrayList<IDropDownTabContentBase>();
        contentViews.add(content_first);
        contentViews.add(content_second);

        dropDownListView.setTitleItemViews(itemViews,null);
        dropDownListView.setTitleContentItemViews(contentViews);

        dropDownListView.notifyTitleItem(Constant.TAB_ID_FOR_FIRST,Constant.ACTION_NOTIFY_TEXT,"购物");
        dropDownListView.notifyTitleItem(Constant.TAB_ID_FOR_SECOND,Constant.ACTION_NOTIFY_TEXT,"美食");


    }


}
