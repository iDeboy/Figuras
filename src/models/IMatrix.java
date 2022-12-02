package models;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public interface IMatrix {

	void producto(MatrixModel other);

	void escalar(int escala);									// Zoom in/out

	void escalarAt(int variableIndex, int escala);				// Zoom in/out At variableIndex

	//void escalarY(int escalaY);							// Zoom in/out Y
	void escalar(int[] escalas);						  // Zoom in/out Y

	void rotacion(double angulo);							// Rotate left/right

	void traslacion(int[] ts);							  // Up | Down | Left | Right | 

	void traslacionAt(int variableIndex, int t);

	boolean addPoint(int[] point);

	boolean setValueAt(int row, int column, int value);

	int get(int row, int column);
}
