package algorithm.deprecated;

// - TODO javadoc, exceptions messages
public class Cogwheel<E> {
	
	private int mask;
	private int offset;
	private int size;
	private Object[] values;
	
	public int size() {
		return size;
	}
	
	public Cogwheel(int capacity) {
		if ((size & (size-1)) > 0) throw new IllegalArgumentException();
		mask = capacity - 1;
		values = new Object[capacity];
	}
	
	public E insert(int index, E value) {
		if (index < 0 | index > size) throw new IndexOutOfBoundsException();
		Object last = size > mask ? values[(offset + mask) & mask] : null;
		if (index + index < size) {
			offset = (offset + mask) & mask;
			for (int i = 0; i < index; i++) {
				values[(offset + i) & mask] = values[(offset + i + 1) & mask];
			}
		}
		else {
			for (int i = size < mask ? size : mask; i > index; i--) {
				values[(offset + i) & mask] = values[(offset + i - 1) & mask];
			}
		}
		values[(offset + index) & mask] = value;
		if (size <= mask) size += 1;
		return (E) last;
	}
	
	public E remove(int index) {
		if (index < 0 | index >= size) throw new IndexOutOfBoundsException();
		E removed = get(index);
		if (index + index < size) {
			for (int i = index; i > 0; i--) {
				values[(offset + i) & mask] = values[(offset + i - 1) & mask];					
			}
			offset = (offset + 1) & mask;
		}
		else {
			for (int i = index + 1; i < size; i++) {
				values[(offset + i - 1) & mask] = values[(offset + i) & mask];
			}
		}
		size -= 1;
		return removed;
	}
	
	public E get(int index) {
		return (E) values[(index + offset) & mask];
	}

}
