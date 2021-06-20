package util;

import com.google.gson.Gson;

import java.io.*;

public class ReadFromFile {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String fileName = "/Users/yangjie/数据分析/祁博/案件/json/委托创作合同纠纷/2jsonFile.json";
        //readFileByBytes(fileName);
        //readFileByChars(fileName);
        //readFileByLines(fileName);
        String str = ReadFile(fileName);
        Gson gson = new Gson();
//        gson.fromJson()
        System.out.println(str);
    }

    /**
     * 读取文件
     *
     * @param Path
     * @return
     */
    public static String ReadFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }
}
