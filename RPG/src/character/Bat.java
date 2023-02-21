package character;

import main.GamePanel;

public class Bat extends Monster{

	public Bat(GamePanel gp) {
		super(gp);
		MaxHP = 200;
		HP = 200; 
		direction = "down";
		name = "Bat";
		lv = 16;
		speed = 3;
		ATK = 50;
		
		graphic.getImageMonster("Bat");
	}
}
