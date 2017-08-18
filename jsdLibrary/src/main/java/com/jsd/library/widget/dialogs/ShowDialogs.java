package com.jsd.library.widget.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsd.library.R;
import com.jsd.library.utils.ToastUtils;
import com.jsd.library.widget.XEditText;


/**
 * Created by Administrator on 2016/9/5 0005.
 * 三思而后行
 */
public class ShowDialogs {
    public static ShowDialogs showDialogs;

    public static ShowDialogs getShowDialogs() {
        if (null == showDialogs) {
            showDialogs = new ShowDialogs();
        }
        return showDialogs;
    }

    public ShowDialogs() {
    }

    private Dialog mDialog;

    public void dismissDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void hide() {
        if (null != mDialog) {
            mDialog.hide();
        }
    }

    public void show(final Context context) {
        if (null == mDialog) {
            initDialog(context, R.layout.dialog);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void showReserveCancel(final Context context, String title, String hint, final String toastInfo, final ServesCancel servesCancel) {
        if (null == mDialog) {
            initDialog(context, R.layout.dialog_reserve_cancel);
            TextView tvTitle = (TextView) mDialog.findViewById(R.id.tv1);
            tvTitle.setText(title);

            final XEditText editText = (XEditText) mDialog.findViewById(R.id.xet_content);
            editText.setHint(hint);
            if (title.contains("密码")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            }


            mDialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ("".equals(editText.getText().toString().trim())) {
                        ToastUtils.show(toastInfo);
                    } else {
                        dismissDialog();
                        servesCancel.ok(editText.getText().toString());
                    }
                }
            });

            mDialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    servesCancel.cancel();
                    dismissDialog();
                }
            });
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 统一提示
     */
    public void showInfo(final Context context, final String title, String info, final DialogTwoClickListener listener) {
        if (null == mDialog) {
            initDialog(context, R.layout.dialog_info);
            mDialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.leftBtn();
                    dismissDialog();
                }
            });
            mDialog.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.rightBtn();
                    dismissDialog();
                }
            });
            TextView tTitle = (TextView) mDialog.findViewById(R.id.tv_title);
            tTitle.setText(title);
            TextView tInfo = (TextView) mDialog.findViewById(R.id.tv_info);
            tInfo.setText(info);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void showService(final Context context, final String title, String info, final DialogTwoClickListener listener) {
        if (null == mDialog) {
            initDialog(context, R.layout.dialog_service);
            mDialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.leftBtn();
                    dismissDialog();
                }
            });
            mDialog.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.rightBtn();
                    dismissDialog();
                }
            });
            TextView tTitle = (TextView) mDialog.findViewById(R.id.tv_title);
            tTitle.setText(title);
            TextView tInfo = (TextView) mDialog.findViewById(R.id.tv_info);
            tInfo.setText(info);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    private void initDialog(Context context, int dialog) {
        if (null == mDialog) {
            mDialog = new Dialog(context, R.style.MyDialog);
            mDialog.setContentView(dialog);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        }
    }

    private void initDialogs(Context context, int dialog) {
        if (null == mDialog) {
            mDialog = new Dialog(context, R.style.MyDialog);
            mDialog.setContentView(dialog);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(true);

        }
    }

    public void setOnCancelListener(final DialogOneClickListener listener) {
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                listener.Btn();
            }
        });
    }

    /**
     * 图片
     */
    public void showQRCode(final Context context, Bitmap qrBitmap, final DialogOneClickListener listener) {
        initDialogs(context, R.layout.dialog_qr_code);
        ImageView qrCode = (ImageView) mDialog.findViewById(R.id.iv_qr_code);
        qrCode.setImageBitmap(qrBitmap);
        mDialog.show();
        setOnCancelListener(listener);
    }
}
