package Driver;

import GameBackend.*;
import java.util.Scanner;;

public class Driver 
{
	
	/**
	 * This is a basic driver of a program.
	 * @param args
	 */
	
	
	public static void main(String[] args)
	{
		/*
		 * Initialize beginner setting for manual testing:
		 * 1) Beginner 
		 * 2) Intermediate
		 * 3) Advanced
		 * 4) Custom
		 * 5) Debug Mode (not used in final release)
		 * */
		
		int GAME_SETTING = 1;
		Screen myGame = new Screen(GAME_SETTING);
		//myGame.showSolutionInConsole();
		
		/**
		 * While game is not over, keep playing
		 */
		while(true)
		{
			while(!myGame.gameOver())
			{
				myGame.updateTime();
				myGame.checkIfWin();   /*spin, spin spin*/
				if (myGame.gameOver())
					break;
			}
			
			
			/**
			 * Display the result once you break out of loop.
			 */
			System.out.println("\nGAME OVER");
			
			if(myGame.loser())
				myGame.lossUnveil();
			
			myGame.displayResults();
			
			while(myGame.gameOver())
			{
				System.out.print("");/*spin, spin spin*/
			}
		}

	}
}
