package character;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class flame extends Monster{
	
	public flame(GamePanel gp) {
		super(gp);
		MaxHP = 500;
		HP = 500;
		name = "flame";
		lv = 15; 
		direction = "down";
		speed = 1;
		ATK = 80;
		
		graphic.getImageMonster("flame");
	}
}