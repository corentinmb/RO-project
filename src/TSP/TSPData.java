package TSP;

import Jama.Matrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TSPData {

    private int size = 0;
    private Matrix matrix = null;

    public TSPData(String filename) {
        boolean readingNodes = false;
        double[] x = new double[size];
        double[] y = new double[size];
        try {
            System.out.println("Lecture du fichier...");
            BufferedReader buf = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = buf.readLine()) != null) {

                if (line.equals("EOF")) {
                    // fin de lecture, on replit la matrice
                    readingNodes = false;

                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            if (i == j)
                                matrix.set(i, j, 999999);
                            else
                                matrix.set(i, j, Math.floor(Math.sqrt((x[i] - x[j]) * (x[i] - x[j])
                                        + (y[i] - y[j]) * (y[i] - y[j])) + 0.5));
                        }
                    }
                    System.out.println("Matrice générée");
                }

                if (readingNodes && matrix != null) {
                    String[] parts = line.trim().split(" ");
                    int index = Integer.parseInt(parts[0]) - 1;
                    x[index] = Double.parseDouble(parts[1]);
                    y[index] = Double.parseDouble(parts[2]);
                }

                if (line.startsWith("DIMENSION:")) {
                    // Ligne contenant la taille
                    String strSize =  line.split(":")[1].trim();
                    System.out.printf("Nombre de noeuds : %s%n", strSize);
                    this.size = Integer.parseInt(strSize);
                    this.matrix = new Matrix(size, size);
                    x = new double[size];
                    y = new double[size];
                }

                if (line.equals("NODE_COORD_SECTION"))
                    readingNodes = true;


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return size;
    }

    public Matrix getMatrix() {
        return matrix;
    }
}
