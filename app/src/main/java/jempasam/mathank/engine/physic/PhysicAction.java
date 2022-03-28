package jempasam.mathank.engine.physic;

import jempasam.mathank.engine.item.Item;

public interface PhysicAction {
    /**
     * Simulate physic by moving the given Item, in the given environment Item
     * @return false if given Item have to disappear
     */
    boolean simulate(Item target, Item environment);
}
