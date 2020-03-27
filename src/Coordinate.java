/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class Coordinate{
	
	final byte SIZE = 8; 
	byte x;
	byte y;
	
	public Coordinate(byte x, byte y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isInvalid() {
		if(this.x >= SIZE || this.y >= SIZE)
			return false;
		return true;
	}
}
