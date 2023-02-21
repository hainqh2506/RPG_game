package character;


import java.awt.Graphics2D;
import main.GamePanel;
import object.Item_Blood_Katana;
import object.Item_God_Skin_Ripper;
import object.Item_HP_Potion_B;
import object.Item_HP_Potion_M;
import object.Item_HP_Potion_S;
import object.Item_Iron_Sword;
import object.Item_Katana;
import object.Item_Key;

public class NPC_Weapon_Smith extends Character{
	
	public int ActionCounter;
	String dialog[] = new String[20];
	int dialogIndex = 0;
	boolean speaking = false;
	int dialogcounter = 0;
	//public ArrayList<MasterObject> inventorytrader = new ArrayList<>();
	public final int InventorySize = 16;
	
	public NPC_Weapon_Smith(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 0;
		
		graphic.up1 = graphic.setup("/npc/Zoro_1",48,48);
		graphic.up2 = graphic.setup("/npc/Zoro_2",48,48);
		graphic.down1 = graphic.setup("/npc/Zoro_1",48,48);
		graphic.down2 = graphic.setup("/npc/Zoro_2",48,48);
		graphic.left1 = graphic.setup("/npc/Zoro_1",48,48);
		graphic.left2 = graphic.setup("/npc/Zoro_2",48,48);
		graphic.right1 = graphic.setup("/npc/Zoro_1",48,48);
		graphic.right2 = graphic.setup("/npc/Zoro_2",48,48);
		graphic.idle1 = graphic.setup("/npc/Zoro_1",48,48);
		graphic.idle2 = graphic.setup("/npc/Zoro_2",48,48);

		setDialog();
		setItem();
	}
	public void setDialog() {
		dialog[0] = "Hello Duck";
		dialog[1] = "I am the best\nsword man in town.";
		dialog[2] = "You can come to me\nfor weapon";
		dialog[3] = "Best resonable price";
	}
	public void setItem() {
		inventory.add(new Item_Iron_Sword(gp));
		inventory.add(new Item_Katana(gp));
		inventory.add(new Item_God_Skin_Ripper(gp));
		inventory.add(new Item_Blood_Katana(gp));
	}
	public void interact() {
		gp.GameState = gp.tradeState;
		speaking = true;
		gp.player.currentInteractNPC = this;
		gp.player.graphic.subState = 0;
	}
	public void update() {
		gp.Colchecker.checkTile(this);
		gp.Colchecker.checkPlayer(this);
		graphic.updateDirection(this,40,2);
	}
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		graphic.drawCharacter(this, g2,gp);
		if(speaking == true) {
			dialogcounter++;
			graphic.drawDialog(g2,dialog[dialogIndex],screenX,screenY);
			if(dialogcounter == 120) {
				dialogIndex++;
				dialogcounter = 0;
			}
			if(dialog[dialogIndex] == null) {
				dialogIndex = 0;
				speaking = false;
			}
		}
	}
}
