package overlay_object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Dungeon_Entrance extends MasterOverlay{
	
	public Dungeon_Entrance() {
		try {
		    image = ImageIO.read(getClass().getResourceAsStream("/overlay/Entrance.png"));
			image = utility.scaleImage(image,1024,1024);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
