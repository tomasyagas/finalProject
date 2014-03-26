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
		int a = f+1;
		int b = c+1;
		CellPosition pos = new CellPosition(a,b);
		pila.push(pos);
    	b = c;
    	CellPosition pos2 = new CellPosition(a, b);
    	pila.push(pos2);
    	b = c-1;
    	CellPosition pos3 = new CellPosition(a, b);
    	pila.push(pos3);
    	a = f;
    	CellPosition pos4 = new CellPosition(a,b);
    	pila.push(pos4);
    	a = f-1;
    	CellPosition pos5 = new CellPosition(a, b);
    	pila.push(pos5);
    	b = c;
    	CellPosition pos6 = new CellPosition(a, b);
    	pila.push(pos6);
    	b = c+1;
    	CellPosition pos7 = new CellPosition(a, b);
    	pila.push(pos7);
    	a = f;
    	CellPosition pos8 = new CellPosition(a, b);
    	pila.push(pos8);
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
    	mat[row][col].setUncovered(true);
    	cellsToUncover = cellsToUncover -1;
    	if (mat[row][col].getNumber()==0){
    		cellsToUncover = cellsToUncover +1;
    		Set<Matrix2DCellPosition> setApplyUncover = MatrixUtils.cascade(this.binaryGrid ,row, col);
    		for (Matrix2DCellPosition m:setApplyUncover) {
    			int colAux = m.getColumn();
    			int rowAux = m.getRow();
    			mat[rowAux][colAux].setUncovered(true);
    			cellsToUncover = cellsToUncover -1;
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