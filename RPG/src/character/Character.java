package character;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import object.MasterObject;

public class Character {
	
	public int worldX,worldY;
	//public int screenX;
	//public int screenY;
	
	public String direction;
	String name;
	public Rectangle collisionBox = new Rectangle(0,0,48,48);
	public Rectangle attackBox = new Rectangle(0,0,0,0);
	public int collisionDefaultX ,collisionDefaultY;
	public boolean collisionOn = false;
	public boolean attacking = false;
	public ArrayList<MasterObject> inventory = new ArrayList<>();
	public boolean onPath = false;
	
	//public boolean PlayerIn = false;
	GamePanel gp;
	
	//Graphic
	public PlayerGraphic graphic = new PlayerGraphic();
	public ItemPicker itemPicker;
	//Stat	
	public int speed;
	public int MaxHP;
	public int HP;
	public int MaxMP;
	public int MP;
	public int ATK;
	public int DEF;
	public int Stamina;
	public int lv;
	public int exp;
	public int NextLvexp;
	public MasterObject OnhandWP;
	public MasterObject Shield;
	int debug = 0;
	
	public Character(GamePanel gp) {
		this.gp = gp;
		itemPicker = new ItemPicker(gp);
	}
	public void setAction() {
		
	}
	public void speak() {
		
	}
	public void interact() {
		
	}
	public void update() {
		
	}
	public void draw(Graphics2D g2) {
		
	}
	public void contact(Character c) {
		
	}
	public void hitted(int dmg,int i,int knockbackpower) {
		
	}
	public void TookDMG(int i) {
		
	}
	public void CostMP(int i) {
		MP -= i; 
		if(MP < 0) {
			MP = 0;
		}
	}
	public void DropItem(MasterObject item) {
		for(int i = 0; i < gp.obj[1].length;i++) {
			if(gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = item;
				gp.obj[gp.currentMap][i].worldX = worldX; 
			    gp.obj[gp.currentMap][i].worldY = worldY;
			    break;
			}
		}	
	}
	public void DropItem(MasterObject item,int x,int y) {
		for(int i = 0; i <gp.obj[1].length;i++) {
			if(gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = item;
				gp.obj[gp.currentMap][i].worldX = x; 
			    gp.obj[gp.currentMap][i].worldY = y;
			    break;
			}
		}	
	}
	public void CheckDrop(int common,int standard,int rare,int superior,int highend,int exotic) {
        int i = new Random().nextInt(100)+1;
		if(i < common) {
			DropItem(itemPicker.picker("common"));
		}
		if(i >= common && i < standard) {
			DropItem(itemPicker.picker("standard"));
		}
		if(i >= standard && i < rare ) {
			DropItem(itemPicker.picker("rare"));
		}
		if(i >= rare && i < superior ) {
			DropItem(itemPicker.picker("superior"));
		}
		if(i >= superior && i < highend ) {
			DropItem(itemPicker.picker("high-end"));
		}
		if(i >= highend && i <= exotic ) {
			DropItem(itemPicker.picker("exotic"));
		}
	}
	public void checkDistance(int distance) { //Compare to Player
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance)/gp.tileSize;
		
		if(onPath == false && tileDistance < distance) {
			int i = new Random().nextInt(100)+1; //50% 
			if(i > 50) {
				onPath = true;
			}
		}
		if(onPath == true && tileDistance > 20) {
			onPath = false;
		}
	}
	public void searchPath(int goalCol, int goalRow) {
		int startCol = (worldX + collisionBox.x)/gp.tileSize;
		int startRow = (worldY + collisionBox.y)/gp.tileSize;
		
		gp.pf.setNodes(startCol, startRow, goalCol, goalRow, this);
		
		if(gp.pf.search() == true) {
			//next world X,Y
			int nextX = gp.pf.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pf.pathList.get(0).row * gp.tileSize;
			
			int cLeftX = worldX + collisionBox.x;
			int cRightX = worldX + collisionBox.x + collisionBox.width;
			int cTopY = worldY + collisionBox.y;
			int cBottomY = worldY + collisionBox.y + collisionBox.height;
			
			if(cTopY > nextY && cLeftX >= nextX && cRightX < nextX + gp.tileSize) {
				direction = "up";
				debug = 1;

			}else if(cTopY < nextY && cLeftX >= nextX && cRightX < nextX + gp.tileSize) {
				direction = "down";
				debug = 2;

			}else if(cTopY >= nextY && cBottomY < nextY + gp.tileSize) {
				if(cLeftX > nextX) {
					direction = "left";
					debug = 3;

				}
				if(cLeftX < nextX) {
					direction = "right";
					debug = 4;

				}
			}
			else if(cTopY > nextY && cLeftX > nextX) {
				//up or left
				direction = "up";
				debug = 5;
				gp.Colchecker.checkTile(this);
				gp.Colchecker.checkPlayer(this);
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(cTopY > nextY && cLeftX < nextX) {
				//up or right
				direction = "up";
				debug = 6;
				gp.Colchecker.checkTile(this);
				gp.Colchecker.checkPlayer(this);
				if(collisionOn == true) {
					direction = "right";
				}
			}
			else if(cTopY < nextY && cLeftX > nextX) {
				//down or left
				direction = "down";
				debug = 7;
				gp.Colchecker.checkTile(this);
				gp.Colchecker.checkPlayer(this);

				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(cTopY < nextY && cLeftX < nextX) {
				//down or right
				direction = "down";
				debug = 8;
				gp.Colchecker.checkTile(this);
				gp.Colchecker.checkPlayer(this);
				if(collisionOn == true) {
					direction = "right";
				}
			}
			if(onPath == true) {
			}
			/*
			int nextCol = gp.pf.pathList.get(0).col;
			int nextRow = gp.pf.pathList.get(0).row;
			if(nextCol == goalCol && nextRow == goalRow) {
				onPath = false;
			}
			*/
		}
	}
}
