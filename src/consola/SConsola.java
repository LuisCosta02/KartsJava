/*
    Esta classe representa uma Janela de consola que permite ao
    utilizador escrever nela. Permite ao programador escrever e
    limpar o seu conteúdo.
    Utiliza a classe ConsolePanel desenvolvida por David Eck 
    
    Autor:  F. Sérgio Barbosa 
            Departmento de Engª das Tecnologias da Informação
            Escola Superior de Tecnologia de CAstelo Branco
            email:  fsergio@est.ipcb.pt

    Criada em 27 de Março de 2007.
*/

package consola;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import consola.*;

import javax.swing.JFrame;

public class SConsola {

	private ConsolePanel console = new ConsolePanel();
	private JFrame janela;
	
	public SConsola(String titulo, int comp, int alt) {
		janela = new ConsoleWindow( titulo, comp, alt );
	}
	
	public SConsola( String titulo ) {
		this( titulo, 500, 500);
	}
	
	public SConsola(int comp, int alt) {
		this( "Consola", comp, alt );
	}
	
	public SConsola( ){
		this( "Consola", 500, 500);
	}
	
	public void setPosition( int x, int y ){
		janela.setLocation( x, y );
	}

	// redireccionar o input da Janela para a consola 
	public byte  readByte()   { return console.getlnByte(); }
	public short readShort()  { return console.getlnShort(); }
	public boolean readBoolean() { return console.getlnBoolean(); }
	public int   readInt()    { return console.getlnInt(); }
    public long  readLong()   { return console.getlnLong(); }
    public char  readChar()   { return console.getlnChar(); }
    public float readFloat()  { return console.getlnFloat();}
    public double readDouble(){ return console.getlnDouble();}
    public String readWord()  { return console.getlnWord();}
    public String readLine()  { return console.getln(); }
    
    // redireccionar o output da Janela para a consola
    public void print( int x )       { console.put( x ); }
    public void println( int x )     { console.putln( x ); }
    public void print( long x )      { console.put( x ); }
    public void println( long x )    { console.putln( x ); }
    public void print( double x )    { console.put( x ); }
    public void println( double x )  { console.putln( x ); }
    public void print( char x )      { console.put( x ); }
    public void println( char x )    { console.putln( x ); }
    public void print( boolean x )   { console.put( x ); }
    public void println( boolean x ) { console.putln( x ); }
    public void print( String x )    { console.put( x ); }
    public void println( String x )  { console.putln( x ); }
    public void println( )           { console.putln(); }
    
    // limpar a consola
    public void clear() {
    	console.clear();
    }
    
	class ConsoleWindow extends JFrame {
		ConsoleWindow( String titulo, int comp, int alt) {
			setSize( comp, alt );
			setTitle( titulo );
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			getContentPane().add( console, BorderLayout.CENTER );			
			setVisible( true );
		}
	}

	public void close(){
		janela.setVisible( false );
		janela.dispose();
	}
}
