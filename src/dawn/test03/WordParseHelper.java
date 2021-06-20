package dawn.test03;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import util.ExcelUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordParseHelper {

    private final static String file_base = "/Users/yangjie/数据分析/语义区分/文本语调分析/";
    private final static String file_lm_words = file_base + "LM词库.xlsx";
    private final static String file_taiwan_words = file_base + "台湾大学词库.xlsx";
    private final static String file_words_会计词汇 = file_base + "会计词汇列表-灵格斯会计词典.xlsx";
    private final static String file_words_代词 = file_base + "代词.xlsx";
    private final static String file_words_连词 = file_base + "连词.xlsx";

    private final static String file_words_汉语次常字 = file_base + "汉语次常字";
    private final static String file_words_逆接成分密度 = file_base + "逆接成分密度";
    private final static String file_words_RD文本信息 = file_base + "RD文本信息";
    private final static String file_words_未来预期 = file_base + "未来预期";

    //LM
    private List<String> list_LM正向;
    private List<String> list_LM负向;
    //台湾
    private List<String> list_台大_正向;
    private List<String> list_台大_负向;

    private List<String> list_汉语次常字;
    private List<String> list_逆接成分密度;
    private List<String> list_RD文本信息;

    private List<String> list_连词;
    private List<String> list_代词;

    private List<String> list_会计词汇;

    private List<String> list_未来预期;

    public static void main(String[] args) {
        WordParseHelper wordParseHelper = new WordParseHelper();
        wordParseHelper.initData();
    }


    public void initData() {
        //LM
        list_LM正向 = getWordList(0, file_lm_words);
        list_LM负向 = getWordList(1, file_lm_words);
        //台湾
        list_台大_正向 = getWordList(0, file_taiwan_words);
        list_台大_负向 = getWordList(1, file_taiwan_words);

        list_连词 = getWordList(0, file_words_连词);
        list_代词 = getWordList(0, file_words_代词);
        list_会计词汇 = getWordList(0, file_words_会计词汇);

        list_汉语次常字 = getWordList(file_words_汉语次常字);
        list_逆接成分密度 = getWordList(file_words_逆接成分密度);
        list_RD文本信息 = getWordList(file_words_RD文本信息);
        list_未来预期 = getWordList(file_words_未来预期);
    }

    public void getWordCount(Model model) {
        if (model == null || model.pdfContent == null || model.pdfContent.length() == 0) {
            return;
        }
        //字的筛选
        for (int i = 0; i < model.pdfContent.length(); i++) {
            String item = String.valueOf(model.pdfContent.charAt(i));
            if (list_汉语次常字 != null && list_汉语次常字.contains(item)) {
                model.次常用字数 += 1;
            }
        }
        Result result = ToAnalysis.parse(model.pdfContent);
        if (result == null || result.getTerms() == null || result.getTerms().size() == 0) {
            return;
        }
        //词的筛选
        for (Term term : result.getTerms()) {
            model.总词数 += 1;
            if (list_逆接成分密度 != null && list_逆接成分密度.contains(term.getName())) {
                model.逆接词数 += 1;
            }
            if (list_RD文本信息 != null && list_RD文本信息.contains(term.getName())) {
                model.RD词数 += 1;
            }
            if (list_LM正向 != null && list_LM正向.contains(term.getName())) {
                model.LM正向词数 += 1;
            }
            if (list_LM负向 != null && list_LM负向.contains(term.getName())) {
                model.LM负向词数 += 1;
            }
            if (list_台大_正向 != null && list_台大_正向.contains(term.getName())) {
                model.台大正向词数 += 1;
            }
            if (list_台大_负向 != null && list_台大_负向.contains(term.getName())) {
                model.台大负向词数 += 1;
            }
            if (list_代词 != null && list_代词.contains(term.getName())) {
                model.代词数 += 1;
            }
            if (list_连词 != null && list_连词.contains(term.getName())) {
                model.连词数 += 1;
            }
            if (list_会计词汇 != null && list_会计词汇.contains(term.getName())) {
                model.会计术语数 += 1;
            }
            if (list_未来预期 != null && list_未来预期.contains(term.getName())) {
                model.未来预期数 += 1;
            }
        }
        return;
    }

    private List<String> getWordList(int sheetIndex, String file_words) {
        List<List<String>> dataList = ExcelUtils.readFromXLSX(file_words, sheetIndex);
        List<String> wordList = new ArrayList<>();
        for (List<String> strList : dataList) {
            if (strList != null && strList.size() > 0) {
                wordList.add(strList.get(0));
            }
        }
        return wordList;
    }

    private List<String> getWordList(String path) {
        String tempStr = ReadFile(path);
        return Arrays.asList(tempStr.split(" "));
    }

    private String ReadFile(String Path) {
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
