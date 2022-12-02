package test;

import models.MatrixModel;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class TestMatrix {

	public static void main(String[] args) {

		MatrixModel M = new MatrixModel(3);

		MatrixModel P = new MatrixModel(3,2);

		P.setValueAt(0, 0, 10);
		P.setValueAt(1, 0, 20);
		P.setValueAt(2, 0, 1);
		
		P.setValueAt(0, 1, 5);
		P.setValueAt(1, 1, 10);
		P.setValueAt(2, 1, 1);
		
		System.out.println(M);
		System.out.println(P);

		M.traslacion(new int[]{-3, 3}); // M1
		M.escalar(3);										// M2
		M.traslacion(new int[]{0, 2});  // M3
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
