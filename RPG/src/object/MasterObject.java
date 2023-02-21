package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Utility;

public class MasterObject {
	
	public BufferedImage image;
	public BufferedImage InventoryImage;
	public String name;
	public boolean collision = false;
	public boolean item = true;
	public int worldX,worldY;
	public Rectangle collisionBox = new Rectangle(0,0,48,48);
	public int collisionBoxDefaultX = 0;
	public int collisionBoxDefaultY = 0;
	public Utility utility = new Utility();
	public String downFX,upFX,leftFX,rightFX;
	public String discription = "";
	public int amount = 1;
	public boolean stackable = false;
	
	//Stat
	public int DEF;
	public int ATK;
	public int range;
	public int price;
	public int knockbackpower;
	public String type;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		//Only create object that visible on screen
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
		}
	}
	public void interact(int i) {
		
	}
	public void effect(character.Character c) {
		
	}

}
