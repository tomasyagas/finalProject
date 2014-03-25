package com.despegar.highflight.service;

import java.util.Random;

public class Grid {
	
    private Cell mat[][];
    
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
    
    public static void main(String[] args) {
        Grid matriz=new Grid();
        matriz.load();
        matriz.display();
        System.out.println();
        matriz.displayInternal();
        System.out.println();
        matriz.displayRaw();
        System.out.println();
        System.out.println(matriz.mat.length);
    }
}
