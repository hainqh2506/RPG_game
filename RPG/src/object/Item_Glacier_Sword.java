package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import skill.Bloodslash;
import skill.Ice_Missile;
import skill.Projectile;

public class Item_Glacier_Sword extends MasterObject{
    GamePanel gp;
    Projectile slash;
    
	public Item_Glacier_Sword(GamePanel gp) {
		
		this.gp = gp;
		downFX = "slashblood1";
		rightFX = "slashblood2";
		upFX = "slashblood3";
		leftFX = "slashblood4";
		name = "Glacier Sword";
		try {
			InventoryImage = ImageIO.read(getClass().getResourceAsStream("/inventory/Glacier.png"));
			utility.scaleImage(InventoryImage,gp.tileSize,gp.tileSize);
			image = ImageIO.read(getClass().getResourceAsStream("/object/Glacier.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		ATK = 100;
		range = 38;
		type = "weapon";
		discription = "An Ancient cursed weapon. \nComsume user blood to \nchanneling energy.";
	}
	public void effect(character.Character c) {
		if(c.MP > 10) {
			Projectile slash = new Ice_Missile(gp);
			gp.projectileList.add(slash);
			slash.set(gp.player.worldX,gp.player.worldY,gp.player.direction,gp.player);
			c.CostMP(10);
		}
	}

}
