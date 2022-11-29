package models;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class ListFigurasModel implements ListModel<FiguraModel>, Iterable<FiguraModel> {

	private final ArrayList<FiguraModel> data;
	private final ArrayList<ListDataListener> listeners;

	public ListFigurasModel() {
		data = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	public boolean addElement(FiguraModel figura) {
		return data.add(figura);
	}

	public boolean removeElement(FiguraModel figura) {
		return data.remove(figura);
	}

	public FiguraModel removeElementAt(int index) {
		return data.remove(index);
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public FiguraModel getElementAt(int index) {
		return data.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	@Override
	public Iterator<FiguraModel> iterator() {
		return data.iterator();
	}

}
