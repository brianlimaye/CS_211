import java.util.ArrayList;
public class Maze {
	
	private char[][] maze;

	public Maze(char[][] data) {

		if(data != null) {
			
			this.maze = data;
		}
	}

	public Maze(ArrayList<String> data) {

		if(data != null) {

			maze = new char[data.size()][];
			initMazeFromList(data);
		}
	}

	public boolean isClear(MazePosition p) {

		int currentRow = p.getRow();
		int currentCol = p.getCol();

		if((currentRow >= maze.length) || (currentRow < 0)) {

			return false;
		}

		if((currentCol >= maze[currentRow].length) || (currentCol < 0)) {

			return false;
		}

		return Character.isWhitespace(maze[currentRow][currentCol]);
	}

	public boolean isGoal(MazePosition p) {

		int currentRow = p.getRow();
		int currentCol = p.getCol();

		if((currentRow >= maze.length) || (currentRow < 0)) {

			return false;
		}

		if((currentCol >= maze[currentRow].length) || (currentCol < 0)) {

			return false;
		}

		return (maze[currentRow][currentCol] == 'G');
	} 

	private void initMazeFromList(ArrayList<String> data) {

		for(int row = 0; row < data.size(); row++) {

			String currentRow = data.get(row);

			maze[row] = currentRow.toCharArray();
		}
	}
}