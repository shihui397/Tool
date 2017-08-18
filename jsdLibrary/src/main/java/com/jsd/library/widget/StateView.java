package com.jsd.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsd.library.R;


/**
 * 日期：2017/3/25 16:23
 * 作者： sf
 * 功能描述：请求网络数据时ui显示的三种状态 分别为加载中，加载失败，无数据
 */
public class StateView extends FrameLayout implements View.OnClickListener {
    private View empty;
    private View error;
    private View loading;

    private State state;
    private OnRetryListener errorListener;
    private EmptyListenter emptyListenter;
    private ImageView ivEmpty;
    private TextView tvEmptyDesc;

    /**
     * 点击当前view时，如果为error时
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (errorListener != null && state == State.error) {
            errorListener.onRetry();
        }
    }

    /**
     * 数据加载失败的回调接口
     */
    public interface OnRetryListener {
        void onRetry();
    }

    public void setOnRetryListener(OnRetryListener listener) {
        this.errorListener = listener;
    }

    /**
     * 枚举各种状态
     */
    public enum State {
        ing, error, done, empty
    }

    public StateView(Context context) {
        super(context);
        initializeView(context);
    }

    public StateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.jsd_view_state, this);
        ivEmpty = (ImageView) findViewById(R.id.iv_empty);
        tvEmptyDesc = (TextView) findViewById(R.id.tv_empty_desc);
        empty = findViewById(R.id.empty);
        loading = findViewById(R.id.loading);
        error = findViewById(R.id.error);
        setOnClickListener(this);
        notifyDataChanged(State.ing);
    }

    public void notifyDataChanged(State state) {
        this.state = state;
        switch (state) {
            case ing:
                setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
                break;
            case empty:
                setVisibility(View.VISIBLE);
                if (emptyListenter != null) {
                    ivEmpty.setImageResource(emptyListenter.setImageViewEmpty());
                    tvEmptyDesc.setText(getResources().getString(emptyListenter.setTextViewEmptyDesc()));
                }
                loading.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                break;
            case error:
                setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                empty.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                break;
            case done:
                setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 数据为空时的监听
     */
    public interface EmptyListenter {
        int setTextViewEmptyDesc();
        int setImageViewEmpty();
    }

    //自定义一个setOnClickListenter方法
    public void setOnEmptyListenter(EmptyListenter emptyListenter){
        this.emptyListenter = emptyListenter;   //调用的时候通过一个匿名内部类映射进来
    }

}
