
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author John
 */
public class Maze3
{

    public boolean[][] maze;
    public boolean mapFound;
    public boolean exitFound;
    public boolean dead; 
    public int exit_row;
    public int exit_col;
    public int user_row;
    public int user_col;
    public int maze_row;
    public int maze_col;
    public int map_row;
    public int map_col;
    public int rand_row;
    public int rand_col;
    
    ArrayList<String> zombieList;
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";    
    
    public static final String BG_ANSI_BLACK = "\u001B[40m";
    public static final String BG_ANSI_RED = "\u001B[41m";
    public static final String BG_ANSI_GREEN = "\u001B[42m";
    public static final String BG_ANSI_YELLOW = "\u001B[43m";
    public static final String BG_ANSI_BLUE = "\u001B[44m";
    public static final String BG_ANSI_PURPLE = "\u001B[45m";
    public static final String BG_ANSI_CYAN = "\u001B[46m";
    public static final String BG_ANSI_WHITE = "\u001B[47m";    
    
    public static final String[] colors = new String[] 
    {    
        BG_ANSI_RED,
        BG_ANSI_GREEN,
        BG_ANSI_YELLOW,
        BG_ANSI_BLUE,
        BG_ANSI_PURPLE,
        BG_ANSI_CYAN 
    } ;
    
    
    public Maze3(int rows, int cols, double prob)
    {
        dead = false;
        exit_row = rows - 1;
        exit_col = cols - 1; 
        user_row = 0;
        user_col = 0;
        
        create(prob);
    }
    
    private void create(double prob)
    {
        boolean validMaze = false;
        do
        {
            maze = new boolean[exit_row + 1][exit_col + 1];
            
            for(int i = 0; i < maze.length; i++)
            {
                for(int j = 0; j < maze[0].length; j++)
                {
                    if ( Math.random() < prob ) maze[i][j] = true;
                }
            }
            if(maze[user_row][user_col] == false && maze[exit_row][exit_col] == false)
            {
                validMaze = true;
            }
        }while(!validMaze);
        
        boolean validMap = false;
        do
        {
            rand_row = (int)(Math.random() * (exit_row + 1));
            rand_col = (int)(Math.random() * (exit_col + 1));
            if(maze[rand_row][rand_col] == false)
            {
                validMap = true;
            }
        }while(!validMap);
        
        map_row = rand_row;
        map_col = rand_col;
        
    }
    
    public void setZombies(int turns)
    {
        zombieList = new ArrayList<String>();
        boolean validPoint;
        for(int i = 0; i < turns; i++)
        {
            validPoint = false;
            do
            {
                rand_row = (int) (Math.random() * (exit_row + 1));
                rand_col = (int) (Math.random() * (exit_col + 1));
                
                if((rand_row == 0 && rand_col == 0) || (rand_row == map_row && rand_col == map_col) || (rand_row == exit_row && rand_col == exit_col))
                {
                    validPoint = false;
                }
                else if(maze[rand_row][rand_col] == false && (!zombieList.contains(rand_row + "," + rand_col)))
                {
                    validPoint = true;
                }
            }while(!validPoint);
            zombieList.add(rand_row + "," + rand_col);
        }
    }
    public void moveZombie()
    {
        int[] direction = {-1,1};
        for(int z = 0; z < zombieList.size(); z++)
        {
            int row_move, col_move;
            
            String[] arr = zombieList.get(z).split(",");
            int z_row = Integer.parseInt(arr[0]);
            int z_col = Integer.parseInt(arr[1]);
            
            row_move = direction[(int)(Math.random() * 2)];
            col_move = direction[(int)(Math.random() * 2)];

            if ((z_row + row_move) >= 0 && (z_row + row_move) < (exit_row + 1) && (z_col + col_move) >= 0 && (z_col + col_move) < (exit_col + 1)) 
            {
                z_row = z_row + row_move;
                z_col = z_col + col_move;
                if ((maze[z_row][z_col] == false)) 
                {
                    if ((z_row == map_row && z_col == map_col) || (z_row == exit_row && z_col == exit_col)) 
                    {
                    } 
                    else if ((!zombieList.contains(z_row + "," + z_col))) 
                    {
                        zombieList.set(z, z_row + "," + z_col);
                    }
                }
            }
        }
    }
    
    public void drawMaze()
    {
        boolean zombieSeted;
        for(int i=0; i < maze.length; i++)
        {
            for(int j = 0; j < maze[0].length; j++)
            {
                zombieSeted = false;
                for(int z = 0; z < zombieList.size(); z++ )
                {
                    String arr[] = zombieList.get(z).split(",");
                    int z_row = Integer.parseInt(arr[0]);
                    int z_col = Integer.parseInt(arr[1]);
                    if(i == z_row && j == z_col)
                    {
                       System.out.print(BG_ANSI_PURPLE + "Z " + ANSI_RESET);
                       zombieSeted = true;
                    }
                }
                
                if (i == user_row && j == user_col && !zombieSeted)
                {
                    System.out.print("@ ");
                }
                else if(i == map_row && j == map_col && !mapFound && !zombieSeted) 
                {
                    System.out.print("M ");
                }
                else if(i == exit_row && j == exit_col && !exitFound && !zombieSeted)
                {
                    System.out.print("E ");
                }
                //# true; . false
                else if(maze[i][j] == true && !zombieSeted)
                {
                    System.out.print("# ");
                }
                else if(maze[i][j] == false && !zombieSeted)
                {
                    System.out.print(". ");
                }
                zombieSeted = false;
            }   
            System.out.println();
        }
    }
    
    
    public void drawMazeSetsInColor(UnionFind uf)
    {
        boolean zombieSeted;
        String[] use_color = new String[ maze.length * maze[0].length];
        String color;
        
        int color_loop = 0;
        
        for(int i=0; i < maze.length; i++)
        {
            for(int j=0; j < maze[0].length; j++)
            {   
                Integer in_set = uf.find(i+","+j);
                if ( in_set != null )
                {
                    color = use_color[in_set];
                    if ( color == null )
                    {
                        color = colors[ color_loop++ % colors.length ];
                        use_color[in_set] = color;
                    }
                } 
                else color = BG_ANSI_WHITE;
                
                zombieSeted = false;
                for (int z = 0; z < zombieList.size(); z++) 
                {
                    String arr[] = zombieList.get(z).split(",");
                    int z_row = Integer.parseInt(arr[0]);
                    int z_col = Integer.parseInt(arr[1]);
                    if (i == z_row && j == z_col) 
                    {
                        System.out.print(color + "Z " + ANSI_RESET);
                        zombieSeted = true;
                    }
                }
                
                if (i == user_row && j == user_col && !zombieSeted)
                {
                    System.out.print(color +"@ " + ANSI_RESET);
                } 
                else if (i == map_row && j == map_col && !mapFound && !zombieSeted) 
                {
                    System.out.print(color + "M " + ANSI_RESET);
                } 
                else if (i == exit_row && j == exit_col && !exitFound && !zombieSeted) 
                {
                    System.out.print(color + "E " + ANSI_RESET);
                }
                else if((maze[i][j] == true && !zombieSeted))
                {
                    System.out.print(color + "# " + ANSI_RESET);
                }
                else if(maze[i][j] == false && !zombieSeted)
                {
                    System.out.print(color + ". " + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }
    
}
