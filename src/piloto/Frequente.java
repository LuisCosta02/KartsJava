package piloto;

import java.util.ArrayList;


public class Frequente extends PilotoDefault {

	public Frequente(String id, String nome) {
		super(id, nome);
		//MENSALIDADE E RESPETIVAS VOLTAS GRÁTIS
		this.setMensal(130);
		this.setvGratis(200);
		
	}
	private int vGratis;
	
	
	//GETTERS E SETTERS
	public int getvGratis() {
		return vGratis;
	}

	public void setvGratis(int vGratis) {
		this.vGratis = vGratis;
	}
	public ArrayList<Double> getTemposRegistados() {
		return temposRegistados;
	}

	public void setTemposRegistados(ArrayList<Double> temposRegistados) {
		this.temposRegistados = temposRegistados;
	}
	//GETTERS E SETTERS
	
	//RECOMECAR MÊS BEM COMO O RESET DA MENSALIDADE E DAS VOLTAS GRÁTIS A QUE TEM DIREITO MENSALMENTE
	public void recomecaMes() {
		setMensal(130);
		this.setvGratis(200);
	}
	
	//CALCULO DO PREÇO DAS VOLTAS, VERIFICAÇÃO SE JÁ EXCEDEU AS VOLTAS GRÁTIS MENSALMENTE
	public double preco(int voltas) {
		if(getvGratis() >= voltas) {
			setvGratis(getvGratis()-voltas);
			return 0+getMensal();
		}
		else if(voltas >= getvGratis()) {
			voltas = voltas-getvGratis()+getMensal();
			setvGratis(0);
			return voltas;
		}
		else
			return voltas;			
	}

	
	

}
