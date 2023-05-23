package PR.Behaviours;

import PR.AgentData;
import PR.Behaviours.SubBehsForFSMStarter.BehaviorForFirstAgentToStart;
import PR.Behaviours.SubBehsForFSMStarter.LastSubBehForStarter;
import PR.Behaviours.SubBehsForFSMStarter.ParallelBehForStarter;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

public class FSMBehForStarter extends FSMBehaviour {
    private final String START = "start", PARBEH = "sendingprice", END = "success";
    AgentData agentData;

    public FSMBehForStarter(Agent a, AgentData agentData) {
        super(a);
        this.agentData = agentData;

        registerFirstState(new BehaviorForFirstAgentToStart(), START);
        registerState(new ParallelBehForStarter(getAgent(), agentData, 10000), PARBEH);
        registerLastState(new LastSubBehForStarter(getAgent(), agentData), END);

        registerDefaultTransition(START, PARBEH);
        registerDefaultTransition(PARBEH, END);
    }
}
