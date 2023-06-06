package PR.Behaviours;

import PR.AgentCfg;
import PR.PriceData;
import PR.XmlHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

public class BehaviourForLastAgent extends Behaviour {
    @Override
    public void action() {
        AgentCfg cfg = XmlHelper.unMarshalAny(AgentCfg.class, getAgent().getLocalName()+".xml");

        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage lastReq = getAgent().receive(mt);
        if (lastReq != null) {
            int numberOfSender = 100;
            for (int i = 0; i < cfg.getNeighbours().size(); i++) {
                if (cfg.getNeighbours().get(i).getName().equals(lastReq.getSender().getName().split("@")[0])) {
                    numberOfSender = i;
                }
            }
            String[] splittedOne = lastReq.getContent().split("!");
            if (Double.parseDouble(splittedOne[1]) >= cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[2])) {
                String lastReqTosplit = lastReq.getContent().split("!")[0].replace("[", "");
                lastReqTosplit = lastReqTosplit.replace("]", "");
                String[] splitedReq = lastReqTosplit.split("\\,\\ ");
                ArrayList<String> namesList = new ArrayList<>();
                for (int b = 0; b < splitedReq.length; b++) {
                    namesList.add(splitedReq[b]);
                }
                namesList.add(getAgent().getLocalName());
                System.out.println("namesList for last to send inform is" + namesList);
                ACLMessage firstInfoSend = new ACLMessage(ACLMessage.INFORM);
                double spentMoney = 0.0;
                if (splittedOne[4].equals("Electrical")) {
                    spentMoney = Double.parseDouble(splittedOne[3]) + cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[2]) * PriceData.getPriceElectricity();
                } else if (splittedOne[4].equals("Fuel")) {
                    spentMoney = Double.parseDouble(splittedOne[3]) + cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[2]) * PriceData.getPriceFuel();
                }
                firstInfoSend.setContent(namesList.toString() + "*0" + "*" + spentMoney + "*" + splittedOne[4]);
                firstInfoSend.addReceiver(new AID(namesList.get(namesList.size() - 2), false));
                getAgent().send(firstInfoSend);
            } else {block();}
        }
        else { block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
