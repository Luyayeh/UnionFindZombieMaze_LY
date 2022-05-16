
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c2786
 */
public class MazeCreate1 
{
    public static int[][] move = {{-1,0},{1,0},{0,-1},{0,1}}; //up, down, left, right
    public MazeCreate1()
    {
        Scanner scan = new Scanner (System.in);
        Maze1 maze = new Maze1(21,70);       
        maze.findPath();
        Pos User = maze.setUser();
        maze.setCoin();
        maze.setCoin();
        Pos Exit = maze.setExit();
        
        maze.drawMaze();
        
        boolean stop = false;
        int Coins = 0;

        while (!stop)
        {
            System.out.print("Coins: "+Coins+"\t(U)p, {L}eft, (D)own, (R)ight:  ");
            String input = scan.nextLine();
            if (input.toUpperCase().equals("U") ) 
            {
                User = goUp(maze.maze, User);
                if (maze.maze[User.x][User.y] == 2)
                    Coins++;
                
                maze.maze[User.x][User.y] = 3;
            }
            else if (input.toUpperCase().equals("L"))
            {
                User = goLeft(maze.maze, User);
                if (maze.maze[User.x][User.y] == 2)
                    Coins++;
                
                maze.maze[User.x][User.y] = 3;
            }
            else if (input.toUpperCase().equals("D"))
            {
                User = goDown(maze.maze, User);
                if (maze.maze[User.x][User.y] == 2)
                    Coins++;
                
                maze.maze[User.x][User.y] = 3;

            }
            else if (input.toUpperCase().equals("R"))
            {
                User = goRight(maze.maze, User);
                if (maze.maze[User.x][User.y] == 2)
                    Coins++;
                
                maze.maze[User.x][User.y] = 3;                
            }
            
            if (Coins == 2)
                maze.maze[Exit.x][Exit.y] = 5;
            
            System.out.println();
            maze.drawMaze();   
            if (maze.maze[User.x][User.y] == 5)
            {
                System.out.println("You complete the level!");
                stop = true;
            }
        }
    }
    
    public Pos goUp (int[][] maze, Pos current)
    {
        maze[current.x][current.y] = 1;
        if ((current.x+move[0][0] >= 0 && current.x+move[0][0] <maze.length && current.y+move[0][1] >= 0 && current.y+move[0][1] < maze[0].length)
            && maze[current.x+move[0][0]][current.y+move[0][1]]!=0)
        {
            current.x = current.x+move[0][0];
            current.y = current.y+move[0][1]; 
        }
        return current;
    }
    public Pos goDown (int[][] maze, Pos current)
    {
        maze[current.x][current.y] = 1;
                      
        if ((current.x+move[1][0] >= 0 && current.x+move[1][0] <maze.length && current.y+move[1][1] >= 0 && current.y+move[1][1] < maze[0].length)
                && maze[current.x+move[1][0]][current.y+move[1][1]]!=0)
        {
            current.x = current.x+move[1][0];
            current.y = current.y+move[1][1];   
        }
        return current;
    }

    public Pos goLeft (int[][] maze, Pos current)
    {
        maze[current.x][current.y] = 1;
                       
        if ((current.x+move[2][0] >= 0 && current.x+move[2][0] <maze.length && current.y+move[2][1] >= 0 && current.y+move[2][1] < maze[0].length)
                && maze[current.x+move[2][0]][current.y+move[2][1]]!=0)
        {
            current.x = current.x+move[2][0];
            current.y = current.y+move[2][1]; ;   
        }
        return current;
    }
    
    public Pos goRight (int[][] maze, Pos current)
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
