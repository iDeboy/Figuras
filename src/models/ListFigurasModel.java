package models;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class ListFigurasModel extends AbstractListModel<FiguraModel> implements Iterable<FiguraModel> {

	private final ArrayList<FiguraModel> data;

	public ListFigurasModel() {
		super();
		data = new ArrayList<>();
	}

	public void checkForIncompletes() {

		for (int i = 0; i < getSize(); i++) {
			if (!getElementAt(i).canDraw()) {

				removeElement(getElementAt(i));

			}
		}

	}

	public boolean addElement(FiguraModel figura) {

		int index = indexOf(figura);

		if (index != -1) {
			setElementAt(index, figura);
			return true;
		}

		index = getSize();
		boolean completed = data.add(figura);

		if (completed) {
			fireIntervalAdded(this, index, index);
		}

		return completed;
	}

	public boolean removeElement(FiguraModel figura) {

		int index = indexOf(figura);

		boolean completed = data.remove(figura);

		if (index != -1) {
			fireIntervalRemoved(this, index, index);
		}

		return completed;
	}

	public FiguraModel setElementAt(int index, FiguraModel figura) {

		FiguraModel old = data.set(index, figura);
		fireContentsChanged(this, index, index);

		return old;
	}

	public FiguraModel removeElementAt(int index) {

		FiguraModel old = data.remove(index);
		fireIntervalRemoved(this, index, index);

		return old;
	}

	public void clear() {

		int endIndex = getSize() - 1;
		data.clear();

		if (endIndex != -1) {
			fireIntervalRemoved(this, 0, endIndex);
		}

	}

	public boolean isEmpty() {
		return data.isEmpty();
	}

	public boolean contains(FiguraModel figura) {

		for (FiguraModel it : this) {

			if (it.getId() == figura.getId()) {
				return true;
			}

		}

		return false;
	}

	public int indexOf(FiguraModel figura) {
		if (!contains(figura)) {
			return -1;
		}

		for (int i = 0; i < data.size(); i++) {

			if (data.get(i).getId() == figura.getId()) {
				return i;
			}

		}

		return -1;
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
	public Iterator<FiguraModel> iterator() {
		return data.iterator();
	}

	public FiguraModel[] toArray() {
		return (FiguraModel[]) data.toArray();
	}

}
