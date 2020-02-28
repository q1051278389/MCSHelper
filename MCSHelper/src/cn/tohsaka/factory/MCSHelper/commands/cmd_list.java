package cn.tohsaka.factory.MCSHelper.commands;

import cn.tohsaka.factory.MCSHelper.MCSHelper;
import cn.tohsaka.factory.MCSHelper.Message;
import cn.tohsaka.factory.MCSHelper.cmd;
import cn.tohsaka.factory.MCSHelper.wsHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class cmd_list extends cmd {
    @Override
    public void invoke(Message msg) {
        String data = "当前服务器有 "+ Bukkit.getServer().getOnlinePlayers().size()+" 名玩家\n";
        for(Player i : Bukkit.getServer().getOnlinePlayers()){
            data+=i.getName()+" ";
        }
        wsHandler.broadcast(MCSHelper.toJson(new Message(null,data,"command")));
        return;
    }
}
