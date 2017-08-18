package com.jsd.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsd.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 下部选择Bar
 * * @author sf
 * 2017/3/15
 */

public class BottomBar extends LinearLayout {
    private static final String TAG = BottomBar.class.getSimpleName();
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private int curIndex = 0, lastIndex = 0;
    private List<TextView> textViewList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();
    private LinearLayout llGroup;

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }
    public BottomBar(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    private void initViews(){
        LayoutInflater.from(mContext).inflate(R.layout.jsd_view_bottombar, this, true);
        llGroup = (LinearLayout)findViewById(R.id.ll_group);
    }

    public void updateViews(List<Integer> icons,List<String> lables){
        if (icons.size() != lables.size() )
            return;
        int i = 0;
        for(String lable:lables){
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.jsd_view_itme_bottombar,null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,1);
            itemView.setLayoutParams(lp);
            itemView.setId(i);
            ImageView iv_icon = (ImageView)itemView.findViewById(R.id.iv_icon);
            TextView tv_lable = (TextView)itemView.findViewById(R.id.tv_lable);
            iv_icon.setImageResource(icons.get(i));
            tv_lable.setText(lable);
            itemView.setOnClickListener(mClickListener);
            llGroup.addView(itemView);

            textViewList.add(tv_lable);
            imageViewList.add(iv_icon);
            i++;
        }
        textViewList.get(curIndex).setSelected(true);
        imageViewList.get(curIndex).setSelected(true);
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            curIndex = id;
            if (curIndex == lastIndex)
                return;
            setSelected();
            lastIndex = curIndex;
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(id);
        }
    };

    public void setCurPosition(int position) {
        curIndex = position;
        if (!(curIndex == 0 && lastIndex == 0)){
            setSelected();
        }
        lastIndex = curIndex;
    }

    public void performClick(int position){
        setCurPosition(position);
        if (mItemClickListener != null)
            mItemClickListener.onItemClick(position);
    }

    private void setSelected() {
        textViewList.get(curIndex).setSelected(true);
        imageViewList.get(curIndex).setSelected(true);
        textViewList.get(lastIndex).setSelected(false);
        imageViewList.get(lastIndex).setSelected(false);
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }
}
