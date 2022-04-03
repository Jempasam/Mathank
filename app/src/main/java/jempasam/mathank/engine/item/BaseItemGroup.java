package jempasam.mathank.engine.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableVector2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

public abstract class BaseItemGroup extends BaseItem implements  Iterable<Item>{

    protected List<Item> items;
    private Vector2d basesize;

    protected BaseItemGroup(Box2d box) {
        super(box);
        basesize=getSize();
        items=new ArrayList<>();
    }

    protected BaseItemGroup(){
        super(null);
        items=new ArrayList<>();
    }

    @LoadableParameter
    public void add(Item item){
        items.add(item);
    }

    public boolean remove(Item item){
        return items.remove(item);
    }

    public int count(Item item){
        return items.size();
    }

    public Item get(int i){
        return items.get(i);
    }

    @Override
    public boolean doCollide(Vector2d point) {
        Vector2d npoint=new MutableVector2d((point.getX()- getPos().getX())*basesize.getX()/getSize().getX(),(point.getY()-getPos().getY())*basesize.getY()/getSize().getY());
        return doChildsCollide(npoint);
    }

    public abstract boolean doChildsCollide(Vector2d point);

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    @LoadableParameter
    protected void init(){
        basesize=getSize();
        System.out.println("TEST");
    }

    public BaseItemGroup clone() throws CloneNotSupportedException {
        BaseItemGroup ret=(BaseItemGroup)super.clone();
        ret.items=new ArrayList<>(items);
        ret.basesize=new MutableVector2d(basesize);
        return ret;
    }
}
