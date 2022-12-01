package models;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class CuadrilateroModel extends FiguraModel {

	public CuadrilateroModel() {
		super();
		puntos = new PointsModel(4);
	}

	@Override
	public void dibujar(Graphics g) {

		puntos.dibujar(g);

		if (!canDraw()) {
			return;
		}
		
		if (isSelected()) {
			g.setColor(Color.RED);
		}else{
			g.setColor(background);
		}
		
		g.drawPolygon(puntos.getXPoints(), puntos.getYPoints(), puntos.size());
		
		g.setColor(background);
		g.fillPolygon(puntos.getXPoints(), puntos.getYPoints(), puntos.size());
		
		// Reset color
		g.setColor(Color.BLACK);
	}

}
