package com.despegar.highflight.service;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {     
        Scanner scan = new Scanner(System.in);
        String move;
        int row;
        int col;
        System.out.println("Welcome! Please, enter the size you want your grid:");
        System.out.println("Number of rows: ");
        row = scan.nextInt();
        System.out.println("Number of columns: ");
        col = scan.nextInt();
        MinesweeperImpl game = new MinesweeperImpl(row, col);
        game.display();
        System.out.println("The number of mines is: "+game.mines);
        while (!game.isGameOver()){
        	System.out.println();
        	System.out.println("Select a move: U (uncover), F (flag) , C (clear flag):");
        	move = scan.next();
        	System.out.println("Where?");
        	System.out.println("Number of row: ");
        	row = scan.nextInt()-1;
        	System.out.println("Number of column: ");
        	col = scan.nextInt()-1;
        	switch (move.charAt(0)) {
        	case 'u': game.uncover(row, col); break;
        	case 'f': game.flagAsMine(row, col); break;
        	case 'c': game.clearFlag(row, col); break;
        	}
        	game.display();
        	System.out.println();
        }
        if (game.isWinningGame()){
        	System.out.println("You are THE Winner!");
        } else {
        	System.out.println("You are a loser ;)");
        	game.displayLoser();
        }
	}
}