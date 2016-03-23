/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteaereo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Luiz Eduardo
 */
public class AgenteCliente extends Agent{
    
    AID[] listaCompanhias;
    
    protected void setup() {
        System.out.println("Novo agente rodando - " + getAID().getName() + ";");
        
        addBehaviour(new WakerBehaviour(this, 10000) {
            @Override
            protected void handleElapsedTimeout() {
                System.out.println("Requisitando voos;");
                
                
                 // Update the list of seller agents
                DFAgentDescription filtro = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("companhia-aerea");
                filtro.addServices(serviceDescription);
                try {
                    DFAgentDescription[] resultado = DFService.search(myAgent, filtro);
                    listaCompanhias = new AID[resultado.length];
                    
                    for (int i = 0; i < resultado.length; ++i) {
                        listaCompanhias[i] = resultado[i].getName();
                    }
                }
                catch (FIPAException fe) {
                    fe.printStackTrace();
                }
                
                // Criando objeto de mensagem
                ACLMessage mensagem = new ACLMessage(ACLMessage.CFP);
                
                for (int i = 0; i < listaCompanhias.length; ++i) {
                    mensagem.addReceiver(listaCompanhias[i]);
                    // System.out.println("sms("+i+") - "+ listaCompanhias[i].getName());
                }
                
                mensagem.setContent("requisito-voos");
                myAgent.send(mensagem);
            }
        });
    }
    
}
