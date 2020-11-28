public class MazePosition {
	
	private int row;
	private int col;

	public MazePosition(int r, int c) {

		this.row = r;
		this.col = c;
	}

	public int getRow() {

		return row;
	}

	public int getCol() {

		return col;
	}

	public MazePosition left() {

		return new MazePosition(row, col - 1);
	}

	public MazePosition right() {

		return new MazePosition(row, col + 1);
	}

	public MazePosition up() {

		return new MazePosition(row - 1, col);
	}

	public MazePosition down() {

		return new MazePosition(row + 1, col);
	}

	@Override
	public boolean equals(Object o) {

		if(o instanceof MazePosition) {

			MazePosition obj = (MazePosition) o;
			return ((this.row == obj.row) && (this.col == obj.col));
		}

		return false;
	}

	@Override
	public String toString() {

		return "MazePosition (" + row + ", " + col + ")";
	}

	@Override
	public int hashCode() {

		return this.toString().hashCode();
	}

}