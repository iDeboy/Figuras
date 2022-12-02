package models;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
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

	public static int distance(Point p1, Point p2) {

		int aux = (int) (Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));

		return (int) Math.sqrt(aux);
	}

	public int size() {
		return puntos.length;
	}

	public Iterator<Point> iterator() {
		return Arrays.asList(puntos).iterator();
	}

	public void up(int c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1);

		aux.traslacionAt(1, -c);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();
	}

	public void down(int c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1);

		aux.traslacionAt(1, c);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void right(int c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1);

		aux.traslacionAt(0, c);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();
	}

	public void left(int c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1);

		aux.traslacionAt(0, -c);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void rotateLeft(double angulo) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1);

		var sorted = sort();

		int[] nMin = new int[]{-sorted[0].x, -sorted[0].y};
		int[] pMin = new int[]{sorted[0].x, sorted[0].y};
		
		aux.traslacion(nMin);
		aux.rotacion(angulo);
		aux.traslacion(pMin);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void rotateRight(double angulo) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1);
		
		var sorted = sort();

		int[] nMin = new int[]{-sorted[0].x, -sorted[0].y};
		int[] pMin = new int[]{sorted[0].x, sorted[0].y};

		aux.traslacion(nMin);
		aux.rotacion(-angulo);
		aux.traslacion(pMin);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void zoomIn(int c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1);

		var sorted = sort();
		
		int[] nMin = new int[]{-sorted[0].x, -sorted[0].y};
		int[] pMin = new int[]{sorted[0].x, sorted[0].y};
		
		aux.traslacion(nMin);
		aux.escalar(c);
		aux.traslacion(pMin);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void zoomOut(int c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1);

		var sorted = sort();
		
		int[] nMin = new int[]{-sorted[0].x, -sorted[0].y};
		int[] pMin = new int[]{sorted[0].x, sorted[0].y};
		
		aux.traslacion(nMin);
		aux.escalar(1/c);
		aux.traslacion(pMin);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public Point[] sort() {

		Point[] aux = puntos.clone();

		Arrays.sort(aux, (o1, o2) -> {
			return o1.distance(0, 0) < o2.distance(0, 0) ? -1 : 1;
		});

		return aux;
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
	public void dibujar(Graphics2D g) {

		for (var p : this) {

			if (p == null) {
				continue;
			}

			g.fillRect(p.x, p.y, 2, 2);
		}

	}

}
