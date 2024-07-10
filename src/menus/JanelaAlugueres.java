package menus;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputFilter.Config;
import java.util.*;

import javax.naming.NamingEnumeration;
import javax.swing.*;

import aluguer.CronometragemListener;
import aluguer.Kartodromo;
import piloto.Cronometrado;
import piloto.Frequente;
import piloto.Piloto;
import piloto.PilotoDefault;
import piloto.Regular;

/** Janela responsável pela criação de pilotos, gestão de alugueres e o fecho do mês
 */
public class JanelaAlugueres extends JFrame implements CronometragemListener {
	private static final long serialVersionUID = 1L;
	
	//ler ficheiro
	public int leFicheiro() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(""));
		int[] data = new int[100];
		int i = 0;
		while (scanner.hasNextInt()) {
			data[i++] = scanner.nextInt();


			scanner.close();
		}	
		return i;
	}
	
 
	// o kartódromo ao qual esta janela está associada
	private Kartodromo karts;
	
	// as cores para os karts estarem livres ou ocupados
	private static final Color disponivelColor = new Color( 30, 130, 30 ); 
	private static final Color indisponivelColor = new Color( 130, 30, 30 );
	
	// mapa para relacionar o número do kart com o respetivo painel de status
	private HashMap<Integer, KartStatusPanel> paineis = new HashMap<Integer,KartStatusPanel>(); 
	
	
	
	
	
	/** Construtor da janela de alugueres
	 * @param karts o kartódromo a que está associada
	 * @throws FileNotFoundException 
	 */
	public JanelaAlugueres( Kartodromo karts ) throws FileNotFoundException {
		super("Aluguer Karts");
		this.karts = karts;
		
		// TODO FEITO preencher a lista com os números dos karts disponíveis no kartódromo  
		//ARMAZENAMENTO DOS KARTS
		ArrayList<Integer> numerosKarts = new ArrayList<Integer>();
		
		//PERCORRER O ARRAY PARA ADICIONAR NO NOVO ARRAY  
		for(int i = 0; i < karts.getNumerosKarts().size() ;i++) {	
			numerosKarts.add(karts.getNumerosKarts().get(i));
				
		}

		// configura a janela, mas para isso precisa de saber
		// quais os números dos karts que podem ser usados 
		//IMPRIME NO ECRÃ OS KARTS
		setupGUI( numerosKarts );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	
	
	
	
	/** chamado quando se pressiona o botão de criar piloto cronometrado
	 */
	protected void criarPilotoCronometrado() {
		DadosPilotoDialog pedeDados = pedirDadosPiloto( "Novo Piloto Cronometrado");
		
		if(validarNomeId(pedeDados.id, pedeDados.nome) == true) {
			Cronometrado pCronometrado = new Cronometrado(pedeDados.getId(),pedeDados.getNome());
			karts.adicionarPiloto(pCronometrado,pCronometrado.getId());
			// TODO FEITO adicionar o piloto
		}
	}

	/** chamado quando se pressiona o botão de criar piloto regular
	 */
	protected void criarPilotoRegular() {
		DadosPilotoDialog pedeDados = pedirDadosPiloto( "Novo Piloto Regular");
		if( validarNomeId(pedeDados.nome, pedeDados.id )== true) {
			// TODO adicionar o piloto
			Regular pRegular = new Regular(pedeDados.getId(),pedeDados.getNome());
			karts.adicionarPiloto(pRegular,pRegular.getId());
		}
	}

	/** chamado quando se pressiona o botão de criar piloto frequente
	 */
	protected void criarPilotoFrequente() {
		DadosPilotoDialog pedeDados = pedirDadosPiloto( "Novo Piloto Frequente");
		if(validarNomeId(pedeDados.nome, pedeDados.id )== true) {
			Frequente pFrequente = new Frequente(pedeDados.getId(),pedeDados.getNome());
			karts.adicionarPiloto(pFrequente,pFrequente.getId());
			// TODO adicionar o piloto
		}
	}

	/** chamado quando se pressiona o botão de criar o piloto,
	 * para validar os dados introduzidos 
	 * @param id id a validar
	 * @param nome nome a validar
	 * @return true se os dados estiverem corretos, false caso contrário
	 */
	
	public boolean validarNomeId(String id, String nome) {
		//VERIFICAÇÕES DOS ID´S
		if( id == null || id.length()>=10 || id.contains(" ") || id == "Guest")
			return false;
		// TODO FEITO verificar se o id está repetido
		//RECEBE OS PILOTOS E O RESPETIVO E ID E COMPARA SE ESTE TEM ESPAÇO VAZIO NO ARRAY 
		if(karts.receberPilotos(id)!=null) 
			return false;
		return true;
	}
	
	@Override
	public void passouKart(int kNum, double tempo) {
		KartStatusPanel ks = paineis.get( kNum );
		// TODO atualizar com o id do piloto, as voltas já dadas e as que alugou
		//RECBER O PILOTO E O KART ASSOCIADO AO PILOTO CORRESPONDENTE
		Piloto piloto = karts.receberPilotos(karts.getIdKart(kNum));
		//INCREMENTAÇÃO
		int voltas = piloto.getVoltasConcluidas();
		voltas++;
		//GUARDAR AS VOLTAS ATUALIZADAS
		piloto.setVoltasConcluidas(voltas);
		//MANDAR PARA O QUADRADO DO KART AS INFORMAÇÕES ATUALIZADAS
		ks.updateKart(piloto.getId(), piloto.getVoltasConcluidas(), piloto.getVoltas());
	
		}
		
	

	/** chamado quando se pressiona o botão de fim do mês
	 */
	protected void realizarFimMes() {
		// TODO implementar este método
		//VAI RECEBER OS PILOTOS PARA OS QUE CONTEM O MÉTODO "RECOMECAMES" PARA DAR RESET 
		//ÀS VOLTAS E ALUGUERES
		for(Piloto p:karts.receberPilotos()) {
			p.recomecaMes();
		}
		//EXTRA
		JOptionPane.showMessageDialog(null, "Mensalidades Repostas!!");
		
	}

	/** chamado quando se pressiona um dos botões de aluguer de kart
	 * @param kNum o número do kart que se vai alugar
	 */
	protected void alugarKart(Integer kNum) {
		// pedir o piloto que vai fazr o aluguer 
		String pilotoId = JOptionPane.showInputDialog("Id do piloto?" );
		
		// TODO verificar se o id é conhecido
		//SE O ID DO PILOTO NÃO EXISTE, MANDAR MENSAGEM DE ERRO
		if(karts.existeId(pilotoId) == false ) {
			JOptionPane.showMessageDialog(null, "Piloto desconhecido");
			return ;
		}
		

		try {
			// pedir o número de voltas para alugar
			//AQUI VAI ASSOCIAR O KART AO PILOTO
			karts.addidKart(kNum,pilotoId);
			Piloto piloto = karts.receberPilotos(pilotoId);
			int numVoltas = Integer.parseInt( JOptionPane.showInputDialog("Voltas pretendidas?" ) );
			//VAI ATRIBUIR AO RESPETIVO PILOTO O NUMERO DE VOLTAS QUE PRETENDE
			piloto.setVoltas(numVoltas);
			
			// TODO FEITO calcular o custo
			//CUSTO CALCULADO EM CADA CLASSE DE CADA PILOTO, POIS PARA OS PILOTOS OS PRECOS NÃO SÃO IGUAIS PARA TODOS
			double custo  = piloto.preco(numVoltas);
		

			// confirmar o aluguer, apresentando o custo
			
			piloto.setMensal(0);
			int resposta = JOptionPane.showConfirmDialog(null, "Vai custar " + custo + "! Confirma?", "Confirmar aluguer", JOptionPane.YES_NO_OPTION );
			if( resposta == JOptionPane.YES_OPTION ) {
				// se a resposta foi a confirmação, então atualizar o painel de status
				KartStatusPanel ks = paineis.get( kNum );
				//METER O PILOTO EM PISTA NO RESPETIVO KART
				ks.updateKart( pilotoId, 0, numVoltas );
				piloto.setKart(kNum);
				// TODO começar o aluguer
				//COMEÇAR O ALUGUER DO RESPETIVO KART
				karts.comecarAluguer(kNum);
				
			}
		} catch( Exception e ){
			JOptionPane.showMessageDialog(null, "Dados mal introduzidos" );
			return;
		}
	}

	/** Cria e retorna a janela de diálogo que irá pedir os dados do piloto a criar 
	 * @param titulo o título a dar à janela que pede os dados
	 * @return a janela que contém os dados introduzidos
	 */
	private DadosPilotoDialog pedirDadosPiloto( String titulo ) {
		DadosPilotoDialog dlg = new DadosPilotoDialog( this, titulo );
		dlg.setVisible( true );
		return dlg;
	}

	/** responsável pela configuração da janela de alugueres, cria e coloca todos
	 * os elementos gráficos necessários à janela
	 */
	private void setupGUI( ArrayList<Integer> numerosKarts ) {
		setSize( 380, 600 );		
		JPanel criarPiloto = setupAreaCriacaoPiloto( );
		JPanel kartsDisponiveis = setupAreaKarts( numerosKarts );
		JButton fimMesBt = new JButton( "Fim do mês" );
		fimMesBt.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				realizarFimMes( );				
			}
		});
		getContentPane().add( criarPiloto, BorderLayout.NORTH );
		getContentPane().add( kartsDisponiveis, BorderLayout.CENTER );		
		getContentPane().add( fimMesBt, BorderLayout.SOUTH );
	}

	/** configura a área com os botões de criação de pilotos
	 * @return o painel com os botões
	 */
	private JPanel setupAreaCriacaoPiloto() {
		JPanel pnlPiloto = new JPanel( new GridLayout(1,0) );
		
		JButton cronoBt = new JButton("Cronometrado");
		cronoBt.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				criarPilotoCronometrado( );
			}
		});
		pnlPiloto.add( cronoBt );
		
		JButton regBt   = new JButton("Regular");
		regBt.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				criarPilotoRegular( );
			}
		});
		pnlPiloto.add( regBt );
		
		JButton freqBt  = new JButton("Frequente");
		freqBt.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				criarPilotoFrequente( );
			}
		});
		pnlPiloto.add( freqBt );
		
		pnlPiloto.setBorder( BorderFactory.createTitledBorder("Criar novo piloto"));
		return pnlPiloto;
	}

	/** configura a área onde estão os botões que representam os karts e 
	 * respetiva info.
	 * @param kartsNum a lista com os números dos karts que há para alugar
	 * @return o painel com os botões
	 */
	private JPanel setupAreaKarts( List<Integer> kartsNum ) {
		GridLayout layout = new GridLayout(0,4);
		layout.setHgap( 5 );
		layout.setVgap( 5 );
		JPanel kartsBts = new JPanel( layout );
		kartsBts.setBorder( BorderFactory.createTitledBorder("Alugar Kart"));

		for( Integer kNum : kartsNum ) {
			KartStatusPanel kpanel = new KartStatusPanel(kNum);
			kartsBts.add( kpanel );
			paineis.put( kNum, kpanel );
		}
		return kartsBts;
	}
	
	/** classe auxiliar que apresenta o número do kart, e o botão de alugar
	 * ou, caso esteja alugado, o número de volta dadas e alugadas
	 * @author F. Sergio Barbosa
	 */
	private class KartStatusPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JButton aluguerBt = new JButton("Alugar");
		private JLabel voltasLbl;
		private JLabel kartNumLbl;
		private JLabel idLbl;
		private int kartNum;
		
		public KartStatusPanel( int kartNum) {
			this.kartNum = kartNum;
			setupKartPanel();
			updateKart( "", 0, 0 );
		}
		
		/** atualiza a informação do painel
		 * @param idPiloto o id do piloto a alugar (vazio se estiver disponível)
		 * @param nVoltasDadas quantas voltas foram dadas
		 * @param nVoltasAlugadas quantas voltas foram alugadas
		 */
		public void updateKart( String idPiloto, int nVoltasDadas, int nVoltasAlugadas ) {
			// se não tem ou acabou um aluguer, apresentar como disponível
			//Piloto piloto = karts.receberPilotos(idPiloto); 
			
				if( nVoltasAlugadas == 0 || nVoltasDadas == nVoltasAlugadas) {
				aluguerBt.setVisible( true );
				voltasLbl.setVisible( false );
				idLbl.setVisible( false );
				kartNumLbl.setForeground( Color.BLACK );
				setBackground( disponivelColor );
				
			}
			// se está a meio de um aluguer, apresentar como indisponível
			else {
				aluguerBt.setVisible( false );
				voltasLbl.setVisible( true );
				idLbl.setVisible( true );
				kartNumLbl.setForeground( Color.WHITE );
				setBackground( indisponivelColor );
				idLbl.setText( idPiloto );
				voltasLbl.setText( nVoltasDadas + "/" + nVoltasAlugadas );
			}
			
				
			
			
			
			
			
		}
		
		/**prepara o painel de informações do kart
		 */
		private void setupKartPanel() {
			SpringLayout layout = new SpringLayout();
			setLayout( layout );
			kartNumLbl = new JLabel( "" + kartNum );
			Font f = kartNumLbl.getFont().deriveFont( kartNumLbl.getFont().getSize2D() + 5 );
			kartNumLbl.setFont( f );
			voltasLbl = new JLabel( );
			voltasLbl.setForeground( Color.white );
			aluguerBt = new JButton("Alugar");
			idLbl = new JLabel( );
			idLbl.setForeground( Color.white );
			add( kartNumLbl );
			add( voltasLbl );
			add( aluguerBt );
			add( idLbl );
			aluguerBt.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					alugarKart( kartNum );
				}
			});

			layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, kartNumLbl, 0, SpringLayout.HORIZONTAL_CENTER, this );
			layout.putConstraint( SpringLayout.VERTICAL_CENTER, kartNumLbl, 0, SpringLayout.VERTICAL_CENTER, this );
			layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, aluguerBt, 0, SpringLayout.HORIZONTAL_CENTER, this );
			layout.putConstraint( SpringLayout.NORTH, aluguerBt, 5, SpringLayout.SOUTH, kartNumLbl );
			layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, voltasLbl, 0, SpringLayout.HORIZONTAL_CENTER, this );
			layout.putConstraint( SpringLayout.NORTH, voltasLbl, 5, SpringLayout.SOUTH, kartNumLbl );
			layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, idLbl, 0, SpringLayout.HORIZONTAL_CENTER, this );
			layout.putConstraint( SpringLayout.NORTH, idLbl, 5, SpringLayout.NORTH, this );
		}
	}
	
	
	/** classe auxiliar que pede os dados do piloto a criar
	 * @author F. Sergio Barbosa
	 */
	private class DadosPilotoDialog extends JDialog {
		private static final long serialVersionUID = 1L;
		
		private String id;
		private String nome;
		private boolean dadosValidos= false;
		
		public DadosPilotoDialog(Frame owner, String title) {
			super(owner, title);
			setPreferredSize( new Dimension(300, 150) );
			setModal( true );
			setupDialog();
			pack();
		}

		/** retorna o nome do piloto a criar. Este valor só tem relevãncia
		 * se o painel tiver dados válidos. 
		 * @return o nome do piloto a criar
		 */
		public String getNome() {
			return nome;
		}

		/** retorna o id do piloto a criar. Este valor só tem relevãncia
		 * se o painel tiver dados válidos. 
		 * @return o id do piloto a criar
		 */
		public String getId() {
			return id;
		}

		/** Inidica se o painel tem dados válidos, isto é, o utilizador
		 * pressionou o botão "criar" e os dados foram verificados
		 * e dados como válidos. Se o utilizador pressinou o botão de cancelar
		 * os dados são inválidos.
		 * @return true se tem dados válidos, false caso contrário
		 */
		public boolean temDadosValidos() {
			return dadosValidos;
		}

		/** configura os elementos gráficos da janela
		 */
		private void setupDialog() {
			SpringLayout layout = new SpringLayout();
			JPanel panel = new JPanel( layout );
			
			JLabel idLbl = new JLabel( "Id: ");
			panel.add( idLbl );
			
			JLabel nomeLbl = new JLabel( "Nome: ");
			panel.add( nomeLbl );
			
			JTextField idTf = new JTextField( );
			panel.add( idTf);

			JTextField nomeTf = new JTextField( );
			panel.add( nomeTf);
			
			JButton criarBt = new JButton("Criar");
			panel.add( criarBt );
			criarBt.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					id = idTf.getText();
					nome = nomeTf.getText();
					if( validarNomeId( id, nome ) ) {
						dadosValidos = true;
						setVisible( false );
					}
					else {
						JOptionPane.showMessageDialog(null, "Dados mal introduzidos", "Erro", JOptionPane.ERROR_MESSAGE );						
					}
				}
			});
			
			JButton cancelBt = new JButton("Cancelar");
			panel.add( cancelBt );
			cancelBt.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dadosValidos = false;
					setVisible( false );
				}
			});
			
			layout.putConstraint( SpringLayout.NORTH, idLbl, 5, SpringLayout.NORTH, panel );
			layout.putConstraint( SpringLayout.WEST, idLbl, 5, SpringLayout.WEST, panel );
			layout.putConstraint( SpringLayout.WEST, idTf, 5, SpringLayout.EAST, idLbl);
			layout.putConstraint( SpringLayout.EAST, idTf, -5, SpringLayout.EAST, panel);
			layout.putConstraint( SpringLayout.BASELINE, idTf, 0, SpringLayout.BASELINE, idLbl);
			
			layout.putConstraint( SpringLayout.NORTH, nomeLbl, 5, SpringLayout.SOUTH, idLbl );
			layout.putConstraint( SpringLayout.WEST, nomeLbl, 0, SpringLayout.WEST, idLbl );
			layout.putConstraint( SpringLayout.WEST, nomeTf, 5, SpringLayout.EAST, nomeLbl);
			layout.putConstraint( SpringLayout.BASELINE, nomeTf, 0, SpringLayout.BASELINE, nomeLbl);
			layout.putConstraint( SpringLayout.EAST, nomeTf, 0, SpringLayout.EAST, idTf);
			
			layout.putConstraint( SpringLayout.EAST, criarBt, -5, SpringLayout.HORIZONTAL_CENTER, panel );
			layout.putConstraint( SpringLayout.SOUTH, criarBt, -5, SpringLayout.SOUTH, panel );
			
			layout.putConstraint( SpringLayout.WEST, cancelBt, 5, SpringLayout.HORIZONTAL_CENTER, panel );
			layout.putConstraint( SpringLayout.SOUTH, cancelBt, -5, SpringLayout.SOUTH, panel );
			
			setContentPane( panel );
		}
	}
}
