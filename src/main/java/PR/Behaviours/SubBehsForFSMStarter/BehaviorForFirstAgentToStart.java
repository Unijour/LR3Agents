package PR.Behaviours.SubBehsForFSMStarter;

import PR.AgentCfg;
import PR.XmlHelper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class BehaviorForFirstAgentToStart extends OneShotBehaviour {

    ArrayList<String> namesForNeighbours = new ArrayList<>();

    @Override
    public void action() {
        AgentCfg cfg = XmlHelper.unMarshalAny(AgentCfg.class, getAgent().getLocalName()+".xml");
        for (int i=0; i<cfg.getNeighbours().size(); i++) {
            namesForNeighbours.add(cfg.getNeighbours().get(i).getName());
        }
        ArrayList<String> namesList = new ArrayList<>();
        namesList.add(getAgent().getLocalName());
        ACLMessage commonSend = new ACLMessage(ACLMessage.REQUEST);
        commonSend.setContent(namesList.toString()+"!i am first"+"!"+cfg.getBattery());
        for (int i=0; i<namesForNeighbours.size(); i++) {
            commonSend.addReceiver(new AID(namesForNeighbours.get(i), false));
        }
        getAgent().send(commonSend);

    }
}
