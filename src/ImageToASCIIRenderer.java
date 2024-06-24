public interface ImageToASCIIRenderer {
    public void setImage(PixelMatrix pixelMatrix);
    public void setBrightnessKey(String brightnessKey, boolean invert);
    public void setBrightnessMode(PixelMatrix.BRIGHTNESS_MODE brightnessMode);
    public String render();
}