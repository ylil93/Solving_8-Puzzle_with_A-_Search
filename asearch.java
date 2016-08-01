/*  Lily Li
    cs383
    Assignment 1: Search
    02/14/2014
    This program uses A* search algorithm to solve an 8-puzzle
*/
import java.util.*;
public class asearch {

    private static Node startState;
    private static Node goalState;
    private static int heuristic;

    public static void main (String[] args) {
        checkArgs(args);
        heuristic = Integer.parseInt(args[0]);
        int[] temp = new int[9];

        //read in start state -- works
        System.out.print("Enter Start State: ");
        Scanner scan = new Scanner(System.in);
        for(int i = 0; i < 9; i++){
            temp[i] = scan.nextInt();
        }
        startState = new Node(temp);
        System.out.println(startState.prettyPrintState(null));
        
        
        //read in goal state -- works
        System.out.print("Enter Goal State: ");
        for(int i = 0; i < 9; i++){
            temp[i] = scan.nextInt();
        }
        goalState = new Node(temp);
        System.out.println(goalState.prettyPrintState(null));

        
        Comparator<Node> comparator = new NodeComparator(goalState, heuristic);
        PriorityQueue<Node> fringe = new PriorityQueue<Node>(10, comparator);
        HashSet<Node> expanded = new HashSet<Node>();
        
        fringe.add(startState);
        Node previous = null;
        Node current = null;
        boolean isStartState = true;
        int index = 0;

        //execute A*
        if(Arrays.equals(goalState.state, startState.state)) return;
        while(fringe.size() != 0){        //if fringe is empty we've failed
            //update pointers (previous, current, index)
            previous = current;
            current = fringe.remove();
            index = 0;

            //update path + gvalue
            if(!isStartState){
                current.solution.add(previous);
                current.gvalue = previous.gvalue + 1;
            }
            else{
                isStartState = false;
            }

            //is goal found?
            if(Arrays.equals(goalState.state, current.state)){
                current.solution.add(current);
                printout(current.solution);
                return;
            }

            //produce children
            while(current.state[index] != 0){ 
                index = index + 1;
                if(index == 9){
                    System.out.println("no 0 found");
                    System.exit(1);
                }
            }
            current.children = produceChildren(current, index);

            //update fringe (with children of current)
            for(Node cursor : current.children){
                if(!expanded.contains(cursor)){
                    cursor.solution = current.solution;
                    fringe.add(cursor);
                }
            }

            //add current to expanded list
            expanded.add(current);

        }
        System.out.println("Solution does not exist :(");
    }
    
    private static void checkArgs(String[] args){
        //correct num of arguments?
        if(args.length != 1){
            System.err.println("Number of arguments must be 1");
            System.exit(1);
        }

        //args should both be integers
        int[] myArgs = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                myArgs[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + " must be an integer");
                System.exit(1);
            }
        }

        //is first argument valid?
        if(!(myArgs[0] == 1 | myArgs[0] == 2)){
            System.err.println("arg1: Invalid Argument");
            System.exit(1);
        }
    }

    //produce children -- works
    private static ArrayList<Node> produceChildren(Node current, int i){
        ArrayList<Node> result = new ArrayList<Node>();
        switch(i){      //should be based on location 0
            case 1: result.add(arrayswap(current.state, i, 0));
                    result.add(arrayswap(current.state, i, 2));
                    result.add(arrayswap(current.state, i, 4));
                    break;

            case 2: result.add(arrayswap(current.state, i, 1));
                    result.add(arrayswap(current.state, i, 5));
                    break;

            case 3: result.add(arrayswap(current.state, i, 0));
                    result.add(arrayswap(current.state, i, 4));
                    result.add(arrayswap(current.state, i, 6));
                    break;
            
            case 4: result.add(arrayswap(current.state, i, 1));
                    result.add(arrayswap(current.state, i, 3));
                    result.add(arrayswap(current.state, i, 5));
                    result.add(arrayswap(current.state, i, 7));
                    break;
            
            case 5: result.add(arrayswap(current.state, i, 2));
                    result.add(arrayswap(current.state, i, 4));
                    result.add(arrayswap(current.state, i, 8));
                    break;
            
            case 6: result.add(arrayswap(current.state, i, 3));
                    result.add(arrayswap(current.state, i, 7));
                    break;
            
            case 7: result.add(arrayswap(current.state, i, 4));
                    result.add(arrayswap(current.state, i, 6));
                    result.add(arrayswap(current.state, i, 8));
                    break;
            
            case 8: result.add(arrayswap(current.state, i, 5));
                    result.add(arrayswap(current.state, i, 7));
                    break;
            
            default:    result.add(arrayswap(current.state, i, 1));
                        result.add(arrayswap(current.state, i, 3));
                        break;
        }
        return result;
    }

    private static Node arrayswap(int[] myarray, int x, int y){
        int[] result = new int[9];
        System.arraycopy(myarray, 0, result, 0, 9);

        int temp = result[x];
        result[x] = result[y];
        result[y] = temp;

        Node n = new Node(result);
        return n;
    }

    //print out solution
    private static void printout(ArrayList<Node> solution){
        String result = "\nSolution:\n";
        Iterator<Node> itr = solution.iterator();
        Node previous, current;
        current = itr.next();
        while(itr.hasNext()){
            previous = current;
            current = itr.next();
            String action = getAction(previous, current);
            result = result + previous.prettyPrintState(action) + '\n';
            if(!itr.hasNext()) result += current.prettyPrintState("Goal!");
        }
        System.out.println(result);
    }

    //action sequence -- assumes that it's given valid input
    private static String getAction(Node parent, Node child){
        if(Arrays.equals(parent.state, child.state)) return "";

        int initValue = 0;
        int finalValue = 0;
        boolean trigger = false;
        
        //find discrepances -- assumes that it's given valid input
        for(int i = 0; i < 9; i++){
            if(parent.state[i] == 0) initValue = i;
            else if(child.state[i] != parent.state[i]) finalValue = i;
        }

        //rows
        if(finalValue/3 - initValue/3 < 0) return "moving tile down";
        else if(finalValue/3 - initValue/3 > 0) return "moving tile up";

        //columns
        else if(finalValue%3 - initValue%3 < 0) return "moving tile right";
        else if(finalValue%3 - initValue%3 > 0) return "moving tile left";
        
        System.out.println("Oops!");
        System.exit(1);
        return "";
    }
}
