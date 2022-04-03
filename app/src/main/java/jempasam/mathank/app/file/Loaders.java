package jempasam.mathank.app.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jempasam.mathank.app.game.init.GameInfos;
import jempasam.mathank.app.resources.MathankResources;
import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.mexpression.MExpressionUtils;
import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.mexpression.tree.MExpression;
import jempasam.mexpression.tree.builder.MExpressionBuilder;
import jempasam.mexpression.tree.builder.MExpressionTerm;
import jempasam.mexpression.tree.serializer.MExpressionSerializer;
import jempasam.swj.data.deserializer.DataDeserializers;
import jempasam.swj.logger.SLogger;
import jempasam.swj.objectmanager.ObjectManager;
import jempasam.swj.objectmanager.loader.ObjectLoader;
import jempasam.swj.objectmanager.loader.SimpleObjectLoader;
import jempasam.swj.parsing.SimpleValueParser;
import jempasam.swj.parsing.ValueParsers;
import jempasam.swj.textanalyzis.tokenizer.Tokenizer;
import jempasam.swj.textanalyzis.tokenizer.impl.InputStreamSimpleTokenizer;

public class Loaders {
    public static ObjectLoader<Item> createItemLoader(ObjectManager<Item> manager, MathankResources res, SLogger logger, Context context){
        //  Value Parser
        SimpleValueParser parser= createValueSerializer(res,logger,context);

        // Loader
        ObjectLoader loader= new SimpleObjectLoader(manager,logger,DataDeserializers.createSGMLLikeBaliseDS(logger),parser,Item.class,"jempasam.mathank.engine.item.");

        return loader;
    }

    public static ObjectLoader<PaintBucket> createPaintLoader(ObjectManager<PaintBucket> manager, MathankResources res, SLogger logger, Context context){
        //  Value Parser
        SimpleValueParser parser= createValueSerializer(res,logger,context);

        // Loader
        ObjectLoader<PaintBucket> loader= new SimpleObjectLoader(manager,logger,DataDeserializers.createSGMLLikeBaliseDS(logger),parser, PaintBucket.class,"jempasam.mathank.ihm.paint.");

        return loader;
    }

    public static ObjectLoader<GameInfos> createGameManager(ObjectManager<GameInfos> manager, MathankResources res, SLogger logger, Context context){
        //  Value Parser
        SimpleValueParser parser= createValueSerializer(res,logger,context);

        // Loader
        ObjectLoader<GameInfos> loader= new SimpleObjectLoader(manager,logger,DataDeserializers.createJSONLikeStrobjoDS(logger),parser, GameInfos.class,"jempasam.mathank.app.game.init.");

        return loader;
    }

    public static ObjectLoader<PhysicAction> createPhysicActionManager(ObjectManager<PhysicAction> manager, MathankResources res, SLogger logger, Context context){
        //  Value Parser
        SimpleValueParser parser= createValueSerializer(res,logger,context);

        // Loader
        ObjectLoader<PhysicAction> loader= new SimpleObjectLoader(manager,logger,DataDeserializers.createSGMLLikeBaliseDS(logger),parser, PhysicAction.class,"jempasam.mathank.engine.physic.");

        return loader;
    }

    private static SimpleValueParser createValueSerializer(MathankResources res, SLogger logger, Context context){
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
        parser.add(MExpression.class,(str)->{
            try {
                MExpression ret=MExpressionUtils.parse(str);
                System.err.println(ret.getVisual());
                return ret;
            }catch (Exception e){
                System.err.println("In Equation \""+str+"\":");
                e.printStackTrace();
                return null;
            }
        });
        parser.add(Bitmap.class,(str)->{
            return ((BitmapDrawable)context.getResources().getDrawable(context.getResources().getIdentifier(str,"drawable",context.getPackageName()))).getBitmap();
        });
        parser.add(Paint.class,(str)->{
            return PaintBucket.colour(Integer.parseInt(str,16));
        });
        parser.add(PaintBucket.class, res.paints::get);
        parser.add(Item.class, res.objects::get);
        parser.add(PhysicAction.class, res.physics::get);
        parser.add(GameInfos.class, res.games::get);

        return parser;
    }
}
