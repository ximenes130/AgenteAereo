/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteaereo.agentes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        this.nomeCompanhia = "Mathias - " + getAID().getName();

        // Mensagem que sinaliza a existencia desse agente no mundo
        System.out.println("Novo agente rodando - " + getAID().getName() + ";");

        for (int i = 0; i < Math.random()%3+3; i++) {
            Voo v = new Voo();

            v.setNumeroVoo(i);
            v.setPreco( ((int)Math.random() % 100f) + 800);
            v.setAeroportoChegada("Aeroporto n: " + Math.random() % 1000);
            v.setAeroportoPartida("Aeroporto n: " + Math.random() % 1000);
            v.setDataSaida(new Date((int) (2020 + Math.random() % 5), 10, (int) Math.random() % 20, 12, 30));
            v.setDataChegada(new Date((int) (2020 + Math.random() % 5), 10, (int) Math.random() % 20, 12, 30));
            
            voos.add(v);
            System.out.println("Novo voo ("+v.getNumeroVoo()+" - R$"+v.getPreco()+") de "+getAID().getName());
        }

        // Adicionando Comportamento que responde a quem procura pela lista de voos
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();     // Recebendo mensagem
                if (msg != null) {                      // Verificando se mensagem foi recebida
                    // Processando mensagem

                    String titulo = msg.getContent();
                    ACLMessage retorno = msg.createReply();     // Criando resposta para a mensagem
                    
                    System.out.println("Mensagem recebida: "+ titulo);

                    if (voos != null) {                 // Verificando se existem voos
                        retorno.setPerformative(ACLMessage.PROPOSE);
                        
                        try{
                            retorno.setContentObject(voos);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        // The requested book is NOT available for sale.
                        retorno.setPerformative(ACLMessage.REFUSE);
                        retorno.setContent("Não há voos disponiveis aqui");
                    }
                    myAgent.send(retorno);
                } else {
                    // Bloqueando comportamento caso não haja mensagem, economizando recursos.
                    // Qdo nova mensagem é transmitida todos os comportamentos são desbloqueados.
                    block();
                }
            }
        });
        //addBehaviour(new VenderVoo());
    }

    protected void takeDown() {
        System.out.println("Encerrando o agente de Companhia Aérea " + getAID().getName() + ";");
    }
}
