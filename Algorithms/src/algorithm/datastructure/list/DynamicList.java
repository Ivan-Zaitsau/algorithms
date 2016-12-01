package algorithm.datastructure.list;

public class DynamicList<E> {
	
	static final private class Block<E> {

		int size;

		private int offset;
		private E[] values;

		@SuppressWarnings("unchecked")
		Block(int capacity) {
			this.offset = 0;
			this.size = 0;
			this.values = (E[]) new Object[capacity];
		}

		E addFirst(final E value) {
			offset = (offset - 1) & (values.length-1);
			E last = values[offset];
			values[offset] = value;
			if (size < values.length) {
				size++;
			}
			return last;
		}
		
		void addLast(final E value) {
			if (size == values.length)
				return;
			values[(offset + size) & (values.length-1)] = value;
			size++;
		}
		
		E add(final int index, final E value) {
			E last = (size == values.length) ? values[(offset - 1) & (values.length-1)] : null;
			if (2*index < size) {
				offset = (offset - 1) & (values.length-1);
				for (int i = 0; i < index; i++) {
					values[(offset + i) & (values.length-1)] = values[(offset + i + 1) & (values.length-1)];
				}
			}
			else {
				for (int i = (size == values.length) ? size-1 : size; i > index; i--) {
					values[(offset + i) & (values.length-1)] = values[(offset + i - 1) & (values.length-1)];
				}
			}
			values[(offset + index) & (values.length-1)] = value;
			if (size < values.length) size++;
			return last;
		}

		E set(final int index, final E value) {
			int i = (offset + index) & (values.length-1);
			E replaced = values[i];
			values[i] = value;
			return replaced;
		}
		
		E removeFirst() {
			E removed = values[offset];
			values[offset] = null;
			offset = (offset + 1) & (values.length-1);
			size--;
			return removed;
		}
		
		E remove(final int index) {
			E removed = values[(offset + index) & (values.length-1)];
			if (2*index < size) {
				for (int i = index; i > 0; i--) {
					values[(offset + i) & (values.length-1)] = values[(offset + i - 1) & (values.length-1)];					
				}
				values[offset] = null;
				offset = (offset + 1) & (values.length-1);
			}
			else {
				for (int i = index + 1; i < size; i++) {
					values[(offset + i - 1) & (values.length-1)] = values[(offset + i) & (values.length-1)];
				}
				values[(offset + size - 1) & (values.length-1)] = null;
			}
			size--;
			return removed;
		}
		
		E get(final int index) {
			return values[(offset + index) & (values.length-1)];
		}
	}
	
	public long size;
	
	private final int blockBitsize;
	private Block<E>[] data;
	
	@SuppressWarnings("unchecked")
	public DynamicList(long capacity) {
		byte blockBitsize = 4;
		while (capacity > 1L << (2*blockBitsize)) {
			blockBitsize++;
		}
		data = new Block[1 + (int) ((capacity-1) >> blockBitsize)];
		for (int i = 0; i < data.length; i++)
			data[i] = new Block<E>(1 << blockBitsize);
		this.size = 0;
		this.blockBitsize = blockBitsize;
	}
	
	public E get(final long index) {
		final int blockIndex = (int) (index >>> blockBitsize);
		final int valueIndex = (int) (index & (-1L >>> -blockBitsize));
		return data[blockIndex].get(valueIndex);
	}
	
	public void set(final long index, E value) {
		final int blockIndex = (int) (index >>> blockBitsize);
		final int valueIndex = (int) (index & (-1L >>> -blockBitsize));
		data[blockIndex].set(valueIndex, value);
	}
	
	public void insert(final long index, E value) {
		int blockIndex = (int) (index >>> blockBitsize);
		int valueIndex = (int) (index & (-1L >>> -blockBitsize));
		int blockSize = 1 << blockBitsize;
		if (data[blockIndex].size < blockSize) {
			data[blockIndex].add(valueIndex, value);
		}
		else {
			value = data[blockIndex].add(valueIndex, value);
			while (data[++blockIndex].size == blockSize) {
				value = data[blockIndex].addFirst(value);
			}
			data[blockIndex].addFirst(value);
		}
		size++;
	}
	
	public E remove(final long index) {
		int blockIndex = (int) (index >>> blockBitsize);
		int valueIndex = (int) (index & (-1L >>> -blockBitsize));
		E removed = data[blockIndex].remove(valueIndex);
		while (++blockIndex < data.length && data[blockIndex].size > 0) {
			data[blockIndex-1].addLast(data[blockIndex].removeFirst());
		}
		size--;
		return removed;
	}
}
