package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import skill.Bloodslash;
import skill.Projectile;

public class Item_Ruin_Sword extends MasterObject{
    GamePanel gp;
    Projectile slash;
    
	public Item_Ruin_Sword(GamePanel gp) {
		
		this.gp = gp;
		name = "Ruin Sword";
		downFX = "slash1";
		rightFX = "slash4";
		upFX = "slash7";
		leftFX = "slash10";
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/Ruin_Sword.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/Ruin_Sword.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		ATK = 300;
		range = 38;
		type = "weapon";
		discription = "Weapon of an old king/n Corrupted by time and /nenemy soul.";
	}
}
