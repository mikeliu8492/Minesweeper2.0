package GameBackend;


/**
 * Basic tile class
 * @author mikeliu8492
 *
 */

public class Tile 
{
	//if tile has a mine
	public boolean minePresent; 
	
	//if tile is hidden from player
	public boolean hidden;
	
	//if non-mine tile, how many mines surround it, else is -1
	public int surrounding;
	
	//if the tile has a flag on it, if it does it cannot be unhidden unless flag is removed
	public boolean flag;
	
	public Tile(boolean minePresent)
	{
		this.minePresent = minePresent;
		this.hidden = true;
		this.flag = false;
	}
}