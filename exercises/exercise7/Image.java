import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.*;
import java.util.List;
import java.util.Collections;

public class Image implements Filter {

	private int height;
	private int width;
	protected ArrayList<ArrayList<Pixel<?>>> pixels;

	public Image(int height, int width, Pixel<?> pixel) {

		if((height <= 0) || (width <= 0)) {

			throw new IndexOutOfBoundsException("invalid height and/or width");
		}

		this.height = height;
		this.width = width;

		pixels = new ArrayList<ArrayList<Pixel<?>>>();
		fillPixels(pixel);
	}

	private Image(int height, int width, ArrayList<ArrayList<Pixel<?>>> pixels) {

		this.height = height;
		this.width = width;
		this.pixels = pixels;
	}

	@Override
	public Image scale(int scalingFactor) {

		if(scalingFactor == 1) {

			return this;
		}

		int newHeight = height;
		int newWidth = width;
		int pixelIncrementer = 1;
		int pixelMultiplier = 1;
		int currPos = 0;
		ArrayList<ArrayList<Pixel<?>>> newPixels;

		boolean isPositive = (scalingFactor > 0) ? true : false;

		if(isPositive) {

			pixelMultiplier = Math.abs(scalingFactor);
			newHeight *= Math.abs(scalingFactor);
			newWidth *= Math.abs(scalingFactor);
		}
		else {

			
			pixelIncrementer = Math.abs(scalingFactor);
			newHeight /= Math.abs(scalingFactor);
			newWidth /= Math.abs(scalingFactor);
		}

		newPixels = new ArrayList<ArrayList<Pixel<?>>>();

		loop:
		for(int row = 0; row < pixels.size(); row += pixelIncrementer) {

			for(int i = 0; i< pixelMultiplier; i++) {

				newPixels.add(calculateScaledPixels(pixelIncrementer, pixelMultiplier, pixels.get(row)));
			}
		}

		return new Image(newHeight, newWidth, newPixels);
	}

	private ArrayList<Pixel<?>> calculateScaledPixels(int pixelIncrementer, int pixelMultiplier, ArrayList<Pixel<?>> pixels) {

		ArrayList<Pixel<?>> scaledPixels = new ArrayList<Pixel<?>>();

		for(int i = 0; i< pixels.size(); i += pixelIncrementer) {

			for(int j = 0; j < pixelMultiplier; j++) {

				scaledPixels.add(pixels.get(i));
			}
		}
		return scaledPixels;
	}

	
	@Override
	public Image median(int sideLength) {
	
		if(sideLength > width) {

			return this;
		}

		Image newImage = new Image(height, width, new Pixel<Integer>(0));

		List<Pixel<?>> sortedPixels;

		sortedPixels = new ArrayList<>();

		for(int medianRow = 0; medianRow < height; medianRow++) {

			for(int medianCol = 0; medianCol < width; medianCol++) {

				int currentRow = medianRow - (sideLength / 2);
				int currentCol = medianCol - (sideLength / 2);

				while((currentCol <= medianCol + (sideLength / 2)) && (currentRow <= medianRow + (sideLength / 2))) {

					try {

						sortedPixels.add(pixels.get(currentRow).get(currentCol));
					}
					catch(Throwable t) {
				

					}
					finally {

						if(currentCol < medianCol + (sideLength / 2)) {

							currentCol++;
						}
						else {

							currentCol = medianCol - (sideLength / 2);
							currentRow++;
						}
					}
				}

				Collections.sort(sortedPixels, null);
				newImage.setPixel(medianRow, medianCol, sortedPixels.get(sortedPixels.size() / 2));
				sortedPixels.clear();
			}
		}
		return newImage;
	}

	private void fillPixels(Pixel<?> pixel) {

		if(pixel == null) {

			return;
		}

		for(int j = 0; j< height; j++) {

			pixels.add(new ArrayList<Pixel<?>>());
			
			for(int i=0; i< width; i++) {
				
				pixels.get(j).add(pixel);
			}
		}
	}

	public void print() {

		for(int row = 0; row < pixels.size(); row++) {

			ArrayList<Pixel<?>> currentRow = pixels.get(row);

			if(currentRow == null) {

				return;
			}
			for(int col = 0; col < currentRow.size(); col++) {

				Pixel<?> currentPixel = currentRow.get(col);
				if(currentPixel instanceof Pixel) {

					System.out.print(currentPixel);

					if(col != currentRow.size() - 1) {

						System.out.print(" ");
					}
				}
			}
			if(row != height - 1) {

				System.out.println();
			}
		}
	}

	public boolean setPixel(int row, int col, Pixel<?> pixel) {

		if((row < 0) || (row >= height) || (col < 0) || (col >= width)) {

			return false;
		}

		ArrayList<Pixel<?>> currentRow = pixels.get(row);
		currentRow.set(col, pixel);

		return true;
	}

	public ArrayList<ArrayList<Pixel<?>>> getPixels() {

		return pixels;
	}
}