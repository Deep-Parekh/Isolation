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
		if(this.x >= SIZE || this.y >= SIZE || this.x < 0 || this.y < 0)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String coordinate = "";
		coordinate += (char)('A' + (int)x);
		coordinate += ((y + 1) + "");
		return coordinate;
	}
}
