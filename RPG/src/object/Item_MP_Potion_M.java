package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Item_MP_Potion_M extends MasterObject{
	
	GamePanel gp;
	
	public Item_MP_Potion_M(GamePanel gp) {
		
		this.gp = gp;
		price = 500;
		stackable = true;
		name = "Medium Mana Potion";
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/MP_Potion_M.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/MP_Potion_M.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		type = "consumable";
		discription = "Use to restore your mana";
	}
	public void interact(int i) {
		gp.player.HP += 300;
		if(gp.player.HP < gp.player.MaxHP) {
		}else {
			gp.player.HP = gp.player.MaxHP;
		}
	}

}
