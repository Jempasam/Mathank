package jempasam.mathank.ihm.drawing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class DrawableUtils {
    public static Bitmap toBipMap(Drawable d, int width, int height){
        Bitmap ret=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(ret);
        d.setBounds(new Rect(0,0,width,height));
        d.draw(c);
        return ret;
    }
}
