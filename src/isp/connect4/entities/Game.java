package isp.connect4.entities;

/**
 * Created with IntelliJ IDEA.
 * User: miul
 * Date: 3/9/14
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {
    private static Game theGame = new Game();
    private Integer curPlayerID;
    public State curState;
    private Integer boardX;
    private Integer boardY;

    private Game() {};

    public static void setBoardSize(int x, int y){
        theGame.boardX = x;
        theGame.boardY = y;
    }

    public static void setInitialState(){
        theGame.curState = new State(theGame.boardX, theGame.boardY);
    }

    public static void setCurrentPlayerID(int id){
        theGame.curPlayerID = id;
    }

    public static int getCurrentPlayerID(){
        return theGame.curPlayerID;
    }

    public void setCurrentState(State state){
        theGame.curState = state;
    }

    public State getCurrentState(){
        return theGame.curState;
    }

    public static Game getInstance(){
        return theGame;
    }

}
