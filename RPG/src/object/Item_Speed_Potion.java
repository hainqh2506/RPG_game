package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Item_Speed_Potion extends MasterObject{
	
	GamePanel gp;
	
	public Item_Speed_Potion(GamePanel gp) {
		
		this.gp = gp;
		price = 5000;
		stackable = true;
		name = "Speed Potion";
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/Speed_Potion.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/Speed_Potion.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		type = "consumable";
		discription = "Use to boost your walk speed";
	}
	public void interact(int i) {
		gp.player.speed += 2;
		gp.ui.showMessage("speed +2");
	}

}
