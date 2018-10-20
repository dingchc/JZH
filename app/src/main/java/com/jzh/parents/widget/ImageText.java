package com.jzh.parents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzh.parents.R;
import com.jzh.parents.utils.Util;

/**
 * 上图片、下文字
 *
 * @author Ding
 */
public class ImageText extends LinearLayout {

    private Context context;
    public static final int DEFAULT_IMAGE_SIZE = 40;
    public ImageView iconImageView;
    public TextView titleTextView;

    private Paint paint;

    /**
     * 是否显示提醒
     */
    private boolean isShowNotify = false;

    public ImageText(Context context) {
        this(context, null);
    }

    public ImageText(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        initAttrsView(attrs);
    }

    private void initAttrsView(AttributeSet attrs) {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Util.Companion.getColorCompat(R.color.red));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        initBaseView();

        if (attrs == null) {
            return;
        }

        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.ImageText);
        if (a.hasValue(R.styleable.ImageText_it_src)) {
            Drawable drawable = a.getDrawable(R.styleable.ImageText_it_src);
            this.iconImageView.setImageDrawable(drawable);
        }
        if (a.hasValue(R.styleable.ImageText_it_text)) {
            String text = a.getString(R.styleable.ImageText_it_text);
            this.titleTextView.setText(text);
        }

        if (a.hasValue(R.styleable.ImageText_it_image_size)) {
            int imageSize = a.getDimensionPixelSize(R.styleable.ImageText_it_image_size, 46);
            LayoutParams params = (LayoutParams) iconImageView
                    .getLayoutParams();
            params.width = imageSize;
            params.height = imageSize;
            iconImageView.setLayoutParams(params);

            this.titleTextView.setSingleLine(true);
            this.titleTextView.setEllipsize(TextUtils.TruncateAt.END);

        }

        if (a.hasValue(R.styleable.ImageText_it_text_color)) {
            titleTextView.setTextColor(a
                    .getColorStateList(R.styleable.ImageText_it_text_color));
        }

        if (a.hasValue(R.styleable.ImageText_it_text_size)) {
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.ImageText_it_text_size,
                    (int) titleTextView.getTextSize()));
        }

        // 控件宽度
        if (a.hasValue(R.styleable.ImageText_it_view_width)) {

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) titleTextView.getLayoutParams();
            layoutParams.width = a.getDimensionPixelSize(R.styleable.ImageText_it_view_width,
                    0);

            titleTextView.setLayoutParams(layoutParams);
        }

        a.recycle();

    }

    private void initBaseView() {

        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

        this.iconImageView = new ImageView(getContext());
        LayoutParams ivParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        iconImageView.setLayoutParams(ivParams);
        iconImageView.setDuplicateParentStateEnabled(true);
        this.addView(iconImageView);

        this.titleTextView = new TextView(getContext());
        LayoutParams tvParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleTextView.setLayoutParams(tvParams);
        tvParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        tvParams.topMargin = Util.Companion.dp2px(context, 1);
        this.titleTextView.setTextSize(14);
        titleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        this.titleTextView.setTextColor(Color.BLACK);
        this.titleTextView.setHighlightColor(Color.DKGRAY);
        titleTextView.setDuplicateParentStateEnabled(true);
        this.addView(titleTextView);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        // 显示红点
        if (isShowNotify) {
            int right = iconImageView.getRight() + 4;
            int top = iconImageView.getTop() + 4;
            int radius = 10;
            canvas.drawCircle(right, top, radius, paint);
        }
    }

    /**
     * 设置是否显示提醒
     *
     * @param isShowNotify 是否显示提醒
     */
    public void setIsShowNotify(boolean isShowNotify) {
        this.isShowNotify = isShowNotify;
        invalidate();
    }

    public void setImageView(int imgId, int imgSize) {
        if (imgId > 0) {
            this.iconImageView.setImageResource(imgId);
        }

        int size;
        if (imgSize > 0) {
            size = imgSize;
        } else {
            size = 46;
        }

        LayoutParams params = (LayoutParams) iconImageView.getLayoutParams();
        params.weight = Util.Companion.dp2px(context, size);
        params.height = Util.Companion.dp2px(context, size);
        iconImageView.setLayoutParams(params);
    }

    public void setText(CharSequence text) {
        titleTextView.setText(text);
    }

    public TextView getTextView() {
        return titleTextView;
    }

    public ImageView getImageView() {
        return iconImageView;
    }
}
