package cn.tohsaka.factory.MCSHelper
import java.io.File
import java.net.URL

open class MCSUtils{
    open fun get(url:String):String{
        return URL(url).readText();
    }
    open fun readFile(path:String):String{
        if(!File(path).exists()){
            return "";
        }
        return File(path).readText().trim();
    }
    open fun writeFile(path:String,data:String){
        if(!File(path).parentFile.exists()){
            File(path).parentFile.mkdirs();
        }
        File(path).writeText(data.trim());
    }
}
