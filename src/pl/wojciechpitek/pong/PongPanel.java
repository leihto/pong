package pl.wojciechpitek.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JPanel;

/* TODO:
 * - AI for right player
 * - Small changes and bugfixing
 */

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	
	static final long serialVersionUID = 1L;

	private int speed = 3;
	
	private static final int verticalCenter = 235;
	
	private int[ ] dims = { -1, 1 };
	
	private int ballSize = 15;
	private int ballX = 350;
	private int ballY = 235;
	
	private int padPlayerW = 5;
	private int padPlayerH = 100;
	
	private int playerPadY = PongPanel.verticalCenter - this.padPlayerH; // Top of pad
	
	private int padSpeed = 4;
	
	private boolean pressedUp = false;
	private boolean pressedDown = false;
	
	private static final int startX = 350;
	private static final int startY = 235;
	
	private int deltaX = -1;
	private int deltaY = 1;
	
	private int scoreL = 0;
	private int scoreR = 0;
	
	private Timer t = new Timer( 1000 / 100, this );
	
	public PongPanel( ) {
		this.setBackground( Color.BLACK );
		
		setFocusable( true );
		addKeyListener( this );
		
		
		this.t.start( );
		
	}
	
	public void actionPerformed( ActionEvent e ) {
		step( );
	}
	
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
		g.setColor( Color.WHITE );
		
		if( this.scoreL > 4 || this.scoreR > 4 ) {
			String x =  this.scoreL > 4 ? "lewy" : "prawy";
			g.drawString( "Wygral gracz " + x, PongPanel.startX - 50, PongPanel.startY - 100 );
			t.stop();
		}
		
		g.fillOval( this.ballX, this.ballY, this.ballSize, this.ballSize );
		
		g.fillRect( 15,  this.playerPadY, this.padPlayerW, this.padPlayerH );
		g.fillRect( getWidth() - 15 - this.padPlayerW ,  this.playerPadY, this.padPlayerW, this.padPlayerH );
	
	
		g.drawString( this.scoreL + " : " + this.scoreR, PongPanel.startX, 15 );
		
	}
	
	public void step( ) {
		
		/* Movement of pads */
		
		int nextPadY = this.playerPadY;
		if( this.pressedUp ) {
			
			nextPadY = this.playerPadY - this.padSpeed;
			if( nextPadY > 0 ) {
				this.playerPadY = nextPadY;
			}
			
		}
		
		if( this.pressedDown ) {
			
			nextPadY = this.playerPadY + this.padSpeed;
			if( nextPadY + this.padPlayerH < this.getHeight() ) {
				this.playerPadY = nextPadY;
			}
			
		}
		
		int ballNextX = this.ballX + this.deltaX * this.speed;
		int ballNextY = this.ballY + this.deltaY * this.speed;
		
		
		if( ballNextX <= 0 || ballNextX + this.ballSize >= this.getWidth() ) {
			
			if( ballNextX <= 0 )
				this.scoreR++;
			else
				this.scoreL++;
			
			this.deltaX = dims[ Math.round( ( float ) Math.random( ) ) ];
			this.deltaY = dims[ Math.round( ( float ) Math.random( ) ) ];
			this.ballX = PongPanel.startX;
			this.ballY = PongPanel.startY;
			return;
		}
		
		if( ballNextY <= 0 || ballNextY + this.ballSize >= this.getHeight() ) {
			this.deltaY *= -1;
		}
		
		/* Ball touches a pad */
		
		if( ( ballNextX <= 15 || ballNextX >= this.getWidth( ) - 30 ) && ballNextY >= this.playerPadY + (int) (this.ballSize / 2 ) && ballNextY <= this.playerPadY + this.padPlayerH - (int) (this.ballSize / 2 ) ) {
			this.deltaX *= -1;
		}
		
		ballX += deltaX * this.speed;
		ballY += deltaY * this.speed;
		
		
		
		repaint( );
		
	}
	
	public void keyPressed( KeyEvent e ) {
		if( e.getKeyCode( ) == KeyEvent.VK_UP )
			this.pressedUp = true;
		else if( e.getKeyCode( ) == KeyEvent.VK_DOWN )
			this.pressedDown = true;
	}

	@Override
	public void keyReleased( KeyEvent e ) {
		if( e.getKeyCode( ) == KeyEvent.VK_UP )
			this.pressedUp = false;
		else if( e.getKeyCode( ) == KeyEvent.VK_DOWN )
			this.pressedDown = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
}
