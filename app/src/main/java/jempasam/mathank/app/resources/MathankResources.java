package jempasam.mathank.app.resources;

import android.content.Context;

import java.io.InputStream;

import jempasam.mathank.R;
import jempasam.mathank.app.file.Loaders;
import jempasam.mathank.app.game.init.GameInfos;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.engine.physic.PhysicManager;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.swj.logger.SLogger;
import jempasam.swj.objectmanager.HashObjectManager;
import jempasam.swj.objectmanager.ObjectManager;

public class MathankResources {

    public ObjectManager<Item> objects=new HashObjectManager<>();
    public ObjectManager<GameInfos> games=new HashObjectManager<>();
    public ObjectManager<PaintBucket> paints=new HashObjectManager<>();
    public ObjectManager<PhysicAction> physics=new HashObjectManager<>();

    public MathankResources(SLogger logger, Context context){
        InputStream paint=context.getResources().openRawResource(R.raw.paints);
        InputStream wobject=context.getResources().openRawResource(R.raw.objects);
        InputStream game=context.getResources().openRawResource(R.raw.game);
        InputStream physic=context.getResources().openRawResource(R.raw.physic);

        //Paint Buckets
        Loaders.createPaintLoader(paints, this,logger,context).loadFrom(paint);

        //Physic Infos
        Loaders.createPhysicActionManager(physics, this,logger,context).loadFrom(physic);

        //World Objects
        Loaders.createItemLoader(objects, this,logger,context).loadFrom(wobject);

        //Game Infos
        Loaders.createGameManager(games, this,logger,context).loadFrom(game);
    }
}
