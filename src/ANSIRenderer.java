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
    protected void preRenderHook() {
        ANSIChannel = this.pixelMatrix.getANSIColourMatrix();
    }

    @Override
    protected void postRenderHook() {
        ASCIIMatrix.append(ConsoleColours.RESET);
    }

}
