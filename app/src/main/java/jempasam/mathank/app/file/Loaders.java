package jempasam.mathank.app.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import java.util.Arrays;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.swj.data.deserializer.DataDeserializers;
import jempasam.swj.logger.SLogger;
import jempasam.swj.objectmanager.ObjectManager;
import jempasam.swj.objectmanager.loader.ObjectLoader;
import jempasam.swj.objectmanager.loader.SimpleObjectLoader;
import jempasam.swj.parsing.SimpleValueParser;
import jempasam.swj.parsing.ValueParsers;

public class Loaders {
    public static ObjectLoader<Item> createItemLoader(ObjectManager<Item> manager, SLogger logger){
        //  Value Parser
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

        // Loader
        ObjectLoader loader= new SimpleObjectLoader(manager,logger,DataDeserializers.createSGMLLikeBaliseDS(logger),parser,Item.class,"jempasam.mathank.engine.item.");

        return loader;
    }

    public static ObjectLoader<PaintBucket> createPaintLoader(ObjectManager<PaintBucket> manager, SLogger logger, Context context){
        //  Value Parser
        SimpleValueParser parser= ValueParsers.createSimpleValueParser();
        parser.add(Bitmap.class,(str)->{
            return ((BitmapDrawable)context.getResources().getDrawable(context.getResources().getIdentifier(str,"drawable",context.getPackageName()))).getBitmap();
        });
        parser.add(Paint.class,(str)->{
            return PaintBucket.colour(Integer.parseInt(str,16));
        });

        // Loader
        ObjectLoader<PaintBucket> loader= new SimpleObjectLoader(manager,logger,DataDeserializers.createSGMLLikeBaliseDS(logger),parser, PaintBucket.class,"jempasam.mathank.ihm.paint.");

        return loader;
    }
}
