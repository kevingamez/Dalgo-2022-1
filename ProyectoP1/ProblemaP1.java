import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class ProblemaP1 {

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        try {
            long t1 = System.currentTimeMillis();//Obtiene le tiempo inicial desde donde se ejecuta el programa.
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            int casos = Integer.parseInt(input); //La primera linea es un entero con el número de casos de prueba
            input = br.readLine();
            for (int i = 0; i < casos; i++) {
                String[] secondLine = input.split(" "); //La segunda linea contiene a n,m y p en este orden.
                int n = Integer.parseInt(secondLine[0]);
                int m = Integer.parseInt(secondLine[1]);
                int p = Integer.parseInt(secondLine[2]);
                ArrayList<String> portales = new ArrayList<>(); //Se crea un arreglo donde se almacenan los x,y de llegada de los portales.
                input = br.readLine();
                String cost[] = input.split(" ");
                int costos[] = new int[n]; //Se crea un arreglo de costos donde el nivel es el indice en donde esta el costo de ese nivel en el arreglo.
                for (int j = 0; j < cost.length; j++) {
                    costos[j] = Integer.parseInt(cost[j]);
                }
                String matrix[][] = new String[n][m]; //Se define una matriz de Strings llena de null.
                input = br.readLine();
                int cont = 0;
                while (cont < p) {
                    String data[] = input.split(" ");
                    int x1 = Integer.parseInt(data[0]) - 1;
                    int y1 = Integer.parseInt(data[1]) - 1;
                    int x2 = Integer.parseInt(data[2]) - 1;
                    int y2 = Integer.parseInt(data[3]) - 1;
                    portales.add(cont, x2 + "," + y2 + ","+x1 + "," + y1);//Se agrega los c,y de llegada a la lista de portales.
                    matrix[x1][y1] = x2 + "," + y2;//Se agrega los x,y de llegada en los x,y de inicio de la matriz que se encuentra con sus valores null.
                    input = br.readLine();
                    cont++;

                }

                Collections.sort(portales, new OrdenarIndice());//Se ordena la lista de portales por el indice de as filas.
                double costo = costo(matrix, costos, portales);//Se ejecuta la función de costo del problema.
                //Si costo es un valor menor a infinito se imprime el entero de ese costo y si es infinito se imprime que no existe un camino.
                if (costo != Double.POSITIVE_INFINITY) {
                    System.out.println((int) costo);
                } else {
                    System.out.println("NO EXISTE");
                }
            }
            long t2 = System.currentTimeMillis();
            br.close();
            //Obtiene el tiempo final del programa.
            double time =(double) (t2-t1)/1000;
            System.out.println("Time:  "+time);//Se imprime el tiempo de ejecución del programa.
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static double costo(String[][] grilla, int[] costos, ArrayList<String> portales) {
        double matrix[][] = new double[grilla.length][grilla[0].length];//Se crea una matriz de costos con el mismo tamaño de la matriz del problema.
        int n = grilla.length;
        int m = grilla[0].length;
        //Se inicializa cada elemento de la matriz con un infinito.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        //Se hace el recorrido solo del primer piso.
        recorrido(0, 0,m,n,grilla,matrix,costos,1);

        for (int i = 0; i < portales.size(); i++) {
            int indice = Integer.parseInt(portales.get(i).split(",")[0]);
            int indiceJ = Integer.parseInt(portales.get(i).split(",")[1]);
            //Se hace el recorrido a la derecha.
            recorrido(indiceJ, indice, m, n, grilla, matrix, costos, 1);
            //Se hace el recorrido a la izquierda.
            recorrido(indiceJ, indice, m, n, grilla, matrix, costos, 0);
        }
        return matrix[n - 1][m - 1];
    }

    /*
    Hace el recorrido en el piso desde el cuarto con indiceJ que llega por parametro en el sentido que se indica, ya sea por derecha o por izquierda.
     */
    public static void recorrido(int indiceJ, int indice, int m, int n, String grilla[][], double matrix[][], int costos[], int sentido) {
        int j = indiceJ;
        while ( j >= 0 && j < m) {
            if (indice == 0 && j==0) {
                matrix[0][0] = 0;
            }
            else if (j == m - 1 && grilla[indice][j] == null && j != 0) {
                matrix[indice][j] = Math.min(matrix[indice][j], matrix[indice][j - 1] + costos[indice]);
            } else if (j == 0 && grilla[indice][j] == null && j != m - 1) {
                matrix[indice][j] = Math.min(matrix[indice][j], matrix[indice][j + 1] + costos[indice]);
            } else if (grilla[indice][j] == null && (j != m - 1 || j != 0)) {
                matrix[indice][j] = Math.min(matrix[indice][j], Math.min(matrix[indice][j + 1] + costos[indice], matrix[indice][j - 1] + costos[indice]));
            } else if (grilla[indice][j] != null && (j == m - 1 || j == 0)) {
                int portalI = Integer.parseInt(grilla[indice][j].split(",")[0]);
                int portalJ = Integer.parseInt(grilla[indice][j].split(",")[1]);
                double x1 = Double.POSITIVE_INFINITY;
                double x2 = Double.POSITIVE_INFINITY;
                if (j == m - 1 && j != 0) {
                    x1 = Math.min(matrix[indice][j - 1] + costos[indice], matrix[portalI][portalJ]);
                } else if (j == 0 && j != m - 1) {
                    x1 = Math.min(matrix[indice][j + 1] + costos[indice], matrix[portalI][portalJ]);
                }
                matrix[indice][j] = Math.min(x1, x2);
                matrix[portalI][portalJ] = matrix[indice][j];
            } else if (grilla[indice][j] != null) {
                int portalI = Integer.parseInt(grilla[indice][j].split(",")[0]);
                int portalJ = Integer.parseInt(grilla[indice][j].split(",")[1]);
                matrix[indice][j] = Math.min(matrix[indice][j], Math.min(matrix[indice][j - 1] + costos[indice], matrix[indice][j + 1] + costos[indice]));
                matrix[portalI][portalJ] = matrix[indice][j];
            }
            if (sentido==1){
                j++;
            }else {
                j--;
            }
        }
    }

    public static class OrdenarIndice implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            int x = Integer.parseInt(o1.split(",")[2]) - Integer.parseInt(o2.split(",")[2]);
            if (x < 0) {
                return -1;
            } else if (x == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
