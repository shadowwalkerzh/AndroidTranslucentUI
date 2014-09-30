package com.keen.ma.customui;

import com.keen.ma.R;
import com.keen.ma.activity.ForgetPwdActivity;
import com.keen.ma.interfaces.PageIndicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * @author wan
 * ViewPager显示当前页面时的View
 */
public class MyPagerIndicator extends View implements PageIndicator {
	
	private static final int INVALID_POINTER = -1;
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private ViewPager mViewPager;
	private int currentPage;
	private float mPositionOffset;//Item的间距
	private OnPageChangeListener mListener;
	private int scrollState;//滑动状态
	
	private int mTouchSlop;//触发移动事件的最小距离
	private float mLastMotionX = -1;//上次滑动时的x距离
    private int mActivePointerId = INVALID_POINTER;//触摸点索引类型
    private boolean mIsDragging;//ViewPager是否处于拖拽状态

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	@SuppressWarnings("deprecation")
	public MyPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if(isInEditMode())
			return;
		final Resources rs = getResources();
		final int defaultSelectedColor = rs.getColor(R.color.selected_color);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.viewPager_indicator, defStyle, 0);
		setSelectedColor(array.getColor(R.styleable.viewPager_indicator_selectedColor, defaultSelectedColor));
		
		//viewPager_indicator_android_background是viewPager_indicator下的android_background属性
		Drawable background = array.getDrawable(R.styleable.viewPager_indicator_android_background);
        if (background != null) {
          setBackgroundDrawable(background);
        }

        array.recycle();
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
	}

	public MyPagerIndicator(Context context, AttributeSet attrs) {
		this(context, attrs,R.attr.viewPageIndicatorStyle);
	}

	public MyPagerIndicator(Context context) {
		this(context,null);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		scrollState = state;
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		currentPage = position;
        mPositionOffset = positionOffset;
        invalidate();
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
	}

	@Override
	public void onPageSelected(int position) {
		if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
            currentPage = position;
            mPositionOffset = 0;
            invalidate();
        }
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
        ForgetPwdActivity.changeTextColor(position);
	}

	@Override
	public void setViewPager(ViewPager viewPager) {
		if (mViewPager == viewPager) {
            return;
        }
        if (mViewPager != null) {
            //清除原有Page
            mViewPager.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager没有创建适配器实例.");
        }
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);
        invalidate();
	}

	@Override
	public void setViewPager(ViewPager view, int initialPosition) {
		setViewPager(view);
        setCurrentItem(initialPosition);
	}

	@Override
	public void setCurrentItem(int itemPosition) {
		if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(itemPosition);
        currentPage = itemPosition;
        invalidate();
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mListener = listener;
	}

	@Override
	public void notifyDataSetChanged() {
		 invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(mViewPager == null)
			return;
		final int count = mViewPager.getAdapter().getCount();
        if (count == 0) {
            return;
        }
        
        if (currentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }
        
        final int paddingLeft = getPaddingLeft();
        final float pageWidth = (getWidth() - paddingLeft - getPaddingRight()) / (1f * count);
        final float left = paddingLeft + pageWidth * (currentPage + mPositionOffset);
        final float right = left + pageWidth;
        final float top = getPaddingTop();
        final float bottom = getHeight() - getPaddingBottom();
        canvas.drawRect(left, top, right, bottom, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (super.onTouchEvent(event)) {
            return true;
        }
        if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
            return false;
        }
        /*
         *  public static final int ACTION_DOWN             = 0; 单点触摸动作
		    public static final int ACTION_UP               = 1; 单点触摸离开动作 
		    public static final int ACTION_MOVE             = 2; 触摸点移动动作 
		    public static final int ACTION_CANCEL           = 3;触摸动作取消 
		     public static final int ACTION_OUTSIDE          = 4;触摸动作超出边界 
		    public static final int ACTION_POINTER_DOWN     = 5;多点触摸动作 
		    public static final int ACTION_POINTER_UP       = 6;多点离开动作 
		   	以下是一些非touch事件 
		    public static final int ACTION_HOVER_MOVE       = 7; 
		    public static final int ACTION_SCROLL           = 8; 
		    public static final int ACTION_HOVER_ENTER      = 9; 
		    public static final int ACTION_HOVER_EXIT       = 10; 
		    ACTION_MASK = 0X000000ff 动作掩码 
 			ACTION_POINTER_INDEX_MASK = 0X0000ff00 触摸点索引掩码 
			ACTION_POINTER_INDEX_SHIF T = 8 获取触摸点索引需要移动的位数 
         */
		final int action = event.getAction() & MotionEventCompat.ACTION_MASK;//获得动作类型
		switch (action) {
        case MotionEvent.ACTION_DOWN:
        	//获得触摸点索引类型
        	//有了索引信息，我们可以在onTOuchEvent事件中判断传进来的MotionEvent对象对应的是单点信息还是多点信息
            mActivePointerId = MotionEventCompat.getPointerId(event,0);
            mLastMotionX = event.getX();
            break;
        case MotionEvent.ACTION_MOVE: {
            final int activePointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
            final float x = MotionEventCompat.getX(event, activePointerIndex);
            final float deltaX = x - mLastMotionX;
            if (!mIsDragging) {
                if (Math.abs(deltaX) > mTouchSlop) {
                    mIsDragging = true;
                }
            }

            if (mIsDragging) {
                mLastMotionX = x;
                if (mViewPager.isFakeDragging() || mViewPager.beginFakeDrag()) {
                    mViewPager.fakeDragBy(deltaX);
                }
            }
            break;
        }
        case MotionEvent.ACTION_UP:
            if (!mIsDragging) {
                final int count = mViewPager.getAdapter().getCount();
                final int width = getWidth();
                final float halfWidth = width / 2f;
                final float sixthWidth = width / 6f;
                
                if ((currentPage > 0) && (event.getX() < halfWidth - sixthWidth)) {
                    if (action != MotionEvent.ACTION_CANCEL) {
                        mViewPager.setCurrentItem(currentPage - 1);
                    }
                    return true;
                } else if ((currentPage < count - 1) && (event.getX() > halfWidth + sixthWidth)) {
                    if (action != MotionEvent.ACTION_CANCEL) {
                        mViewPager.setCurrentItem(currentPage + 1);
                    }
                    return true;
                }
            }

            mIsDragging = false;
            mActivePointerId = INVALID_POINTER;
            if (mViewPager.isFakeDragging()) 
            	mViewPager.endFakeDrag();//终止控制滑动事件
            
            break;

        case MotionEventCompat.ACTION_POINTER_DOWN: {
            final int index = MotionEventCompat.getActionIndex(event);
            mLastMotionX = MotionEventCompat.getX(event, index);
            mActivePointerId = MotionEventCompat.getPointerId(event, index);
            break;
        }

        case MotionEventCompat.ACTION_POINTER_UP:
            final int pointerIndex = MotionEventCompat.getActionIndex(event);
            final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
            if (pointerId == mActivePointerId) {
                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
            }
            mLastMotionX = MotionEventCompat.getX(event, MotionEventCompat.findPointerIndex(event, mActivePointerId));
            break;
    }
		return true;
	}

	@Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState)state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = currentPage;
        return savedState;
    }
    
    static class SavedState extends BaseSavedState {
        int currentPage;
        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
	
	/**
	 * 设置选中时Indicator的颜色
	 * @param selectedColor
	 */
	public void setSelectedColor(int selectedColor) {
        mPaint.setColor(selectedColor);
        invalidate();
    }
	
	public int getSelectedColor() {
        return mPaint.getColor();
    }
	
}
