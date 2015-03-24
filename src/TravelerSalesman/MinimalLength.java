package TravelerSalesman;

import java.util.ArrayList;
import java.util.Stack;

public class MinimalLength {
    Stack<Node> aStack;
    Node root;

    ArrayList<Arc> bestPath;
    Double bestSolution;

    public MinimalLength(Node root){
        this.root = root;
        aStack = new Stack<Node>();
        aStack.push(this.root);
        this.bestPath = new ArrayList<Arc>();
        this.bestSolution = new Double(Double.MAX_VALUE);

    }

    /**
     * Ajouter des enfants à la pile
     * @param nodes la liste d'enfant
     */
    private void addChildToStack(ArrayList<Node> nodes){
        for(Node n : nodes){
            aStack.push(n);
        }
    }

    /**
     * L'algorithme permettant de connaître la solution au problème
     */
    public void algorithm(){
        //Le noeud courant
        System.out.println("Algorithm is entered");
        Node current;

        while (!aStack.isEmpty()){
            System.out.println("CC pd");
            System.out.println();
            current = aStack.pop();
            if(current.isLeaf()){
                System.out.println("Apparemment c'est une feuille");
                if(current.isRealizable() && (current.inferiorBorn() < bestSolution)){
                    bestSolution = current.inferiorBorn();
                    this.bestPath = current.getFixedArc();

                    if(bestSolution.equals(root.inferiorBorn())){
                        return;
                    }
                }
            } else {
                System.out.println("Apparement ce n'est pas une feuille");
                if (current.inferiorBorn() <= bestSolution){
                    current.createChild();
                    this.addChildToStack(current.getChilds());
                }
            }
        }
    }


    public ArrayList<Arc> getBestPath() {
        return this.bestPath;
    }


    public Double getBestSolution() {
        return this.bestSolution;
    }

}
