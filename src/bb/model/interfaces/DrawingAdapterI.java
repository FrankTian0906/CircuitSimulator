package bb.model.interfaces;

/**  A drawing adaptor represents a tool for outputting
 *   drawings to a part of a screen or page or similar 
 *   device.
 * 
 * @author theo
 *
 */
public interface DrawingAdapterI {

	/** Set the line color.  Also used for outlines of shapes.
	 * 
	 * @param r  How much red, as a number from 0 to 255 inclusive.
	 * @param g  How much green, as a number from 0 to 255 inclusive.
	 * @param b  How much blue, as a number from 0 to 255 inclusive.
	 */
	void setLineColor( int r, int g, int b ) ;
	
	/** Set the fill color for solid objects.
	 * 
	 * @param r  How much red, as a number from 0 to 255 inclusive.
	 * @param g  How much green, as a number from 0 to 255 inclusive.
	 * @param b  How much blue, as a number from 0 to 255 inclusive.
	 */
	void setFillColor( int r, int g, int b ) ;
	
	/** Set the text color.
	 * 
	 * @param r  How much red, as a number from 0 to 255 inclusive.
	 * @param g  How much green, as a number from 0 to 255 inclusive.
	 * @param b  How much blue, as a number from 0 to 255 inclusive.
	 */
	void setTextColor( int r, int g, int b ) ;
	
	/** Set the thickness of lines and outlines.
	 * 
	 * @param width  -- Width is roughly in world units. I.e. width of 1 is roughly 1 in world units.
	 */
	void setLineWidth( float width ) ;
	
	/** Draw an AND gate within a given bounding box.
	 *  
	 * @param x   -- The distance from the left edge to the centre of the bounding box in world coordinates.
	 * @param y   -- The distance from the top edge to the centre of the bounding box in world coordinates. 
	 * @param width  -- The width in world coordinates.
	 * @param height -- The height in world coordinates.
	 * @param orientation -- The direction of the output.
	 */
	void drawAndShape( double x, double y, double width, double height, OrientationE orientation ) ;
	
	/** Draw an OR gate within a given bounding box.
	 *  
	 * @param x   -- The distance from the left edge to the centre of the bounding box in world coordinates.
	 * @param y   -- The distance from the top edge to the centre of the bounding box in world coordinates. 
	 * @param width  -- The width in world coordinates.
	 * @param height -- The height in world coordinates.
	 * @param orientation -- The direction of the output.
	 */
	void drawOrShape( double x, double y, double width, double height, OrientationE orientation ) ;
	
	/** Draw a buffer (i.e. triangle) within a given bounding box.
	 *  
	 * @param x   -- The distance from the left edge to the centre of the bounding box in world coordinates.
	 * @param y   -- The distance from the top edge to the centre of the bounding box in world coordinates. 
	 * @param width  -- The width in world coordinates.
	 * @param height -- The height in world coordinates.
	 * @param orientation -- The direction of the output.
	 */
	void drawBufferShape( double x, double y, double width, double height, OrientationE orientation ) ;
	
	/** Draw a NOT gate within a given bounding box.
	 *  
	 * @param x   -- The distance from the left edge to the centre of the bounding box in world coordinates.
	 * @param y   -- The distance from the top edge to the centre of the bounding box in world coordinates. 
	 * @param width  -- The width in world coordinates.
	 * @param height -- The height in world coordinates.
	 * @param orientation -- The direction of the output.
	 */
	void drawNotShape( double x, double y, double width, double height, OrientationE orientation ) ;
	
	/** Draw a rectangle.
	 *  
	 * @param x   -- The distance from the left edge to the centre of the bounding box in world coordinates.
	 * @param y   -- The distance from the top edge to the centre of the bounding box in world coordinates. 
	 * @param width  -- The width in world coordinates.
	 * @param height -- The height in world coordinates.
	 */
	void drawRect( double x, double y, double width, double height ) ;
	
	/** Draw a string within a given bounding box. 
	 * The string will be drawn horizontally as one line.
	 * The size will be chosen so that the string fits within the box both horizontally and vertically.
	 *  
	 * @param x   -- The distance from the left edge to the centre of the bounding box in world coordinates.
	 * @param y   -- The distance from the top edge to the centre of the bounding box in world coordinates. 
	 * @param width  -- The width in world coordinates.
	 * @param height -- The height in world coordinates.
	 */
	void drawText( String str, double x, double y, double width, double height ) ;
	
	/** Draw an ellipse within a given bounding box. 
	 *  
	 * @param x   -- The distance from the left edge to the centre of the bounding box in world coordinates.
	 * @param y   -- The distance from the top edge to the centre of the bounding box in world coordinates. 
	 * @param radius  -- The radius in world coordinates.
	 */
	void drawCricle( double x, double y, double radius ) ;
	
	/** Draw a line between two points in world coordinates.
	 * 
	 * @param x0 -- The x coordinate (distance from left edge) of the first point.
	 * @param y0 -- The y coordinate (distance from top edge) of the first point.
	 * @param x1 -- The x coordinate (distance from left edge) of the second point.
	 * @param y1 -- The y coordinate (distance from top edge) of the second point.
	 */
	void drawLine( double x0, double y0, double x1, double y1 ) ;

}
