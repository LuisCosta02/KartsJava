package piloto;

public class Convidado extends PilotoDefault {

	public Convidado(String id, String nome) {
		super(id, nome);
		
	}
	
	public double preco(int voltasExtra) {
		return voltasExtra+1;	
	}
	
	
	
}
