package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;



import java.io.IOException;
import java.util.*;


public class GraController {


    public   Rectangle floor;
    public  Rectangle rightWall;
    public  Rectangle leftWall;
    public  Pane pane;
    public Rectangle line1;
    public Rectangle line2,line3,line4,line5,line6,line7,line8,line9,line10,line11,line12,line13,line14,line15,line16,line17,line18,line19,line20;
    public Label pointsText;
    public Rectangle endLine;
    private Player player;
    private AnimationTimer timer;
    private Vector<Vector<GameObject>> stayObjects=new Vector<>();
    private boolean isStart=false;
    private Rectangle linie[]=new Rectangle[20];
    private int points;


    public GraController(){
        for(int i=0;i<20;i++){
            stayObjects.add(new Vector<>());
        }
        points=0;
    }

//Adds one block to the scene
    public void addGameObject(GameObject object, double x, double y) {

        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        pane.getChildren().add(object.getView());

    }


    private void clearkLine(int i){
        for (GameObject object : stayObjects.get(i)
                ) {
            pane.getChildren().remove(object.getView());
        }
        stayObjects.get(i).clear();
    }
    //return number  of lines where is 10 blocks
    private int howMuchLine(){
        int i=0;
        for(int j=0;j<20;j++){
            if(stayObjects.get(j).size()==10)
                i++;
            else if(stayObjects.get(j).size()==0)
                break;
        }
        return i;
    }
    private void gameOver() {
            Label over=new Label();
            over.setText("GAME OVER");
            over.setTranslateX(150);
            over.setTranslateY(200);
            over.setFont(Font.font(35));
            pane.getChildren().add(over);
    }


    // Chooses random Tetrimino
    @NotNull
    private Player random(){

        switch (new Random().nextInt(7)) {
            case 0:
                return new TetrimoI();

            case 1:
                return new TetriminoO();
            case 2:
                return new TetriminoT();
            case 3:
                return new TetriminoZ();
            case 4:
                return new TetriminoL();
            case 5:
                return new TetriminoJ();
        }
        return new TetriminoS();
    }


@FXML

    public void press(KeyEvent keyEvent) {
            if(keyEvent.getCode()== KeyCode.DOWN){
                player.getList().forEach(GameObject :: moveDown);
                if(player.objectColision(stayObjects) || player.isWallColision(rightWall,leftWall,floor))
                player.getList().forEach(GameObject::moveUp);
            }
            else if(keyEvent.getCode()== KeyCode.UP){
                player.Rotate();
            }
            else if(keyEvent.getCode()==KeyCode.RIGHT){
                player.getList().forEach(GameObject::moveRight);
                    if(player.isWallColision(rightWall,leftWall,floor)||player.objectColision(stayObjects))
                    player.getList().forEach(GameObject::moveLeft);
            }
            else if(keyEvent.getCode()==KeyCode.LEFT){
                player.getList().forEach(GameObject::moveLeft);
                if(player.isWallColision(rightWall,leftWall,floor)||player.objectColision(stayObjects))
                    player.getList().forEach(GameObject::moveRight);
            }
}

    public void backToMenu(ActionEvent event) throws IOException {
        stayObjects.clear();
        if(isStart) {
            timer.stop();
        }
        Parent parent=FXMLLoader.load(this.getClass().getResource("sample.fxml"));
        Scene scene=new Scene(parent);
        Stage mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainStage.setScene(scene);
        mainStage.show();
    }


    public void start(ActionEvent event) {
        isStart=true;
        player=random();
        linie[0]=line1;
        linie[1]=line2;
        linie[2]=line3;
        linie[3]=line4;
        linie[4]=line5;
        linie[5]=line6;
        linie[6]=line7;
        linie[7]=line8;
        linie[8]=line9;
        linie[9]=line10;
        linie[10]=line11;
        linie[11]=line12;
        linie[12]=line13;
        linie[13]=line14;
        linie[14]=line15;
        linie[15]=line16;
        linie[16]=line17;
        linie[17]=line18;
        linie[18]=line19;
        linie[19]=line20;

         timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                    onUpdate();

            }
        };
        timer.start();
    }


    private void onUpdate() {


        player.getList().forEach(GameObject::update);

        if (player.objectColision(stayObjects) || player.isWallColision(rightWall,leftWall,floor)) {
            player.getList().forEach(GameObject::setNostay);
            for (GameObject object : player.getList()
                    ) {
                for (int i = 0; i < 20; i++) {
                    if (object.isStop(linie[i])) {
                        stayObjects.get(i).add(object);
                    }
                    else if(object.isStop(endLine)){
                        gameOver();
                        timer.stop();
                    }

                }
            }
            player.getList().clear();

            while (howMuchLine() > 0) {
                for (int i = 0; i < 20; i++) {

                    if (stayObjects.get(i).size() == 10) {
                        clearkLine(i);
                        points+=20;
                        pointsText.setText("Points: "+points);
                        pointsText.setRotate(pointsText.getRotate()+2);
                        for (int j = i + 1; j < 20; j++) {
                            if (stayObjects.get(j).isEmpty()) {
                                break;
                            }
                            System.out.println(stayObjects.get(j).get(0).getTranslateY());
                            for (GameObject object : stayObjects.get(j)
                                    ) {
                                object.setTranslateY(object.getTranslateY() + 30);
                            }
                            for (GameObject object : stayObjects.get(j)
                                    ) {
                                stayObjects.get(j - 1).add(object);
                            }
                            stayObjects.get(j).clear();
                        }


                    }
                }
            }
        }


            if (player.getList().isEmpty()) {
                player = random();
            }
    }

    private class TetriminoO extends Player{

        TetriminoO(){
            int x=150;
            int y=-30;
            Rectangle rectangle=new Rectangle(28,28,Color.AQUA);
            rectangle.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(rectangle));
            addGameObject(Tetrimino.get(0),x,y);
            rectangle=new Rectangle(28,28,Color.AQUA);
            rectangle.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(rectangle));
            addGameObject(Tetrimino.get(1),x+30,y);
            rectangle=new Rectangle(28,28,Color.AQUA);
            rectangle.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(rectangle));
            addGameObject(Tetrimino.get(2),x,y+30);
            rectangle=new Rectangle(28,28,Color.AQUA);
            rectangle.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(rectangle));
            addGameObject(Tetrimino.get(3),x+30,y+30);

        }


        @Override
        public void Rotate() {

        }


   }

    private class TetrimoI extends Player {

        private TetrimoI() {
            int y=0;
            for (int i=0;i<4;i++) {
                Rectangle r=new Rectangle(28,28, Color.LEMONCHIFFON);
                r.setStroke(Color.BLACK);
                GameObject d=new GameObject(r);
                Tetrimino.add(d);
                addGameObject(d,150,y);
                y-=30;
            }
            RotateBlock=Tetrimino.get(1);
        }

        public void Rotate(){
            double x=-30;
            double y=30;
            int i=0;
            double xT[]=new double[5];
            double yT[]= new double[5] ;

            for(GameObject object:Tetrimino){

                xT[i]=object.getTranslateX()-RotateBlock.getTranslateX();
                yT[i]=object.getTranslateY()-RotateBlock.getTranslateY();
                i++;
                if(RotateBlock.equals(object)) {
                    y -= 30;
                    x+=30;
                    continue;
                }
                if(RotateBlock.getTranslateX()==object.getTranslateX()){
                    object.getView().setTranslateY(RotateBlock.getTranslateY());
                    object.setTranslateX(RotateBlock.getTranslateX() + x);
                    x += 30;
                }

               else  if(RotateBlock.getTranslateY()==object.getTranslateY()){
                    object.setTranslateY(RotateBlock.getTranslateY() + y);
                    object.setTranslateX(RotateBlock.getTranslateX());
                    y -= 30;
                }
            }
            if(isWallColision(rightWall,leftWall,floor)||objectColision(stayObjects)){
                previousPosition(xT,yT);
            }
        }
    }

    private class TetriminoT extends Player{

        List<double[]> coordinatesX=new ArrayList<>();
        List<double[]> coordinatesY=new ArrayList<>();
        int i;
        public TetriminoT(){
            int x=150;
            int y=-30;
            i=0;
            Rectangle r=new Rectangle(28,28,Color.ORCHID);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(0),x-30,y);
            r=new Rectangle(28,28,Color.ORCHID);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(1),x,y);
            r=new Rectangle(28,28,Color.ORCHID);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(2),x+30,y);
            r=new Rectangle(28,28,Color.ORCHID);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(3),x,y+30);
            RotateBlock=Tetrimino.get(1);

            coordinatesX.add(new double[]{0,0,0,30});
            coordinatesX.add(new double[]{0,0,0,-30});
            coordinatesX.add(new double[]{+30,0,-30,0});
            coordinatesX.add(new double[] {-30,0,30,0});

            coordinatesY.add(new double[]{-30,0,30,0});
            coordinatesY.add(new double[]{30,0,-30,0});
            coordinatesY.add(new double[]{0,0,0,30});
            coordinatesY.add(new double[]{0,0,0,-30});
        }


        @Override
        public void Rotate() {
            int j=0;
            double [] tabX=coordinatesX.get(i);
            double[]tabY=coordinatesY.get(i);
            for (GameObject object:Tetrimino
                 ) {
                object.setTranslateX(RotateBlock.getTranslateX()+tabX[j]);
                object.setTranslateY(RotateBlock.getTranslateY()+tabY[j]);
                j++;
            }
            if(isWallColision(rightWall,leftWall,floor)||objectColision(stayObjects)){
                if(i!=0)
                previousPosition(coordinatesX.get(i-1),coordinatesY.get(i-1));
                else
                    previousPosition(coordinatesX.get(3),coordinatesY.get(3));
            }else {
                i++;
            }

            if(i>3){
                i=0;
            }
            }
        }

    private class TetriminoZ extends Player{
           private List<double[]> coordinatesX=new ArrayList<>();
            private List<double[]> coordinatesY=new ArrayList<>();
            int i=1;
        public TetriminoZ(){
            int x=150;
            int y=-30;
            Rectangle r=new Rectangle(28,28,Color.LIGHTGREEN);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(0),x-30,y);

            r=new Rectangle(28,28,Color.LIGHTGREEN);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(1),x,y);

            r=new Rectangle(28,28,Color.LIGHTGREEN);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(2),x,y+30);

            r=new Rectangle(28,28,Color.LIGHTGREEN);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(3),x+30,y+30);
            RotateBlock=Tetrimino.get(1);

            coordinatesX.add(new double[]{-30,0,0,30});
            coordinatesX.add(new double[]{0,0,-30,-30});
            coordinatesY.add(new double[]{0,0,30,30});
            coordinatesY.add(new double[]{-30,0,0,30});
        }
            @Override
            public void Rotate() {

                int j=0;
                double [] tabX=coordinatesX.get(i);
                double[]tabY=coordinatesY.get(i);
                for (GameObject object:Tetrimino
                        ) {
                    object.setTranslateX(RotateBlock.getTranslateX()+tabX[j]);
                    object.setTranslateY(RotateBlock.getTranslateY()+tabY[j]);
                    j++;
                }
                if(isWallColision(rightWall,leftWall,floor)||objectColision(stayObjects)){
                    if(i!=0)
                        previousPosition(coordinatesX.get(i-1),coordinatesY.get(i-1));
                    else
                        previousPosition(coordinatesX.get(1),coordinatesY.get(1));
                }else {
                    i++;
                }

                if(i>1){
                    i=0;
                }
            }


        }

    private class TetriminoS extends Player{

            private List<double[]> coordinatesX=new ArrayList<>();
            private List<double[]> coordinatesY=new ArrayList<>();
            int i=1;
            public TetriminoS(){
                int x=150;
                int y=-30;
                Rectangle r=new Rectangle(28,28,Color.LIGHTPINK);
                r.setStroke(Color.BLACK);
                Tetrimino.add(new GameObject(r));
                addGameObject(Tetrimino.get(0),x+30,y);

                r=new Rectangle(28,28,Color.LIGHTPINK);
                r.setStroke(Color.BLACK);
                Tetrimino.add(new GameObject(r));
                addGameObject(Tetrimino.get(1),x,y);

                r=new Rectangle(28,28,Color.LIGHTPINK);
                r.setStroke(Color.BLACK);
                Tetrimino.add(new GameObject(r));
                addGameObject(Tetrimino.get(2),x,y+30);

                r=new Rectangle(28,28,Color.LIGHTPINK);
                r.setStroke(Color.BLACK);
                Tetrimino.add(new GameObject(r));
                addGameObject(Tetrimino.get(3),x-30,y+30);

                RotateBlock=Tetrimino.get(1);

                coordinatesX.add(new double[]{30,0,0,-30});
                coordinatesX.add(new double[]{0,0,+30,+30});
                coordinatesY.add(new double[]{0,0,30,30});
                coordinatesY.add(new double[]{-30,0,0,30});
            }
            @Override
            public void Rotate() {

                int j=0;
                double [] tabX=coordinatesX.get(i);
                double[]tabY=coordinatesY.get(i);
                for (GameObject object:Tetrimino
                        ) {
                    object.setTranslateX(RotateBlock.getTranslateX()+tabX[j]);
                    object.setTranslateY(RotateBlock.getTranslateY()+tabY[j]);
                    j++;
                }
                if(isWallColision(rightWall,leftWall,floor)||objectColision(stayObjects)){
                    if(i!=0)
                        previousPosition(coordinatesX.get(i-1),coordinatesY.get(i-1));
                    else
                        previousPosition(coordinatesX.get(1),coordinatesY.get(1));
                }else {
                    i++;
                }

                if(i>1){
                    i=0;
                }
            }

        }

    private class TetriminoL extends Player{
        private List<double[]> coordinatesX=new ArrayList<>();
        private List<double[]> coordinatesY=new ArrayList<>();
        int i=1;
        public TetriminoL(){
            int x=150;
            int y=-30;
            Rectangle r=new Rectangle(28,28,Color.YELLOWGREEN);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(0),x,y-30);

            r=new Rectangle(28,28,Color.YELLOWGREEN);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(1),x,y);

            r=new Rectangle(28,28,Color.YELLOWGREEN);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(2),x,y+30);

            r=new Rectangle(28,28,Color.YELLOWGREEN);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(3),x+30,y+30);
            RotateBlock=Tetrimino.get(1);

            coordinatesX.add(new double[]{0,0,0,30});
            coordinatesX.add(new double[]{-30,0,+30,-30});
            coordinatesX.add(new double[]{0,0,0,-30});
            coordinatesX.add(new double[]{-30,0,30,30});

            coordinatesY.add(new double[]{30,0,-30,30});
            coordinatesY.add(new double[]{0,0,0,30});
            coordinatesY.add(new double[]{30,0,-30,-30});
            coordinatesY.add(new double[]{0,0,0,-30});
        }
        @Override
        public void Rotate() {

            int j=0;
            double [] tabX=coordinatesX.get(i);
            double[]tabY=coordinatesY.get(i);
            for (GameObject object:Tetrimino
                    ) {
                object.setTranslateX(RotateBlock.getTranslateX()+tabX[j]);
                object.setTranslateY(RotateBlock.getTranslateY()+tabY[j]);
                j++;
            }
            if(isWallColision(rightWall,leftWall,floor)||objectColision(stayObjects)){
                if(i!=0)
                    previousPosition(coordinatesX.get(i-1),coordinatesY.get(i-1));
                else
                    previousPosition(coordinatesX.get(3),coordinatesY.get(3));
            }else {
                i++;
            }

            if(i>3){
                i=0;
            }
        }

    }

    private class TetriminoJ extends Player{
        private List<double[]> coordinatesX=new ArrayList<>();
        private List<double[]> coordinatesY=new ArrayList<>();
        int i=1;
        public TetriminoJ(){
            int x=150;
            int y=-30;
            Rectangle r=new Rectangle(28,28,Color.GOLD);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(0),x,y-30);

            r=new Rectangle(28,28,Color.GOLD);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(1),x,y);

             r=new Rectangle(28,28,Color.GOLD);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(2),x,y+30);

            r=new Rectangle(28,28,Color.GOLD);
            r.setStroke(Color.BLACK);
            Tetrimino.add(new GameObject(r));
            addGameObject(Tetrimino.get(3),x-30,y+30);
            RotateBlock=Tetrimino.get(1);

            coordinatesX.add(new double[]{0,0,0,-30});
            coordinatesX.add(new double[]{-30,0,+30,-30});
            coordinatesX.add(new double[]{0,0,0,30});
            coordinatesX.add(new double[]{-30,0,30,30});

            coordinatesY.add(new double[]{30,0,-30,30});
            coordinatesY.add(new double[]{0,0,0,-30});
            coordinatesY.add(new double[]{30,0,-30,-30});
            coordinatesY.add(new double[]{0,0,0,+30});
        }
        @Override
        public void Rotate() {

            int j=0;
            double [] tabX=coordinatesX.get(i);
            double[]tabY=coordinatesY.get(i);
            for (GameObject object:Tetrimino
                    ) {
                object.setTranslateX(RotateBlock.getTranslateX()+tabX[j]);
                object.setTranslateY(RotateBlock.getTranslateY()+tabY[j]);
                j++;
            }
            if(isWallColision(rightWall,leftWall,floor)||objectColision(stayObjects)){
                if(i!=0)
                    previousPosition(coordinatesX.get(i-1),coordinatesY.get(i-1));
                else
                    previousPosition(coordinatesX.get(3),coordinatesY.get(3));
            }else {
                i++;
            }

            if(i>3){
                i=0;
            }
        }
    }
}



