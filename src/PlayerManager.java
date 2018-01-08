import javafx.geometry.Pos;
import java.lang.Math.*;

import java.util.*;

public class PlayerManager {
    private int startX, startY, endX, endY;
    private List<Position> usedPositions;
    private SelectorList<Player> players;

    public Player createPlayer(String name, String shortName) {
        Position randomPosition = randomArea(new Position(startX, startY), new Position(endX, endY), usedPositions);
        Player player =  new Player(randomPosition, name, shortName);
        usedPositions.add(randomPosition);
        players.addNodeAtEnd(player);

        return player;
    }

    public PlayerManager(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        players = new SelectorList<>();
        usedPositions = new ArrayList<>();
    }

    // https://stackoverflow.com/a/47232576
    public Position randomArea(Position topLeft, Position bottomRight, List<Position> excepts) {
        Random ran = new Random();
        Position origin = topLeft;
        Position translatedBottomRight = bottomRight.subtract(origin);
        int width = translatedBottomRight.x + 1;
        int height = translatedBottomRight.y + 1;

        // TODO Check if Range is 0 (No free fields anymore)(random.nextInt raises exception)
        int range = width * height - excepts.size();
        int randNum = ran.nextInt(range);
        int posIndex;

        Collections.sort(excepts); // sort excluding values in ascending order
        int i=0;
        for(Position except : excepts) {
            Position exceptTranslate = except.subtract(origin).add(new Position(1,1));
            int exceptNum = getIndex(width, exceptTranslate);
            if(randNum < exceptNum - i){
                posIndex = randNum + i;
                int x = getXFromIndex(width, posIndex);
                int y = getYFromIndex(width, posIndex);
                return new Position(x, y);
            }
            i++;
        }

        posIndex = randNum + i;
        int x = getXFromIndex(width, posIndex);
        int y = getYFromIndex(width, posIndex);
        return new Position(x, y);
    }

    public Player getCurrentPlayer() {
        Node curNode = players.getCurrentNode();
        if(curNode != null) {
            return (Player)curNode.data;
        }

        return null;
    }

    public Player selectNextPlayer() {
        Node nextNode = players.selectNextNode();
        if(nextNode != null) {
            return (Player)nextNode.data;
        }

        return null;
    }

    public int getIndex(int width, Position pos) {
        return (width * (pos.y - 1) + pos.x) - 1 ;
    }

    public int getXFromIndex(int width, int index) {
        return (index % width) + 1;
    }

    public int getYFromIndex(int width, int index) {
        return (int)((index + width) / width);
    }
}
