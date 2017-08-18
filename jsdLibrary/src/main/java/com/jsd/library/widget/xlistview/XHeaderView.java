package com.jsd.library.widget.xlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsd.library.R;
import com.jsd.library.widget.gif.Gif;
import com.jsd.library.widget.gif.MovieView;


/**
 * @author markmjw
 * @date 2013-10-08
 */
public class XHeaderView extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    private static final int ROTATE_ANIM_DURATION = 180;

    private LinearLayout mContainer;

    private View mArrowImageView;

    private ImageView mProgressBar;
    private Animation anim;

    private TextView mHintTextView;

    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private boolean mIsFirst;

    public XHeaderView(Context context) {
        super(context);
        initView(context);
    }

    public XHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // Initial set header widget height 0
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_vw_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mArrowImageView = findViewById(R.id.header_arrow);
        Gif gif = new Gif(R.drawable.loading_list, Gif.MODE_REPEAT, Integer.MAX_VALUE);
        if (mArrowImageView instanceof MovieView){
            ((MovieView)mArrowImageView).setMovie(gif);
            ((MovieView)mArrowImageView).startMovie();
        }

        mHintTextView = (TextView) findViewById(R.id.header_hint_text);
        mProgressBar = (ImageView) findViewById(R.id.header_progressbar);
        anim = AnimationUtils.loadAnimation(context, R.anim.xlistview_loading_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    public void setState(int state) {
        if (state == mState && mIsFirst) {
            mIsFirst = true;
            return;
        }

        if (state == STATE_REFRESHING) {
            // show progress
            if (!(mArrowImageView instanceof MovieView)){
                mArrowImageView.clearAnimation();
                mArrowImageView.setVisibility(View.INVISIBLE);
            }
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setAnimation(anim);
        } else {
            // show arrow image
            if (!(mArrowImageView instanceof MovieView)){
                mArrowImageView.setVisibility(View.VISIBLE);
            }
            mProgressBar.setVisibility(View.INVISIBLE);
            mProgressBar.clearAnimation();
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
                    if (!(mArrowImageView instanceof MovieView)){
                        mArrowImageView.startAnimation(mRotateDownAnim);
                    }
                }

                if (mState == STATE_REFRESHING) {
                    if (!(mArrowImageView instanceof MovieView)){
                        mArrowImageView.clearAnimation();
                    }
                }

                mHintTextView.setText(R.string.header_hint_refresh_normal);
                break;

            case STATE_READY:
                if (mState != STATE_READY) {
                    if (!(mArrowImageView instanceof MovieView)){
                        mArrowImageView.clearAnimation();
                        mArrowImageView.startAnimation(mRotateUpAnim);
                    }
                    mHintTextView.setText(R.string.header_hint_refresh_ready);
                }
                break;

            case STATE_REFRESHING:
                mHintTextView.setText(R.string.header_hint_refresh_loading);
                break;

            default:
                break;
        }

        mState = state;
    }

    /**
     * Set the header widget visible height.
     *
     * @param height
     */
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * Get the header widget visible height.
     *
     * @return
     */
    public int getVisibleHeight() {
        return mContainer.getHeight();
    }

}