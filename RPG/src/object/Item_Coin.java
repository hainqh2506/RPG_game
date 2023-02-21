package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Item_Coin extends MasterObject{
	
	GamePanel gp;
	
	public Item_Coin(GamePanel gp ) {
		this.gp = gp;
		name = "Coin";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/object/coin.png"));
			utility.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		type = "coin";
	}
	public void interact(int i) {
		int amount = (int) ((Math.random() * (1000 - 10)) + 10);
		this.amount = amount;
		gp.player.gold += amount;
	}
}
