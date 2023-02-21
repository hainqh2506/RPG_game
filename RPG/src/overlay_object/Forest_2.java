package overlay_object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Forest_2 extends MasterOverlay{
	
	public Forest_2() {
		try {
		    image = ImageIO.read(getClass().getResourceAsStream("/overlay/Forest_2.png"));
			image = utility.scaleImage(image,800,800);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
