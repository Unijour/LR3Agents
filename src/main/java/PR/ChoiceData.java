package PR;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ChoiceData {

    private static HashMap<String, ArrayList<ArrayList<Double>>> choicesList = new HashMap<>();

    public static HashMap<String, ArrayList<ArrayList<Double>>> getChoicesList() {
        return choicesList;
    }

    public static void setChoicesList(HashMap<String, ArrayList<ArrayList<Double>>> choicesList) {
        ChoiceData.choicesList = choicesList;
    }
}
