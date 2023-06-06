package PR.Behaviours;

import PR.AgentCfg;
import PR.PriceData;
import PR.XmlHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

public class BehaviourForCommonAgent2 extends Behaviour {

    @Override
    public void action() {
        AgentCfg cfg = XmlHelper.unMarshalAny(AgentCfg.class, getAgent().getLocalName() + ".xml");

        MessageTemplate info = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage inform = getAgent().receive(info);
        if (inform != null) {
            List<String> namesForNeighbours = new ArrayList<>();
            List<Double> weightsForNeighbours = new ArrayList<>();
            for (int i = 0; i < cfg.getNeighbours().size(); i++) {
                namesForNeighbours.add(cfg.getNeighbours().get(i).getName());
                weightsForNeighbours.add(cfg.getNeighbours().get(i).getWeight());
            }
            String informTosplit = inform.getContent().replace("[", "");
            informTosplit = informTosplit.replace("]", "");
            String[] splitedInform = informTosplit.split("\\*");
            double sumWeight = Double.parseDouble(splitedInform[1]);
            String[] splitedInf = splitedInform[0].split("\\,\\ ");
            ArrayList<String> infoNamesList = new ArrayList<>();
            for (int b = 0; b < splitedInf.length; b++) {
                infoNamesList.add(splitedInf[b]);
            }
            int myPlaceInThisList = 0;
            int sendersNameInMyNeighboursList = 0;
            for (int i = 0; i < infoNamesList.size(); i++) {
                if (getAgent().getLocalName().equals(infoNamesList.get(i))) {
                    myPlaceInThisList = i;
                }
            }
            int sendersPlaceInThisList = myPlaceInThisList++;
            for (int i = 0; i < namesForNeighbours.size(); i++) {
                if (namesForNeighbours.get(i).equals(infoNamesList.get(sendersPlaceInThisList + 1))) {
                    sendersNameInMyNeighboursList = i;
                }
            }
            double newSumWeight = sumWeight + weightsForNeighbours.get(sendersNameInMyNeighboursList);

            ACLMessage InfoSend = new ACLMessage(ACLMessage.INFORM);
            InfoSend.setContent(infoNamesList.toString() + "*" + newSumWeight + "*" + splitedInform[2] + "*" + splitedInform[3]);
            InfoSend.addReceiver(new AID(infoNamesList.get(myPlaceInThisList - 2), false));
            getAgent().send(InfoSend);


        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
