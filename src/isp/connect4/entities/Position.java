package isp.connect4.entities;

import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: miul
 * Date: 3/9/14
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class Position {
    private String ID;
    private int assignedToPlayer = 0;
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;

        this.ID = Integer.toString(x) + Integer.toString(y);
    }

    public void setAssignedToPlayer(int playerID){
        this.assignedToPlayer = playerID;
    }

    public Boolean isFree(){
        return this.assignedToPlayer > 0;
    }

    public Integer getOwnerID(){
        return this.assignedToPlayer;
    }

    public Integer getX(){
        return this.x;
    }

    public Integer getY(){
        return this.y;
    }

    public String getID(){
        return this.ID;
    }

}
