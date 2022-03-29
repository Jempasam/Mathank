package jempasam.mathank;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.ElipseItem;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.item.ItemGroup;
import jempasam.mathank.engine.item.NegativeItem;
import jempasam.mathank.engine.physic.GravityPAction;
import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.engine.physic.PhysicManager;
import jempasam.mathank.engine.physic.SlideGravityPAction;
import jempasam.mathank.ihm.drawing.DrawableItem;
import jempasam.mathank.ihm.drawing.DrawableItemList;
import jempasam.mathank.ihm.paint.BitmapPaintBucket;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.mathank.ihm.paint.RandomPaintBucket;
import jempasam.mathank.ihm.view.ItemListView;
import jempasam.swj.data.deserializer.DataDeserializers;
import jempasam.swj.logger.SLogger;
import jempasam.swj.logger.SimpleSLogger;
import jempasam.swj.objectmanager.HashObjectManager;
import jempasam.swj.objectmanager.ObjectManager;
import jempasam.swj.objectmanager.ObjectManagers;
import jempasam.swj.objectmanager.loader.ObjectLoader;
import jempasam.swj.objectmanager.loader.SimpleObjectLoader;
import jempasam.swj.parsing.SimpleValueParser;
import jempasam.swj.parsing.ValueParser;
import jempasam.swj.parsing.ValueParsers;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SLogger logger=new SimpleSLogger(System.out);
    ObjectManager<Item> fieldManager=new HashObjectManager<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logger.log(DataDeserializers.createSGMLLikeBaliseDS(logger).loadFrom(getResources().openRawResource(R.raw.field1)).toString());

        InputStream field=getResources().openRawResource(R.raw.field1);
        SimpleValueParser parser= ValueParsers.createSimpleValueParser();
        parser.add(Box2d.class,(str)->{
            String[] part=str.split(" ");
            logger.log(Arrays.toString(part));
            return new MutableBox2d(Integer.parseInt(part[0]), Integer.parseInt(part[1]), Integer.parseInt(part[2]), Integer.parseInt(part[3]));
        });
        parser.add(Vector2d.class,(str)->{
            String[] part=str.split(" ");
            return new MutableVector2d(Integer.parseInt(part[0]), Integer.parseInt(part[1]));
        });

        ObjectLoader loader= new SimpleObjectLoader(fieldManager,logger,DataDeserializers.createSGMLLikeBaliseDS(logger),parser,Item.class,"jempasam.mathank.engine.item.");
        loader.loadFrom(getResources().openRawResource(R.raw.field1));

        // Paints
        PaintBucket paint=new RandomPaintBucket(Arrays.asList(PaintBucket.colour(0x00ff00),PaintBucket.colour(0x00aa00)));
        PaintBucket playerpaint=new RandomPaintBucket(Arrays.asList(PaintBucket.colour(0x0000ff),PaintBucket.colour(0x0000aa)));

        // Environnement World
        /*ItemGroup map=new ItemGroup(new MutableBox2d(0,0,2000,1000));
        map.add(new ElipseItem(new MutableBox2d(-500, 500, 1000, 1000)));
        map.add(new ElipseItem(new MutableBox2d(100, 600, 800, 800)));
        map.add(new ElipseItem(new MutableBox2d(850, 800, 400, 400)));
        map.add(new ElipseItem(new MutableBox2d(1000, 500, 1500, 1500)));*/
        Item map=fieldManager.get("classic");

        // PlayerMap
        Item player=new ElipseItem(new MutableBox2d(500,0,100,100));

        //Physic
        PhysicManager physicmanager=new PhysicManager(map);
        physicmanager.register(player,new SlideGravityPAction(5),null);

        // Display
        Map<Item,PaintBucket> colors=new HashMap<>();
        colors.put(map, new BitmapPaintBucket(((BitmapDrawable)getDrawable(R.drawable.grass)).getBitmap()));
        colors.put(player, new BitmapPaintBucket(((BitmapDrawable)getDrawable(R.drawable.metal)).getBitmap()));

        //mapview.setImageDrawable(new DrawableItemList(colors, Arrays.asList(map,player), new MutableVector2d(2000,1000), 200));
        ItemListView v=new ItemListView(this,colors,Arrays.asList(map,player),new MutableVector2d(2000,1000),400);
        ((FrameLayout)findViewById(R.id.displayframe)).addView(v);

        Handler looper=new Handler(getMainLooper());
        looper.post(new Runnable() {
            @Override
            public void run() {
                physicmanager.run();
                v.invalidate();
                looper.postDelayed(this,100);
            }
        });
    }
}