

import java.util.Stack;

public class Maze1 
{

    public int[][] maze;
    public boolean[][] hasVisited;
    
    public static final String BG_ANSI_WHITE = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BG_ANSI_CYAN = "\u001B[46m";
    public static final String BG_ANSI_BLUE = "\u001B[44m";
    public static final String BG_ANSI_YELLOW = "\u001B[43m"; 
    public static final String BG_ANSI_GREEN = "\u001B[42m";
    
    Stack<Pos> path = new Stack<Pos>();
    
    Pos current_pos; 
    Pos next_pos;
    public int[][] move = {{0,2},{0,-2},{-2,0},{2,0}}; //up, down, left, right
        
    public Maze1(int rows, int cols)
    {
        maze = new int[rows][cols]; // 0: #, 1: ., 2: Coins, 3: @, 4: hidden Exit, 5: Exit 
        hasVisited = new boolean[rows][cols];
        for(int i=0; i<maze.length; i++)
        {
            for(int j=0; j<maze[0].length; j++)
            {
                maze[i][j] = 0;
                hasVisited[i][j] = false;
            }
        }

        current_pos = new Pos ((int)(Math.random()*maze.length),(int)(Math.random()*maze[0].length)); //start
    }      
    
    public void checkIntial()
    {
        for(int i=0;i<21;i++)
            for(int j = 0;j<70;j++)
                hasVisited[i][j] = false;
    }
    
    public void findPath()
    {
        path.push(current_pos);
        while(!path.isEmpty())
        {
            Pos[] neighbor = isValid(current_pos);
            if(neighbor.length == 0) //if there are no valid next pos
            {
                current_pos = path.pop();
                continue;
            }
            next_pos = neighbor[(int)(Math.random()*(neighbor.length))]; 
  
            if(hasVisited[next_pos.x][next_pos.y])
            {
                current_pos = path.pop();
            }
            else
            {
                path.push(next_pos);
                hasVisited[next_pos.x][next_pos.y] = true;
                maze[next_pos.x][next_pos.y] = 1;
                maze[(current_pos.x + next_pos.x) / 2][(current_pos.y + next_pos.y) / 2] = 1; 
                current_pos = next_pos;
            }        
        }    
    }
    
    Pos[] isValid(Pos pos)
    {
        Stack<Pos> Evaluate = new Stack<Pos>();
        for(int i = 0; i < move.length; i++)
        {
            int x = pos.x + move[i][0];
            int y = pos.y + move[i][1];
            if( x >= 0 && x <maze.length && y >= 0 && y < maze[0].length) //should in the maze
            {
                if(!hasVisited[x][y])
                {
                    Evaluate.add(new Pos(x,y));
                }
            }
        }
        
        Pos[] ans = new Pos[Evaluate.size()];
        for(int i = 0; i < Evaluate.size(); i++)
        {
            ans[i] = Evaluate.get(i);
        }
        return ans;
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
                else if (maze[i][j]==1 || maze[i][j]==4)
                    System.out.print(BG_ANSI_GREEN + "." + ANSI_RESET );
                else if (maze[i][j]==3)
                    System.out.print(BG_ANSI_BLUE + "@" + ANSI_RESET );
                else if (maze[i][j]==2)
                    System.out.print(BG_ANSI_YELLOW + "C" + ANSI_RESET );
                else if (maze[i][j]==5)
                    System.out.print(BG_ANSI_CYAN + "E" + ANSI_RESET );
            }
            
            System.out.println();
        }
    }
    public Pos setUser()
    {
        int UserX = (int)(Math.random()*maze.length);
        int UserY = (int)(Math.random()*maze[0].length);
        while (maze[UserX][UserY]!=3)
        {
            if (maze[UserX][UserY] == 1)
            {
                maze[UserX][UserY] = 3;
            }
            else
            {
                UserX = (int)(Math.random()*maze.length);
                UserY = (int)(Math.random()*maze[0].length);
            }
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
        int ExitY = (int)(Math.random()*maze[0].length);
        while (maze[ExitX][ExitY]!=4)
        {
            if (maze[ExitX][ExitY] == 1)
            {
                maze[ExitX][ExitY] = 4;
            }
            else
            {
                ExitX = (int)(Math.random()*maze.length);
                ExitY = (int)(Math.random()*maze[0].length);
            }
        }
        Pos Exit = new Pos(ExitX,ExitY);
        return Exit;
    }
}

