package rutaanalisis;

/**
 *
 * @author elco45
 */
public class edge {

    node start;
    node end;
    double distance;

    public edge(node start, node end) {
        this.start = start;
        this.end = end;
        this.distance = Math.sqrt((Math.pow(end.getX() - start.getX(), 2)) + (Math.pow(end.getY() - start.getY(), 2)));
    }

    public edge(node start, node end, double distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public node getNode1() {
        return start;
    }

    public void setNode1(node start) {
        this.start = start;
    }

    public node getNode2() {
        return end;
    }

    public void setNode2(node end) {
        this.end = end;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void reverse() {
        node swap = this.end;
        this.end = start;
        this.start = swap;
    }

    @Override
    public String toString() {
        return "edge{" + "start=" + start + ", end=" + end + ", distance=" + distance + '}';
    }

}
