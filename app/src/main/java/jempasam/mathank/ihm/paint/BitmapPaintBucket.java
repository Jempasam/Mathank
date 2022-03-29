package jempasam.mathank.ihm.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;

import java.util.List;
import java.util.Random;

import jempasam.mathank.MainActivity;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class BitmapPaintBucket implements PaintBucket{

    @LoadableParameter
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

    // Loadable
    private BitmapPaintBucket(){};
}
