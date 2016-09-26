package rutaanalisis;

import java.util.ArrayList;

/**
 *
 * @author elco45
 */
public class route {

    double actual;
    edge[][] allEdges;
    ArrayList<edge> edges;
    double lastEdgeDistance;

    public route(int nodesNumber, double lastEdgeDistance) {
        edges = new ArrayList<edge>();
        allEdges = new edge[nodesNumber][2];
        this.lastEdgeDistance = lastEdgeDistance;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public ArrayList<edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<edge> edges) {
        this.edges = edges;
    }

    public void insert(edge e) {
        this.edges.add(e);
        allEdges[e.start.num][1] = e;
        allEdges[e.end.num][0] = e;
        e.start.ruta = this;
        e.end.ruta = this;
    }

    public boolean merge(route r2, edge mergingEdge) {
        if (this.allEdges[mergingEdge.start.num][1].end.num == 0 && r2.allEdges[mergingEdge.end.num][0].start.num == 0) {
            edge e = this.allEdges[0][0];
            this.allEdges[e.start.num][1] = null;
            this.allEdges[0][0] = null;
            this.edges.remove(e);
            edge e2 = r2.allEdges[0][1];
            r2.allEdges[e.end.num][0] = null;
            r2.allEdges[0][1] = null;
            r2.edges.remove(e2);
            for (int i = 0; i < r2.getEdges().size(); i++) {
                this.insert(r2.getEdges().get(i));
                if (r2.getEdges().get(i).end.num == 0) {
                    lastEdgeDistance = r2.getEdges().get(i).distance;
                }
            }
            this.insert(mergingEdge);
            calculateActual();
            return true;
        } else if (r2.allEdges[mergingEdge.end.num][1].end.num == 0 && this.allEdges[mergingEdge.start.num][0].start.num == 0) {
            mergingEdge.reverse();
            edge e = this.allEdges[0][1];
            this.allEdges[e.end.num][0] = null;
            this.allEdges[0][1] = null;
            this.edges.remove(e);
            edge e2 = r2.allEdges[0][0];
            r2.allEdges[e2.start.num][1] = null;
            r2.allEdges[0][0] = null;
            r2.edges.remove(e2);
            int siz = r2.getEdges().size();
            for (int i = 0; i < siz; i++) {
                this.insert(r2.getEdges().get(i));
            }
            this.insert(mergingEdge);
            calculateActual();
            return true;
        } else {
            return false;
        }
    }

    public void calculateActual() {
        this.actual = 0;
        for (int i = 0; i < edges.size(); i++) {
            this.actual += Math.sqrt((Math.pow(edges.get(i).end.getX() - edges.get(i).start.getX(), 2)) + (Math.pow(edges.get(i).end.getY() - edges.get(i).start.getY(), 2)));
        }
    }

    @Override
    public String toString() {
        return "route{" + "actual=" + actual + ", edges=" + edges + '}';
    }

}
