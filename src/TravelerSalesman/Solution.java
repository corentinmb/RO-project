package TravelerSalesman;

public class Solution {
    
    Node aNode;

    public Solution(Node current) {
        aNode = current;
    }

    public Solution(){
        aNode = null;
    }

    public boolean isRealizable(Node current) {
        return true;
    }

    public int value(){
        if(aNode == null)
            return 0;
        return 1;
    }
}
