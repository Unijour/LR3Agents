package PR.Behaviours.SubBehsForFSMStarter;

import PR.AgentCfg;
import PR.AgentData;
import PR.FinalCalc;
import PR.XmlHelper;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;


public class GettingMessagesForStarter2 extends Behaviour {

    AgentData agentData;

    public GettingMessagesForStarter2(Agent a, AgentData agentData) {
        super(a);
        this.agentData = agentData;
    }


    @Override
    public void action() {

        MessageTemplate request = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage commonReq = getAgent().receive(request);

    }

    @Override
    public boolean done() {
        return false;
    }
}
