package piloto;

import java.util.ArrayList;

public class Regular extends PilotoDefault {


	
	public Regular(String id, String nome) {
		super(id, nome);
		this.setMensal(10);
		
	}
	
	
	//GETTERS E SETTERS
	public ArrayList<Double> getTemposRegistados() {
		return temposRegistados;
	}

	public void setTemposRegistados(ArrayList<Double> temposRegistados) {
		this.temposRegistados = temposRegistados;
	}
	//GETTERS E SETTERS
	
	//CALCULAR O PRECO DA MENSALIDADE BEM COMO AS CONDIÇÕES ESPECIAIS PARA O PILOTO REGULAR
	public double preco(int VoltasaDar) {
		int apontador = 2;
		if(VoltasaDar>=10 && VoltasaDar<20) {
			VoltasaDar= VoltasaDar - apontador;
		}
		for(int i=0;i<VoltasaDar;i++) {
			apontador = apontador + 2;
			if(VoltasaDar>=20 + i && VoltasaDar<30 + i)
				VoltasaDar = VoltasaDar - apontador;
		}
		return VoltasaDar + getMensal();
	} 
	
	public void limparLinha() {
		
	}
	
	//MÉTODO PARA RECOMEÇAR MÊS COM AS CONDIÇÕES DESTE PILOTO, PARA RESET DA MENSALIDADE E LIMPEZA DOS TEMPOS JÁ EFETUADOS PELO MESMO
	public void recomecaMes() {
		setMensal(10);
		temposRegistados.clear();
	}
	
	
	
	


}
