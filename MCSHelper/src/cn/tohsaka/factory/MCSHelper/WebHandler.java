package cn.tohsaka.factory.MCSHelper;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.spigotmc.SpigotConfig;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLDecoder;

public class WebHandler implements HttpHandler {
    Server server;
    public WebHandler(Server s){
        server=s;
    }

    @Override
    public void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse, HttpControl httpControl) {
        try {
            httpResponse.content("please use websocket").end();
        }catch (Exception e){

        }
    }
    /*@Override
    public void handleHttpRequest(HttpRequest req, HttpResponse res, HttpControl rec) throws Exception {
        String data="ok";
        if(req.uri().contains("/say")){
            String str = req.uri().substring(req.uri().indexOf("/say?str=")+9);
            server.broadcastMessage(URLDecoder.decode(str,"UTF-8"));
        }
        if(req.uri().contains("/pic")){
            String[] str = req.uri().substring(req.uri().indexOf("/pic?str=")+9).split("%7C");
            System.out.println(str[0]);
            System.out.println(str[1]);
            TextComponent message = new TextComponent( URLDecoder.decode(str[0],"UTF-8")+ChatColor.UNDERLINE+ "[图片]" );
            message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, str[1] ) );
            for(Player i : server.getOnlinePlayers()){
                i.spigot().sendMessage(message);
            }
        }
        if(req.uri().contains("/list")){
            data = "当前服务器有 "+server.getOnlinePlayers().size()+" 名玩家\n";
            for(Player i : server.getOnlinePlayers()){
                data+=i.getName()+" ";
            }
        }
        res.content(data).end();
    }*/


}
