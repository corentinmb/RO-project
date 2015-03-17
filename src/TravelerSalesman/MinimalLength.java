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

    public void algorithm(ArrayList<Arc> bestPath, Double bestSolution){
        //Le noeud courant
        Node current;

        while (aStack.isEmpty()){
            current = aStack.pop();
            if(current.isLeaf()){
                if(current.isRealizable() && (current.inferiorBorn() < bestSolution)){
                    bestSolution = current.inferiorBorn();
                    bestPath = current.getFixedArc();
                    if(bestSolution.equals(root.inferiorBorn())){
                        return;
                    }
                }
            } else {
                if (current.inferiorBorn() <= bestSolution){
                    current.createChild();
                    this.addChildToStack(current.getChilds());
                }
            }

        }
    }
}
