/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteaereo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import pojos.Voo;

/**
 *
 * @author Luiz Eduardo
 */
public class AgenteCliente extends Agent {

    AID[] listaCompanhias;
    Vector<Voo> voos;

    protected void setup() {
        System.out.println("Novo agente rodando - " + getAID().getName() + ";");
        voos = new Vector();

        // /////////////////////////////////////////////////////////////////////
        // Criando comportamento que solicita voos das companhias na rede     //
        // /////////////////////////////////////////////////////////////////////
        addBehaviour(new WakerBehaviour(this, 10000) {
            @Override
            protected void handleElapsedTimeout() {
                System.out.println("Requisitando voos;");

                // //////////////////////////////////////////
                // Atualizando lista de companhias aéreas  //
                // //////////////////////////////////////////
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
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }

                // ////////////////////////////////////////////////////
                // Enviando mensagem para as companhias encontradas  //
                // ////////////////////////////////////////////////////
                // Criando objeto de mensagem
                ACLMessage mensagem = new ACLMessage(ACLMessage.CFP);

                // Adicionando os remetentes
                for (int i = 0; i < listaCompanhias.length; ++i) {
                    mensagem.addReceiver(listaCompanhias[i]);
                }

                mensagem.setPerformative(ACLMessage.REQUEST);                   // Setando tipo de mensagem
                mensagem.setContent("requisito-voos");                          // Setando conteudo da mensagem
                voos.clear();                                                   // Preparando nova lista
                myAgent.send(mensagem);                                         // Enviando mensagem
            }
        });

        
        
        // ////////////////////////////////////////////////////////////////
        // Adicionando Comportamento que recebe resposta da companhia    //
        // ////////////////////////////////////////////////////////////////
        
        
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();     // Recebendo mensagem
                if (msg != null) {                      // Verificando se mensagem foi recebida
                    if (msg.getPerformative() == ACLMessage.INFORM) {

                        try {
                            Object conteudo = msg.getContentObject();

                            if (conteudo != null) {
                                // Processando mensagem
                                voos.addAll((List) conteudo);
                                
                                voos.sort((Voo v1, Voo v2) -> Float.compare(v1.getPreco(), v2.getPreco()));

                                for (Voo v : voos) {
                                    System.out.println(v.toString());
                                }
                                System.out.println("\n\n");
                            } else {
                                System.out.println("Erro ao receber mensagem;");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // Bloqueando comportamento caso não haja mensagem, economizando recursos.
                    // Qdo nova mensagem é transmitida todos os comportamentos são desbloqueados.
                    block();
                }
            }
        });

    }

}
