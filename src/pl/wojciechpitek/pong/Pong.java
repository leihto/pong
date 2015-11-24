package pl.wojciechpitek.pong;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Pong {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame( "Pong" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize( 700, 500 );
		frame.setResizable( false );
		
		frame.setLayout( new BorderLayout() );
		
		PongPanel pong = new PongPanel( );
		
		frame.add( pong, BorderLayout.CENTER );
		
		frame.setVisible( true );
	}

}
