package overlay_object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Hut extends MasterOverlay{
	
	public Hut() {
		try {
		    image = ImageIO.read(getClass().getResourceAsStream("/overlay/Cabin.png"));
			image = utility.scaleImage(image,200,200);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
