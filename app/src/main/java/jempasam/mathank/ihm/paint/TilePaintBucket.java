package jempasam.mathank.ihm.paint;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class TilePaintBucket implements PaintBucket{

    private List<Paint> paints;

    @LoadableParameter
    private int squareNumber;

    public TilePaintBucket(List<Paint> paints, int squareNumber){
        this.paints=paints;
        this.squareNumber=squareNumber;
    }


    @Override
    public Paint getPaint(float x, float y, int seed) {
        squareNumber=4;
        return paints.get(((int)(x*squareNumber)+(int)(y*squareNumber))%paints.size());
    }

    // Loadable
    private TilePaintBucket(){
        paints=new ArrayList<>();
        System.out.println("[["+paints+"]]");
    }

    @LoadableParameter
    private void color(Paint p){
        paints.add(p);
    }

}
