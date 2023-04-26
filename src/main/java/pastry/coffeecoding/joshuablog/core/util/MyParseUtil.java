package pastry.coffeecoding.joshuablog.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyParseUtil {
    public static String getThumbnail(String html){ // content
        String thumbnail;
        Document doc = Jsoup.parse(html);
        Elements els = doc.select("img"); // 여러건

        if(els.size() == 0){
            thumbnail= "/images/dora.png";
        }else{
            Element el = els.get(0);
            thumbnail = el.attr("src");
        }
        return thumbnail;
    }
}
