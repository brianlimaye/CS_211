import java.util.Iterator;

public class MeshIterator<E> implements Iterator<E> {

	private Mesh<E> mesh;
	private Point<E> currentNode;

	public MeshIterator(Mesh<E> m) {

		if(m != null) {

			this.mesh = m;
			currentNode = mesh.getHead();
		}
	}

	@Override
	public boolean hasNext() {

		return (currentNode != null);
	}

	@Override
	public E next() {

		E currentData = currentNode.getValue();
		String currentDirection = mesh.getDirection();

		switch(currentDirection) {

			case "right":
				currentNode = currentNode.getNextRight();
				break;
			case "down":
				currentNode = currentNode.getNextDown();
				break;
			case "down_right":
				currentNode = currentNode.getNextDown();

				if(currentNode != null) {

					currentNode = currentNode.getNextRight();
				}
				break;
		}
		return currentData;
	}

	@Override
	public void remove() {

		throw new UnsupportedOperationException();
	}
}