import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

/**
 *This class is not thread safe.
*/
public class MazeSolver {
	
	private Maze maze;
	private ArrayList<String> directions = new ArrayList<String>();
	private ArrayList<String> cpy = new ArrayList<String>();
	private String direction = "";
	private boolean foundGoal = false;

	public MazeSolver(Maze m) {

		this.maze = m;
	}

	public ArrayList<String> solve(MazePosition p, Collection<MazePosition> visited) {

		visited.add(p);

		if(maze.isGoal(p)) {

			foundGoal = true;
			
			if(!direction.equals("")) {
				
				directions.add(direction);
			}

			directions.add("Goal!");
			this.cpy = copyToCopy();
			directions.clear();
			this.direction = "";
			return cpy;
		}

		else if((hasDuplicateLocations(visited)) || (!maze.isClear(p))) {

			if(hasDuplicateLocations(visited)) {

				visited.remove(p);
			}
			return null;
		}

		else if((!direction.equals("")) && (!foundGoal) && (maze.isClear(p))) {

			directions.add(direction);
			//System.out.println(directions);
		}


		this.direction = "Up";
		
		if(!visited.contains(p.up())) {

			solve(p.up(), visited);
		}

		this.direction = "Left";

		if(!visited.contains(p.left())) {

			solve(p.left(), visited);
		}
		
		this.direction = "Down";
		
		if(!visited.contains(p.down())) {

			solve(p.down(), visited);
		}
		
		
		this.direction = "Right";
		
		if(!visited.contains(p.right())) {

			solve(p.right(), visited);
		}

		if(!foundGoal) {

			if(directions.size() > 0) {
				
				directions.remove(directions.size() - 1);
			}
		}

		return (foundGoal) ? cpy: null;
	}

	private ArrayList<String> copyToCopy() {

		ArrayList<String> cpy = new ArrayList<String>();

		for(String element: directions) {

			cpy.add(new String(element));
		}

		return cpy;
	}

	private boolean hasDuplicateLocations(Collection<MazePosition> visited) {

		ArrayList<MazePosition> tmp = new ArrayList<MazePosition>();

		for(MazePosition e: visited) {

			tmp.add(e);
		}

		for(int i = 0; i< tmp.size(); i++) {

			MazePosition mp = tmp.get(i);
			for(int j = i + 1; j < tmp.size(); j++) {

				if(mp.equals(tmp.get(j))) {

					return true;
				}
			}
		}

		return false;
	}
}