package GameBackend;

import junit.framework.TestCase;

public class UnitTests extends TestCase {

	public UnitTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * The next few tests insure that the board is set up properly, the mines properly placed,
	 * the non-mine tiles properly labeled, and  
	 *
	 */
	
	
	/**
	 * This creates an initial board with no mines, then creates mines to be placed in preset location.
	 * Then ensures that the mines are in the proper location and the non-mined spaces are indicated as such.
	 */
	public void testMinesSetProperly() 
	{
		Board testBoard = new Board(5, 5, 5, false);
		testBoard.setMine(0, 0);
		testBoard.setMine(2, 2);
		testBoard.setMine(3, 4);
		testBoard.setMine(4, 3);
		testBoard.setMine(4, 4);
		testBoard.assignNumbers();
		
		assertTrue(testBoard.getTile(0, 0).minePresent);
		assertFalse(testBoard.getTile(0, 1).minePresent);
		assertFalse(testBoard.getTile(0, 2).minePresent);
		assertFalse(testBoard.getTile(0, 3).minePresent);
		assertFalse(testBoard.getTile(0, 4).minePresent);
		
		assertFalse(testBoard.getTile(1, 0).minePresent);
		assertFalse(testBoard.getTile(1, 1).minePresent);
		assertFalse(testBoard.getTile(1, 2).minePresent);
		assertFalse(testBoard.getTile(1, 3).minePresent);
		assertFalse(testBoard.getTile(1, 4).minePresent);
		
		assertFalse(testBoard.getTile(2, 0).minePresent);
		assertFalse(testBoard.getTile(2, 1).minePresent);
		assertTrue(testBoard.getTile(2, 2).minePresent);
		assertFalse(testBoard.getTile(2, 3).minePresent);
		assertFalse(testBoard.getTile(2, 4).minePresent);
		
		assertFalse(testBoard.getTile(3, 0).minePresent);
		assertFalse(testBoard.getTile(3, 1).minePresent);
		assertFalse(testBoard.getTile(3, 2).minePresent);
		assertFalse(testBoard.getTile(3, 3).minePresent);
		assertTrue(testBoard.getTile(3, 4).minePresent);

		assertFalse(testBoard.getTile(4, 0).minePresent);
		assertFalse(testBoard.getTile(4, 1).minePresent);
		assertFalse(testBoard.getTile(4, 2).minePresent);
		assertTrue(testBoard.getTile(4, 3).minePresent);
		assertTrue(testBoard.getTile(4, 4).minePresent);
		
		
		assertEquals(testBoard.getRemainingClear(), 20);
		
	}
	
	/**
	 * This creates an initial board with no mines, then creates mines to be placed in preset location.
	 * Then ensures that the non-mined tiles show the correct mine counts in their neighbor tiles.
	 */
	
	public void testNeighborCountCorrect() 
	{
		Board testBoard = new Board(5, 5, 5, false);
		testBoard.setMine(0, 0);
		testBoard.setMine(2, 2);
		testBoard.setMine(3, 4);
		testBoard.setMine(4, 3);
		testBoard.setMine(4, 4);
		testBoard.assignNumbers();
		
		assertEquals(testBoard.getTile(0, 1).surrounding, 1);
		assertEquals(testBoard.getTile(0, 2).surrounding, 0);
		assertEquals(testBoard.getTile(0, 3).surrounding, 0);
		assertEquals(testBoard.getTile(0, 4).surrounding, 0);
		
		
		assertEquals(testBoard.getTile(1, 0).surrounding, 1);
		assertEquals(testBoard.getTile(1, 1).surrounding, 2);
		assertEquals(testBoard.getTile(1, 2).surrounding, 1);
		assertEquals(testBoard.getTile(1, 3).surrounding, 1);
		assertEquals(testBoard.getTile(1, 4).surrounding, 0);
		
		assertEquals(testBoard.getTile(2, 0).surrounding, 0);
		assertEquals(testBoard.getTile(2, 1).surrounding, 1);
		assertEquals(testBoard.getTile(2, 3).surrounding, 2);
		assertEquals(testBoard.getTile(2, 4).surrounding, 1);
		
		assertEquals(testBoard.getTile(3, 0).surrounding, 0);
		assertEquals(testBoard.getTile(3, 1).surrounding, 1);
		assertEquals(testBoard.getTile(3, 2).surrounding, 2);
		assertEquals(testBoard.getTile(3, 3).surrounding, 4);
		
		assertEquals(testBoard.getTile(4, 0).surrounding, 0);
		assertEquals(testBoard.getTile(4, 1).surrounding, 0);
		assertEquals(testBoard.getTile(4, 2).surrounding, 1);
		
		
	}
	
	/**
	 * Test to ensure on initiation of board all tiles are hidden
	 */
	
	public void testAllInitialHidden()
	{
		Board testBoard = new Board(5, 5, 5, false);
		testBoard.setMine(0, 0);
		testBoard.setMine(2, 2);
		testBoard.setMine(3, 4);
		testBoard.setMine(4, 3);
		testBoard.setMine(4, 4);
		testBoard.assignNumbers();
		
		for (int i = 0; i < 5; i++)
		{
			for(int m = 0; m < 5; m++)
			{
				assertTrue(testBoard.getTile(i, m).hidden);
			}
			
		}
	}
	
	/**
	 * This creates a randomized board. Then ensure there are only 10 mines placed.
	 */
	
	public void testRandom()
	{
		int ROWS = 10;
		int COLUMNS = 10;
		int MINES = 10;
		
		Board testBoard = new Board(ROWS, COLUMNS, MINES, true);
		testBoard.assignNumbers();
		
		int totalMines = 0;
		for (int i = 0; i < ROWS; i++)
		{
			for(int m = 0; m < COLUMNS; m++)
			{
				Tile current = testBoard.getTile(i, m);
				assertTrue(current.hidden);
				if(current.minePresent)
					totalMines++;
			}
			
		}
		
		assertEquals(totalMines, 10);
	}
	
	
	/**
	 * Test flagging is correct
	 */
	public void testFlag()
	{
		Board testBoard = new Board(5, 5, 5, false);
		testBoard.setMine(0, 0);
		testBoard.setMine(2, 2);
		testBoard.setMine(3, 4);
		testBoard.setMine(4, 3);
		testBoard.setMine(4, 4);
		testBoard.assignNumbers();
		
		testBoard.setFlag(0, 0);
		testBoard.setFlag(2, 2);
		
		assertTrue(testBoard.getTile(0, 0).flag);
		assertFalse(testBoard.getTile(0, 1).flag);
		assertFalse(testBoard.getTile(0, 2).flag);
		assertFalse(testBoard.getTile(0, 3).flag);
		assertFalse(testBoard.getTile(0, 4).flag);
		
		assertFalse(testBoard.getTile(1, 0).flag);
		assertFalse(testBoard.getTile(1, 1).flag);
		assertFalse(testBoard.getTile(1, 2).flag);
		assertFalse(testBoard.getTile(1, 3).flag);
		assertFalse(testBoard.getTile(1, 4).flag);
		
		assertFalse(testBoard.getTile(2, 0).flag);
		assertFalse(testBoard.getTile(2, 1).flag);
		assertTrue(testBoard.getTile(2, 2).flag);
		assertFalse(testBoard.getTile(2, 3).flag);
		assertFalse(testBoard.getTile(2, 4).flag);
		
		assertFalse(testBoard.getTile(3, 0).flag);
		assertFalse(testBoard.getTile(3, 1).flag);
		assertFalse(testBoard.getTile(3, 2).flag);
		assertFalse(testBoard.getTile(3, 3).flag);
		assertFalse(testBoard.getTile(3, 4).flag);

		assertFalse(testBoard.getTile(4, 0).flag);
		assertFalse(testBoard.getTile(4, 1).flag);
		assertFalse(testBoard.getTile(4, 2).flag);
		assertFalse(testBoard.getTile(4, 3).flag);
		assertFalse(testBoard.getTile(4, 4).flag);
		
		int surrounding_1_1 = testBoard.countFlags(1, 1);
		assertEquals(surrounding_1_1, 2);
		
	}
	
	
	public void testUnveilCascade()
	{
		Game currentGame = new Game(5);
		currentGame.openPosition(0, 4);
		
		Board currentBoard = currentGame.gameBoard;
		
		assertTrue(currentBoard.getTile(0, 0).hidden);
		assertFalse(currentBoard.getTile(0, 1).hidden);
		assertFalse(currentBoard.getTile(0, 2).hidden);
		assertFalse(currentBoard.getTile(0, 3).hidden);
		assertFalse(currentBoard.getTile(0, 4).hidden);
		
		assertTrue(currentBoard.getTile(1, 0).hidden);
		assertFalse(currentBoard.getTile(1, 1).hidden);
		assertFalse(currentBoard.getTile(1, 2).hidden);
		assertFalse(currentBoard.getTile(1, 3).hidden);
		assertFalse(currentBoard.getTile(1, 4).hidden);
		
		assertTrue(currentBoard.getTile(2, 0).hidden);
		assertTrue(currentBoard.getTile(2, 1).hidden);
		assertTrue(currentBoard.getTile(2, 2).hidden);
		assertFalse(currentBoard.getTile(2, 3).hidden);
		assertFalse(currentBoard.getTile(2, 4).hidden);
		
		assertTrue(currentBoard.getTile(3, 0).hidden);
		assertTrue(currentBoard.getTile(3, 1).hidden);
		assertTrue(currentBoard.getTile(3, 2).hidden);
		assertTrue(currentBoard.getTile(3, 3).hidden);
		assertTrue(currentBoard.getTile(3, 4).hidden);
		
		assertTrue(currentBoard.getTile(4, 0).hidden);
		assertTrue(currentBoard.getTile(4, 1).hidden);
		assertTrue(currentBoard.getTile(4, 2).hidden);
		assertTrue(currentBoard.getTile(4, 3).hidden);
		assertTrue(currentBoard.getTile(4, 4).hidden);
		

	}
	
	public void testFlagWrong()
	{
		Game currentGame = new Game(5);
		currentGame.setFlag(0, 2);
		currentGame.openPosition(0, 1);
		currentGame.openPosition(0, 1);
		
		currentGame.printCurrentBoard();
		
		Board currentBoard = currentGame.gameBoard;
		
		assertFalse(currentBoard.getTile(0, 0).hidden);
		assertFalse(currentBoard.getTile(0, 1).hidden);
		assertTrue(currentBoard.getTile(0, 2).hidden);
		assertTrue(currentBoard.getTile(0, 3).hidden);
		assertTrue(currentBoard.getTile(0, 4).hidden);
		
		assertFalse(currentBoard.getTile(1, 0).hidden);
		assertFalse(currentBoard.getTile(1, 1).hidden);
		assertFalse(currentBoard.getTile(1, 2).hidden);
		

	}
	
	
	

}