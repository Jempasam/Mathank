package jempasam.mathank.ihm.paint;

import android.graphics.Paint;

public interface PaintBucket {
    Paint getPaint(int x, int y, int seed);
    public static Paint colour(int color){
        Paint ret=new Paint();
        ret.setColor(color);
        ret.setAlpha(255);
        return ret;
    }
}
