
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
public class MazeCreate2 
{
    public int count = 0;
    
    public MazeCreate2 ()
    {
        Maze2 maze = new Maze2(19, 50);
        boolean stop = false;

        
        Scanner scan = new Scanner(System.in);
        do
        {
            System.out.print("Moves: "+ count + "." +  " Enter coordinates for flood fill (Ex: AC or ACC): ");
            String str = scan.nextLine();
            int row = 0;
            int col = 0;
            if(str.length() == 2)
            {
                char ch =  Character.toUpperCase(str.charAt(0)); 
                row = (int)(ch - 'A' );
                ch = Character.toUpperCase(str.charAt(1));
                col = (int)(ch - 'A');
            }
            else if (str.length() == 3) 
            {
                char ch = Character.toUpperCase(str.charAt(0));
                row = (int) (ch - 'A');
                ch = Character.toUpperCase(str.charAt(1));
                col = (int) (ch - 'A' + 26);
            }
            maze.run(row, col);
            count++;
            stop = maze.isComplete();
        }while(!stop);
        
        System.out.println("Level Completed In " + count + " Turns.");
    }
    public int getCount()
    {
        return count;
    }
}
