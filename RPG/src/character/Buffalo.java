package character;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

public class Buffalo extends Monster{

	public Buffalo(GamePanel gp) {
		super(gp);
		MaxHP = 1200;
		HP = 1200;
		name = "Buffalo"; 
		lv = 20;
		direction = "down";
		speed = 1;
		ATK = 150;
		
		graphic.getImageMonster("Bufalo");
	}
}
