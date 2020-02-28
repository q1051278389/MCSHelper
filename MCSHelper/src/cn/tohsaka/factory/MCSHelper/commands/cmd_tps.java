package cn.tohsaka.factory.MCSHelper.commands;

import cn.tohsaka.factory.MCSHelper.MCSHelper;
import cn.tohsaka.factory.MCSHelper.Message;
import cn.tohsaka.factory.MCSHelper.cmd;
import cn.tohsaka.factory.MCSHelper.wsHandler;

public class cmd_tps extends cmd {
    @Override
    public void invoke(Message msg) {
        wsHandler.broadcast(MCSHelper.toJson(new Message(null,"当前服务器TPS:"+ String.format("%.2f",MCSHelper.tps),"command")));
    }

}
