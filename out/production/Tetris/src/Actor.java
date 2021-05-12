import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class Actor extends ImageView {
    public Actor() {

    }

    public abstract void act();

    public void move(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public World getWorld() {
        return (World) getParent();
    }

    public double getWidth() {
        return getBoundsInLocal().getWidth();
    }

    public double getHeight() {
        return getBoundsInLocal().getHeight();
    }

    public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls) {
        ArrayList<A> intersectingObjects = new ArrayList<>();
        ObservableList<Node> actors = getParent().getChildrenUnmodifiable();
        for(int i = 0; i < actors.size(); i++) {
            if(actors.get(i) != this && cls.isInstance(actors.get(i)) && intersects(actors.get(i).getBoundsInLocal())) {
                intersectingObjects.add((A) actors.get(i));
            }
        }
        return intersectingObjects;
    }

    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
        A intersectingObject = null;
        ObservableList<Node> actors = getParent().getChildrenUnmodifiable();
        for(int i = 0; i < actors.size(); i++) {
            if(actors.get(i) != this && cls.isInstance(actors.get(i)) && intersects(actors.get(i).getBoundsInLocal())) {
                intersectingObject = (A) actors.get(i);
                break;
            }
        }
        return intersectingObject;
    }
}