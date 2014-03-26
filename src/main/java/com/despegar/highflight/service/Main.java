package com.despegar.highflight.service;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {     
        Scanner scan = new Scanner(System.in);
        String move;
        int row;
        int col;
        System.out.println("Bienvenido al juego, ingrese el tamaño del que desea su grilla");
        System.out.println("Cantidad de filas: ");
        row = scan.nextInt();
        System.out.println("Cantidad de columnas: ");
        col = scan.nextInt();
        MinesweeperImpl game = new MinesweeperImpl(row, col);
        game.display();
        System.out.println("La cantidad de minas es: "+game.mines);
        while (!game.isGameOver()){
        	System.out.println();
        	System.out.println("Seleccione un movimiento: U (uncover), F (flag) , C (clear flag):");
        	move = scan.next();
        	System.out.println("En qué celda desea realizarlo?");
        	System.out.println("Numero de fila: ");
        	row = scan.nextInt()-1;
        	System.out.println("Numero de columna: ");
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
        	System.out.println("GANASTEEEE!");
        } else {
        	System.out.println("Perdiste loser ;)");
        	game.displayLoser();
        }
	}
}