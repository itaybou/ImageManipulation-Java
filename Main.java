public class Main {

    public static void main(String[] args) {
        ImageManipulator imgMan = new ImageManipulator("Test.jpg");
        imgMan.boxBlur("BoxBlur", 9);
        imgMan.gaussianBlur("GaussianBlur");
    }
}