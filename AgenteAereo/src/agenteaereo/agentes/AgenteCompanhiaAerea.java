/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteaereo.agentes;

import jade.core.Agent;
import java.util.ArrayList;
import java.util.Date;
import pojos.Voo;

/**
 *
 * @author Luiz Eduardo
 */
public class AgenteCompanhiaAerea extends Agent{
    private ArrayList voos;
    private String nomeCompanhia;
    
    protected void setup() {
        voos = new ArrayList();
        this.nomeCompanhia = "Mathias - "+ getAID().getName();
        
        // Mensagem que sinaliza a existencia desse agente no mundo
        System.out.println("Novo agente rodando - "+getAID().getName()+";");
        
        for(int i=0 ; i<15 ; i++){
            Voo v = new Voo();
            
            v.setNumeroVoo((int)Math.random()%10000);
            v.setPreco((float)Math.random()%100 + 800);
            v.setAeroportoChegada("Aeroporto n:"+ Math.random()%1000);
            v.setAeroportoPartida("Aeroporto n:"+ Math.random()%1000);
            v.setDataSaida  (new Date((int)(2020+Math.random()%5), 10, (int)Math.random()%20,12,30));
            v.setDataChegada(new Date((int)(2020+Math.random()%5), 10, (int)Math.random()%20,12,30));
            
            voos.add(v);
        }
        
        //addBehaviour(new PublicaVoos());
        //addBehaviour(new VenderVoo());
    }

}
