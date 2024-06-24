public abstract class Renderer implements ImageToASCIIRenderer {

    protected PixelMatrix pixelMatrix;
    protected String brightnessKey;
    protected ASCIIBrightnessScale brightnessScale;
    protected PixelMatrix.BRIGHTNESS_MODE brightnessMode;
    protected StringBuilder ASCIIMatrix;

    protected int[][] brightnessMatrix;

    public Renderer() {
        pixelMatrix = null;
        brightnessKey = null;
        brightnessScale = null;
        brightnessMode = null;
        ASCIIMatrix = null;

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
        ASCIIMatrix = new StringBuilder();

        preRenderHook();

        for (int x = 0; x < brightnessMatrix.length; x++) {
            for (int y = 0; y < brightnessMatrix[1].length; y++) {
                String ASCIIPixel = renderPixel(x, y);
                ASCIIMatrix.append(ASCIIPixel);
            }
            newLine(x);
        }

        postRenderHook();

        return ASCIIMatrix.toString();
    }

    protected void newLine(int x) {
        ASCIIMatrix.append('\n');
    }

    public String render(PixelMatrix pixelMatrix, String brightnessKey, boolean invert, PixelMatrix.BRIGHTNESS_MODE brightnessMode) {
        setImage(pixelMatrix);
        setBrightnessKey(brightnessKey, invert);
        setBrightnessMode(brightnessMode);
        return render();
    }

    protected void preRenderHook() {}

    protected void postRenderHook() {}

    protected abstract String renderPixel(int x, int y);
}
