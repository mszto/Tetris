package sample;

import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.awt.*;

/**
 * Created by dkoby on 06.07.2017.
 */
public class GameObject {

    private Node view;
    private Point2D piont= new Point2D(0,0.6);
    private boolean stay=false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject object = (GameObject) o;

        if (view != null ? !view.equals(object.view) : object.view != null) return false;
        return piont != null ? piont.equals(object.piont) : object.piont == null;
    }

    @Override
    public int hashCode() {
        int result = view != null ? view.hashCode() : 0;
        result = 31 * result + (piont != null ? piont.hashCode() : 0);
        return result;
    }

    public GameObject(Node view){
        this.view=view;
    }
    public Point2D getPiont() {
        return piont;
    }

    public boolean getStay(){
        return stay;
    }

    public void setNostay(){
        stay=true;
    }

    public void setPiont(Point2D piont) {
        this.piont = piont;
    }


    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }

    public void rotateRight(double x){
        view.setTranslateX(x);
    }

    public void rotateLeft(){
        view.setRotate(view.getRotate()-10);
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + piont.getX());
        view.setTranslateY(view.getTranslateY() + piont.getY());
    }
    public void moveDown(){
        view.setTranslateY(view.getTranslateY()+30);
    }
    public void moveUp(){
        view.setTranslateY(view.getTranslateY()-30);
    }

    public double getTranslateX(){
        return getView().getTranslateX();
    }

    public double getTranslateY(){
        return getView().getTranslateY();
    }

    public void setTranslateX(double x){
        getView().setTranslateX(x);
    }

    public  void setTranslateY(double y){
        getView().setTranslateY(y);
    }
    public void moveRight(){
        view.setTranslateX(view.getTranslateX()+30);
    }

    public void moveLeft(){
        view.setTranslateX(view.getTranslateX()-30);
    }

    public double getRotate() {
        return view.getRotate();
    }

    public boolean isEnd(){
        if(getView().getLayoutY()>=600)
            return true;
        else
            return false;
    }

    public boolean isStop(GameObject other){
           return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    public boolean isStop(Node r){
        return getView().getBoundsInParent().intersects(r.getBoundsInParent());
    }
}
