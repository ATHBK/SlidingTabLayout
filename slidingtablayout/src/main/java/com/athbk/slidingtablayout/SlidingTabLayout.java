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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by athbk on 3/23/17.
 */

public class SlidingTabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener {

    private final int DEFAULT_COLOR_NORMAL = Color.parseColor("#000000");
    private final int DEFAULT_COLOR_UNDER_LINE = Color.parseColor("#000000");
    private final boolean DEFAULT_VISIBLE_UNDER_LINE = true;
    private final float DEFAULT_PADDING = 16f;
    private final float DEFAULT_SIZE = 14f;
    private final int DEFAULT_PADDING_DRAWBLE = 10;
    private final int DEFAULT_SIZE_UNDER_LINE = 5;

    private int resBackground;
    private int textColorNormal;
    private int colorUnderLiner;
    private boolean isVisibleUnderLiner;
    private float sizeWidthTab;
    private float textSize;

    private float paddingLeft;
    private float paddingRight;
    private float paddingTop;
    private float paddingBottom;

    private ViewPager viewPager;
    private SlidingTabAdapter adapter;
    private Paint mUnderLinePaint;

    private Context context;

    private int currentPosition;
    private int beforePosition;
    private int afterPosition;
    private int mScrollState;
    private float currentPositionOffset;

    private LinearLayout tabLayout;
//    private LinearLayout parentlayout;
    private int tabOrientation;
    private int iconPosition;

    public SlidingTabLayout(Context context) {
        super(context);
        this.context = context;
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isVisibleUnderLiner) return;
        int count = tabLayout.getChildCount();
        if (count <=0) return;
        View childView = tabLayout.getChildAt(currentPosition);
        float left = childView.getLeft();
        float right = childView.getRight();
        float width = childView.getWidth();
        if (currentPositionOffset > 0f && currentPosition < count - 1) {
            final float nextTabLeft = left + width;
            left = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * left);
            right = left + width;
        }
        canvas.drawRect(left, childView.getHeight() - DEFAULT_SIZE_UNDER_LINE , right, childView.getHeight(), mUnderLinePaint);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int tabStripChildCount = tabLayout.getChildCount();
        if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
            return;
        }

        View selectedTitle = tabLayout.getChildAt(position);
        int extraOffset = (selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0;
        scrollToTab(position, extraOffset);

        this.currentPosition = position;
        this.currentPositionOffset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        this.beforePosition = afterPosition;
        this.afterPosition = position;
        if (beforePosition == afterPosition) return;
        try {
            View beforeChildView = tabLayout.getChildAt(beforePosition);
            View currentChildView = tabLayout.getChildAt(afterPosition);
            beforeChildView.setSelected(false);
            currentChildView.setSelected(true);

            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                scrollToTab(position, 0);
            }
            invalidate();
        }catch (NullPointerException ex){}
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        this.mScrollState = state;
    }


    private void init(Context context, AttributeSet attrs, int def) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingTabLayout, def, 0);
        textColorNormal = typedArray.getResourceId(R.styleable.SlidingTabLayout_sl_tab_text_color, R.color.tab_color_selected_default);
        textSize = typedArray.getDimension(R.styleable.SlidingTabLayout_sl_tab_text_size, DEFAULT_SIZE);
        colorUnderLiner = typedArray.getColor(R.styleable.SlidingTabLayout_sl_tab_under_line_color, DEFAULT_COLOR_UNDER_LINE);
        isVisibleUnderLiner = typedArray.getBoolean(R.styleable.SlidingTabLayout_sl_tab_under_line_visible, DEFAULT_VISIBLE_UNDER_LINE);
        sizeWidthTab = typedArray.getDimension(R.styleable.SlidingTabLayout_sl_tab_size_width, 0);
        paddingLeft = typedArray.getDimension(R.styleable.SlidingTabLayout_sl_tab_padding_left, DEFAULT_PADDING);
        paddingRight = typedArray.getDimension(R.styleable.SlidingTabLayout_sl_tab_padding_right, DEFAULT_PADDING);
        paddingTop = typedArray.getDimension(R.styleable.SlidingTabLayout_sl_tab_padding_top, DEFAULT_PADDING);
        paddingBottom = typedArray.getDimension(R.styleable.SlidingTabLayout_sl_tab_padding_bottom, DEFAULT_PADDING);
        resBackground = typedArray.getResourceId(R.styleable.SlidingTabLayout_sl_tab_background, 0);
        tabOrientation = typedArray.getInt(R.styleable.SlidingTabLayout_sl_orientation, 1);
        iconPosition = typedArray.getInt(R.styleable.SlidingTabLayout_sl_icon_position, 0);
        typedArray.recycle();

        mUnderLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnderLinePaint.setColor(colorUnderLiner);

        this.setFillViewport(true);
        LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (tabOrientation == 0) {
            parentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
//        parentlayout = new LinearLayout(context);
//        parentlayout.setOrientation(LinearLayout.VERTICAL);

        tabLayout = new LinearLayout(context);
        tabLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (tabOrientation == 0){
            tabLayout.setOrientation(LinearLayout.VERTICAL);
        }
        tabLayout.setLayoutParams(parentParams);

//        parentlayout.addView(tabLayout);
//        if (isVisibleUnderLiner){
//            View view = new View(context);
//            ViewGroup.LayoutParams pr = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DEFAULT_SIZE_UNDER_LINE);
//            view.setLayoutParams(pr);
//            parentlayout.addView(view);
//        }

        this.addView(tabLayout);
        setLayoutParams(parentParams);
        setHorizontalScrollBarEnabled(false);

        setWillNotDraw(false);
    }

    public void setViewPager(ViewPager viewPager, SlidingTabAdapter slidingTabAdapter) {
        this.viewPager = viewPager;
        this.adapter = slidingTabAdapter;
        this.viewPager.addOnPageChangeListener(this);

        int count = slidingTabAdapter.getCount();
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (sizeWidthTab != 0) {
            layoutParams = new LinearLayout.LayoutParams((int)sizeWidthTab, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        for (int i = 0; i < count; i++) {
            String title = adapter.getTitle(i);
            int icon = adapter.getIcon(i);
            if (!TextUtils.isEmpty(title) && icon != 0) {
                tabLayout.addView(addTextWithIcon(i, title, icon, layoutParams));
            } else if (TextUtils.isEmpty(title) && icon != 0) {
                tabLayout.addView(addIcon(i, icon, layoutParams));
            } else {
                tabLayout.addView(addTextView(i, title, layoutParams));
            }
        }
        View childView = tabLayout.getChildAt(0);
        childView.setSelected(true);
        requestLayout();
    }

    private View addTextView(final int position, String text, ViewGroup.LayoutParams layoutParams) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(textSize);
        textView.setTextColor(getResources().getColorStateList(textColorNormal));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager == null) return;
                viewPager.setCurrentItem(position, true);
            }
        });
        if (resBackground != 0) {
            textView.setBackgroundResource(resBackground);
        }
        return textView;
    }

    private View addTextWithIcon(final int position, String text, int icon, ViewGroup.LayoutParams layoutParams) {
        final TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(textSize);
        textView.setTextColor(getResources().getColorStateList(textColorNormal));
        switch (iconPosition){
            case 0:
                textView.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
                break;

            case 1:
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, icon);
                break;

            case 2:
                textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
                break;

            case 3:
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0);
                break;

        }
        textView.setCompoundDrawablePadding(DEFAULT_PADDING_DRAWBLE);
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager == null) return;
                viewPager.setCurrentItem(position, true);
            }
        });
        if (resBackground != 0) {
            textView.setBackgroundResource(resBackground);
        }
        return textView;
    }

    private View addIcon(final int position, int icon, ViewGroup.LayoutParams layoutParams) {
        ImageView imageView = new ImageView(context);
        imageView.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        imageView.setLayoutParams(layoutParams);

        Drawable myIcon = getResources().getDrawable(icon);
        imageView.setImageDrawable(myIcon);
//        imageView.setImageResource(icon);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager == null) return;
                viewPager.setCurrentItem(position, true);
            }
        });
        if (resBackground != 0) {
            imageView.setBackgroundResource(resBackground);
        }
        return imageView;
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = tabLayout.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = tabLayout.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= (int) DeviceDimensionsHelper.convertDpToPixel(DEFAULT_PADDING * 2, context);
            }

            scrollTo(targetScrollX, 0);
        }
    }
}
