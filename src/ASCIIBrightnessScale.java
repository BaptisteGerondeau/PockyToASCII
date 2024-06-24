public class ASCIIBrightnessScale{
    public char[] scale;

    private int coefficient;
    private int remainder;

    private boolean invertBrightnessValue;

    public ASCIIBrightnessScale(String scale, boolean invert){
        this.scale = scale.toCharArray();

        remainder = 255 % scale.length();
        coefficient = (255 - remainder) / (scale.length() - 1);
    }

    public char GetCharacterFromBrightness(int brightnessValue){
        int brightness = brightnessValue - 1;

        if (invertBrightnessValue){
            brightness = Math.abs(254 - brightnessValue);
        }

        int index = Math.max(brightness - remainder, 0) / coefficient;
        if (index == 65){
            System.out.println("" + remainder + " b " + coefficient);
        }
        return scale[index];
    }
}
