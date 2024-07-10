package menus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import aluguer.Kartodromo;
import piloto.Piloto;



/** Janela respopnsável pela visualização da informação de um piloto
 */
public class JanelaPiloto extends JFrame {
	private static final long serialVersionUID = 1L;

	// o kartódromo ao qual a janela está associada
	private Kartodromo karts; 
	
	// elementos gráficos que tem de ser atualizados
	private JLabel idLbl;
	private JLabel nomeLbl;
	private JLabel melhorLbl; 
	private JTable tabelaTempos; 
	private DefaultTableModel listaTempos;
	
	/** Cria a janela que mostra a informação do piloto
	 * @param karts o kartódromo associado à janela
	 */
	public JanelaPiloto( Kartodromo karts ) {
		super("Visualização de Piloto");
		this.karts = karts;
		setupGUI();
	}

	/** chamado quando se pressiona o botão de mudar de piloto
	 * e após o utilizador introduzir o id do piloto
	 * @param pilotoId o id do piloto do qual se quer ver informação
	 */
	protected void mudarPiloto( String pilotoId ) {
		// TODO FEITO verificar se o id é válido
		
		//COMPARAÇÃO DO ID INTRODUZIDO PARA COMPARAR
		if( karts.existeId(pilotoId) != true ) {
			JOptionPane.showMessageDialog(null, "Piloto desconhecido");
			return;
		}
		// limpar a lista de tempos
		listaTempos.setRowCount(0);

		// TODO atualizar a informação do piloto
		//VAI RECEBER O ID DO PILOTO, O NOME E TAMBÉM O SEU MELHOR TEMPO REALIZADO
		Piloto piloto = karts.receberPilotos(pilotoId);
		atualizarInfoPiloto( piloto.getId() , piloto.getNome() , piloto.getMelhorTempo() );
		
		// TODO para cada tempo do piloto, adicionar o respetivo tempo à lista
		//LIMPAR OS REGISTOS, IR AOS TEMPOS JÁ REGISTADOS E ADICIONAR POR LINHA
		piloto.limpar();
		//MEDIR O ARRAY DOS TEMPOS ARMAZENADOS PELO SISTEMA E REGISTAR NA JANELA PILOTO
		for(int i = 0;  i < piloto.getTemposRegistados().size();i++) {
			adicionaLinhaTempo( piloto.getKart(), piloto.getTemposRegistados().get(i), LocalDate.now() );
			
			
		}
		

	}	
	
	/** atualiza a informação do piloto na janela
	 * @param id o id do piloto a ver
	 * @param nome o nome do piloto a ver
	 * @param tempo o tempo de melhor volta do piloto
	 */
	private void atualizarInfoPiloto(String id, String nome, double tempo) {	
	for(int i=0;i<=karts.receberPilotos().size();i++) {
		idLbl.setText( id);
		nomeLbl.setText( nome );
		melhorLbl.setText( "" + tempo );
	}
		
	}

	/** adiciona uma linha de tempo à lista de tempos do piloto
	 * @param kartNum em que kart foi feita a volta
	 * @param tempo o tempo da volta
	 * @param date a data em qeu a volta foi realizada
	 */
	private void adicionaLinhaTempo(int kartNum, double tempo, LocalDate date ) {
		String sd = date.getDayOfMonth()+"/"+date.getMonthValue() +"/" + date.getYear();
		Object data[] = { kartNum, tempo, sd }; 
		listaTempos.insertRow(0, data);
	}

	/** configura os elementos gráficos da janela
	 */
	private void setupGUI() {
		setSize( 400, 600 );
		
		JPanel infoPiloto = setupAreaPiloto( );
		JPanel tempos = setupAreaTempos( );
		getContentPane().add( infoPiloto, BorderLayout.NORTH );
		getContentPane().add( tempos, BorderLayout.CENTER );
	}

	/** prepara os elementos gráficos da lista de tempos
	 * @return o painel com os tempos
	 */
	private JPanel setupAreaTempos() {
		JPanel tempos = new JPanel( new BorderLayout() );
		String nomesCol[] = {"Kart", "Tempo", "Data" };
		listaTempos = new DefaultTableModel(nomesCol, 0);		
		tabelaTempos = new JTable( listaTempos ); 
		tabelaTempos.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabelaTempos.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabelaTempos.getColumnModel().getColumn(2).setPreferredWidth(80);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tabelaTempos.getColumnModel().getColumn(0).setCellRenderer( centerRenderer);
		tabelaTempos.getColumnModel().getColumn(1).setCellRenderer( centerRenderer);
				
		tempos.add(  new JScrollPane( tabelaTempos ) );		
		return tempos;
	}

	/** prepara os elementos gráficos da área do piloto
	 * @return o painel com os elementos do piloto
	 */
	private JPanel setupAreaPiloto() {
		SpringLayout layout = new SpringLayout();  
		JPanel pnlPiloto = new JPanel( layout);
		pnlPiloto.setPreferredSize( new Dimension(600,80) );
		
		idLbl = new JLabel( "Id piloto" );
		Font f = idLbl.getFont().deriveFont( idLbl.getFont().getSize2D() + 2 );
		idLbl.setFont( f );
		pnlPiloto.add( idLbl );

		nomeLbl = new JLabel( "Nome piloto" );
		nomeLbl.setFont( f );		
		pnlPiloto.add( nomeLbl );

		melhorLbl = new JLabel( "Melhor volta", SwingConstants.CENTER );
		melhorLbl.setFont( f );
		pnlPiloto.add( melhorLbl );
		
		JButton mudarBt = new JButton( "Mudar" );
		pnlPiloto.add( mudarBt );
		mudarBt.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id = JOptionPane.showInputDialog( "Identificador do piloto?" );
				mudarPiloto( id );				
			}
		});
		
		layout.putConstraint(SpringLayout.NORTH, idLbl, 5, SpringLayout.NORTH, pnlPiloto );
		layout.putConstraint(SpringLayout.WEST, idLbl, 5, SpringLayout.WEST, pnlPiloto );
		layout.putConstraint(SpringLayout.EAST, idLbl, -5, SpringLayout.WEST, mudarBt );

		layout.putConstraint(SpringLayout.NORTH, nomeLbl, 5, SpringLayout.SOUTH, idLbl );
		layout.putConstraint(SpringLayout.WEST, nomeLbl, 0, SpringLayout.WEST, idLbl );
		layout.putConstraint(SpringLayout.EAST, nomeLbl, -5, SpringLayout.WEST, mudarBt );
		
		layout.putConstraint(SpringLayout.NORTH, melhorLbl, 5, SpringLayout.SOUTH, nomeLbl );
		layout.putConstraint(SpringLayout.WEST, melhorLbl, 0, SpringLayout.WEST, nomeLbl );
		layout.putConstraint(SpringLayout.EAST, melhorLbl, 0, SpringLayout.EAST, nomeLbl );
		
		layout.putConstraint(SpringLayout.EAST, mudarBt, -5, SpringLayout.EAST, pnlPiloto );
		layout.putConstraint(SpringLayout.NORTH, mudarBt, 5, SpringLayout.NORTH, pnlPiloto );
		layout.putConstraint(SpringLayout.SOUTH, mudarBt, 0, SpringLayout.SOUTH, melhorLbl );
		return pnlPiloto;
	}
}
