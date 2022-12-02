package models;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public abstract class FiguraModel implements Iterable<Point>, IDrawable {

	private int id;
	protected String nombre;
	protected PointsModel puntos;
	protected Color background;
	protected boolean selected;

	private static final ArrayList<Integer> ids = new ArrayList<>();

	protected FiguraModel() {
		id = generateId();
		nombre = "Figura";
		background = Color.BLACK;
		selected = false;
		
	}

	private static int generateId() {

		int id = new Random().nextInt(1, Integer.MAX_VALUE);

		while (ids.contains(id)) {
			id = new Random().nextInt(1, Integer.MAX_VALUE);
		}

		ids.add(id);

		return id;
	}

	public int getId() {
		return id;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public PointsModel getPuntos() {
		return puntos;
	}
	
	public String getNombre(){
		return nombre;
	}

	public Iterator<Point> iterator() {
		return puntos.iterator();
	}

	public boolean canDraw() {
		return puntos.isFull();
	}

	@Override
	public String toString() {

		ArrayList<String> puntosStr = new ArrayList<>();

		for (int i = 0; i < this.puntos.size(); i++) {

			if (this.puntos.getValueAt(i) == null) {
				continue;
			}

			puntosStr.add("\tP" + (i + 1) + "(" + this.puntos.getValueAt(i).x + ", " + this.puntos.getValueAt(i).y + ")");
			
		}

		return nombre + "{\n" + String.join(", \n", puntosStr) + "\n}";
	}

}
