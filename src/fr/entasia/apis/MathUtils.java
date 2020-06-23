package fr.entasia.apis;

@Deprecated
public class MathUtils {

	public static boolean isBetween(double value, double first, double second){
		double min = getLower(first, second);
		double max = getGreater(first, second);
		
		if(value >= min && value <= max) return true;
		return false;
	}
	
	public static double getLower(double first, double second){
		if(first <= second){
			return first;
		} else {
			return second;
		}
	}
	
	public static double getGreater(double first, double second){
		if(first >= second){
			return first;
		} else {
			return second;
		}
	}
	
}
