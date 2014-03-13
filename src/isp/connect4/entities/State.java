package isp.connect4.entities;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: miul
 * Date: 3/9/14
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class State {
    private static final Integer[] coords = {0, 0}; // {x, y}
//    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * availablePositions should maintain order of elements, with indexes as columns (x - 1)
     */
    private ArrayList<String> availablePositions = new ArrayList<String>(); // fringe
    private HashMap<String, Position> allPositions = new HashMap<String, Position>();
    private ArrayList<HashSet<String>> alignments = new ArrayList<HashSet<String>>();

    /**
     * initialize with empty grid and set the grid's size
     * @param width
     * @param height
     */
    public State(int width, int height){
        // set dimensions
        coords[0] = width;
        coords[1] = height;

        // set all positions as free
        for(int i = 0; i < coords[1]; i++){
            for(int j = 0; j < coords[0]; j++){
                Position p = new Position(j, i);
                String pIndex = Integer.toString(j) + Integer.toString(i);
                this.allPositions.put(pIndex, p);
                // if on the bottom line,
                if(i == 0){
                    // add it to the fringe
                    this.availablePositions.add(pIndex);
                }
            }
        }


    }

    /**
     * used for creating children of states
     * NB: shallow copy
     */
    public State(State original, int playerID, int newX, int newY){
        this.allPositions = original.getAllPositions();
        this.availablePositions = original.getAvailablePositions();
        this.alignments = original.getAlignments();

        // create a new Position object and replace the existing one, to avoid
        // changing the original object referenced from the parent states
        Position freshPos = new Position(newX, newY);
        freshPos.setAssignedToPlayer(playerID);
        this.allPositions.put(freshPos.getID(), freshPos);


        // once a position is occupied, the one above it becomes part of the fringe
        if(newY < coords[1] ){
            Integer posIndex = this.availablePositions.indexOf(freshPos.getID());
            this.availablePositions.set(posIndex, Integer.toString(newX) + Integer.toString(newY + 1));
            reinforceAlignments(freshPos);
        }else{
            // throw an error or something...
        }

    }

    public ArrayList<Integer> checkAlignmentsFor(Position pos){
        ArrayList<Integer> belongsToAlignments = new ArrayList<Integer>();

        return belongsToAlignments;
    }

    public HashMap<String, HashSet<String>> reinforceAlignments(Position pos){
        int startX = pos.getX();
        int startY = pos.getY();
        String posId = pos.getID();

        ArrayList<String> neighbours = new ArrayList<String>();

        HashMap<String, HashSet<String>> trajectories = new HashMap<String, HashSet<String>>();
        trajectories.put("NS", new HashSet<String>());
        trajectories.put("NESW", new HashSet<String>());
        trajectories.put("EW", new HashSet<String>());
        trajectories.put("SENW", new HashSet<String>());

        // N
        if(startY < coords[1] - 1){
            // if not the top-row, there might be a free position on top
            if(getPosition(Integer.toString(startX) + Integer.toString(startY + 1)).isFree()){
                neighbours.add(Integer.toString(startX) + Integer.toString(startY + 1));
            }

            HashSet<String> foundFriends = friendsInPath(startX, startY, 0, 1, pos.getOwnerID(), new HashSet<String>());
            trajectories.put("NS", foundFriends);

            System.out.print('\n');
            System.out.print("N ---->> NS - friends: " + foundFriends.toString());
            System.out.print('\n');

            trajectories.get("NS").add(posId);
        }

        // NE
        if(startY < coords[1] - 1 && startX < coords[0] - 1){
            neighbours.add(Integer.toString(startX + 1) + Integer.toString(startY + 1));

            HashSet<String> foundFriends = friendsInPath(startX, startY, 1, 1, pos.getOwnerID(), new HashSet<String>());
            trajectories.put("NESW", foundFriends);

            System.out.print('\n');
            System.out.print("NE ---->> NESW - friends: " + foundFriends.toString());
            System.out.print('\n');

            trajectories.get("NESW").add(posId);
        }

        // E
        if(startX < coords[0] - 1){
            neighbours.add(Integer.toString(startX + 1) + Integer.toString(startY));

            HashSet<String> foundFriends = friendsInPath(startX, startY, 1, 0, pos.getOwnerID(), new HashSet<String>());
            trajectories.put("EW", foundFriends);

            System.out.print('\n');
            System.out.print("E ---->> EW - friends: " + foundFriends.toString());
            System.out.print('\n');

            trajectories.get("EW").add(posId);
        }

        // SE
        if(startX < coords[0] - 1 && startY > 0){
            neighbours.add(Integer.toString(startX + 1) + Integer.toString(startY - 1));

            HashSet<String> foundFriends = friendsInPath(startX, startY, 1, -1, pos.getOwnerID(), new HashSet<String>());
            trajectories.put("SENW", foundFriends);

            System.out.print('\n');
            System.out.print("SE ---->> SENW - friends: " + foundFriends.toString());
            System.out.print('\n');

            trajectories.get("SENW").add(posId);
        }

        /**
         * these are checked last for opposite-directions sets,
         * so we can go ahead and store the alignments in the big list
         */

        // S
        if(startY > 0){
            neighbours.add(Integer.toString(startX) + Integer.toString(startY - 1));

            HashSet<String> foundFriends = friendsInPath(startX, startY, 0, -1, pos.getOwnerID(), new HashSet<String>());
            if(foundFriends.size() > 0){
                foundFriends.add(posId);
            }

            System.out.print('\n');
            System.out.print("S ---->> NS 2 - friends: " + foundFriends.toString());
            System.out.print('\n');

            trajectories.get("NS").addAll(foundFriends);
        }

        // SW
        if(startX > 0 && startY > 0){
            neighbours.add(Integer.toString(startX - 1) + Integer.toString(startY - 1));

            HashSet<String> foundFriends = friendsInPath(startX, startY, -1, -1, pos.getOwnerID(), new HashSet<String>());
            if(foundFriends.size() > 0){
                foundFriends.add(posId);
            }

            System.out.print('\n');
            System.out.print("SW ---->> NESW 2 - friends: " + foundFriends.toString());
            System.out.print('\n');

            trajectories.get("NESW").addAll(foundFriends);
        }

        // W
        if(startX > 0){
            neighbours.add(Integer.toString(startX - 1) + Integer.toString(startY));

            HashSet<String> foundFriends = friendsInPath(startX, startY, -1, 0, pos.getOwnerID(), new HashSet<String>());
            if(foundFriends.size() > 0){
                foundFriends.add(posId);
            }

            System.out.print('\n');
            System.out.print("W ---->> EW 2 - friends: " + foundFriends.toString());
            System.out.print('\n');

            trajectories.get("EW").addAll(foundFriends);
        }

        // NW
        if(startX > 0 && startY < coords[1] - 1){
            neighbours.add(Integer.toString(startX - 1) + Integer.toString(startY + 1));

            HashSet<String> foundFriends = friendsInPath(startX, startY, -1, 1, pos.getOwnerID(), new HashSet<String>());
            if(foundFriends.size() > 0){
                foundFriends.add(posId);
            }

            System.out.print('\n');
            System.out.print("NW ---->> SENW 2 - friends: " + foundFriends.toString());
            System.out.print('\n');

            trajectories.get("SENW").addAll(foundFriends);
        }

        if(trajectories.get("NS").size() > 1){
            this.alignments.add(trajectories.get("NS"));
        }

        if(trajectories.get("NESW").size() > 1){
            this.alignments.add(trajectories.get("NESW"));
        }

        if(trajectories.get("EW").size() > 1){
            this.alignments.add(trajectories.get("EW"));
        }

        if(trajectories.get("SENW").size() > 1){
            this.alignments.add(trajectories.get("SENW"));
        }


        return trajectories;
    }

    private HashSet<String> friendsInPath(int x, int y, int deltaX, int deltaY, int playerID, HashSet<String> foundSoFar){
        HashSet<String> knapSack = foundSoFar;
        if(coordsInRange(x + deltaX, y + deltaY)){
            Position p = this.getPosition(Integer.toString(x + deltaX) + Integer.toString(y + deltaY));
            System.out.print('\n');
            System.out.print(x);
            System.out.print(y);
            System.out.print(" ( " + playerID + " ) ");
            System.out.print(" -> ");
            System.out.print(p.getID());
            System.out.print(" ( " + p.getOwnerID() + " ) ");
            System.out.print(p.getOwnerID() == playerID);

            if(p.getOwnerID() == playerID){
                System.out.print(" is friend; -> ");
                System.out.print('\n');

                // add it to foundSoFar
                knapSack.add(p.getID());
                friendsInPath(p.getX(), p.getY(), deltaX, deltaY, playerID, knapSack);
            }else{
                clearSubAlignment(foundSoFar);
            }
        }else{
            clearSubAlignment(foundSoFar);
        }

        return knapSack;
    }

    private void clearSubAlignment(HashSet<String> subAlign){
        // remove this line from connections, if previously inserted
        if(subAlign.size() > 1){
            for(int i=0; i<this.alignments.size(); i++){
                if (this.alignments.get(i).containsAll(subAlign)){
                    System.out.print("------------ clearSubAlignment ---------------");
                    System.out.print('\n');
                    System.out.print("subAlign " + i + " : " + subAlign.toString());
                    System.out.print('\n');
                    System.out.print("<<<<<<<<<<<< clearSubAlignment ");
                    this.alignments.remove(i);
                }
            }
        }
    }

    private boolean coordsInRange(int x, int y){
        return x >= 0 && x < coords[0] && y >= 0 && y < coords[1];
    }

    public ArrayList<HashSet<String>> getAlignments(){
        return this.alignments;
    }

    public ArrayList<HashSet<String>> getAlignments(int length){
        ArrayList<HashSet<String>> filtered = new ArrayList<HashSet<String>>();
        for(HashSet<String> ali : this.alignments){
            if(ali.size() == length){
                filtered.add(ali);
            }
        }
        System.out.print("-----------  getAlignments(4)  ------------ = ");
        System.out.print(filtered.toString());
        System.out.print('\n');
        System.out.print("-----------  this.availablePositions :");
        System.out.print(this.availablePositions.toString());
        System.out.print('\n');
        System.out.print("-----------  this.alignments :");
        System.out.print(this.alignments.toString());
        System.out.print('\n');

        System.out.print('\n');
        return filtered;
    }

    public HashMap<String, Position> getAllPositions(){
        return this.allPositions;
    }

    public Position getPosition(String id){
        return this.allPositions.get(id);
    }

    public ArrayList<String> getAvailablePositions(){
        return this.availablePositions;
    }

    public String getAvailablePosition(int col){
        return this.availablePositions.get(col);
    }

    public boolean hasEmptyPositions(){
        return this.availablePositions.size() > 0;
    }

    private void addNewPosition(int x, int y, Position newPosition){
        this.allPositions.put(Integer.toString(x) + Integer.toString(y), newPosition);
    }

}
