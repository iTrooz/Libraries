package fr.entasia.apis;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatComponent {
	private List<BaseComponent> comps;

	public ChatComponent(){
		comps = new ArrayList<>();
	}

	public ChatComponent(String text){
		comps = Arrays.asList(TextComponent.fromLegacyText(text));
	}

	public void setHoverEvent(HoverEvent hover){
		for(BaseComponent b : comps) b.setHoverEvent(hover);
	}

	public void setClickEvent(ClickEvent click){
		for(BaseComponent b : comps) b.setClickEvent(click);
	}

	public void setColor(ChatColor color){
		for(BaseComponent b : comps) b.setColor(color);
	}



	public void append(ChatComponent cc){
		comps.addAll(cc.comps);
	}



	public BaseComponent[] create(){
		return comps.toArray(new BaseComponent[0]);
	}

//	public static BaseComponent[] create(Object... objs){
//		List<BaseComponent> components = new ArrayList<>();
//		for(Object o : objs){
//			if(o instanceof BaseComponent[]){
//				components.addAll(Arrays.asList((BaseComponent[])o));
//			}else if(o instanceof ChatComponent){
//				components.addAll(((ChatComponent)o).comps);
//			}else if(o instanceof String){
//				components.addAll(Arrays.asList(create((String)o)));
//			}
//		}
//		return components.toArray(new BaseComponent[0]);
//	}

	public static BaseComponent[] create(ChatComponent... compos){
		List<BaseComponent> components = new ArrayList<>();
		for(ChatComponent cc : compos){
			components.addAll(cc.comps);
		}
		return components.toArray(new BaseComponent[0]);
	}

//	public static BaseComponent[] create(BaseComponent[]... compos){
//		List<BaseComponent> components = new ArrayList<>();
//		for(BaseComponent[] bc : compos) {
//			components.addAll(Arrays.asList(bc));
//		}
//		return components.toArray(new BaseComponent[0]);
//	}

	public static BaseComponent[] create(String... compos){
		StringBuilder a = new StringBuilder();
		for(String i : compos)a.append(i);
		return create(a.toString());
	}

	public static BaseComponent[] create(String text){
		return TextComponent.fromLegacyText(text);
	}

}
