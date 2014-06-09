package algorithm.datastructure.matrix;

public class BinaryMatrix64 extends AbstractBinaryMatrix {
	private long[] matrix;

	public BinaryMatrix64(int rows, int cols) {
		super(rows, cols);
	}

	public BinaryMatrix64(String[] matrix, char significant) {
		super(matrix, significant);
	}

	protected void initMatrix() {
		matrix = new long[rows];
	}

	public void setBit(int row, int column) {
		matrix[row] |= 1L << column;
	}

	public void clearBit(int row, int column) {
		matrix[row] &= ~(1L << column);
	}
	
	public boolean isBitSet(int row, int column) {
		return (matrix[row] & 1L << column) > 0;
	}

	public int bitCount(int startingRow, int endingRow, int startingColumn, int endingColumn) {
		long mask = ((1L << (endingColumn + 1)) - 1) & ~((1L << startingColumn) - 1);
		int result = 0;
		for (int i = startingRow; i <= endingRow; i++)
			result += Long.bitCount(matrix[i] & mask);
		return result;
	}
}