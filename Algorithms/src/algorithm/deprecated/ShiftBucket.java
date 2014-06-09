package algorithm.deprecated;

import java.util.List;

public interface ShiftBucket<E> {

	public E insert(int index, E element);

	public E get(int index);

	public List<E> getElements();

}