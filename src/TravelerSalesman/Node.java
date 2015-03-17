package TravelerSalesman;

import java.util.ArrayList;

public class Node {
    ArrayList<Node> childs;
    

    public boolean isLeaf() {
        return childs.isEmpty();
    }


    public int inferiorBorn() {
        return 0;
    }

    public void createChild() {

    }

    public ArrayList<Node> getChilds() {
        return this.childs;
    }
}
