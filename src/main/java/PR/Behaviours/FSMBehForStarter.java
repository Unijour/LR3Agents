package PR.Behaviours;

import PR.AgentData;
import PR.Behaviours.SubBehsForFSMStarter.BehaviorForFirstAgentToStart;
import PR.Behaviours.SubBehsForFSMStarter.LastSubBehForStarter;
import PR.Behaviours.SubBehsForFSMStarter.ParallelBehForStarter;
import PR.PriceData;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.WakerBehaviour;

public class FSMBehForStarter extends FSMBehaviour {
    private final String START = "start", PARBEH = "sendingprice", END = "success";
    AgentData agentData = new AgentData();

    public FSMBehForStarter(Agent a) {
        super(a);

        registerFirstState(new BehaviorForFirstAgentToStart(), START);
        registerState(new ParallelBehForStarter(getAgent(), agentData, 5000), PARBEH);
        registerLastState(new LastSubBehForStarter(getAgent(), agentData), END);

        registerDefaultTransition(START, PARBEH);
        registerDefaultTransition(PARBEH, END);
    }

    @Override
    public int onEnd() {
        if (PriceData.getPriceElectricity() < 20) {
            this.getAgent().addBehaviour(new WakerBehaviour(this.getAgent(), 1000) {
                @Override
                protected void onWake() {
                    this.getAgent().addBehaviour(new FSMBehForStarter(this.getAgent()));
                }
            });
        }
        return super.onEnd();
    }
}
