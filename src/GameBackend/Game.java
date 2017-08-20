package GameBackend;

public class Game 
{
	
	//self explanatory booleans for if you win or lose the game
	private boolean youLose;
	private boolean youWon;
	
	//the board hosted by game
	public Board gameBoard;
	
	//number of tiles that are non-mine
	private int clearTiles;
	
	/**
	 * before randomizing the board, create a fixed game board to test basic functionality
	 * @param type -> the type of game selected, True for randomized, False for debug-mode
	 * 
	 * 
	 * 1) Beginner:  10x10, 10 mines
	 * 2) Intermediate:  16x16, 40 mines
	 * 3) Expert:  16x30, 99 mines
	 * 4) Custom is handled in separate constructor
	 * 5) Debug:  Preset board for debugging purposes. Not for use in released version.
	 */
	public Game(int type)
	{
		if (type == 1)
		{
			this.gameBoard = new Board(10, 10, 10, true);
			gameBoard.assignNumbers();
		}
		else if (type == 2)
		{
			this.gameBoard = new Board(16, 16, 40, true);
			gameBoard.assignNumbers();
		}
		else if (type == 3)
		{
			this.gameBoard = new Board(16, 30, 99, true);
			gameBoard.assignNumbers();
		}
		else if (type == 5)
		{
			this.gameBoard = new Board(5, 5, 5, false);
			gameBoard.setMine(0, 0);
			gameBoard.setMine(2, 2);
			gameBoard.setMine(3, 4);
			gameBoard.setMine(4, 3);
			gameBoard.setMine(4, 4);
			gameBoard.assignNumbers();
			
		}

	}
	
	
	/**
	 * Constructor for custom game. It is not up to the constructor to validate parameters but for the 
	 * GUI to handle constraints.
	 */
	public Game(int row, int column, int mines)
	{
		this.gameBoard = new Board(row, column, mines, true);
		gameBoard.assignNumbers();
	}
	
	
	//print the current board state, simple wrapper function
	public void printCurrentBoard()
	{
		gameBoard.printActualBoard();
	}
	
	
	/**
	 * check to determine if all win conditions are met
	 * 1) there are no more non-mine tiles that are hidden
	 * 2) no mine tiles have been un-hidden
	 * 
	 */
	public void checkWinCondition()
	{
		if(gameBoard.getRemainingClear() == 0 && youLose == false)
			youWon = true;
	}
	
	
	/**
	 * boolean to determine if game is over bc you win or lose
	 * @return		bool on whether game is over
	 */
	public boolean gameOver()
	{
		return youWon || youLose;
	}
	
	
	/**
	 * 
	 * Based on row/column, reveals the current tile.  If mine, player loses.  Else game continues.
	 * Currently updated with a cascade function of BFS should initial tile be hidden.
	 * 
	 * If initial tile is non-hidden and the # flags surrounding it matches the number neighbor
	 * count in said tile, then cascade function unveils all immediate neighbors not flagged, even
	 * if a neighbor is a mine.
	 * 
	 * @param row
	 * @param col
	 */
	public void openPosition(int row, int col)
	{
		Tile currentTile = gameBoard.getTile(row, col);
		
		
		//if flag, do not unveil tile
		if(currentTile.flag)
			return;
		
		
		/*
		 * If current tile is hidden, the cascade to neighbors if initial mine has a count of 0.
		 * Keep the recursion going
		 * */
		if(currentTile.hidden)
		{
			//save the previous state of the tile
			boolean prev_state = gameBoard.getTile(row, col).hidden;
			
			//reveal the tile
			gameBoard.revealTile(row, col);
			if(currentTile.minePresent)
			{
				//if the tile is a mine, then set the "lose" variable to true
				youLose = true;
			}
			else
			{
				/**
				 * if the tile was previously hidden but now unveiled, decrement the
				 * count of the tiles that are non-mine and hidden
				 */
				
				if(prev_state)
					gameBoard.decrementRemainTiles();
				
				/**
				 * if the tile has no surrounding mines, unveil its neighbors unless it
				 * is a flag.  recursion will continue until the tile revealed shows at
				 * least one mine surrounds it
				 */
				if (currentTile.surrounding == 0)
				{
					for(int rowDiff = -1; rowDiff <= 1; rowDiff++)
					{
						for(int colDiff = -1; colDiff <= 1; colDiff++)
						{
							int otherR = row + rowDiff;
							int otherC = col + colDiff;
							
							if(gameBoard.withinBounds(otherR, otherC))
								this.openPosition(otherR, otherC);
						}
						
					}
				}
			}
		}
		else
		{
			/**
			 * if tile is not hidden, calculate the number of flags surrounding it
			 * 
			 */
			int surroundFlags = gameBoard.countFlags(row, col);
			
			
			/**
			 * if number of flags surrounding the tile match the tile's neighboring mine count number,
			 * then unveil all immediate neighboring tiles
			 */
			if (surroundFlags == currentTile.surrounding)
			{
				for(int rowDiff = -1; rowDiff <= 1; rowDiff++)
				{
					for(int colDiff = -1; colDiff <= 1; colDiff++)
					{
						int otherR = row + rowDiff;
						int otherC = col + colDiff;
						
						
						
						if(gameBoard.withinBounds(otherR, otherC))
						{
							Tile cascadeTile = gameBoard.getTile(otherR, otherC);
							if (cascadeTile.hidden)
								this.openPosition(otherR, otherC);
						}
							
					}
					
				}
				
			}
			return;
		}
		

		
	}
	
	
	//wrapper function to show all mines when losing
	public void showMines()
	{
		gameBoard.printAllMines();
	}
	
	
	//print the final game result on console
	public void printFinalResult()
	{
		if(youWon)
		{
			System.out.println("YOU WON!");
		}
		else
		{
			System.out.println("YOU LOSE!  GOOD DAY SIR/MA'AM\n\n");
			showMines();
		}
	}
	
	
	/**
	 * wrapper function to set the flag on a particular tile
	 * 
	 * @param row		row
	 * @param col		column
	 */
	public void setFlag(int row, int col)
	{
		this.gameBoard.setFlag(row, col);
	}
	
	
	/**
	 * wrapper function to print a debug mode version of the board to show all mine locations
	 * and non-mine locations for manual testing
	 */
	public void printDebugMode()
	{
		gameBoard.printDebugBoard();
	}
	
	
	/**
	 * Function to retrieve a tile for manipulation
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Tile getTile(int row, int column)
	{
		return gameBoard.getTile(row, column);
	}
	
	
	/**
	 * 
	 * @return number of row the gameboard holds
	 */
	public int getRows()
	{
		return gameBoard.getRows();
	}
	
	/**
	 * 
	 * @return number of columns the gameboard holds
	 */
	public int getColumns()
	{
		return gameBoard.getColumns();
	}
	
	
	/**
	 * 
	 * @return boolean on whether you won
	 */
	public boolean getWin()
	{
		return this.youWon;
	}
	

	/**
	 * 
	 * @return boolean on whether you lost
	 */
	public boolean getLoss()
	{
		return this.youLose;
	}

	/**
	 * Wrapper to unveil mines
	 */
	public void unveilMineWrapper()
	{
		gameBoard.mineUnveiled();
	}
	
	
	/**
	 * 
	 * @return total number of non-mine tiles you need to unveil
	 */
	public int getRemainingClear()
	{
		return gameBoard.getRemainingClear();
	}
	
	
	/**
	 * 
	 * @return total number of flags you still have available to place
	 */
	public int getRemainingFlags()
	{
		return gameBoard.getRemainingFlags();
	}

}
