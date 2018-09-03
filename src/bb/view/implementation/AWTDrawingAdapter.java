package bb.view.implementation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import util.Assert;
import bb.model.interfaces.DrawingAdapterI;
import bb.model.interfaces.OrientationE;
import bb.model.interfaces.WorldToViewI;

public class AWTDrawingAdapter implements DrawingAdapterI {

	private class Box {
		final double xa, ya, xb, yb, xc, yc, xd, yd;

		Box(double x, double y, double width, double height, OrientationE orientation) {
			double x0 = worldToView.convertX(x - width / 2);
			double y0 = worldToView.convertY(y - height / 2);
			double x1 = worldToView.convertX(x + width / 2);
			double y1 = worldToView.convertY(y + height / 2);
			switch (orientation) {
			case EAST: {
				xa = x0;
				ya = y0;
				xb = x1;
				yb = y0;
				xc = x1;
				yc = y1;
				xd = x0;
				yd = y1;
			}
				break;
			case SOUTH: {
				xd = x0;
				yd = y0;
				xa = x1;
				ya = y0;
				xb = x1;
				yb = y1;
				xc = x0;
				yc = y1;
			}
				break;
			case WEST: {
				xc = x0;
				yc = y0;
				xd = x1;
				yd = y0;
				xa = x1;
				ya = y1;
				xb = x0;
				yb = y1;
			}
				break;
			case NORTH: {
				xb = x0;
				yb = y0;
				xc = x1;
				yc = y0;
				xd = x1;
				yd = y1;
				xa = x0;
				ya = y1;
			}
				break;
			default:
				throw new AssertionError("Unreachable");
			}
		}
	}

	private Graphics2D g;
	private WorldToViewI worldToView;
	private Color fillColor = Color.GRAY;
	private Color drawColor = Color.BLACK;
	private Color textColor = Color.BLACK;
	private final Font font;
	private double MIN_FONT_SIZE = 15;

	public AWTDrawingAdapter(Graphics2D graphics, WorldToViewI worldToView) {
		this.g = graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.font = graphics.getFont();
		this.worldToView = worldToView;
	}

	@Override
	public void setLineColor(int r, int g, int b) {
		Assert.check(0 <= r && r < 256);
		Assert.check(0 <= g && g < 256);
		Assert.check(0 <= b && b < 256);
		drawColor = new Color(r, g, b);
	}

	@Override
	public void setFillColor(int r, int g, int b) {
		Assert.check(0 <= r && r < 256);
		Assert.check(0 <= g && g < 256);
		Assert.check(0 <= b && b < 256);
		fillColor = new Color(r, g, b);
	}

	@Override
	public void setTextColor(int r, int g, int b) {
		Assert.check(0 <= r && r < 256);
		Assert.check(0 <= g && g < 256);
		Assert.check(0 <= b && b < 256);
		textColor = new Color(r, g, b);
	}

	@Override
	public void setLineWidth(float width) {
		Assert.check(0 <= width);
		double w = width * (worldToView.convertX(1) - worldToView.convertX(0));
		g.setStroke(new BasicStroke((float) w));
	}

	@Override
	public void drawText(String str, double x, double y, double width, double height) {
		Assert.check(width > 0);
		Assert.check(height > 0);
		if (str.length() == 0)
			return;
		double x0 = worldToView.convertX(x - width / 2);
		double y0 = worldToView.convertY(y - height / 2);
		double x1 = worldToView.convertX(x + width / 2);
		double y1 = worldToView.convertY(y + height / 2);
		double h = y1 - y0;
		double w = x1 - x0;

		FontMetrics fm = g.getFontMetrics(font);
		double xstretch = w / fm.stringWidth(str);
		double ystretch = h / (fm.getAscent() + fm.getDescent());
		double minstretch = MIN_FONT_SIZE / (fm.getAscent() + fm.getDescent());
		double stretch = Math.max(minstretch, Math.min(xstretch, ystretch));
		AffineTransform t = AffineTransform.getScaleInstance(stretch, stretch);
		Font f1 = font.deriveFont(t);
		g.setFont(f1);
		FontMetrics fm1 = g.getFontMetrics(f1);
		double ybl = y0 + (y1 - y0) / 2 + fm1.getAscent() / 2;
		g.setColor(textColor);
		g.drawString(str, (float) x0, (float) ybl);
	}

	@Override
	public void drawAndShape(double x, double y, double width, double height, OrientationE orientation) {
		Box r = new Box(x, y, width, height, orientation);
		GeneralPath gp = new GeneralPath();
		// Make a rectangle from a to (a+b)/2 to (c+d)/2 to d and back to a.
		gp.moveTo(r.xa, r.ya);
		gp.lineTo((r.xa + r.xb) / 2, (r.ya + r.yb) / 2);
		gp.lineTo((r.xc + r.xd) / 2, (r.yc + r.yd) / 2);
		gp.lineTo(r.xd, r.yd);
		gp.closePath();

		// Make an ellipse within box a, b, c, d
		double cxmin = Math.min(Math.min(r.xa, r.xb), Math.min(r.xc, r.xd));
		double cxmax = Math.max(Math.max(r.xa, r.xb), Math.max(r.xc, r.xd));
		double cymin = Math.min(Math.min(r.ya, r.yb), Math.min(r.yc, r.yd));
		double cymax = Math.max(Math.max(r.ya, r.yb), Math.max(r.yc, r.yd));
		Ellipse2D circle = new Ellipse2D.Double(cxmin, cymin, cxmax - cxmin, cymax - cymin);

		// Union the rectangle and ellipse
		Area a = new Area(circle);
		a.add(new Area(gp));
		g.setColor(fillColor);
		g.fill(a);
		g.setColor(drawColor);
		g.draw(a);
	}

	@Override
	public void drawOrShape(double x, double y, double width, double height, OrientationE orientation) {
		Box r = new Box(x, y, width, height, orientation);
		GeneralPath gp = new GeneralPath();
		// Start at point a
		gp.moveTo(r.xa, r.ya);
		// Curve to point (b+c)/2 --- Control point is between a and b
		gp.quadTo((r.xa + r.xb) / 2, (r.ya + r.yb) / 2, (r.xb + r.xc) / 2, (r.yb + r.yc) / 2);
		// Curve to point d --- Control point is between c and d
		gp.quadTo((r.xc + r.xd) / 2, (r.yc + r.yd) / 2, r.xd, r.yd);
		// Curve to point a
		gp.quadTo(0.25 * (r.xa + r.xb + r.xc + r.xd), 0.25 * (r.ya + r.yb + r.yc + r.yd), r.xa, r.ya);
		// Close it just in case.
		gp.closePath();
		g.setColor(fillColor);
		g.fill(gp);
		g.setColor(drawColor);
		g.draw(gp);
	}

	@Override
	public void drawBufferShape(double x, double y, double width, double height, OrientationE orientation) {
		Box r = new Box(x, y, width, height, orientation);
		GeneralPath gp = new GeneralPath();
		// Start at point a
		gp.moveTo(r.xa, r.ya);
		// Line to point (b+c)/2
		gp.lineTo((r.xb + r.xc) / 2, (r.yb + r.yc) / 2);
		// Line to d
		gp.lineTo(r.xd, r.yd);
		// Back to a
		gp.closePath();
		g.setColor(fillColor);
		g.fill(gp);
		g.setColor(drawColor);
		g.draw(gp);
	}

	@Override
	public void drawNotShape(double x, double y, double width, double height, OrientationE orientation) {
		Box r = new Box(x, y, width, height, orientation);
		GeneralPath gp = new GeneralPath();
		// Start at point a
		gp.moveTo(r.xa, r.ya);
		// Line to point (b+c)/2
		gp.lineTo((r.xb + r.xc) / 2, (r.yb + r.yc) / 2);
		// Line to d
		gp.lineTo(r.xd, r.yd);
		// Back to a
		gp.closePath();
		g.setColor(fillColor);
		g.fill(gp);
		g.setColor(drawColor);
		g.draw(gp);

		double circleCentreX = (r.xb + r.xc) / 2;
		double circleCentreY = (r.yb + r.yc) / 2;
		double radius = Math.sqrt((r.xb - r.xc) * (r.xb - r.xc) + (r.yb - r.yc) * (r.yb - r.yc)) / 10;
		Ellipse2D circle = new Ellipse2D.Double(circleCentreX - radius, circleCentreY - radius, 2 * radius, 2 * radius);
		g.setColor(fillColor);
		g.fill(circle);
		g.setColor(drawColor);
		g.draw(circle);
	}

	@Override
	public void drawRect(double x, double y, double width, double height) {
		double x0 = worldToView.convertX(x - width / 2);
		double y0 = worldToView.convertY(y - height / 2);
		double x1 = worldToView.convertX(x + width / 2);
		double y1 = worldToView.convertY(y + height / 2);
		Rectangle2D rect = new Rectangle2D.Double(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x1 - x0),
				Math.abs(y1 - y0));
		g.setColor(fillColor);
		g.fill(rect);
		g.setColor(drawColor);
		g.draw(rect);

	}

	@Override
	public void drawCricle(double x, double y, double radius) {
		double x0 = worldToView.convertX(x - radius);
		double y0 = worldToView.convertY(y - radius);
		double x1 = worldToView.convertX(x + radius);
		double y1 = worldToView.convertY(y + radius);
		Ellipse2D circle = new Ellipse2D.Double(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x1 - x0),
				Math.abs(y1 - y0));
		g.setColor(fillColor);
		g.fill(circle);
		g.setColor(drawColor);
		g.draw(circle);
	}

	@Override
	public void drawLine(double vx0, double vy0, double vx1, double vy1) {
		double x0 = worldToView.convertX(vx0);
		double y0 = worldToView.convertY(vy0);
		double x1 = worldToView.convertX(vx1);
		double y1 = worldToView.convertY(vy1);
		Line2D line = new Line2D.Double(x0, y0, x1, y1);
		g.setColor(drawColor);
		g.draw(line);
	}
}
