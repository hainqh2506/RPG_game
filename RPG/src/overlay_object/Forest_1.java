package overlay_object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Forest_1 extends MasterOverlay{
	
	public Forest_1() {
		try {
		    image = ImageIO.read(getClass().getResourceAsStream("/overlay/Forest_1.png"));
			image = utility.scaleImage(image,800,800);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
