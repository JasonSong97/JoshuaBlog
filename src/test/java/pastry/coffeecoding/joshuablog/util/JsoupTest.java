package pastry.coffeecoding.joshuablog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class JsoupTest {
    String html = "<div id=\"weather\">10도</div>\n" +
            "<div class=\"loc\"><h1>서울</h1></div>";

    @Test
    public void jsoup_test(){
        Document doc = Jsoup.parse(html); // html 형태를 만들어 낸다.
        Elements elements = doc.select("#weather"); // css 선택자 -> 배열 리턴
        System.out.println(elements.get(0).text()); // String

        Elements elements2 = doc.select(".loc");
        System.out.println(elements2.get(0).text()); // html 같이
    }

    @Test
    public void coffee_test() throws Exception {
        Document doc = Jsoup.connect("https://www.starbucks.co.kr/coffee/product_list.do").get();
        System.out.println(doc.toString());
    }
}