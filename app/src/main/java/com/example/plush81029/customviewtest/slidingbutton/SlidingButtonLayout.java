//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.plush81029.customviewtest.slidingbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.plush81029.customviewtest.R;

import java.util.ArrayList;


public class SlidingButtonLayout extends FrameLayout {
    private ViewDragHelper mViewDragHelper;
    private ImageView mImageView;
    private TextView mTextView;
    private ViewGroup parentGroup;
    private Point initPosition = new Point();
    private ProgressBar progressBar;
    private String initText;
    private String dragFinishText;
    private Drawable initImageViewDrawable;
    private Drawable dragFinishDrawable;
    private int ivBackgroundColor;
    private int dragFinishIvBackgroundColor;
    private int textViewTextColor;
    private int dragFinishTextViewTextColor;
    private float textviewTextSize;
    private SlidingButtonLayout.OnFinshDragListener mOnFinshDragListener;
    private boolean isFinishDragFlag = false;
    private float mMaxFlingVelocity = 0.01F;
    private float imageWidth;
    private Drawable progressDrawable;
    private Context mContext;
    private String appCode;
    private VelocityTracker mVelocityTracker;
    private boolean dragFlag;
    private float nowXspeed;
    private float nowYspeed;
    private ArrayList<SlidingTrackItem> slidingTrackItems = new ArrayList();
    float x1;
    private int pbWidth;

//    private boolean isFinishDragFlag

    public void setDragFlag(boolean dragFlag) {
        this.dragFlag = dragFlag;
    }

    public void setOnFinshDragListener(SlidingButtonLayout.OnFinshDragListener onFinshDragListener) {
        this.mOnFinshDragListener = onFinshDragListener;
    }

    public void initUIconfig(String dragFinishText, Drawable dragFinishDrawable) {
        this.dragFinishText = dragFinishText;
        this.dragFinishDrawable = dragFinishDrawable;
    }

    public SlidingButtonLayout(Context context) {
        super(context);
    }

    public void refreshToInitStatus() {
        this.isFinishDragFlag = false;
        if(!TextUtils.isEmpty(this.initText)) {
            this.mTextView.setText(this.initText);
        }

        this.mTextView.setTextColor(this.textViewTextColor);
        this.progressBar.setProgress(0);
        if(null != this.initImageViewDrawable) {
            this.mImageView.setBackgroundDrawable(this.initImageViewDrawable);
        } else {
            this.mImageView.setBackgroundDrawable(new ColorDrawable(this.ivBackgroundColor));
        }

        this.mViewDragHelper.smoothSlideViewTo(this.mImageView, this.initPosition.x, this.initPosition.y);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void dragFinishUiChange() {
        if(!TextUtils.isEmpty(this.dragFinishText)) {
            this.mTextView.setText(this.dragFinishText);
        }

        this.mTextView.setTextColor(this.dragFinishTextViewTextColor);
        if(null != this.dragFinishDrawable) {
            this.mImageView.setBackgroundDrawable(this.dragFinishDrawable);
        } else {
            this.mImageView.setBackgroundDrawable(new ColorDrawable(this.dragFinishIvBackgroundColor));
        }

    }

    public SlidingButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mMaxFlingVelocity = (float)ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ac_sliding_layout, this);
        this.parentGroup = (ViewGroup)this.findViewById(R.id.parent_group);
        this.mImageView = (ImageView)view.findViewById(R.id.drag_button);
        this.mTextView = (TextView)view.findViewById(R.id.content_textview);
        this.progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        this.initStyles(context, attrs);
        this.mViewDragHelper = ViewDragHelper.create(this.parentGroup, 1.0F, new Callback() {
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                if(SlidingButtonLayout.this.pbWidth == 0) {
                    SlidingButtonLayout.this.pbWidth = SlidingButtonLayout.this.progressBar.getWidth();
                    SlidingButtonLayout.this.progressBar.setMax(SlidingButtonLayout.this.pbWidth);
                }
                SlidingButtonLayout.this.progressBar.setProgress(left);
                if (left==SlidingButtonLayout.this.progressBar.getMax()-changedView.getWidth()+2){
                    SlidingButtonLayout.this.isFinishDragFlag = true;
                    onViewReleased(changedView,0,0);
                    Log.e("LHD","应该结束滑动....."+left+"----"+(SlidingButtonLayout.this.progressBar.getMax()-changedView.getWidth()));
                }
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            public boolean tryCaptureView(View child, int pointerId) {
                return SlidingButtonLayout.this.isFinishDragFlag?false:child == SlidingButtonLayout.this.mImageView;
            }

            public int clampViewPositionVertical(View child, int top, int dy) {
                return SlidingButtonLayout.this.initPosition.y;
            }

            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int leftBound = SlidingButtonLayout.this.getPaddingLeft();
                int rightBound = SlidingButtonLayout.this.getWidth() - child.getWidth() - leftBound;
                int pix = Math.min(Math.max(left, leftBound), rightBound);
                if(left > rightBound - 10) {
                    SlidingButtonLayout.this.isFinishDragFlag = true;
                    pix = rightBound;
                }

                return pix;
            }

            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if(releasedChild == SlidingButtonLayout.this.mImageView && !SlidingButtonLayout.this.isFinishDragFlag) {
                    SlidingButtonLayout.this.mViewDragHelper.settleCapturedViewAt(SlidingButtonLayout.this.initPosition.x, SlidingButtonLayout.this.initPosition.y);
                    SlidingButtonLayout.this.invalidate();
                    SlidingButtonLayout.this.progressBar.setProgress(0);
                } else {
                    SlidingButtonLayout.this.progressBar.setProgress(SlidingButtonLayout.this.pbWidth);
                    if(SlidingButtonLayout.this.mOnFinshDragListener != null) {
                        SlidingButtonLayout.this.mOnFinshDragListener.onFinshDragDone(SlidingButtonLayout.this.getLastData());
                    }

                    SlidingButtonLayout.this.dragFinishUiChange();
                }

            }
        });
    }

    private String getLastData() {
        String infoS = (new InfosItem(this.appCode, this.mContext)).generateInfoMd5String();
        String desKey = this.get3desKey(infoS);
        String desSource = this.generateNeed3desStr();
        String datas = null;

        try {
            datas = ThreeDes.encrypt(desKey, desSource, 1);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return infoS + "^IAR^" + datas;
    }

    private String get3desKey(String str) {
        String finalStr = Utils.MD5(str.replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", ""));
        StringBuilder sb = new StringBuilder();
        sb.append(finalStr.substring(0, 24));
        return sb.toString().toUpperCase();
    }

    private String generateNeed3desStr() {
        int slidingTrackItemsSize = this.slidingTrackItems.size();
        int deleteItemNum = slidingTrackItemsSize - 17;

        int lastSlidingTrackItemsSize;
        for(lastSlidingTrackItemsSize = 1; lastSlidingTrackItemsSize <= deleteItemNum; ++lastSlidingTrackItemsSize) {
            this.slidingTrackItems.remove(1);
        }

        lastSlidingTrackItemsSize = this.slidingTrackItems.size();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < lastSlidingTrackItemsSize; ++i) {
            if(i == lastSlidingTrackItemsSize - 1) {
                sb.append(((SlidingTrackItem)this.slidingTrackItems.get(i)).toString());
            } else {
                sb.append(((SlidingTrackItem)this.slidingTrackItems.get(i)).toString()).append("^");
            }
        }

        sb = this.init3desSourceStr(sb);
        return sb.toString();
    }

    private StringBuilder init3desSourceStr(StringBuilder sb) {
        if(sb.length() % 8 == 0) {
            return sb;
        } else {
            sb.append(0);
            return this.init3desSourceStr(sb);
        }
    }

    private void initStyles(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.slidingButtonLayout);
        this.initText = typedArray.getString(R.styleable.slidingButtonLayout_textview_text);
        this.dragFinishText = typedArray.getString(R.styleable.slidingButtonLayout_textview_dragfinish_text);
        this.dragFinishTextViewTextColor = typedArray.getColor(R.styleable.slidingButtonLayout_textview_dragfinish_textcolor, 0);
        this.initImageViewDrawable = typedArray.getDrawable(R.styleable.slidingButtonLayout_imageview_background);
        this.dragFinishDrawable = typedArray.getDrawable(R.styleable.slidingButtonLayout_imageview_dragfinish_background);
        this.ivBackgroundColor = typedArray.getColor(R.styleable.slidingButtonLayout_imageview_backgroundColor, 0);
        this.dragFinishIvBackgroundColor = typedArray.getColor(R.styleable.slidingButtonLayout_imageview_dragfinish_backgroundColor, 0);
        this.appCode = typedArray.getString(R.styleable.slidingButtonLayout_appCode);
        this.mTextView.setText(this.initText);
        this.textViewTextColor = typedArray.getColor(R.styleable.slidingButtonLayout_textview_textcolor, 16777215);
        this.mTextView.setTextColor(this.textViewTextColor);
        this.textviewTextSize = typedArray.getDimension(R.styleable.slidingButtonLayout_textview_text_size, 0.0F);
        float de = context.getApplicationContext().getResources().getDisplayMetrics().density;
        this.mTextView.setTextSize(this.textviewTextSize / de);
        this.dragFlag = typedArray.getBoolean(R.styleable.slidingButtonLayout_drag_flag, false);
        this.progressDrawable = typedArray.getDrawable(R.styleable.slidingButtonLayout_progessbar_drawable);
        this.imageWidth = typedArray.getDimension(R.styleable.slidingButtonLayout_imageview_width, 0.0F);
        if(null != this.initImageViewDrawable) {
            this.mImageView.setBackgroundDrawable(this.initImageViewDrawable);
        } else {
            LayoutParams lp = new LayoutParams((int)this.imageWidth, -1);
            this.mImageView.setLayoutParams(lp);
            this.mImageView.setBackgroundDrawable(new ColorDrawable(this.ivBackgroundColor));
        }

        this.mImageView.setTag("imageview");
        this.progressBar.setProgressDrawable(this.progressDrawable);
        this.progressBar.setIndeterminate(false);
        this.progressBar.setMax(600);
        typedArray.recycle();
    }

    public SlidingButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void computeScroll() {
        if(this.mViewDragHelper.continueSettling(true)) {
            this.invalidate();
        }

    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(!this.isFinishDragFlag) {
            super.onLayout(changed, left, top, right, bottom);
            this.initPosition.x = this.mImageView.getLeft();
            this.initPosition.y = this.mImageView.getTop();
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    private void startGetInfo(SlidingTrackItem slidingTrackItem) {
        this.slidingTrackItems.add(slidingTrackItem);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(!this.dragFlag||this.isFinishDragFlag) {
            return true;
        }
        else {
            this.mViewDragHelper.processTouchEvent(event);
            if(this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }

            this.mVelocityTracker.addMovement(event);
            SlidingTrackItem slidingTrackItem = new SlidingTrackItem(System.currentTimeMillis());
            byte pointId = -1;
            switch(event.getAction()) {
                case 0:
                    int pointId1 = event.getPointerId(0);
                    this.slidingTrackItems.clear();
                    break;
                case 1:
                    this.recycleVelocityTracker();
                    break;
                case 2:
                    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
                    this.nowXspeed = this.mVelocityTracker.getXVelocity(pointId);
                    this.nowYspeed = this.mVelocityTracker.getYVelocity(pointId);
                    slidingTrackItem.setX(event.getX());
                    slidingTrackItem.setY(event.getY());
                    slidingTrackItem.setSpeed(this.nowXspeed);
                    this.slidingTrackItems.add(slidingTrackItem);
            }

            return true;
        }
    }

    private void recycleVelocityTracker() {
        if(this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }

    }

    protected void onFinishInflate() {
        this.pbWidth = this.progressBar.getWidth();
        super.onFinishInflate();
    }

    public interface OnFinshDragListener {
        void onFinshDragDone(String var1);
    }
}
