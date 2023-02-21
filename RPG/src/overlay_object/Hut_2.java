package overlay_object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Hut_2 extends MasterOverlay{
	
	public Hut_2() {
		try {
		    image = ImageIO.read(getClass().getResourceAsStream("/overlay/Hut_2.png"));
			image = utility.scaleImage(image,300,300);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
