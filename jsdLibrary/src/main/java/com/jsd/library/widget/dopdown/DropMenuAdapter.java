package com.jsd.library.widget.dopdown;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.jsd.library.R;
import com.jsd.library.utils.CheckUtils;
import com.jsd.library.utils.DensityUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter  implements MenuAdapter {
    public static final int SINGLE_TYPE = 1;
    public static final int DOUBLE_TYPE = 2;
    public static final int GIRD_TYPE = 3;

    private Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private List<TitleBean> titles;
    private List<List> dataList;

    public DropMenuAdapter(Context context, List<TitleBean> titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
        dataList = new ArrayList<>();

        for(TitleBean bean :titles){
            if(bean.type == DOUBLE_TYPE){
                List<FilterDoule> doubleList = new ArrayList<>();
                dataList.add(doubleList);
            }else if(bean.type == SINGLE_TYPE){
                List<FilterBean> singleList = new ArrayList<>();
                dataList.add(singleList);
            }
        }
    }

    @Override
    public int getMenuCount() {
        return titles.size();
    }

    @Override
    public String getMenuTitle(int position) {
        return titles.get(position).title;
    }

    @Override
    public int getBottomMargin(int position) {
        return DensityUtils.dip2px(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (titles.get(position).type) {
            case SINGLE_TYPE:
                view = createSingleListView(position);
                break;
            case DOUBLE_TYPE:
                view = createDoubleListView(position);
                break;
        }

        return view;
    }
    /**
     * 单列
     * @return
     */
    private View createSingleListView(final int index) {
        SingleListView<FilterBean> singleListView = new SingleListView<FilterBean>(mContext)
                .adapter(new SimpleTextAdapter<FilterBean>(dataList.get(index), mContext) {
                    @Override
                    public String provideText(FilterBean filterBean) {
                        return filterBean.desc;
                    }
                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = DensityUtils.dip2px(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<FilterBean>() {
                    @Override
                    public void onItemClick(FilterBean item) {
                        onFilterDone(index,item.desc,item.value);
                    }
                });
        return singleListView;
    }
    public void setList(int postion,List dates) {
        dataList.get(postion).clear();
        dataList.get(postion).addAll(dates);
    }

    /**
     * 两列
     * @return
     */
    private View createDoubleListView(final int index) {
        DoubleListView<FilterDoule, FilterBean> comTypeDoubleListView = new DoubleListView<FilterDoule, FilterBean>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterDoule>(null, mContext) {
                    @Override
                    public String provideText(FilterDoule filterDoule) {
                        return filterDoule.filterBean.desc;
                    }
                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(DensityUtils.dip2px(mContext, 44), DensityUtils.dip2px(mContext, 15), 0, DensityUtils.dip2px(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<FilterBean>(null, mContext) {
                    @Override
                    public String provideText(FilterBean filterBean) {
                        return filterBean.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(DensityUtils.dip2px(mContext, 30), DensityUtils.dip2px(mContext, 15), 0, DensityUtils.dip2px(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterDoule, FilterBean>() {
                    @Override
                    public List<FilterBean> provideRightList(FilterDoule item, int position) {
                        List<FilterBean> child = item.filterBeanChilds;
                        if (CheckUtils.isEmpty(child)) {
                            //如果没有下级则请求事件
                            onFilterDone(index,item.filterBean.desc,item.filterBean.value);
                        }
                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterDoule, FilterBean>() {
                    @Override
                    public void onRightItemClick(FilterDoule item, FilterBean filterBean) {
                        onFilterDone(index,filterBean.desc,filterBean.value);
                    }
                });
        //初始化选中.
        comTypeDoubleListView.setLeftList(dataList.get(index), 0);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }

    private void onFilterDone(int position, String positionTitle, String urlValue) {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(position, positionTitle,urlValue);
        }
    }
}
