package com.jsd.library.widget.gif;

/**
 * Created by lenovo on 2016/2/22.
 */
public class Gif {

    public static final int MODE_REPEAT = 0;

    public static final int MODE_UNREPEAT = 1;

    private int drawableId = -1;

    private int mode = MODE_REPEAT;

    private int repeatCount = 0;

    public Gif(int drawableId, int repeatMode, int repeatCount) {
        this.drawableId = drawableId;
        this.repeatCount = repeatCount;
        this.mode = repeatMode;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getMode() {
        return mode;
    }

    public int getRepeatCount() {
        return repeatCount;
    }
}
