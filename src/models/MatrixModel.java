package models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class MatrixModel implements IMatrix, Iterable<ArrayList<Integer>> {

	public final MatrixModel IDENTITY;

	private final int rows;
	private final int columns;
	private final boolean identity;
	private ArrayList<Integer>[] matrix;

	public MatrixModel(int rows, int columns) {
		this(rows, columns, 0);
	}

	public MatrixModel(int rows, int columns, int diagonal) {
		this(rows, columns, diagonal, false);
	}

	public MatrixModel(int orden) {
		this(orden, 1, false);
	}

	private MatrixModel(int orden, boolean identity) {
		this(orden, 1, identity);
	}

	private MatrixModel(int orden, int diagonal, boolean identity) {
		this(orden, orden, diagonal, identity);
	}

	private MatrixModel(int rows, int columns, int diagonal, boolean identity) {
		this.rows = rows;
		this.columns = columns;
		this.identity = identity;

		initMatrix();
		initMatrixWithDiagonal(diagonal);
		IDENTITY = initIdentity();
	}

	private void initMatrix() {
		matrix = new ArrayList[rows];

		for (int i = 0; i < rows; i++) {

			matrix[i] = new ArrayList<>();

			for (int j = 0; j < columns; j++) {
				matrix[i].add(0);
			}

		}
	}

	private MatrixModel initIdentity() {

		if (this.isIdentity()) {
			return this;
		}

		MatrixModel _identity = new MatrixModel(rows, true);

		return _identity;
	}

	private void initMatrixWithDiagonal(int diagonalNumber) {

		if (this.isIdentity()) {
			diagonalNumber = 1;
		}

		for (int pointIndex = 0; pointIndex < this.columns; pointIndex++) {

			for (int varIndex = 0; varIndex < this.rows; varIndex++) {

				if (pointIndex == varIndex) {
					this._setValueAt(varIndex, pointIndex, diagonalNumber);
				}

			}

		}

	}

	private boolean _addPoint(int[] point) {

		if (rows != point.length) {
			return false;
		}

		for (int i = 0; i < rows; i++) {

			if (!matrix[i].add(point[i])) {
				initMatrix();
				return false;
			}

		}

		return true;
	}

	@Override
	public boolean addPoint(int[] point) {

		if (this.isIdentity()) {
			return false;
		}

		return _addPoint(point);
	}

	private boolean _setValueAt(int row, int column, int value) {

		if (column < 0 || row < 0) {
			return false;
		}

		if (row > rows - 1) {
			return false;
		}

		if (matrix[row] == null) {
			return false;
		}

		if (column > columns - 1) {
			return false;
		}

		matrix[row].set(column, value);
		return true;
	}

	@Override
	public boolean setValueAt(int row, int column, int value) {

		if (this.isIdentity()) {
			return false;
		}

		return _setValueAt(row, column, value);

	}

	@Override
	public int get(int row, int column) {
		return matrix[row].get(column);
	}

	public static MatrixModel producto(MatrixModel matrix1, MatrixModel matrix2) {

		if (matrix2.rows != matrix1.columns) {
			return null;
		}

		MatrixModel product = new MatrixModel(matrix1.rows, matrix2.columns, 0, false);

		for (int i = 0; i < matrix1.rows; i++) {

			for (int j = 0; j < matrix2.columns; j++) {

				for (int k = 0; k < matrix2.rows; k++) {

					product.setValueAt(i, j, product.get(i, j)
									+ matrix1.get(i, k) * matrix2.get(k, j));

				}

			}

		}

		return product;
	}

	public static MatrixModel matrixFromPoints(PointsModel points) {

		MatrixModel aux = new MatrixModel(3, points.size());

		int[] xs = points.getXPoints();
		int[] ys = points.getYPoints();

		for (int i = 0; i < xs.length; i++) {
			aux.setValueAt(0, i, xs[i]);
		}

		for (int i = 0; i < ys.length; i++) {
			aux.setValueAt(1, i, ys[i]);
		}

		for (int i = 0; i < points.size(); i++) {
			aux.setValueAt(2, i, 1);
		}

		return aux;
	}

	@Override
	public void producto(MatrixModel other) {

		if (this.isIdentity()) {
			return;
		}

		var producto = producto(other, this);

		if (producto == null) {
			return;
		}

		matrix = producto.matrix;
	}

	@Override
	public void escalar(int escala) {

		int[] escalaAll = new int[rows - 1];

		for (int i = 0; i < escalaAll.length; i++) {
			escalaAll[i] = escala;
		}

		escalar(escalaAll);
	}

	@Override
	public void escalarAt(int variableIndex, int escala) {

		MatrixModel matrizEscala = new MatrixModel(rows);

		matrizEscala.initMatrixWithDiagonal(1);

		for (int pointIndex = 0; pointIndex < matrizEscala.columns; pointIndex++) {

			for (int varIndex = 0; varIndex < matrizEscala.rows - 1; varIndex++) {

				if (pointIndex == varIndex && variableIndex == varIndex) {
					matrizEscala._setValueAt(varIndex, pointIndex, escala);
				}

			}

		}

		producto(matrizEscala);
	}


	@Override
	public void escalar(int[] escalas) {

		if (escalas.length > rows - 1) { // 3 2
			return;
		}

		for (int i = 0; i < escalas.length; i++) {
			escalarAt(i, escalas[i]);
		}

	}

	@Override
	public void rotacion(double angulo) {

		if (rows < 3) {
			return;
		}

		MatrixModel matrizRotacion = new MatrixModel(rows);

		matrizRotacion.initMatrixWithDiagonal(1);

		matrizRotacion._setValueAt(0, 0, (int) Math.cos(Math.toRadians(angulo)));
		matrizRotacion._setValueAt(0, 1, (int) -Math.sin(Math.toRadians(angulo)));
		matrizRotacion._setValueAt(1, 0, (int) Math.sin(Math.toRadians(angulo)));
		matrizRotacion._setValueAt(1, 1, (int) Math.cos(Math.toRadians(angulo)));

		producto(matrizRotacion);
	}

	@Override
	public void traslacionAt(int variableIndex, int t) {

		MatrixModel matrizTraslacion = new MatrixModel(rows);

		matrizTraslacion.initMatrixWithDiagonal(1);

		for (int varIndex = 0; varIndex < matrizTraslacion.rows - 1; varIndex++) {

			if (varIndex == variableIndex) {
				matrizTraslacion._setValueAt(varIndex, matrizTraslacion.columns - 1, t);
			}

		}

		producto(matrizTraslacion);

	}

	@Override
	public void traslacion(int[] ts) {

		if (ts.length > rows - 1) {
			return;
		}

		for (int i = 0; i < ts.length; i++) {
			traslacionAt(i, ts[i]);
		}

	}

	public static ArrayList<String> arrayIntToString(ArrayList<Integer> integers) {

		ArrayList<String> result = new ArrayList<>();

		for (var integer : integers) {
			result.add(String.valueOf(integer));
		}

		return result;
	}

	@Override
	public String toString() {

		String result = "";

		if (rows == 0) {
			return "";
		}

		for (int i = 0; i < rows - 1; i++) {

			result += "X" + (i + 1) + "\t→ | ";

			for (var it : matrix[i]) {

				result += it + " ";

			}

			result += "|\n";
		}

		result += "h\t→ | ";

		for (var it : matrix[rows - 1]) {

			result += it + " ";

		}

		result += "|\n";

		return result;
	}

	public ArrayList<Integer>[] toArray() {
		return matrix.clone();
	}

	public Point[] toPoints() {

		Point[] points = new Point[columns];

		for (int i = 0; i < columns; i++) {

			points[i] = new Point(get(0, i), get(1, i));

		}

		return points;

	}

	public boolean isIdentity() {
		return identity;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	@Override
	public Iterator<ArrayList<Integer>> iterator() {
		return Arrays.asList(matrix).iterator();
	}

}
