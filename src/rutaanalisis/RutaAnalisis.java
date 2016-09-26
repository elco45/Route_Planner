package rutaanalisis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author elco45
 */
public class RutaAnalisis {

    static ArrayList<node> listaNodos = new ArrayList();
    static int numCamion = 0;
    static double limit = 0;
    static double[][] matrizAdj;
    static ArrayList<saving> listaSavings = new ArrayList();
    static ArrayList<route> listaRutas = new ArrayList();
    static ArrayList<ArrayList<Integer>> nodesRoute = new ArrayList();

    public static void main(String[] args) {
        readFile();
        listAll();
        System.out.println("NumCamion " + numCamion + "\n");

        if (numCamion <= 0 || numCamion >= listaNodos.size()) {
            System.out.println("WHY YOU DO THIS TO ME!!! Q.Q");
        } else {
            matrizAdj = createAdj();
            writeAdj(matrizAdj);
            writeSaving(createSaving());
            listaSavings.sort(Comparator.comparing(saving::getValue));
            Collections.reverse(listaSavings);
            for (int i = 0; i < listaSavings.size(); i++) {
                System.out.println(listaSavings.get(i));
            }
            startPlanner();
            printRutas();
            printSalida();
            printCalculos();
        }
    }

    public static void readFile() {
        listaNodos.clear();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File("./entrada.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            int cont = -1;
            while ((linea = br.readLine()) != null) {
                if (cont != -1 && cont != 0) {
                    String[] tmp = linea.split(",");
                    node nd = new node(cont, Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), listaNodos.get(0));
                    listaNodos.add(nd);
                } else if (cont == 0) {  //origin
                    String[] tmp = linea.split(",");
                    node nd = new node(cont, Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 0);
                    listaNodos.add(nd);
                } else {
                    numCamion = Integer.parseInt(linea);
                }
                cont++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {

            }
        }
    }

    public static void listAll() {
        for (int i = 0; i < listaNodos.size(); i++) {
            System.out.println(listaNodos.get(i));
        }
    }

    public static double[][] createAdj() {
        limit = 0;
        double[][] ans = new double[listaNodos.size()][listaNodos.size()];
        for (int i = 0; i < listaNodos.size(); i++) {
            for (int j = 0; j < listaNodos.size(); j++) {
                ans[i][j] = distance(listaNodos.get(i), listaNodos.get(j));
                if (i == 0) {
                    limit += ans[i][j];
                }
            }
        }
        limit /= numCamion;
        return ans;
    }

    public static double distance(node init, node end) {
        return Math.sqrt((Math.pow(end.getX() - init.getX(), 2)) + (Math.pow(end.getY() - init.getY(), 2)));
    }

    public static void writeAdj(double[][] adj) {
        System.out.println("Adj");
        for (int i = 0; i < adj.length; i++) {
            for (int j = 0; j < adj.length; j++) {
                System.out.print("[" + (int) adj[i][j] + "]");
            }
            System.out.println("");
        }
        System.out.println("Limit " + limit + "\n");
    }

    public static saving[][] createSaving() {
        saving[][] ans = new saving[listaNodos.size()][listaNodos.size()];
        for (int i = 0; i < listaNodos.size(); i++) {
            for (int j = 0; j < listaNodos.size(); j++) {
                if (j > i && i != 0) {
                    saving tmp = new saving(distance(listaNodos.get(0), listaNodos.get(i)) + distance(listaNodos.get(0), listaNodos.get(j)) - distance(listaNodos.get(i), listaNodos.get(j)), listaNodos.get(i), listaNodos.get(j));
                    ans[i][j] = tmp;
                    listaSavings.add(tmp);
                } else {
                    ans[i][j] = new saving(0);
                }
            }
        }
        return ans;
    }

    public static void writeSaving(saving[][] sv) {
        System.out.println("Saving");
        for (int i = 0; i < sv.length; i++) {
            for (int j = 0; j < sv.length; j++) {
                System.out.print("[" + (int) sv[i][j].value + "]");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void clarkeWright() {
        listaRutas = new ArrayList<route>();
        for (int i = 1; i < listaNodos.size(); i++) {
            node nTmp = listaNodos.get(i);
            route rTmp = new route(listaNodos.size(),matrizAdj[0][nTmp.num]);
            rTmp.actual += nTmp.weight;
            rTmp.insert(new edge(listaNodos.get(0), nTmp, matrizAdj[0][nTmp.num]));
            rTmp.insert(new edge(nTmp, listaNodos.get(0), matrizAdj[0][nTmp.num]));
            listaRutas.add(rTmp);
        }
        for (int i = 0; i < listaSavings.size(); i++) {
            saving actualS = listaSavings.get(i);
            node n1 = actualS.node1;
            node n2 = actualS.node2;
            route r1 = n1.ruta;
            route r2 = n2.ruta;
            
            if (!r1.equals(r2) && (r1.actual)+ (r2.actual) < limit) {
                edge in = r1.allEdges[n1.num][0];
                edge out = r2.allEdges[n2.num][1];
                if (out != null && in != null) {
                    boolean success = r1.merge(r2, new edge(n1, n2, matrizAdj[n1.num][n2.num]));
                    if (success) {
                        listaRutas.remove(r2);
                    }
                }
            }
        }
        for (int i = 0; i < listaRutas.size(); i++) {
         System.out.println(listaRutas.get(i));
         }
    }

    public static void startPlanner() {
        do {
            if (listaRutas.size() > numCamion) {
                limit += limit * 0.01;
            } else {
                limit -= limit * 0.01;
            }
            clarkeWright();
            System.out.println("///////////////////////");
        } while (listaRutas.size() != numCamion);
        System.out.println("");
    }

    public static void printRutas() {
        for (int i = 0; i < listaRutas.size(); i++) {
            edge e = listaRutas.get(i).allEdges[0][1];
            System.out.print(e.start.num + "-" + e.end.num + "-");
            do {
                e = listaRutas.get(i).allEdges[e.end.num][1];
                listaRutas.get(i).calculateActual();
                System.out.print(e.end.num + "-");
            } while (e.end.num != 0);
            System.out.print("Distancia recorrida: " + listaRutas.get(i).actual);
            System.out.println("");
        }
    }

    public static void printSalida() {
        File archivo = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            archivo = new File("./salida.txt");
            fw = new FileWriter(archivo, false);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < nodesRoute.size(); i++) {
                bw.write(nodesRoute.get(i).size() + "");
                bw.newLine();
                for (int j = 0; j < nodesRoute.get(i).size(); j++) {
                    bw.write(nodesRoute.get(i).get(j) + "");
                    bw.newLine();
                }
            }
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {

            }
        }
    }

    public static void printCalculos() {
        File archivo = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            archivo = new File("./calculos.txt");
            fw = new FileWriter(archivo, false);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < listaRutas.size(); i++) {
                bw.write("Ruta " + i + " distancia: " + listaRutas.get(i).actual);
                bw.newLine();
            }
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {

            }
        }
    }

}
