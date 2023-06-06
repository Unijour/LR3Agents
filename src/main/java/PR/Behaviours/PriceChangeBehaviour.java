package PR.Behaviours;

import PR.PriceData;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;

public class PriceChangeBehaviour extends TickerBehaviour {
    public PriceChangeBehaviour(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
        this.getAgent().addBehaviour(new WakerBehaviour(this.getAgent(), 4000) {
            @Override
            protected void onWake() {
                PriceData.setPriceElectricity(PriceData.getPriceElectricity() + 1);
            }
        });
    }
}
