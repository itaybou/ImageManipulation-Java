import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImageManipulator {
    private BufferedImage image = null;
    private int width;
    private int height;

    public ImageManipulator(String filename) {
        try {
            this.image = ImageIO.read(ImageManipulator.class.getResource(filename));
        } catch (IOException e) {
            System.out.println("Could not read test image.\n" + e);
        } finally {
            this.width = this.image.getWidth();
            this.height = this.image.getHeight();
        }
    }

    public int[][] multiplyByKernel(int[][] pixels, double[][] kernel) {
        int[][] result = new int[height][width];
        for (int i = 0; i < result.length - kernel.length; i++) {
            for (int j = 0; j < result[0].length - kernel.length; j++) {
                int sumR = 0, sumG = 0, sumB = 0;
                for (int k1 = 0; k1 < kernel.length; k1++) {
                    for (int k2 = 0; k2 < kernel.length; k2++) {
                        Color pixel = new Color(pixels[i + k1][j + k2]);
                        int red = (int) ((double) (pixel.getRed()) * kernel[k1][k2]);
                        int green = (int) ((double) (pixel.getGreen()) * kernel[k1][k2]);
                        int blue = (int) ((double) (pixel.getBlue()) * kernel[k1][k2]);
                        sumR += red;
                        sumG += green;
                        sumB += blue;
                    }
                }
                result[i + 1][j + 1] = new Color(sumR > 255 ? 255 : sumR < 0 ? 0 : sumR,
                        sumG > 255 ? 255 : sumG < 0 ? 0 : sumG, sumB > 255 ? 255 : sumB < 0 ? 0 : sumB).getRGB();
            }
        }
        return result;
    }

    public void saveImage(String name, int[][] pixels) throws IOException {
        BufferedImage out = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                out.setRGB(x, y, pixels[x][y]);
            }
        }
        System.out.println();
        File img = new File(System.getProperty("user.dir") + "\\output\\" + name + ".jpg");
        if (ImageIO.write(out, "JPG", img)) {
            System.out.println(name + ".jpg Created successfully");
        }
    }

    public void boxBlur(String imgName, int factor) {
        double[][] kernel = new double[factor][factor];
        Arrays.stream(kernel).forEach(a -> Arrays.fill(a, (1f / (float) Math.pow(factor, 2))));
        try {
            saveImage(imgName, multiplyByKernel(convertToPixels(), kernel));
        } catch (IOException e) {
            System.out.println("Could not save output image\n" + e);
        }
    }

    public void gaussianBlur(String imgName) {
        double[][] kernel = { { 1f / 16f, 1f / 8f, 1f / 16f }, { 1f / 8f, 1f / 4f, 1f / 8f },
                { 1f / 16f, 1f / 8f, 1f / 16f } };
        try {
            saveImage(imgName, multiplyByKernel(convertToPixels(), kernel));
        } catch (IOException e) {
            System.out.println("Could not save output image\n" + e);
        }
    }

    public void sharpen(String imgName) {
        double[][] kernel = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };
        try {
            saveImage(imgName, multiplyByKernel(convertToPixels(), kernel));
        } catch (IOException e) {
            System.out.println("Could not save output image\n" + e);
        }
    }

    public void edges(String imgName) {
        double[][] kernel = { { -1, -1, -1 }, { -1, 8, -1 }, { -1, -1, -1 } };
        try {
            saveImage(imgName, multiplyByKernel(convertToPixels(), kernel));
        } catch (IOException e) {
            System.out.println("Could not save output image\n" + e);
        }
    }

    public int[][] convertToPixels() {

        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(row, col);
            }
        }
        return result;
    }
}