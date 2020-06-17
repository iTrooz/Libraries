package fr.entasia.apis;

import io.netty.util.internal.MathUtil;
import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
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

	private static String[] n = {"seconde","minute","heure","jour","semaine","mois", "année","siècle","millénaire"};

	public static String secondsToTime(int a){
		int[] d = {60, 60, 24, 7, 4, 12, 100, 10};
		int[] t = new int[n.length];
		t[0] = a;
		for(int i = 0; i<n.length; i++){
			if(i != n.length-1){
				t[i+1] = (t[i] / d[i]);
				t[i] = t[i] % d[i];
			}
			if(t[i+1] == 0)break;
		}
		StringBuilder f = new StringBuilder();
		for(int i=t.length-1;i>=0;i--){
			if(t[i] !=0){
				if(i==0)f.append(" et ");
				else f.append(", ");
				f.append(t[i]).append(" ").append(n[i]);
				if(t[i] != 1&&i != 5)f.append('s');
			}
		}
		return f.substring(2);
	}

	public static String fill(String str, char c, int len){
		StringBuilder sb = new StringBuilder();
		for(int i=str.length(); i<len; i++){
			sb.append(c);
		}
		return sb.toString()+str;
	}

//	public static void main(String args[]){
//		int test = tr("1m");
//		System.out.println(test);
//
//	}
//
//	private static List<String> filter(String[] array){
//		List<String> a = new ArrayList<>();
//		for(String i : array){
//			if(!i.equals(""))a.add(i);
//		}
//		return a;
//	}

//	public static int tr(String t){
//
//		t = t.toLowerCase();
//		for(String i : t.split("[a-z]")){
//			System.out.println(i);
//		}
//		System.out.println(" ");
//		for(String i : t.split("[0-9]")){
//			System.out.println(i);
//		}
//		int[] a = filter(t.split("[a-z]"), "").toArray(new Integer[0]);
//		String[] b = filter(t.split("[0-9]"), "").toArray(String.class);
//		int c = 0;
//		for(int i=0;i<a.length;i++){
//			switch(b[i]){
//				case "mo":
//					c+=a[i]*3600*24*31;
//					break;
//				case "w":
//					c+=a[i]*3600*24*7;
//					break;
//				case "h":
//					c+=a[i]*3600;
//					break;
//				case"m":case"mi":case"min":
//					c+=a[i]*60;
//					break;
//				case "s":case"sec":
//					c+=a[i];
//					break;
//			}
//		}
//		return 1;
//	}
}
