package com.despegar.highflight.service;

import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.despegar.highflight.utils.Matrix2DCellPosition;
import com.despegar.highflight.utils.MatrixUtils;

public class MinesweeperImpl {
	
    private Cell mat[][];
    private int[][] binaryGrid;
    private int uncoveredCells = 0;
    
    public void load() {
        int row= 3;
        int col= 4;
        int mines = 3;
        
        mat = new Cell[row][col];
        
        for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	Cell celda = new Cell();
            	mat[f][c] = celda;
            }
        }
        
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
        
        for(int f=0;f<mat.length;f++) {
            for(int c=0;c<mat[f].length;c++) {
            	Stack<CellPosition> positions = this.searchPosition(f,c);
            	int number = 0;
            	while (!positions.empty()) {
            		CellPosition pos = positions.pop();
            		if ((pos.getRow()>=0)&&(pos.getCol()>=0)&&(pos.getRow()<mat.length)&&(pos.getCol()<mat[f].length)){
            			if (this.mat[pos.getRow()][pos.getCol()].getMine()){
                			number++;
                		}
            		}
            	}
            	this.mat[f][c].setNumber(number);
            }
        }
    }
    
    private Stack<CellPosition> searchPosition(int f, int c) {
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
    	this.uncoveredCells++;
    	if (mat[row][col].getNumber()==0){
    		MatrixUtils matrix = new MatrixUtils();
    		Set<Matrix2DCellPosition> setApplyUncover = matrix.cascade(this.binaryGrid ,row, col);
    		for (Matrix2DCellPosition m:setApplyUncover) {
    			int colAux = m.getColumn();
    			int rowAux = m.getRow();
    			mat[rowAux][colAux].setUncovered(true);
    			this.uncoveredCells++;
    		}
    	}
    }
    
    public static void main(String[] args) {
        MinesweeperImpl game=new MinesweeperImpl();
        game.load();
        game.display();
        System.out.println();
        game.displayInternal();
        System.out.println();
        game.displayRaw();
        System.out.println();
        System.out.println(game.mat.length);
        System.out.println();
        game.displayBinaryGrid();
        System.out.println();
        game.uncover(0, 0);
        game.display();
    }
}
