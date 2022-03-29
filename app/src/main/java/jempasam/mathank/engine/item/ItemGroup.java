package jempasam.mathank.engine.item;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jempasam.mathank.engine.containers.Box2d;
import jempasam.mathank.engine.containers.MutableBox2d;
import jempasam.mathank.engine.containers.Vector2d;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class ItemGroup extends BaseItem implements  Iterable<Item>{

    private List<Item> items;

    public ItemGroup(Box2d box) {
        super(box);
        items=new ArrayList<>();
    }

    private ItemGroup(){
        this(null);
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
    public boolean doCollide(Vector2d point){
        for(Item it: items){
            if(/*it.getBox().containPoint(point) && */it.doCollide(point))return true;
        }
        return false;
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
