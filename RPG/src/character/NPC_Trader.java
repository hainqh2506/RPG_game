package character;


import java.awt.Graphics2D;
import main.GamePanel;
import object.Item_HP_Potion_B;
import object.Item_HP_Potion_M;
import object.Item_HP_Potion_S;
import object.Item_Key;

public class NPC_Trader extends Character{
	
	public int ActionCounter;
	String dialog[] = new String[20];
	int dialogIndex = 0;
	boolean speaking = false;
	int dialogcounter = 0;
	//public ArrayList<MasterObject> inventorytrader = new ArrayList<>();
	public final int InventorySize = 16;
	
	public NPC_Trader(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 0;
		
		graphic.up1 = graphic.setup("/npc/Trader1",72,72);
		graphic.up2 = graphic.setup("/npc/Trader2",72,72);
		graphic.down1 = graphic.setup("/npc/Trader1",72,72);
		graphic.down2 = graphic.setup("/npc/Trader2",72,72);
		graphic.left1 = graphic.setup("/npc/Trader1",72,72);
		graphic.left2 = graphic.setup("/npc/Trader2",72,72);
		graphic.right1 = graphic.setup("/npc/Trader1",72,72);
		graphic.right2 = graphic.setup("/npc/Trader2",72,72);
		graphic.idle1 = graphic.setup("/npc/Trader1",72,72);
		graphic.idle2 = graphic.setup("/npc/Trader2",72,72);

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
		inventory.add(new Item_Key(gp));
	}
	public void interact() {
		gp.GameState = gp.tradeState;
		speaking = true;
		gp.player.currentInteractNPC = this;
		gp.player.graphic.subState = 0;
	}
	public void update() {
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
