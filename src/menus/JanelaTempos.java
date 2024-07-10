package menus;

import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import aluguer.CronometragemListener;
import aluguer.Kartodromo;
import piloto.Piloto;



/** Janela que apresenta os tempos por volta de todos os karts 
 */
public class JanelaTempos extends JFrame implements CronometragemListener {
	private static final long serialVersionUID = 1L;

	// kartódromo ao qual a janela está associada
	private Kartodromo karts;
	

	// elementos gráficos necessários à janela
	private JTable tabelaTempos; 
	private DefaultTableModel listaTempos;
	
	/** cria a janela que vai mostrar os tempos por volta dos vários karts
	 * @param karts o kartódromo associado à janela
	 */
	public JanelaTempos( Kartodromo karts ) {
		super("Tempos");
		this.karts = karts;
		setupGUI();
	}

	@Override
	public void passouKart(int kartNum, double tempo) {
		// TODO verificar se o número do kart é válido
		
		//VERIFICAR SE O ID INTRODUZIDO É NULO NO ARRAY
		for(int i=0;i<=karts.getNumerosKarts().size();i++) {
			if(karts.getNumerosKarts() == null )
			return;
		}
		
		
		// TODO adicionar uma linha à tabela de tempos
		Piloto piloto = karts.receberPilotos(karts.getIdKart(kartNum));
		
		//SE O PILOTO FOR DIFERENTE DE "GUEST" VAI ATUALIZAR, ADICIONAR NOS TEMPOS E DEFINIR O MELHOR TEMPO
		if(piloto.getId() != "Guest") {
			adicionaLinha( kartNum, piloto.getNome(), tempo );
			piloto.setMelhorTempo(tempo);
			piloto.setTemposRegistados(tempo);
		}
		//SENÃO VAI SÓ DEFINIR O MELHOR TEMPO
		else{
			adicionaLinha( kartNum, piloto.getNome(), tempo );
			piloto.setMelhorTempo(999.999);
			piloto.setTemposRegistados(tempo);
		}
		
		
		
		
	}
	
	/** adiciona uma linha à lista de tempos
	 * @param kartNum o número do kart
	 * @param nome o nome do piloto
	 * @param tempo o tempo da volta
	 */
	private void adicionaLinha(int kartNum, String nome, double tempo) {
		Object data[] = { kartNum, nome, tempo }; 
		listaTempos.insertRow(0, data);
	}

	/** prepara a janela e respetivos elementos gráficos
	 */
	private void setupGUI() {
		setSize( 300, 600 );
		
		String nomesCol[] = {"Kart", "Piloto", "Tempo" };
		listaTempos = new DefaultTableModel(nomesCol, 0);		
		tabelaTempos = new JTable( listaTempos ); 
		tabelaTempos.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabelaTempos.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabelaTempos.getColumnModel().getColumn(2).setPreferredWidth(80);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tabelaTempos.getColumnModel().getColumn(0).setCellRenderer( centerRenderer);
		tabelaTempos.getColumnModel().getColumn(2).setCellRenderer( centerRenderer);
				
		JScrollPane scroll = new JScrollPane( tabelaTempos );
		getContentPane().add( scroll );		
	}
}
