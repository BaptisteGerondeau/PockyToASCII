package com.pocky.pockytoascii;

public class RawASCIIRenderer extends Renderer {

    @Override
    protected String renderPixel(int x, int y) {
        return String.valueOf(this.brightnessScale.GetCharacterFromBrightness(this.brightnessMatrix[x][y]));
    }
}
