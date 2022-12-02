package models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class CirculoModel extends FiguraModel {

	public CirculoModel() {
		super();
		nombre = "Circulo";
		puntos = new PointsModel(2);
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

		Point p1 = puntos.getValueAt(0);
		Point p2 = puntos.getValueAt(1);

		int distance = PointsModel.distance(p1, p2);

		g.drawOval(p1.x - distance, p1.y - distance, 2 * distance, 2 * distance);

		g.setStroke(new BasicStroke(2));
		g.setColor(background);
		g.fillOval(p1.x - distance, p1.y - distance, 2 * distance, 2 * distance);

		// Reset color
		g.setColor(Color.BLACK);

	}

	@Override
	public String toString() {

		String centro = "";
		String radio = "";
		ArrayList<String> valores = new ArrayList<>();

		if (puntos.getValueAt(0) != null) {
			centro = "Centro(" + puntos.getValueAt(0).x + ", " + puntos.getValueAt(0).y + ")";
			valores.add(centro);
		}

		if (canDraw()) {
			radio = "Radio=" + PointsModel.distance(puntos.getValueAt(0), puntos.getValueAt(1));
			valores.add(radio);
		}

		return "CirculoModel{\n" + String.join(", ", valores) + "\n}";
	}

}
