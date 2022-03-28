package jempasam.mathank.ihm.paint;

import android.graphics.Paint;

import java.util.List;
import java.util.Random;

public class RandomPaintBucket implements PaintBucket{

    private List<Paint> paints;

    public RandomPaintBucket(List<Paint> paints){
        this.paints=paints;
    }


    @Override
    public Paint getPaint(int x, int y, int seed) {
        Random random=new Random();
        return paints.get(random.nextInt(paints.size()));
    }
}
