package jempasam.mathank.app.game.init;

import jempasam.mathank.engine.physic.PhysicAction;
import jempasam.mathank.engine.physic.PhysicManager;

public class PhysicActionSetup {

    private PhysicAction action;

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
}
