package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;
public class GamePanel extends JPanel implements Runnable{
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
    
    //WORLD SETTINGS
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
    int FPS = 60;
    
    //System
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public Player player = new Player(this, keyH);
    TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public SuperObject obj[] = new SuperObject[10];
    public AssetPlacer aPlacer = new AssetPlacer(this);
    Thread gameThread;
    public UI ui = new UI(this);

    public GamePanel() {
    	this.setPreferredSize(new Dimension(screenWidth, screenHeight) );
    	this.setBackground(Color.black);
    	this.setDoubleBuffered(true);
    	this.addKeyListener(keyH);
    	this.setFocusable(true);
    }
    public void setupGame() {
    	aPlacer.setObject();
    	playMusic(0);
    }
    
    public void startGameThread() {
    	gameThread = new Thread(this);
    	gameThread.start();
    }
    
	@Override
	public void run() {
		//delta time method
		
		double drawInterval = 1000000000/FPS;
		double delta = 0 ;
		long lastTime = System.nanoTime();
		long currentTime;
	
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime)/drawInterval;
			
			lastTime = currentTime;
			
			if(delta>=1) {
				update();
				repaint();
				delta--;
			}
		}
		
		
	}
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		
		tileM.draw(g2);
		
		for(int i=0; i<obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2,this);
			}
		}
		
		player.draw(g2);
		
		ui.draw(g2);
		
		g2.dispose();
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
    public void stopMusic() {
    	music.stop();
    }
    public void playSE(int i) {
    	se.setFile(i);
    	se.play();
    }
  
}
