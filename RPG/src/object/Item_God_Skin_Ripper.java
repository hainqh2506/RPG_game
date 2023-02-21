package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import skill.Bloodslash;
import skill.Projectile;

public class Item_God_Skin_Ripper extends MasterObject{
    GamePanel gp;
    Projectile slash;
    
	public Item_God_Skin_Ripper(GamePanel gp) {
		
		this.gp = gp;
		name = "God Skin Ripper";
		downFX = "slash1";
		rightFX = "slash4";
		upFX = "slash7";
		leftFX = "slash10";
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/God_Skin_Ripper.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/God_Skin_Ripper.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		ATK = 500;
		range = 42;
		type = "weapon";
		discription = "A cursed weapon used by\n acient demon. Got it's \n name through killing\n mutiple God";
	}
}
