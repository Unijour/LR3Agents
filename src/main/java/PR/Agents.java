package PR;

import PR.Behaviours.*;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Agents extends Agent {
    private AgentData agentData = new AgentData();
    @Override
    protected void setup() {

        Long currentTime = System.currentTimeMillis();

        AgentCfg agentCfg = XmlHelper.unMarshalAny(AgentCfg.class, getLocalName()+".xml");
        if (agentCfg.getType().equals("helper")) {
            addBehaviour(new PriceChangeBehaviour(this, 10000));
            addBehaviour(new LastBehaviour(this, 120000));
        }
         if (agentCfg.getType().equals("owner")) {
             ChoiceData.getChoicesList().put(this.getLocalName(), new ArrayList<>());
             addBehaviour(new BehaviourForOwner());
         }
         if (agentCfg.getType().equals("refueling")) {
             addBehaviour(new ParallelBehForCommon(this));
         }
         if (agentCfg.getType().equals("Destination")) {
             addBehaviour(new BehaviourForLastAgent(currentTime));
         }
         if (agentCfg.getType().equals("Electrical") || agentCfg.getType().equals("Fuel")) {
             addBehaviour(new FSMBehForStarter(this));
         }
        super.setup();



         /* TimerTask task = new TimerTask() {
            public void run() {
                System.exit(1);
            }
        };

        Timer timer = new Timer("Timer");

        long delay = 6000L;
        timer.schedule(task, delay); */
    }
}
