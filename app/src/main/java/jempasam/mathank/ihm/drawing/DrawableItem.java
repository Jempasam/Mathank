package jempasam.mathank.ihm.drawing;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.ihm.paint.PaintBucket;

public class DrawableItem extends Drawable {
    private Item item;
    private int precision;
    private PaintBucket paint;

    public DrawableItem(Item item, int precision, PaintBucket paint) {
        this.item = item;
        this.precision=precision;
        this.paint=paint;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int xsize=(getBounds().right-getBounds().left)/precision+1;
        int ysize=(getBounds().bottom-getBounds().top)/precision+1;
        int paintseed= (int)(Math.random()*Integer.MAX_VALUE);

        for(int x=0; x<precision; x++){
            for(int y=0; y<precision; y++){

                Paint p=paint.getPaint(x*1f/precision,y*1f/precision,paintseed);

                int xitem=item.getSize().getX()*x/precision+item.getPos().getX();
                int yitem=item.getSize().getY()*y/precision+item.getPos().getY();
                if(item.doCollide(new MutableVector2d(xitem,yitem))) {
                    int xdisp=(getBounds().right-getBounds().left)*x/precision+getBounds().left;
                    int ydisp=(getBounds().bottom-getBounds().top)*y/precision+getBounds().top;
                    canvas.drawRect(new Rect(xdisp,ydisp,xdisp+xsize,ydisp+ysize),p);
                }
            }
        }
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
