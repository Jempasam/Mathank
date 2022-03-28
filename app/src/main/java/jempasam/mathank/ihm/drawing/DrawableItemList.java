package jempasam.mathank.ihm.drawing;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.mathank.ihm.paint.RandomPaintBucket;

public class DrawableItemList extends Drawable {

    private Map<Item,Drawable> sprites;
    private List<Item> items;
    private Vector2d view;

    public DrawableItemList(Map<Item, Drawable> sprites, List<Item> items, Vector2d view) {
        this.sprites = sprites;
        this.items = items;
        this.view = view;
    }

    public DrawableItemList(Map<Item, PaintBucket> colors, List<Item> items, Vector2d view, int precision){
        Map<Item,Drawable> sprites=new HashMap<>();
        for(Item it : items){
            Drawable d=new BitmapDrawable(DrawableUtils.toBipMap(new DrawableItem(it,precision,colors.get(it)), it.getSize().getX(), it.getSize().getY()));
            sprites.put(it,d);
        }
        this.sprites = sprites;
        this.items = items;
        this.view = view;
    }

    public Map<Item, Drawable> getSprites() {
        return sprites;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        for(Item it : items){
            int x=it.getPos().getX()*getBounds().width()/view.getX()+getBounds().left;
            int y=it.getPos().getY()*getBounds().height()/view.getY()+getBounds().top;
            int sizex=it.getSize().getX()*getBounds().width()/view.getX();
            int sizey=it.getSize().getY()*getBounds().height()/view.getY();
            Drawable d=sprites.get(it);
            d.setBounds(new Rect(x,y,x+sizex,y+sizey));
            d.draw(canvas);
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
