package bb.model.interfaces;


/** A transformation from world to view coordinates and back.
 * 
 * @author theo
 *
 */
public interface WorldToViewI {

	/** Convert a world x coordinate to a view x coordinate.
	 * 
	 * @param world_x
	 * @return
	 */
	double convertX(double world_x) ;

	/** Convert a world y coordinate to a view y  coordinate.
	 * 
	 * @param world_y
	 * @return
	 */
	double convertY(double world_y) ;

	/** Convert a view x coordinate to a world x coordinate.
	 * 
	 * @param view_x
	 * @return
	 */
	double invertX(double view_x) ;
	
	/** Convert a view y coordinate to a world y coordinate.
	 * 
	 * @param view_y
	 * @return
	 */
	double invertY(double view_y) ;

}
