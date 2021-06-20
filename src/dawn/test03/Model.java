package dawn.test03;

import java.util.HashMap;
import java.util.Map;

public class Model {

    public String TCode;
    public String 股票代码;
    public String 首次披露日期;
    public String errorMsg;
    public long 文件大小;
    public String pdfContent;
    public int 总页数;
    public int 总段落数;
    public int 总字数;
    public int 总句数;

    public int 总词数;
    public int LM正向词数;
    public int LM负向词数;
    public int 台大正向词数;
    public int 台大负向词数;
    public int 逆接词数;
    public int 次常用字数;
    public int RD词数;

    public int 会计术语数;
    public int 连词数;
    public int 代词数;
    public int 未来预期数;


    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("TCode", this.TCode);
        map.put("errorMsg", this.errorMsg);
        map.put("股票代码", this.股票代码);
        map.put("首次披露日期", this.首次披露日期);
        map.put("pdfContent", this.pdfContent);
        map.put("文件大小", this.文件大小);
        map.put("总页数", this.总页数);
        map.put("总词数", this.总词数);
        map.put("总字数", this.总字数);
        map.put("总句数", this.总句数);
        map.put("总段落数", this.总段落数);

        map.put("LM正向词数", this.LM正向词数);
        map.put("LM负向词数", this.LM负向词数);
        map.put("台大正向词数", this.台大正向词数);
        map.put("台大负向词数", this.台大负向词数);
        map.put("连词数", this.连词数);
        map.put("代词数", this.代词数);
        map.put("逆接词数", this.逆接词数);
        map.put("会计术语数", this.会计术语数);
        map.put("次常用字数", this.次常用字数);
        map.put("RD词数", this.RD词数);
        map.put("未来预期数", this.未来预期数);
        return map;
    }
}
