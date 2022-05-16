
import java.util.Scanner;

public class MazeCreate4 
{

    public static int[][] move = {{-1,0},{1,0},{0,-1},{0,1}}; //up, down, left, right
    
    public MazeCreate4(int completedTurns)
    {
        Scanner scan = new Scanner (System.in);
        
        KruskalMaze maze = new KruskalMaze(21,75);
        maze.findPath();
        Pos User = maze.setUser();
        int count = completedTurns;
        Pos[] Zombies = new Pos[count*4];
        for (int z=0; z < Zombies.length; z++)
            Zombies[z] = maze.setZombie();
        Pos Exit = maze.setExit();
        for (int coins=0; coins < 10; coins++)
            maze.setCoin();
        maze.drawMaze(); 
        
        int steps = 100;
        boolean stop = false;
        while (steps != 0 && !stop)
        {
            System.out.print("Move(s): "+steps+"\t(U)p, {L}eft, (D)own, (R)ight: ");
            String input = scan.nextLine();
            if (input.toUpperCase().equals("U") ) 
            {
                User = goUp(maze.maze, User, maze);
                if (maze.maze[User.x][User.y] == 2)
                {
                    steps = steps+100;
                    maze.setCoin();
                }     
                if(maze.maze[User.x][User.y] == 5)
                    User = maze.setUser();
                maze.maze[User.x][User.y] = 3;
            }
            else if (input.toUpperCase().equals("L"))
            {                            
                User = goLeft(maze.maze, User, maze);
                if (maze.maze[User.x][User.y] == 2)
                {
                    steps = steps+100;
                    maze.setCoin();
                }
                if(maze.maze[User.x][User.y] == 5)
                    User = maze.setUser();
                
                maze.maze[User.x][User.y] = 3;
            }
            else if (input.toUpperCase().equals("D"))
            {
                User = goDown(maze.maze, User, maze);
                if (maze.maze[User.x][User.y] == 2)
                {
                    steps = steps+100;
                    maze.setCoin();
                }
                if(maze.maze[User.x][User.y] == 5)
                    User = maze.setUser();
                
                maze.maze[User.x][User.y] = 3;

            }
            else if (input.toUpperCase().equals("R"))
            {
                User = goRight(maze.maze, User, maze);
                if (maze.maze[User.x][User.y] == 2)
                {
                    steps = steps+100;
                    maze.setCoin();
                }
                if(maze.maze[User.x][User.y] == 5)
                    User = maze.setUser();

                maze.maze[User.x][User.y] = 3;                
            }
            
            steps--;

            for (int z=0; z < Zombies.length; z++)
            {
                if (check(Zombies[z],maze.maze))
                {
                    boolean isValid = false;
                    while (!isValid)
                    {
                    
                        int d = (int)(Math.random()*move.length);
                        int x = Zombies[z].x + move[d][0];
                        int y = Zombies[z].y + move[d][1];
                        if ((x >= 0 && x <maze.maze.length && y >= 0 && y < maze.maze[0].length)&& maze.maze[x][y]==1)
                        {
                            maze.maze[Zombies[z].x][Zombies[z].y] =1;
                            Zombies[z] = new Pos(x,y);
                            maze.maze[Zombies[z].x][Zombies[z].y] = 5;    
                            isValid = true;
                        }
                    }    
                }
            }
            
            System.out.println();
            maze.drawMaze();
            
            if ((User.x==Exit.x)&&(User.y==Exit.y))
            {
                System.out.println("You complete the level!");
                stop = true;
            }
        }
        
    }
    public boolean check(Pos Zombies, int[][] maze)
    { 
        for (int i=0; i<move.length; i++)
        {
            int x = Zombies.x + move[i][0];
            int y = Zombies.y + move[i][1];
            if ((x >= 0 && x <maze.length && y >= 0 && y < maze[0].length)&&maze[x][y]==1)
                return true;
        }
        return false;
    }
    
    
    public Pos goUp (int[][] maze, Pos current, KruskalMaze M)
    {
        maze[current.x][current.y] = 1;
        

        if ((current.x+move[0][0] >= 0 && current.x+move[0][0] < maze.length && current.y+move[0][1] >= 0 && current.y+move[0][1] < maze[0].length)
            && maze[current.x+move[0][0]][current.y+move[0][1]]!=0)
        {
            current.x = current.x+move[0][0];
            current.y = current.y+move[0][1]; 

        }
        return current;
    }
    
    public Pos goDown (int[][] maze, Pos current, KruskalMaze M)
    {
        maze[current.x][current.y] = 1;
                      
        if ((current.x+move[1][0] >= 0 && current.x+move[1][0] < maze.length && current.y+move[1][1] >= 0 && current.y+move[1][1] < maze[0].length)
                && maze[current.x+move[1][0]][current.y+move[1][1]]!=0)
        {
            current.x = current.x+move[1][0];
            current.y = current.y+move[1][1];   

        }
        
        return current;
    }

    public Pos goLeft (int[][] maze, Pos current, KruskalMaze M)
    {
        maze[current.x][current.y] = 1;
                    
        if ((current.x+move[2][0] >= 0 && current.x+move[2][0] <maze.length && current.y+move[2][1] >= 0 && current.y+move[2][1] < maze[0].length)
                && maze[current.x+move[2][0]][current.y+move[2][1]]!=0)
        {
            current.x = current.x+move[2][0];
            current.y = current.y+move[2][1]; 

        }
        
        return current;
    }
    
    public Pos goRight (int[][] maze, Pos current, KruskalMaze M)
    {
        maze[current.x][current.y] = 1;
                      
        if ((current.x+move[3][0] >= 0 && current.x+move[3][0] <maze.length && current.y+move[3][1] >= 0 && current.y+move[3][1] < maze[0].length)
                && maze[current.x+move[3][0]][current.y+move[3][1]]!=0)
        {
            current.x = current.x+move[3][0];
            current.y = current.y+move[3][1];   
        }
        
        return current;
    }
    
}
