package asgard.weapon.mapDisplay;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import asgard.weapon.R;
import asgard.weapon.map.*;


public class MapImageView extends View {

private static final int INVALID_POINTER_ID = -1;

	private Paint lineColour;
	private Node[] nodeList;
    private Drawable mImage;
    private Drawable nodes;
    private float mPosX, mPosY;
    private float mLastTouchX, mLastTouchY;
    private int mActivePointerId = INVALID_POINTER_ID;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    int screenWidth;
    int screenHeight;
    
    

    public MapImageView(Context context) {
        this(context, null, 0);
     /*   mImage = getResources().getDrawable(R.drawable.map);
        if(!this.isInEditMode())
        {
        ShapeDrawable mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(0xff74AC23);
        mDrawable.setBounds(0, 0, 100, 500);
        nodes = mDrawable;
        mImage.setBounds(0, 0, mImage.getIntrinsicWidth(), mImage.getIntrinsicHeight());
        }
        if(!this.isInEditMode()){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        
        screenWidth = display.getWidth();  // deprecated
        screenHeight = display.getHeight();  // deprecated
        }
        else
        {
        	screenHeight = screenWidth = 0;
        }*/
    }

    public MapImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
       /* mImage = getResources().getDrawable(R.drawable.map);
        if(!this.isInEditMode())
        {
        ShapeDrawable mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(0xff74AC23);
        mDrawable.setBounds(0, 0, 100, 500);
        nodes = mDrawable;
        mImage.setBounds(0, 0, mImage.getIntrinsicWidth(), mImage.getIntrinsicHeight());
        }
        if(!this.isInEditMode()){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        
        screenWidth = display.getWidth();  // deprecated
        screenHeight = display.getHeight();  // deprecated
        }
        else
        {
        	screenHeight = screenWidth = 0;
        }*/
    }

    public MapImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        nodeList = null;
        lineColour = new Paint();
        lineColour.setColor(Color.rgb(80, 39, 132));
        lineColour.setStrokeWidth(10);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mImage = getResources().getDrawable(R.drawable.map);
        if(!this.isInEditMode())
        {
        ShapeDrawable mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(0xff74AC23);
        mDrawable.setBounds(0, 0, 100, 500);
        nodes = mDrawable;
        mImage.setBounds(0, 0, mImage.getIntrinsicWidth(), mImage.getIntrinsicHeight());
        }
        if(!this.isInEditMode()){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        
        screenWidth = display.getWidth();  // deprecated
        screenHeight = display.getHeight();  // deprecated
        }
        else
        {
        	screenHeight = screenWidth = 0;
        }
    }
    public void setImage(int imageResource)
    {
    	mImage = getResources().getDrawable(imageResource);
    	mImage.setBounds(0, 0, mImage.getIntrinsicWidth(), mImage.getIntrinsicHeight());
    	this.invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN: {
            final float x = ev.getX();
            final float y = ev.getY();
            mLastTouchX = x;
            mLastTouchY = y;
            mActivePointerId = ev.getPointerId(0);
            break;
        }

        case MotionEvent.ACTION_MOVE: {
            final int pointerIndex = ev.findPointerIndex(mActivePointerId);
            final float x = ev.getX(pointerIndex);
            final float y = ev.getY(pointerIndex);

            // Only move if the ScaleGestureDetector isn't processing a gesture.
            if (!mScaleDetector.isInProgress()) {
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx;
                mPosY += dy;

                invalidate();
            }

            mLastTouchX = x;
            mLastTouchY = y;

            break;
        }

        case MotionEvent.ACTION_UP: {
            mActivePointerId = INVALID_POINTER_ID;
            break;
        }

        case MotionEvent.ACTION_CANCEL: {
            mActivePointerId = INVALID_POINTER_ID;
            break;
        }

        case MotionEvent.ACTION_POINTER_UP: {
            final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
                    >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            final int pointerId = ev.getPointerId(pointerIndex);
            if (pointerId == mActivePointerId) {
                // This was our active pointer going up. Choose a new
                // active pointer and adjust accordingly.
                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                mLastTouchX = ev.getX(newPointerIndex);
                mLastTouchY = ev.getY(newPointerIndex);
                mActivePointerId = ev.getPointerId(newPointerIndex);
            }
            break;
        }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        Log.d("DEBUG", "X: "+mPosX+" Y: "+mPosY);
        if (mPosY > 0) mPosY = 0;
        if (mPosX > 0) mPosX = 0;
        
        int imageHieght = mImage.getMinimumHeight();
        int imageWidth = mImage.getMinimumWidth();
        
      //  if (screenWidth < mImage.getIntrinsicWidth() + mPosX) 
       // 	mPosX =  mImage.getIntrinsicWidth()- screenWidth;
       // if (screenWidth > imageWidth + mPosX - screenWidth) 
        //	mPosX = -imageWidth + (2 * screenWidth);
        
      //  if (-mPosY + screenHeight > imageHieght)
       // 	mPosY = screenHeight - imageHieght;
       // if (screenHeight > imageHieght + mPosY - screenHeight) 
        //	mPosY = -imageHieght + (2 * screenHeight);
        	//mPosY = mImage.getIntrinsicHeight() - screenHeight + mPosY;
        
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        mImage.draw(canvas);
        if (nodeList != null)
        {
        	for (int i = 0; i < nodeList.length -1; i++)
        	{
        		canvas.drawLine(nodeList[i].getxPos(), nodeList[i].getyPos(), 
        				nodeList[i+1].getxPos(), nodeList[i+1].getyPos(), lineColour);
        	}
        }
       // nodes.draw(canvas);
        canvas.restore();
    }
    public void scale(double factor) {
        mScaleFactor *= factor;
        mPosX *= factor;
        mPosY *= factor;
        // Don't let the object get too small or too large.
        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

        invalidate();
        return;
    }
    public void drawNodes(Node[] nodes)
    {
    	nodeList = nodes;    
    	this.invalidate();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mPosX *= detector.getScaleFactor();
            mPosY *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            invalidate();
            return true;
        }
    }

}

