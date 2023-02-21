package character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.GamePanel;
import skill.PoisonSpit;
import skill.Projectile;

public class FinalBoss extends Character{
	public int ActionCounter;
	int AnimCounter = 0;
	int AtkCounter = 0;
	int KnockbackCounter = 0;
	int cooldown;
	boolean activated = false;
	boolean hpbarOn;
	int hpbartimer;
	int OnmapIndex;
	boolean death = false;
	int defaultspeed;
	boolean knockback;
	Random randomatk = new Random();
	int j = randomatk.nextInt(120)+40;
	public FinalBoss(GamePanel gp) {
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
		collisionBox.x = 12;
		collisionBox.y = 16;
		collisionDefaultX = collisionBox.x;
		collisionDefaultY = collisionBox.y;
		collisionBox.width = 24;
		collisionBox.height = 32;
	
		//Set Image
		int size = 48;
		graphic.up1 = graphic.setup("/npc/Nagato_1",size,size);
		graphic.up2 = graphic.setup("/npc/Nagato_2",size,size);
		graphic.down1 = graphic.setup("/npc/Nagato_1",size,size);
		graphic.down2 = graphic.setup("/npc/Nagato_2",size,size);
		graphic.left1 = graphic.setup("/npc/Nagato_1",size,size);
		graphic.left2 = graphic.setup("/npc/Nagato_2",size,size);
		graphic.right1 = graphic.setup("/npc/Nagato_1",size,size);
		graphic.right2 = graphic.setup("/npc/Nagato_2",size,size);
		graphic.idle1 = graphic.setup("/npc/Nagato_1",size,size);
		graphic.idle2 = graphic.setup("/npc/Nagato_2",size,size);
		
	}
	public void setAction() {
		if(onPath == true) {
			AtkCounter++;	
			int goalCol = (gp.player.worldX + gp.player.collisionBox.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.collisionBox.y)/gp.tileSize;
			searchPath(goalCol, goalRow);
			
			if(AtkCounter == j) {
				Projectile poison = new PoisonSpit(gp);
				gp.projectileList.add(poison);
				poison.set(worldX,worldY,direction,this);
				AtkCounter = 0;
			}
			
			
		}else {
			ActionCounter++;
			if(ActionCounter == 60) {
				ActionCounter = 0;
				
				Random random = new Random();
				int i = random.nextInt(100)+1;// get a number from 1 to 100
				if(i <= 25) {
					direction = "down";
				}
				if(i > 25 && i <= 50) {
					direction = "up";
				}
				if(i > 50 && i <= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}
			}	
		}
	}
	public void contact(character.Character c) {
		if(activated == false) {
			c.TookDMG(ATK);
			if(gp.player.parrying == true) {
				knockback = true;
				speed = 10;
				direction = gp.player.direction;
			}
			activated = true;
		}
	}
	public void update() {
		if(knockback == true) {
			gp.Colchecker.checkTile(this);
			gp.Colchecker.checkPlayer(this);
			if(collisionOn == true) {
				KnockbackCounter = 0;
				knockback = false;
				speed = defaultspeed;
			}
			else if(collisionOn == false){
				graphic.updateDirection(this, 20,2);
			}
			KnockbackCounter++;
			if(KnockbackCounter == 5) {
				KnockbackCounter = 0;
				knockback = false;
				speed = defaultspeed;
			}
		}else {
			if(activated == true) {
				cooldown++;
				if(cooldown == 60) {
					activated = false;
					cooldown = 0;
				}
			}
			setAction();
			checkDistance(5);
			gp.Colchecker.checkTile(this);
			gp.Colchecker.checkPlayer(this);
			graphic.updateDirection(this, 20,2);
		}
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
	public void hitted(int dmg,int i,int knockbackpower) {
		knockback = true;
		speed = knockbackpower;
		direction = gp.player.direction;
		HP -= dmg; 
		OnmapIndex = i;
		hpbarOn = true;
		onPath = true;
		gp.ui.addMessage(""+dmg);
		hpbartimer = 0;
		if(HP <= 0) {
			gp.player.exp += 2*lv;
			gp.player.LevelUp();
			gp.ui.addMessage("+ "+ 2*lv+" exp");
			death = true;
			speed = 0;
		}
	}
}
