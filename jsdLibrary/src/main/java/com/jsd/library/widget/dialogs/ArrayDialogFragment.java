package com.jsd.library.widget.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.jsd.library.R;

/**
 * Created by wyf on 2015/10/14.
 */
public class ArrayDialogFragment extends BaseDialogFragment {

    protected final static String ARG_MESSAGE = "message";
    protected final static String ARG_TITLE = "title";
    protected final static String ARG_POSITIVE_BUTTON = "positive_button";
    protected final static String ARG_NEGATIVE_BUTTON = "negative_button";
    protected final static String ARG_NEUTRAL_BUTTON = "neutral_button";
    private static String ARG_ITEMS = "items";
    private static String ARG_ITEMSARRAYRESID = "itemsArrayResID";
    private IArrayDialogListener arrayDialogListener;

    public static SimpleListDialogBuilder createBuilder(Context context,
                                                        FragmentManager fragmentManager) {
        return new SimpleListDialogBuilder(context, fragmentManager);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() == null) {
            throw new IllegalArgumentException(
                    "use SimpleListDialogBuilder to construct this dialog");
        }
    }

    public static class SimpleListDialogBuilder extends BaseDialogBuilder<SimpleListDialogBuilder> {

        private String title;

        private String[] items;
        private int itemsArrayResID;

        private String cancelButtonText;

        private boolean mShowDefaultButton = true;

        public SimpleListDialogBuilder(Context context, FragmentManager fragmentManager) {
            super(context, fragmentManager, ArrayDialogFragment.class);
        }

        @Override
        protected SimpleListDialogBuilder self() {
            return this;
        }

        private Resources getResources() {
            return mContext.getResources();
        }

        public SimpleListDialogBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public SimpleListDialogBuilder setTitle(int titleResID) {
            this.title = getResources().getString(titleResID);
            return this;
        }

        public SimpleListDialogBuilder setItems(int itemsArrayResID) {
            this.itemsArrayResID = itemsArrayResID;
            this.items = getResources().getStringArray(itemsArrayResID);
            return this;
        }

        public SimpleListDialogBuilder setCancelButtonText(String text) {
            this.cancelButtonText = text;
            return this;
        }

        public SimpleListDialogBuilder setCancelButtonText(int cancelBttTextResID) {
            this.cancelButtonText = getResources().getString(cancelBttTextResID);
            return this;
        }

        @Override
        public ArrayDialogFragment show() {
            return (ArrayDialogFragment) super.show();
        }

        /**
         * When there is neither positive nor negative button, default "close" button is created if
         * it was enabled.<br/>
         * Default is true.
         */
        public SimpleListDialogBuilder hideDefaultButton(boolean hide) {
            mShowDefaultButton = !hide;
            return this;
        }

        @Override
        protected Bundle prepareArguments() {
            if (mShowDefaultButton && cancelButtonText == null) {
                cancelButtonText = mContext.getString(R.string.dialog_close);
            }

            Bundle args = new Bundle();
            args.putString(ARG_TITLE, title);
            args.putString(ARG_POSITIVE_BUTTON, cancelButtonText);
            args.putStringArray(ARG_ITEMS, items);
            args.putInt(ARG_ITEMSARRAYRESID, itemsArrayResID);
            return args;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        IArrayDialogListener onListItemSelectedListener = getDialogListener();
        if (onListItemSelectedListener != null) {
            onListItemSelectedListener.onCancelled();
        }
    }

    @Override
    protected Builder build(Builder builder) {
        final String title = getTitle();
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }

        if (!TextUtils.isEmpty(getPositiveButtonText())) {
            builder.setPositiveButton(getPositiveButtonText(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IArrayDialogListener onListItemSelectedListener = getDialogListener();
                    if (onListItemSelectedListener != null) {
                        onListItemSelectedListener.onCancelled();
                    }
                    dismiss();
                }
            });
        }

        final String[] items = getItems();
        if (items != null && items.length > 0) {
            ListAdapter adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.dialog_list_item,
                    R.id.list_item_text,
                    items);

            builder.setItems(adapter, 0, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    IArrayDialogListener onListItemSelectedListener = getDialogListener();
                    if (onListItemSelectedListener != null) {
                        onListItemSelectedListener
                                .onListItemSelected(getItemsArrayResID(), getItems()[position], position);
                        dismiss();
                    }
                }
            });
        }

        return builder;
    }

    private IArrayDialogListener getDialogListener() {
        final Fragment targetFragment = getTargetFragment();
        if (arrayDialogListener != null) {
            return arrayDialogListener;
        } else if (targetFragment != null) {
            if (targetFragment instanceof IArrayDialogListener) {
                return (IArrayDialogListener) targetFragment;
            }
        } else {
            if (getActivity() instanceof IArrayDialogListener) {
                return (IArrayDialogListener) getActivity();
            }
        }
        return null;
    }


    public void setArrayDialogListener(IArrayDialogListener arrayDialogListener) {
        this.arrayDialogListener = arrayDialogListener;
    }

    private String getTitle() {
        return getArguments().getString(ARG_TITLE);
    }

    private String[] getItems() {
        return getArguments().getStringArray(ARG_ITEMS);
    }

    private String getPositiveButtonText() {
        return getArguments().getString(ARG_POSITIVE_BUTTON);
    }

    private int getItemsArrayResID() {
        return getArguments().getInt(ARG_ITEMSARRAYRESID);
    }
}

