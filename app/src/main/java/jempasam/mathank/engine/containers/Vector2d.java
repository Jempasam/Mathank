package jempasam.mathank.engine.containers;

public interface Vector2d {

    public static Class<?> defaultSubclass(){ return Vector2d.class; }

    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    default int distance(Vector2d vec){
        int xoffset=vec.getX()-getX();
        int yoffset=vec.getY()-getY();
        return (int)Math.sqrt(xoffset*xoffset+yoffset*yoffset);
    }
}
