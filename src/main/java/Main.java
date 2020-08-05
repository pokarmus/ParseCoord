import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {



    public static void main (String args[]){
        String argsN[] = new String[2];

        if (args.length <1){
            System.out.println("[LOG] Plik wsadowy nie istnieje. Wychodzę.");
            System.exit(0);
        }
        else if (args.length == 1) {
            argsN[0] = args[0];
            argsN[1] = "Plik_wyjsciowy.csv";
        } else {
            argsN[0] = args[0];
            argsN[1] = args[1];
        }
            File file = new File(argsN[0]);
            if (file.exists()){
                startAction(argsN);
            }else{
                System.out.println("[LOG] Plik wsadowy nie istnieje. Wychodzę.");
                System.exit(0);
            }
    }

    private static void startAction(String[] argsN){
        LocalDataReader reader = new LocalDataReader();
        ArrayList <LocationJSON> list = new ArrayList<>();
        ArrayList <ConstPositionJSON> constPositions = new ArrayList<>();

        try {
            reader.convertFile(list, constPositions, reader.readOutputFile(argsN[0]), argsN[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
