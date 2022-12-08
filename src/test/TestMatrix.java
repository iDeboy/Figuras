package test;

import models.MatrixModel;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class TestMatrix {

	public static void main(String[] args) {

		MatrixModel M = new MatrixModel(3);

		MatrixModel P = new MatrixModel(3, 2);

		P.setValueAt(0, 0, 10d);
		P.setValueAt(1, 0, 20d);
		P.setValueAt(2, 0, 1d);

		P.setValueAt(0, 1, 5d);
		P.setValueAt(1, 1, 10d);
		P.setValueAt(2, 1, 1d);

		System.out.println(M);
		System.out.println(P);

		M.traslacion(new Double[]{-3d, 3d}); // M1
		M.escalar(3d);										// M2
		M.traslacion(new Double[]{0d, 2d});  // M3
		// .
		// .
		// Mn

		//var R = MatrixModel.producto(M, P);
		P.producto(M);
		System.out.println(M);
		System.out.println(P);
		//System.out.println(R);

	}

}
