package fr.entasia.apis.other;

import fr.entasia.errors.EntasiaException;

import java.util.Random;

public class Randomiser {

	public static final Random random = new Random();

	public int max;
	public double number;
	public boolean real = true;
	public double cPercent;

	public Randomiser(){
		this(100);
	}

	public Randomiser(int max){
		this.max = max;
		regen();
	}


	public boolean isInNext(double percents){
		if(cPercent ==-1)throw new EntasiaException("Value already found !");
		cPercent += percents;
		if(cPercent >= max)throw new EntasiaException("Excedded maximum !");

		if(cPercent>= number){
			cPercent = -1;
			return real;
		}
		return false;
	}

	public void regen(){
		this.number = random.nextInt(max);
		this.cPercent = 0;
	}


//	private ArrayList<Integer> list = new ArrayList<>();
//	private boolean finalized = false;

//	public Randomiser add(int index, int percent){
//		if(finalized)throw new EntasiaException("Already initialized !");
//		list.add(index, percent);
//		return this;
//	}
//
//	public Randomiser finish(){
//		int n = 0;
//		for (Integer integer : list) n += integer;
//		if(n!=100)throw new EntasiaException("Not 100%");
//		finalized = true;
//		return this;
//	}
//
//	public int random(){
//		if(finalized){
//			int price = random.nextInt(100);
//			int n = 0;
//			int i = 0;
//			for (Integer integer : list) {
//				n += integer;
//				if (price < n) {
//					return i;
//				}
//				i++;
//			}
//			return -10;
//		}else throw new EntasiaException("Not finalized");
//	}
}
