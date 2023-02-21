package object;

import main.GamePanel;

public class Item_Interactable extends MasterObject{
	GamePanel gp;
	public boolean destructible = false;
	
	public Item_Interactable(GamePanel gp) {
		this.gp = gp;
	}

}
