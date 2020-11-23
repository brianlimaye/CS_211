import java.util.Iterator;

public class Mesh<E> implements Iterable<E> {
	
	protected Point<E> [][] points;
	private Point<E> head;
	private String direction;

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Mesh(int width, int height) {

		if((width > 0) && (height > 0)) {

			this.points = new Point[height][width];
			this.direction = "right";
			setPoints(width, height);
			head = points[0][0];
		}	
	}

	public void setDirection(String newDirection) {

		if(newDirection != null) {

			switch(newDirection) {

				case "down":
				case "down_right":
				case "right":
					this.direction = newDirection;
					break;
			}
		}
	}

	public String getDirection() {

		return direction;

	}

	public Point<E> getHead() {

		return head;
	}

	@Override
	public MeshIterator<E> iterator() {

		return new MeshIterator<E>(this);
	}

	private void setPoints(int width, int height) {

		for(int i = 0; i< height; i++) {

			for(int j = 0; j< width; j++) {
					
				if((i == 0) && (j == 0)) {

					points[i][j] = new Point<>();      
				}
				
				if(i + 1 < height) {

					points[i + 1][j] = new Point<>();
					points[i][j].setNextDown(points[i + 1][j]);
				}

				if(j + 1 < width) {

					if(i == 0) {
						
						points[i][j + 1] = new Point<>();
					}
					points[i][j].setNextRight(points[i][j + 1]);
				}
			}
		}
	}
}