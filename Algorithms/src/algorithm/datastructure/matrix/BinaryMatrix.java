package algorithm.datastructure.matrix;

public interface BinaryMatrix {
	int getRows();
	int getColumns();
	void setBit(int row, int column);
	void clearBit(int row, int column);
	boolean isBitSet(int row, int column);
	int bitCount(int startingRow, int endingRow, int startingColumn, int endingColumn);
}

abstract class AbstractBinaryMatrix implements BinaryMatrix {

	int rows;
	int columns;

	AbstractBinaryMatrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		initMatrix();
	}

	AbstractBinaryMatrix(String[] matrix, char significant) {
		this(matrix.length, matrix[0].length());
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				if (matrix[i].charAt(j) == significant) {
					setBit(i, j);
				}
	}

	abstract protected void initMatrix();

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
}