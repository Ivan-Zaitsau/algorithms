package algorithm.datastructure.matrix;

public class BinaryMatrixX extends AbstractBinaryMatrix {
	private int width;
	private long[] matrix;
	
	public BinaryMatrixX(int rows, int cols) {
		super(rows, cols);
	}

	public BinaryMatrixX(String[] matrix, char significant) {
		super(matrix, significant);
	}

	protected void initMatrix() {
		width = 1 + ((columns - 1) >>> 6);
		matrix = new long[rows * width];
	}

	public void setBit(int row, int column) {
		matrix[row * width + (column >>> 6)] |= 1L << (column & 63);
 	}
	
	public void clearBit(int row, int column) {
		matrix[row * width + (column >>> 6)] &= ~(1L << (column & 63));
	}

	public boolean isBitSet(int row, int column) {
		return (matrix[row * width + (column >>> 6)] & 1L << (column & 63)) > 0;
	}

	public int bitCount(int startingRow, int endingRow, int startingColumn, int endingColumn) {
		long leftMask = ~((1L << (startingColumn & 63)) - 1);
		long rightMask = ((1L << ((endingColumn & 63) + 1)) - 1);
		int startingColumnSet = startingColumn >>> 6;
		int endingColumnSet = endingColumn >>> 6;
		int result = 0;
		if (startingColumnSet == endingColumnSet) {
			for (int i = startingRow; i <= endingRow; i++) {
				result += Long.bitCount(matrix[i * width + startingColumnSet] & leftMask & rightMask);
			}
		}
		else {
			for (int i = startingRow; i <= endingRow; i++) {
				result += Long.bitCount(matrix[i * width + startingColumnSet] & leftMask);
				for (int j = startingColumnSet + 1; j <= endingColumnSet - 1; j++) {
					result += Long.bitCount(matrix[i * width + j]);
				}
				result += Long.bitCount(matrix[i * width + endingColumnSet] & rightMask);
			}
		}
		return result;
	}
}