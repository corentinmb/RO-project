package TravelerSalesman;

import java.util.ArrayList;
import java.util.Stack;

public class MinimalLength {
    Stack<Node> aStack;
    Node root;

    public MinimalLength(Node root){
        this.root = root;
        aStack = new Stack<Node>();
        aStack.push(this.root);

    }

    public void addChildToStack(ArrayList<Node> nodes){
        for(Node n : nodes){
            aStack.push(n);
        }
    }

    public Solution algorithm(){
        //Le noeud courant
        Node current;

        //La meilleur solution
        Solution bestSolution = new Solution();

        while (aStack.isEmpty()){
            current = aStack.pop();
            if(current.isLeaf()){
                Solution aSolution = new Solution(current);
                if(aSolution.isRealizable(current) && (aSolution.value() < bestSolution.value())){
                    bestSolution = aSolution;
                    if(bestSolution.value() == root.inferiorBorn()){
                        return bestSolution;
                    }
                }
            } else {
                if (current.inferiorBorn() <= bestSolution.value()){
                    current.createChild();
                    this.addChildToStack(current.getChilds());
                }
            }

        }
        return bestSolution;
    }
}
