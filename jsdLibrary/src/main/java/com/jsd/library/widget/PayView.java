package com.jsd.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
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
public class PayView extends LinearLayout implements View.OnClickListener {
    private static final String ALI_PAY = "01";
    private static final String WEIXIN_PAY = "02";
    private static final String CONSULATION_PAY = "07";

    private OnItemClickListener mOnItemClickListener;
    private TextView tv_money;
    private String balance = "0.00";
    private int curIndex = 0, lastIndex = -1;
    private boolean beyond = false;//余额不足

    private List<TextView> txtViewList = new ArrayList<>();
    private List<LinearLayout> linearLayoutList = new ArrayList<>();

    private boolean isShowAliPay;
    private boolean isShowWeiXin;
    private boolean isShowBalance;
    private Context mContext;

    public PayView(Context context) {
        super(context);
        initializeView(context,null,0);
    }

    public PayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context,attrs,0);
    }

    public PayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context,attrs,defStyleAttr);
    }

    private void initializeView(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PayView, defStyleAttr, 0);
        isShowAliPay = typedArray.getBoolean(R.styleable.PayView_show_alipay, true);
        isShowWeiXin = typedArray.getBoolean(R.styleable.PayView_show_weixin, true);
        isShowBalance = typedArray.getBoolean(R.styleable.PayView_show_balance, true);
        typedArray.recycle();
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.jsd_view_pay, this);
        txtViewList.clear();
        linearLayoutList.clear();
        if(isShowAliPay){
            txtViewList.add((TextView) findViewById(R.id.cb_alipay));
            LinearLayout ll_alipay = (LinearLayout) findViewById(R.id.ll_alipay);
            ll_alipay.setOnClickListener(this);
            linearLayoutList.add(ll_alipay);
        }else{
            findViewById(R.id.ll_alipay).setVisibility(View.GONE);
        }
        if(isShowWeiXin){
            txtViewList.add((TextView) findViewById(R.id.cb_wechat));
            LinearLayout ll_wechat = (LinearLayout) findViewById(R.id.ll_wechat);
            ll_wechat.setOnClickListener(this);
            linearLayoutList.add(ll_wechat);
        }else{
            findViewById(R.id.ll_wechat).setVisibility(View.GONE);
        }
        if(isShowBalance){
            txtViewList.add((TextView) findViewById(R.id.cb_balance));
            tv_money = (TextView) findViewById(R.id.tv_money);
            LinearLayout ll_balance = (LinearLayout) findViewById(R.id.ll_balance);
            ll_balance.setOnClickListener(this);
            linearLayoutList.add(ll_balance);
        }else{
            findViewById(R.id.ll_balance).setVisibility(View.GONE);
        }
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setConsulationBalance(String money) {
        if(tv_money!= null){
            tv_money.setText(money);
            balance = money;
        }
    }

    public void setDefault(int index) {

        if (index >= linearLayoutList.size() || index < 0)
            return;
        linearLayoutList.get(index).performClick();
    }

    public void setAllPay(String money) {
        if(tv_money != null) {
            if (!TextUtils.isEmpty(money)) {
                if (Double.parseDouble(money) > Double.parseDouble(balance)) {
                    tv_money.setTextColor(getResources().getColor(R.color.jsd_divide));
                    beyond = true;
                } else {
                    beyond = false;
                }
            }
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
                    mOnItemClickListener.onItemClick(WEIXIN_PAY, "03");
                }
            }


        } else if (id == R.id.ll_balance) {
            //余额
            curIndex = 2;
            if (lastIndex != curIndex) {
                //余额不足时不能选择有提示
                if(!beyond)
                    setChecked();
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(CONSULATION_PAY, "");
                }
            }

        }
    }

    private void setChecked() {
        if (lastIndex != curIndex) {
            txtViewList.get(curIndex).setSelected(true);
            if (lastIndex != -1)
                txtViewList.get(lastIndex).setSelected(false);
            lastIndex = curIndex;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String payMethod, String tradeType);
    }
}
