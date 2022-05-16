
import java.util.Scanner;

public class MazeDriver 
{

    public static void main(String[] args) 
    {
        int completedTurns = 0; //Default 0 turns if you do not complete level 2 
        Scanner level = new Scanner(System.in);
        System.out.println("*************************************");
        System.out.println("*     COMP 2230 DATA STRUCTURES     *");
        System.out.println("*          FINAL PROJECT            *");
        System.out.println("*************************************");
        System.out.println("Which level do you want to start at?(1-4)");
        System.out.println("Note: once you complete a level you go on to the next regardless of the level you start on.\n      So if I start on level 2 and complete it, I immediately go on to the level 3 and the level 4.\n      If I comlete the 4th level the game is considered over(the end).");
        System.out.print("Enter Level: ");
        int chosen = level.nextInt();
        System.out.println();
        while (chosen != 5)
        {
            if (chosen == 1)
            {
                System.out.println("Level 1: \n");
                MazeCreate1 mazeLV1 = new MazeCreate1();
                chosen++;
                System.out.println();
            }
            if (chosen == 2)
            {
                System.out.println("Level 2: \n");
                MazeCreate2 mazeLV2 = new MazeCreate2();
                chosen++;
                completedTurns = mazeLV2.getCount();
                System.out.println();
            }
            if (chosen == 3)
            {
                System.out.println("Level 3: \n");
                if(completedTurns == 0)
                {
                    System.out.println("There is no zombie because you did not successfully complete level 2.");
                    System.out.println();
                }
                else
                {
                    System.out.println("If you feel " + completedTurns * 4 + " zombies might be too powerful, consider using the teleporation. TYPE M to teleport to the map, E to teleport to the exit.");
                    System.out.println();
                }
                
                MazeCreate3 mazeLV3 = new MazeCreate3(completedTurns);
                chosen++;
                System.out.println();
            }
            if (chosen == 4)
            {
                System.out.println("Level 4: \n");
                if (completedTurns == 0) 
                {
                    System.out.println("There is no zombie because you did not successfully complete level 2.");
                }
                MazeCreate4 mazeLV4 = new MazeCreate4(completedTurns);
                chosen++;
                System.out.println();
            }
        }
            
    }
    
}
