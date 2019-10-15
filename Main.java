public class Main {

    public static void main(String[] args) {
        ImageManipulator imgMan = new ImageManipulator("Test.jpg");
        imgMan.boxBlur("BoxBlur", 3);
        imgMan.gaussianBlur("GaussianBlur");
        imgMan.sharpen("Sharpen");
        imgMan.edges("EdgeDetection");
    }
}