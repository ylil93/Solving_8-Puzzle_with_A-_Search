# Solving 8-Puzzle with A* Search
Lily Li
Assignment 1: Search
02/14/2014
This program uses A* search algorithm to solve an 8-puzzle

There should be either 1 integers initially passed to this program
First parameter selects heuristic: 
* 1 = Manhattan Distance
* 2 = Tile Switches Remaning


In the next two lines, the program will expect a 
start state and end state, respectively.

###Start/End state formatting:
* One 8-Puzzle configuration per line with single white-space separated tiles. 
* Each tile is specified from top-left to bottom-right with 0 indicating the blank space.

## How to Run (Example):
```
> javac asearch.java
> java asearch 1
> Enter Start State: 0 1 2 3 4 5 6 7 8
> Enter Goal State: 3 1 2 4 0 5 6 7 8
```

## Bugs
Currently the program will hang if there is no solution.