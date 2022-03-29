package jempasam.mathank.ihm.paint;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class RandomPaintBucket implements PaintBucket{

    private List<Paint> paints;

    public RandomPaintBucket(List<Paint> paints){
        this.paints=paints;
    }


    @Override
    public Paint getPaint(float x, float y, int seed) {
        Random random=new Random();
        return paints.get(random.nextInt(paints.size()));
    }

    // Loadable
    private RandomPaintBucket(){
        paints=new ArrayList<>();
    };

    @LoadableParameter
    private void color(Paint p){
        paints.add(p);
    }

}
