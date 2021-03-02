# Connect-4

This was completed as an extra credit assignment for my AI class at UTA. The Assignment and relavent information can be found [HERE](http://vlm1.uta.edu/~athitsos/courses/cse4308_summer2013/assignments/programming2/).

The program implements a depth limited minimax algorithm that uses alpha-beta pruning. A weighted linear sum evaluation function was used with the following weights: 
  ..* moves that produce a connect 4 are weighted by 100
  ..* moves that produce 3 in a row are weighted by 20
  ..* moves that produce 2 in a row are weighted by 10
