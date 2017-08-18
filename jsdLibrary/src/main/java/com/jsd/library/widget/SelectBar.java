package com.jsd.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsd.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：2017/3/23 16:14
 * 作者： sf
 * 功能描述：$desc$
 */

public class SelectBar extends LinearLayout {
    private Context mContext;
    private BottomBar.OnItemClickListener mItemClickListener;
    private int curIndex = 0, lastIndex = -1;
    private List<TextView> textViewList = new ArrayList<>();
    private LinearLayout llGroup;
    public SelectBar(Context context) {
        super(context);
        initViews(context);
    }
    public SelectBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }
    public SelectBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context){
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.jsd_view_bottombar, this, true);
        llGroup = (LinearLayout)findViewById(R.id.ll_group);
    }

    public void updateViews(List<String> lables){
        int i = 0;
        for(String lable:lables){
            TextView textView = new TextView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,1);

            textView.setId(i);
            textView.setGravity(Gravity.CENTER);
            textView.setText(lable);
            textView.setOnClickListener(mClickListener);
            textView.setTextColor(getResources().getColorStateList(R.color.selector_select_bar));
            if(i == 0){
                textView.setLayoutParams(lp);
                textView.setBackgroundResource(R.drawable.selector_left_half);
            }else if(i== lables.size()-1){
                lp.leftMargin = -2;
                textView.setBackgroundResource(R.drawable.selector_right_half);
                textView.setLayoutParams(lp);
            }else{
                lp.leftMargin = -2;
                textView.setBackgroundResource(R.drawable.selector_mind_half);
                textView.setLayoutParams(lp);
            }
            llGroup.addView(textView);
            textViewList.add(textView);
            i++;
        }
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            curIndex = id;
            setSelected();
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(id);
        }
    };
    public void setDefaultPosition(int index) {

        if (index >= textViewList.size() || index < 0)
            return;
        textViewList.get(index).performClick();
    }

    private void setSelected() {
        if (lastIndex != curIndex) {
            textViewList.get(curIndex).setSelected(true);
            if (lastIndex != -1)
                textViewList.get(lastIndex).setSelected(false);
            lastIndex = curIndex;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(BottomBar.OnItemClickListener listener){
        mItemClickListener = listener;
    }
}
