/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteaereo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Luiz Eduardo
 */
public class AgenteCliente extends Agent{
    protected void setup() {
        System.out.println("Novo agente rodando - " + getAID().getName() + ";");
        
        addBehaviour(new WakerBehaviour(this, 10000) {
            @Override
            protected void handleElapsedTimeout() {
                System.out.println("Requisitando voos;");
                
                // Criando objeto de mensagem
                ACLMessage mensagem = new ACLMessage(ACLMessage.CFP);
                /*
                for (int i = 0; i < sellerAgents.lenght; ++i) {
                    mensagem.addReceiver(sellerAgents[i]);
                }
                */
                
                mensagem.addReceiver(new AID("jonas", true));
                
                mensagem.setContent("requisito-voos");
                myAgent.send(mensagem);
            }
        });
    }
    
}
