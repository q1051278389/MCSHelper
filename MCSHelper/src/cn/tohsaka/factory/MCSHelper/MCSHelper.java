package cn.tohsaka.factory.MCSHelper;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.webbitserver.*;

import java.io.Console;
import java.util.logging.Logger;

public final class MCSHelper extends JavaPlugin implements Listener {
    public static MCSHelper instance;
    public static WebServer webServer;
    @Override
    public void onLoad() {
        getLogger().info("MCSHelper is loadad.");
    }
    public static double tps = 0;
    @Override
    public void onEnable() {
        String port = new MCSUtils().readFile("./plugins/MCSHelper/config.dat");
        if(port==""){
            new MCSUtils().writeFile("./plugins/MCSHelper/config.dat","8088");
            port="8088";
        }



// in onEnable or something
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            long sec;
            long currentSec;
            int ticks;
            int delay;

            @Override
            public void run()
            {
                sec = (System.currentTimeMillis() / 1000);

                if(currentSec == sec)
                {
                    // this code block triggers each tick

                    ticks++;
                }
                else
                {
                    // this code block triggers each second

                    currentSec = sec;
                    tps = (tps == 0 ? ticks : ((tps + ticks) / 2));
                    ticks = 0;

                }
            }
        }, 0, 1);

        getLogger().info("MCSHelper is started.");
        this.getServer().getPluginManager().registerEvents(this, this);
        instance = this;
        try{
            webServer = WebServers.createWebServer(Integer.valueOf(port));
            Logger.getLogger("MCSHelper").info("[MCSHelper] 监听端口:"+port);
            webServer.add("/ws",new wsHandler(this.getServer())).add(new WebHandler(this.getServer())).start();
        }catch (Exception e){

        }
    }

    @Override
    public void onDisable() {
        getLogger().info("MCSHelper is disabled.");
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        //System.out.println("http://192.168.199.254:8088/"+encode(event.getPlayer().getName()+"|"+event.getMessage()));
        //new MCSUtils().get("http://192.168.199.254:8088/"+encode(event.getPlayer().getName()+"|"+event.getMessage()));
        wsHandler.broadcast(toJson(new Message(event.getPlayer().getName(),event.getMessage(),"server")));
        System.out.println("send broadcast");
    }


    public static String encode(String data) {
        return new sun.misc.BASE64Encoder().encode(data.getBytes());
    }



    public static String toJson(Object obj){
        return new Gson().toJson(obj);
    }

}