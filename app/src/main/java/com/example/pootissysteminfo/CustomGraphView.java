package com.example.pootissysteminfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class CustomGraphView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private List<Float> dataPoints = new ArrayList<>();

    public CustomGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.parseColor("#4CAF50")); // Verde DevCheck
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);
    }

    public void addDataPoint(float point) {
        dataPoints.add(point);
        if (dataPoints.size() > 40) dataPoints.remove(0);
        invalidate(); // Redibuja
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (dataPoints.size() < 2) return;
        path.reset();
        float widthStep = (float) getWidth() / 39;
        for (int i = 0; i < dataPoints.size(); i++) {
            float x = i * widthStep;
            float y = getHeight() - (dataPoints.get(i) / 100f * getHeight());
            if (i == 0) path.moveTo(x, y);
            else path.lineTo(x, y);
        }
        canvas.drawPath(path, paint);
    }
}