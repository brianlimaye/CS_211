public class Pixel<T extends Comparable<T>> implements Comparable<Pixel<T>> {

	private T pixelValue;

	public Pixel(T value) {

		pixelValue = value;
	}

	public void setColor(T newColor) {

		this.pixelValue = newColor;
	}

	public T getColor() {

		return pixelValue;
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(getColor());

		return sb.toString();
	}

	public int compareTo(Pixel<T> obj) {

		int cmp = this.pixelValue.compareTo(obj.getColor());

		if(cmp > 0) {

			return 1;
		}
		else if(cmp < 0) {

			return -1;
		}

		return 0;
	}
}