package aluguer;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/** Classe que "cronometra" as voltas dos karts.
 * Avisa o kartrodromo de cada vez que um kart "passa pela meta",
 * gerando tempos aleatórios para cada kart a cada 5 segundos 
 * 
 * @author F. Sergio Barbosa
 */
public class SistemaCronometragem {

	// o kartodromo associado ao sistema de cronometragem
	private Kartodromo karts;

	// hashmap com os temporizadores associados a cada kart em andamento
	private HashMap<Integer,Timer> timers = new HashMap<Integer,Timer>();
	
	// o gerador de números aleatórios
	private Random gerador = new Random();
	
	/** cria o sistema de cronometragem
	 */
	public SistemaCronometragem( ) {
	}
	
	/** definie qual o kartodromo que recebe as notificações
	 * @param karts o kartodromo vai receber as notificações
	 */
	public void setKartodromo(Kartodromo karts) {
		this.karts = karts;
	}
	
	/** inicia o processo de cronometragem de um kart
	 * @param kartNum o número do kart
	 */
	public void iniciarCronometragem( int kartNum ) {
		// criar um timer e associa-lo ao kart
		Timer t = new Timer( );		
		TimerTask tt = new TimerTask() {			
			@Override
			public void run() {
				// passaram 5 segundos desde que o kart passou a meta,
				// simular nova passagem
				passouUmKart( kartNum );
			}
		};
		// de 5 em 5 segundos gera um tempo de volta para o kart
		t.scheduleAtFixedRate( tt , 5000, 5000);
		timers.put( kartNum, t );
	}
	
	/** termina o processo de crionometragem de um kart
	 * @param kartNum o número do kart
	 */
	public void terminarCronometragem( int kartNum ) {
		Timer t = timers.remove( kartNum );
		t.cancel();
	}
	
	/** método chamado sempre um um kart passa na meta
	 * @param k o número do kart que cruzou a meta
	 */
	private void passouUmKart( int k ) {
		double tempo = calculaTempo();
		karts.passouKart(k, tempo);
	}
	
	/**
	 * gera um tempo com probabilidade normal com média 62 segundos e desvio padrão
	 * de 2 segundos. Nunca gera valores abaixo dos 60 segundos...
	 * @return o tempo da volta
	 */
	private double calculaTempo() {
		// gerar tempos com probabilidade normal por volta dos 62 segundos
		// com desvio padrão de 2
		double tempo = gerador.nextGaussian() * 2 + 62;
		// se for menor que 60 segundos converter num número maior
		if (tempo < 60)
			tempo = 62 + (60 - tempo);
		return tempo;
	}
}
