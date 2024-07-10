package piloto;

import java.util.ArrayList;

public interface Piloto {

	//INTERFACE DA SUPERCLASSE PILOTODEFAULT
	String getId();

	void setId(String id);

	String getNome();

	void setNome(String nome);

	int getVoltasConcluidas();

	int getVoltas();

	void setVoltas(int voltas);

	void setVoltasConcluidas(int voltasConcluidas);
	
	ArrayList<Double> getTemposRegistados();

	void setTemposRegistados(double t);
	
	int getKart();

	void setKart(int kart);
	
	int getMensal();

	void setMensal(int mensal);

	void recomecaMes();

	double preco(int numVoltas);
	
	double getMelhorTempo();

	void setMelhorTempo(double melhorTempo);
	
	void limpar();
	
	ArrayList<String> getAssociarKart();

	void setAssociarKart(ArrayList<String> associarKart);


}