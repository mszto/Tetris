package sample;



import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by dkoby on 06.07.2017.
 */
public abstract class Player{
    protected List<GameObject> Tetrimino=new ArrayList<>();
    protected GameObject RotateBlock;
    public  List<GameObject> getList() {
        return Tetrimino;
    }

    public abstract void Rotate();
    protected void previousPosition(double xT[],double yT[]){
        int i=0;
        for (GameObject object:Tetrimino){
            object.getView().setTranslateX(RotateBlock.getTranslateX()+xT[i]);
            object.getView().setTranslateY(RotateBlock.getTranslateY()+yT[i]);
            i++;
        }
    }

    public boolean objectColision(Vector<Vector<GameObject>> linie){

        if(linie==null)
            return false;

        for(Vector<GameObject> ob:linie) {
            for (GameObject obj : Tetrimino
                    ) {

                    for (GameObject object : ob
                            ) {
                        if (obj.isStop(object)) {
                            return true;
                        }
                    }

            }
        }

        return false;
    }

    public boolean isWallColision(Rectangle rightWall, Rectangle leftWall, Rectangle floor){

        for (GameObject object:Tetrimino
                ) {

            if(object.isStop(rightWall)) {
                return true;
            }
            if(object.isStop(leftWall)){
                return true;
            }
            if(object.isStop(floor)){
                return true;
            }
        }
        return false;
    }

}
