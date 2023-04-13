package Komoran;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import java.util.List;

public class tutorials {

    public static void main(String[] args) {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        String strToAnalyze = "타이머 5분 세팅해줘"; //"계란, 양파, 시금치, 당근으로 할 수 있는 요리가 뭐가 있을까?";

        KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

        System.out.println(analyzeResultList.getPlainText());

        List<Token> tokenList = analyzeResultList.getTokenList();
        for (Token token : tokenList) {
            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }

        KomoranResult result = komoran.analyze(strToAnalyze);
        System.out.println("result.getNouns() = " + result.getNouns()); // "계란, 양파, 시금치, 당근으로 할 수 있는 요리가 뭐가 있을까?"; => 명사 추출

        System.out.println("result.getMorphesByTags(\"SN\") = " + result.getMorphesByTags("SN"));
        System.out.println("result.getMorphesByTags(\"NNP\") = " + result.getMorphesByTags("NNP"));


    }
}
