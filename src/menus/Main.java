package menus;


import java.io.File;

import java.io.IOException;
import java.util.Scanner;

import aluguer.Kartodromo;
import aluguer.SistemaCronometragem;
import piloto.Convidado;



public class Main {

	/**
	 * Arranque do sistema
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// criar o Kartódromo e respetivo sistema de cronometragem
		Kartodromo estKarts = new Kartodromo( new SistemaCronometragem( ) );
		
		// carregar a informação inicial
		setupKartodromo( estKarts );
		
		// criar e mostrar a janela que trata dos aluguerSes
		JanelaAlugueres janelaAluguer = new JanelaAlugueres( estKarts );
		janelaAluguer.setLocation(20, 50);
		janelaAluguer.setVisible( true );
		estKarts.addCronometragemListener( janelaAluguer );
		
		// criar e mostrar a janela que apresenta os tempos dos karts que estão a andar
		JanelaTempos janelaTempos = new JanelaTempos( estKarts );
		janelaTempos.setLocation(400, 50);
		janelaTempos.setVisible( true );
		estKarts.addCronometragemListener( janelaTempos );

		// criar e mostrar a janela que apresenta a informação sobre um piloto
		JanelaPiloto janelaPiloto = new JanelaPiloto( estKarts );
		janelaPiloto.setLocation( 700, 50);
		janelaPiloto.setVisible( true );
		
		
	}

	/** método para fazer a leitura dos karts e pilotos 
	 * @throws IOException */
	private static void setupKartodromo(Kartodromo estKarts) throws IOException {
		// TODO FEITO adicionar um piloto convidado com o id "guest"
		
		//CRIAÇÃO DO PILOTO "GUEST"
		Convidado guest = new Convidado("Guest","Piloto Convidado");
		estKarts.adicionarPiloto(guest,guest.getId() );
		
		
		
		// TODO FEITO fazer a leitura dos ficheiros de karts e pilotos
		
		//LEITURA DO FICHEIRO DE KARTS PARA NA CLASSE KARTODROMO CRIAR OS RESPETIVOS
		File lerFicheiroKart = new File("C:\\Users\\Luís Costa\\Downloads\\PDS_TP1_EI_LuisCosta_MarcoGordo\\PDS_TP1_EI_LuisCosta_MarcoGordo\\config\\karts.dat");
		Scanner scnr = new Scanner(lerFicheiroKart);
		while(scnr.hasNextLine()){
		   String line = scnr.nextLine();
		   estKarts.addNumerosKarts(Integer.parseInt(line));
		}
		
		//LEITURA DE FICHEIROS PARA RECEÇÃO DOS DADOS
		File lerFicheiroPilotos = new File("C:\\Users\\Luís Costa\\Downloads\\PDS_TP1_EI_LuisCosta_MarcoGordo\\PDS_TP1_EI_LuisCosta_MarcoGordo\\config\\pilotos.dat");
		Scanner scannner = new Scanner(lerFicheiroPilotos);
		while(scnr.hasNextLine()){
		   String line = scannner.nextLine();
		   estKarts.adicionarPiloto(guest, line);
		     
		}
		
		
	}
		
		
	}

