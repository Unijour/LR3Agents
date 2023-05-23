package PR;

import java.util.ArrayList;

public class AgentData {
    public ArrayList<FinalCalc> getFinalCalcList() {
        return finalCalcList;
    }

    public void setFinalCalcList(ArrayList<FinalCalc> finalCalcList) {
        this.finalCalcList = finalCalcList;
    }

    private ArrayList<FinalCalc> finalCalcList = new ArrayList<>();
}
