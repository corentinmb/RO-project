package TravelerSalesman;

import TSP.TSPData;

public class App {

    public static void main(String[] args) {

        // Chemin absolu vers le fichier de données
        TSPData tspData = new TSPData(args[0]);
        Node node = new Node(tspData.getMatrix());
        MinimalLength minimalLength = new MinimalLength(node);

        //On crée nos paramètres qui vont servir pour rechercher la meilleur solution

        minimalLength.algorithm();

        /////////////////////////////////////////////////////
        //ICI ON A NORMALEMENT RECUPERER TOUT CE QU'IL FAUT//
        /////////////////////////////////////////////////////

        System.out.println("Les meilleurs solutions sont : ");
        System.out.println("La meilleur solution de passage est " + minimalLength.getBestSolution());
        System.out.println("Le meilleur chemin est :");
        for(Arc a : minimalLength.getBestPath()){
            System.out.println("Arc x : " + a.getX() + " y " + a.getY());
        }
    }
}
