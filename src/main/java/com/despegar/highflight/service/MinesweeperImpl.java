package com.despegar.highflight.service;

import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.despegar.highflight.utils.Matrix2DCellPosition;
import com.despegar.highflight.utils.MatrixUtils;

public class MinesweeperImpl {
	
    private Cell mat[][];
    private int[][] binaryGrid;
    boolean gameOver = false;
    boolean uncoveredMine = false;
    int row;
    int col;
    int mines;
    int totalCells;
    int cellsToUncover;
    
    public MinesweeperImpl(int row, int col) {
		this.row = row;
		this.col = col;
		this.mines = row*col*15/100;
		totalCells = row*col;
		cellsToUncover = totalCells-mines;
		this.load();
	}

	public void load() {
        
        mat = new Cell[row][col];
        //load cells
        for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	Cell celda = new Cell();
            	mat[f][c] = celda;
            }
        }
        //load mines in the grid
        Random rand = new Random();
        for (int m=0;m<mines;m++){
        	int rowMine = rand.nextInt(row);
        	int colMine = rand.nextInt(col);
        		while (this.mat[rowMine][colMine].getMine().equals(true)){
        			rowMine = rand.nextInt(row);
        			colMine = rand.nextInt(col);
        		}
        	this.mat[rowMine][colMine].setMine(true);
        }
        
        binaryGrid = new int[row][col];
        this.loadBinaryGrid();
        
        //load numbers in the grid
        for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	if (!thereIsMine(f,c)) {
            	Stack<CellPosition> positions = this.nearPositions(f,c);
            	int number = 0;
            	while (!positions.empty()) {
            		CellPosition pos = positions.pop();
            		if (isValidPosition(pos.getRow(),pos.getCol())){
            			if (thereIsMine(pos.getRow(),pos.getCol())){
                			number++;
                		}
            		}
            	}
            	this.mat[f][c].setNumber(number);
            	}
            }
        }
    }
    
	//return a stack with the positions near to the pos
    private Stack<CellPosition> nearPositions(int f, int c) {
    	Stack<CellPosition>pila = new Stack<CellPosition>();
    	int a=0, b=0;
		for (int p=1;p<9;p++){
			switch (p) {
			case 1: a= f+1; b = c+1; break;
			case 2: a= f+1; b = c; break;
			case 3: a= f+1; b = c-1; break;
			case 4: a= f; b = c-1; break;
			case 5: a= f-1; b = c-1; break;
			case 6: a= f-1; b = c; break;
			case 7: a= f-1; b = c+1; break;
			case 8: a= f; b = c+1; break;
			}
			CellPosition pos = new CellPosition(a,b);
			pila.push(pos);
		}
    	return pila;
	}

    public boolean isValidPosition(int row, int col){
    	if ((row>=0)&&(col>=0)&&(row<this.row)&&(col<this.col)){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void loadBinaryGrid(){
    	for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	if (this.mat[f][c].getMine()) {
            		this.binaryGrid[f][c] = 1;
            	} else {
            		this.binaryGrid[f][c] = 0;
            	}
            }
        }
    }
    
	public void display() {
        for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	mat[f][c].display();
            }
            System.out.println();
        }
    }
    
    public void displayInternal() {
        for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	mat[f][c].displayInternal();
            }
            System.out.println();
        }
    }
    
    public void displayRaw() {
        for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	mat[f][c].displayRaw();
            }
            System.out.println();
        }
    }
    
    public void displayLoser(){
    	for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	mat[f][c].displayLoser();
            }
            System.out.println();
        }
    }
    
    public void displayBinaryGrid() {
    	for(int f=0;f<binaryGrid.length;f++) {
            for(int c=0;c<binaryGrid[f].length;c++) {
            	System.out.print(binaryGrid[f][c]+" ");
            }
            System.out.println();
        }
    }
    
    public void uncover(int row, int col){
    	if (!mat[row][col].getUncovered()) {
    		mat[row][col].setUncovered(true);
    		cellsToUncover = cellsToUncover -1;
    	}
    	if (mat[row][col].getNumber()==0){
    		Set<Matrix2DCellPosition> setApplyUncover = MatrixUtils.cascade(this.binaryGrid ,row, col);
    		for (Matrix2DCellPosition m:setApplyUncover) {
    			int colAux = m.getColumn();
    			int rowAux = m.getRow();
    			if (!mat[rowAux][colAux].getUncovered()) {
    			mat[rowAux][colAux].setUncovered(true);
    			cellsToUncover = cellsToUncover -1;
    			}
    		}
    	}
    	if (thereIsMine(row,col)){
    		gameOver = true;
    		uncoveredMine = true;
    	}
    }
    
    public boolean thereIsMine(int row, int col) {
		if (mat[row][col].getMine()){
			return true;
		} else {
			return false;
		}
	}

	public void flagAsMine(int row, int col){
    	mat[row][col].setFlag(true);
    }
    
    public void clearFlag(int row, int col){
    	mat[row][col].setFlag(false);
    }
    
    public boolean isGameOver(){
    	if (0==cellsToUncover) {
    		gameOver = true;
    	}
    	return gameOver;
    }
    
    public boolean isWinningGame(){
    	if (isGameOver()&&0==cellsToUncover&&!uncoveredMine){
    		return true;
    	} else {
    		return false;
    	}
    }
    
}