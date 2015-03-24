package TravelerSalesman;

import TSP.TSPData;

public class App {

    public static void main(String[] args) {


        TSPData tspData = new TSPData("/Users/Ananas/Documents/DUTInfo/S3-S4/RechercheOperationnelle/out/production/RechercheOperationnelle/toy_instances/toy04.tsp");
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
