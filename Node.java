import java.util.*;

class Node{
    public int gvalue;
    public int[] state;
    public ArrayList<Node> children;
    public ArrayList<Node> solution;

    public Node(int[] n){
        gvalue = 0;
        state = new int[9];
        System.arraycopy(n, 0, state, 0, 9);
        children = new ArrayList<Node>();
        solution = new ArrayList<Node>();
    }

    /*  
        +---+---+---+
        | 1 | 2 | 3 |
        +---+---+---+
        | 4 | 5 | 6 |
        +---+---+---+
        | 7 | 8 |   |
        +---+---+---+
    */
    public String prettyPrintState(String msg){
        String pattern  = "+---+---+---+";
        String patternNL = "+---+---+---+\n";
        String result = "";
        String buffer = "";
        for(int i = 0; i < 3; i++){
            result += patternNL;
            if(i == 1) result += _getRow(i, msg);
            else result += _getRow(i, null);
        }
        result += pattern;
        return result;
    }

    private String _getRow(int row, String msg){
        String startPattern    = "| ";
        String middlePattern   = " | ";
        String endPattern      = " |\n";
        String result = new String(startPattern);

        for(int column = 0; column < 3; column++){
            int position = (3 * row) + column;
            int tile = state[position];

            result += (tile != 0) ? tile : " ";
            if (column == 2){
                if(msg == null) result += endPattern;
                else result += " |\t" + msg + '\n';
            } 
            else result += middlePattern;
        }
        return result;
    }
}