package com.jsd.library.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jsd.library.R;
import com.jsd.library.bean.PopBean;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;

import java.util.List;

/**
 * Created by Hankkin on 16/1/25.
 */
public class ListPopWindow extends PopupWindow{

    private Context context;        //上下文
    private View rootView;        //视图
    private List<PopBean> dataList; //item数据源
    private OnPopClickListener listener;    //item点击接口
    private ListView listView;    //item列表视图
    private View viewTop;   //title视图
    private String topText,bottomText;  //title文字，bottom文字
    private TextView tvTop,tvBottom;    //title文本，bottom文本
    private Adapter<PopBean> adapter;   //适配器

    public ListPopWindow(Context context,OnPopClickListener listener ,List<PopBean> dataList,String bottomText,String topText){
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.topText = topText;
        this.bottomText = bottomText;
        initConfig();
        initViews();
    }

    private void initConfig() {
        rootView = LayoutInflater.from(context).inflate(R.layout.list_popwindow,null);
        setContentView(rootView);

        //设置弹出窗体的高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(android.R.style.Animation_Dialog);
//        //view添加OnTouchListener监听判断获取触屏位置如果在布局外面则销毁弹出框
        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = rootView.findViewById(R.id.ll_bottom).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        update();
    }
    private void initViews(){
        listView = (ListView) rootView.findViewById(R.id.lv_popwindow);
        viewTop = rootView.findViewById(R.id.view_line1);
        tvBottom = (TextView) rootView.findViewById(R.id.tv_popwindow_bottom);
        tvTop = (TextView) rootView.findViewById(R.id.tv_popwindow_first);

        adapter = new Adapter<PopBean>(context,dataList,R.layout.listview_popwindow_item) {
            @Override
            protected void convert(AdapterHelper helper, PopBean item) {
                helper.setText(R.id.tv_title,item.getTitle());
//                helper.setTextColor(R.id.tv_title,item.getIcon_res());
                if (helper.getPosition() != dataList.size()-1) {
                    helper.setVisible(R.id.v_line,View.INVISIBLE);
                    helper.setBackgroundRes(R.id.v_line,R.drawable.selector_bottom_half);
                } else {
                    helper.setVisible(R.id.v_line,View.VISIBLE);
                    helper.setBackgroundRes(R.id.v_line,R.drawable.list_gray_item);
                }
            }
        };
        listView.setAdapter(adapter);

//        // 添加条目过多的时候控制高度
//        if (dataList.size() >= 5) {
//            ViewGroup.LayoutParams params = listView.getLayoutParams();
//            //params.height = display.getHeight() / 2;
//            params.height = DensityUtils.dip2px(context,40)*5;
//            listView.setLayoutParams(params);
//        }
        setVisibility();
        setListener();
    }

    private void setVisibility() {
        if (!TextUtils.isEmpty(topText)){
            tvTop.setVisibility(View.VISIBLE);
            tvTop.setText(topText);
            viewTop.setVisibility(View.VISIBLE);
        } else {
            tvTop.setVisibility(View.GONE);
            viewTop.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(bottomText)){
            tvBottom.setVisibility(View.VISIBLE);
            tvBottom.setText(bottomText);
        } else {
            tvBottom.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listener != null)
                    listener.onPopItemClick(view, i);
            }
        });

        tvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onBottomClick();
            }
        });
    }

    public interface  OnPopClickListener{
        void onPopItemClick(View view, int position);
        void onBottomClick();
    }
}
