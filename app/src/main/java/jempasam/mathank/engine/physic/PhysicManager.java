package jempasam.mathank.engine.physic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jempasam.mathank.engine.item.Item;

public class PhysicManager implements Runnable{

    private Map<Item, List<PhysicRegistrement>> registries;
    private Item environnement;

    public PhysicManager(Item environnement){
        registries=new HashMap<>();
        this.environnement=environnement;
    }

    public void register(Item item,PhysicAction action, OnPhysicSimulationEnd sim){

        List<PhysicRegistrement> actions=registries.get(item);
        if(actions==null){
            actions=new ArrayList<>();
            registries.put(item,actions);
        }
        actions.add(new PhysicRegistrement(action,sim));
    }

    public void unregister(Item item,PhysicAction action){

        List<PhysicRegistrement> actions=registries.get(item);
        if(actions!=null){
            actions.removeIf((pr)->pr.action==action);
        }
    }

    public void unregister(Item item){
        registries.put(item,new ArrayList<>());
    }

    public void tick(Item item){
        List<PhysicRegistrement> actions=registries.get(item);
        for(PhysicRegistrement pr : actions){
            if(!pr.action.simulate(item,environnement) && pr.onend!=null){
                pr.onend.simulationEnd(this,item,pr.action);
            }
        }
    }

    public void tickAll(){
        for(Item i : registries.keySet()){
            tick(i);
        }
    }

    @Override
    public void run() {
        tickAll();
    }

    public static interface OnPhysicSimulationEnd{
        void simulationEnd(PhysicManager manager, Item target, PhysicAction action);
    }

    private class PhysicRegistrement {
        public PhysicAction action;
        public OnPhysicSimulationEnd onend;

        public PhysicRegistrement(PhysicAction action, OnPhysicSimulationEnd onend) {
            this.action = action;
            this.onend = onend;
        }
    }

    public static OnPhysicSimulationEnd ONEND_STOP=new OnPhysicSimulationEnd(){
        @Override
        public void simulationEnd(PhysicManager manager, Item target, PhysicAction action) {
            manager.unregister(target,action);
        }
    };
}
