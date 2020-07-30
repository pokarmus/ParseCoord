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

    File readOutputFile(String path){
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


    void convertFile(ArrayList<LocationJSON> locations, File file, String cuttedPath) throws IOException {

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
                }
            } else {
                skippedLines++;
                //System.out.println("Error: records should be an array: skipping.");
                jp.skipChildren();
            }
        }
        writeLocationToFile(locations, cuttedPath, skippedLines);
    }


    private void writeLocationToFile(ArrayList<LocationJSON> locations, String cuttedPath,  int skippedLines){
        Writer output;
        File file = new File(cuttedPath);
        if (file.delete())
            System.out.println("[LOG] Usunięto stary plik wyjściowy.");

        for (LocationJSON element: locations){
            String outputLine = element.getCoord().getLat() + "," + element.getCoord().getLng() + "," + element.getId() + "\n";
            try {

                output = new BufferedWriter(new FileWriter(cuttedPath, true));
                output.append(outputLine);
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[LOG] Wyeksportowano " + locations.size() + " pozycji.\n[LOG] Wykryto " + skippedLines + " niezgodnych węzłów.");
        }
}
