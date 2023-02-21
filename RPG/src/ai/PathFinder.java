package ai;

import java.util.ArrayList;

import main.GamePanel;

public class PathFinder {
	
	GamePanel gp;
	Node[][] node;
	public ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode,goalNode,currentNode; 
	int step = 0;
	boolean goalReached = false;
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNode();
	}

	public void instantiateNode() {
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			node[col][row] = new Node(col,row);
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	public void resetNodes() {
		
		int col = 0;
		int row = 0;
		
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//reset open, checked ,state
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
		//reset all setting
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow, character.Character c ) {
		resetNodes();
		//set start and goal node
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//set solid node
			//check tile
			int tileNum = gp.tile.mapTileNum[gp.currentMap][col][row];
			if(gp.tile.tile[tileNum].collision == true) {
				node[col][row].solid = true;
			}
			
			//set Cost
			getCost(node[col][row]);
			col ++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}

	public void getCost(Node node) {
		
		//G cost
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		//H Cost
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		//F Cost
		node.fCost = node.gCost + node.hCost;
	}
	public boolean search() {
		while(goalReached == false && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.checked = true;
			openList.remove(currentNode);
			if(row - 1 >= 0) {
				openNode(node[col][row-1]);
			}
			if(col - 1 >= 0) {
				openNode(node[col-1][row]);
			}
			if(row + 1 < gp.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			if(col + 1 < gp.maxWorldCol) {
				openNode(node[col+1][row]);
			}
			
			//Find best Target node
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for( int i = 0; i < openList.size(); i++) {
				if(openList.get(i).fCost < bestNodefCost ) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			//if no node available-> end loop
			if(openList.size() == 0) {
				break;
			}
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackPath();
			}
			step++;
		}
		return goalReached;
	}
	private void trackPath() {
		Node current = goalNode;
		while(current != startNode) {
			pathList.add(0,current);
			current = current.parent;
		}
		
	}

	public void openNode(Node node) {
		if(node.open == false && node.checked == false && node.solid == false) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}

}
