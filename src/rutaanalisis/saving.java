package rutaanalisis;

/**
 *
 * @author elco45
 */
public class saving {
    double value;
    node node1;
    node node2;

    public saving() {
    }

    public saving(double value) {
        this.value = value;
    }

    public saving(double value, node node1, node node2) {
        this.value = value;
        this.node1 = node1;
        this.node2 = node2;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public node getNode1() {
        return node1;
    }

    public void setNode1(node node1) {
        this.node1 = node1;
    }

    public node getNode2() {
        return node2;
    }

    public void setNode2(node node2) {
        this.node2 = node2;
    }

    @Override
    public String toString() {
        return "saving{" + "value=" + value + ", node1=" + node1 + ", node2=" + node2 + '}';
    }
}
