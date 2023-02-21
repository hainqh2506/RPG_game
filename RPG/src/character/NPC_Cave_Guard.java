package character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.GamePanel;

public class NPC_Cave_Guard extends Character{
	
	public int ActionCounter;
	String dialog[] = new String[20];
	int dialogIndex = 0;
	boolean speaking = false;
	int dialogcounter = 0;
	
	public NPC_Cave_Guard(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 0;
		
		//Set Image
		graphic.up1 = graphic.setup("/npc/Luffy_1");
		graphic.up2 = graphic.setup("/npc/Luffy_2");
		graphic.down1 = graphic.setup("/npc/Luffy_1");
		graphic.down2 = graphic.setup("/npc/Luffy_2");
		graphic.left1 = graphic.setup("/npc/Luffy_1");
		graphic.left2 = graphic.setup("/npc/Luffy_2");
		graphic.right1 = graphic.setup("/npc/Luffy_1");
		graphic.right2 = graphic.setup("/npc/Luffy_2");
		graphic.idle1 = graphic.setup("/npc/Luffy_1");
		graphic.idle2 = graphic.setup("/npc/Luffy_2");
		setDialog();
	}
	public void setDialog() {
		dialog[0] = "Hello, mate";
		dialog[1] = "Beware of the cave";
		dialog[2] = "There is an old \ndungeon down there";
		dialog[3] = "I have warned you";
		dialog[4] = "Becareful!!!";
	}
	public void interact() {
		speaking = true;
	}
	public void update() {
		gp.Colchecker.checkPlayer(this);
		graphic.updateDirection(this,10,2);
	}
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		graphic.drawCharacter(this, g2,gp);
		if(speaking == true) {
			dialogcounter++;
			graphic.drawDialog(g2,dialog[dialogIndex],screenX,screenY);
			if(dialogcounter == 120) {// 2s
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
