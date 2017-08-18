package com.jsd.library.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsd.library.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 日期：2017/3/25 16:23
 * 作者： sf
 * 功能描述：支付
 */
public class PayView2 extends LinearLayout implements View.OnClickListener {
    private static final String ALI_PAY = "01";
    private static final String WEIXIN_PAY = "02";
    private static final String CONSULATION_PAY = "07";
    private OnItemClickListener mOnItemClickListener;
    private TextView tv_money;
    private String balance = "0.00";
    private LinearLayout ll_top;

    private int curIndex = 0, lastIndex = -1;

    private List<TextView> txtViewList = new ArrayList<>();
    private List<LinearLayout> linearLayoutList = new ArrayList<>();

    public PayView2(Context context) {
        super(context);
        initializeView(context);
    }

    public PayView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public PayView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.jsd_view_pay2, this);
        txtViewList.clear();
        linearLayoutList.clear();
        txtViewList.add((TextView) findViewById(R.id.cb_alipay));
        txtViewList.add((TextView) findViewById(R.id.cb_wechat));
        txtViewList.add((TextView) findViewById(R.id.cb_balance));
        ll_top = (LinearLayout) findViewById(R.id.ll_top);
        tv_money = (TextView) findViewById(R.id.tv_money);
        LinearLayout ll_alipay = (LinearLayout) findViewById(R.id.ll_alipay);
        LinearLayout ll_wechat = (LinearLayout) findViewById(R.id.ll_wechat);
        LinearLayout ll_balance = (LinearLayout) findViewById(R.id.ll_balance);
        ll_alipay.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_balance.setOnClickListener(this);
        linearLayoutList.add(ll_alipay);
        linearLayoutList.add(ll_wechat);
        linearLayoutList.add(ll_balance);
    }

    public void setOnItemClickListener(PayView2.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setConsulationBalance(String money) {
        tv_money.setText(money);
        balance = money;
    }

    public void setDefault(int index) {

        if (index >= linearLayoutList.size() || index < 0)
            return;
        linearLayoutList.get(index).performClick();
    }

    public void setAllPay(String money) {
        if (!TextUtils.isEmpty(money)) {
            if (Double.parseDouble(money) > Double.parseDouble(balance)) {
                txtViewList.get(2).setEnabled(false);
            }
            tv_money.setTextColor(getResources().getColor(R.color.jsd_divide));
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_alipay) {
            //支付宝
            curIndex = 0;
            if (lastIndex != curIndex) {
                setChecked();
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(ALI_PAY, "");
                }
            }

        } else if (id == R.id.ll_wechat) {
            //微信
            curIndex = 1;
            if (lastIndex != curIndex) {
                setChecked();
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(WEIXIN_PAY, "2");
                }
            }


        } else if (id == R.id.ll_balance) {
            //余额
            curIndex = 2;
            if (lastIndex != curIndex) {
                setChecked();
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(CONSULATION_PAY, "");
                }
            }

        }
    }

    private void setChecked() {
        txtViewList.get(curIndex).setSelected(true);
        if (lastIndex != -1)
            txtViewList.get(lastIndex).setSelected(false);
        lastIndex = curIndex;

    }

    public interface OnItemClickListener {
        void onItemClick(String payMethod, String tradeType);
    }
}
