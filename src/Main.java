public class Main {
    public static void main(String[] args) {
        System.out.print("Hello and welcome!\n");

        PixelMatrix pixels = new PixelMatrix("Image/pocky.jpg", 300, -1);

        System.out.println(pixels);
        System.out.println("<pre id=\"tiresult\" style=\"font-size: 9px; background-color: #000000; font-weight: bold; padding: 4px 5px; --fs: 9px;\">");

        String ASCIIBrightnessScale = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

        Renderer PockyToASCII = new HTMLRenderer();
        PockyToASCII.setImage(pixels);
        PockyToASCII.setBrightnessMode(PixelMatrix.BRIGHTNESS_MODE.AVERAGE);
        PockyToASCII.setBrightnessKey(ASCIIBrightnessScale, false);

        //printMatrix(brightnessAVGMatrix, scale, true, false, ANSIColoursMatrix);

        System.out.println();

        System.out.print(PockyToASCII.render());
        //printMatrix(brightnessLUMMatrix, scale, false, true, ANSIColoursMatrix, RawColoursMatrix);

        System.out.println();

        //printMatrix(brightnessLIGMatrix, scale, false, false, ANSIColoursMatrix);

    }
}