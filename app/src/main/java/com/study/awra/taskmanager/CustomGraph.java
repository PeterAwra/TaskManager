package com.study.awra.taskmanager;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

class CustomGraph extends View {
  private int colorLinePoint;
  private int colorLine;
  private int colorPoint;
  private int colorLineX;
  private int colorUnderLine;
  private float lineWidth;
  private Path path;
  private float radiusPoint;
  private int widthView;
  private int heightView;
  private Point[] points;
  private int[] data;
  private int maxX;
  private int maxY;
  private Paint paint;
  private float textSize;
  private float marginText;
  private int colorText;
  private String[] texts = { "AA", "BB", "CC", "DD", "FF", "EE", "GG", };
  private float paddingX;
  private float paddingY;

  public CustomGraph(Context context) {
    this(context, null);
  }

  public CustomGraph(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    handlerAttributes(context, attrs);
  }

  private void handlerAttributes(Context context, @Nullable AttributeSet attrs) {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    path = new Path();
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomGraph);
    Resources resources = context.getResources();
    {
      int defValue = resources.getColor(R.color.color_under_line_graph_default);
      colorUnderLine = typedArray.getColor(R.styleable.CustomGraph_colorUnderLine, defValue);
    }
    {
      int defValue = resources.getColor(R.color.color_graph_line_default);
      colorLine = typedArray.getColor(R.styleable.CustomGraph_colorLine, defValue);
    }
    {
      int defValue = resources.getColor(R.color.color_graph_line_x_default);
      colorLineX = typedArray.getColor(R.styleable.CustomGraph_colorLineX, defValue);
    }
    {
      int defValue = resources.getColor(R.color.color_graph_line_point_default);
      colorLinePoint = typedArray.getColor(R.styleable.CustomGraph_colorLinePoint, defValue);
    }
    {
      int defValue = resources.getColor(R.color.color_graph_point_default);
      colorPoint = typedArray.getColor(R.styleable.CustomGraph_colorPoint, defValue);
    }
    {
      int defValue = resources.getColor(R.color.color_text_default);
      colorText = typedArray.getColor(R.styleable.CustomGraph_textColor, defValue);
    }
    {
      float defValue = resources.getDimension(R.dimen.line_width_graph_default);
      lineWidth = typedArray.getDimension(R.styleable.CustomGraph_widthLine, defValue);
    }
    {
      float defValue = resources.getDimension(R.dimen.radius_point_graph_default);
      radiusPoint = typedArray.getDimension(R.styleable.CustomGraph_radiusPoint, defValue);
    }
    {
      float defValue = resources.getDimension(R.dimen.textSize_default);
      textSize = typedArray.getDimension(R.styleable.CustomGraph_textSize, defValue);
    }
    {
      float defValue = resources.getDimension(R.dimen.text_margin_default);
      marginText = typedArray.getDimension(R.styleable.CustomGraph_textMargin, defValue);
    }
    typedArray.recycle();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    widthView = MeasureSpec.getSize(widthMeasureSpec);
    heightView = MeasureSpec.getSize(heightMeasureSpec);
    makePointGraph();
  }

  private void makePointGraph() {
    if (maxX != 0 && maxY != 0) {
      paddingX = radiusPoint + lineWidth + textSize;
      paddingY = textSize + marginText + radiusPoint + lineWidth;
      float scaleX = (widthView - 2 * paddingX) / (maxX - 1);
      float scaleY = (heightView - 2 * paddingY) / maxY;
      for (int i = 0; i < maxX; i++) {
        points[i] = new Point((int) (i * scaleX + paddingX),
            (int) (heightView - (paddingY + data[i] * scaleY)));
      }
      if(maxX==1)points[0].set(widthView/2,heightView/2);
    }
  }

  public void setData(int[] points, String[] s) {
    data = points;
    maxX = points.length;
    maxY = 0;
    for (int point : points) {
      if (point > maxY) maxY = point;
    }
    this.points = new Point[maxX];
    texts = s;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawGraph(canvas);
  }

  private void drawGraph(Canvas canvas) {
    if (maxX != 0 && maxY != 0) {
      if (maxX > 1) drawRegionGraph(canvas);
      drawLinesScaleX(canvas);
      drawLineGraph(canvas);
      drawPointGraph(canvas);
      drawTextScaleX(canvas);
    }
  }

  private void drawTextScaleX(Canvas canvas) {
    if (texts != null && maxX == texts.length) {
      paint.setTextSize(textSize);
      paint.setStyle(Paint.Style.FILL);
      paint.setColor(colorText);
      for (int i = 0; i < maxX; i++) {
        canvas.drawText(texts[i], (float) (points[i].x - 0.7 * textSize), heightView, paint);
      }
    }
  }

  private void drawPointGraph(Canvas canvas) {
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(colorPoint);
    for (Point point : points) {
      canvas.drawCircle(point.x, point.y, radiusPoint, paint);
    }
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(colorLinePoint);
    for (Point point : points) {
      canvas.drawCircle(point.x, point.y, radiusPoint, paint);
    }
  }

  private void drawLineGraph(Canvas canvas) {
    path.reset();
    paint.setColor(colorLine);
    for (Point point : points) {
      if (point != points[0]) {
        path.lineTo(point.x, point.y);
      } else {
        path.moveTo(point.x, point.y);
      }
    }
    canvas.drawPath(path, paint);
  }

  private void drawLinesScaleX(Canvas canvas) {
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(colorLineX);
    for (Point point : points) {
      canvas.drawLine(point.x, heightView - paddingY, point.x, point.y, paint);
    }
  }

  private void drawRegionGraph(Canvas canvas) {
    paint.setStyle(Paint.Style.FILL);
    paint.setStrokeWidth(lineWidth);
    paint.setColor(colorUnderLine);
    path.reset();
    path.moveTo(paddingX, heightView - paddingY);
    for (Point point : points) {
      path.lineTo(point.x, point.y);
    }
    path.lineTo(widthView - paddingX, heightView - paddingY);
    path.close();
    canvas.drawPath(path, paint);
  }
}