package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import skill.Bloodslash;
import skill.Projectile;

public class Item_Katana extends MasterObject{
    GamePanel gp;
    Projectile slash;
    
	public Item_Katana(GamePanel gp) {
		
		this.gp = gp;
		name = "Katana";
		downFX = "slash1";
		rightFX = "slash4";
		upFX = "slash7";
		leftFX = "slash10";
		knockbackpower = 4;
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/Katana.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/Katana.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		ATK = 150;
		range = 42;
		type = "weapon";
		discription = "A traditional Japanese weapon.\n Famous for it's sharpness \n and light weight";
	}
}
