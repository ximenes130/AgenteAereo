/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteaereo.agentes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.Date;
import pojos.Voo;

/**
 *
 * @author Luiz Eduardo
 */
public class AgenteCompanhiaAerea extends Agent {

    private ArrayList voos;
    private String nomeCompanhia;

    protected void setup() {
        this.voos = new ArrayList();
        this.nomeCompanhia = getAID().getName();

        // Mensagem que sinaliza a existencia desse agente no mundo
        System.out.println("Novo agente rodando - " + getAID().getName() + ";");
        
         // Registrando o agente no catalogo de agentes
        DFAgentDescription dfd = new DFAgentDescription();                      // Instanciando descrição do agente
        dfd.setName(getAID());                                                  // Salvando AID do agente
        ServiceDescription serviceDescription = new ServiceDescription();       // Instanciando descrição do serviç
        serviceDescription.setType("companhia-aerea");                          // Salvando tipo de serviço
        serviceDescription.setName("JADE-voo-desponibilisador");                // Salvando nome do serviço
        dfd.addServices(serviceDescription);                                    // Adicionando serviço na descrição
        
        try {
            DFService.register(this, dfd);                                      // Registrando agente no catalogo
        }
        catch (FIPAException e) {
            e.printStackTrace();
        }
        

        for (int i = 0; i < Math.random() * 3 + 3; i++) {
            Voo v = new Voo();

            v.setAidCompanhia(getAID());
            v.setNumeroVoo(i);
            v.setQtdAcentos(3);
            v.setPreco((int)(Math.random() * 100) + 800);
            v.setAeroportoChegada("Aeroporto n: " + Math.random() * 1000);
            v.setAeroportoPartida("Aeroporto n: " + Math.random() * 1000);
            v.setDataSaida(new Date((int) (2020 + Math.random() * 5), 10, (int) Math.random() * 20, 12, 30));
            v.setDataChegada(new Date((int) (2020 + Math.random() * 5), 10, (int) Math.random() * 20, 12, 30));
            
            voos.add(v);
            System.out.println("Novo voo (" + v.getNumeroVoo() + " - R$" + v.getPreco() + ") de " + getAID().getName());
        }

        // Adicionando Comportamento que responde a quem procura pela lista de voos
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();     // Recebendo mensagem
                if (msg != null) {                      // Verificando se mensagem foi recebida
                    if (msg.getPerformative() == ACLMessage.REQUEST) {   
                        // Processando mensagem
                        
                        String titulo = msg.getContent();
                        ACLMessage retorno = msg.createReply();     // Criando resposta para a mensagem

                        System.out.println("Mensagem recebida: " + titulo);

                        if (voos != null) {                 // Verificando se existem voos
                            retorno.setPerformative(ACLMessage.INFORM);

                            try {
                                retorno.setContentObject(voos);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            // The requested book is NOT available for sale.
                            retorno.setPerformative(ACLMessage.REFUSE);
                            retorno.setContent("Não há voos disponiveis aqui");
                        }
                        myAgent.send(retorno);
                    }
                } else {
                    // Bloqueando comportamento caso não haja mensagem, economizando recursos.
                    // Qdo nova mensagem é transmitida todos os comportamentos são desbloqueados.
                    block();
                }
            }
        });
    }

    protected void takeDown() {
        
        // Removendo agente do catalogo
        try {
            DFService.deregister(this);
        }
        catch (FIPAException e) {
            e.printStackTrace();
        }

        // Sinalizando o encerramento do agente
        System.out.println("Encerrando o agente de Companhia Aérea " + getAID().getName() + ";");
    }
}
