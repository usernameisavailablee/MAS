package A;

import jade.core.Agent;
import jade.core.AID;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

/**
 *
 * @author michael
 */
public class AMain extends Agent {
    public void setup() {
        System.out.println("Привет! агент " + getAID().getName() + " готов.");

        // Добавление циклического поведения
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    System.out.println(" – " + myAgent.getLocalName() + " received: " + msg.getContent());
                } else {
                    block(); // Блокируем поведение, пока в очереди сообщений нет сообщений
                }
            }
        });

        // Поиск других агентов
        AMSAgentDescription[] agents = null;
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(Long.valueOf(-1));
            agents = AMSService.search(this, new AMSAgentDescription(), c);
        } catch (Exception e) {
            System.out.println("Problem searching AMS: " + e);
            e.printStackTrace();
        }

        // Отправка сообщений другим агентам
        
        for (int i = 0; i < agents.length; i++) {
            AID agentID = agents[i].getName();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(agentID); // ID агента, которому отправляем сообщение
            msg.setLanguage("English"); // Язык
            msg.setContent("Ping"); // Содержимое сообщения
            send(msg); // Отправляем сообщение
        }
        
    }
}


