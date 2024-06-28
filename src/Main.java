import com.pocky.pockytoascii.*;
import org.apache.commons.cli.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    
    static String imagePath = null;
    static String outputPath = null;
    static int width = -1;
    static int height = -1;
    static FORMATS format;

    static enum FORMATS {
        ASCII,
        ANSI,
        HTML
    }
    
    public static void main(String[] args) {
        if(parseArgs(args)) {
            return;
        }

        PixelMatrix pixels = PixelMatrix.load(imagePath, width, height);

        System.out.println(pixels);

        String ASCIIBrightnessScale = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

        Renderer PockyToASCII = null;

        StringBuilder renderedImage = new StringBuilder();

        switch (format) {
            case ASCII:
                PockyToASCII = new RawASCIIRenderer();
                break;
            case ANSI:
                PockyToASCII = new ANSIRenderer();
                break;
            case HTML:
                PockyToASCII = new HTMLRenderer();
                renderedImage.append("<pre id=\"htmlrender\" style=\"background-color: #000000;" +
                        " font-weight: bold; padding: 4px 5px;letter-spacing: 5px; line-height: 13px\">");
                break;
            default:
                PockyToASCII = new RawASCIIRenderer();
        }

        PockyToASCII.setImage(pixels);
        PockyToASCII.setBrightnessMode(PixelMatrix.BRIGHTNESS_MODE.AVERAGE);
        PockyToASCII.setBrightnessKey(ASCIIBrightnessScale, false);

        renderedImage.append(PockyToASCII.render());
        if (format == FORMATS.HTML) {
            renderedImage.append("</pre>");
        }

        if (outputPath != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
                writer.write(renderedImage.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.print(renderedImage.toString());
        }

    }

    private static boolean parseArgs(String[] args) {
        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (cmd.hasOption("input")) {
            imagePath = String.valueOf(cmd.getOptionValue("input"));
        } else {
            imagePath = "Image/pocky.jpg";
        }

        if (cmd.hasOption("output")){
            outputPath = String.valueOf(cmd.getOptionValue("output"));
        }

        if (cmd.hasOption("width")){
            width = Integer.parseInt(String.valueOf(cmd.getOptionValue("width")));
        } else {
            width = 300;
        }

        if (cmd.hasOption("height")){
            height = Integer.parseInt(String.valueOf(cmd.getOptionValue("height")));
        }
        else {
            height = -1;
        }

        if (cmd.hasOption("version")){
            System.out.print("1.0.0");
            return true;
        }

        if (cmd.hasOption("format")){
            switch (String.valueOf(cmd.getOptionValue("format"))){
                case "ASCII":
                    format = FORMATS.ASCII;
                    break;
                case "ANSI":
                    format = FORMATS.ANSI;
                    break;
                case "HTML":
                    format = FORMATS.HTML;
                    break;
                default:
                    format = FORMATS.ASCII;
            }
        } else
        {
            format = FORMATS.ASCII;
        }

        if (cmd.hasOption("help")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar PockyToASCII.jar", options);
            return true;
        }
        return false;
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "This utility takes an image and outputs an ASCII Art version of it, in either raw ASCII, or with ANSI codes, or in HTML.");
        options.addOption("i", "input", true, "Path to the input file. Default is Image/pocky.jpg");
        options.addOption("o", "output", true, "Path to the output file");
        options.addOption("v", "version", false, "Version of the application");
        options.addOption("width", "width", true, "Desired width of the output image. Default is 300");
        options.addOption("height", "height", true, "Desired height of the output image. Default is -1");
        options.addOption("f", "format", true, "Format of the output image. Values are ASCII, ANSI or HTML. Default is ASCII.");
        return options;
    }
}