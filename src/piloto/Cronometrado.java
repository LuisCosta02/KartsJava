package piloto;

import java.util.ArrayList;

public class Cronometrado extends PilotoDefault {

	
	
	public Cronometrado(String id, String nome) {
		super(id, nome);
		//MENSALIDADE A 0, UMA VEZ QUE O PILOTO CRONOMETRADO NÃO TEM QUE PAGAR MENSALIDADE
		this.setMensal(0);
	}	
	//GETTERS E SETTERS
	public ArrayList<Double> getTemposRegistados() {
		return temposRegistados;
	}

	public void setTemposRegistados(ArrayList<Double> temposRegistados) {
		this.temposRegistados = temposRegistados;
	}
	//GETTERS E SETTERS
	
	//PREÇO DAS VOLTAS MAIS O ALUGUER
	public double preco(int voltasExtra) {
		return voltasExtra+1;	
	}
	
	//VAI SÓ LIMPAR OS TEMPOS
	public void limparTempos() {
		getTemposRegistados().clear();
	}

}
