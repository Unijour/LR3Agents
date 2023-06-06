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

public class BehaviourForCommonAgent extends Behaviour {



    @Override
    public void action() {
        AgentCfg cfg = XmlHelper.unMarshalAny(AgentCfg.class, getAgent().getLocalName()+".xml");

        MessageTemplate request = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage commonReq = getAgent().receive(request);
        if (commonReq != null) {
            List<String> namesForNeighbours = new ArrayList<>();
            List<Double> weightsForNeighbours = new ArrayList<>();
            for (int i = 0; i < cfg.getNeighbours().size(); i++) {
                namesForNeighbours.add(cfg.getNeighbours().get(i).getName());
                weightsForNeighbours.add(cfg.getNeighbours().get(i).getWeight());
            }
            if (commonReq.getContent().contains("!i am first")) {
                int numberOfSender = 100;
                for (int i = 0; i < cfg.getNeighbours().size(); i++) {
                    if (cfg.getNeighbours().get(i).getName().equals(commonReq.getSender().getName().split("@")[0])) {
                        numberOfSender = i;
                    }
                }
                String[] splittedOne = commonReq.getContent().split("!");
                if (Double.parseDouble(splittedOne[2]) - cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[3]) >= 0) {
                    String commonFirstReq = splittedOne[0].replace("[", "");
                    commonFirstReq = commonFirstReq.replace("]", "");
                    ArrayList<String> namesList = new ArrayList<>();
                    namesList.add(commonFirstReq);
                    namesList.add(getAgent().getLocalName());
                    ACLMessage commonSend = new ACLMessage(ACLMessage.REQUEST);
                    double spentMoney = 0.0;
                    if (splittedOne[4].equals("Electrical")) {
                        spentMoney = cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[3]) * PriceData.getPriceElectricity();
                    } else if (splittedOne[4].equals("Fuel")) {
                        spentMoney = cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[3]) * PriceData.getPriceFuel();
                    }
                    commonSend.setContent(namesList.toString()+"!" + splittedOne[2] + "!" + splittedOne[3] + "!" + spentMoney + "!" + splittedOne[4]);
                    List<String> namesToSend;
                    namesToSend = namesForNeighbours;
                    try {
                        if (namesForNeighbours.size() > 1) {
                            for (int i = 0; i < namesToSend.size(); i++) {
                                for (int a = 0; a < namesList.size(); a++) {
                                    if (namesToSend.get(i).equals(namesList.get(a))) {
                                        namesToSend.remove(i);
                                    }
                                }
                            }
                            if (namesToSend.size() > 0) {
                                for (int i = 0; i < namesToSend.size(); i++) {
                                    commonSend.addReceiver(new AID(namesToSend.get(i), false));
                                }
                                getAgent().send(commonSend);
                            } else {
                                block();
                            }
                        } else {
                            block();
                        }
                    } catch (IndexOutOfBoundsException e) {
                        block();
                    }
                }
            } else {
                int numberOfSender = 100;
                for (int i = 0; i < cfg.getNeighbours().size(); i++) {
                    if (cfg.getNeighbours().get(i).getName().equals(commonReq.getSender().getName().split("@")[0])) {
                        numberOfSender = i;
                    }
                }
                String[] splittedOne = commonReq.getContent().split("!");
                if (Double.parseDouble(splittedOne[1]) - cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[2]) >= 0) {
                    String commonReqTosplit = commonReq.getContent().split("!")[0].replace("[", "");
                    commonReqTosplit = commonReqTosplit.replace("]", "");
                    String[] splitedReq = commonReqTosplit.split("\\,\\ ");
                    ArrayList<String> namesList = new ArrayList<>();
                    for (int b = 0; b < splitedReq.length; b++) {
                        namesList.add(splitedReq[b]);
                    }
                    namesList.add(getAgent().getLocalName());
                    ACLMessage commonSend = new ACLMessage(ACLMessage.REQUEST);
                    double spentMoney = 0.0;
                    if (splittedOne[4].equals("Electrical")) {
                        spentMoney = Double.parseDouble(splittedOne[3]) + cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[2]) * PriceData.getPriceElectricity();
                    } else if (splittedOne[4].equals("Fuel")) {
                        spentMoney = Double.parseDouble(splittedOne[3]) + cfg.getNeighbours().get(numberOfSender).getWeight() * Double.parseDouble(splittedOne[2]) * PriceData.getPriceFuel();
                    }
                    commonSend.setContent(namesList.toString() + "!" + splittedOne[1] + "!" + splittedOne[2] + "!" + spentMoney  + "!" + splittedOne[4]);
                    List<String> namesToSend;
                    namesToSend = namesForNeighbours;
                    try {
                        if (namesForNeighbours.size() > 1) {   //от тупиков
                            for (int i = 0; i < namesList.size(); i++) {
                                for (int a = 0; a < namesToSend.size(); a++) {
                                    if (namesToSend.get(a).equals(namesList.get(i))) {
                                        namesToSend.remove(namesList.get(i));
                                    }
                                }

                            }
                            if (namesToSend.size() > 0) {

                                //от зацикливаний
                                for (int i = 0; i < namesToSend.size(); i++) {
                                    commonSend.addReceiver(new AID(namesToSend.get(i), false));
                                }
                                getAgent().send(commonSend);
                            } else {
                                block();
                            }
                        } else {
                            block();
                        }
                    } catch (IndexOutOfBoundsException e) {
                        block();
                    }
                } {block();}
            }
        } else {block();}

        MessageTemplate info = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage inform = getAgent().receive(info);
        if (inform != null) {
            List<String> namesForNeighbours = new ArrayList<>();
            List<Double> weightsForNeighbours = new ArrayList<>();
            for (int i=0; i<cfg.getNeighbours().size(); i++) {
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
            int myPlaceInThisList=0;
            int sendersNameInMyNeighboursList=0;
            for (int i = 0; i<infoNamesList.size(); i++) {
                if (getAgent().getLocalName().equals(infoNamesList.get(i))) {
                    myPlaceInThisList = i;
                }
            }
            int sendersPlaceInThisList = myPlaceInThisList++;
            for (int i = 0; i<namesForNeighbours.size(); i++) {
                if (namesForNeighbours.get(i).equals(infoNamesList.get(sendersPlaceInThisList+1))) {
                    sendersNameInMyNeighboursList = i;
                }
            }
            double newSumWeight = sumWeight + weightsForNeighbours.get(sendersNameInMyNeighboursList);

            ACLMessage InfoSend = new ACLMessage(ACLMessage.INFORM);
            InfoSend.setContent(infoNamesList.toString()+"*"+newSumWeight + "*" + splitedInform[2] + "*" + splitedInform[3]);
            InfoSend.addReceiver(new AID(infoNamesList.get(myPlaceInThisList-2), false));
            getAgent().send(InfoSend);

        } else { block(); }

    }

    @Override
    public boolean done() {
        return false;
    }
}
