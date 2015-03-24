package TravelerSalesman;

import Jama.Matrix;

import java.util.ArrayList;

/**
 * Class pour gérer le noeud
 */
public class Node {
    //Les enfants produits par ce schéma
    private ArrayList<Node> childs;

    //La matrice courante concernée par le noeud
    private Matrix currentMatrix;

    //Solution partielles
    private ArrayList<Arc> fixedArc;

    /**
     * Le constructeur utilisé pour le noeud racine
     * @param matrix La matrice qui initialise le noeud
     */
    public Node(Matrix matrix){
        System.out.println("Je me construit en root");
        this.currentMatrix = matrix;
        this.fixedArc = new ArrayList<Arc>();
        this.childs = new ArrayList<Node>();
        //System.out.println("Montrons la matrice");
        //this.currentMatrix.print(this.currentMatrix.getColumnDimension(), this.currentMatrix.getColumnDimension());
    }

    /**
     * Le constructeur pour construire des enfants
     * @param forbidden indiquer si c'est le côté interdit du schéma
     * @param matrix la matrix que l'on veut donner au noeud
     * @param arcs la liste d'arc du parent
     * @param currentArc l'arc concerné par cette création d'enfant
     */
    public Node(Boolean forbidden, Matrix matrix, ArrayList<Arc> arcs, Arc currentArc){
        //System.out.println("Je me construit comme étant un enfant");
        this.currentMatrix = matrix;
        this.fixedArc = arcs;

        if(!forbidden){
            //On met des plus l'infini à la ligne, colonne et à l'arc inverse
            //System.out.println("Je suis le gentil enfant,  on peut me passer dessus");
            this.fixedArc.add(currentArc);
            //Pour rappel X est la ligne et Y la colonne

            //On met des plus l'infini à la ligne
            for(int j = 0; j < this.currentMatrix.getColumnDimension(); ++j){
                if(j != currentArc.getY())
                    this.currentMatrix.getArray()[currentArc.getX()][j] = Double.MAX_VALUE;
            }

            //On met des plus l'infini sur la colonne
            for(int i = 0; i < this.currentMatrix.getRowDimension(); ++i){
                if(i != currentArc.getX())
                    this.currentMatrix.getArray()[i][currentArc.getY()] = Double.MAX_VALUE;
            }

            //On met + l'infini l'arc inverse
            this.currentMatrix.getArray()[currentArc.getY()][currentArc.getX()] = Double.MAX_VALUE;
        } else{
            System.out.println("Je suis le méchant enfant");
            //On met +l'infini à l'arc concerné
            this.currentMatrix.getArray()[currentArc.getX()][currentArc.getY()] = Double.MAX_VALUE;
        }
        System.out.println("Je sors du constructeur spécial enfant");
    }


    /**
     * Savoir si un noeud est une feuille
     * @return si c'est une feuille
     */
    public boolean isLeaf() {
        return fixedArc.size() == currentMatrix.getColumnDimension();
    }

    /**
     * Connaître la borne inférieur de notre matrice avec sa modification
     * @return la borde inférieur
     */
    public Double inferiorBorn() {
        Double sumMinLine = new Double(0);
        Double minOfLine = null;
        Double minOfColumn = null;
        Double sumMinColumn = new Double(0);
        int numberOfLines = currentMatrix.getRowDimension();
        int numberOfColumns = currentMatrix.getColumnDimension();
        double[][] array = currentMatrix.getArray();

        //Pour trouver le minimum des lignes
        for (int i = 0; i < numberOfLines; ++i) {
            //Parcours de la ligne pour chercher le minimum
            for (int j = 0; j < numberOfColumns; ++j) {
                if (minOfLine == null)
                    minOfLine = array[i][j];
                else if (array[i][j] <= minOfLine) {
                    minOfLine = array[i][j];
                }
            }
            //On ajout le minimum courant à la somme des minimum
            sumMinLine += minOfLine;

            //On soustrait le minimum
            for (int j = 0; j < numberOfColumns; ++j) {
                array[i][j] -= minOfLine;
            }
        }

        //Pour trouver le minimum des colonnes
        for(int i = 0; i < numberOfColumns; ++i){
            //Parcours de la colonne pour chercher le minimum
            for(int j = 0; j < numberOfLines; ++j){
                if (minOfColumn == null)
                    minOfColumn = array[j][i];
                else if (array[i][j] <= minOfColumn) {
                    minOfColumn = array[j][i];
                }
            }

            sumMinColumn += minOfColumn;

            //On soustrait le minimum
            for (int j = 0; j < numberOfLines; ++j) {
                array[j][i] -= minOfColumn;
            }
        }
        System.out.println("La borne inférieure est " + (sumMinLine+sumMinColumn));
        System.out.println("Voici la matrice maintenant");
        this.currentMatrix.print(this.currentMatrix.getRowDimension(), this.currentMatrix.getColumnDimension());
        return sumMinLine + sumMinColumn;
    }

    /**
     * La création d'enfant du noeud couran
     */
    public void createChild(){
        //L'arc par lequel on passe, un enddroit où il y a 0
        System.out.println("Copulons ...");
        Arc arc = null;
        double[][] array = currentMatrix.getArray();
        boolean find = false;

        System.out.println("Calculons les infos");
        for(int i = 0; i < currentMatrix.getRowDimension(); ++i){
            for(int j = 0; j < currentMatrix.getColumnDimension(); ++j){
                if(array[i][j] == 0){
                    arc = new Arc(i,j);
                    find = true;
                    break;
                }
            }
            if(find)
                break;
        }
        System.out.println("Commencons la copulation");
        //On crée les deux enfants, un où l'on interdit le noeud
        this.childs.add(new Node(false, this.currentMatrix, this.fixedArc,arc));
        //L'autre où l'on autorise le noeud
        this.childs.add(new Node(true, this.currentMatrix, this.fixedArc, arc));

    }


    /**
     * Méthode à implémenter pour savoir si une solution est réalisable
     * @return un booléen
     */
    public boolean isRealizable() {
        System.out.println("isRealizable");
        ArrayList<Arc> visitedArc = new ArrayList<Arc>();

        //On doit faire le parcours complet
        Arc currentArc = this.fixedArc.get(0);

        visitedArc.add(currentArc);

        int SommetDeFin = currentArc.getY();

        //On va parcourir tous nos arcs
        for(int i = 0; i <= fixedArc.size(); ++i){

            //Pour chaque arc, on regarde si son point de départ est le même que le point de fin du noeud précédent
            for(Arc a : this.fixedArc){
                //On ne vérifie uniquement si l'arc n'a pas été vérifié
                if(!visitedArc.contains(a)){

                    if( a.getX() == SommetDeFin ){
                        SommetDeFin = a.getY();
                        visitedArc.add(a);
                    }
                }
            }
        }
        return visitedArc.size() == fixedArc.size();

    }

    public ArrayList<Node> getChilds() {
        return this.childs;
    }


    public ArrayList<Arc> getFixedArc() {
        return this.fixedArc;
    }

}
