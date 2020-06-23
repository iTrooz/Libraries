package fr.entasia.apis.other;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static fr.entasia.libraries.Paper.main;

public class Signer {
    public static PacketContainer pc = null;
    public static boolean activate=false;

    private static Map<String, Signer.SignReponse> listeners = new HashMap<>();

    public static boolean open(Player p, SignReponse r) {
        open(p, 0 , 0, 0, r);
        return true;
    }

    public static void open(Player p, int x, int y, int z, SignReponse response){
        if(pc==null) throw new RuntimeException("ProtocolLib not here !");
        try {
            pc.getBlockPositionModifier().write(0, new BlockPosition(x, y, z));
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, pc);
            listeners.put(p.getDisplayName(), response);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void initPackets() {
        pc = new PacketContainer(PacketType.Play.Server.OPEN_SIGN_EDITOR);

//        if(ServerUtils.version.equals("1_8_R3")){
//            ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(main, PacketType.Play.Client.UPDATE_SIGN) {
//                @Override
//                public void onPacketReceiving(PacketEvent event) {
//                    if (listeners.containsKey(event.getPlayer().getDisplayName())) {
//                        event.setCancelled(true);
//                        WrappedChatComponent[] a = event.getPacket().getChatComponentArrays().read(0);
//                        String[] b = new String[4];
//                        for (int i = 0; i < 4; i++) {
//                            b[i] = a[i].getJson();
//                            b[i] = b[i].substring(1, b[i].length()-1);
//                        }
//                        listeners.get(event.getPlayer().getDisplayName()).onFinish(b);
//                        listeners.remove(event.getPlayer().getDisplayName());
//                    }
//                }
//            });
//        }else{
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(main, PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Signer.SignReponse r = listeners.remove(event.getPlayer().getDisplayName());
                if(r!=null){
                    event.setCancelled(true);
                    String[] a = event.getPacket().getStringArrays().read(0);
                    try{
                        r.onFinish(a);
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Exception caught (problème d'un plugin externe)");
                    }
                }
            }
        });
//        }
        activate=true;

    }

    public interface SignReponse {
        void onFinish(String[] lines);
    }
}
