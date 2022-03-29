package jempasam.mathank.ihm.paint;

import android.graphics.Bitmap;
import android.graphics.Paint;

import java.util.List;
import java.util.Random;

public class BitmapPaintBucket implements PaintBucket{

    private Bitmap bitmap;

    public BitmapPaintBucket(Bitmap bitmap){
        this.bitmap=bitmap;
    }


    @Override
    public Paint getPaint(float x, float y, int seed) {
        int pxcolor=bitmap.getPixel((int)(x*bitmap.getWidth()),(int)(y*bitmap.getHeight()));
        Paint p=new Paint();
        p.setColor(pxcolor);
        p.setAlpha(255);
        return p;
    }
}
