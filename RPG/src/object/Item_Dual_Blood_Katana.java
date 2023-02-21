package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import skill.Bloodslash;
import skill.Projectile;

public class Item_Dual_Blood_Katana extends MasterObject{
    GamePanel gp;
    Projectile slash;
    
	public Item_Dual_Blood_Katana(GamePanel gp) {
		
		this.gp = gp;
		downFX = "slashblood1";
		rightFX = "slashblood2";
		upFX = "slashblood3";
		leftFX = "slashblood4";
		name = "Dual River of Blood";
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/Dual_Blood_Katana.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/Dual_Blood_Katana.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		ATK = 5000;
		range = 60;
		type = "weapon";
		discription = "Ancient cursed weapon. \nComsume user blood to \nchanneling energy.";
	}
	public void effect(character.Character c) {
		if(c.MP > 10) {
			Projectile slash = new Bloodslash(gp);
			gp.projectileList.add(slash);
			gp.projectileList.add(slash);
			slash.set(gp.player.worldX,gp.player.worldY,gp.player.direction,gp.player);
			c.CostMP(10);
		}
		c.TookDMG(100);
	}

}
