package entity;

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
	public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp =gp;
		this.keyH = keyH;
		
		hasKey  =0;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 16, 16);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
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
		
		// CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		//check object collision
		int objIndex =  gp.cChecker.checkObject(this,true);
		pickUpObject(objIndex);
		
		// If collision is false, movement is okayed
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
	
	
	public void pickUpObject(int index) {
		if (index!=999) {
			String objectName = gp.obj[index].name;
			switch(objectName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[index] = null;
				break;
			
			case "Door":
				if(hasKey>0) {
					gp.playSE(3);

					gp.obj[index] = null;
					hasKey--;
					
				}
				break;
			case "Boots":
				gp.playSE(2);

				speed+=2;
				gp.obj[index] =null;
				break;
				
			
			case "Chest":
				gp.stopMusic();
				gp.playSE(4);
				gp.obj[index] = null;
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
