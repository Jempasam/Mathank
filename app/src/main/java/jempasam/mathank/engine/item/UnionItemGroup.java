package jempasam.mathank.engine.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class UnionItemGroup extends BaseItem implements  Iterable<Item>{

    private List<Item> items;

    public UnionItemGroup(Box2d box) {
        super(box);
        items=new ArrayList<>();
    }

    private UnionItemGroup(){super(null);}

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
    public boolean doCollide(Vector2d point){
        for(Item it: items){
            if(/*it.getBox().containPoint(point) && */!it.doCollide(point))return false;
        }
        return true;
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
