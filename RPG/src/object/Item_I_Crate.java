package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Item_I_Crate extends Item_Interactable{

	public Item_I_Crate(GamePanel gp) {
		super(gp);
		item = false;
		// TODO Auto-generated constructor stub
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/object/crate.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		destructible = true;
	}

}
