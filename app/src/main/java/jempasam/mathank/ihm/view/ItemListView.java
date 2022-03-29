package jempasam.mathank.ihm.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.ihm.drawing.DrawableItemList;
import jempasam.mathank.ihm.paint.PaintBucket;

public class ItemListView extends ViewGroup {

    private ImageView display;
    private List<Item> items;
    private Vector2d view;
    private OnItemTouchListener onItemTouch;

    public OnItemTouchListener getOnItemTouch() {
        return onItemTouch;
    }

    public void setOnItemTouch(OnItemTouchListener onItemTouch) {
        this.onItemTouch = onItemTouch;
    }

    public ItemListView(Context context, Map<Item, PaintBucket> colors, List<Item> items, Vector2d view, int precision) {
        super(context);

        this.items=items;
        this.view=view;

        display=new ImageView(context);
        display.setImageDrawable(new DrawableItemList(colors,items,view,precision));
        addView(display);
    }

    @Override
    public void invalidate() {
        display.invalidate();
        super.invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        left+=getPaddingLeft();
        top+=getPaddingTop();
        right+=getPaddingRight();
        bottom+=getPaddingBottom();

        display.layout(left,top,right,bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret=false;
        if(onItemTouch!=null){
            int lwidth=getWidth()-getPaddingLeft()-getPaddingRight();
            int lheight=getHeight()-getPaddingBottom()-getPaddingTop();

            int width=(int)(event.getX()*view.getX()/lwidth);
            int height=(int)(event.getY()*view.getY()/lheight);

            Vector2d touchpos=new MutableVector2d(width,height);

            for(Item it: items){
                if(it.getBox().containPoint(touchpos) && it.doCollide(touchpos)){
                    ret=ret||onItemTouch.onAction(event,it);
                }
            }
        }

        return super.onTouchEvent(event)||ret;
    }

    public static interface OnItemTouchListener{
        public boolean onAction(MotionEvent event, Item target);
    }
}
