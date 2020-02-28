package cn.tohsaka.factory.MCSHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebSocketConnection;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

public class wsHandler implements org.webbitserver.WebSocketHandler {
    Server server;
    public wsHandler(Server s){
        server=s;
    }
    private static Set<WebSocketConnection> connections = new HashSet<WebSocketConnection>();
    @Override
    public void onOpen(WebSocketConnection webSocketConnection) throws Throwable {
        connections.add(webSocketConnection);
    }

    @Override
    public void onClose(WebSocketConnection webSocketConnection) throws Throwable {
        connections.remove(webSocketConnection);
    }

    @Override
    public void onMessage(WebSocketConnection webSocketConnection, String s) throws Throwable {
        System.out.println("[Websocket Message]"+s);
        Message msg = new Gson().fromJson(s, new TypeToken<Message>(){}.getType());
        if(msg.type.equals("group")){
            server.broadcastMessage(msg.message);
            return;
        }

        if(msg.type.equals("picture")){
            TextComponent message = new TextComponent( msg.message+ ChatColor.UNDERLINE+ "[图片]" );
            message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, msg.sender ) );
            for(Player i : server.getOnlinePlayers()){
                i.spigot().sendMessage(message);
            }
            return;
        }

        if(msg.type.equals("command")){
            String cmd ="";
            if(msg.message.indexOf(" ")==-1){
                cmd = msg.message.substring(1);
            }else{
                cmd = msg.message.substring(1,msg.message.indexOf(" ")-1);
            }
            try{
                Class<cmd> clazz = (Class<cmd>) Class.forName("cn.tohsaka.factory.MCSHelper.commands.cmd_"+cmd);
                if(clazz != null){
                    clazz.newInstance().invoke(msg);
                }
            }catch (ClassNotFoundException ex){
                wsHandler.broadcast(MCSHelper.toJson(new Message(null,"未知命令:"+msg.message,"command")));
            }
        }

    }

    public static void broadcast(String msg) {
        for (WebSocketConnection connection : connections) {
            connection.send(msg);
        }
    }








    @Override
    public void onMessage(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {
        onMessage(webSocketConnection, new String(bytes, "utf-8"));
    }
    @Override
    public void onPing(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {

    }
    @Override
    public void onPong(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {

    }
}
