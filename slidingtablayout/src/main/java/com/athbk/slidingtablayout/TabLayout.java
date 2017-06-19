package com.athbk.slidingtablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by athbk on 3/22/17.
 */

public class TabLayout extends LinearLayout implements ViewPager.OnPageChangeListener{

    private final int DEFAULT_COLOR_UNDER_LINE = Color.parseColor("#000000");
    private final boolean DEFAULT_VISIBLE_UNDER_LINE = true;
    private final boolean DEFAULT_AUTO_ALIGN = true;
    private final float DEFAULT_PADDING = 16f;
    private final float DEFAULT_SIZE = 14f;
    private final int DEFAULT_PADDING_DRAWBLE = 10;

    private float textSize;
    private int textColorNormal;
    private int colorUnderLiner;
    private boolean isVisibleUnderLiner;
    private boolean isAutoAlign;
    private int resBackground;
    private float paddingTop;
    private float paddingLeft;
    private float paddingRight;
    private float paddingBottom;

    private ViewPager viewPager;
    private SlidingTabAdapter adapter;
    private Paint mUnderLinePaint;

    private Context context;

    private int currentPosition;
    private int beforePosition;
    private int afterPosition;
    private float currentPositionOffset;

    private int tabOrientation;
    private int scaleTypeImageIcon;

    public TabLayout(Context context) {
        super(context);
        this.context = context;
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs, defStyleAttr);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.currentPosition = position;
        this.currentPositionOffset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        this.beforePosition = afterPosition;
        this.afterPosition = position;
        if (beforePosition == afterPosition) return;
        View beforeChildView = getChildAt(beforePosition);
        View currentChildView = getChildAt(afterPosition);
        beforeChildView.setSelected(false);
        currentChildView.setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isVisibleUnderLiner) return;
        int count = getChildCount();

        View currentTab = getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();
        if (currentPositionOffset > 0f && currentPosition < count - 1) {
            final float nextTabLeft = lineLeft + currentTab.getWidth();
            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = lineLeft + currentTab.getWidth();
        }

        canvas.drawRect(lineLeft, currentTab.getHeight() - 5, lineRight, currentTab.getHeight(), mUnderLinePaint);
    }


    private void init(Context context, AttributeSet attrs, int def){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabLayout, def, 0);
        textColorNormal = typedArray.getResourceId(R.styleable.TabLayout_tab_text_color, R.color.tab_color_selected_default);
        textSize = typedArray.getDimension(R.styleable.TabLayout_tab_text_size, DEFAULT_SIZE);
        colorUnderLiner = typedArray.getColor(R.styleable.TabLayout_tab_under_line_color, DEFAULT_COLOR_UNDER_LINE);
        isVisibleUnderLiner = typedArray.getBoolean(R.styleable.TabLayout_tab_under_line_visible, DEFAULT_VISIBLE_UNDER_LINE);
        isAutoAlign = typedArray.getBoolean(R.styleable.TabLayout_tab_auto_align, DEFAULT_AUTO_ALIGN);
        paddingLeft = typedArray.getDimension(R.styleable.TabLayout_tab_padding_left, DEFAULT_PADDING);
        paddingRight = typedArray.getDimension(R.styleable.TabLayout_tab_padding_right, DEFAULT_PADDING);
        paddingTop = typedArray.getDimension(R.styleable.TabLayout_tab_padding_top, DEFAULT_PADDING);
        paddingBottom = typedArray.getDimension(R.styleable.TabLayout_tab_padding_bottom, DEFAULT_PADDING);
        resBackground = typedArray.getResourceId(R.styleable.TabLayout_tab_background, 0);
        tabOrientation = typedArray.getInt(R.styleable.TabLayout_tab_orientation, 1);
        scaleTypeImageIcon = typedArray.getInt(R.styleable.TabLayout_tab_scale_type, -1);
        typedArray.recycle();

        mUnderLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnderLinePaint.setColor(colorUnderLiner);
        if (tabOrientation == 1){
            setOrientation(HORIZONTAL);
        }
        else {
            isVisibleUnderLiner = false;
            setOrientation(VERTICAL);
        }

        setWillNotDraw(false);
    }

    public void setViewPager(ViewPager viewPager, SlidingTabAdapter slidingTabAdapter){
        this.viewPager = viewPager;
        this.adapter = slidingTabAdapter;
        this.viewPager.addOnPageChangeListener(this);



//        LinearLayout.LayoutParams parentParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        setLayoutParams(parentParams);

        int count = slidingTabAdapter.getCount();
        if (count <= 0) return;
        int padding = (int) DeviceDimensionsHelper.convertDpToPixel(DEFAULT_PADDING, context);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        if (tabOrientation == 0){
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        }
        if (!isAutoAlign){
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        for (int i=0; i< count; i++){
            String title = adapter.getTitle(i);
            int icon = adapter.getIcon(i);
            if (!TextUtils.isEmpty(title) && icon != 0){
                this.addView(addTextWithIcon(i, title, icon, layoutParams));
            }
            else if (TextUtils.isEmpty(title) && icon != 0){
                this.addView(addIcon(i, icon, layoutParams));
            }
            else {
                this.addView(addTextView(i, title, layoutParams));
            }
        }
        View childView = getChildAt(0);
        childView.setSelected(true);
        requestLayout();
    }

    private View addTextView(final int position, String text, ViewGroup.LayoutParams layoutParams){
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding((int)paddingLeft, (int)paddingTop, (int)paddingRight, (int)paddingBottom);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(textSize);
        try {
            textView.setTextColor(getResources().getColorStateList(textColorNormal));
        }
        catch (Exception e){

        }
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager == null) return;
                viewPager.setCurrentItem(position, true);
            }
        });

        if (resBackground != 0){
            textView.setBackgroundResource(resBackground);
        }
        return textView;
    }

    private View addTextWithIcon(final int position, String text, int icon, ViewGroup.LayoutParams layoutParams){
        final TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding((int)paddingLeft, (int)paddingTop, (int)paddingRight, (int)paddingBottom);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(textSize);
        try {
            textView.setTextColor(getResources().getColorStateList(textColorNormal));
        }
        catch (Exception e){
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
        textView.setCompoundDrawablePadding(DEFAULT_PADDING_DRAWBLE);
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager == null) return;
                viewPager.setCurrentItem(position, true);
            }
        });
        if (resBackground != 0){
            textView.setBackgroundResource(resBackground);
        }
        return textView;
    }

    private View addIcon(final int position, int icon, ViewGroup.LayoutParams layoutParams){
        ImageView imageView = new ImageView(context);
        imageView.setPadding((int)paddingLeft, (int)paddingTop, (int)paddingRight, (int)paddingBottom);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(icon);
        if (scaleTypeImageIcon == 0){
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
        }
        else if (scaleTypeImageIcon == 1){
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else if (scaleTypeImageIcon == 2){
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
        }
        else if (scaleTypeImageIcon == 3){
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        else if (scaleTypeImageIcon == 4){
            imageView.setScaleType(ImageView.ScaleType.FIT_END);
        }
        else if (scaleTypeImageIcon == 5){
            imageView.setScaleType(ImageView.ScaleType.CENTER);
        }
        else if (scaleTypeImageIcon == 6){
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else if (scaleTypeImageIcon == 7){
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager == null) return;
                viewPager.setCurrentItem(position, true);
            }
        });
        if (resBackground != 0){
            imageView.setBackgroundResource(resBackground);
        }
        return imageView;
    }


}
