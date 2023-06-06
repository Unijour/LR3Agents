package PR.Behaviours;

import PR.ChoiceData;
import PR.PriceData;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

public class BehaviourForOwner extends Behaviour {
    List<String> answers = new ArrayList<>();
    @Override
    public void action() {

        MessageTemplate request = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage commonReq = getAgent().receive(request);
        if (commonReq != null) {
            answers.add(commonReq.getContent());
            if (answers.size() == 2) {
                if (Double.parseDouble(answers.get(0).split("!")[1]) < Double.parseDouble(answers.get(1).split("!")[1])) {
                    System.out.println("i prefer " + answers.get(0).split("!")[2]);
                    ArrayList<Double> choicePrice = new ArrayList<>();
                    if (answers.get(0).split("!")[2].equals("Electrical")) {
                        choicePrice.add(1.0);
                    } else if (answers.get(0).split("!")[2].equals("Fuel")) {
                        choicePrice.add(0.0);
                    }
                    choicePrice.add(PriceData.getPriceElectricity());
                    ChoiceData.getChoicesList().get(getAgent().getLocalName()).add(choicePrice);
                } else {
                    System.out.println("i prefer " + answers.get(1).split("!")[2]);
                    ArrayList<Double> choicePrice = new ArrayList<>();
                    if (answers.get(1).split("!")[2].equals("Electrical")) {
                        choicePrice.add(1.0);
                    } else if (answers.get(1).split("!")[2].equals("Fuel")) {
                        choicePrice.add(0.0);
                    }
                    choicePrice.add(PriceData.getPriceElectricity());
                    ChoiceData.getChoicesList().get(getAgent().getLocalName()).add(choicePrice);
                }
                answers.clear();
                System.out.println(getAgent().getLocalName());
            } else {
                block();
            }
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
