package aluguer;

/** interface que representa todos os componente do sistema que
 * estiverem interessados em saber que um kart cruzou a meta
 */
public interface CronometragemListener {

	/** chamado sempre que um kart cruza a meta
	 * @param k o número do kart que cruzou a meta
	 * @param tempo o tempo da volta que o kart fez
	 */
	public void passouKart( int k, double tempo );
	
}
