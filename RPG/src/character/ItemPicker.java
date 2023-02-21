package character;

import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import object.Item_Blood_Katana;
import object.Item_Cake;
import object.Item_Chicken;
import object.Item_Chocolate;
import object.Item_Cocktail;
import object.Item_Coin;
import object.Item_Crusader_Shield;
import object.Item_Dual_Blood_Katana;
import object.Item_Glacier_Sword;
import object.Item_God_Skin_Ripper;
import object.Item_HP_Potion_B;
import object.Item_HP_Potion_M;
import object.Item_HP_Potion_S;
import object.Item_Iron_Sword;
import object.Item_Katana;
import object.Item_Key;
import object.Item_LightSaber;
import object.Item_Lolipop;
import object.Item_MP_Potion_B;
import object.Item_MP_Potion_M;
import object.Item_MP_Potion_S;
import object.Item_Mushroom;
import object.Item_OrangeJuice;
import object.Item_Ruin_Sword;
import object.Item_Soda;
import object.Item_Speed_Potion;
import object.Item_Wooden_Shield;
import object.MasterObject;

public class ItemPicker {
	ArrayList<MasterObject> common = new ArrayList<>();
	ArrayList<MasterObject> standard = new ArrayList<>();
	ArrayList<MasterObject> rare = new ArrayList<>();
	ArrayList<MasterObject> superior = new ArrayList<>();	
	ArrayList<MasterObject> highend = new ArrayList<>();
	ArrayList<MasterObject> exotic = new ArrayList<>();
	
	GamePanel gp;
	public ItemPicker(GamePanel gp){
		this.gp = gp;
		//Common item
		common.add(new Item_Coin(gp));
		common.add(new Item_Iron_Sword(gp));
		common.add(new Item_Wooden_Shield(gp));
		common.add(new Item_HP_Potion_S(gp));
		common.add(new Item_MP_Potion_S(gp));
		common.add(new Item_Mushroom(gp));
		common.add(new Item_Cocktail(gp));
		//Standard item
		standard.add(new Item_HP_Potion_M(gp));
		standard.add(new Item_MP_Potion_M(gp));
		standard.add(new Item_Katana(gp));
		standard.add(new Item_Chicken(gp));
		standard.add(new Item_Soda(gp));
		//Rare item
		rare.add(new Item_HP_Potion_B(gp));
		rare.add(new Item_MP_Potion_B(gp));
		rare.add(new Item_Ruin_Sword(gp));
		rare.add(new Item_Lolipop(gp));
		rare.add(new Item_OrangeJuice(gp));
		//Superior
		superior.add(new Item_Key(gp));
		superior.add(new Item_Glacier_Sword(gp));
		superior.add(new Item_Speed_Potion(gp));
		superior.add(new Item_Chocolate(gp));
		//High-end
		highend.add(new Item_Crusader_Shield(gp));
		highend.add(new Item_God_Skin_Ripper(gp));
		highend.add(new Item_Cake(gp));
		//Exotic item
		exotic.add(new Item_Blood_Katana(gp));
		exotic.add(new Item_LightSaber(gp));
		exotic.add(new Item_Dual_Blood_Katana(gp));
	}
	public MasterObject pickItem(ArrayList<MasterObject> item) {
		int i = new Random().nextInt(item.size())+0;
        return item.get(i);
	}
	public MasterObject picker(String rarity) {
		switch(rarity) {
		case "common":                      //40%
			return pickItem(common);
		case "standard":                    //25%
			return pickItem(standard);
		case "rare":                        //15%
			return pickItem(rare);
		case "superior":                    //10%
			return pickItem(superior);
		case "high-end":                    //7%
			return pickItem(highend);
		case "exotic":                      //3%
			return pickItem(exotic);
		}
		return null;
	}
}
