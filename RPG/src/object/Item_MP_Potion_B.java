package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Item_MP_Potion_B extends MasterObject{
	
	GamePanel gp;
	
	public Item_MP_Potion_B(GamePanel gp) {
		
		this.gp = gp;
		price = 1200;
		stackable = true;
		name = "Big Mana Potion";
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/MP_Potion_B.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/MP_Potion_B.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		type = "consumable";
		discription = "Use to restore your mana";
	}
	public void interact(int i) {
		gp.player.HP += 500;
		if(gp.player.HP < gp.player.MaxHP) {
		}else {
			gp.player.HP = gp.player.MaxHP;
		}

	}

}
