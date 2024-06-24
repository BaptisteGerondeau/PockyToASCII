public class ANSIRenderer extends Renderer {

    private int[][] ANSIChannel;

    public ANSIRenderer() {
        super();
        ANSIChannel = null;
    }
    @Override
    protected String renderPixel(int x, int y) {
        StringBuilder ANSIPixel = new StringBuilder();
        ANSIPixel.append(ConsoleColours.ANSIfromChannel(ANSIChannel[x][y]));
        ANSIPixel.append(this.brightnessScale.GetCharacterFromBrightness(this.brightnessMatrix[x][y]));
        return ANSIPixel.toString();
    }

    @Override
    protected void preRenderHook(StringBuilder ASCIIMatrix) {
        ANSIChannel = this.pixelMatrix.getANSIColourMatrix();
    }

    @Override
    protected void postRenderHook(StringBuilder ASCIIMatrix) {
        ASCIIMatrix.append(ConsoleColours.RESET);
    }

}
