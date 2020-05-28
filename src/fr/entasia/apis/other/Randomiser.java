package fr.entasia.apis.other;

import fr.entasia.errors.EntasiaException;

import java.util.ArrayList;
import java.util.Random;

public class Randomiser {

	public static Random random = new Random();
	public ArrayList<Integer> list = new ArrayList<>();
	private boolean finalized = false;

	public Randomiser add(int index, int percent){
		list.add(index, percent);
		return this;
	}

	public Randomiser finish(){
		int n = 0;
		for (Integer integer : list) n += integer;
		if(n!=100)throw new EntasiaException("Not 100%");
		finalized = true;
		return this;
	}

	public int random(){
		if(finalized){
			int price = random.nextInt(100);
			int n = 0;
			int i = 0;
			for (Integer integer : list) {
				n += integer;
				if (price < n) {
					return i;
				}
				i++;
			}
			return -10;
		}else throw new EntasiaException("Not finalized");
	}
}
