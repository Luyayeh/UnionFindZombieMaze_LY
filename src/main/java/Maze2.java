import java.util.*;

public class Maze2 
{

    public char[][] maze;
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BG_ANSI_GREEN = "\u001B[42m";
    public static final String BG_ANSI_BLUE = "\u001B[44m";
    public static final String BG_ANSI_CYAN = "\u001B[46m";
    public static final String BG_ANSI_WHITE = "\u001B[47m";
    
    Scanner scan;
 
    public char output;
    public char initial;
    public char want; 
    public boolean[][] isVisited;
    
    public boolean bothCoverting;
    
    public int limit_row;
    public int limit_col;

    int rand_row_player;
    int rand_col_player;
    int rand_row_exit;
    int rand_col_exit;
    
    public Position pos, npos, rand_player, rand_exit;
    
    public ArrayList<Position> greenPos, bluePos;
    
    public Deque<Position> deque;
    
    
    
    
    public Maze2(int rows, int cols)
    {
        scan = new Scanner(System.in);
        maze = new char[rows][cols];
        isVisited = new boolean[rows][cols];
        limit_row = rows;
        limit_col = cols;
        
        bothCoverting = false;
        
        greenPos = new ArrayList<>();
        bluePos = new ArrayList<>();
        
        for(int i = 0; i < maze.length; i++)
        {
            for(int j = 0; j < maze[0].length; j++)
            {
                int rand = (int)(Math.random() * 2);
                if(rand == 0)
                {
                    maze[i][j] = 'A';
                    Position temp = new Position(i, j);
                    greenPos.add(temp);
                    
                }
                else if(rand == 1)
                {
                    maze[i][j] = 'B';
                    Position temp = new Position(i, j);
                    bluePos.add(temp);
                }
                isVisited[i][j] = false;
            }
        }
        
        int rand = (int)(Math.random()*greenPos.size());
        rand_row_player = greenPos.get(rand).row;
        rand_col_player = greenPos.get(rand).col;
        
        rand = (int) (Math.random() * bluePos.size());
        rand_row_exit = bluePos.get(rand).row;
        rand_col_exit = bluePos.get(rand).col;
        
        deque = new ArrayDeque<>();
        
        drawMaze();
    }
    
    public void run(int r, int c)
    {
        pos = new Position(r, c);
        initial = maze[r][c];
        maze[r][c] = '.';
        isVisited[r][c] = true;
        
        deque.add(pos);
        
        if(initial == 'A')
        {
            want = 'B';
        }
        else if (initial == 'B')
        {
            want = 'A';
        }

        while(!(deque.isEmpty()))
        {
            pos = deque.element();
            
            //check down
            if (pos.row + 1 < limit_row)
            {  
                char down = maze[pos.row + 1][pos.col];
                
                if(!(down == want) && !isVisited[pos.row + 1][pos.col])
                {
                    maze[pos.row + 1][pos.col] = '.';
                    npos = new Position(pos.row + 1, pos.col);
                    deque.add(npos);
                    isVisited[pos.row + 1][pos.col] = true;
                }
            }
            //check up
            if (pos.row - 1 >= 0) 
            {
                char up = maze[pos.row - 1][pos.col];
                if (!(up == want) && !isVisited[pos.row - 1][pos.col])
                {
                    maze[pos.row - 1][pos.col] = '.';
                    npos = new Position(pos.row - 1, pos.col);
                    deque.add(npos);
                    isVisited[pos.row - 1][pos.col] = true;
                } 
            }
            //check right
            if (pos.col + 1 < limit_col) 
            {
                char right = maze[pos.row][pos.col + 1];
                if (!(right == want) && !isVisited[pos.row][pos.col + 1])
                {
                    maze[pos.row][pos.col + 1] = '.';
                    npos = new Position(pos.row, pos.col + 1);
                    deque.add(npos);
                    isVisited[pos.row][pos.col + 1] = true;
                } 
            }
            //check left
            if (pos.col - 1 >= 0) 
            {
                char left = maze[pos.row ][pos.col - 1];
                if (!(left == want) && !isVisited[pos.row][pos.col - 1])
                {
                    maze[pos.row][pos.col - 1] = '.';
                    npos = new Position(pos.row, pos.col - 1);
                    deque.add(npos);
                    isVisited[pos.row][pos.col - 1] = true;
                } 
            }
            deque.remove();
        }
        
        if(maze[rand_row_player][rand_col_player] == '.' && maze[rand_row_player][rand_col_player] == maze[rand_row_exit][rand_col_exit])
        {
            bothCoverting = true;
        }
        System.out.println("Intermediate Result.");
        drawMaze();
        
        convert(want);
        System.out.println("Final Result.");
        drawMaze();
        resetVisit();
    }
    
    public class Position
    {
        int row;
        int col;
        
        public Position(int row, int col)
        {
            this.row = row;
            this.col = col; 
        }
    }
    
    public void convert(char want)
    {
        for (int i = 0; i < maze.length; i++) 
        {
            for (int j = 0; j < maze[0].length; j++) 
            {
                if(maze[i][j] == '.')
                {
                    maze[i][j] = want;
                }
            }
        }
        
    }
    
    public void resetVisit()
    {
        for (int i = 0; i < maze.length; i++) 
        {
            for (int j = 0; j < maze[0].length; j++) 
            {
                isVisited[i][j] = false;
            }
        }
        
    }
    
    public boolean isComplete()
    {
        if(bothCoverting)
        {
            return true;
        }
        return false;
    }
    
    public void drawMaze()
    {   
        char coor_row = 'A';
        char coor_col = 'A';
        
        int count = 0; 
        
        System.out.print(" ");
        while (count < maze[0].length) 
        {
            if(count < 26)
            {
                System.out.print(BG_ANSI_WHITE + " " + ANSI_RESET);
            }
            else
            {
                System.out.print(BG_ANSI_WHITE + coor_col + ANSI_RESET);
                coor_col++;
            }
            count++;
        }
        
        coor_col = 'A';
        count = 0; 
        System.out.println();
        System.out.print(" ");
        while(count < maze[0].length)
        {
            System.out.print(BG_ANSI_WHITE + coor_col + ANSI_RESET);
            coor_col++;
            if (count == 25) 
            {
                coor_col = 'A';
            }
            count++;
        }
        System.out.println("");
        
        for(int i = 0; i < maze.length; i++)
        {
            System.out.print(BG_ANSI_WHITE + coor_row + ANSI_RESET);
            for(int j = 0; j < maze[0].length; j++)
            {

                if(i == rand_row_player && j == rand_col_player)
                {
                    if (maze[i][j] == '.') 
                    {
                        System.out.print(BG_ANSI_CYAN + "@" + ANSI_RESET);
                    }
                    else if(maze[i][j] == 'A')
                    {
                    System.out.print( BG_ANSI_GREEN  + "@" + ANSI_RESET);
                    }
                    else if (maze[i][j] == 'B') 
                    {
                        System.out.print(BG_ANSI_BLUE + "@" + ANSI_RESET);
                    }
                }
                else if(i == rand_row_exit && j == rand_col_exit)
                {
                    if (maze[i][j] == '.') 
                    {
                        System.out.print(BG_ANSI_CYAN + "E" + ANSI_RESET);
                    }
                    else if(maze[i][j] == 'A')
                    {
                        System.out.print(BG_ANSI_GREEN + "E" + ANSI_RESET);
                    }
                    else if(maze[i][j] == 'B')
                    {
                        System.out.print(BG_ANSI_BLUE + "E" + ANSI_RESET);
                    }
                }
                else if(maze[i][j] == 'A')
                {
                    System.out.print( BG_ANSI_GREEN + ' ' + ANSI_RESET);
                }
                else if(maze[i][j] == 'B')
                {
                    System.out.print(BG_ANSI_BLUE + '_' + ANSI_RESET);
                }
                else if(maze[i][j] == '.')
                {
                    System.out.print(BG_ANSI_CYAN + '.' + ANSI_RESET);
                }
                
            }
            System.out.print(BG_ANSI_WHITE + coor_row + ANSI_RESET);
            coor_row++; 
            System.out.println();
        }
        coor_col = 'A';
        count = 0;
        System.out.print(" ");
        while (count < maze[0].length) 
        {
            System.out.print(BG_ANSI_WHITE + coor_col + ANSI_RESET);
            coor_col++;
            if (count == 25) 
            {
                coor_col = 'A';
            }
            count++;
        }
        System.out.println();
    }
}
