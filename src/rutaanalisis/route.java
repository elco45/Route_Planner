package rutaanalisis;

import java.util.ArrayList;

/**
 *
 * @author elco45
 */
public class route {

    double limit;
    double actual;
    edge[][] allEdges;
    ArrayList<edge> edges;

    public route(int nodesNumber) {
        edges = new ArrayList<edge>();
        allEdges = new edge[nodesNumber][2];
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
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
            }
            this.actual += r2.actual;
            this.insert(mergingEdge);
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
            this.actual += r2.actual;
            this.insert(mergingEdge);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "route{" + "limit=" + limit + ", actual=" + actual + ", edges=" + edges + '}';
    }

}
