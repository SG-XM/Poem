package com.zq.poem.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zq.poem.R;
import com.zq.poem.Util;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by SGXM on 2019/11/22.
 */

/**
 * @Description: My custom View Component
 * @Author: SGXM
 * @Date: 2019/11/19
 */
public class PoemDisplayView extends View {

    private boolean hasDraw = false;
    /**
     * list of verse
     */
    private List<String> verses = new ArrayList<>();
    /**
     * padding                                     padding
     * Left    col                                 Right
     * |   | Spacing |         |         |         |   |
     * +---+---------+---------+---------+---------+---+
     * |   |                                       |   | padding Top
     * +---+---------------------------------------+---+ ---
     * |   | colWidth                              |   |
     * |   |    _                                  |   |
     * |   |   |x|  col   x         x        x     |   |
     * |   |   |x| Height x         x        x     |   | Content Height
     * |   |   |x|        x         x        x     |   |
     * |   |   |x|        x         x        x     |   |
     * |   |   |x|        x         x        x     |   |
     * |   |    -                                  |   |
     * +---+---------------------------------------+---+ ---
     * |   |                                       |   | padding Bottom
     * +---+---------+---------+---------+---------+---+
     * |              ContentWidth             |
     */

    private int contentWidth, contentHeight;
    private int paddingLeft, paddingRight, paddingTop, paddingBottom;
    private int cols;
    private float colSpacing;
    private float colWidth, colHeight;
    private int postIndex;
    private int singleVerseLength;
    private Bitmap srcBitmap, shaderBitmap;
    private Canvas srcCanvas, shaderCanvas;
    private PorterDuffXfermode xformode;
    private static final int BACKGROUND_PADDING = 36;
    private static final int BACKGROUND_PADDING2 = 57;
    /**
     * mPaint to draw text
     */
    private TextPaint mVersePaint = new TextPaint();

    /**
     * Paint to draw shape
     */
    private Paint mShadowPaint = new Paint();

    /**
     * Paint to draw shape
     */
    private Paint mBackgroundPaint = new Paint();

    /**
     * Paint to draw shape
     */
    private Paint mBackgroundPaint2 = new Paint();

    /**
     * constructor
     */
    public PoemDisplayView(Context context) {
        super(context);
        initListener();
    }

    /**
     * constructor
     */
    public PoemDisplayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        initListener();
    }

    /**
     * constructor
     */
    public PoemDisplayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initListener();
    }

    private void initListener() {
        this.setOnClickListener(Util.getListener(this.getContext(), verses, this));
    }

    /**
     * main method to draw my verses
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cols = verses.size();
        if (cols > 0) {
            if (!hasDraw) {
                int max = verses.get(0).length();
                for (int i = 0; i < verses.size(); ++i) {
                    if (verses.get(i).length() > max) max = verses.get(i).length();
                }
                singleVerseLength = max;// verses.get(0).length();
                contentWidth = getWidth() - paddingLeft - paddingRight;
                contentHeight = getHeight() - paddingTop - paddingBottom;
                colSpacing = (contentWidth - cols * mVersePaint.getTextSize()) / (cols - 1);
                colWidth = mVersePaint.getTextSize();
                colHeight = (singleVerseLength + 2) * mVersePaint.getTextSize();


                srcBitmap = Bitmap.createBitmap(contentWidth, (int) colHeight, Bitmap.Config.ARGB_8888);
                srcCanvas = new Canvas(srcBitmap);
                shaderBitmap = Bitmap.createBitmap(contentWidth, (int) colHeight, Bitmap.Config.ARGB_8888);
                shaderCanvas = new Canvas(shaderBitmap);

                //draw Background
                Rect rect = new Rect(paddingLeft - BACKGROUND_PADDING2, (getHeight() - (int) colHeight) / 2 - BACKGROUND_PADDING2, contentWidth + paddingLeft + BACKGROUND_PADDING2, (getHeight() - (int) colHeight) / 2 + (int) colHeight + BACKGROUND_PADDING2);
                canvas.drawRect(rect, mBackgroundPaint2);
                rect = new Rect(paddingLeft - BACKGROUND_PADDING, (getHeight() - (int) colHeight) / 2 - BACKGROUND_PADDING, contentWidth + paddingLeft + BACKGROUND_PADDING, (getHeight() - (int) colHeight) / 2 + (int) colHeight + BACKGROUND_PADDING);
                canvas.drawRect(rect, mBackgroundPaint);


                //draw verse
                for (int i = 0; i < cols; ++i) {
                    srcCanvas.save();
                    srcCanvas.translate(i * (colSpacing + colWidth), 0);
                    StaticLayout staticLayout = new StaticLayout(verses.get(i), mVersePaint, (int) mVersePaint.getTextSize(), Layout.Alignment.ALIGN_CENTER, 1, 0, true);
                    staticLayout.draw(srcCanvas);
                    srcCanvas.restore();
                }
                //draw dst(shader) bitmap
                //draw transparent
                mShadowPaint.setColor(Color.TRANSPARENT);
                RectF rectF = new RectF(0, 0, contentWidth, colHeight);
                shaderCanvas.drawRect(rectF, mShadowPaint);
                //draw black
                mShadowPaint.setColor(Color.BLACK);
                int drawCols = (int) Math.ceil(postIndex / colHeight);
                for (int i = 0; i < drawCols; ++i) {
                    shaderCanvas.save();
                    shaderCanvas.translate(i * (colSpacing + colWidth), 0);
                    rectF = new RectF(0, 0, colWidth, postIndex - i * colHeight);
                    shaderCanvas.drawRect(rectF, mShadowPaint);
                    shaderCanvas.restore();
                }


                //combine
                beforeDraw();
                mShadowPaint.setXfermode(xformode);
                srcCanvas.drawBitmap(shaderBitmap, 0, 0, mShadowPaint);
                mShadowPaint.setXfermode(null);

                //draw final bitmap
                int top;
                if (colHeight >= contentHeight)
                    top = 0;
                else
                    top = (getHeight() - (int) colHeight) / 2;
                canvas.drawBitmap(srcBitmap, paddingLeft, top, null);
                if (postIndex < cols * colHeight) {
                    postIndex += 5;
                    postInvalidateDelayed(4);
                } else {
                    postIndex = 0;
                    hasDraw = true;
                }
            } else {
                singleVerseLength = verses.get(0).length();
                contentWidth = getWidth() - paddingLeft - paddingRight;
                contentHeight = getHeight() - paddingTop - paddingBottom;
                colSpacing = (contentWidth - cols * mVersePaint.getTextSize()) / (cols - 1);
                colWidth = mVersePaint.getTextSize();
                colHeight = (singleVerseLength + 2) * mVersePaint.getTextSize();
                postIndex = cols * (int) colHeight + 10;

                srcBitmap = Bitmap.createBitmap(contentWidth, (int) colHeight, Bitmap.Config.ARGB_8888);
                srcCanvas = new Canvas(srcBitmap);
                shaderBitmap = Bitmap.createBitmap(contentWidth, (int) colHeight, Bitmap.Config.ARGB_8888);
                shaderCanvas = new Canvas(shaderBitmap);

                //draw Background
                Rect rect = new Rect(paddingLeft - BACKGROUND_PADDING2, (getHeight() - (int) colHeight) / 2 - BACKGROUND_PADDING2, contentWidth + paddingLeft + BACKGROUND_PADDING2, (getHeight() - (int) colHeight) / 2 + (int) colHeight + BACKGROUND_PADDING2);
                canvas.drawRect(rect, mBackgroundPaint2);
                rect = new Rect(paddingLeft - BACKGROUND_PADDING, (getHeight() - (int) colHeight) / 2 - BACKGROUND_PADDING, contentWidth + paddingLeft + BACKGROUND_PADDING, (getHeight() - (int) colHeight) / 2 + (int) colHeight + BACKGROUND_PADDING);
                canvas.drawRect(rect, mBackgroundPaint);


                //draw verse
                for (int i = 0; i < cols; ++i) {
                    srcCanvas.save();
                    srcCanvas.translate(i * (colSpacing + colWidth), 0);
                    StaticLayout staticLayout = new StaticLayout(verses.get(i), mVersePaint, (int) mVersePaint.getTextSize(), Layout.Alignment.ALIGN_CENTER, 1, 0, true);
                    staticLayout.draw(srcCanvas);
                    srcCanvas.restore();
                }
                //draw dst(shader) bitmap
                //draw transparent
                mShadowPaint.setColor(Color.TRANSPARENT);
                RectF rectF = new RectF(0, 0, contentWidth, colHeight);
                shaderCanvas.drawRect(rectF, mShadowPaint);
                //draw black
                mShadowPaint.setColor(Color.BLACK);
                int drawCols = (int) Math.ceil(postIndex / colHeight);
                for (int i = 0; i < drawCols; ++i) {
                    shaderCanvas.save();
                    shaderCanvas.translate(i * (colSpacing + colWidth), 0);
                    rectF = new RectF(0, 0, colWidth, postIndex - i * colHeight);
                    shaderCanvas.drawRect(rectF, mShadowPaint);
                    shaderCanvas.restore();
                }


                //combine
                beforeDraw();
                mShadowPaint.setXfermode(xformode);
                srcCanvas.drawBitmap(shaderBitmap, 0, 0, mShadowPaint);
                mShadowPaint.setXfermode(null);

                //draw final bitmap
                int top;
                if (colHeight >= contentHeight)
                    top = 0;
                else
                    top = (getHeight() - (int) colHeight) / 2;
                canvas.drawBitmap(srcBitmap, paddingLeft, top, null);
                if (postIndex < cols * colHeight) {
                    postIndex += 5;
                    postInvalidateDelayed(4);
                } else {
                    //postIndex = 0;
                    hasDraw = true;
                }
            }
        }

    }

    public List<String> getVerses() {
        return verses;
    }

    /**
     * set method
     */
    public void setVerses(List<String> verses) {
        if (this.verses != verses) {
            this.verses.clear();
            this.verses.addAll(verses);
            hasDraw = false;
            initPaint();
            invalidate();
        }
    }

    /**
     * prepare before draw
     */
    private void beforeDraw() {
        xformode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mShadowPaint.setColor(Color.BLACK);
    }

    /**
     * initialize my paint
     */
    private void initPaint() {
        mVersePaint.setColor(getResources().getColor(R.color.colorLine));
        mVersePaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "HYshangweishoushuW.ttf"));
        mVersePaint.setTextSize(80f);
        mShadowPaint.setColor(Color.BLACK);
        mBackgroundPaint.setColor(getResources().getColor(R.color.colorBack));
        mBackgroundPaint2.setColor(getResources().getColor(R.color.colorBack2));
    }

    /**
     * initialize this view
     */
    private void initAttr(@Nullable AttributeSet attrs) {
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
    }

}
