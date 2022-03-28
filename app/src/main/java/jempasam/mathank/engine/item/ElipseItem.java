package jempasam.mathank.engine.item;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;

public class ElipseItem extends BaseItem {

    private Vector2d foyer1;
    private Vector2d foyer2;
    private int d;

    public ElipseItem(Box2d box) {
        super(box);
    }

    @Override
    public boolean doCollide(Vector2d point) {
        int largeur_grandaxe=Math.max(box.getSize().getX(),box.getSize().getY());
        int largeur_petitaxe=Math.min(box.getSize().getX(),box.getSize().getY());

        d=largeur_grandaxe;

        Vector2d center=new MutableVector2d(box.getPos().getX()+box.getSize().getX()/2, box.getPos().getY()+box.getSize().getY()/2);

        int f=(int)Math.sqrt(largeur_grandaxe*largeur_grandaxe-largeur_petitaxe*largeur_petitaxe);

        foyer1=new MutableVector2d(center);
        foyer2=new MutableVector2d(center);

        if(box.getSize().getX()>box.getSize().getY()){
            foyer1.setX(foyer1.getX()+f);
            foyer2.setX(foyer2.getX()-f);
        }
        else{
            foyer1.setY(foyer1.getY()+f);
            foyer2.setY(foyer2.getY()-f);
        }
        return foyer1.distance(point)+foyer2.distance(point)<=d;
    }
}
