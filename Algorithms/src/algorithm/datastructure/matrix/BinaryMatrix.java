package algorithm.datastructure.matrix;

// - TODO test
public class BinaryMatrix {

	private static int BLOCK_ADDRESS_BITS = 6;
	private static int BLOCK_SIZE = 1 << BLOCK_ADDRESS_BITS;
	private static int BLOCK_MASK = BLOCK_SIZE - 1;
	
	private int rows, cols;
	private int widthAddressBits;
	private long[] matrix;
	
	public BinaryMatrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		initMatrix();
	}

	public BinaryMatrix(String[] matrix, char significant) {
		this(matrix.length, matrix[0].length());
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				if (matrix[i].charAt(j) == significant)
					setBit(i, j);
	}

	protected void initMatrix() {
		widthAddressBits = 0;
		while (BLOCK_SIZE << widthAddressBits < cols) widthAddressBits++;
		matrix = new long[rows << widthAddressBits];
	}

	public void setBit(int row, int col) {
		matrix[(row << widthAddressBits) + (col >>> BLOCK_ADDRESS_BITS)] |= 1L << (col & BLOCK_MASK);
 	}
	
	public void clearBit(int row, int col) {
		matrix[(row << widthAddressBits) + (col >>> BLOCK_ADDRESS_BITS)] &= ~(1L << (col & BLOCK_MASK));
	}

	public boolean isBitSet(int row, int col) {
		return (matrix[(row << widthAddressBits) + (col >>> BLOCK_ADDRESS_BITS)] & (1L << (col & BLOCK_MASK))) != 0;
	}

	public int bitCount(int firstRow, int lastRow, int firstColumn, int lastColumn) {
		long leftMask = ~((1L << (firstColumn & BLOCK_MASK)) - 1);
		long rightMask = (1L << ((lastColumn+1) & BLOCK_MASK)) - 1;
		int startingColumnSet = firstColumn >>> BLOCK_ADDRESS_BITS;
		int endingColumnSet = lastColumn >>> BLOCK_ADDRESS_BITS;
		int result = 0;
		if (startingColumnSet == endingColumnSet) {
			long mask = leftMask & rightMask;
			for (int i = firstRow; i <= lastRow; i++) {
				result += Long.bitCount(matrix[(i << widthAddressBits) + startingColumnSet] & mask);
			}
			return result;
		}
		for (int i = firstRow; i <= lastRow; i++) {
			result += Long.bitCount(matrix[(i << widthAddressBits) + startingColumnSet] & leftMask);
			for (int j = startingColumnSet + 1; j < endingColumnSet; j++) {
				result += Long.bitCount(matrix[(i << widthAddressBits) + j]);
			}
			result += Long.bitCount(matrix[(i << widthAddressBits) + endingColumnSet] & rightMask);
		}

		return result;
	}
}
