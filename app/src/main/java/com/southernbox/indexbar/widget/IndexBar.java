package com.southernbox.indexbar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.southernbox.indexbar.Util.DisplayUtil;

/**
 * Created by nanquan.lin on 2016/10/25 0025.
 */

public class IndexBar extends View {

    /**
     * 索引字母颜色
     */
    private static final int LETTER_COLOR = 0xff2e8be6;

    /**
     * 索引字母
     */
    public String[] letters = {};

    private Paint mPaint;

    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;
    /**
     * 单元格的高度
     */
    private float mCellHeight;
    /**
     * 顶部间距
     */
    private float mMarginTop;

    public IndexBar(Context context) {
        super(context);
        init();
    }

    public IndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(LETTER_COLOR);
        mPaint.setTextSize(DisplayUtil.dp2px(getContext(), 12));
        mPaint.setAntiAlias(true); // 去掉锯齿，让字体边缘变得平滑
    }

    public void setLetters(String[] letters) {
        this.letters = letters;
        mMarginTop = (mHeight - mCellHeight * letters.length) / 2;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //字母的坐标点：(x,y)
        if (letters.length > 0) {
            for (int i = 0; i < letters.length; i++) {
                String letter = letters[i];
                float x = (mWidth / 2 - getTextWidth(letter) / 2);
                float y = (mCellHeight / 2 + getTextHeight(letter) / 2 + mCellHeight * i) + mMarginTop;
                canvas.drawText(letter, x, y, mPaint);
            }
        }
    }

    /**
     * 获取字符的宽度
     *
     * @param text
     * @return
     */
    public float getTextWidth(String text) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    /**
     * 获取字符的高度
     *
     * @param text
     * @return
     */
    public float getTextHeight(String text) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mCellHeight = (mHeight * 1f / 27);    //26个字母加上“#”
        if (letters.length > 0) {
            mMarginTop = (mHeight - mCellHeight * letters.length) / 2;
        }
    }

    /**
     * 当前按下的字母
     */
    private int index = -1;

    // 处理Touch事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 按下字母的索引
        int letterIndex;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    // 按下
                letterIndex = (int) ((event.getY() - mMarginTop) / mCellHeight);
                // 越界判断
                if (letterIndex >= 0 && letterIndex < letters.length) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(letters[letterIndex]);

                    //如果与上一次选择的字母相同，则不打印
                    if (letterIndex != index) {
                        index = letterIndex;

                        if (mOnLetterChangedListener != null) {
                            mOnLetterChangedListener.onLetterChanged(letters[letterIndex]);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:    // 滑动
                letterIndex = (int) ((event.getY() - mMarginTop) / mCellHeight);
                // 越界判断
                if (letterIndex >= 0 && letterIndex < letters.length) {

                    // 显示首字母
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(letters[letterIndex]);

                    if (letterIndex != index) {
                        index = letterIndex;

                        if (mOnLetterChangedListener != null) {
                            mOnLetterChangedListener.onLetterChanged(letters[letterIndex]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:        // 提起
                textView.setVisibility(View.GONE);
                break;
        }

        // 返回true，让控件持续接收事件
        return true;
    }

    public interface OnLetterChangedListener {
        /**
         * 按下字母改变了
         *
         * @param index 按下的字母
         */
        void onLetterChanged(String index);
    }

    private OnLetterChangedListener mOnLetterChangedListener;

    private TextView textView;

    public void setOnLetterChangedListener(
            OnLetterChangedListener onLetterChangedListener) {
        this.mOnLetterChangedListener = onLetterChangedListener;
    }

    /**
     * 设置显示按下首字母的TextView
     */
    public void setInitialLetterTextView(TextView textView) {
        this.textView = textView;
    }
}
