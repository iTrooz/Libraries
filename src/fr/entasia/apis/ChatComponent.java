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

	private final List<BaseComponent> comps;



	public ChatComponent(){
		comps = new ArrayList<>();
	}

	public ChatComponent(String text){
		comps = new ArrayList<>();
		append(text);
	}

	public ChatComponent(String... lines){
		comps = new ArrayList<>();
		append(String.join("\n", lines));
	}

	public void append(String text){
		comps.addAll(Arrays.asList(TextComponent.fromLegacyText(text)));
	}

	public void append(ChatComponent cc){
		comps.addAll(cc.comps);
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



//	public static BaseComponent[] create(BaseComponent[]... compos){
//		List<BaseComponent> components = new ArrayList<>();
//		for(BaseComponent[] bc : compos) {
//			components.addAll(Arrays.asList(bc));
//		}
//		return components.toArray(new BaseComponent[0]);
//	}



	public BaseComponent[] create(){
	return comps.toArray(new BaseComponent[0]);
}

	public static BaseComponent[] create(String text){
		return TextComponent.fromLegacyText(text);
	}

	public static BaseComponent[] create(ChatComponent... text){
		ArrayList<BaseComponent> list = new ArrayList<>();
		for(ChatComponent cc : text){
			list.addAll(cc.comps);
		}
		return list.toArray(new BaseComponent[0]);
	}

}
