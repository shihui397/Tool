package com.jsd.library.widget.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MovieView extends ImageView {

    public static boolean avalible = true;

    private Movie mMovie;
    private Gif gif;
    private int repeatCount = 0;
    private long mMovieStart;
    private boolean mMoviePlay = false;
    private boolean defaultEnd = false;
    private int duration = 0;

    public MovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieView setMovie(Gif gif) {
        if (gif == null) {
            throw new NullPointerException("gif cannot be null");
        }
        if (this.gif != gif) {
            this.gif = gif;
            mMovie = Movie.decodeStream(getContext().getResources().openRawResource(
                    gif.getDrawableId()));
            if (mMovie != null) {
                duration = mMovie.duration();
            } else {
                duration = 0;
            }
            //使用软解码.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                setLayerType(LAYER_TYPE_SOFTWARE, null);
            }
            stopMovie();
            forceLayout();
            requestLayout();
        }
        invalidate();
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMovie != null) {
            float mScale = 1f;
            int movieWidth = mMovie.width();
            int movieHeight = mMovie.height();
        /*
         * Calculate horizontal scaling
         */

            float scaleH = 1f;
            int measureModeWidth = MeasureSpec.getMode(widthMeasureSpec);
            if (measureModeWidth != MeasureSpec.UNSPECIFIED) {
                int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
                if (movieWidth > maximumWidth && maximumWidth > 0)
                    scaleH = (float) movieWidth / (float) maximumWidth;
            }
        /*
         * calculate vertical scaling
         */
            float scaleW = 1f;
            int measureModeHeight = MeasureSpec.getMode(heightMeasureSpec);
            if (measureModeHeight != MeasureSpec.UNSPECIFIED) {
                int maximumHeight = MeasureSpec.getSize(heightMeasureSpec);
                if (movieHeight > maximumHeight && maximumHeight > 0)
                    scaleW = (float) movieHeight / (float) maximumHeight;
            }

        /*
         * calculate overall scale
         */
            mScale = 1f / Math.max(scaleH, scaleW);
            int mMeasuredMovieWidth = (int) (movieWidth * mScale);
            int mMeasuredMovieHeight = (int) (movieHeight * mScale);

            setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);
//            setMeasuredDimension(movieWidth,movieHeight);
        } else {
        /*
         * No movie set, just set minimum available size.
    */
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(null);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(null);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(-1);
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(-1);
    }

    public void showDefaultFrameForEnd() {
        this.defaultEnd = true;
    }

    public void showDefaultFrameForStart() {
        this.defaultEnd = false;
    }

    public void startMovie() {
        if (mMoviePlay) {
            return;
        }
        mMoviePlay = true;
        initMovieStartTime();
        invalidate();
    }

    public void stopMovie() {
        if (!mMoviePlay) {
            return;
        }
        reset();
        invalidate();
    }

    private void reset() {
        mMoviePlay = false;
        mMovieStart = 0;
    }

    protected void onDraw(Canvas canvas) {
        if (mMoviePlay && avalible) {
            drawMovieOnPlay(canvas);
        } else {
            drawMovieDefaultFrame(canvas);
        }
    }

    private void drawMovieDefaultFrame(Canvas canvas) {
        if (mMovie == null) {
            return;
        }
        drawMovieAtTime(canvas, defaultEnd ? duration : 0);
    }

    private void drawMovieAtTime(Canvas canvas, int time) {
        mMovie.setTime(time);
        mMovie.draw(canvas, getWidth() / 2 - mMovie.width() / 2,
                getHeight() / 2 - mMovie.height() / 2);
    }

    private void drawMovieOnPlay(Canvas canvas) {
        if (mMovie == null) {
            return;
        }
        int currentMovieTime = getCurrentMovieTime();
        drawMovieAtTime(canvas, currentMovieTime);
        invalidate();
    }

    private int getCurrentMovieTime() {
        int dur = getMovieDuration();
        long now = android.os.SystemClock.uptimeMillis();
        int relTime = (int) (now - mMovieStart);
        if (gif.getMode() == Gif.MODE_REPEAT && gif.getRepeatCount() >= repeatCount) {
            repeatCount = relTime / dur;
            relTime = relTime % dur;
        } else if (relTime > dur) {
            relTime = defaultEnd ? dur : 0;
        }
        return relTime;
    }

    public int getMovieDuration() {
        int dur = mMovie.duration();
        return dur == 0 ? 1000 : dur;
    }

    private void initMovieStartTime() {
        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) { // first time
            mMovieStart = now;
        }
    }

}
