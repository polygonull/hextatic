package polygonull.noskulls.components;

public class Counter {

    private int initialCount;
    private int count;
    private boolean initiallyIncreasing;
    private boolean isIncreasing;
    private int step;
    private int power;
    private boolean isRepeating;
    private boolean isDoubleRepeating;
    private int repeats;
    private int maxRepeats;
    private int floor;
    private int ceiling;
    private boolean isFrozen;
    private boolean isCounter;
    private int scale;

    public Counter(int initialCount, int step, boolean initiallyIncreasing, boolean isRepeating, boolean isDoubleRepeating, int maxRepeats, int ceiling, int floor, boolean isFrozen, boolean isCounter, int scale) {
        this.initialCount = initialCount;
        this.count = initialCount;
        this.initiallyIncreasing = initiallyIncreasing;
        this.isIncreasing = initiallyIncreasing;
        this.step = step;
        this.isRepeating = isRepeating;
        this.isDoubleRepeating = isDoubleRepeating;
        this.repeats = 0;
        this.maxRepeats = maxRepeats;
        this.ceiling = ceiling;
        this.floor = floor;
        this.isFrozen = isFrozen;
        this.isCounter = isCounter;
        this.scale = scale;
    }

    public Counter(int initialCount, int step, boolean initiallyIncreasing, int ceiling, int floor, boolean isFrozen, boolean isCounter, int scale) {
        this(initialCount, step, initiallyIncreasing, false, false, 0, ceiling, floor, isFrozen, isCounter, scale);
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public float getCount() {
        return (float) count / scale;
    }

    public int getRealCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isInitiallyIncreasing() {
        return initiallyIncreasing;
    }

    public void setInitiallyIncreasing(boolean initiallyIncreasing) {
        this.initiallyIncreasing = initiallyIncreasing;
    }

    public boolean isIncreasing() {
        return isIncreasing;
    }

    public void setIncreasing(boolean increasing) {
        isIncreasing = increasing;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }

    public boolean isDoubleRepeating() {
        return isDoubleRepeating;
    }

    public void setDoubleRepeating(boolean doubleRepeating) {
        isDoubleRepeating = doubleRepeating;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public int getMaxRepeats() {
        return maxRepeats * 2;
    }

    public int getRealMaxRepeats() {
        return maxRepeats;
    }

    public void setMaxRepeats(int maxRepeats) {
        this.maxRepeats = maxRepeats;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCeiling() {
        return ceiling;
    }

    public void setCeiling(int ceiling) {
        this.ceiling = ceiling;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public boolean isCounter() {
        return isCounter;
    }

    public void setCounter(boolean counter) {
        isCounter = counter;
    }

    public int stepUp() {

        if (isFrozen) return count;

        count = count + step;

        if (count >= ceiling) {
            if (isRepeating) {
                isIncreasing = false;
            }
        }

        if (isRepeating) {
            if (isDoubleRepeating) {
                if (count == initialCount) {
                    repeats++;
                }
            } else {
                if (count == initialCount) {
                    repeats = repeats + 2;
                }
            }
        }

        return count;

    }

    public int stepDown() {

        if (isFrozen) return count;

        count = count - step;

        if (count <= floor) {
            if (isRepeating) {
                isIncreasing = true;
            }
        }

        if (isRepeating) {
            if (isDoubleRepeating) {
                if (count == initialCount) {
                    repeats++;
                }
            } else {
                if (count == initialCount) {
                    repeats = repeats + 2;
                }
            }
        }

        return count;

    }

    public int step() {

        if (isFrozen) return count;

        if (isIncreasing) {

            count = count + step;

            if (count > ceiling) {
                if (isRepeating) {
                    isIncreasing = false;
                }
            }

        } else {

            count = count - step;

            if (count < floor) {
                if (isRepeating) {
                    isIncreasing = true;
                }
            }

        }

        if (isRepeating) {
            if (isDoubleRepeating) {
                if (count == initialCount) {
                    repeats++;
                }
            } else {
                if (count == initialCount) {
                    repeats = repeats + 2;
                }
            }
        }

        return count;

    }

    public void reset() {
        count = initialCount;
        isIncreasing = initiallyIncreasing;
        repeats = 0;
    }

    public void freeze() {
        isFrozen = true;
    }

    public void unfreeze() {
        isFrozen = false;
    }

}
