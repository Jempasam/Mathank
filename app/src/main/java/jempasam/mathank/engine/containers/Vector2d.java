package jempasam.mathank.engine.containers;

public interface Vector2d {

    int getX();
    int getY();
    void setX(int x);
    void setY(int y);

    default int distance(Vector2d vec){
        int xoffset=vec.getX()-getX();
        int yoffset=vec.getY()-getY();
        return (int)Math.sqrt(xoffset*xoffset+yoffset*yoffset);
    }

    default Vector2d add(Vector2d vec){
        return new MutableVector2d(getX()+vec.getX(),vec.getY()+vec.getY());
    }

    default Vector2d center(Vector2d vec){
        return new MutableVector2d((vec.getX()+getX())/2, (vec.getY()+getY())/2);
    }
}
