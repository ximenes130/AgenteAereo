/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteaereo.agentes.comportamentos;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.List;
import jdk.nashorn.internal.runtime.JSONFunctions;
import pojos.Voo;

/**
 *
 * @author Luiz Eduardo
 */
public class BehaviourDispVoo extends CyclicBehaviour{
    private List<Voo> voos;

    public BehaviourDispVoo(List voos) {
        this.voos = voos;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();     // Recebendo mensagem
        if (msg != null) {                      // Verificando se mensagem foi recebida
             // Processando mensagem
            
            String titulo = msg.getContent();
            ACLMessage retorno = msg.createReply();
            
            if (voos != null) {                 // Verificando se existem voos
            retorno.setPerformative(ACLMessage.PROPOSE);
            retorno.setContentObject(voos);
            }
            else {
            // The requested book is NOT available for sale.
            retorno.setPerformative(ACLMessage.REFUSE);
            retorno.setContent("Não há voos disponiveis aqui");
            }
            myAgent.send(retorno);
        }
        else {
            // Bloqueando comportamento caso não haja mensagem, economizando recursos.
            // Qdo nova mensagem é transmitida todos os comportamentos são desbloqueados.
            block();                            
        } 
    }
    
}
