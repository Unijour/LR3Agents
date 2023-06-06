package PR.Behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;

public class ParallelBehForCommon extends ParallelBehaviour {
 private Behaviour receiver1, receiver2;

    public ParallelBehForCommon(Agent a) {
        super(a, WHEN_ANY);
        receiver1 = new BehaviourForCommonAgent();
        receiver2 = new BehaviourForCommonAgent2();

        addSubBehaviour(receiver1);
        addSubBehaviour(receiver2);
    }
}
