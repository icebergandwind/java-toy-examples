package maze;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class maze {

    private static final int right = 0;
    private static final int down = 1;
    private static final int left = 2;
    private static final int up = 3;
    
    public static int Size;    
    
    public static class Point {  // a Point is a position in the maze

        public int x, y;
        public boolean visited;   // for DFS
        public Point parent;      // for DFS
        public boolean flag;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void copy(Point p) {
            this.x = p.x;
            this.y = p.y;
        }
    }
    
    public static class Set {
    		int up, weight;
    		
    		public Set(int up, int weight) {
    				this.up = up;
    				this.weight = weight;
    		}
    }
    
    public static class Edge { 
		// an Edge is a link between two Points: 
		// For the grid graph, an edge can be represented by a point and a direction.
		Point point;
		int direction;
		boolean used;     // for maze creation
		boolean deleted;  // for maze creation
		
		public Edge(Point p, int d) {
	            this.point = p;
	            this.direction = d;	    
	            this.used = false;
	            this.deleted = false;
	        }
	    }

    // A board is an SizexSize array whose values are Points                                                                                                           
    public static Point[][] board;
    
    // A graph is simply a set of edges: graph[i][d] is the edge 
    // where i is the index for a Point and d is the direction 
    public static Edge[][] graph;
    public static Set[] set;
    public static int N;   // number of points in the graph
    
    public static void displayInitBoard() {
        System.out.println("\nInitial Configuration:");

        for (int i = 0; i < Size; ++i) {
            System.out.print("    -");
            for (int j = 0; j < Size; ++j) System.out.print("----");
            System.out.println();
            if (i == 0) System.out.print("Start");
            else System.out.print("    |");
            for (int j = 0; j < Size; ++j) {
                if (i == Size-1 && j == Size-1)
                	System.out.print("   End");
                else System.out.print("   |");
            }
            System.out.println();
        }
        System.out.print("    -");
        for (int j = 0; j < Size; ++j) System.out.print("----");
        System.out.println();
    }

    public static void displayMaze() {
	      System.out.println("\nMaze Configuration:");
	
	      for (int i = 0; i < Size; ++i) {
	          System.out.print("    -");
	          for (int j = 0; j < Size; ++j) {
	          	if (graph[i*Size+j][up].deleted == true)
	          		System.out.print("    ");
	          	else
	          		System.out.print("----");
	          }
	          System.out.println();
	          if (i == 0) System.out.print("Start");
	          else System.out.print("    |");
	          for (int j = 0; j < Size; ++j) {
	              if (i == Size-1 && j == Size-1)
	              	System.out.print(" * End");
	              else {
	              	if (board[i][j].flag == true) {
		              	if (graph[i*Size+j][right].deleted == true)
		              		System.out.print(" *  ");
		              	else
		              		System.out.print(" * |");
	              	}
	              	else{
		              	if (graph[i*Size+j][right].deleted == true)
		              		System.out.print("    ");
		              	else
		              		System.out.print("   |");
		              }
	              }
	          }
	          System.out.println();
	      }
	      System.out.print("    -");
	      for (int j = 0; j < Size; ++j) System.out.print("----");
	      System.out.println();
    }
    
    // weighted union
    public static void W_Union (int i, int j) {
    	// Pre: i and j are roots
    	int wi = set[i].weight;
    	int wj = set[j].weight;
    	if (wi < wj) {
    		set[i].up = j;
    		set[j].weight = wi + wj;
    	}
    	else {
    		set[j].up = i;
    		set[i].weight = wi + wj;
    	}
    }
    
    // path compression find
    public static int PC_Find(int i) {
    	int r = i;
    	while (set[r].up != 0) // find root
    		r = set[r].up;
    	if (i != r) { // compress path
    		int k = set[i].up;
    		while (k != r) {
    			set[i].up = r;
    			i = k;
    			k = set[k].up;
    		}
    	}
    	
    	return r;
    }
    
    // DFS
    public static void DFS(Point point, int Size) {
    	point.visited = true;
    	for(int i = 0; i < 4; ++i){
    		if(graph[point.x*Size+point.y][i].deleted == true){
    			if(i==0 && board[point.x][point.y+1].visited != true && point.y < Size-1){
    				board[point.x][point.y+1].parent = board[point.x][point.y];
    				DFS(board[point.x][point.y+1], Size);
    			}
    			if(i==1 && board[point.x+1][point.y].visited != true && point.x < Size-1){
    				board[point.x+1][point.y].parent = board[point.x][point.y];
    				DFS(board[point.x+1][point.y], Size);
    			}
    			if(i==2 && board[point.x][point.y-1].visited != true && point.y > 0){
    				board[point.x][point.y-1].parent = board[point.x][point.y];
    				DFS(board[point.x][point.y-1], Size);
    			}
    			if(i==3 && board[point.x-1][point.y].visited != true && point.x > 0){
    				board[point.x-1][point.y].parent = board[point.x][point.y];
    				DFS(board[point.x-1][point.y], Size);
    			}
    		}
    	}
    }
    
    public static void main(String[] args) {
         
		Scanner scan = new Scanner(System.in);
	         
		try {
		     
		    System.out.println("What's the size of your maze? ");
		    Size = scan.nextInt();
	         
		    Edge dummy = new Edge(new Point(0, 0), 0);
		    dummy.used = true;
		    dummy.point.visited = true;
		     
		    board = new Point[Size][Size];
		    N = Size*Size;  // number of points
		    graph = new Edge[N][4];
		    set = new Set[N];
		    		    
		    for (int i = 0; i < Size; ++i) 
		    	for (int j = 0; j < Size; ++j) {
			    Point p = new Point(i, j);
			    int pindex = i*Size+j;   // Point(i, j)'s index is i*Size + j
			     
			    board[i][j] = p;
			    set[pindex] = new Set(0, 1);
			     
			    graph[pindex][right] = (j < Size-1)? new Edge(p, right): dummy;
			    graph[pindex][down] = (i < Size-1)? new Edge(p, down) : dummy;        
			    graph[pindex][left] = (j > 0)? graph[pindex-1][right] : dummy;         
			    graph[pindex][up] = (i > 0)? graph[pindex-Size][down] : dummy;
	
		    	}
		    
		    displayInitBoard();
	         	    
		    int count = 0;
		    for(int i = 0; i < N; ++i)
		    	if (set[i].up == 0)
		    		count++;
		    
		    while (count > 1){
		    	Random rand = new Random();
		    	int a = rand.nextInt(N);
		    	int b = rand.nextInt(4);
	        while(graph[a][b].used == true) {
	        	a = rand.nextInt(N);
	        	b = rand.nextInt(4);
	        }
	        int i = a;
	        int j = 0;
	        if (b == 0 && a%Size != Size-1) j = a + 1;
	        if (b == 1 && a < (Size-1)*Size) j = a + Size;
	        if (b == 2 && a%Size != 0) j = a - 1;
	        if (b == 3 && a > Size -1) j = a - Size;
	        int u = PC_Find(i);
	        int v = PC_Find(j);
	        if (u != v) {
	        	W_Union(u, v);
	        	graph[a][b].deleted = true;
	        }
	        else
	        	graph[a][b].used = true;
	        
	        count = 0;
			    for(int k = 0; k < N; ++k)
			    	if (set[k].up == 0)
			    		count++;
		    }
		    
		    // DFS to find a path from Start to End
		    board[0][0].parent = new Point(0,0);
		    DFS(board[0][0], Size);
		    Point temp = board[Size-1][Size-1];
		    while (temp != board[0][0]) {
		    	temp.flag = true;
		    	temp = temp.parent;
		    }
		    temp.flag = true;
		    
		    displayMaze();
	         
		}
		catch(Exception ex){
		    ex.printStackTrace();
		}
		scan.close();
	  }    
}

