package GameBackend;

import java.util.Random;

public class Board 
{
	//total number of mines
	private int totalMines;
	
	//total number of tiles
	private int totalTiles;
	
	//total number of non-mine tiles hidden
	private int totalClearRemain;
	
	//total number of permissible flags
	private int flagStore;
	
	private int rows;
	private int columns;
	
	//array of tiles
	private Tile [][] currentBoard;
	
	/**
	 * 
	 * @return The total number of tiles on the board
	 */
	public int getTotalTiles()
	{
		return totalTiles;
	}
	
	
	/**
	 * 
	 * @return the total number of non-mined tiles on the board that have not yet been revealed
	 */
	public int getRemainingClear()
	{
		return totalClearRemain;
	}
	
	/**
	 * When a tile is revealed and was not previously hidden and is not a mine, then
	 * the number of tiles to be cleared is decremented.
	 */
	
	public void decrementRemainTiles()
	{
		if(totalClearRemain > 0)
			totalClearRemain--;
	}
	
	
	public int getRows()
	{
		return rows;
	}
	
	public int getColumns()
	{
		return columns;
	}
	
	
	
	/**
	 * This is a debugging function for the early stages to print out
	 * a copy of the minesweeper board to the console that reveals all tiles and identity
	 */
	public void printDebugBoard()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int m = 0; m < columns; m++)
			{
				Tile position = currentBoard[i][m];
				if(position.minePresent)
					System.out.print("*");
				else
					System.out.print(position.surrounding);
			}
			System.out.println();
		}
		
		
	}
	
	/**
	 * This prints the actual board on the console as would be seen in the game,
	 * with hidden tiles, etc.
	 */
	
	public void printActualBoard()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int m = 0; m < columns; m++)
			{
				Tile position = currentBoard[i][m];
				if(position.hidden)
				{
					if (position.flag)
						System.out.print("^");
					else
						System.out.print("-");
				}
				else
				{
					if(position.minePresent)
						System.out.print("*");
					else
						System.out.print(position.surrounding);
				}

			}
			System.out.println();
		}
		
		
	}
	
	
	/**
	 * Constructor to construct a board for unit testing the general functions.  Since the randomly generated board
	 * will be different each time, it cannot use a deterministic unit tests to show exact positions of where mines
	 * are or where the neighbors are.  So if the random flag is set to "false", then the constructor will create an
	 * empty board.
	 * 
	 * Also, initialize the number of permissible flags to the same number as mines.
	 * 
	 * Also, assume for now the parameters are valid.  The GUI interface to be programmed later will ensure validity
	 * of measures (e.g. row/col are positive, number of mines is less than 1/3 of the total tiles.
	 * 
	 * @param row 		number of rows to place on board
	 * @param col		number of columns to place on board
	 * @param mines		number of mines to place on board
	 * @param random	bool to determine if you want to manually place mines for testing or stochastically generate
	 * 					mines for the actual game
	 */
	
	
	public Board(int row, int col, int mines, boolean random)
	{
		this.rows = row;
		this.columns = col;
		this.totalMines = mines;
		this.flagStore = this.totalMines;
		this.totalTiles = this.rows*this.columns;
		this.totalClearRemain = totalTiles-totalMines;
		
		currentBoard = new Tile[this.rows][this.columns];
		
		
		if (random)
		{
			/**
			 * We are using a random generator seeded with the current time in milliseconds since Jan 1, 1970.
			 */
			Random myRandom = new Random(System.currentTimeMillis());
			int minesRemaining = totalMines;
			
			for(int i = 0; i < rows; i++)
			{
				for(int m = 0; m < columns; m++)
				{
					currentBoard[i][m] = new Tile(false);
				}
			}
			
			while (minesRemaining > 0)
			{
				int targetRow = (int)(Math.random()*rows);
				int targetColumn = (int)(Math.random()*columns);
				if(!currentBoard[targetRow][targetColumn].minePresent)
				{
					int number = (int)(Math.random()*10) + 1;
					if (number == 1)
					{
						currentBoard[targetRow][targetColumn].minePresent = true;
						minesRemaining--;
					}
						
				}
						
			}
			
			
		}
		else
		{

			for(int i = 0; i < rows; i++)
			{
				for(int m = 0; m < columns; m++)
				{
					currentBoard[i][m] = new Tile(false);
				}
			}
				
			
		}
	}
	
	
	/**
	 * Function to place a mine in a row/column
	 * @param r		row
	 * @param c		column
	 */
	public void setMine(int r, int c)
	{
		if(withinBounds(r,c))
			currentBoard[r][c].minePresent = true;
	}
	
	
	/**
	 * Function to reveal a particular tile
	 * @param r		row
	 * @param c		column
	 */
	public void revealTile(int r, int c)
	{
		if(withinBounds(r, c))
			currentBoard[r][c].hidden = false;
	}
	
	
	
	/**
	 * 
	 * HELPER FUNCTIONS!!!!
	 * 
	 * 
	 */
	
	/**
	 * 
	 * Determines if specific Board coordinates are in bounds
	 * 
	 * @param r		row in question
	 * @param c		column in question
	 * @return		determines if the given or and column are in bounds
	 */
	
	public boolean withinBounds(int r, int c)
	{
		if(r >= 0 && r < rows)
		{
			if(c >= 0 && c < columns)
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	
	/**
	 * Function that will go over entire board and call the "sweepNeighbors"
	 * function to determine for any non-mine tile how many neighbors have mines in it.
	 */
	public void assignNumbers()
	{
		for (int i = 0; i < rows; i++)
		{
			for(int m = 0; m < columns; m++)
			{
				if(currentBoard[i][m].minePresent)
				{
					currentBoard[i][m].surrounding = -1;
				}
				else
				{
					currentBoard[i][m].surrounding = sweepNeighbors(i, m);
				}
			}
			
		}
	}
	
	
	/**
	 * Function to examine the immediate neighbors of the cells for mines.
	 * It looks in the 8 directions of horizontal, vertical, and diagonal.
	 * If cell is valid on the grid, then it is examined to determine if a mine exists.
	 * If mine exists, increment a counter.
	 * 
	 * @param r		row
	 * @param c		column
	 * @return		total number of surrounding mines
	 */
	private int sweepNeighbors(int r, int c)
	{
		if(withinBounds(r, c) && !currentBoard[r][c].minePresent)
		{
			int total = 0;
			
			for(int rowDiff = -1; rowDiff <= 1; rowDiff++)
			{
				for(int colDiff = -1; colDiff <= 1; colDiff++)
				{
					int otherR = r + rowDiff;
					int otherC = c + colDiff;
					
					if(withinBounds(otherR, otherC))
					{
						if(currentBoard[otherR][otherC].minePresent)
							total++;
					}
				}
				
			}
			
			return total;
			
		}
			
		//it should NEVER GET HERE!
		return -10;
	}
	
	
	/**
	 * Retrieves a tile to manipulate
	 * 
	 * @param row
	 * @param col
	 * @return	reference to a specific tile
	 */
	public Tile getTile(int row, int col)
	{
		if(withinBounds(row, col))
		{
			return currentBoard[row][col];
		}
		
		return null;
	}
	
	/**
	 * Function to print all mines should the player lose.
	 */
	public void printAllMines()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int m = 0; m < columns; m++)
			{
				Tile position = currentBoard[i][m];
				if(position.minePresent)
				{
					System.out.print("*");
				}
				else
				{
					if (position.flag)
						System.out.print("^");
					else if(position.hidden)
						System.out.print("-");
					else
						System.out.print(position.surrounding);
				}

			}
			System.out.println();
		}
		
		
	}
	
	
	/**
	 * Figure to count the flags surrounding a particular tile.
	 * @param r		row
	 * @param c		column
	 * @return		# of flags surrounding a particular column
	 */
	public int countFlags(int r, int c)
	{
		if(withinBounds(r, c) && !currentBoard[r][c].minePresent)
		{
			int total = 0;
			
			for(int rowDiff = -1; rowDiff <= 1; rowDiff++)
			{
				for(int colDiff = -1; colDiff <= 1; colDiff++)
				{
					int otherR = r + rowDiff;
					int otherC = c + colDiff;
					
					if(withinBounds(otherR, otherC))
					{
						if(currentBoard[otherR][otherC].flag)
							total++;
					}
				}
				
			}
			
			return total;
			
		}
			
		//it should NEVER GET HERE!
		return -10;
	}
	
	
	/**
	 * Function to set a flag variable in a particular tile.  
	 * Tile must be hidden or else is no-op.
	 * If you have exhausted the number of available flags, then is no-op.
	 * If tile already has a flag, then the flag is removed.
	 * Availability of flags is updated as flags are placed and removed.
	 * 
	 * @param row
	 * @param col
	 */
	public void setFlag(int row, int col)
	{
		Tile current = this.getTile(row, col);
		if (current.hidden)
		{
			if(current.flag)
			{
				current.flag = false;
				flagStore++;
			}
			else if(!current.flag && flagStore > 0)
			{
				current.flag = true;
				flagStore--;
			}
				
		}
	}
	
	
	
	/**
	 * If you lose the game, this will cause all hidden mines to become unveiled if:
	 * - mine is currently hidden
	 * - hidden mine is not currently flagged
	 * 
	 */
	public void mineUnveiled()
	{
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < columns; c++)
			{
				Tile currentTile = currentBoard[r][c];
				if (currentTile.hidden && currentTile.minePresent && !currentTile.flag)
					currentTile.hidden = false;
			}
		}
		
	}
	
	
	/**
	 * 
	 * @return number of flags you can still place
	 */
	public int getRemainingFlags()
	{
		return flagStore;
	}

}
