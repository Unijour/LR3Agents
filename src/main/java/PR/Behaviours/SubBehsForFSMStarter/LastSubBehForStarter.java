package PR.Behaviours.SubBehsForFSMStarter;

import PR.AgentCfg;
import PR.AgentData;
import PR.XmlHelper;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class LastSubBehForStarter extends OneShotBehaviour {
    AgentData agentData;

    public LastSubBehForStarter(Agent a, AgentData agentData) {
        super(a);
        this.agentData = agentData;
    }

    @Override
    public void action() {
        AgentCfg cfg = XmlHelper.unMarshalAny(AgentCfg.class, getAgent().getLocalName()+".xml");
        int mini = 0;
        double finalWeight = agentData.getFinalCalcList().get(mini).getFinalWeight();
        for (int i = 1; i<agentData.getFinalCalcList().size(); i++) {
            if (finalWeight>=agentData.getFinalCalcList().get(i).getFinalWeight()) {
                finalWeight = agentData.getFinalCalcList().get(i).getFinalWeight();
                mini = i;
            }
        }
        System.out.println(agentData.getFinalCalcList().get(mini).getWay().split("\\*")[0]+" Sum of weights is "+agentData.getFinalCalcList().get(mini).getFinalWeight() + " total price is " +
                agentData.getFinalCalcList().get(mini).getWay().split("\\*")[2]);

        ACLMessage answer = new ACLMessage(ACLMessage.REQUEST);
        answer.setContent(agentData.getFinalCalcList().get(mini).getWay().split("\\*")[0]  + "!" +
                agentData.getFinalCalcList().get(mini).getWay().split("\\*")[2] + "!" + agentData.getFinalCalcList().get(mini).getType());
        answer.addReceiver(new AID(cfg.getOwner(), false));
        getAgent().send(answer);

    }
}
