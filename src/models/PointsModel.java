package models;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class PointsModel implements Iterable<PointModel>, IDrawable {

	private PointModel[] puntos;

	public PointsModel() {
		this(0);
	}

	public PointsModel(int capacity) {
		puntos = new PointModel[capacity];
	}

	public boolean setValueAt(int index, PointModel point) {

		try {
			puntos[index] = point;
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	public boolean setValueAt(int index, Point point) {

		try {
			puntos[index] = new PointModel((double) point.x, (double) point.y);
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	public PointModel getValueAt(int index) {

		try {
			return puntos[index];
		} catch (Exception ex) {
			return null;
		}
	}

	@Deprecated
	public static int distance(Point p1, Point p2) {

		int aux = (int) (Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));

		return (int) Math.sqrt(aux);
	}

	public int size() {
		return puntos.length;
	}

	public Iterator<PointModel> iterator() {
		return Arrays.asList(puntos).iterator();
	}

	public void up(Double c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1d);

		aux.traslacionAt(1, -c);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();
	}

	public void down(Double c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1d);

		aux.traslacionAt(1, c);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void right(Double c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1d);

		aux.traslacionAt(0, c);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();
	}

	public void left(Double c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1d);

		aux.traslacionAt(0, -c);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void rotateLeft(double angulo) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1d);

		var sorted = sort();

		Double[] nMin = new Double[]{-sorted[0].x, -sorted[0].y};
		Double[] pMin = new Double[]{sorted[0].x, sorted[0].y};

		aux.traslacion(nMin);
		aux.rotacion(angulo);
		aux.traslacion(pMin);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void rotateRight(double angulo) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1d);

		var sorted = sort();

		Double[] nMin = new Double[]{-sorted[0].x, -sorted[0].y};
		Double[] pMin = new Double[]{sorted[0].x, sorted[0].y};

		aux.traslacion(nMin);
		aux.rotacion(-angulo);
		aux.traslacion(pMin);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void zoomIn(Double c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1d);

		var sorted = sort();

		Double[] nMin = new Double[]{-sorted[0].x, -sorted[0].y};
		Double[] pMin = new Double[]{sorted[0].x, sorted[0].y};

		aux.traslacion(nMin);
		aux.escalar(c);
		aux.traslacion(pMin);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public void zoomOut(Double c) {

		MatrixModel matrixPoints = MatrixModel.matrixFromPoints(this);

		MatrixModel aux = new MatrixModel(3, 3, 1d);

		var sorted = sort();

		Double[] nMin = new Double[]{-sorted[0].x, -sorted[0].y};
		Double[] pMin = new Double[]{sorted[0].x, sorted[0].y};

		aux.traslacion(nMin);
		aux.escalar(1d / c);
		aux.traslacion(pMin);

		matrixPoints.producto(aux);

		puntos = matrixPoints.toPoints();

	}

	public PointModel[] sort() {

		PointModel[] aux = puntos.clone();

		Arrays.sort(aux, (o1, o2) -> {
			return o1.distance(0d, 0d) < o2.distance(0d, 0d) ? -1 : 1;
		});

		return aux;
	}

	public Double[] getXPoints() {

		Double[] xPoints = new Double[size()];

		for (int i = 0; i < size(); i++) {
			xPoints[i] = puntos[i].getX();
		}

		return xPoints;
	}

	public Double[] getYPoints() {

		Double[] yPoints = new Double[size()];

		for (int i = 0; i < size(); i++) {
			yPoints[i] = puntos[i].getY();
		}

		return yPoints;
	}

	public int[] getXPointsInt() {

		int[] xPoints = new int[size()];

		for (int i = 0; i < size(); i++) {
			xPoints[i] = puntos[i].getX().intValue();
		}

		return xPoints;
	}

	public int[] getYPointsInt() {

		int[] yPoints = new int[size()];

		for (int i = 0; i < size(); i++) {
			yPoints[i] = puntos[i].getY().intValue();
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

			g.fillRect(p.getX().intValue(), p.getY().intValue(), 2, 2);
		}

	}

}
