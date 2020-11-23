public class Point<E> {

	private E data;
	private Point<E> right;
	private Point<E> below;

	public Point() {

		this.data = null;
		this.right = null;
		this.below = null;
	}

	public void setValue(E newData) {

		if(newData != null) {

			this.data = newData;
		}
	}

	public E getValue() {

		return data;
	}

	public void setNextRight(Point<E> newRight) {

		this.right = newRight;
		
	}

	public Point<E> getNextRight() {

		return right;
	}

	public void setNextDown(Point<E> newDown) {

		this.below = newDown;
		
	}

	public Point<E> getNextDown() {

		return below;
	}
}