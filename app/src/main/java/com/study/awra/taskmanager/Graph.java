package com.study.awra.taskmanager;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

class Graph extends View {
    private Paint paint;
    private Context mContext;
    private Point pointSecond;
    private Point pointFirst;
    private int[] mP;
    private int mX;
    private int color_line;
    private int color_point;
    private int color_line_y;
    private int color_graph;
    private float line_width;
    private Path path;
    private int mY;
    private int radius_point;

    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();

    }

    public Graph(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial();

    }

    private void initial() {
        pointFirst = new Point(0, 0);
        pointSecond = new Point(0, 0);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        color_point= getResources().getColor(R.color.point_graph);
        color_line_y= getResources().getColor(R.color.line_y_graph);
        color_graph=  getResources().getColor(R.color.color_graph);
        color_line =getResources().getColor(R.color.line_graph);
        line_width =getResources().getDimension(R.dimen.line_width);
        radius_point= (int) getResources().getDimension(R.dimen.radius_point);
        path=new Path();
        paint.setStrokeWidth(line_width);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        float scaleY = (height - 2 * (radius_point + line_width)) / mY;
        int length = mP.length;
        float scaleX = (width - 2 * (radius_point + line_width)) / (length - 1);
        paint.setStrokeWidth(line_width);
        for(int i=0;i<mP.length;i++) {
            pointSecond.set((int) (scaleX * i + radius_point + line_width), (int) (height - scaleY * mP[i]) - radius_point);
            if (path.isEmpty())
                path.moveTo(pointSecond.x, height);
            path.lineTo(pointSecond.x, pointSecond.y);
            if(i+1==length)
                path.lineTo(pointSecond.x,height);
        }
        path.close();
        paint.setColor(color_graph);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path,paint);
        pointFirst.set(0,0);
        paint.setColor(color_line);
        paint.setStyle(Paint.Style.STROKE);
        for(int i=0;i<length;i++) {
            pointSecond.set((int) (scaleX * i + radius_point + line_width), (int) (height - scaleY * mP[i]) - radius_point);
            if(!pointFirst.equals(0,0) && i-1<length)
                canvas.drawLine(pointFirst.x,pointFirst.y,pointSecond.x,pointSecond.y,paint);
            pointFirst.set(pointSecond.x,pointSecond.y);
        }
        for(int i=0;i<length;i++) {
            paint.setColor(color_line_y);
            pointSecond.set((int) (scaleX * i + radius_point + line_width), (int) (height - scaleY * mP[i]) - radius_point);
            canvas.drawLine(pointSecond.x,height,pointSecond.x,pointSecond.y,paint);
            paint.setColor(color_line);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(pointSecond.x,pointSecond.y,radius_point,paint);
            paint.setColor(color_point);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(pointSecond.x,pointSecond.y,radius_point,paint);
        }
    }


    public void setData(int...i) {
        mP = i;
        mX=i.length;
        mY=0;
        for(int m:i)
            if(m>mY) mY=m;
    }
}
