/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteaereo.agentes.comportamentos;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Luiz Eduardo
 */
public class BehaviourDispVoo extends Behaviour{

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();     // Recebendo mensagem
        if (msg != null) {                      // Verificando se mensagem foi recebida
        }
        else {
            // Bloqueando comportamento caso não haja mensagem, economizando recursos.
            // Qdo nova mensagem é transmitida todos os comportamentos são desbloqueados.
            block();                            
        } 
    }

    @Override
    public boolean done() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
