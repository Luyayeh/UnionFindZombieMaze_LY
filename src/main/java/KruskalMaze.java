
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c2786
 */
public class KruskalMaze 
{
    public int[][] maze;
    GraphEdge<String> edge;
    Queue<GraphEdge<String>> heap = new PriorityQueue<>();
    UnionFind<String> uf = new UnionFind<>();
    ArrayList<GraphEdge<String>> SpanningTree = new ArrayList<>();
    
    public static final String BG_ANSI_WHITE = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BG_ANSI_CYAN = "\u001B[46m";
    public static final String BG_ANSI_BLUE = "\u001B[44m";
    public static final String BG_ANSI_YELLOW = "\u001B[43m"; 
    public static final String BG_ANSI_GREEN = "\u001B[42m";
    public static final String BG_ANSI_RED = "\u001B[41m";
    public static final String BG_ANSI_PURPLE = "\u001B[45m";
    
    public KruskalMaze(int rows, int cols)
    {
        maze = new int[rows][cols]; // 0: #, 1: ., 2: Coins, 3: @, 4: Exit, 5: Zombie
        for(int i=0; i<maze.length; i++)
        {
            for(int j=0; j<maze[0].length; j++)
            {
                maze[i][j] = 0;
            }
        }
    }   
    public void findPath()
    {
        for (int row=0; row < maze.length; row=row+2)
        {
            for(int col=0; col < maze[0].length; col=col+2)
            {
                if (col+2 <  maze[0].length)
                {
                    edge = new GraphEdge<>();
                    edge.v1 = row+","+col;
                    edge.v2 = row+","+(col+2);
                    edge.e = (int)(Math.random()*1000);
                    heap.add(edge);
                }
            }
        }
        
        for (int col=0; col < maze[0].length; col=col+2)
        {
            for (int row=0; row < maze.length; row=row+2)
            {
                if (row+2 <  maze.length)
                {
                    edge = new GraphEdge<>();
                    edge.v1 = row+","+col;
                    edge.v2 = (row+2)+","+col;
                    edge.e = (int)(Math.random()*1000);
                    heap.add(edge);
                }
            }
        }
        
        while(!heap.isEmpty())
        {           
            edge = heap.remove();
            Integer set1 = uf.find(edge.v1);
            Integer set2 = uf.find(edge.v2);
            if (set1 == null && set2 == null)
            {
                set1 = uf.add(edge.v1);
                set2 = uf.add(edge.v2);
                uf.union(set1,set2);
                SpanningTree.add(edge);
            }
            else if (set1 != null && set2 == null)
            {
                set2 = uf.add(edge.v2);
                uf.union(set1, set2);
                SpanningTree.add(edge);
            }
            else if (set1 == null && set2 != null)
            {
                set1 = uf.add(edge.v1);
                uf.union(set1, set2);
                SpanningTree.add(edge);
            }
            else if (set1!=null && set2!=null && set1!=set2)
            {
                uf.union(set1, set2);
                SpanningTree.add(edge);
            }
        }
        
        Pos start = new Pos();
        Pos end = new Pos();
        for (int i=0; i<SpanningTree.size();i++)
        {
            edge = SpanningTree.get(i);
            String str1 = edge.v1;
            String array1[]= str1.split(",");
            start.x = Integer.parseInt(array1[0]);
            start.y = Integer.parseInt(array1[1]);
            String str2 = edge.v2;
            String array2[]= str2.split(",");
            end.x = Integer.parseInt(array2[0]);
            end.y = Integer.parseInt(array2[1]);
            //System.out.println(start.x+","+start.y+" "+end.x+","+end.y);
            maze[start.x][start.y] = 1;
            maze[end.x][end.y] = 1;
            maze[(start.x + end.x) / 2][(start.y + end.y) / 2] = 1; 
        }
    }
    public void drawMaze()
    {
        for(int i=0; i<maze.length; i++)
        {
            for(int j=0; j<maze[0].length; j++)
            {                
                //System.out.print( maze[i][j] ? BG_ANSI_WHITE + "#" + ANSI_RESET : BG_ANSI_CYAN + "." + ANSI_RESET );
                if (maze[i][j]==0)
                    System.out.print(BG_ANSI_WHITE + "#" + ANSI_RESET );
                else if (maze[i][j]==1)
                    System.out.print(BG_ANSI_CYAN + "." + ANSI_RESET );
                else if (maze[i][j]==3)
                    System.out.print(BG_ANSI_GREEN + "@" + ANSI_RESET );
                else if (maze[i][j]==2)
                    System.out.print(BG_ANSI_YELLOW + "C" + ANSI_RESET );
                else if (maze[i][j]==4)
                    System.out.print(BG_ANSI_RED + "E" + ANSI_RESET );
                else if (maze[i][j]==5)
                    System.out.print(BG_ANSI_PURPLE + "Z" + ANSI_RESET );
            }
            
            System.out.println();
        }
    }
    public Pos setUser()
    {
        int UserX = (int)(Math.random()*maze.length);
        int UserY = 0;
        while (maze[UserX][UserY]!=3)
        {
            if (maze[UserX][UserY] == 1)
            {
                maze[UserX][UserY] = 3;
            }
            else
                UserX = (int)(Math.random()*maze.length);
        }
        Pos User = new Pos(UserX,UserY);
        return User;
    }
    public Pos setCoin()
    {
        int CoinX = (int)(Math.random()*maze.length);
        int CoinY = (int)(Math.random()*maze[0].length);
        while (maze[CoinX][CoinY] != 2)
        {
            if (maze[CoinX][CoinY] == 1)
            {
                maze[CoinX][CoinY] = 2;
            }
            else
            {
                CoinX = (int)(Math.random()*maze.length);
                CoinY = (int)(Math.random()*maze[0].length);
            }
        }
        Pos Coin = new Pos(CoinX, CoinY);
        return Coin;
    }
    public Pos setExit()
    {  
        int ExitX = (int)(Math.random()*maze.length);
        int ExitY = maze[0].length-1;
        while (maze[ExitX][ExitY]!=4)
        {
            if (maze[ExitX][ExitY] == 1)
            {
                maze[ExitX][ExitY] = 4;
            }
            else
                ExitX = (int)(Math.random()*maze.length);
        }
        Pos Exit = new Pos(ExitX,ExitY);
        return Exit;
    }
    public Pos setZombie()
    {  
        int ZombieX = (int)(Math.random()*maze.length);
        int ZombieY = (int)(Math.random()*maze[0].length);
        while (maze[ZombieX][ZombieY]!=5)
        {
            if (maze[ZombieX][ZombieY] == 1)
            {
                maze[ZombieX][ZombieY] = 5;
            }
            else
            {
                ZombieX = (int)(Math.random()*maze.length);
                ZombieY = (int)(Math.random()*maze[0].length);
            }
        }
        Pos Zombie = new Pos(ZombieX,ZombieY);
        return Zombie;
    }
    
}
