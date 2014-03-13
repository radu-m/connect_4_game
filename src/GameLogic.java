import isp.connect4.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GameLogic implements IGameLogic {
    private int x = 0;
    private int y = 0;
    private int playerID;

    private Game liveGame;

    
    public GameLogic() {
        //TODO Write your implementation for this method

    }
	
    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        this.playerID = playerID;

        //TODO Write your implementation for this method
        System.out.print("-----------  initializeGame  ------------");
        System.out.print('\n');

        this.liveGame = Game.getInstance();

        State initState = new State(x, y);

        this.liveGame.setCurrentState(initState);
    }
	
    public Winner gameFinished() {


        //TODO Write your implementation for this method
        ArrayList<HashSet<String>> line = this.liveGame.getCurrentState().getAlignments(4);
        Integer winnerID = 0;
        State curState = this.liveGame.getCurrentState();
        Winner winner;

        if(line.size() > 0){
            winnerID = curState.getPosition(line.get(0).iterator().next()).getOwnerID();
        }

        switch (winnerID){
            case 0:
                    if(curState.hasEmptyPositions()){
                        winner = Winner.NOT_FINISHED;
                    }else{
                        winner = Winner.TIE;
                    }
                    break;
            case 1: winner = Winner.PLAYER1;
                    break;
            case 2: winner = Winner.PLAYER2;
                    break;
            default: winner = Winner.NOT_FINISHED;
                    break;
        }

        System.out.print("-----------  gameFinished  ------------");
        System.out.print(winnerID);
//        System.out.print('\n');
        System.out.print(this.liveGame.getCurrentState());
        System.out.print('\n');

        return winner;
    }


    public void insertCoin(int column, int playerID) {
        //TODO Write your implementation for this method

//        System.out.print('\n');
//        System.out.print("----------  insertCoin  ------------- ");
//        System.out.print(column);
//        System.out.print(" -- ");
//        System.out.print(playerID);
//        System.out.print('\n');
//        System.out.print("...... inserted coin ..........");

        Position targetPos = this.liveGame.curState.getPosition(this.liveGame.curState.getAvailablePosition(column));

//        System.out.print('\n');
//        System.out.print("targetPos obj: " + targetPos);
//        System.out.print('\n');
//        System.out.print("liveGame Old curState obj: " + this.liveGame.curState);
//        System.out.print('\n');
//        System.out.print("-----------");

        /**
         * discard current state of game and replace it with a new one
         */
        this.liveGame.curState = new State(this.liveGame.curState, playerID, targetPos.getX(), targetPos.getY());

//        State newState = new State(this.liveGame.curState, playerID, targetPos.getX(), targetPos.getY());
//        this.liveGame.curState = newState;
//        Position p = this.liveGame.curState.getPosition(Integer.toString(targetPos.getX()) + Integer.toString(targetPos.getY()));
//        System.out.print('\n');
//        System.out.print("targetPos obj: " + targetPos);
//        System.out.print('\n');
//        System.out.print("newState obj: " + newState);
//        System.out.print('\n');
//        System.out.print("liveGame obj: " + this.liveGame.curState);
//        System.out.print('\n');
//        System.out.print("id: " + p.getID() + "; ownerID: " + p.getOwnerID() + "; isFree: " + p.isFree());
//        System.out.print('\n');
//        System.out.print(".....................");
//        System.out.print('\n');

    }

    public int decideNextMove() {
        System.out.print('\n');
        System.out.print("-----------  decideNextMove  ------------");
        System.out.print(this.liveGame.getCurrentState());

        //TODO Write your implementation for this method
        return (int)(Math.random() * (this.x + 1));
    }

}
