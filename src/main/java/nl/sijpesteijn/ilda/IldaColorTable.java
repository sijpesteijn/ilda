package nl.sijpesteijn.ilda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Gijs
 */
public class IldaColorTable {

    private Map<Integer, ColorData> colors = new HashMap<Integer, ColorData>();

    public IldaColorTable() {
        InputStream inputStream = getClass().getResourceAsStream("/ildaColorTable.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                createColor(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createColor(String line) {
        String[] split = line.split(" ");
        ColorData colorData = new ColorData();
        int colorNr = Integer.valueOf(split[0].trim());
        int red = Integer.valueOf(split[1].trim());
        int green = Integer.valueOf(split[2].trim());
        int blue = Integer.valueOf(split[3].trim());
        colorData.setRed1(red);
        colorData.setGreen1(green);
        colorData.setBlue1(blue);
        colorData.setCode(colorNr);
        colors.put(colorNr, colorData);
    }

    public ColorData getColor(int colorNr) {
        return colors.get(colorNr);
    }

}
