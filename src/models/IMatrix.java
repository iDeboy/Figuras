package models;

/**
 *
 * @author Honorio Acosta Ruiz
 * @param <T>
 */
public interface IMatrix<T extends Number> {

	void producto(MatrixModel other);

	void escalar(T escala);									// Zoom in/out

	void escalarAt(int variableIndex, T escala);				// Zoom in/out At variableIndex

	//void escalarY(int escalaY);							// Zoom in/out Y
	void escalar(T[] escalas);						  // Zoom in/out Y

	void rotacion(double angulo);							// Rotate left/right

	void traslacion(T[] ts);							  // Up | Down | Left | Right | 

	void traslacionAt(int variableIndex, T t);

	boolean addPoint(T[] point);

	boolean setValueAt(int row, int column, T value);

	T get(int row, int column);
}
