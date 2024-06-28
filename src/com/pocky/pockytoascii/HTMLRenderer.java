package com.pocky.pockytoascii;

public class HTMLRenderer extends Renderer {

    private int[][][] RawColourMatrix;
    private StringBuilder currentColourChainPixels;
    private int[] currentColourChainValue;

    public HTMLRenderer() {
        currentColourChainValue = new int[3];
        RawColourMatrix = null;
    }
    @Override
    protected String renderPixel(int x, int y) {
        if (RawColourMatrix[x][y][0] == currentColourChainValue[0]
        && RawColourMatrix[x][y][1] == currentColourChainValue[1]
        && RawColourMatrix[x][y][2] == currentColourChainValue[2]) {
            currentColourChainPixels.append(this.brightnessScale.GetCharacterFromBrightness(this.brightnessMatrix[x][y]));
            return "";
        }
        else {
            String res = flushCurrentColourChain();
            resetColourChain(x, y);
            currentColourChainPixels.append(this.brightnessScale.GetCharacterFromBrightness(this.brightnessMatrix[x][y]));
            return res;
        }
    }

    @Override
    protected void newLine(int x){
        ASCIIMatrix.append(flushCurrentColourChain());
        ASCIIMatrix.append("<br>");
        resetColourChain(x, 0);
    }

    @Override
    protected void preRenderHook() {
        RawColourMatrix = pixelMatrix.getRgbMatrix();

        resetColourChain(0, 0);
    }

    @Override
    protected void postRenderHook() {
        ASCIIMatrix.append(flushCurrentColourChain());
    }

    private String flushCurrentColourChain() {
        StringBuilder flush = new StringBuilder();
        String s = "<b style=\"color:rgb(" + currentColourChainValue[0] + "," + currentColourChainValue[1] + "," + currentColourChainValue[2] + ")\">";
        flush.append(s);
        flush.append(currentColourChainPixels);
        flush.append("</b>");

        return flush.toString();
    }

    private void resetColourChain(int x, int y) {
        currentColourChainPixels = new StringBuilder();
        currentColourChainValue[0] = RawColourMatrix[x][y][0];
        currentColourChainValue[1] = RawColourMatrix[x][y][1];
        currentColourChainValue[2] = RawColourMatrix[x][y][2];
    }
}