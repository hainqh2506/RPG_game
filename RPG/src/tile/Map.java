package tile;

import main.GamePanel;

public class Map {
	GamePanel gp;
	public TileManager tile;
	
	public Map(GamePanel gp) {
		this.gp = gp;
		tile = new TileManager(gp);
	}
	
	public void loadMap(String name,int mapIndex) {
		tile.loadMap("/map/"+ name,mapIndex);
	}
	public void switchmap(int mapIndex) {
		gp.currentMap = mapIndex;
	}

}
