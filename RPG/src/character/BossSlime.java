package character;

import java.awt.Graphics2D;
import java.util.Random;

import main.GamePanel;
import object.Item_Coin;

public class BossSlime extends Character{
	public int ActionCounter;
	int AnimCounter;
	int AtkCounter;
	int cooldown;
	boolean activated = false;
	boolean hpbarOn;
	int hpbartimer;
	int OnmapIndex;
	boolean death = false;
	Random randomatk = new Random();
	int j = randomatk.nextInt(120)+40;
	public BossSlime(GamePanel gp) {
		super(gp);
		this.name = "Boss Slime";
		direction = "down";
		speed = 1;
		lv = 1;
		MaxHP = 8000;
		HP = MaxHP;
		ATK = 250;
		//
		collisionDefaultX = 12;
		collisionDefaultY = 20;
		collisionBox.width = 80;
		collisionBox.height = 60;
		//Set Image
		int size = 100;
		graphic.up1 = graphic.setup("/monster/slime1",size,size);
		graphic.up2 = graphic.setup("/monster/slime2",size,size);
		graphic.down1 = graphic.setup("/monster/slime1",size,size);
		graphic.down2 = graphic.setup("/monster/slime2",size,size);
		graphic.left1 = graphic.setup("/monster/slime1",size,size);
		graphic.left2 = graphic.setup("/monster/slime2",size,size);
		graphic.right1 = graphic.setup("/monster/slime1",size,size);
		graphic.right2 = graphic.setup("/monster/slime2",size,size);
		graphic.idle1 = graphic.setup("/monster/slime1",size,size);
		graphic.idle2 = graphic.setup("/monster/slime2",size,size);
	}
	public void setAction() {
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
	public void contact(character.Character c) {
		if(activated == false) {
			c.TookDMG(ATK);
			activated = true;
		}
	}
	public void update() {
		if(activated == true) {
			cooldown++;
			if(cooldown == 60) {
				activated = false;
				cooldown = 0;
			}
		}
		setAction();
		gp.Colchecker.checkTile(this);
		gp.Colchecker.checkPlayer(this);
		graphic.updateDirection(this, 20,2);
	}
	public void draw(Graphics2D g2) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		if(HP > 0) {
			graphic.drawCharacter(this, g2,gp);
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
				CheckDrop(0,15,60,85,95,100);
				CheckDrop(0,15,60,85,95,100);
				CheckDrop(0,15,60,80,95,100);
				DropItem(new Item_Coin(gp),worldX+10,worldY+17);
				DropItem(new Item_Coin(gp),worldX+17,worldY+25);
				DropItem(new Item_Coin(gp),worldX+10,worldY+38);
				DropItem(new Item_Coin(gp),worldX+24,worldY+12);
				DropItem(new Item_Coin(gp),worldX+26,worldY+28);
				DropItem(new Item_Coin(gp),worldX+25,worldY+34);
				DropItem(new Item_Coin(gp),worldX+37,worldY+18);
				DropItem(new Item_Coin(gp),worldX+30,worldY+28);
				DropItem(new Item_Coin(gp),worldX+32,worldY+39);
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
		HP -= dmg; 
		OnmapIndex = i;
		hpbarOn = true;
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
