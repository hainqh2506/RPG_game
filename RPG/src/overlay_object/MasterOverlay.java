package overlay_object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Utility;

public class MasterOverlay {
	
	public BufferedImage image;
	public int worldX,worldY;
	public Utility utility = new Utility();
	
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		g2.drawImage(image,screenX,screenY,null);	
	}

}
