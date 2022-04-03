package jempasam.mathank.ihm.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import jempasam.mathank.R;
import jempasam.mathank.app.game.ingame.InGameInfos;
import jempasam.mathank.app.game.init.GameInfos;
import jempasam.mathank.app.resources.MathankResources;
import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.mathank.engine.item.ElipseItem;
import jempasam.mathank.engine.item.Item;
import jempasam.mathank.engine.mexpression.MExpressionUtils;
import jempasam.mathank.engine.physic.MExpressionPAction;
import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.engine.physic.PhysicManager;
import jempasam.mathank.engine.physic.SlideGravityPAction;
import jempasam.mathank.engine.physic.ThrowedPAction;
import jempasam.mathank.ihm.fragment.EquationKeyboard;
import jempasam.mathank.ihm.paint.BitmapPaintBucket;
import jempasam.mathank.ihm.paint.PaintBucket;
import jempasam.mathank.ihm.paint.RandomPaintBucket;
import jempasam.mathank.ihm.view.GameView;
import jempasam.mathank.ihm.view.ItemListView;
import jempasam.mexpression.tree.MExpression;
import jempasam.swj.logger.SLogger;
import jempasam.swj.logger.SimpleSLogger;

import android.os.Handler;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    SLogger logger=new SimpleSLogger(System.out);
    MathankResources mathankres;
    boolean turn=true;
    Item player1;
    Item player2;
    Item shot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mathankres=new MathankResources(logger,this);

        GameInfos infos=null;
        Random rand=new Random();
        int aaa=rand.nextInt(mathankres.games.size());
        Iterator<Map.Entry<String,GameInfos>> it=mathankres.games.iterator();
        for(int i=0; i<=aaa; i++)infos=it.next().getValue();

        InGameInfos game=InGameInfos.startGame(infos,300);

        GameView v=new GameView(this,game);
        ((FrameLayout)findViewById(R.id.displayframe)).addView(v);
        v.setOnItemTouch(new GameView.OnItemTouchListener() {
            @Override
            public boolean onAction(MotionEvent event, Item target) {
                System.out.println(target);
                return true;
            }
        });

        EquationKeyboard keyboard=new EquationKeyboard(game.infos().getKeys());
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.keyboard,keyboard);
        transaction.commit();

        player1=game.getFromId("player1");
        player2=game.getFromId("player2");
        shot=game.getFromId("shot");

        keyboard.setOnShoot(new EquationKeyboard.OnShoot() {
            @Override
            public void onShoot(MExpression mexpression) {
                Vector2d shootstart=null;
                if(turn)shootstart=new MutableVector2d(player1.getPos().getX()+player1.getSize().getX()+50,player1.getPos().getY());
                else shootstart=new MutableVector2d(player2.getPos().getX()-50,player2.getPos().getY());

                keyboard.clear();
                shot.setPos(shootstart);
                game.physic().register(shot, new MExpressionPAction(shot, mexpression, 20, turn ? 1 : -1), new PhysicManager.OnPhysicSimulationEnd() {
                    @Override
                    public void simulationEnd(PhysicManager manager, Item target, PhysicAction action) {
                        for(String k : game.infos().getKeys())keyboard.addKey(k);
                        manager.unregister(target);
                        if(player1.doCollide(target.getBox().center())||player2.doCollide(target.getBox().center())){
                            Intent sendIntent = new Intent(Intent.ACTION_SEND);
                            startActivity(sendIntent);
                            //finish();
                        }
                    }
                });

                turn=!turn;
            }

            @Override
            public void onBadExpression() {

            }
        });

        Handler looper=new Handler(getMainLooper());
        looper.post(new Runnable() {
            @Override
            public void run() {
                game.tick();
                v.invalidate();
                looper.postDelayed(this,100);
            }
        });
    }
}