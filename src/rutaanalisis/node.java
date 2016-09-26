package rutaanalisis;

/**
 *
 * @author elco45
 */
public class node {

    int num;
    int x;
    int y;
    double weight;
    route ruta;

    public node() {
    }

    public node(int num, int x, int y, double weight) {
        this.num = num;
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public node(int num, int x, int y, node m) {
        this.num = num;
        this.x = x;
        this.y = y;
        this.weight = Math.sqrt((Math.pow(x - m.getX(), 2)) + (Math.pow(y - m.getY(), 2)));
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public double getAmount() {
        return weight;
    }

    public void setAmount(double weight) {
        this.weight = weight;
    }

    public route getRuta() {
        return ruta;
    }

    public void setRuta(route ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "node{" + "num=" + num + ", weight=" + weight + '}';
    }

    

}
