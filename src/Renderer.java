public abstract class Renderer implements ImageToASCIIRenderer {

    protected PixelMatrix pixelMatrix;
    protected String brightnessKey;
    protected ASCIIBrightnessScale brightnessScale;
    protected PixelMatrix.BRIGHTNESS_MODE brightnessMode;

    protected int[][] brightnessMatrix;

    public Renderer() {
        pixelMatrix = null;
        brightnessKey = null;
        brightnessScale = null;
        brightnessMode = null;

        brightnessMatrix = null;
    }
    @Override
    public void setImage(PixelMatrix pixelMatrix) {
        this.pixelMatrix = pixelMatrix;
    }

    @Override
    public void setBrightnessKey(String brightnessKey, boolean invert) {
        this.brightnessKey = brightnessKey;
        this.brightnessScale = new ASCIIBrightnessScale(brightnessKey, invert);
    }

    @Override
    public void setBrightnessMode(PixelMatrix.BRIGHTNESS_MODE brightnessMode) {
        this.brightnessMode = brightnessMode;
    }

    @Override
    public String render() {
        if (pixelMatrix == null || brightnessMode == null || brightnessScale == null) {
            throw new IllegalArgumentException("Renderer not initialized, call setters or provide parameters");
        }

        brightnessMatrix = pixelMatrix.getBrightnessMatrix(brightnessMode);
        StringBuilder ASCIIMatrix = new StringBuilder();

        preRenderHook(ASCIIMatrix);

        for (int x = 0; x < brightnessMatrix.length; x++) {
            // Newline since we're going down a row
            ASCIIMatrix.append('\n');
            for (int y = 0; y < brightnessMatrix[1].length; y++) {
                String ASCIIPixel = renderPixel(x, y);
                ASCIIMatrix.append(ASCIIPixel);
            }
        }

        postRenderHook(ASCIIMatrix);

        return ASCIIMatrix.toString();
    }

    public String render(PixelMatrix pixelMatrix, String brightnessKey, boolean invert, PixelMatrix.BRIGHTNESS_MODE brightnessMode) {
        setImage(pixelMatrix);
        setBrightnessKey(brightnessKey, invert);
        setBrightnessMode(brightnessMode);
        return render();
    }

    protected void preRenderHook(StringBuilder ASCIIMatrix) {}

    protected void postRenderHook(StringBuilder ASCIIMatrix) {}

    protected abstract String renderPixel(int x, int y);
}
