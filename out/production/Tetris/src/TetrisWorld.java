public class TetrisWorld extends World {
    private double tickSpeed;

    public TetrisWorld(double tickSpeed) {
        this.tickSpeed = tickSpeed;
    }

    @Override
    public void act() {

    }

    public double getTickSpeed() {
        return tickSpeed;
    }

    public void setTickSpeed(double tickSpeed) {
        this.tickSpeed = tickSpeed;
    }
}
