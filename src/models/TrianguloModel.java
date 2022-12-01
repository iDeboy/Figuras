package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class TrianguloModel extends FiguraModel {

	public TrianguloModel() {
		super();
		puntos = new PointsModel(3);
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
