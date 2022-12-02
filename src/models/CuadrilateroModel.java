package models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class CuadrilateroModel extends FiguraModel {

	public CuadrilateroModel() {
		super();
		nombre = "Cuadrilatero";
		puntos = new PointsModel(4);
	}

	@Override
	public void dibujar(Graphics2D g) {

		if (!canDraw()) {
			puntos.dibujar(g);
			return;
		}

		if (isSelected()) {
			g.setStroke(new BasicStroke(10));
			g.setColor(Color.RED);
		} else {
			g.setColor(background);
		}

		g.drawPolygon(puntos.getXPoints(), puntos.getYPoints(), puntos.size());

		g.setStroke(new BasicStroke(2));
		g.setColor(background);
		g.fillPolygon(puntos.getXPoints(), puntos.getYPoints(), puntos.size());

		// Reset color
		g.setColor(Color.BLACK);
	}

}
