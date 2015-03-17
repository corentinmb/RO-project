package TravelerSalesman;

import Jama.Matrix;

import java.util.ArrayList;

public class Node {
    private ArrayList<Node> childs;
    private Matrix currentMatrix;

    //Solution partielles
    private ArrayList<Arc> fixedArc;

    public Node(Matrix matrix){
        this.currentMatrix = matrix;
    }

    public Node(Boolean forbidden, Matrix matrix, ArrayList<Arc> arcs, Arc currentArc){
        this.currentMatrix = matrix;
        this.fixedArc = arcs;
        if(!forbidden){
            this.fixedArc.add(currentArc);
        }
    }


    public boolean isLeaf() {
        return fixedArc.size() == currentMatrix.getColumnDimension();
    }


    public Double inferiorBorn() {
        Double sumMinLine = null;
        Double minOfLine = null;
        Double minOfColumn = null;
        Double sumMinColumn = null;
        int numberOfLines = currentMatrix.getRowDimension();
        int numberOfColumns = currentMatrix.getColumnDimension();
        double[][] array = currentMatrix.getArray();

        //Pour trouver le minimum des lignes
        for (int i = 0; i <= numberOfLines; ++i) {
            //Parcours de la ligne pour chercher le minimum
            for (int j = 0; j <= numberOfColumns; ++j) {
                if (minOfLine == null)
                    minOfLine = array[i][j];
                else if (array[i][j] <= minOfLine) {
                    minOfLine = array[i][j];
                }
            }
            //On ajout le minimum courant à la somme des minimum
            sumMinLine += minOfLine;

            //On soustrait le minimum
            for (int j = 0; j <= numberOfColumns; ++j) {
                array[i][j] -= minOfLine;
            }
        }

        //Pour trouver le minimum des colonnes
        for(int i = 0; i <= numberOfColumns; ++i){
            //Parcours de la colonne pour chercher le minimum
            for(int j = 0; j <= numberOfLines; ++j){
                if (minOfColumn == null)
                    minOfColumn = array[j][i];
                else if (array[i][j] <= minOfColumn) {
                    minOfColumn = array[j][i];
                }
            }

            sumMinColumn += minOfColumn;

            //On soustrait le minimum
            for (int j = 0; j <= numberOfLines; ++j) {
                array[j][i] -= minOfColumn;
            }
        }

        return sumMinLine + sumMinColumn;
    }

    public void createChild(){
        //L'arc par lequel on passe, un enddroit où il y a 0
        Arc arc = null;
        double[][] array = currentMatrix.getArray();
        boolean find = false;

        for(int i = 0; i <= currentMatrix.getRowDimension(); ++i){
            for(int j = 0; j <= currentMatrix.getColumnDimension(); ++j){
                if(array[i][j] == 0){
                    arc = new Arc(i,j);
                    find = true;
                    break;

                }
            }
            if(find)
                break;
        }
        this.childs.add(new Node(false, this.currentMatrix, this.fixedArc,arc));
        this.childs.add(new Node(true, this.currentMatrix, this.fixedArc, arc));

    }

    public ArrayList<Node> getChilds() {
        return this.childs;
    }

    public boolean isRealizable() {
        return false;
    }

    public Node setChilds(ArrayList<Node> childs) {
        this.childs = childs;
        return this;
    }

    public Matrix getCurrentMatrix() {
        return this.currentMatrix;
    }

    public Node setCurrentMatrix(Matrix currentMatrix) {
        this.currentMatrix = currentMatrix;
        return this;
    }

    public ArrayList<Arc> getFixedArc() {
        return this.fixedArc;
    }

    public Node setFixedArc(ArrayList<Arc> fixedArc) {
        this.fixedArc = fixedArc;
        return this;
    }
}
