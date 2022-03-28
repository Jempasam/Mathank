package jempasam.mathank;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.item.ElipseItem;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.item.ItemGroup;
import jempasam.mathank.engine.item.NegativeItem;
import jempasam.mathank.engine.physic.GravityPAction;
import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.ihm.drawing.DrawableItem;
import jempasam.mathank.ihm.drawing.DrawableItemList;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.mathank.ihm.paint.RandomPaintBucket;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView mapview=(ImageView)findViewById(R.id.environnement);

        // Paints
        PaintBucket paint=new RandomPaintBucket(List.of(PaintBucket.colour(0x00ff00),PaintBucket.colour(0x00aa00)));
        PaintBucket playerpaint=new RandomPaintBucket(List.of(PaintBucket.colour(0x0000ff),PaintBucket.colour(0x0000aa)));

        // Environnement World
        ItemGroup map=new ItemGroup(new MutableBox2d(0,0,2000,1000));
        map.add(new ElipseItem(new MutableBox2d(-500, 500, 1000, 1000)));
        map.add(new ElipseItem(new MutableBox2d(100, 600, 800, 800)));
        map.add(new ElipseItem(new MutableBox2d(850, 800, 400, 400)));
        map.add(new ElipseItem(new MutableBox2d(1000, 500, 1500, 1500)));

        // PlayerMap
        Item player=new ElipseItem(new MutableBox2d(500,0,100,100));

        // Display
        Map<Item,PaintBucket> colors=new HashMap<>();
        colors.put(map, new RandomPaintBucket(List.of(PaintBucket.colour(0x00ff00),PaintBucket.colour(0x00aa00))));
        colors.put(player, new RandomPaintBucket(List.of(PaintBucket.colour(0x0000ff),PaintBucket.colour(0x0000aa))));

        mapview.setImageDrawable(new DrawableItemList(colors, List.of(map,player), new MutableVector2d(2000,1000), 200));

        PhysicAction playerphysic=new GravityPAction(10);

        Handler looper=new Handler(getMainLooper());
        looper.postDelayed(new Runnable() {
            @Override
            public void run() {
                playerphysic.simulate(player,map);
                mapview.invalidate();

                looper.postDelayed(this,100);
            }
        },100);
    }
}