package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Item_Crusader_Shield extends MasterObject{
	
GamePanel gp;
	
	public Item_Crusader_Shield(GamePanel gp) {
		
		this.gp = gp;
		name = "Wooden Shield";
		price = 250000;
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/Crusader_Shield.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/Crusader_Shield.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		DEF = 25;
		type = "shield";
	}

}
