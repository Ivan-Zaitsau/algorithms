package algorithm.deprecated;

import java.util.ArrayList;
import java.util.List;

public class SimpleShiftBucket<E> implements ShiftBucket<E> {

	private int elementsCount;
	private int offset;
	private int mask;
	private Object[] elements;

	public SimpleShiftBucket(int size) {
		if (Integer.bitCount(size) == 1) {
			elementsCount = 0;
			offset = 0;
			mask = size - 1;
			elements = new Object[size];
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@SuppressWarnings("unchecked")
	public E insert(int index, E element) {
		if (element == null)
			return null;
		if (elementsCount <= mask | index+index > mask) {
			for (int i = index; element != null & i <= mask; i++) {
				E nextElement = (E) elements[(offset + i) & mask];
				elements[(offset + i) & mask] = element;
				element = nextElement;
			}
			if (element == null) {
				elementsCount++;
			}
			return element;
		}
		else {
			offset = (offset + mask) & mask;
			E lastElement = (E) elements[offset];
			for (int i = 0; i < index; i++) {
				elements[(offset + i) & mask] = elements[(offset + i + 1) & mask];
			}
			elements[(offset + index) & mask] = element;
			return lastElement;
		}
	}

	@SuppressWarnings("unchecked")
	public E get(int position) {
		return (E) elements[(position + offset) & mask];
	}

	public List<E> getElements() {
		List<E> elementsList = new ArrayList<E>(mask + 1);
		for (int i = 0; i <= mask; i++) {
			elementsList.add(get(i));
		}
		return elementsList;
	}
}