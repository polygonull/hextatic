package polygonull.noskulls.components;

import java.util.ArrayList;

import polygonull.noskulls.NoSkulls;

public class SkullAndHeartPanel extends Panel implements Cloneable {

    private int x;
    private int y;
    private ArrayList<SkullAndHeartPanel> neighbours = new ArrayList<>();

    public SkullAndHeartPanel(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<SkullAndHeartPanel> getNeighbours() {
        return neighbours;
    }

    public void populateNeighbours(SkullAndHeartPanel[][] panels) {

        if(this.x == 0 && this.y == 0) {
            neighbours.add(panels[this.x+1][this.y]);
            neighbours.add(panels[this.x][this.y+1]);
            neighbours.add(panels[this.x+1][this.y+1]);
        } else if(this.x == 0 && this.y == NoSkulls.BOARD_WIDTH_COUNT - 1) {
            neighbours.add(panels[this.x+1][this.y]);
            neighbours.add(panels[this.x][this.y-1]);
            neighbours.add(panels[this.x+1][this.y-1]);
        } else if(this.x == NoSkulls.BOARD_HEIGHT_COUNT - 1 && this.y == 0) {
            neighbours.add(panels[this.x-1][this.y]);
            neighbours.add(panels[this.x][this.y+1]);
            neighbours.add(panels[this.x-1][this.y+1]);
        } else if(this.x == NoSkulls.BOARD_HEIGHT_COUNT - 1 && this.y == NoSkulls.BOARD_WIDTH_COUNT - 1) {
            neighbours.add(panels[this.x-1][this.y]);
            neighbours.add(panels[this.x][this.y-1]);
            neighbours.add(panels[this.x-1][this.y-1]);
        } else if(this.x == 0) {
            neighbours.add(panels[this.x+1][this.y]);
            neighbours.add(panels[this.x][this.y-1]);
            neighbours.add(panels[this.x][this.y+1]);
            neighbours.add(panels[this.x+1][this.y-1]);
            neighbours.add(panels[this.x+1][this.y+1]);
        } else if(this.y == 0) {
            neighbours.add(panels[this.x][this.y+1]);
            neighbours.add(panels[this.x-1][this.y]);
            neighbours.add(panels[this.x+1][this.y]);
            neighbours.add(panels[this.x-1][this.y+1]);
            neighbours.add(panels[this.x+1][this.y+1]);
        } else if(this.x == NoSkulls.BOARD_HEIGHT_COUNT - 1) {
            neighbours.add(panels[this.x-1][this.y]);
            neighbours.add(panels[this.x][this.y-1]);
            neighbours.add(panels[this.x][this.y+1]);
            neighbours.add(panels[this.x-1][this.y-1]);
            neighbours.add(panels[this.x-1][this.y+1]);
        } else if(this.y == NoSkulls.BOARD_WIDTH_COUNT - 1) {
            neighbours.add(panels[this.x][this.y-1]);
            neighbours.add(panels[this.x-1][this.y]);
            neighbours.add(panels[this.x+1][this.y]);
            neighbours.add(panels[this.x-1][this.y-1]);
            neighbours.add(panels[this.x+1][this.y-1]);
        } else {
            neighbours.add(panels[this.x-1][this.y]);
            neighbours.add(panels[this.x+1][this.y]);
            neighbours.add(panels[this.x][this.y-1]);
            neighbours.add(panels[this.x][this.y+1]);
            neighbours.add(panels[this.x-1][this.y-1]);
            neighbours.add(panels[this.x-1][this.y+1]);
            neighbours.add(panels[this.x+1][this.y-1]);
            neighbours.add(panels[this.x+1][this.y+1]);
        }

    }

}
