package polygonull.noskulls.components;

import java.util.ArrayList;

import polygonull.noskulls.NoSkulls;

/**
 * Created by skerd on 12-Jun-18.
 */

public class Panel {

    private boolean active;
    private int state;
    private int prevState;
    private boolean justFlipped;
    private boolean flipping;

    public Panel() {
        this.active = true;
        this.state = 16;
        this.prevState = 16;
        this.justFlipped = false;
        this.flipping = false;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void changeState() {
        this.state++;
    }

    public int getPrevState() {
        return prevState;
    }

    public void setPrevState(int prevState) {
        this.prevState = prevState;
    }

    public boolean isJustFlipped() {
        return justFlipped;
    }

    public void setJustFlipped(boolean justFlipped) { this.justFlipped = justFlipped; }

    public boolean isFlipping() { return this.justFlipped || (this.state != 0 && this.state != 16); }

}
