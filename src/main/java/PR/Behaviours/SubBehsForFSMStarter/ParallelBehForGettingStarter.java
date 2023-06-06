package PR.Behaviours.SubBehsForFSMStarter;

import PR.AgentData;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;

public class ParallelBehForGettingStarter extends ParallelBehaviour {
 private Behaviour receiver1, receiver2;

    public ParallelBehForGettingStarter(Agent a, AgentData agentData) {
        super(a, WHEN_ANY);
        receiver1 = new GettingMessagesForStarter(getAgent(), agentData);
        receiver2 = new GettingMessagesForStarter2(getAgent(), agentData);

        addSubBehaviour(receiver1);
        addSubBehaviour(receiver2);
    }
}
