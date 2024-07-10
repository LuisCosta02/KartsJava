package piloto;

import java.util.ArrayList;

//import teste.PilotoInvalidoException;

public class PilotoDefault implements Piloto  {
	
	private String nome;
	private String id;
	
	//VOLTAS QUE O PILOTO JÁ REALIZOU
	private int voltasConcluidas;
	
	//VOLTAS A ALUGAR 
	private int voltas;
	
	//REGISTOS DE TEMPOS
	ArrayList<Double>temposRegistados = new ArrayList<Double>(); 
	private int kart;
	
	//VALOR MENSAL A PAGAR
	private int Mensal;
	
	//TEMPO DA MELHOR VOLTA
	private double melhorTempo;
	
	//ASSOCIAÇÃO DE KART COM O PILOTO
	ArrayList<String>associarKart = new ArrayList<String>();
	

	//MÉTODO PARA IR BUSCAR O MELHOR TEMPO DE UM PILOTO NOS SEUS REGISTOS DE TEMPO 
	public void setMelhorTempo(double melhorTempo) {		
		for (int i = 0; i < getTemposRegistados().size(); i++)
            if (getTemposRegistados().get(i) < melhorTempo)
                melhorTempo = getTemposRegistados().get(i);
		
		this.melhorTempo = melhorTempo;
	}
	
	
	//GETTERS E SETTERS
	public ArrayList<String> getAssociarKart() {
		return associarKart;
	}

	public void setAssociarKart(ArrayList<String> associarKart) {
		this.associarKart = associarKart;
	}

	public void limpar() {}
	
	
	public double getMelhorTempo() {
		return melhorTempo;
		
	}

	@Override
	public double preco(int numVoltas) {
		return 0;
	}
	
	public void recomecaMes() {
		
	}
	
	
	public int getMensal() {
		return Mensal;
	}

	public void setMensal(int mensal) {
		Mensal = mensal;
	}

	public int getKart() {
		return kart;
	}

	public void setKart(int kart) {
		this.kart = kart;
	}

	public ArrayList<Double> getTemposRegistados() {
		return temposRegistados;
	}

	
	public void setTemposRegistados(double t) {
		temposRegistados.add(t);
	}

	

	public int getVoltasConcluidas() {
		return voltasConcluidas;
	}

	public int getVoltas() {
		return voltas;
	}

	public void setVoltas(int voltas) {
		this.voltas = voltas;
	}


	public void setVoltasConcluidas(int voltasConcluidas) {
		this.voltasConcluidas = voltasConcluidas;
	}

	
	public PilotoDefault(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {	
			this.id = id;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//GETTERS E SETTERS


}
