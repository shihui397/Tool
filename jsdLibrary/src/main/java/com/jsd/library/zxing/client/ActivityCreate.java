package com.jsd.library.zxing.client;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.jsd.library.R;
import com.jsd.library.zxing.WriterException;

/**
 * Created by tangshuai on 16/7/20.
 */
public class ActivityCreate extends Activity {

    EditText etContent;
    ImageView ivCode;
    CheckBox cbAddLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initView();
    }

    private void initView() {
        etContent = (EditText) findViewById(R.id.et_content);
        ivCode = (ImageView) findViewById(R.id.iv_code);
        cbAddLogo = (CheckBox) findViewById(R.id.cb_addLogo);
    }

    public void onCreateQrcode(View view) {
        String content = etContent.getText().toString();
        Bitmap bitmap = null;
        if (cbAddLogo.isChecked()) {
            bitmap = BitmapUtil.createQRImage(content, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.alipay));
        } else {
            try {
                bitmap = BitmapUtil.createQRCode(content, 400);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        ivCode.setImageBitmap(bitmap);
    }

}
