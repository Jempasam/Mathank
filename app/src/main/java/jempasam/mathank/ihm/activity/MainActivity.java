package jempasam.mathank.ihm.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import jempasam.mathank.R;
import jempasam.mathank.app.resources.MathankResources;
import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.item.ElipseItem;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.physic.PhysicManager;
import jempasam.mathank.engine.physic.SlideGravityPAction;
import jempasam.mathank.engine.physic.ThrowedPAction;
import jempasam.mathank.ihm.paint.BitmapPaintBucket;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.mathank.ihm.paint.RandomPaintBucket;
import jempasam.mathank.ihm.view.ItemListView;
import jempasam.swj.logger.SLogger;
import jempasam.swj.logger.SimpleSLogger;

import android.os.Handler;
import android.widget.FrameLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SLogger logger=new SimpleSLogger(System.out);
    MathankResources mathankres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mathankres=new MathankResources(logger,this);

        // Objects
        Item world=mathankres.field_managers.get("classic");
        Item player=mathankres.player_managers.get("classic");

        //Physic
        PhysicManager physicmanager=new PhysicManager(world);
        physicmanager.register(player,new ThrowedPAction(30,-30,20,0.001f),null);

        // Display
        Map<Item,PaintBucket> colors=new HashMap<>();
        colors.put(world, mathankres.paint_manager.get("default_field"));
        colors.put(player, mathankres.paint_manager.get("default_player1"));

        //mapview.setImageDrawable(new DrawableItemList(colors, Arrays.asList(map,player), new MutableVector2d(2000,1000), 200));
        ItemListView v=new ItemListView(this,colors,Arrays.asList(world,player),new MutableVector2d(2000,1000),400);
        ((FrameLayout)findViewById(R.id.displayframe)).addView(v);

        Handler looper=new Handler(getMainLooper());
        looper.post(new Runnable() {
            @Override
            public void run() {
                physicmanager.run();
                v.invalidate();
                looper.postDelayed(this,50);
            }
        });
    }
}