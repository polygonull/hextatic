package polygonull.noskulls.components;

import com.badlogic.gdx.Gdx;

public class Counter {

    private float initialCount;
    private float count;
    private boolean initiallyIncreasing;
    private boolean increasing;
    private float step;
    private float power;
    private float topThreshold;
    private float bottomThreshold;
    private boolean freeze;
    private boolean timer;

    public Counter(float initialCount, float step, boolean initiallyIncreasing, float topThreshold, float bottomThreshold, boolean timer) {
        this.initialCount = initialCount;
        this.count = initialCount;
        this.step = step;
        this.power = 1;
        this.initiallyIncreasing = initiallyIncreasing;
        this.increasing = initiallyIncreasing;
        this.topThreshold = topThreshold;
        this.bottomThreshold = bottomThreshold;
        this.freeze = false;
        this.timer = timer;
    }

    public Counter(float initialCount, float step, float power, boolean initiallyIncreasing, float topThreshold, float bottomThreshold, boolean timer) {
        this(initialCount, step, initiallyIncreasing, topThreshold, bottomThreshold, timer);
        this.power = power;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public boolean isIncreasing() {
        return increasing;
    }

    public void setIncreasing(boolean increasing) {
        this.increasing = increasing;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public float getTopThreshold() {
        return topThreshold;
    }

    public void setTopThreshold(float topThreshold) {
        this.topThreshold = topThreshold;
    }

    public float getBottomThreshold() {
        return bottomThreshold;
    }

    public void setBottomThreshold(float bottomThreshold) {
        this.bottomThreshold = bottomThreshold;
    }

    public float getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(float initialCount) {
        this.initialCount = initialCount;
    }

    public boolean isInitiallyIncreasing() {
        return initiallyIncreasing;
    }

    public void setInitiallyIncreasing(boolean initiallyIncreasing) {
        this.initiallyIncreasing = initiallyIncreasing;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public boolean isFrozen() {
        return freeze;
    }

    public void setFrozen(boolean freeze) {
        this.freeze = freeze;
    }

    public boolean isTimer() {
        return timer;
    }

    public void setTimer(boolean timer) {
        this.timer = timer;
    }

    public float step() {
        if(freeze) return count;
        if(increasing) {
            if(timer) {
                //count = count + (step + Gdx.graphics.getDeltaTime() * step);
                count = count + step;
            } else {
                count = count + step;
            }
            if(power != 1) {
                count = (float) Math.pow(count, power);
            }
            if(count > topThreshold) {
                if(bottomThreshold != -1) {
                    increasing = false;
                } else if(topThreshold != -1) {
                    reset();
                }
            }
        } else {
            if(timer) {
                //count = count - (step + Gdx.graphics.getDeltaTime() * step);
                count = count - step;
            } else {
                count = count - step;
            }
            if(count < bottomThreshold) {
                increasing = true;
            }
        }

        return count;
    }

    public float stepDown() {

        if(freeze) return count;

        if(timer) {
            count = count - (step + Gdx.graphics.getDeltaTime() * step);
        } else {
            count = count - step;
        }

        return count;

    }

    public void reset() {
        count = initialCount;
        increasing = initiallyIncreasing;
    }

    public void freeze() {
        freeze = true;
    }

    public void unfreeze() {
        freeze = false;
    }

}
