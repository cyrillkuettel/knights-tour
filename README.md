# What's knight's tour?
The knight's tour is series of moves of a chess knight that visits all squares on the board exactly once. It's a classical combinatorics problem. 
Many algorithms and heuristics have been developed to solve this problem. 
![Preview](https://github.com/cyrillkuettel/knights-tour/blob/main/demo.png?raw=true)

I implemented depth-first search, with a backtracking approach. I'm currently writing different algorithms to solve the problem of knigh's tour.

The important key insight was:
For each step, always visit the square with the _minimum number of onward moves_ first. This increases performance tremendously when compared with random selection.
 
This simple priority queue idea has made all the difference, reducing the total runtime from a 10 seconds to 50ms (!)

# How to build this application?
1. It's a plain java project. Just clone it and open it in your IDE.
2. Locate the class [demoSearch](src/backtracking/demoSearch.java). This is the driver class. You can set the starting position (x,y) of the Knight. Default board size is 8x8. This, too can be changed. 
3. To see the visualization, use [ChessBoard](src/backtracking/ChessBoard.java) class. This is experimental. There are some issues with saving the image. 




# TODO
- Next, I will code a genetic algorithm. The main driving force, let's say _inspiration_, was this [wonderful paper](https://www.iiitb.ac.in/CSL/projects/Chitrakavya/downloads/01331065.pdf) 
- For backtracking: Replace List<Square> candidates with a Priority Queue? Might be even faster, because this approach does not need to sort the List.
 However, since the list.length will never exceed 8, it's unlikely to have any significant effect. 
  
