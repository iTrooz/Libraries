package fr.entasia.apis.utils;

import io.netty.util.internal.MathUtil;
import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;

public class TextUtils {

	public static String firstLetterUpper(String a){
		return a.substring(0, 1).toUpperCase()+a.substring(1).toLowerCase();
	}

	public static Pattern regex = Pattern.compile("entasia", Pattern.CASE_INSENSITIVE);

	public static String setColors(String msg) {
		char[] list = msg.toCharArray();
		for(int i=1;i<list.length;i++){
			if(list[i]=='&'){
				if(ChatColor.getByChar(list[i-1])!=null){
					list[i] = '§';
				}
			}
		}
		return new String(list);
	}

	public static String formatMessage(String msg, ChatColor color) {
		return regex.matcher(msg).replaceAll("§bEnta§7sia"+color)
				.replace("#", "§c#"+color).replace("<3", "§c❤"+color)
				.replace(":)", "§6☺"+color).replace("(:", "§6☻"+color);
	}

	private static final int[] d = {60, 60, 24, 30, 12, 100};
	private static final String[] n = {"seconde","minute","heure","jour","mois","année","siècle"};

	public static void main(String[] fa){
		System.out.println(secondsToTime(426424218));
	}


	public static String secondsToTime(int seconds){
		int[] t = new int[n.length];
		t[0] = seconds;
		int i;
		for(i = 1; i<n.length; i++){
			t[i]  = t[i-1] / d[i-1];
			t[i-1] = t[i-1] % d[i-1];
			if(t[i] == 0)break;
		}
		StringBuilder f = new StringBuilder();
		for(int j=0;j<t.length;j++){
			if(t[j]!=0){
				if(t[j] != 1&&j != 4)f.insert(0, 's'); // pluriel
				f.insert(0, t[j]+" "+n[j]);
				f.insert(0, ", ");
			}
		}
		f.delete(0, 2);
		i = f.lastIndexOf(",");
		if(i!=-1)f.replace(i, i+1, " et");
		return f.toString();
	}


	public static int timeToSeconds(String timeStr){
		int time = 0;
		boolean expectNumber = true;
		StringBuilder a1 = new StringBuilder();
		StringBuilder a2 = new StringBuilder();

		char[] array = timeStr.toCharArray();
		try{
			Integer.parseInt(String.valueOf(array[0]));
		}catch(NumberFormatException ignore){
			return 0;
		}
		try{
			Integer.parseInt(String.valueOf(array[array.length-1]));
			return 0;
		}catch(NumberFormatException ignore){
		}

		for(char c : timeStr.toCharArray()){
			if(c>=48&&c<=57){
				if(expectNumber){
					a1.append(c);
				}else{
					try{
						time += Integer.parseInt(a1.toString()) * getMultiplier(a2.toString());
					}catch(NumberFormatException e){
						return 0;
					}
					expectNumber = true;
					a1 = new StringBuilder();
					a1.append(c);
				}
			}else{ // lettre
				if(expectNumber){
					expectNumber = false;
					a2 = new StringBuilder();
					a2.append(c);
				}else {
					a2.append(c);
				}
			}
		}
		if(expectNumber)return 0;
		time += Integer.parseInt(a1.toString()) * getMultiplier(a2.toString());
		return time;
	}

	private static int getMultiplier(String tu){
		int m = 1;
		switch(tu) {
			case "month":
			case "mo":{
				m *= 30;
			}
			case "day":
			case "d":{
				m *= 24;
			}
			case "hours":
			case "hour":
			case "h":{
				m *= 60;
			}
			case "min":
			case "m":{
				m *= 60;
			}
			case "sec":
			case "s":{
				break;
			}
			default:{
				return 0;
			}
		}
		return m;
	}


	public static String fill(String str, char c, int len){
		StringBuilder sb = new StringBuilder();
		for(int i=str.length(); i<len; i++){
			sb.append(c);
		}
		return sb.toString()+str;
	}

	public static String formatCalendar(Calendar c){
		return get(c, Calendar.YEAR, 0)+"/"+get(c, Calendar.MONTH, 1)+"/"+get(c, Calendar.DAY_OF_MONTH, 0)
				+" "+get(c, Calendar.HOUR_OF_DAY, 0)+":"+get(c, Calendar.MINUTE, 0);
	}

	private static String get(Calendar when, int type, int add){
		String b = String.valueOf(when.get(type)+add);
		if(b.length()==1)return "0"+b;
		else return b;
	}


}
