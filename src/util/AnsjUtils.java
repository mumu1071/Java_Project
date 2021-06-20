package util;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;

public class AnsjUtils {

    public static void main(String[] args) {
        Result result = ToAnalysis.parse("让战士们过一个欢乐祥和的新春佳节。");
        System.out.println(result);

    }
}
