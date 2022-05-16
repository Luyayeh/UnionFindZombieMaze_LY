
import java.util.ArrayList;
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
public class MazeCreate3 
{
    /** This is your UnionFind generic class that does Union-Find 
     * Its a generic class and we're going to store the coordinates
	as Strings. (row,col).
     */
    
    public static UnionFind<String> uf;
    
    /** Class that contains code to produce the maze/grid randomly
     *  as well as code to colorize the regions
     */
    public static Maze3 grid;
    
    public static Scanner scan = new Scanner(System.in);
    
    public static final int row = 25;
    public static final int col = 50;
    
    public static int completedTurns; // Assume leve2 in 5 turns
    public MazeCreate3(int turns)
    {
        completedTurns = turns;
        System.out.print("Enter probability of a wall (0.0 - 1.0) >> ");
        double pro = scan.nextDouble();
        
        do 
        {
            grid = new Maze3(row, col, pro);
            grid.setZombies(completedTurns * 4);
            /**
             * create a new instance of your UnionFind class. Attempt to find
             * the regions of the grid.
             */
            uf = new UnionFind<String>();

            findRegions();

        } while (!pathFinder());

        /* Display the random grid generated */
        System.out.println("Random Grid");
        grid.drawMaze();
        System.out.println("Instruction: @ = User M = Map E = Exit Z = Zombie.");

        do
        {
            System.out.print("TYPE U=up D=down L=left R=right: ");
            String str = scan.next();
            
            if(str.length() == 1)
            {
                char ch = Character.toLowerCase(str.charAt(0));
                if(ch == 'u')
                {
                    if(grid.user_row - 1 >= 0 && grid.maze[grid.user_row - 1][grid.user_col] == false)
                    {
                        grid.user_row = grid.user_row - 1;
                        checkDead();
                        if (grid.dead == true) 
                        {
                            if(grid.mapFound == true)
                            {
                                grid.drawMazeSetsInColor(uf);
                                System.out.println("You're Dead!");
                            }
                            else
                            {
                                grid.drawMaze();
                                System.out.println("You're Dead!");
                            }
                        }
                        else {
                            grid.moveZombie();
                            checkDead();
                            if (!grid.mapFound) 
                            {
                                checkMap();
                            }
                            checkExit();
                            if (grid.dead == true) 
                            {
                                if (grid.mapFound == true) 
                                {
                                    grid.drawMazeSetsInColor(uf);
                                    System.out.println("You're Dead!");
                                } else 
                                {
                                    grid.drawMaze();
                                    System.out.println("You're Dead!");
                                }
                            } 
                            else if (!grid.mapFound) 
                            {
                                grid.drawMaze();
                            } 
                            else 
                            {
                                grid.drawMazeSetsInColor(uf);
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Invalid Direction.");
                    }
                }
                else if(ch == 'd')
                {
                    if (grid.user_row + 1 < row && grid.maze[grid.user_row + 1][grid.user_col] == false) 
                    {
                        grid.user_row = grid.user_row + 1;
                        checkDead();
                        if (grid.dead == true) 
                        {
                            if (grid.mapFound == true) 
                            {
                                grid.drawMazeSetsInColor(uf);
                                System.out.println("You're Dead!");
                            } 
                            else 
                            {
                                grid.drawMaze();
                                System.out.println("You're Dead!");
                            }
                        }
                        else 
                        {
                            grid.moveZombie();
                            checkDead();
                            if (!grid.mapFound) 
                            {
                                checkMap();
                            }
                            checkExit();
                            if (grid.dead == true) 
                            {
                                if (grid.mapFound == true) 
                                {
                                    grid.drawMazeSetsInColor(uf);
                                    System.out.println("You're Dead!");
                                } 
                                else 
                                {
                                    grid.drawMaze();
                                    System.out.println("You're Dead!");
                                }
                            } else if (!grid.mapFound) {
                                grid.drawMaze();
                            } else {
                                grid.drawMazeSetsInColor(uf);
                            }
                        }
                    } 
                    else 
                    {
                        System.out.println("Invalid Direction.");
                    }
                }
                else if(ch == 'l')
                {
                    if (grid.user_col - 1 < col && grid.maze[grid.user_row][grid.user_col - 1] == false) 
                    {
                        grid.user_col = grid.user_col - 1;
                        checkDead();
                        if (grid.dead == true) 
                        {
                            if (grid.mapFound == true) 
                            {
                                grid.drawMazeSetsInColor(uf);
                                System.out.println("You're Dead!");
                            } 
                            else 
                            {
                                grid.drawMaze();
                                System.out.println("You're Dead!");
                            }
                        }
                        else 
                        {
                            grid.moveZombie();
                            checkDead();
                            if(!grid.mapFound)
                            {
                                checkMap();
                            }
                            checkExit();
                            if (grid.dead == true) 
                            {
                                if (grid.mapFound == true) 
                                {
                                    grid.drawMazeSetsInColor(uf);
                                    System.out.println("You're Dead!");
                                } 
                                else 
                                {
                                    grid.drawMaze();
                                    System.out.println("You're Dead!");
                                }
                            } 
                            else if (!grid.mapFound) 
                            {
                                grid.drawMaze();
                            } 
                            else 
                            {
                                grid.drawMazeSetsInColor(uf);
                            }
                        }
                    } 
                    else 
                    {
                        System.out.println("Invalid Direction.");
                    }
                }
                else if (ch == 'r') 
                {
                    if (grid.user_col + 1 < col && grid.maze[grid.user_row][grid.user_col + 1] == false) 
                    {
                        grid.user_col = grid.user_col + 1;
                        checkDead();
                        if (grid.dead == true) 
                        {
                            if (grid.mapFound == true) 
                            {
                                grid.drawMazeSetsInColor(uf);
                                System.out.println("You're Dead!");
                            } 
                            else 
                            {
                                grid.drawMaze();
                                System.out.println("You're Dead!");
                            }
                        }
                        else
                        {
                            grid.moveZombie();
                            checkDead();
                            if (!grid.mapFound) 
                            {
                                checkMap();
                            }
                            checkExit();
                            if (grid.dead == true) 
                            {
                                if (grid.mapFound == true) 
                                {
                                    grid.drawMazeSetsInColor(uf);
                                    System.out.println("You're Dead!");
                                } 
                                else 
                                {
                                    grid.drawMaze();
                                    System.out.println("You're Dead!");
                                }
                            } 
                            else if (!grid.mapFound)
                            {
                                grid.drawMaze();
                            } 
                            else 
                            {
                                grid.drawMazeSetsInColor(uf);
                            }
                        }
                    } 
                    else 
                    {
                        System.out.println("Invalid Direction.");
                    }
                }
                else if (ch == 'm') 
                {
                    grid.user_row = grid.map_row;
                    grid.user_col = grid.map_col;
                    grid.mapFound = true;
                    grid.drawMazeSetsInColor(uf);
                }
                else if (ch == 'e') 
                {
                    grid.user_row = grid.exit_row;
                    grid.user_col = grid.exit_col;
                    grid.exitFound = true;
                }
                else
                {
                    System.out.println("Invalid Input.");
                }
            }
            else
            {
                System.out.println("Invalid Input.");
            }
        }while(!grid.exitFound && !grid.dead);
        
        if(!grid.dead)
        {
            if (!grid.mapFound) 
            {
                grid.drawMaze();
            } else 
            {
                grid.drawMazeSetsInColor(uf);
            }
            System.out.println("Congrads. You survived!");
        }
    }
    
    public void checkDead()
    {
        ArrayList<String> list = grid.zombieList;
        for (int z = 0; z < list.size(); z++) 
        {
            String[] arr = list.get(z).split(",");
            int z_row = Integer.parseInt(arr[0]);
            int z_col = Integer.parseInt(arr[1]);
            if (grid.user_row == z_row && grid.user_col == z_col) 
            {
                grid.dead = true;
            }
        }
    }
    
    public void checkMap()
    {
        if(grid.map_row == grid.user_row && grid.map_col == grid.user_col)
        {
            grid.mapFound = true;
        }
    }
    public void checkExit()
    {
        if (grid.exit_row == grid.user_row && grid.exit_col == grid.user_col) 
        {
            grid.exitFound = true;
        } 
    }
    
    public void findRegions()
    {
        /**
         * Your code goes here. Program the algorithm as discussed in class 
         * and described in class slides.
         */
        for (int i = 0; i < grid.maze.length; i++)
        {
            for (int j = 0; j < grid.maze[0].length; j++)
            {
                if(grid.maze[i][j] == false)
                {
                    String location;
                    location = i + "," + j;
                    uf.add(location);
                }
            }
        }
        
        for (int i = 0; i < grid.maze.length; i++)
        {
            for (int j = 0; j < grid.maze[0].length; j++)
            {
                if(grid.maze[i][j] == false)
                {
                    int cell_id = uf.find(i + "," + j);
                    //check up
                    if(i - 1 >= 0)
                    {
                        if(grid.maze[i - 1][j] == false)
                        {
                            int cell_up_id = uf.find((i - 1) + "," + j);
                            uf.union(cell_id, cell_up_id);
                        }
                    }
                    //check down
                    cell_id = uf.find(i + "," + j);
                    if (i + 1 < row) 
                    {
                        if (grid.maze[i + 1][j] == false) 
                        {
                            int cell_down_id = uf.find((i + 1) + "," + j);
                            uf.union(cell_id, cell_down_id);
                        }
                    }
                    //check right
                    cell_id = uf.find(i + "," + j);
                    if (j + 1 < col) 
                    {
                        if (grid.maze[i][j + 1] == false) 
                        {
                            int cell_right_id = uf.find(i + "," + (j + 1));
                            uf.union(cell_id, cell_right_id);
                        }
                    }
                    //check left
                    cell_id = uf.find(i + "," + j);
                    if (j - 1 >= 0) 
                    {
                        if (grid.maze[i][j - 1] == false) 
                        {
                            int cell_left_id = uf.find(i + "," + (j - 1));
                            uf.union(cell_id, cell_left_id);
                        }
                    }
                }
            }
        }  
    }
    
    public boolean pathFinder()
    {
        int set_id1 = uf.find("0,0");
        int set_id2 = uf.find((row - 1) + "," + (col - 1));
        int set_id_map = uf.find(grid.map_row + "," + grid.map_col);
        if (set_id1 == set_id2 && set_id1 == set_id_map) 
        {
            return true;
        }
        return false;
    }
}
