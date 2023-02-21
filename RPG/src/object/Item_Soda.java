package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Item_Soda extends MasterObject{
	
	GamePanel gp;
	
	public Item_Soda(GamePanel gp) {
		
		this.gp = gp;
		price = 600;
		stackable = true;
		name = "Soda";
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/Soda.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/Soda.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		type = "consumable";
		discription = "Restore HP over time";
	}
	public void interact(int i) {
		gp.player.HP += 500;
		if(gp.player.HP < gp.player.MaxHP) {
		}else {
			gp.player.HP = gp.player.MaxHP;
		}

	}

}
