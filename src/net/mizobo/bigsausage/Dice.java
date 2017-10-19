package net.mizobo.bigsausage;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

public class Dice {

	private List<Die> dice;
	
	private int faces;
	
	private int modifier;
	private int multiplier;
	private boolean hasMod = false;
	private boolean hasMult = false;
	private boolean multFirst = false;
	
	private Dice(){}
	public Dice(List<Die> dice){
		this.dice = dice;
	}

	public static Dice getDice(String dice) {
		int num = Integer.valueOf(dice.substring(0, dice.indexOf("d")));
		int faces = Integer.valueOf(dice.substring(dice.indexOf("d") + 1));
		LinkedList<Die> diceList = new LinkedList<Die>();
		Dice newDice = new Dice();
		for(int i = 0; i < num; i++){
			diceList.add(newDice.new Die(faces));
		}
		newDice.setDiceList(diceList);
		newDice.faces = faces;
		return newDice;
	}
	
	public int getNumOfFaces(){
		return faces;
	}
	
	public int roll(){
		int result = 0;
		for(Die d : this.dice){
			result += d.roll();
		}
		if(!this.multFirst){
			if(this.hasMod){
				result += this.modifier;
			}
			if(this.hasMult){
				result *= this.multiplier;
			}
		}else{
			if(this.hasMult){
				result *= this.multiplier;
			}
			if(this.hasMod){
				result += this.modifier;
			}
		}
		return result;
	}
	
	public List<Integer> rollAndReturnListOfResults(){
		List<Integer> results = new LinkedList<Integer>();
		for(Die d : this.dice){
			results.add(d.roll());
		}
		if(!this.multFirst){
			if(this.hasMod){
				results.add(this.modifier);
			}
			if(this.hasMult){
				results.add(this.multiplier);
			}
		}else{
			if(this.hasMult){
				results.add(this.multiplier);
			}
			if(this.hasMod){
				results.add(this.modifier);
			}
		}
		return results;
	}
	
	public Dice multFirst(){
		this.multFirst = true;
		return this;
	}
	
	public Dice multLast(){
		this.multFirst = false;
		return this;
	}

	public void setDiceList(List<Die> dice){
		this.dice = dice;
	}
	
	public Dice modifier(int modifier){
		this.modifier = modifier;
		this.hasMod = true;
		return this;
	}
	
	public Dice multiplier(int mult){
		this.multiplier = mult;
		this.hasMult = true;
		return this;
	}
	
	@Override
	public String toString(){
		return this.dice.size() + "d" + this.dice.get(0).faces;
	}
	
	public class Die {

		public Die(int faces){
			this.faces = faces;
		}
		
		SecureRandom rand = new SecureRandom();
		int faces;

		public int roll() {
			return rand.nextInt(faces) + 1;
		}
	}
}
