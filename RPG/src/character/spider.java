package character;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

public class spider extends Monster{

	public spider(GamePanel gp) {
		super(gp);
		MaxHP = 800;
		HP = 800;
		name = "spider";
		lv = 2;
		direction = "down";
		speed = 2;
		ATK = 80;
		
		graphic.getImageMonster("spider");
	}
} 
