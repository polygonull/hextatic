package polygonull.noskulls.components;

public class Counter {

    float initialCount;
    float count;
    boolean initiallyIncreasing;
    boolean increasing;
    float step;
    float power;
    float topThreshold;
    float bottomThreshold;
    boolean freeze;

    public Counter(float initialCount, float step, boolean initiallyIncreasing, float topThreshold, float bottomThreshold) {
        this.initialCount = initialCount;
        this.count = initialCount;
        this.step = step;
        this.power = 1;
        this.initiallyIncreasing = initiallyIncreasing;
        this.increasing = initiallyIncreasing;
        this.topThreshold = topThreshold;
        this.bottomThreshold = bottomThreshold;
        this.freeze = false;
    }

    public Counter(float initialCount, float step, float power, boolean initiallyIncreasing, float topThreshold, float bottomThreshold) {
        this(initialCount, step, initiallyIncreasing, topThreshold, bottomThreshold);
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

    public float step() {
        if(freeze) return count;
        if(increasing) {
            count = count + step;
            if(power != 1) {
                count = (float) Math.pow(count, power);
            }
            if(count >= topThreshold) {
                if(bottomThreshold != -1) {
                    increasing = false;
                } else if(topThreshold != -1) {
                    reset();
                }
            }
        } else {
            count = count - step;
            if(count <= bottomThreshold) {
                increasing = true;
            }
        }

        return count;
    }

    public float stepDown() {

        if(freeze) return count;

        count = count - step;

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
