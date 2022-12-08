package models;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class PointModel {

	public Double x;
	public Double y;

	public PointModel() {
		this(0d, 0d);
	}

	public PointModel(PointModel other) {
		this(other.x, other.y);
	}

	public PointModel(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public void setLocation(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double distance(PointModel other) {

		double aux = Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2);

		return Math.sqrt(aux);
	}

	public Double distance(Double x, Double y) {
		return this.distance(new PointModel(x, y));
	}

}
