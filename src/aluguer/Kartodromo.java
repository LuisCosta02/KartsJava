package aluguer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import piloto.Piloto;
import piloto.PilotoDefault;



/** Classe que representa o kartodromo
 */
public class Kartodromo implements CronometragemListener {
	private SistemaCronometragem crono;

	// lista de elementos que têm de ser avisados quando um kart cruza a meta 
	private ArrayList<CronometragemListener> cronoLists = new ArrayList<CronometragemListener>(); 
	//ARRAYLIST PARA ARMAZENAR TODOS OS KARTS DO SISTEMA
	ArrayList<Integer> numerosKarts = new ArrayList<Integer>();
	
	//HASHMAP PARA ARMAZENAMENTO DOS PILOTOS
	private Map<String,PilotoDefault> pilotos = new HashMap<String,PilotoDefault>();
	
	private Map<Integer,String> idKart = new HashMap<Integer,String>();
  
	
	//GETTERS E SETTERS
	public String getIdKart(int kart) {
		return idKart.get(kart);
	}

	public void addidKart(Integer kNum, String pilotoId) {
		idKart.put(kNum,pilotoId);
		
	}
	
	public List<Integer> getNumerosKarts() {
		return Collections.unmodifiableList(numerosKarts);
	}
 
	public void addNumerosKarts(Integer nKarts) {
		numerosKarts.add(nKarts);
	}
	
	public Collection<PilotoDefault> receberPilotos() {
		return Collections.unmodifiableCollection(pilotos.values()); 
	}

	public void adicionarPiloto(PilotoDefault piloto, String id) {
		pilotos.put(id,piloto);
	}
	
	
	
	

	public PilotoDefault receberPilotos(String codigo) {
		return pilotos.get( codigo );
	}
	//GETTERS E SETTERS
	
	
	//MÉTODO PARA VERIFICAÇÃO QUE NOS PILOTS EXISTE O RESPETIVO ID (CÓDIGO)
	public boolean existeId(String codigo) {
		return pilotos.containsKey(codigo);
	}
	
	
	
	
	

	/** cria um kartodromo com o respetivo sistema de cronometragem
	 * @param crono sistema de cronometragem
	 */
	public Kartodromo( SistemaCronometragem crono ){
		this.crono = crono;
		crono.setKartodromo( this );
	}

	
	
	public void passouKart(int kNum, double tempo) {
		// avisar as janelas de que o kart passou na meta
		notificarPassagem(kNum, tempo);

		// TODO Se tiver acabado o aluguer informar a cronometragem
		
		Piloto piloto = receberPilotos(getIdKart(kNum));
		//CICLO PARA VERIFICAR SE JÁ CONCLUI AS VOLTAS, SENÃO VAI CONTINUAR A INCREMENTAR AS VOLTAS
		//ATÉ AO FINAL.
		//TERMINA A CRONOMETRAGEM, MANDA UMA MENSAGEM A INFORMAR E DÁ UM RESET ÀS VOLTAS 
		//PARA QUANDO O PILOTO VOLTAR A CORRER AS MESMAS ESTEJAM COM RESET
		if( piloto.getVoltasConcluidas() == piloto.getVoltas()) {
			crono.terminarCronometragem( kNum );
			JOptionPane.showMessageDialog(null, +piloto.getVoltasConcluidas()+" volta(s) completa(s)");
			piloto.setVoltasConcluidas(0);
			
		}
		
	}

	/** começa um aluguer do kart
	 * @param kartNum o número do kart a iniciar o aluguer
	 */
	public void comecarAluguer( int kartNum ) {
		
		// é preciso informar o sistema de cronometragem para cronometrar este kart
		crono.iniciarCronometragem( kartNum );
		
	}
	
	/** adiciona um elemento que tem de ser avisado de que um kart zcruzou a meta
	 * @param l o elemento a adicionar
	 */
	public void addCronometragemListener( CronometragemListener l ) {
		cronoLists.add( l );
	}
	
	/** remove um elemento que tem de ser avisado de que um kart zcruzou a meta
	 * @param l o elemento a remover
	 */
	public void removeCronometragemListener( CronometragemListener l ) {
		cronoLists.remove( l );
	}
	
	/** avisa os ouvintes de que o Kart cruzou a meta
	 * @param kNum o número do kart
	 * @param tempo o tempo por volta
	 */
	private void notificarPassagem( int kNum, double tempo ) {
		for( int i=cronoLists.size()-1; i >= 0; i-- )
			cronoLists.get(i).passouKart(kNum, tempo);
	}


	

	

	

	
}
