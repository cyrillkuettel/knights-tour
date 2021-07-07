# knights-tour

# What is a knight's tour?
## (classic problem)
It's a series of moves of a chess knight that visits all squares on the board exactly once.
 
# How can I run it?
1. Clone the project.
2. Locate the class [demoSearch](src/Backtracking/demoSearch.java). This is the driver class. You can set the starting position (x,y) of the Knight. Default board size is 8x8. This, too can be changed. 
I'm currently writing different algorithms to solve the problem of knigh's tour. This programm has a working backtracking implementation. 

# TODO
- Next, I will code a genetic algorithm. The main driving force, let's say _inspiration_, was this [wonderful paper](https://www.iiitb.ac.in/CSL/projects/Chitrakavya/downloads/01331065.pdf) 
- For backtracking: Replace List<Square> candidates with a Priority Queue? Might be even faster, because this approach does not need to sort the List.
 However, since the list.length will never exceed 8, it's unlikely to have any significant effect. 
  
![Preview](https://github.com/cyrillkuettel/knights-tour/blob/main/demo.png?raw=true)
