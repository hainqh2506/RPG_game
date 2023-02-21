package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import object.Item_Key;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_30;
	Font warning;
	public Boolean messageOn = false;
	public String message = "";
	int messageDuration = 0;//frame counter
	public String currentDialog = "";
	BufferedImage StatBox = null;
	BufferedImage A,D,ENTER;
	ArrayList<String> notice = new ArrayList<>();
	ArrayList<Integer> noticeCounter = new ArrayList<>();
	int counter;
	int spriteNum;
	public int i=0;
	int SelectionCount = 1;
	BufferedImage background,button,pressedbutton;
	
	public UI (GamePanel gp) {
		this.gp = gp;
		arial_30 = new Font("Airal",Font.PLAIN, 30);
		warning = new Font("Airal",Font.BOLD,15);
		Item_Key key = new Item_Key(gp);
		//KeyImage = key.image;
		try {
			StatBox = ImageIO.read(getClass().getResource("/UI/LvCircle.png"));
			A = ImageIO.read(getClass().getResource("/Key/A.png"));
			D = ImageIO.read(getClass().getResource("/Key/D.png"));
			ENTER = ImageIO.read(getClass().getResource("/Key/Enter.png"));
			background = ImageIO.read(getClass().getResource("/UI/background.jpg"));
			button = ImageIO.read(getClass().getResource("/UI/button_grey.png"));
			pressedbutton = ImageIO.read(getClass().getResource("/UI/button_grey_pressed.png"));
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	public void addMessage(String text) {
		notice.add(text);
		noticeCounter.add(0);
		
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_30);
		g2.setColor(Color.white);
		//g2.drawImage(KeyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		//g2.drawString("x " +gp.player.Key,80,60);
		if(gp.GameState == gp.titleState) {
			drawTitle();
		}
		if(gp.GameState == gp.playState) {
			drawHUD();//	HP,MP,Lv
			drawMessage();
		}
		if(gp.GameState == gp.pauseState) {
			drawPauseScreen(); //
			drawHUD();
		}
		if(gp.GameState == gp.statState) {
			drawHUD(); //Inventory
		}
		if(gp.GameState == gp.tradeState) {
			//drawHUD();
		}
		if(gp.GameState == gp.finishState) {
			if(i == 255) {
				//stop count	
				}else {
					i+=3;
				}
			drawFinishScreen(i);
		}
		if(gp.GameState == gp.gameoverState) {
			if(i == 255) {
			//stop count	
			}else {
				i+=3;
			}
			drawGameOver(i);
		}
		if(messageOn == true) {
			g2.setFont(new Font("Teko", Font.PLAIN, 26));
			g2.drawString(message, gp.tileSize*10, gp.tileSize*8);
			
			messageDuration++;
			
			if(messageDuration > 120) {
				messageDuration = 0;
				messageOn = false;
			}
		}
	}
	private void drawTitle() {
		//Title
		g2.drawImage(background, 0, 0,48*32,48*18, null);
		g2.setFont(new Font("x12y16pxMaruMonica", Font.BOLD, 180));
		
		String text = "DUCKBOY ADVENTURE";
		
		int x = 100;
		int y = 240;
		
		g2.setColor(Color.BLACK);
		g2.drawString(text,x+10,y+10);
		g2.setColor(Color.WHITE);
		g2.drawString(text,x,y);
		
		y += 180; 
		g2.setFont(new Font("x12y16pxMaruMonica", Font.BOLD, 60));
		
		//Button
		g2.setColor(new Color(230,146,6));
		g2.drawImage(button, x-15, y+20,  320, 80, null);
		g2.drawImage(button, x-15, y+105,  320, 80, null);
		g2.drawImage(button, x-15, y+190,  320, 80, null);
		//Outline current choice
		//((Graphics2D) g2).setStroke(new BasicStroke(5));
		g2.setColor(new Color(255,205,34));
		if(SelectionCount == 1) {
			//g2.drawRoundRect(x-15, y+25, 400, 70, 10, 10);
			g2.drawImage(pressedbutton, x-15, y+20,  320, 80, null);
		}
		if(SelectionCount == 2) {
			//g2.drawRoundRect(x-15, y+105, 400, 70, 10, 10);
			g2.drawImage(pressedbutton, x-15, y+105,  320, 80, null);
		}
		if(SelectionCount == 3) {
			//g2.drawRoundRect(x-15, y+185, 400, 70, 10, 10);
			g2.drawImage(pressedbutton, x-15, y+190,  320, 80, null);
		}
		//Button's text
		g2.setColor(Color.BLACK);
		text = "NEW GAME";
		y += 80; 
		g2.drawString(text, x, y);
		text = "LOAD GAME";
		y += 85; 		
		g2.drawString(text, x, y);		
		text = "QUIT";
		y += 85;		
		g2.drawString(text, x, y);
		y = 420;
		
		//Version
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("x12y16pxMaruMonica", Font.BOLD, 24));
		g2.drawString("version 1.3.0",1300,820);
		
	}

	private void drawGameOver(int i) {
		// TODO Auto-generated method stub
		g2.setColor(new Color(0,0,0,i)); 
		g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);
		int x;
		int y;
		
		String text;
		g2.setFont(new Font("x12y16pxMaruMonica", Font.PLAIN, 120));
		text = "YOU DIED";
		x = 600;
		y = gp.tileSize * 8;
		g2.setColor(Color.DARK_GRAY);
		g2.drawString(text, x, y);
		g2.setColor(Color.RED);
		g2.drawString(text, x-4, y-4);
		
		if(i == 255) {
			//Instruction
			g2.drawImage(A,30, 750, 35, 35, null);
			g2.drawImage(D,70, 750, 35, 35, null);
			g2.drawImage(ENTER,240, 750, 35, 35, null);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("x12y16pxMaruMonica", Font.PLAIN, 26));
			g2.drawString("Move Cursor", 110, 775);
			g2.drawString("Select", 280, 775);
			
			//Button
			g2.setColor(new Color(230,146,6));
			g2.fillRoundRect(x-145, y+220, 200, 40, 10, 10);
			g2.fillRoundRect(x+275, y+220, 200, 40, 10, 10);
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Teko", Font.PLAIN, 35));
			text = "RESPAWN";
			g2.drawString(text, x-100, y+250);
			text = "EXIT TO TITLE";
			g2.drawString(text, x+310, y+250);
			
			//Outline current choice
			((Graphics2D) g2).setStroke(new BasicStroke(5));
			g2.setColor(new Color(255,205,34));
			if(SelectionCount == 1) {
				g2.drawRoundRect(x-145, y+220, 200, 40, 10, 10);
			}
			if(SelectionCount == 2) {
				g2.drawRoundRect(x+275, y+220, 200, 40, 10, 10);
			}
		}
	}
	private void drawFinishScreen(int i) {
		g2.setColor(new Color(0,0,0,i)); 
		g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);
		int x;
		int y;
		
		String text;
		g2.setFont(new Font("x12y16pxMaruMonica", Font.PLAIN, 120));
		text = "YOU WON";
		x = 600;
		y = gp.tileSize * 8;
		g2.setColor(Color.DARK_GRAY);
		g2.drawString(text, x, y);
		g2.setColor(Color.YELLOW);
		g2.drawString(text, x-4, y-4);
		
		if(i == 255) {
			//Instruction
			g2.drawImage(A,30, 750, 35, 35, null);
			g2.drawImage(D,70, 750, 35, 35, null);
			g2.drawImage(ENTER,240, 750, 35, 35, null);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("x12y16pxMaruMonica", Font.PLAIN, 26));
			g2.drawString("Move Cursor", 110, 775);
			g2.drawString("Select", 280, 775);
			//Text winning
			g2.setFont(new Font("x12y16pxMaruMonica", Font.BOLD, 46));
			g2.drawString("You have successfully saved the village", x-150, y+60);
			//Button
			g2.setColor(new Color(230,146,6));
			g2.fillRoundRect(x-145, y+220, 200, 40, 10, 10);
			g2.fillRoundRect(x+275, y+220, 200, 40, 10, 10);
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Teko", Font.PLAIN, 35));
			text = "RESTART";
			g2.drawString(text, x-100, y+250);
			text = "EXIT TO TITLE";
			g2.drawString(text, x+310, y+250);
			
			//Outline current choice
			((Graphics2D) g2).setStroke(new BasicStroke(5));
			g2.setColor(new Color(255,205,34));
			if(SelectionCount == 5) {
				g2.drawRoundRect(x-145, y+220, 200, 40, 10, 10);
			}
			if(SelectionCount == 6) {
				g2.drawRoundRect(x+275, y+220, 200, 40, 10, 10);
			}
		}
	}

	private void drawMessage() {
		
		int messageX = 1300;
		int messageY = 60;
		g2.setFont(new Font("x12y16pxMaruMonica", Font.BOLD, 22));
		for(int i = 0; i < notice.size(); i++) {
			if(notice.get(i) != null) {
				g2.setColor(Color.BLACK);
				g2.drawString(notice.get(i), messageX+2, messageY+2);
				g2.setColor(Color.WHITE);
				g2.drawString(notice.get(i), messageX, messageY);
				
				int counter =noticeCounter.get(i) + 1; //message Counter ++
				noticeCounter.set(i, counter); //set counter to array
				messageY += 20;
				
				if(noticeCounter.get(i) > 120) {
					notice.remove(i);
					noticeCounter.remove(i);
				}
			}
		}
	}

	//HUD(mp and hp)
	public void drawHUD() {
		//gp.player.HUD(g2);
		PlayerHUD(g2);
		g2.setFont(new Font("x12y16pxMaruMonica", Font.PLAIN, 28));
		g2.drawImage(StatBox,10, 5, 100, 100, null);
		g2.drawString("Lv."+gp.player.lv, 45, 65);
		g2.setColor(Color.BLACK);
		g2.drawString("Lv."+gp.player.lv, 47, 67);
	}
	public void drawPauseScreen() {
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		g2.drawString(text,x,y);
	}
	public void drawDialogScreen() {
		
		//Dialog window size
		int x = gp.tileSize;
		int y = gp.tileSize*12;
		int width = gp.screenWidth - (gp.tileSize*2);
		int height = gp.tileSize*5;
		drawSubWindow(x,y,width,height);
		//Dialog text
		x += gp.tileSize;
		y += gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
		//split line whenever the text have "\n"
		for(String line : currentDialog.split("\n")) {
			g2.drawString(currentDialog, x, y);
			y += 40;
		}
	}
	public void drawSubWindow(int x,int y,int width,int height) {
		//Using Paint to define correct RGB,A is for opacity
		Color c = new Color(30,30,30,200);
		g2.setColor(c);
		g2.fillRect(x, y, width, height);
		
		g2.setColor(c);
		g2.fillRect(gp.tileSize, gp.tileSize*11, gp.tileSize*4, gp.tileSize);
		
		c = new Color(255,255,255);
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(x+5, y+5, width-10, height-10);
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenHeight/2 - length/2;
		return x;

	}
	public void drawCollision(Graphics2D g2,int x,int y,int width,int height) {
		this.g2 = g2;
		g2.setColor(Color.WHITE);
		g2.fillRect(x, y, width, height);
	}
	public void PlayerHUD(Graphics2D g2) {

		//HP bar
		double hpscale = (double)gp.tileSize*10/gp.player.MaxHP;
		double hpbarValue = hpscale*gp.player.HP;
		g2.setColor(new Color(130,0,0,200));
		g2.fillRect(100, 35, gp.tileSize*10, 20);
		g2.setColor(new Color(215,0,0));
		g2.fillRect(100, 35, (int)hpbarValue, 20);
		
		//MP bar
		double mpscale = (double)gp.tileSize*6/gp.player.MaxMP;
		double mpbarValue = mpscale*gp.player.MP;
		g2.setColor(new Color(0,24,83,200));
		g2.fillRect(100, 60, gp.tileSize*6, 10);
		g2.setColor(new Color(14,86,254));
		g2.fillRect(100, 60, (int)mpbarValue, 10);
		g2.setColor(Color.white);
	}
}
