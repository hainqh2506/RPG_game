package skill;

import java.awt.Graphics2D;

import character.Player;
import main.GamePanel;

public class Bloodslash extends Projectile{
    int counter = 0;
    boolean hitted = false;
	public Bloodslash(GamePanel gp) {
		super(gp);
		speed = 15;
		graphic.setImage("/skill/Bloodslash");
		active = true;
		direction = gp.player.direction;
		// TODO Auto-generated constructor stub
	}
	public void update() { //60 times per sec
		counter++;
		if(counter > 120) { //2s
			active = false;
		}
		graphic.UpdateDirection(this, 12, 2);
		avatar.worldX = x;
		avatar.worldY = y;
		//Check collision
		int mobIndex = gp.Colchecker.checkCharacter(avatar, gp.npc);
		if(mobIndex != 999) {
			if(hitted == false) {
				gp.npc[gp.currentMap][mobIndex].hitted(50, mobIndex,0);
				hitted = true;
				active = false;
			}
		}
	}
	public void draw(Graphics2D g2) {
		graphic.draw(g2, gp, this);
	}
	public void set(int x,int y, String direction, Player p) {
		avatar.direction = gp.player.direction;
		active = true;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
}