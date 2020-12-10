package fourinarow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class fourinarow {

    private static final int playerX = 1;
    private static final int playerO = 2;

    private static int opponent(int player) {
        return 3-player;
    }
    
    private static int fromLevel(int level) {
    	if (level % 2 == 1) return playerX; else return playerO;  
    	// if level is odd, return playerX; else ...
    }

    class Point {

        int x, y;

        public Point(int x, int y) {
            this.x = x;
	        this.y = y;
        }

        public void copy(Point p) {
            this.x = p.x;
            this.y = p.y;
        }
    }

    // A board is 5x5 array whose values are 0 (empty),                                                               
    // 1 (taken by playerX), or 2 (taken by playerO)                                                                  
    int[][] board = new int[5][5];

    Point bestMove = new Point(0, 0);
    
    // test if neighbor is filled
    public boolean neighbor(int i, int j){
    	int sum = 0;
    	if (i-1 >= 0) {
    		sum += board[i-1][j];
    		if (j-1 >= 0) sum = sum + board[i-1][j-1] + board[i][j-1];
    		if (j+1 <= 4) sum = sum + board[i-1][j+1] + board[i][j+1];
    	}
    	if (i+1 <= 4) {
    		sum += board[i+1][j];
    		if (j-1 >= 0) sum += board[i+1][j-1];
    		if (j+1 <= 4) sum += board[i+1][j+1];
    	}
    	if (sum > 0)
    		return true;
    	else
    		return false;
    }
    
    //Get the playable locations available on the board                                                                   
    public List<Point> getAvailablePositions() {
    	List<Point> availablePoints = new ArrayList<Point>();
    		
        for (int i = 0; i < 5; ++i) 
           for (int j = 0; j < 5; ++j) 
                if (board[i][j] == 0 && neighbor(i,j)) {
                    availablePoints.add(new Point(i, j));
                    //System.out.print("("+i+","+j+")");
                }
        //System.out.println();
       return availablePoints;
    }
    
    public boolean isGameOver() {
        // Game is over when someone has won, or board is full (draw)
        return (hasWon(playerX) || hasWon(playerO) || (getAvailablePositions().isEmpty() && board[0][0] !=0) || (NoWin(playerX) && NoWin(playerO)));
    }
    
    //if your opponent has no chance to win
    public boolean NoWin(int player) {
      if ((board[0][0] != player && board[1][1] != player && board[2][2] != player && board[3][3] != player) ||
      		(board[1][1] != player && board[2][2] != player && board[3][3] != player && board[4][4] != player) ||
      		(board[1][0] != player && board[2][1] != player && board[3][2] != player && board[4][3] != player) ||
      		(board[0][1] != player && board[1][2] != player && board[2][3] != player && board[3][4] != player) ||
      		(board[0][4] != player && board[1][3] != player && board[2][2] != player && board[3][1] != player) ||
      		(board[1][3] != player && board[2][2] != player && board[3][1] != player && board[4][0] != player) ||
      		(board[0][3] != player && board[1][2] != player && board[2][1] != player && board[3][0] != player) ||
      		(board[1][4] != player && board[2][3] != player && board[3][2] != player && board[4][1] != player)) {                     
          return false;
      }

      for (int i = 0; i < 5; ++i) {
          if ((board[i][0] != player && board[i][1] != player && board[i][2] != player && board[i][3] != player) ||
          		(board[i][1] != player && board[i][2] != player && board[i][3] != player && board[i][4] != player) ||
              (board[0][i] != player && board[1][i] != player && board[2][i] != player && board[3][i] != player) ||
              (board[1][i] != player && board[2][i] != player && board[3][i] != player && board[4][i] != player)) {
              return false;
          }
      }

      return true;
  }
      
    public boolean hasWon(int player) {
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player && board[3][3] == player) ||
        		(board[1][1] == player && board[2][2] == player && board[3][3] == player && board[4][4] == player) ||
        		(board[1][0] == player && board[2][1] == player && board[3][2] == player && board[4][3] == player) ||
        		(board[0][1] == player && board[1][2] == player && board[2][3] == player && board[3][4] == player) ||
        		(board[0][4] == player && board[1][3] == player && board[2][2] == player && board[3][1] == player) ||
        		(board[1][3] == player && board[2][2] == player && board[3][1] == player && board[4][0] == player) ||
        		(board[0][3] == player && board[1][2] == player && board[2][1] == player && board[3][0] == player) ||
        		(board[1][4] == player && board[2][3] == player && board[3][2] == player && board[4][1] == player)) {
            //System.out.println("player" + player + " Diagonal Win");                     
            return true;
        }

        for (int i = 0; i < 5; ++i) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player && board[i][3] == player) ||
            		(board[i][1] == player && board[i][2] == player && board[i][3] == player && board[i][4] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player && board[3][i] == player) ||
                (board[1][i] == player && board[2][i] == player && board[3][i] == player && board[4][i] == player)) {
                //System.out.println("player" + player + "Row or Column win"); 
                return true;
            }
        }

        return false;
    }
    
    // if there are 3 consecutive pieces in the middle of a row (column, diagonal)
    public boolean Consecutive3(int player) {
    	if ((board[1][1] == player && board[2][2] == player && board[3][3] == player && board[0][0] != opponent(player) && board[4][4] != opponent(player)) ||
    			(board[3][1] == player && board[2][2] == player && board[1][3] == player && board[0][4] != opponent(player) && board[4][0] != opponent(player))) {
    		return true;
    	}
    	for (int i = 0; i < 5; i++) {
    		if ((board[i][1] == player && board[i][2] == player && board[i][3] == player && board[i][0] != opponent(player) && board[i][4] != opponent(player)) ||
    				(board[1][i] == player && board[2][i] == player && board[3][i] == player && board[0][i] != opponent(player) && board[4][i] != opponent(player))) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // if there are 2 consecutive pieces in the middle of a row, without blocking
    public boolean Consecutive2H(int player) {
    	for (int i = 0; i < 5; i++) {
    		if ((board[i][1] == player && board[i][2] == player && board[i][3] != opponent(player) && board[i][0] != opponent(player) && board[i][4] != opponent(player)) ||
    				(board[i][2] == player && board[i][3] == player && board[i][1] != opponent(player) && board[i][0] != opponent(player) && board[i][4] != opponent(player))) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // if there are 2 consecutive pieces in the middle of a column, without blocking
    public boolean Consecutive2V(int player) {
    	for (int i = 0; i < 5; i++) {
    		if ((board[1][i] == player && board[2][i] == player && board[3][i] != opponent(player) && board[0][i] != opponent(player) && board[4][i] != opponent(player)) ||
    				(board[2][i] == player && board[3][i] == player && board[1][i] != opponent(player) && board[0][i] != opponent(player) && board[4][i] != opponent(player))) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // if there are 2 consecutive pieces in the middle of a diagonal (L), without blocking
    public boolean Consecutive2L(int player) {
    	if ((board[1][1] == player && board[2][2] == player && board[3][3] != opponent(player) && board[0][0] != opponent(player) && board[4][4] != opponent(player)) ||
    			(board[2][2] == player && board[3][3] == player && board[1][1] != opponent(player) && board[0][0] != opponent(player) && board[4][4] != opponent(player))) {
    		return true;
    	}
    	return false;
    }
    
    // if there are 2 consecutive pieces in the middle of a diagonal (R), without blocking
    public boolean Consecutive2R(int player) {
    	if ((board[3][1] == player && board[2][2] == player && board[1][3] != opponent(player) && board[0][4] != opponent(player) && board[4][0] != opponent(player)) ||
    			(board[2][2] == player && board[1][3] == player && board[3][1] != opponent(player) && board[0][4] != opponent(player) && board[4][0] != opponent(player))) {
    		return true;
    	}
    	return false;
    }
    
    public void makeMove(Point point, int player) {
        board[point.x][point.y] = player;   //player = 1 for X, 2 for O 
    }

    public void makeMove(int x, int y, int player) {
        board[x][y] = player;   //player = 1 for X, 2 for O 
    }
    
    public void undoMove(Point point) {
        board[point.x][point.y] = 0;
    }
    
    //if there are three pieces in a row (column, diagonal) with an opening fourth position
    public boolean possible3(int player){
    	List<Point> listPoints = getAvailablePositions();
    	for (Point point : listPoints) {
    		this.makeMove(point, player);
    		if (hasWon(player)) {
    			this.undoMove(point);
    			return true;
    		}
    		this.undoMove(point);
    	}
    	return false;
    }
   
    //The actual MinMax idea is in this function            
    public int minimaxScore (int depth) {
        List<Point> listPoints = getAvailablePositions();
        if (hasWon(playerX)) {
            return +10;
        } else if (hasWon(playerO)) {
            return -10;
        } else if (listPoints.isEmpty()) {
            return 0;
        } else {
            int bestScore, score, player;
            player = fromLevel(depth);
            if (player == playerX) bestScore = -9; else bestScore = 9;

            // Rule 14: limit the depth of the search tree
            if (depth == 6) {
            	int count = 0;
            	if (Consecutive3(player))
            		count += 3;
            	if (Consecutive2H(player))
            		count++;
            	if (Consecutive2V(player))
            		count++;
            	if (Consecutive2L(player))
            		count++;
            	if (Consecutive2R(player))
            		count++;
            	if (player == playerX)
            		return count;
            	else 
            		return -count;
            }
            	
            // Rule 1: If this move is a winning position, take it.                 
            for (Point point : listPoints) {                         
                this.makeMove(point, player);
                if (hasWon(player)) {
                	this.undoMove(point);
                    if (player == playerX) {
                       if (depth == 1) bestMove.copy(point);
                       return +8;
                    } else return -8;
                }
                this.undoMove(point);
            }
            
           
            // Rule 2: If this is the opponent's winning move, block it. 
            for (Point point : listPoints) {               
                this.makeMove(point, opponent(player));
                if (hasWon(opponent(player))) {
                	// We have to take this position, but we don't know the score of this move
                		this.makeMove(point, player);
                    if (player == playerX && depth == 1) bestMove.copy(point);
                    score = this.minimaxScore(depth+1);
                    this.undoMove(point);
                    return score;
                }
                this.undoMove(point);
            }
            
            // Rule 3: if this move creates three consecutive pieces, take it.
            for (Point point : listPoints) {
            	this.makeMove(point, player);
            	if (Consecutive3(player)) {
            		this.undoMove(point);
            		if (player == playerX) {
            			if (depth == 1) bestMove.copy(point);
            			return +7;
            		} else return -7;
            	}
            	this.undoMove(point);
            }
            
            // Rule 4: if this is the opponent's three consecutive move, block it.
            for (Point point : listPoints) {
            	this.makeMove(point, opponent(player));
            	if (Consecutive3(opponent(player))) {
            		this.makeMove(point, player);
            		if (player == playerX && depth == 1) bestMove.copy(point);
            		score = this.minimaxScore(depth+1);
            		this.undoMove(point);
            		return score;
            	}
            	this.undoMove(point);
            }
            
            // Rule 5: if this move meet Rule 9 and 10, take it
            for (Point point : listPoints) {
            	this.makeMove(point, player);
            	if (possible3(player) && 
            			(Consecutive2H(player) || Consecutive2V(player) || Consecutive2L(player) || Consecutive2R(player))) {
            		this.undoMove(point);
            		if (player == playerX) {
            			if (depth == 1) bestMove.copy(point);
            			return +6;
            		} else return -6;
            	}
            	this.undoMove(point);
            }
            
            // Rule 6: if the opponent meet Rule 9 and 10, block it
            for (Point point : listPoints) {
            	this.makeMove(point, opponent(player));
            	if (possible3(opponent(player)) && 
            			(Consecutive2H(opponent(player)) || Consecutive2V(opponent(player)) || Consecutive2L(opponent(player)) || Consecutive2R(opponent(player)))) {
            		this.makeMove(point, player);
            		if (player == playerX && depth == 1) bestMove.copy(point);
            		score = this.minimaxScore(depth+1);
            		this.undoMove(point);
            		return score;
            	}
            	this.undoMove(point);
            }
            
            // Rule 7: if this move meet Rule 10 twice, take it
            for (Point point : listPoints) {
            	this.makeMove(point, player);
            	if ((Consecutive2H(player) && Consecutive2V(player)) ||
            			(Consecutive2H(player) && Consecutive2L(player)) ||
            			(Consecutive2H(player) && Consecutive2R(player)) ||
            			(Consecutive2V(player) && Consecutive2L(player)) || 
            			(Consecutive2V(player) && Consecutive2R(player)) || 
            			(Consecutive2L(player) && Consecutive2R(player))) {
            		this.undoMove(point);
            		if (player == playerX) {
            			if (depth == 1) bestMove.copy(point);
            			return +5; 
            		} else return -5;
            	}
            	this.undoMove(point);
            }
            
            // Rule 8: if the opponent meet Rule 10 twice, block it
            for (Point point : listPoints) {
            	this.makeMove(point, opponent(player));
            	if ((Consecutive2H(opponent(player)) && Consecutive2V(opponent(player))) ||
            			(Consecutive2H(opponent(player)) && Consecutive2L(opponent(player))) ||
            			(Consecutive2H(opponent(player)) && Consecutive2R(opponent(player))) ||
            			(Consecutive2V(opponent(player)) && Consecutive2L(opponent(player))) || 
            			(Consecutive2V(opponent(player)) && Consecutive2R(opponent(player))) || 
            			(Consecutive2L(opponent(player)) && Consecutive2R(opponent(player)))) {
            		this.makeMove(point, player);
            		if (player == playerX && depth == 1) bestMove.copy(point);
            		score = this.minimaxScore(depth+1);
            		this.undoMove(point);
            		return score;
            	}
            	this.undoMove(point);
            }
            
            // Rule 9: if this move creates a wining chance, take it.
            for (Point point : listPoints) {
            	this.makeMove(point, player);
            	if (possible3(player)) {
            		this.undoMove(point);
            		if (player == playerX) {
            			if (depth == 1) bestMove.copy(point);
            			return +4;
            		} else return -4;
            	}
            	this.undoMove(point);
            }
            
            //Rule 10: if this move creates two consecutive pieces, take it.
            for (Point point : listPoints) {
            	this.makeMove(point, player);
            	if (Consecutive2H(player) || Consecutive2V(player) || Consecutive2L(player) || Consecutive2R(player)) {
            		this.undoMove(point);
            		if (player == playerX) {
            			if (depth == 1) bestMove.copy(point);
            			return +3; 
            		} else return -3;
            	}
            	this.undoMove(point);
            }
            
            // Rule 11: if this is the opponent's wining chance, block it.
            for (Point point : listPoints) {
            	this.makeMove(point, opponent(player));
            	if (possible3(opponent(player))) {
            		this.makeMove(point, player);
            		if (player == playerX && depth == 1) bestMove.copy(point);
            		score = this.minimaxScore(depth+1);
            		this.undoMove(point);
            		return score;
            	}
            	this.undoMove(point);
            }
                       
            //Rule 12: if this is the opponent's two consecutive move, block it.
            for (Point point : listPoints) {
            	this.makeMove(point, opponent(player));
            	if (Consecutive2H(opponent(player)) || Consecutive2V(opponent(player)) || Consecutive2L(opponent(player)) || Consecutive2R(opponent(player))) {
            		this.makeMove(point, player);
            		if (player == playerX && depth == 1) bestMove.copy(point);
            		score = this.minimaxScore(depth+1);
            		this.undoMove(point);
            		return score;
            	}
            	this.undoMove(point);
            }
            
            // Rule 13: Now minimax rules              
            for (Point point : listPoints) {                                                                              
            		this.makeMove(point, player);
                score = this.minimaxScore(depth+1);
                
                if (player == playerX) {
                	//if (depth < 3) System.out.println("1 score("+point.x+","+point.y+") = "+score);
                    if (score > bestScore) {
                    	if (depth == 1) bestMove.copy(point);
                        bestScore = score;
                    }
                } else { 
                	if (score < bestScore) bestScore = score;
                	//if (depth < 3) System.out.println("2 score("+point.x+","+point.y+") = "+score);
                }

                this.undoMove(point);
             }
             return bestScore;
         }
     }

     public void displayBoard() {
         System.out.println();

         for (int i = 0; i < 5; ++i) {
             System.out.println(" ---------------------");
             for (int j = 0; j < 5; ++j) {
                 System.out.print(" | " + ((board[i][j] == 0)? " " : ((board[i][j] == playerX)? "X" : "O")));
             }
             System.out.println(" |");
         }
         System.out.println(" ---------------------");
     }

     void placeFirstMove() {
         Random rand = new Random();
         Point p = new Point(1+rand.nextInt(3), 1+rand.nextInt(3));
         makeMove(p, playerX);
     }
 
     public static void main(String[] args) {

         fourinarow b = new fourinarow();
         b.displayBoard();
         Scanner scan = new Scanner(System.in);
         
         try {
        	 
             System.out.println("Who's gonna move first? (1) You : (2) Rot? ");
             int choice = scan.nextInt();
         
             if (choice == 2) {
                b.placeFirstMove();
                b.displayBoard();
             }

             while (!b.isGameOver()) {
            	   System.out.println("Your move (row=0-4; col=0-4): ");
                 int x = scan.nextInt();
                 int y = scan.nextInt();
                 if (x < 0 || x > 4 || y < 0 || y > 4 || b.board[x][y] != 0) {
            	     System.out.println("Invalid position! Try again.");
            	     continue;
                 }
                 b.makeMove(x, y, playerO);
                 if (b.isGameOver()) break;
                 b.displayBoard();

                 choice = b.minimaxScore(1);
                 System.out.println("Score("+b.bestMove.x+","+b.bestMove.y+") = "+choice);
                 b.makeMove(b.bestMove, playerX);
                 b.displayBoard();
 	     }

       if (b.hasWon(playerX)) {
                 System.out.println("Unfortunately, you lost!");
             } else if (b.hasWon(playerO)) {
                 System.out.println("Segmentation fault. Computer always wins or draws :)\n");
             } else {
                 System.out.println("It's a draw!");
             }
         }
         catch(Exception ex) {
             ex.printStackTrace();
         }
         scan.close();
     }    
}
