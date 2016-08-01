# Solving 8-Puzzle with A* Search
There should be either 1 integers initially passed to this program
First parameter selects heuristic: 
    1 = Manhattan Style
    2 = Tile Switches Remaning

In the next two lines, the program will expect a 
start state and end state, respectively.

Start/End state formatting:
    one 8-Puzzle configuration per line with single white-space separated tiles. 
    Each tile is specified from top-left to bottom-right with 0 indicating the blank space.

    eg. 0 1 2 3 4 5 6 7 8

# How to Run (Example):
> javac asearch.java
> java asearch 0
> Enter Start State: 0 1 2 3 4 5 6 7 8
> Enter Goal State: 3 1 2 4 0 5 6 7 8

# Note
Currently the program will hang if there is no solution.