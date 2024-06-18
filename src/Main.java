public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.print("Hello and welcome!\n");

        PixelMatrix pixels = new PixelMatrix("Image/pocky.jpg", 100, -1);

        System.out.println(pixels);
        System.out.println("<pre id=\"tiresult\" style=\"font-size: 9px; background-color: #000000; font-weight: bold; padding: 4px 5px; --fs: 9px;\">");

        String ASCIIBrightnessScale = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
        ASCIIBrightnessScale scale = new ASCIIBrightnessScale(ASCIIBrightnessScale);

        int[][] brightnessAVGMatrix = pixels.getBrightnessMatrix(PixelMatrix.BRIGHTNESS_MODE.AVERAGE);
        int[][] brightnessLUMMatrix = pixels.getBrightnessMatrix(PixelMatrix.BRIGHTNESS_MODE.LUMINOSITY);
        int[][] brightnessLIGMatrix = pixels.getBrightnessMatrix(PixelMatrix.BRIGHTNESS_MODE.LIGHTNESS);
        int[][] ANSIColoursMatrix = pixels.getANSIColourMatrix();
        int[][][] RawColoursMatrix = pixels.getRgbMatrix();

        //printMatrix(brightnessAVGMatrix, scale, true, false, ANSIColoursMatrix);

        System.out.println();

        printMatrix(brightnessLUMMatrix, scale, false, true, ANSIColoursMatrix, RawColoursMatrix);

        System.out.println();

        //printMatrix(brightnessLIGMatrix, scale, false, false, ANSIColoursMatrix);

        System.out.println();
        System.out.println("</pre>");
    }

    public static void printMatrix(int[][] Matrix, ASCIIBrightnessScale scale, boolean invert, boolean colour, int[][] ColourMatrix, int[][][] RawColoursMatrix) {
        int blobr = RawColoursMatrix[0][0][0], r = blobr;
        int blobg = RawColoursMatrix[0][0][1], g = blobg;
        int blobb = RawColoursMatrix[0][0][2], b = blobb;
        char charbright;
        StringBuilder strbright = new StringBuilder(new String());
        StringBuilder strcolor = new StringBuilder(new String());
        for (int x = 0; x < Matrix.length; x++) {
            System.out.println();
            strcolor = new StringBuilder(new String());
            for (int y = 0; y < Matrix[1].length; y++) {
                strbright = new StringBuilder(new String());
                charbright = scale.GetCharacterFromBrightness(Matrix[x][y], invert);
                if (colour) {
                    r = RawColoursMatrix[x][y][0];
                    g = RawColoursMatrix[x][y][1];
                    b = RawColoursMatrix[x][y][2];

                    if (r == blobr && g == blobg && b == blobb) {
                        strcolor.append(charbright);
                    } else {
                        System.out.print(HTMLColour(blobr, blobg, blobb));
                        System.out.print(strcolor);
                        System.out.print("</b>");

                        blobr = r;
                        blobg = g;
                        blobb = b;
                        strcolor = new StringBuilder(new String());
                        strcolor.append(charbright);
                    }
                } else {
                    strbright.append(charbright);
                    System.out.print(strbright);
                }
            }
            // Flush current color blob as we are changing lines
            System.out.print(HTMLColour(blobr, blobg, blobb));
            System.out.print(strcolor);
            System.out.print("</b>");
            strcolor = new StringBuilder(new String());
        }
    }

    public static String HTMLColour(int r, int g, int b)
    {
        String s = "<b style=\"color:rgb(" + r + "," + g + "," + b + ")\">";
        return s;
    }
}