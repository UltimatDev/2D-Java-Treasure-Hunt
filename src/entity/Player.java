package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp =gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 16, 16);
		
		setDefaultValues();
		getPlayerImage();
		
		
	}
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed=4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			up = ImageIO.read(getClass().getResourceAsStream("/player/player_up.png"));
			down = ImageIO.read(getClass().getResourceAsStream("/player/player_up.png"));
			left = ImageIO.read(getClass().getResourceAsStream("/player/player_left.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/player/player_right.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void update() {
		//in java upper left corner is 0,0
		if (keyH.upPressed == true) {
			direction = "up";
		}
		else if (keyH.downPressed == true) {
			direction = "down";
				}
		else if (keyH.leftPressed == true) {
			direction = "left";
		}
		else if (keyH.rightPressed == true) {
			direction = "right";
		
		}
		else {
			direction = "static";
		}
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		if(collisionOn == false) {
			switch(direction) {
			case "up":
				worldY -= speed;				
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			case "static":
				break;
			}
		}
		
		
	}
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		BufferedImage image = null;
		switch(direction) {
		case "up":
			image = up;
			break;
		case "down":
			image = down;
			break;
		case "left":
			image = left;
			break;
		case "right":
			image = right;
			break;
			
		case "static":
			image=up;
			break;
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
	}
}
