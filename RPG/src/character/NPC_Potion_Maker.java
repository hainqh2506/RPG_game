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
import object.Item_MP_Potion_B;
import object.Item_MP_Potion_M;
import object.Item_MP_Potion_S;
import object.Item_Speed_Potion;

public class NPC_Potion_Maker extends Character{
	
	public int ActionCounter;
	String dialog[] = new String[20];
	int dialogIndex = 0;
	boolean speaking = false;
	int dialogcounter = 0;
	//public ArrayList<MasterObject> inventorytrader = new ArrayList<>();
	public final int InventorySize = 16;
	
	public NPC_Potion_Maker(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 0;
		
		graphic.up1 = graphic.setup("/npc/Usope_1",48,48);
		graphic.up2 = graphic.setup("/npc/Usope_2",48,48);
		graphic.down1 = graphic.setup("/npc/Usope_1",48,48);
		graphic.down2 = graphic.setup("/npc/Usope_2",48,48);
		graphic.left1 = graphic.setup("/npc/Usope_1",48,48);
		graphic.left2 = graphic.setup("/npc/Usope_2",72,48);
		graphic.right1 = graphic.setup("/npc/Usope_1",48,48);
		graphic.right2 = graphic.setup("/npc/Usope_2",48,48);
		graphic.idle1 = graphic.setup("/npc/Usope_1",48,48);
		graphic.idle2 = graphic.setup("/npc/Usope_2",48,48);

		setDialog();
		setItem();
	}
	public void setDialog() {
		dialog[0] = "Welcome mate!";
		dialog[1] = "I might have what\nyou needed.";
		dialog[2] = "With reasonable \nprice";
		dialog[3] = "Come and check it\nout";
	}
	public void setItem() {
		inventory.add(new Item_HP_Potion_S(gp));
		inventory.add(new Item_HP_Potion_M(gp));
		inventory.add(new Item_HP_Potion_B(gp));
		inventory.add(new Item_Speed_Potion(gp));
		inventory.add(new Item_MP_Potion_S(gp));
		inventory.add(new Item_MP_Potion_M(gp));
		inventory.add(new Item_MP_Potion_B(gp));
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
