package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import skill.Bloodslash;
import skill.Projectile;

public class KeyInput implements KeyListener{
	public boolean up,down,left,right,idle,interactKey;
	GamePanel gp;
	int counter;
	int pressed;
	
	public KeyInput(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// Action when key pressed
		//check();
		int code = e.getKeyCode();//get code of pressed key
		//Play State
		if(gp.GameState == gp.playState) {
			if(code == KeyEvent.VK_W) {
				up = true;
				idle = false;
			}
			if(code == KeyEvent.VK_S) {
				down = true;
				idle = false;
			}
			if(code == KeyEvent.VK_A) {
				left = true;
				idle = false;
			}
			if(code == KeyEvent.VK_D) {
				right = true;
				idle = false;
			}
			if(code == KeyEvent.VK_ESCAPE) {
				gp.GameState = gp.pauseState;
			}
			if(code == KeyEvent.VK_F) {
				interactKey = true;
			}
			if(code == KeyEvent.VK_B) {
				gp.GameState = gp.statState;
			}
			if(code == KeyEvent.VK_T){
				if(pressed == 0) {
					pressed = 1;
					gp.player.debugmode = true;
				}
				if(pressed == 2) {
					gp.player.debugmode = false;
					pressed = 3;
				}
			}
			if(code == KeyEvent.VK_SPACE) {
				if(gp.player.attacking == false) {
					gp.player.attacking = true;
				}
			}
			//PARRY
			if(code == KeyEvent.VK_SHIFT) {
				gp.player.parrying = true;
			}
			//SKILL SET
			if(code == KeyEvent.VK_1) {
				if(gp.player.skillusing == false) {
					gp.player.skill = 1;
					gp.player.skillusing = true;
				}
			}
			if(code == KeyEvent.VK_2) {
				if(gp.player.skillusing == false) {
					gp.player.gun = true;

				}
			}
			
		}
		//Pause State
		else if(gp.GameState == gp.pauseState || gp.GameState == gp.statState) {
			if(code == KeyEvent.VK_ESCAPE) {
				gp.GameState = gp.playState;
			}
		}
		//In dialog State
		if(gp.GameState == gp.dialogState) {
			if(code == KeyEvent.VK_SPACE) {
				gp.GameState = gp.playState;
			}
		}
		
		//Trading State
		if(gp.GameState == gp.tradeState) {
			if(gp.player.graphic.subState == 0) {
				if(code == KeyEvent.VK_W) {
					gp.player.graphic.SelectionCount--;
					if(gp.player.graphic.SelectionCount < 1) {
						gp.player.graphic.SelectionCount = 3;
					}
				}
				if(code == KeyEvent.VK_S) {
					gp.player.graphic.SelectionCount++;
					if(gp.player.graphic.SelectionCount > 3) {
						gp.player.graphic.SelectionCount = 1;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					if(gp.player.graphic.SelectionCount == 1) {//buy
						gp.player.graphic.subState = 1;
					}
	                if(gp.player.graphic.SelectionCount == 2) {//sell
	                	gp.player.graphic.subState = 2;
					}
	                if(gp.player.graphic.SelectionCount == 3) {//leave
	                	gp.player.currentInteractNPC = null;
	                	gp.GameState = gp.playState;
	                }
				}
			}
			if(gp.player.graphic.subState == 1) {
				if(code == KeyEvent.VK_W) {
					if(gp.player.graphic.traderslotRow != 0) {
						gp.player.graphic.traderslotRow--;
					}
				}
				if(code == KeyEvent.VK_D) {
					if(gp.player.graphic.traderslotCol != 3) {
						gp.player.graphic.traderslotCol++;
					}
				}
				if(code == KeyEvent.VK_S) {
					if(gp.player.graphic.traderslotRow != 3) {
						gp.player.graphic.traderslotRow++;
					}
				}
				if(code == KeyEvent.VK_A) {
					if(gp.player.graphic.traderslotCol != 0) {
						gp.player.graphic.traderslotCol--;
					}
				}
				if(code == KeyEvent.VK_SPACE) {
					gp.player.BuyItem();
				}
				if(code == KeyEvent.VK_ESCAPE) {
					gp.GameState = gp.playState;
				}
			}
			if(gp.player.graphic.subState == 2) {
				if(code == KeyEvent.VK_W) {
					if(gp.player.graphic.slotRow != 0) {
						gp.player.graphic.slotRow--;
					}
				}
				if(code == KeyEvent.VK_D) {
					if(gp.player.graphic.slotCol != 6) {
						gp.player.graphic.slotCol++;
					}
				}
				if(code == KeyEvent.VK_S) {
					if(gp.player.graphic.slotRow != 5) {
						gp.player.graphic.slotRow++;
					}
				}
				if(code == KeyEvent.VK_A) {
					if(gp.player.graphic.slotCol != 0) {
						gp.player.graphic.slotCol--;
					}
				}
				if(code == KeyEvent.VK_SPACE) {
					gp.player.SellItem();
				}
				if(code == KeyEvent.VK_ESCAPE) {
					gp.GameState = gp.playState;
				}
			}
		}
		//Finish Screen state
		if(gp.GameState == gp.finishState) {
			if(code == KeyEvent.VK_A) {
				gp.ui.SelectionCount = 5;
			}
			if(code == KeyEvent.VK_D) {
				gp.ui.SelectionCount = 6;
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.SelectionCount == 5) {//RESTART
					gp.player.restart();
				}
				if(gp.ui.SelectionCount == 6) {//EXIT TO TITLE
					gp.GameState = gp.titleState;
				}
			}
		}
		//Show Inventory
		if(gp.GameState == gp.statState) {
			if(code == KeyEvent.VK_W) {
				if(gp.player.graphic.slotRow != 0) {
					gp.player.graphic.slotRow--;
				}
			}
			if(code == KeyEvent.VK_D) {
				if(gp.player.graphic.slotCol != 6) {
					gp.player.graphic.slotCol++;
				}
			}
			if(code == KeyEvent.VK_S) {
				if(gp.player.graphic.slotRow != 5) {
					gp.player.graphic.slotRow++;
				}
			}
			if(code == KeyEvent.VK_A) {
				if(gp.player.graphic.slotCol != 0) {
					gp.player.graphic.slotCol--;
				}
			}
			if(code == KeyEvent.VK_SPACE) {
				gp.player.selectItem();
			}
		}
		if(gp.GameState == gp.titleState) {
			if(code == KeyEvent.VK_W) {
				gp.ui.SelectionCount--;
				if(gp.ui.SelectionCount < 1) {
					gp.ui.SelectionCount = 3;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.SelectionCount++;
				if(gp.ui.SelectionCount > 3) {
					gp.ui.SelectionCount = 1;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.SelectionCount == 1) {//NEW GAME
					gp.player.restart();
				}
				if(gp.ui.SelectionCount == 2) {//LOAD GAME
					gp.player.respawn();
				}
				if(gp.ui.SelectionCount == 3) {//EXIT GAME
					System.exit(0);
				}
			}
		}
		//Death State
		if(gp.GameState == gp.gameoverState) {
			if(code == KeyEvent.VK_A) {
				gp.ui.SelectionCount = 1;
			}
			if(code == KeyEvent.VK_D) {
				gp.ui.SelectionCount = 2;
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.SelectionCount == 1) {//RESPAWN
					gp.player.respawn();
				}
				if(gp.ui.SelectionCount == 2) {//EXIT TO TITLE
					gp.GameState = gp.titleState;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//Action when key released
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			up = false;
			idle = true;
		}
		if(code == KeyEvent.VK_S) {
			down = false;
			idle = true;
		}
		if(code == KeyEvent.VK_A) {
			left = false;
			idle = true;
		}
		if(code == KeyEvent.VK_D) {
			right = false;
			idle = true;
		}
		if(code == KeyEvent.VK_F) {
			interactKey = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			gp.player.gun = false;
			//gp.player.speed = 4;
			//gp.player.attacking = false;
		}	
		if(code == KeyEvent.VK_T) {
			if(pressed == 1) {
				pressed = 2;
			}
			if(pressed == 3) {
				pressed = 0;
			}
			
		}
		//SKILL SET
		if(code == KeyEvent.VK_1) {
			gp.player.skill = 0;
			gp.player.skillusing = false;
		}
		if(code == KeyEvent.VK_2) {
			gp.player.skill = 0;
			gp.player.skillusing = false;
		}
	}
	public void check() {
		System.out.println("left " +left);
		System.out.println("right " +right);
		System.out.println("down " +down);
		System.out.println("up " +up);
		
	}

}
