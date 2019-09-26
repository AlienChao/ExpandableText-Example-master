package com.mrtrying.widget.expandabletext;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 文 件 名：ExpandLayout
 * 描    述：
 * 作    者：chenhao
 * 时    间：2018/6/8
 * 版    权：v1.0
 */
public class ExpandLayout extends LinearLayout {
    public ExpandLayout(Context context) {
        this(context, null);
    }

    public ExpandLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private View layoutView;
    private int viewHeight;
    private boolean isExpand;
    private long animationDuration;
    private boolean lock;
    private ScrollView mScrollView;

    private void initView() {
        layoutView = this;
        isExpand = true;
        animationDuration = 300;
        setViewDimensions();
    }

    /**
     * @param isExpand 初始状态是否折叠
     */
    public void initExpand(boolean isExpand) {
        this.isExpand = isExpand;
        setViewDimensions();
    }

    public void setScrollView(ScrollView scrollView) {
        this.mScrollView = scrollView;
    }

    /**
     * 设置动画时间
     *
     * @param animationDuration 动画时间
     */
    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * 获取subView的总高度
     * View.post()的runnable对象中的方法会在View的measure、layout等事件后触发
     */
    private void setViewDimensions() {
        layoutView.post(new Runnable() {
            @Override
            public void run() {
                if (viewHeight <= 0) {
                    viewHeight = layoutView.getMeasuredHeight();
                }
                setViewHeight(layoutView, isExpand ? viewHeight : 0);
            }
        });
    }

    public void setViewHeight(View view, final int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.requestLayout();
    }

    /**
     * 切换动画实现
     */
    private void animateToggle(long animationDuration) {
        ValueAnimator heightAnimation = isExpand ?
                ValueAnimator.ofInt(0, viewHeight) : ValueAnimator.ofInt(viewHeight, 0);
//        heightAnimation.setDuration(animationDuration);
//        heightAnimation.setStartDelay(animationDuration);

        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setViewHeight(layoutView, value);
                if (isExpand && mScrollView != null) {
                    mScrollView.smoothScrollBy(0, value);
                }
                if (value == viewHeight || value == 0) {
                    lock = false;
                }
            }
        });

        heightAnimation.start();
        lock = true;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 折叠view
     */
    public void collapse() {
        isExpand = false;
        animateToggle(animationDuration);
    }

    /**
     * 展开view
     */
    public void expand() {
        isExpand = true;
        animateToggle(animationDuration);
    }

    public void toggleExpand() {
        if (lock) {
            return;
        }
        if (isExpand) {
            collapse();
        } else {
            expand();
        }
    }
}
