package rawe.gordon.com.expandablebutton;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

/**
 * Created by Developer on 2015/1/17.
 */
public class ExpandableButton extends Button {

    private enum Speed {
        FAST, SLOW;
    }

    private int interval;
    private float exceedMargin;
    private float center_x, center_y;
    private float radius;
    private boolean visible;
    private Speed mSpeed;
    private float max_radius, min_radius;
    private float window_width, window_height;
    private Paint paint;
    private ObjectAnimator objectAnimator;


    public ExpandableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        visible = false;
        interval = 2500;
        center_x = 0;
        center_y = 0;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        objectAnimator = ObjectAnimator.ofFloat(this, "radius", 0f, 0f);
        mSpeed = Speed.SLOW;

        //读取配置信息
        if (attrs != null) {
            final TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ExpandableButton);
            int color = arr.getColor(R.styleable.ExpandableButton_eb_color, Color.BLACK);//后面跟的是默认值
            exceedMargin = arr.getDimension(R.styleable.ExpandableButton_eb_exceed_margin, 20);
            paint.setColor(color);
            arr.recycle();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        radius = window_height < window_width ? window_height : window_width;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        window_width = getMeasuredWidth();
        window_height = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        center_x = w / 2;
        center_y = h / 2;
        max_radius = w > h ? w : h;
        min_radius = w < h ? w : h;
        invalidate();
    }

    public void setRadius(float radius) {
        this.radius = radius;
        this.invalidate();
    }

    public float getRadius() {
        return this.radius;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            visible = true;
            expands();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        center_x = event.getX();
        if (center_x > window_width + exceedMargin) center_x = window_width + exceedMargin;
        else if (center_x < -exceedMargin) center_x = -exceedMargin;
        center_y = event.getY();
        if (center_y > window_height + exceedMargin) center_y = window_height + exceedMargin;
        else if (center_y < -exceedMargin) center_y = -exceedMargin;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!within(center_x, center_y)) {
                    //让速度变快
                    if (mSpeed != Speed.FAST) {
                        mSpeed = Speed.FAST;
                        objectAnimator.cancel();
                        expands();
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                visible = false;
                becomeNormal();
                invalidate();
                mSpeed = Speed.SLOW;
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean within(float x, float y) {
        return x > 0 && x < window_width && y > 0 && y < window_height ? true : false;
    }

    public void expands() {
        objectAnimator.setFloatValues(max_radius);
        switch (mSpeed) {
            case FAST:
                objectAnimator.setDuration(interval / 5).setInterpolator(new DecelerateInterpolator(1f));
                break;
            case SLOW:
                objectAnimator.setDuration(interval).setInterpolator(new DecelerateInterpolator(1f));
                break;
        }
        objectAnimator.start();
    }

    public void becomeNormal() {
        objectAnimator.setFloatValues(min_radius);
        objectAnimator.setDuration(interval / 100).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha(59);
        if (visible)
            canvas.drawCircle(center_x, center_y, radius, paint);
    }
}
