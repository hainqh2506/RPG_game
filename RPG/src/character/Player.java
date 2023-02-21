package character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyInput;
import object.Item_Iron_Sword;
import object.Item_Wooden_Shield;
import object.MasterObject;
import skill.Bullet;
import skill.Ice_Missile;
import skill.PoisonSpit;
import skill.Projectile;

public class Player extends Character{

	KeyInput key;
	
	public int screenX;
	public int screenY;
	
	boolean right = true;
	int FrameCounter = 0;
	int AnimCounter = 0;
	int AttackCooldown = 0;
	public int gold;
	public int spawnX,spawnY;
	public boolean debugmode;
	public character.Character currentInteractNPC;
	
	public ArrayList<MasterObject> inventory = new ArrayList<>();
	public final int InventorySize = 42;
	public int skill;
	public boolean skillusing = false;

	private int skillcounter;

	public int WPindex;
	public int shieldindex;

	public boolean parrying = false;
	private int parrycounter = 0;

	public boolean gun;
	
	public Player(GamePanel gp, KeyInput key) {
		super(gp);
		
		this.gp = gp;
		this.key = key;
		
		
		
		//Middle of the screen
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		//Collision setting
		collisionBox = new Rectangle();
		collisionBox.x = 12;
		collisionBox.y = 16;
		collisionDefaultX = collisionBox.x;
		collisionDefaultY = collisionBox.y;
		collisionBox.width = 24;
		collisionBox.height = 32;
		
		//Melee setting
		//attackBox.width = 36;
		//attackBox.height = 36;
		
		setDefaultValues();
		
		//Set Image
		/*
		graphic.up1 = graphic.setup("/player/back_1");
		graphic.up2 = graphic.setup("/player/back_2");
		graphic.down1 = graphic.setup("/player/idle_2");
		graphic.down2 = graphic.setup("/player/idle_3");
		graphic.left1 = graphic.setup("/player/idle_L");
		graphic.left2 = graphic.setup("/player/idle_L_2");
		graphic.right1 = graphic.setup("/player/idle_R");
		graphic.right2 = graphic.setup("/player/idle_R_2");
		graphic.idle1 = graphic.setup("/player/idle1");
		graphic.idle2 = graphic.setup("/player/idle_2");
		*/
		graphic.up1 = graphic.setup("/player/duckboy_B1");
		graphic.up2 = graphic.setup("/player/duckboy_B2");
		graphic.down1 = graphic.setup("/player/duckboy_1");
		graphic.down2 = graphic.setup("/player/duckboy_2");
		graphic.left1 = graphic.setup("/player/duckboy_L1");
		graphic.left2 = graphic.setup("/player/duckboy_L2");
		graphic.right1 = graphic.setup("/player/duckboy_R1");
		graphic.right2 = graphic.setup("/player/duckboy_R2");
		graphic.idle1 = graphic.setup("/player/duckboy_1");
		graphic.idle2 = graphic.setup("/player/duckboy_2");
		
		graphic.gunImageUp = graphic.setup("/object/Sniper_up");
		graphic.gunImageDown = graphic.setup("/object/Sniper_down");
		graphic.gunImageL = graphic.setup("/object/Sniper_Left");
		graphic.gunImageR = graphic.setup("/object/Sniper");
		graphic.getAtkImage("/player");
	}
	//Spawn default information
	public void setDefaultValues() {
		//Starting Spawn Location
		spawnX = worldX = gp.tileSize*25;
		spawnY = worldY = gp.tileSize*25;
		speed = 6;	//move 4 pixel each frame
		direction = "down"; //default spawn direction
		
		//Stat
		lv = 1;
		MaxHP = 1000;
		HP = 1000;
		MaxMP = 200;
		MP = 120;
		ATK = 5;
		DEF = 0;
		exp = 0;
		NextLvexp = 5;
		gold = 50000;
		OnhandWP = new Item_Iron_Sword(gp);
		Shield = new Item_Wooden_Shield(gp);
		ATK += getGearStat("Weapon");
		DEF += getGearStat("Shield");
		//attackBox.width = getGearStat("Range");
		//attackBox.height = getGearStat("Range");
		setItem();
	}
	public int getGearStat(String type) {
		switch(type) {
		case"Weapon":
			return OnhandWP.ATK;
		case"Shield":
			return Shield.DEF;
		case"Range":
			return OnhandWP.range;
		}
		return 0;
	}

	//update player location
	public void update() {
		//Mana generation
		//System.out.println("row"+worldX/gp.tileSize);
		//System.out.println("col"+worldY/gp.tileSize);
		if(MP < 0) {
			MP = 0;
		}
		if(MP < 200) {
			FrameCounter++;
			if(FrameCounter == 20) {
				MP++;
				FrameCounter = 0;
			}
		}
		if(key.up == true ||key.down == true ||key.right == true||key.left == true ||key.interactKey == true) {
			if(key.up == true) {
			direction = "up";
		}
		else if(key.down == true) {
			direction = "down";
		}
		else if(key.left == true) {
			direction = "left";	
		}
		else if(key.right == true) {
			direction = "right";
		}
		/*
		if(key.up == true && key.right == true) {
			direction = "upright";
		}
		if(key.up == true && key.left == true) {
			direction = "upleft";
		}
		if(key.down == true && key.right == true) {
			direction = "downright";
		}
		if(key.down == true && key.left == true) {
			direction = "downleft";
		}
		*/
		//Collision checking	
		collisionOn = false;
		gp.Colchecker.checkTile(this);
		
		//Check entity Collision
		int objIndex = gp.Colchecker.CheckObject(this, true);
		pickupObject(objIndex);
		
		//Check NPC/Mob Collision
		int npcIndex = gp.Colchecker.checkCharacter(this, gp.npc);
		interactNPC(npcIndex);
		
		//int mobIndex = gp.Colchecker.checkCharacter(this, gp.mob);
		//interactNPC(mobIndex);
		
		graphic.updateDirection(this, 10, 2);	
		}
		if(HP <= 0) {
			gp.GameState = gp.gameoverState;
		}
		//System.out.println(direction);
		//SKILL
		if(skill == 1) {
			if(MP < 20) {
				
			}else {
				MP -= 20;
				if(skillusing == true) {
				skillcounter++;
				if(skillcounter == 1) {
					Projectile ice = new Ice_Missile(gp);
					ice.set(worldX,worldY,direction,this);
				    gp.projectileList.add(ice);
				    
				    skillcounter = 0;
				    skillusing = false;
				    }	
				}
			}
		}
		if(skill == 2) {
			gun = true;
		}
			
	}
	
	//Effect when pick up Items 
	private void pickupObject(int i) {
		// TODO Auto-generated method stub
		if(i != 999) {
			//i not 999 mean character just touch an object -> pick it up and it disappear on ground
			//String objectName = gp.obj[i].name;
			if(Obtainable(gp.obj[gp.currentMap][i]) == true) {
				if(gp.obj[gp.currentMap][i].type == "coin") {
					gp.obj[gp.currentMap][i].interact(i);
					gp.ui.addMessage("+ " + gp.obj[gp.currentMap][i].amount +" "+ gp.obj[gp.currentMap][i].name);
				}else {
					//inventory.add(gp.obj[gp.currentMap][i]);
					gp.ui.addMessage("+ " + gp.obj[gp.currentMap][i].name);
				}
			}
			gp.obj[gp.currentMap][i] = null;
		}	
	}
	//Search item function(for stacking purposes) through name
	public int searchItem(String name) {
		int itemIndex = 999;
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).name.equals(name)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}
	public boolean Obtainable(MasterObject item) {
		boolean canObtain = false;
		if(item.stackable == true) {
			int index = searchItem(item.name);
			if(index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			}
			else { // new item need to check vacancy
				if(inventory.size() != InventorySize) {
					if(item.type == "coin") {
						canObtain = true;
					}else {
						inventory.add(item);
					    canObtain = true;
					}			
				}
			}
		}else {
			if(inventory.size() != InventorySize) {
				if(item.type == "coin") {
					canObtain = true;
				}else {
					inventory.add(item);
				    canObtain = true;
				}	
			}
		}
		return canObtain;
	}
	public void setItem() {
		inventory.add(OnhandWP);
		inventory.add(Shield);	
	}
	void parry(Graphics2D g2) {
		if(parrying == true) {
			parrycounter++;
			if(parrycounter < 15) {
				g2.drawImage(Shield.image, screenX, screenY, 48,48,null);
				
			}else {
				parrycounter = 0;
				parrying = false;
			}
		}
	}
	void attacking(Graphics2D g2) {
		OnhandWP.effect(this);
		Shield.effect(this);
		//Move collisionBox to the attack direction( attack Box location )
		attackBox.width = getGearStat("Range");
		attackBox.height = getGearStat("Range");		
		//Save current WorldX,Y,collision Box size
		int currentWorldX = worldX;
		int currentWorldY = worldY;
		int collisionBoxW = collisionBox.width;
		int collisionBoxH = collisionBox.height;
		
		switch(direction) {
		case"up":   worldY -= attackBox.height; break;
		case"down": worldY += attackBox.height; break;
		case"left": worldX -= attackBox.width; break;
		case"right":worldX += attackBox.width; break;
		}
		
		//collision Box move to attack Box location
		collisionBox.width = attackBox.width;
		collisionBox.height = attackBox.height;
		//
		int monsterIndex = gp.Colchecker.checkCharacter(this, gp.npc);
		//graphic.drawCollision(g2, screenX+collisionBox.x, screenY+collisionBox.y, collisionBox.width,collisionBox.height);
		if(monsterIndex != 999) {
			gp.npc[gp.currentMap][monsterIndex].hitted(ATK,monsterIndex,OnhandWP.knockbackpower); //deal dmg
			//Get Weapon Effect
		}
		else {
		}
		//restore original collision setting
		worldX = currentWorldX;
		worldY = currentWorldY;
		collisionBox.width = collisionBoxW;
		collisionBox.height = collisionBoxH;
	}
	public void shooting() {
		Projectile bullet = new Bullet(gp);
		bullet.set(worldX,worldY,direction,this);
	    gp.projectileList.add(bullet);
	}
	public void MeleeAttack(Graphics2D g2) {
		if(attacking == true) {
		switch(direction) {
        case "up":
    			AnimCounter++;
    			if(gun == true) {
    				if(AnimCounter == 10) {
    					shooting();
    					AnimCounter = 0;
    				}
				}else {
					if(AnimCounter == 1) {
	    				attacking(g2);	
					}
	    			if(AnimCounter <= 5) {
	    				//graphic.attack(g2,7,this,-24,-72);
	    				graphic.SpecialAtkFX(g2,OnhandWP.upFX,this,-24,-72);
	    			}
	    			if(AnimCounter > 5 && AnimCounter < 15) {
	    				graphic.attack(g2,8,this,-24,-72);
	    			}
	    			if(AnimCounter >= 15 && AnimCounter < 20) {
	    				graphic.attack(g2,9,this,-24,-72);
	    			}
	    			if(AnimCounter >= 20) {
	    				AnimCounter = 0;
	    				attacking = false;
	    			}
				}
			break;
		case "down":
    			AnimCounter++;
    			if(gun == true) {
    				if(AnimCounter == 10) {
    					shooting();
    					AnimCounter = 0;
    				}
    			}else {
    				if(AnimCounter == 1) {
    			    	
    					attacking(g2);
   
				}
    			if(AnimCounter <= 5) {
    				//graphic.attack(g2,1,this,-24,15);
    				graphic.SpecialAtkFX(g2,OnhandWP.downFX,this,-24,15);
    			}
    			if(AnimCounter > 5 && AnimCounter < 15) {
    				graphic.attack(g2,2,this,-24,15);
    				    			}
    			if(AnimCounter >= 15 && AnimCounter < 20) {
    				graphic.attack(g2,3,this,-24,15);
    			}
    			if(AnimCounter >= 20) {
    				AnimCounter = 0;
    				attacking = false;
    			}
    			}
			break;
		case "left":
    			AnimCounter++;    
    			if(gun == true) {
    				if(AnimCounter == 10) {
    					shooting();
    					AnimCounter = 0;
    				}
    			}else {
    				if(AnimCounter == 1) {
        				
    					attacking(g2);
    		
				}
    			if(AnimCounter <= 5) {
    				//graphic.attack(g2,10,this,-68,-24);
    				graphic.SpecialAtkFX(g2,OnhandWP.leftFX,this,-68,-24);
    			}
    			if(AnimCounter > 5 && AnimCounter < 15) {
    				graphic.attack(g2,11,this,-68,-24);
    				
    			}
    			if(AnimCounter >= 15 && AnimCounter < 20) {
    				graphic.attack(g2,12,this,-68,-24);
    			}
    			if(AnimCounter >= 20) {
    				AnimCounter = 0;
    				attacking = false;
    			}
    			}
    			
			break;
		case "right":
    			AnimCounter++;
    			if(gun == true) {
    				if(AnimCounter == 10) {
    					shooting();
    					AnimCounter = 0;
    				}
    			}else {
    				if(AnimCounter == 1) {
        				
    					attacking(g2);
    				
				}
    			if(AnimCounter <= 5) {
    				//graphic.attack(g2,4,this,15,-24);
    				graphic.SpecialAtkFX(g2,OnhandWP.rightFX,this,15,-24);
    			}
    			if(AnimCounter > 5 && AnimCounter < 15) {
    				graphic.attack(g2,5,this,15,-24);
    				
    			}
    			if(AnimCounter >= 15 && AnimCounter < 20) {
    				graphic.attack(g2,6,this,15,-24);
    			}
    			if(AnimCounter >= 20) {
    				AnimCounter = 0;
    				attacking = false;
    			}
    			}
    			
			break;
		    }
		}
	}
	public void TookDMG(int dmg) {
		if(parrying != true) {
			float dmgpost = 0;
			dmgpost = (float)dmg / (1+((float)DEF / 100));//DEF equation
			HP -= dmgpost;
			if(HP - dmgpost < 0) {
				HP = 0;
			}
		}else {
			gp.ui.addMessage("parry successfully");
		}
	}
	private void interactNPC(int i) {
		if(i != 999) {
			if(gp.key.interactKey == true) {
				//gp.Event.interactOnKey(i);
				gp.npc[gp.currentMap][i].interact();
			}else {
				gp.npc[gp.currentMap][i].contact(this);
			}
			gp.key.interactKey = false;
		}
	}
	//Update player image/animation
	public void draw(Graphics2D g2) {
		//System.out.println(AnimCounter);
		//System.out.println(graphic.spriteNum);
		
		graphic.drawPlayer(this, g2);
		if(gp.player.debugmode == true) {
			graphic.drawDebug(gp,g2, this);
		}
		//graphic.drawCollision(g2, screenX+collisionBox.x, screenY+collisionBox.y, collisionBox.width,collisionBox.height);
		//graphic.drawName(this, g2, "Lv."+ lv, gp, 15, Color.WHITE);
		MeleeAttack(g2);
		parry(g2);
		if(gp.GameState == gp.statState) {
			graphic.drawStatHUD(gp,g2);
			graphic.drawInventory(gp, g2, true);
		}
		if(gp.GameState == gp.tradeState) {
			graphic.drawTraderInventory(gp, g2);
		}
		if(gun == true) {
			graphic.SetGunDirection(g2, direction, screenX, screenY);
		}
	}
	public void LevelUp() {
		if(exp >= NextLvexp) {
			lv++;
			NextLvexp = NextLvexp*2;
			MaxHP += 100;
			MaxMP += 10;
		}
	}
	public void selectItem() {
		int itemIndex = graphic.getItemIndexSlot(7,graphic.slotCol,graphic.slotRow);
		if(itemIndex < inventory.size()) {
			MasterObject selectedItem = inventory.get(itemIndex);
			if(selectedItem.type == "weapon") {
				OnhandWP = selectedItem;
				WPindex = itemIndex;
				ATK = getGearStat("Weapon");			
			}
			if(selectedItem.type == "shield") {
				Shield = selectedItem;
				shieldindex = itemIndex;
				DEF = getGearStat("Shield");
			}
			if(selectedItem.type == "consumable") {
				if(selectedItem.amount > 1) {
					selectedItem.interact(itemIndex);
					selectedItem.amount --;
				}else {
					selectedItem.interact(itemIndex);
					inventory.remove(itemIndex);
				}
			}
		}
	}
	public void respawn() {
		gp.GameState = gp.playState;
		HP = MaxHP;
		MP = MaxMP;
		worldX = spawnX;
		worldY = spawnY;
		gp.ui.i = 0;
		gp.reset();
	}
	public void restart() {
		gp.GameState = gp.playState;
		HP = MaxHP;
		MP = MaxMP;
		worldX = gp.tileSize*15;
		worldY = gp.tileSize*15;
		gp.ui.i = 0;
		inventory.clear();
		gp.restart();
		inventory.add(Shield);
		inventory.add(OnhandWP);
	}
	public void BuyItem() {
		int itemIndex = graphic.getItemIndexSlot(4,graphic.traderslotCol,graphic.traderslotRow);
		int i;
		if(itemIndex < currentInteractNPC.inventory.size()) {	
			if(gold - currentInteractNPC.inventory.get(itemIndex).price >= 0) {
				if(Obtainable(currentInteractNPC.inventory.get(itemIndex)) == true) {
					gold -= currentInteractNPC.inventory.get(itemIndex).price;
				}
			}
		}
	}
	public void SellItem() {
		int itemIndex = graphic.getItemIndexSlot(7,graphic.slotCol,graphic.slotRow);
		if(itemIndex < inventory.size()) {
			if(inventory.get(itemIndex) == OnhandWP || inventory.get(itemIndex) == Shield) {
				//do nothing
			}else {
				if(inventory.get(itemIndex).amount > 1) {
					inventory.get(itemIndex).amount --;
				}else {
					inventory.remove(itemIndex);
				}
				gold += inventory.get(itemIndex).price/10;
			}
		}
	}
}
