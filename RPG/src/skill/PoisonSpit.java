package skill;

import java.awt.Graphics2D;

import character.Player;
import main.GamePanel;

public class PoisonSpit extends Projectile{
    int counter = 0;
    boolean hitted = false;
	int AnimCounter = 0;
	Graphics2D g2;
	public PoisonSpit(GamePanel gp) {
		super(gp);
		speed = 8;
		graphic.setImage("/skill/Poison");
		active = true;
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
		boolean isPlayer = gp.Colchecker.checkPlayerCol(avatar);
		if(isPlayer == true) {
			if(hitted == false) {
				gp.player.TookDMG(50);
				hitted = true;
			}
		}
	}
	public void draw(Graphics2D g2) {
		//
		graphic.draw(g2, gp, this);
		
		
		//
		if(hitted == true) {
			AnimCounter++;
			speed = 0;
			if(AnimCounter <= 5 ) {
				graphic.AnimFX(g2,1,gp.player.screenX,gp.player.screenY);			
			}
			if(AnimCounter > 5 && AnimCounter < 15) {
				graphic.AnimFX(g2,2,gp.player.screenX,gp.player.screenY);
				
			}
			if(AnimCounter >= 15 && AnimCounter < 20) {
				graphic.AnimFX(g2,3,gp.player.screenX,gp.player.screenY);
			}
			if(AnimCounter >= 20) {
				AnimCounter = 0;
				active = false;
			}
		}
	}
	public void set(int x,int y, String direction, character.Character p) {
		avatar.direction = p.direction;
		this.direction = direction;
		active = true;
		this.x = x;
		this.y = y;
		
	}
	
}