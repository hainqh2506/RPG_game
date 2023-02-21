package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.Utility;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	private boolean drawPath = true;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[100];
		mapTileNum = new int[10][gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/map/Level1.txt",0);
		loadMap("/map/trader_room1.txt",1);
		loadMap("/map/Level0.txt",2);
		loadMap("/map/trader_room1.txt",3);
		loadMap("/map/trader_room1.txt",4);
		loadMap("/map/trader_room1.txt",5);
	}
	
    //get data of each type of Tile
	private void getTileImage() {
		//index,name of picture,collision t/f
		setup(0,"Void",false);
		setup(1,"Stone_Wall",true);
		setup(2,"Stone_Wall_R",true);
		setup(3,"Stone_Wall_L",true);
		setup(4,"Floor_1",false);
		setup(5,"Stone_Wall_Corner_L",true);
		setup(6,"Stone_Wall_Corner_R",true);
		setup(7,"Stone_Wall_Corner_L_out",true);
		setup(8,"Stone_Wall_Corner_R_out",true);
		setup(9,"Stone_Wall_Leak",true);
		setup(10,"Water_Hole",true);
		setup(11,"Water_Hole_2",true);
		
		//Unused(old tile set)
		setup(12,"Floor_2",false);
		setup(13,"Floor_2_L",false);
		setup(14,"Floor_2_R",false);
		setup(15,"Floor_2_Corner_L",false);
		setup(16,"Floor_2_Corner_R",false);
		setup(17,"Floor_2_L_Corner",false);
		setup(18,"Floor_2_R_Corner",false);
		setup(19,"Floor_2_Stone",false);
		setup(20,"Floor_2_Tile",false);
		setup(21,"Floor_3",false);
		setup(22,"Floor_3_Manhole",false);
		setup(23,"Floor_1_Blob",false);
		//
		setup(24,"plank",false);
		setup(25,"Floor_Gray",false);
		setup(26,"Grass_1",false);
		setup(27,"Water",false);
		//Map #2 stone path
		setup(28,"Stone_Path_1",false);
		setup(29,"Stone_Path_2",false);
		setup(30,"Stone_Path_3",false);
		setup(31,"Stone_Path_4",false);
		setup(32,"Grass_2",false);
		setup(33,"Grass_1",true);// collision for house
		//Map#2 fence
		setup(34,"Fence",true);//horizontal fence
		setup(35,"Fence_Vertical",true);
		setup(36,"Fence_Top_L",true);
		setup(37,"Fence_Bottom_L",true);
		setup(38,"Fence_End_L",true);
		setup(39,"Fence_End_R",true);
		setup(40,"Fence_Top_R",true);
		setup(41,"Fence_Bottom_R",true);
		//Map#2 bush
		setup(42,"Bush",true);
		// tile for debugging
		setup(99,"debug",false); 
	}
	
	//Scale texture before hand	
	public void setup(int index,String imagePath,boolean collision) {
		Utility utility = new Utility();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tile/" + imagePath + ".png"));
			tile[index].image = utility.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}catch(IOException e) {
			
		}
	}
	//Load text file into map
	public void loadMap(String map, int mapindex) {
		
		try {
			InputStream is = getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			//read text file 
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				//read each line of the text file
					
			//Split each line into single 0, 1, 0 ,2 ,.....
				while(col < gp.maxWorldCol) {
					//scan and split each line
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[mapindex][col][row] = num;
					col++;
				}
				//when hit the line limit jump to next line 
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
			
		}
		
	}
	//create image
	public void draw(Graphics2D g2) {
		int worldcol = 0;
		int worldrow = 0;
		
		//a bit complex will explain later
		//calculate where is the tile at if the player is the (0,0) of the 2D graph
		while(worldcol < gp.maxWorldCol && worldrow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldcol][worldrow];
			
			int worldX = worldcol * gp.tileSize;
			int worldY = worldrow * gp.tileSize;
			
			//Location on screen -> to tell system where to draw
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//Only create tile that visible on screen
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image,screenX,screenY,null);
				if(gp.player.debugmode == true) {
					g2.drawRect(screenX, screenY, 48, 48);
				}
			}
			worldcol++;
			if(worldcol == gp.maxWorldCol) {
				worldcol = 0;			
				worldrow++;
			}
			
		}
		if(gp.player.debugmode == true) {
			g2.setColor(new Color(255,0,0,70));
			for(int i = 0; i < gp.pf.pathList.size(); i++) {
				int worldX = gp.pf.pathList.get(i).col * gp.tileSize;
				int worldY = gp.pf.pathList.get(i).row * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				g2.fillRect(screenX,screenY,48,48);
			}
		}
		
	}

}
