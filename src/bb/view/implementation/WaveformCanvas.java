package bb.view.implementation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.Observable;

import javax.swing.JPanel;

import bb.model.interfaces.ThreeValuedLogic;
import bb.model.interfaces.WaveformModelInterface;
import bb.model.interfaces.WaveformModelInterface.WaveformEvent;
import bb.view.interfaces.WaveformClickListenerInterface;

/** A JComponent to display and edit waveforms.
 * 
 * <p> Each waveform is shown horizontally across the component.
 * 
 * <p> The data for the waveform comes from a {@link WaveformModelInterface}
 * object.
 * 
 * <p> Mouse clicks on a WaveformCanvas may be sent to a {@link WaveformClickListenerInterface}
 * object.
 * 
 * @author theo
 *
 */
public class WaveformCanvas extends JPanel implements java.util.Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5775589548609016471L;

	private class WaveformMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if( clickListener != null ) {
				int x = e.getX();
				int y = e.getY();
				int worldX = viewToWorldHorizontal(x) ;
				ThreeValuedLogic value = viewToWorldVertical(y) ;
				clickListener.clickOnWaveForm(worldX, value) ; } } }
	
	private static final double Y_TRUE = 0.05 ;
	private static final double Y_UNDEFINED = 0.35 ;
	private static final double Y_FALSE = 0.65 ;
	private static final double Y_SMALL_TICK_TOP = 0.9 ;
	private static final double Y_BIG_TICK_TOP = 0.7 ;
	private static final double Y_TICK_BOTTOM = 1.0 ;
	private static final int CENTRE = 0;
	private static final int LEFT = 1;
	
	private int simulationLength ;
	private int pixelsPerStep ;
	private int minimumHeight ;
	private final int leftMargin = 30 ;
	private final int rightMargin = 20 ;
	private WaveformClickListenerInterface clickListener = null ;
	private WaveformModelInterface model = null ;
	
	/** Construct a canvas onto which to draw a waveform.
	 * 
	 * @param simulationLength The number of simulation steps to display.
	 * @param pixelsPerStep The number of horizontal pixels to devote to each simulation step
	 * @param height This is the minimum and preferred height in pixels
	 */
	public WaveformCanvas( int simulationLength, int pixelsPerStep, int height){
		assert simulationLength > 0 ;
		assert pixelsPerStep > 0 ;
		this.simulationLength = simulationLength ;
		this.pixelsPerStep = pixelsPerStep ;
		this.minimumHeight = height ;
		addMouseListener( new WaveformMouseListener() ) ;
		setOpaque(true) ;
		setBackground(Color.white) ;
		setDimensions() ;
	}
	
	/** The number of pixels for each simulation step. */
	public int getPixelsPerStep() {
		return pixelsPerStep ;
	}
	
	/** Set the number of pixels for each simulation step
	 * 
	 * @param pixelsPerStep > 0
	 */
	public void setPixelsPerStep(int pixelsPerStep ) {
		assert pixelsPerStep > 0 ;
		this.pixelsPerStep = pixelsPerStep ;
		setDimensions() ;
		revalidate() ;
		repaint() ;
	}
	
	/** Set the number of simulation steps to show
	 * 
	 * @param simulationLength > 0
	 */
	public void setSimulationLength(int simulationLength ) {
		assert simulationLength > 0 ;
		this.simulationLength = simulationLength ;
		setDimensions() ;
		revalidate() ;
		repaint() ;
	}

	/** Set a listener for clicks on the canvas.
	 * 
	 * @param l The listener.  If null then the current listener is removed.
	 */
	public void setClickListener( WaveformClickListenerInterface l) { 
		clickListener = l ;
	}
	
	/** Set the model from which to obtain the data to display.
	 * 
	 * @param model
	 */
	public void setModel( WaveformModelInterface model ) {
		this.model = model ;
	}
	
	/**
	 * update() should be called when the model changes its state in some way that may affect this view.
	 * <p>Don't confuse this update() with update(Graphics) which is inherited from Component!
	 * @see schematic.viewInterfaces.ObserverInterface#update()
	 */

	@Override
	public void update(Observable o, Object arg) {
		// The call to repaint causes a future call to paint
		// to be scheduled.  This call to paint will cause
		// paintComponent to be called.
		repaint( ) ;
	}
	
	/** paintComponent is called when the canvas needs to be redisplayed.
	 * 
	 */
	@Override protected void paintComponent( Graphics graphics ) {
		// Let the UI delegate do its thing, including painting the background.
		super.paintComponent( graphics ) ;

		Color color0 = graphics.getColor() ;
		
		int h = getHeight() ;
		
		// Draw a tick for ever step
		graphics.setColor( Color.blue ) ;
		for( int step = 0 ; step < simulationLength+1 ; ++step ) {
			int x = worldToViewHorizontal(step) ;
			graphics.drawLine(x, (int)(h*Y_SMALL_TICK_TOP), x, (int)(h*Y_TICK_BOTTOM)) ; }
		
		// Draw a bigger tick for every 5 steps
		for( int step = 0 ; step < simulationLength+1 ; step += 5 ) {
			int x = worldToViewHorizontal(step) ;
			graphics.drawLine(x, (int)(h*Y_BIG_TICK_TOP), x, (int)(h*Y_TICK_BOTTOM)) ; }
		
		graphics.setColor( Color.black ) ;
		// Label the tick for every 10 steps
		int labelTop = (int)(h*Y_BIG_TICK_TOP) ;
		int labelBot = h ;
		for( int step = 0 ; step < simulationLength+1 ; step += 10 ) {
			int x = worldToViewHorizontal(step) ;
			putLabelAt( graphics, step+"", x, labelTop, labelBot, CENTRE ) ; }
		
		// Label the Y axis
		putLabelAt( graphics, "T", 0, (int)(h*Y_TRUE-h*0.1), (int)(h*Y_TRUE+h*0.1), LEFT ) ;
		putLabelAt( graphics, "U", 0, (int)(h*Y_UNDEFINED-h*0.1), (int)(h*Y_UNDEFINED+h*0.1), LEFT ) ;
		putLabelAt( graphics, "F", 0, (int)(h*Y_FALSE-h*0.1), (int)(h*Y_FALSE+h*0.1), LEFT ) ;
		
		// Stop here if no model
		if( model != null ) {

			// Put the name of the waveform
			putLabelAt( graphics, model.getName(), leftMargin, 0, (int)(h*0.40), LEFT, Color.blue ) ;

			// Draw the wave form itself.
			int x0 = worldToViewHorizontal(0) ;
			int y0 = worldToViewVertical( ThreeValuedLogic.UNDEFINED ) ;
			Iterator<WaveformEvent> waveformEvents = model.getIterator() ;
			while( waveformEvents.hasNext() ) {
				WaveformEvent event = waveformEvents.next() ;
				int x1 = worldToViewHorizontal( event.getTimeStep() ) ;
				assert x1 >= x0 ;
				int y1 = worldToViewVertical( event.getValue() ) ;
				graphics.drawLine(x0, y0, x1, y0) ; // Horizontal
				graphics.drawLine(x1, y0, x1, y1) ; // Vertical
				x0 = x1 ; y0 = y1 ;
			}
			//One last Horizontal to the end of time.
			int x1 = worldToViewHorizontal( simulationLength ) ;
			graphics.drawLine(x0, y0, x1, y0) ; }
		
		graphics.setColor( color0 ) ;
	}
	

	private void putLabelAt(Graphics g, String msg, int x, int y0, int y1, int align, Color color) {
		Color color0 = g.getColor() ;
		g.setColor(color) ;
		putLabelAt(g, msg, x, y0, y1, align ) ;
		g.setColor(color0) ;
	}
	
	private void putLabelAt(Graphics g, String msg, int x, int y0, int y1, int align) {
		int h = y1 - y0 ;
		Font font0 = g.getFont() ;
		Font font1 = new Font("Dialog", Font.PLAIN, 10 ) ;
		int ascent = g.getFontMetrics(font1).getAscent() ;
		double scale = ((double)h) / ((double)ascent) ;
		AffineTransform xform = new AffineTransform() ;
		xform.scale(scale, scale) ;
		Font font = font1.deriveFont( xform ) ;
		g.setFont( font ) ;
		int w = g.getFontMetrics().stringWidth(msg) ;
		if( align == CENTRE ) { x = x - w/2 ; }
		g.drawString(msg, x, y1) ;
		g.setFont( font0 ) ;
	}
	
	private void setDimensions() {
		Dimension size = new Dimension( leftMargin + rightMargin + simulationLength * pixelsPerStep, minimumHeight ) ;
		setMinimumSize(size) ;
		setPreferredSize(size) ; }

	private int worldToViewHorizontal(int step) {
		return leftMargin + step*pixelsPerStep;
	}

	private  int viewToWorldHorizontal(int x) {
		return Math.max(0, Math.min(simulationLength-1, (x-leftMargin+pixelsPerStep/2) / pixelsPerStep) ) ;
	}
	
	private int worldToViewVertical( ThreeValuedLogic value ) {
		int h = getHeight() ;
		switch( value ) {
		case TRUE : return (int)(h * Y_TRUE) ;
		case FALSE : return (int)(h * Y_FALSE) ;
		default : return (int)(h * Y_UNDEFINED) ; }
	}
	
	private ThreeValuedLogic viewToWorldVertical(int y) {
		int h = getHeight() ;
		if( y < h*(Y_TRUE+Y_UNDEFINED)/2 ) return ThreeValuedLogic.TRUE ;
		if( y > h*(Y_FALSE+Y_UNDEFINED)/2 ) return ThreeValuedLogic.FALSE ;
		return ThreeValuedLogic.UNDEFINED ;
	}
}