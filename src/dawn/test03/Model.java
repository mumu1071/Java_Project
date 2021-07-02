package dawn.test03;

import org.bson.types.ObjectId;

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

    public String testStr;

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public void setTCode(String TCode) {
        this.TCode = TCode;
    }

    public void set股票代码(String 股票代码) {
        this.股票代码 = 股票代码;
    }

    public void set首次披露日期(String 首次披露日期) {
        this.首次披露日期 = 首次披露日期;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void set文件大小(long 文件大小) {
        this.文件大小 = 文件大小;
    }

    public void setPdfContent(String pdfContent) {
        this.pdfContent = pdfContent;
    }

    public void set总页数(int 总页数) {
        this.总页数 = 总页数;
    }

    public void set总段落数(int 总段落数) {
        this.总段落数 = 总段落数;
    }

    public void set总字数(int 总字数) {
        this.总字数 = 总字数;
    }

    public void set总句数(int 总句数) {
        this.总句数 = 总句数;
    }

    public void set总词数(int 总词数) {
        this.总词数 = 总词数;
    }

    public void setLM正向词数(int LM正向词数) {
        this.LM正向词数 = LM正向词数;
    }

    public void setLM负向词数(int LM负向词数) {
        this.LM负向词数 = LM负向词数;
    }

    public void set台大正向词数(int 台大正向词数) {
        this.台大正向词数 = 台大正向词数;
    }

    public void set台大负向词数(int 台大负向词数) {
        this.台大负向词数 = 台大负向词数;
    }

    public void set逆接词数(int 逆接词数) {
        this.逆接词数 = 逆接词数;
    }

    public void set次常用字数(int 次常用字数) {
        this.次常用字数 = 次常用字数;
    }

    public void setRD词数(int RD词数) {
        this.RD词数 = RD词数;
    }

    public void set会计术语数(int 会计术语数) {
        this.会计术语数 = 会计术语数;
    }

    public void set连词数(int 连词数) {
        this.连词数 = 连词数;
    }

    public void set代词数(int 代词数) {
        this.代词数 = 代词数;
    }

    public void set未来预期数(int 未来预期数) {
        this.未来预期数 = 未来预期数;
    }

    public String getTCode() {
        return TCode;
    }

    public String get股票代码() {
        return 股票代码;
    }

    public String get首次披露日期() {
        return 首次披露日期;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public long get文件大小() {
        return 文件大小;
    }

    public String getPdfContent() {
        return pdfContent;
    }

    public int get总页数() {
        return 总页数;
    }

    public int get总段落数() {
        return 总段落数;
    }

    public int get总字数() {
        return 总字数;
    }

    public int get总句数() {
        return 总句数;
    }

    public int get总词数() {
        return 总词数;
    }

    public int getLM正向词数() {
        return LM正向词数;
    }

    public int getLM负向词数() {
        return LM负向词数;
    }

    public int get台大正向词数() {
        return 台大正向词数;
    }

    public int get台大负向词数() {
        return 台大负向词数;
    }

    public int get逆接词数() {
        return 逆接词数;
    }

    public int get次常用字数() {
        return 次常用字数;
    }

    public int getRD词数() {
        return RD词数;
    }

    public int get会计术语数() {
        return 会计术语数;
    }

    public int get连词数() {
        return 连词数;
    }

    public int get代词数() {
        return 代词数;
    }

    public int get未来预期数() {
        return 未来预期数;
    }

    public String getTestStr() {
        return testStr;
    }

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
