package jempasam.mathank.app.resources;

import android.content.Context;

import jempasam.mathank.R;
import jempasam.mathank.app.file.Loaders;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.swj.logger.SLogger;
import jempasam.swj.objectmanager.HashObjectManager;
import jempasam.swj.objectmanager.ObjectManager;

public class MathankResources {

    public ObjectManager<Item> field_managers=new HashObjectManager<>();
    public ObjectManager<Item> player_managers=new HashObjectManager<>();
    public ObjectManager<Item> object_manager=new HashObjectManager<>();

    public ObjectManager<PaintBucket> paint_manager=new HashObjectManager<>();

    public MathankResources(SLogger logger, Context context){
        // Field
        Loaders.createItemLoader(field_managers,logger).loadFrom(context.getResources().openRawResource(R.raw.field));

        // Player Models
        Loaders.createItemLoader(player_managers,logger).loadFrom(context.getResources().openRawResource(R.raw.players));

        //World Objects
        Loaders.createItemLoader(object_manager,logger).loadFrom(context.getResources().openRawResource(R.raw.objects));

        //Paint Buckets
        Loaders.createPaintLoader(paint_manager,logger,context).loadFrom(context.getResources().openRawResource(R.raw.paints));
    }
}
