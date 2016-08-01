import java.util.*;

class NodeComparator implements Comparator<Node>{
    private int[] goalstate;
    private int h;

    public NodeComparator(Node goal, int h){
        goalstate = goal.state;
        this.h = h;
    }

    @Override
    public int compare(Node n1, Node n2){
        return heuristic(n1, n2);
    }

    //heuristic function
    private int heuristic(Node n1, Node n2){
        if(h == 1){
            int result = heuristic1(n1.state, goalstate) - heuristic1(n2.state, goalstate) + n1.gvalue + n2.gvalue;
            if(result != 0) return result;
            return tieBreaker(n1, n2);
        } 
        int result = heuristic2(n1.state, goalstate) - heuristic2(n2.state, goalstate) + n1.gvalue + n2.gvalue;
        if(result != 0) return result;
        return tieBreaker(n1, n2);
    }

    //Manhattan distance
    private int heuristic1(int[] start, int[] goal){
        int result = 0;
        int j;
        for(int i = 0; i < 9; i++){
            if(start[i] != 0){
                j = 0;
                while(goal[j] != start[i]) j++;
                result += Math.abs(Math.abs(start[i]/3 - i/3) - Math.abs(goal[j]/3 - j/3));
                result += Math.abs(Math.abs(start[i]%3 - i%3) - Math.abs(goal[j]%3 - j%3));
            }
        }
        return result;
    }
    
    //tile switches remaining
    private int heuristic2(int[] start, int[] goal){
        int result = 0;
        for(int i = 0; i < 9; i++){
            if(start[i] != 0){
                if(start[i] != goal[i]) result++;
            }
        }
        return result;
    }

    //settles ties
    public static int tieBreaker(Node n1, Node n2){
        int a = 0;
        int b = 0;
        for(int i = 0; i < 9; i++){
            a += n1.state[i]*Math.pow(10,8-i);
            b += n2.state[i]*Math.pow(10,8-i);
        }
        return b-a;
    }
}
