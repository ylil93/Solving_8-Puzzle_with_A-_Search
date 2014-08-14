/*	Lily Li
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
    private static int outputStyle;

    public static void main (String[] args) {

    	//correct num of arguments? -- works
    	if(args.length == 0 | args.length > 2){
    		System.err.println("Argument number must be 1 or 2");
    		System.exit(1);
    	}

        //are they integers? -- works
        int[] myArgs = new int[args.length];
        for (int i = 0; i < args.length; i++) {
			try {
    			myArgs[i] = Integer.parseInt(args[i]);
    		} catch (NumberFormatException e) {
    			System.err.println("Argument" + " must be an integer");
    			System.exit(1);
    		}
    	}

    	//is first argument valid? -- works
    	if(!(myArgs[0] == 1 | myArgs[0] == 2)){
    		System.err.println("arg1: Invalid Argument");
    		System.exit(1);
    	}
        heuristic = myArgs[0];

    	//is there a second argument? valid? -- works
        if(args.length == 2){
    		if(!(myArgs[1] == 0 | myArgs[1] ==1)){
    			System.err.println("arg2: Invalid Argument");
    			System.exit(1);
    		}
            outputStyle = myArgs[1];
    	} else outputStyle = 0;

    	//initialization1
    	int[] temp = new int[9];

    	//read in start state -- works
    	// System.out.print("Enter Start State: ");
    	Scanner scan = new Scanner(System.in);
    	for(int i = 0; i < 9; i++){
    		temp[i] = scan.nextInt();
    	}
    	startState = new Node(temp);
    	

    	//read in goal state -- works
		// System.out.print("Enter Goal State: ");
		for(int i = 0; i < 9; i++){
    		temp[i] = scan.nextInt();
    	}
    	goalState = new Node(temp);

    	// startState.printout1();
    	// goalState.printout1();

        //initialization2
        Comparator<Node> comparator = new NodeComparator(goalState, heuristic);
        PriorityQueue<Node> fringe = new PriorityQueue<Node>(10, comparator);
        HashSet<Node> expanded = new HashSet<Node>();
        
        fringe.add(startState);
        // startState.solution.add(startState);
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
                // System.out.println("not start state!");
                current.solution.add(previous);
                current.gvalue = previous.gvalue + 1;
            }
            else{
                // System.out.println("start state!");
                isStartState = false;
            }

            //is goal found?
            // System.out.println(Arrays.equals(goalState.state, current.state));
            if(Arrays.equals(goalState.state, current.state)){
                // System.out.println("GOAL FOUND!");
                current.solution.add(current);
                printout(current.solution, outputStyle);
                return;
            }

            //produce children -- DANGEROUS
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

/********************* vvv SHIT TO PRINT OUT vvv ********************/
            // String string1 = "";

            // //print out current.children
            // System.out.println("current.children: ");
            // string1 = "";
            // for(Node poop : current.children){
            //     string1 = printout1(poop);
            //     System.out.println(string1);
            // }

            // //print out current.solution
            // System.out.println("current.solution: ");
            // string1 = "";
            // for(Node poop : current.solution){
            //     string1 = printout1(poop);
            //     System.out.println(string1);
            // }

            // //print out fringe
            // string1 = "";
            // System.out.println("fringe:");
            // for(Node poop : fringe){
            //     System.out.println(printout1(poop));    
            // }

            // //print out expanded
            // System.out.println("expanded: ");
            // string1 = "";
            // Iterator<Node> itr = expanded.iterator();
            // while(itr.hasNext()){
            //     string1 = printout1(itr.next());
            //     System.out.println(string1);
            // }
/********************* ^^^ SHIT TO PRINT OUT ^^^ ********************/
        }
        System.out.println("nil");
    }

    //produce children -- works
    public static ArrayList<Node> produceChildren(Node current, int i){
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

    public static Node arrayswap(int[] myarray, int x, int y){
        int[] result = new int[9];
        System.arraycopy(myarray, 0, result, 0, 9);

        int temp = result[x];
        result[x] = result[y];
        result[y] = temp;

        Node n = new Node(result);
        return n;
    }

    //decides which outputstyle to use -- untested
    public static void printout(ArrayList<Node> solution, int outputStyle){
        String result = "";
        Iterator<Node> itr = solution.iterator();
        Node previous, current;
        if(outputStyle == 0){
            current = itr.next();
            while(itr.hasNext()){
                previous = current;
                current = itr.next();
                if(itr.hasNext()) result = result + getAction(previous, current) + ", ";
                else result = result + getAction(previous, current);
            }
        } else{
            while(itr.hasNext()){
                current = itr.next();
                if(itr.hasNext()) result = result + printout1(current) + '\n';
                else result = result + printout1(current);
            }
        }
        System.out.println(result);
    }

    //action sequence -- assumes that it's given valid input
    public static String getAction(Node parent, Node child){
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
        if(finalValue/3 - initValue/3 < 0) return "d";
        else if(finalValue/3 - initValue/3 > 0) return "u";

        //columns
        else if(finalValue%3 - initValue%3 < 0) return "r";
        else if(finalValue%3 - initValue%3 > 0) return "l";
        
        System.out.println("Oops!");
        System.exit(1);
        return "";
    }

    //state transition -- works
    public static String printout1(Node n){
        String result = "";
        for(int i = 0; i < 9; i++){
            if(i!=8) result = result + n.state[i] + " ";
            else  result = result + n.state[i];
        }
        return result;
    }
}

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
}

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

    //heuristic function -- not fully tested, probably works? (åŒã˜gvalueã§ works)
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

    //Manhattan distance -- works
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
    
    //tile switches remaining -- works
    private int heuristic2(int[] start, int[] goal){
        int result = 0;
        for(int i = 0; i < 9; i++){
            if(start[i] != 0){
                if(start[i] != goal[i]) result++;
            }
        }
        return result;
    }

    //settles ties -- works
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
