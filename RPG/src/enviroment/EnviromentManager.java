package enviroment;

import java.awt.Graphics2D;

import main.GamePanel;

public class EnviromentManager {
	GamePanel gp;
	Lighting lighting;
	
	public EnviromentManager(GamePanel gp) {
		this.gp = gp;
	}
	public void setup() {
		lighting = new Lighting(gp,700);
	}
	public void draw(Graphics2D g2) {
		
		lighting.draw(g2);
	}
}
