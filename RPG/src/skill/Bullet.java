package skill;

import java.awt.Graphics2D;

import character.Player;
import main.GamePanel;

public class Bullet extends Projectile{
    int counter = 0;
    boolean hitted = false;
	public Bullet(GamePanel gp) {
		super(gp);
		speed = 12;
		graphic.setImage("/skill/Bullet");
		active = true;
		direction = gp.player.direction;
		// TODO Auto-generated constructor stub
	}
	public void update() {
		counter++;
		if(counter > 120) {
			active = false;
		}
		graphic.UpdateDirection(this, 12, 2);
		avatar.worldX = x;
		avatar.worldY = y;
		int mobIndex = gp.Colchecker.checkCharacter(avatar, gp.npc);
		if(mobIndex != 999) {
			if(hitted == false) {
				gp.npc[gp.currentMap][mobIndex].hitted(150, mobIndex,0);
				hitted = true;
				active = false;
			}
		}
	}
	public void draw(Graphics2D g2) {
		graphic.draw(g2, gp, this,60,60);
	}
	public void set(int x,int y, String direction, Player p) {
		avatar.direction = gp.player.direction;
		active = true;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
}