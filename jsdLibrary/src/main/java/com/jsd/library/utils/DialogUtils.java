package com.jsd.library.utils;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jsd.library.app.BaseApplication;
import com.jsd.library.widget.dialogs.ArrayDialogFragment;
import com.jsd.library.widget.dialogs.IArrayDialogListener;
import com.jsd.library.widget.dialogs.IDialogListener;
import com.jsd.library.widget.dialogs.INeutralDialogListener;
import com.jsd.library.widget.dialogs.ListDialogFragment;
import com.jsd.library.widget.dialogs.ProgressDialogFragment;
import com.jsd.library.widget.dialogs.SimpleDialogFragment;


/**
 * Created by yanghu on 2015/1/26.
 */
public class DialogUtils {
    private DialogFragment progressDialogFragment;
    private ListDialogFragment listDialogFragment;
    private FragmentManager mManager;
    private Fragment mFragment;
    private int requestCode;
    private String title;
    private static DialogUtils mInstance = null;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        if (mInstance == null) {
            synchronized (DialogUtils.class) {
                if (mInstance == null) {
                    mInstance = new DialogUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     */
    public void showProgressDialog(String message, FragmentManager manager, boolean cancelable) {
        showProgressDialog(message, manager, null, cancelable);
    }

    /**
     * 显示浮层对话框的提示
     *
     * @param message FragmentManager
     * @param manager 要提示的消息
     */
    public void showProgressDialog(String message, FragmentManager manager) {
        showProgressDialog(message, manager, null, true);
    }


    /**
     * 显示浮层对话框的提示
     *
     * @param message FragmentManager
     * @param manager 要提示的消息
     */
    public void showProgressDialog(String message, FragmentManager manager, String tag) {
        showProgressDialog(message, manager, tag, true);
    }


    public void showProgressDialog(String message, FragmentManager manager, String tag, boolean cancelable) {
        try {
            progressDialogFragment = ProgressDialogFragment.createBuilder(BaseApplication.getContext(), manager)
                    .setMessage(message)
                    .setCancelableOnTouchOutside(false)
                    .setCancelable(cancelable)
                    .setTag(tag)
                    .showAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showConfirmDialog(FragmentManager manager, String message) {
        showConfirmDialog(manager, message, null);
    }

    /**
     * 显示"取消"和"确认"两个按钮的对话框
     *
     * @param manager FragmentManager
     * @param message 要提示的消息
     */
    public void showConfirmDialog(FragmentManager manager, String message, IDialogListener listener) {
        try {
            SimpleDialogFragment.SimpleDialogBuilder builder = SimpleDialogFragment.createBuilder(BaseApplication.getContext(), manager);
            if (mFragment != null) {
                builder.setTargetFragment(mFragment, requestCode);
            }
            if (requestCode != 0) {
                builder.setRequestCode(requestCode);
            }
            if (!CheckUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            SimpleDialogFragment dialog = (SimpleDialogFragment) builder.setMessage(message)
                    .setNegativeButtonText("取消")
                    .setPositiveButtonText("确定")
                    .setRequestCode(requestCode)
                    .setCancelableOnTouchOutside(true)
                    .showAllowingStateLoss();
            if (listener != null) {
                dialog.setDialogListener(listener);
            }
            requestCode = 0;
            mFragment = null;
            title = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示只有"确认"的对话框
     *
     * @param manager FragmentManager
     * @param message 要提示的消息
     */
    public void showPromptDialog(FragmentManager manager, String message) {
        showPromptDialog(manager, message, "确定", null);
    }


    /**
     * 显示只有"确认"的对话框
     *
     * @param manager FragmentManager
     * @param message 要提示的消息
     */
    public void showPromptDialog(FragmentManager manager, String message, String buttonText, final View.OnClickListener listener) {
        try {
            SimpleDialogFragment.SimpleDialogBuilder builder = SimpleDialogFragment.createBuilder(BaseApplication.getContext(), manager);
            if (requestCode != 0) {
                builder.setRequestCode(requestCode);
            }
            builder.setMessage(message)
                    .setPositiveButton(buttonText, new View.OnClickListener() {
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onClick(v);
                            }
                        }
                    })
                    .setCancelableOnTouchOutside(true)
                    .showAllowingStateLoss();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showListDialog(FragmentManager manager, int arraysRes) {
        try {
            ListDialogFragment.SimpleListDialogBuilder builder = ListDialogFragment.createBuilder(BaseApplication.getContext(), manager);
            if (mFragment != null) {
                builder.setTargetFragment(mFragment, requestCode);
            }
            builder.hideDefaultButton(true)
                    .setItems(arraysRes)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showArrayDialog(FragmentManager manager, int arraysRes) {
        try {
            ArrayDialogFragment.SimpleListDialogBuilder builder = ArrayDialogFragment.createBuilder(BaseApplication.getContext(), manager);
            if (mFragment != null) {
                builder.setTargetFragment(mFragment, requestCode);
            }
            ArrayDialogFragment dialog = builder.hideDefaultButton(true)
                    .setItems(arraysRes)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showArrayDialog(FragmentManager manager, int arraysRes, IArrayDialogListener listener) {
        try {
            ArrayDialogFragment.SimpleListDialogBuilder builder = ArrayDialogFragment.createBuilder(BaseApplication.getContext(), manager);
            if (mFragment != null) {
                builder.setTargetFragment(mFragment, requestCode);
            }
            ArrayDialogFragment dialog = builder.hideDefaultButton(true)
                    .setItems(arraysRes)
                    .show();
            dialog.setArrayDialogListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        try {
            if (progressDialogFragment != null) {
                progressDialogFragment.dismissAllowingStateLoss();
                progressDialogFragment = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public DialogUtils setTagFragment(Fragment fragment) {
        mFragment = fragment;
        return this;
    }

    public DialogUtils setRequstCode(int requstCode) {
        this.requestCode = requstCode;
        return this;
    }

    public DialogUtils setTitle(String message) {
        this.title = message;
        return this;
    }

    public DialogUtils setFragmentManager(FragmentManager manager) {
        this.mManager = manager;
        return this;
    }

    public void showConfirmDialog(FragmentManager manager, String message, String cancel, String confirm) {
        showConfirmDialog(manager, message, cancel, confirm, null);
    }

    public void showConfirmDialog(FragmentManager manager, String message, String cancel, String confirm, IDialogListener listener) {
        cancel = CheckUtils.isEmpty(cancel) ? "取消" : cancel;
        confirm = CheckUtils.isEmpty(confirm) ? "确认" : confirm;
        try {
            SimpleDialogFragment.SimpleDialogBuilder builder = SimpleDialogFragment.createBuilder(BaseApplication.getContext(), manager);
            if (mFragment != null) {
                builder.setTargetFragment(mFragment, requestCode);
            }
            if (requestCode != 0) {
                builder.setRequestCode(requestCode);
            }
            if (!CheckUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            SimpleDialogFragment dialog = (SimpleDialogFragment) builder.setMessage(message)
                    .setNegativeButtonText(cancel)
                    .setPositiveButtonText(confirm)
                    .setRequestCode(requestCode)
                    .setCancelableOnTouchOutside(true)
                    .showAllowingStateLoss();
            if (listener != null) {
                dialog.setDialogListener(listener);
            }
            requestCode = 0;
            mFragment = null;
            title = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showConfirmDialog(FragmentManager manager, String message, String cancel, String confirm, String neutral, IDialogListener listener) {
        cancel = CheckUtils.isEmpty(cancel) ? "取消" : cancel;
        confirm = CheckUtils.isEmpty(confirm) ? "确认" : confirm;
        try {
            SimpleDialogFragment.SimpleDialogBuilder builder = SimpleDialogFragment.createBuilder(BaseApplication.getContext(), manager);
            if (mFragment != null) {
                builder.setTargetFragment(mFragment, requestCode);
            }
            if (requestCode != 0) {
                builder.setRequestCode(requestCode);
            }
            if (!CheckUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            SimpleDialogFragment dialog = (SimpleDialogFragment) builder.setMessage(message)
                    .setNegativeButtonText(cancel)
                    .setPositiveButtonText(confirm)
                    .setNeutralButtonText(neutral)
                    .setRequestCode(requestCode)
                    .setCancelableOnTouchOutside(true)
                    .showAllowingStateLoss();
            if (listener != null) {
                dialog.setDialogListener(listener);
            }
            if (listener != null && listener instanceof INeutralDialogListener) {
                dialog.setNeutralDialogListener((INeutralDialogListener) listener);
            }
            requestCode = 0;
            mFragment = null;
            title = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * app版本更新内容提示
     *
     * @param manager  FragmentManager
     * @param message  要提示的消息
     * @param listener 确认与取消（退出）点击事件回调
     */
    public void showAppUpdateMessageDialog(FragmentManager manager, String message, IDialogListener listener, SimpleDialogFragment.KeyBackListener keyBackListener, boolean isForceUpdate) {
        String sureStr = "立即更新";
        String cancelStr = "下次再说";
        if (isForceUpdate) {
            cancelStr = "";
        }
        try {
            SimpleDialogFragment.SimpleDialogBuilder builder = SimpleDialogFragment.createBuilder(BaseApplication.getContext(), manager);
            if (mFragment != null) {
                builder.setTargetFragment(mFragment, requestCode);
            }
            if (requestCode != 0) {
                builder.setRequestCode(requestCode);
            }
            if (!CheckUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            SimpleDialogFragment dialog = (SimpleDialogFragment) builder.setMessage(message)
                    .setNegativeButtonText(cancelStr)
                    .setPositiveButtonText(sureStr)
                    .setRequestCode(requestCode)
                    .setCancelableOnTouchOutside(false)
                    .showAllowingStateLoss();
            if (listener != null) {
                dialog.setDialogListener(listener);
            }
            if (keyBackListener != null) {
                dialog.setKeyBackListener(keyBackListener);
            }
            requestCode = 0;
            mFragment = null;
            title = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示只有"确认"的对话框
     *
     * @param manager FragmentManager
     * @param message 要提示的消息
     */
    public void showReLoginDilog(FragmentManager manager, String message, String sureStr, IDialogListener listener) {
        try {
            SimpleDialogFragment.SimpleDialogBuilder builder = SimpleDialogFragment.createBuilder(BaseApplication.getContext(), manager);

            SimpleDialogFragment dialog = (SimpleDialogFragment) builder.setMessage(message)
                    .setPositiveButtonText(sureStr)
                    .setNegativeButtonText("")
                    .setRequestCode(requestCode)
                    .setCancelableOnTouchOutside(false)
                    .showAllowingStateLoss();
            if (listener != null) {
                dialog.setDialogListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
