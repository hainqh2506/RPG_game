package character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.GamePanel;

public class NPC_Flame_Pet extends Character{
	
	public int ActionCounter;
	String dialog[] = new String[20];
	int dialogIndex = 0;
	boolean speaking = false;
	int dialogcounter = 0;
	
	public NPC_Flame_Pet(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 5;
		onPath = true;
		collisionDefaultX = 0;
		collisionDefaultY = 0;
		collisionBox.width = 0;
		collisionBox.height = 0;
		//Set Image
		graphic.up1 = graphic.setup("/npc/Blue_Ball");
		graphic.up2 = graphic.setup("/npc/Blue_Ball");
		graphic.down1 = graphic.setup("/npc/Blue_Ball");
		graphic.down2 = graphic.setup("/npc/Blue_Ball");
		graphic.left1 = graphic.setup("/npc/Blue_Ball");
		graphic.left2 = graphic.setup("/npc/Blue_Ball");
		graphic.right1 = graphic.setup("/npc/Blue_Ball");
		graphic.right2 = graphic.setup("/npc/Blue_Ball");
		graphic.idle1 = graphic.setup("/npc/Blue_Ball");
		graphic.idle2 = graphic.setup("/npc/Blue_Ball");
	}
	public void setAction() {
		if(onPath == true) {
			int goalCol = (gp.player.worldX + gp.player.collisionBox.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.collisionBox.y)/gp.tileSize;
			searchPath(goalCol, goalRow);
		}
	}
	public void interact() {
		//gp.GameState = gp.dialogState;
		onPath = true;
		//speak();
	}
	public void update() {
		setAction();
		gp.Colchecker.checkTile(this);
		gp.Colchecker.checkPlayer(this);
		graphic.updateDirection(this,10,2);
	}
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		graphic.drawCharacter(this, g2,gp);
		if(gp.player.debugmode == true) {
			graphic.drawCollision(g2, screenX+collisionBox.x, screenY+collisionBox.y, collisionBox.width,collisionBox.height);
		}
		if(speaking == true) {
			dialogcounter++;
			graphic.drawDialog(g2,dialog[dialogIndex],screenX,screenY);
			if(dialogcounter == 120) {
				dialogIndex++;
				dialogcounter = 0;
			}
			if(dialog[dialogIndex] == null) {
				speaking = false;
				dialogIndex = 0;
			}
		}
	}
}
