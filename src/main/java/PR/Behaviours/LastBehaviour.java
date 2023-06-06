package PR.Behaviours;

import PR.ChoiceData;
import PR.PriceData;
import PR.TempField2D;
import PR.TempField3D;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

import java.util.ArrayList;
import java.util.Map;

public class LastBehaviour extends WakerBehaviour {
    public LastBehaviour(Agent a, long timeout) {
        super(a, timeout);
    }

    @Override
    protected void onWake() {
        System.out.println(ChoiceData.getChoicesList());
        double[][] result = new double[11][ChoiceData.getChoicesList().size()];
        System.out.println(result);
        int i = 0;
        for (Map.Entry<String, ArrayList<ArrayList<Double>>> entry: ChoiceData.getChoicesList().entrySet()) {
            for (int j = 0; j < entry.getValue().size(); j++) {
                result[j][i] = entry.getValue().get(j).get(0);
            }
            i = i+1;
        }
        TempField2D.showField(result);
    }
}
