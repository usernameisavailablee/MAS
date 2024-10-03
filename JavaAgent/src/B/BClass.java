package B;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

/**
 *
 * @author michael
 */
public class BClass extends Agent {
    protected void setup() {
        System.out.println("Привет! агент " + getAID().getName() + " готов.");

        // Добавляем циклическое поведение
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive(); // Получаем сообщение
                if (msg != null) {
                    // Если сообщение получено, выводим его содержимое
                    System.out.println(" – " + myAgent.getLocalName() + " received: " + msg.getContent());
                    
                    // Создаём ответ на сообщение
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM); // Устанавливаем тип сообщения
                    reply.setContent("Pong"); // Содержимое сообщения
                    send(reply); // Отправляем ответ
                }
                    block(); // Блокируем поведение, если сообщений нет
            }
        });
    }
}
