package jempasam.mathank.app.game.init;

import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.engine.physic.PhysicManager;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;

@Loadable
public class PhysicActionSetup {

    @LoadableParameter
    private PhysicAction action;

    @LoadableParameter
    private PhysicManager.OnPhysicSimulationEnd onend;

    public PhysicActionSetup(PhysicAction action, PhysicManager.OnPhysicSimulationEnd onend) {
        this.action = action;
        this.onend = onend;
    }

    public PhysicAction getAction() {
        return action;
    }

    public PhysicManager.OnPhysicSimulationEnd getOnend() {
        return onend;
    }

    /* LOADABLE */
    private PhysicActionSetup(){
        onend=null;
        action=null;
    }
}
