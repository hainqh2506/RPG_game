package character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.GamePanel;
import skill.PoisonSpit;
import skill.Projectile;

public class GreenSlime extends Monster{
	int KnockbackCounter = 0;
	int defaultspeed;
	boolean knockback;
	Random randomatk = new Random();
	int j = randomatk.nextInt(120)+40;
	public GreenSlime(GamePanel gp) {
		super(gp);
		this.name = "Green Slime";
		direction = "down";
		speed = 1;
		defaultspeed = speed;
		lv = 1;
		MaxHP = 400;
		HP = MaxHP;
		ATK = 100;
		//Collision setting
		collisionDefaultX = 7;
		collisionDefaultY = 7;
		collisionBox.width = 36;
		collisionBox.height = 32;
		
		//Set Image
		graphic.up1 = graphic.setup("/monster/slime1");
		graphic.up2 = graphic.setup("/monster/slime2");
		graphic.down1 = graphic.setup("/monster/slime1");
		graphic.down2 = graphic.setup("/monster/slime2");
		graphic.left1 = graphic.setup("/monster/slime1");
		graphic.left2 = graphic.setup("/monster/slime2");
		graphic.right1 = graphic.setup("/monster/slime1");
		graphic.right2 = graphic.setup("/monster/slime2");
		graphic.idle1 = graphic.setup("/monster/slime1");
		graphic.idle2 = graphic.setup("/monster/slime2");
		
	}
	public void draw(Graphics2D g2) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		if(HP > 0) {
			graphic.drawCharacter(this, g2,gp);
			graphic.drawName(this, g2, name, gp, 22,Color.WHITE);
			graphic.drawLv(this, g2, lv, gp);
		}else {
			//Death Animation
			AnimCounter++;
			if(AnimCounter <= 5 ) {
				graphic.AnimFX(g2,1,screenX,screenY);			
			}
			if(AnimCounter > 5 && AnimCounter < 15) {
				graphic.AnimFX(g2,2,screenX,screenY);	
			}
			if(AnimCounter >= 15 && AnimCounter < 20) {
				graphic.AnimFX(g2,3,screenX,screenY);
			}
			if(AnimCounter >= 20) {
				AnimCounter = 0;
				CheckDrop(40,65,80,90,97,100);
				//CheckDrop(40,40,40,40,100,100);
				gp.npc[gp.currentMap][OnmapIndex] = null;
			}
		}
		if(hpbarOn == true) {
			hpbartimer ++;
			graphic.drawHP(g2, gp, this);
			if(hpbartimer > 600) {
				hpbartimer = 0;
				hpbarOn = false;
			}
		}
		if(gp.player.debugmode == true) {
			graphic.drawCollision(g2, screenX+collisionBox.x, screenY+collisionBox.y, collisionBox.width,collisionBox.height);
		}
	}
}
