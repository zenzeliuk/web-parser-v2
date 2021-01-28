package ua.mainacademy.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.mainacademy.model.Item;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class ItemPageParser {

    private List<Item> items;
    private Document document;
    private String url;

    public static boolean isItemPage(Document document) {
        String extractCode = document.getElementsByClass("iek-prodcat-catalog-detail-article-wrap").first()
                .getElementsByTag("span").text();
        ;
        return extractCode.contains("Артикул");
    }


    public Item getItemFromPage(String url, Document document) {
        Element productBlock = document.getElementById("content");
        Element navigationBlock = document.getElementById("navstr");


        String name = extractName(document);
        String code = extractCode(productBlock);
        String imageUrl = extractImageUrl(productBlock);
//        String group = extractGroup(productBlock);
//        String specifications = extractSpecifications (productBlock);

        return Item.builder()
                .name(name)
                .code(code)
//                .group(group)
                .url(url)
                .imageUrl(imageUrl)
//                .specifications(specifications)
                .build();

    }

    private static String extractName(Element element) {
        return element.getElementsByTag("h1").first().getElementsByClass("navstring").first().text();
    }

    private static String extractCode(Element element) {
        return element.getElementsByClass("iek-prodcat-catalog-detail-article-wrap").first()
                .getElementsByTag("span").text();
    }

    private static String extractImageUrl(Element element) {
        Element img = element.select("img").first();
        String src = img.attr("src");
        return String.format("https://www.iek.ru%s", src);

    }


//
//    private int extractPrice(Element element) {
//        String price = element.getElementsByAttributeValueStarting("itemprop", "price").first().text();
//        return Integer.valueOf(price.replaceAll("\\D", ""));
//    }
//
//    private static String extractCode(Element element) {
//        return element.getElementsByClass("details single-product__details").first()
//                .getElementsByClass("details__sku").first().text();
//
//    }
//
//    private static String extractName(Element element) {
//        return element.getElementsByClass("container").first().select("h1").first().text();
//    }
//

}
