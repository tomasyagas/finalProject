package com.despegar.highflight.service;

public class Cell {
	private Boolean mine;
	private Boolean flag;
	private int number;
	private Boolean uncovered;
	
	public Boolean getMine() {
		return mine;
	}
	public void setMine(Boolean mine) {
		this.mine = mine;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Boolean getUncovered() {
		return uncovered;
	}
	public void setUncovered(Boolean uncovered) {
		this.uncovered = uncovered;
	}
	public Cell() {
		super();
		this.mine = false;
		this.flag = false;
		this.number = 0;
		this.uncovered = false;
	}
	
	public void display(){
		if (this.getUncovered()){
    		if (this.getMine()){
    			System.out.print("M"+" ");
    		} else {
    		System.out.print(this.getNumber()+" ");
    		}
		} else if (this.getFlag()){
			System.out.print("F"+" ");
    	} else {
    		System.out.print("-"+" ");
    	}
	}
	
	public void displayInternal(){
		if (this.getMine()){
			System.out.print("M"+" ");
		} else {
		System.out.print(this.getNumber()+" "); 
		}
	}
	
	public void displayRaw(){
		if (this.getMine()){
    		System.out.print(1+" ");
    	} else {
    		System.out.print(0+" ");
    	}
	}
	
	public void displayLoser(){
		if (this.getMine()){
			System.out.print("M"+" ");
		} else if (this.getUncovered()){
    		System.out.print(this.getNumber()+" ");
    	} else {
    		System.out.print("-"+" ");
    	}
	}
}