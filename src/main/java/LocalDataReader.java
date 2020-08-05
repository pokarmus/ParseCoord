import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

class LocalDataReader {
    //private final String outputFilePATH = "src\\main\\resources\\komiwojazer_output.csv";
    //private final String cuttedFilePATH = "src\\main\\resources\\komiwojazer_output_cuttted.csv";

    File readOutputFile(String path) {
        File file = new File(path);
        FileReader fileReader;// = null;
        ArrayList<String> lines = new ArrayList<>();
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    void convertFile(ArrayList<LocationJSON> locations, ArrayList<ConstPositionJSON> constPoints, File file, String cuttedPath) throws IOException {

        int skippedLines = 0;
        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = f.createParser(file);
        JsonToken current;
        current = jp.nextToken();
        if (current != JsonToken.START_OBJECT) {
            System.out.println("[LOG] Brak węzła root w pliku wejściowym. Wychodzę.");
            return;
        }
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            current = jp.nextToken();
            if (current == JsonToken.START_ARRAY) {
                // For each of the records in the array
                while (jp.nextToken() != JsonToken.END_ARRAY) {
                    JsonNode node = jp.readValueAsTree();
                    ObjectMapper mapper = new ObjectMapper();
                    locations.add(mapper.readValue(node.toString(), LocationJSON.class));
                    //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(node.toString(), LocationJSON.class)));
                }
            } else {
                if (skippedLines++ < 2) {
                    JsonNode node = jp.readValueAsTree();
                    ObjectMapper mapper = new ObjectMapper();
                    constPoints.add(mapper.readValue(node.toString(), ConstPositionJSON.class));
                    //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(node.toString(), ConstPositionJSON.class)));
                }
                //System.out.println("Error: records should be an array: skipping.");
                jp.skipChildren();
            }
        }
        writeLocationToFile(locations, constPoints, cuttedPath);
    }


    private void writeLocationToFile(ArrayList<LocationJSON> locations, ArrayList<ConstPositionJSON> constPoints, String cuttedPath) {
        try {
            File file = new File(cuttedPath);

            if (file.delete())
                System.out.println("[LOG] Usunięto stary plik wyjściowy.");

            Writer output;
            output = new BufferedWriter(new FileWriter(cuttedPath, true));

            String write = constPoints.get(0).getLocation().getCoord().getLat() + "," +
                           constPoints.get(0).getLocation().getCoord().getLng() + "," + constPoints.get(0).getLocation().getId() + "\n";
            output.append(write);

            for (LocationJSON element : locations) {
                String outputLine = element.getCoord().getLat() + "," + element.getCoord().getLng() + "," + element.getId() + "\n";
                output.append(outputLine);
            }
            write = constPoints.get(1).getLocation().getCoord().getLat() + "," +
                    constPoints.get(1).getLocation().getCoord().getLng() + "," + constPoints.get(1).getLocation().getId() + "\n";
            output.append(write);
            output.close();
            System.out.println("[LOG] Wyeksportowano " + locations.size() + " pozycji.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
