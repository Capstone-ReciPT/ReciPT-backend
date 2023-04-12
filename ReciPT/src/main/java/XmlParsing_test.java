import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParsing_test {

    // tag값의 정보를 가져오는 메소드
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static void main(String[] args) {
        int page = 1; // 페이지 초기값
        int startIdx = 1;
        int endIdx = 2; // 100개씩 호출
        try {
            while (true) {
                // parsing할 url 지정(API 키 포함해서)
                String url = "https://openapi.foodsafetykorea.go.kr/api/736edd38b0f847b68c60/COOKRCP01/xml/" + startIdx + "/" + endIdx;

                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                // root tag
                doc.getDocumentElement().normalize();
                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("row");
                System.out.println("파싱할 리스트 수 : " + nList.getLength());

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        System.out.println("######################");
                        //System.out.println(eElement.getTextContent());
                        System.out.println("음식이름  : " + getTagValue("RCP_NM", eElement));
                        System.out.println("식재료  : " + getTagValue("RCP_PARTS_DTLS", eElement));
                        System.out.println("조리방법  : " + getTagValue("RCP_WAY2", eElement));
                        System.out.println("해시태그 : " + getTagValue("HASH_TAG", eElement));
                        System.out.println("썸네일  : " + getTagValue("ATT_FILE_NO_MK", eElement));
                        int manual = 1;
                        while (true) {
                            System.out.println("만드는 법  : " + getTagValue("MANUAL0" + manual, eElement));
                            System.out.println("만드는 이미지  : " + getTagValue("MANUAL_IMG0" + manual, eElement));

                            manual += 1;
                            if (getTagValue("MANUAL0" + manual, eElement) == null && getTagValue("MANUAL0" + (manual + 1), eElement) == null && getTagValue("MANUAL0" + (manual + 2), eElement) == null) {
                                break;
                            }
                        }

                    }    // for end
                }    // if end

                startIdx = endIdx;
                endIdx += 1;
                System.out.println("page number : " + page);
                page += 1;
                if (page > 1114) {
                    break;
                }
            }    // while end

        } catch (Exception e) {
            e.printStackTrace();
        }    // try~catch end
    }    // main end
}    // class end