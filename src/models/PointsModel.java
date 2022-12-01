package models;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class PointsModel implements Iterable<Point>, IDrawable {

	private Point[] puntos;

	public PointsModel() {
		this(0);
	}

	public PointsModel(int capacity) {
		puntos = new Point[capacity];
	}

	public boolean setValueAt(int index, Point point) {

		try {
			puntos[index] = point;
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	public Point getValueAt(int index) {

		try {
			return puntos[index];
		} catch (Exception ex) {
			return null;
		}
	}

	public int size() {
		return puntos.length;
	}

	public Iterator<Point> iterator() {
		return Arrays.asList(puntos).iterator();
	}

	public int[] getXPoints() {

		int[] xPoints = new int[size()];

		for (int i = 0; i < size(); i++) {
			xPoints[i] = puntos[i].x;
		}

		return xPoints;
	}

	public int[] getYPoints() {

		int[] yPoints = new int[size()];

		for (int i = 0; i < size(); i++) {
			yPoints[i] = puntos[i].y;
		}

		return yPoints;
	}

	public boolean isFull() {

		for (var p : this) {
			if (p == null) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void dibujar(Graphics g) {

		for (var p : this) {

			if (p == null) {
				continue;
			}

			g.fillRect(p.x, p.y, 2, 2);
		}

	}

}
