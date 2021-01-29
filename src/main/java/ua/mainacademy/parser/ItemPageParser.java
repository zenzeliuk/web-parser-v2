package ua.mainacademy.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.mainacademy.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemPageParser {

    public static boolean isItemPage(Document document) {
        String extractCode = document.getElementsByClass("iek-prodcat-catalog-detail-article-wrap").first()
                .getElementsByTag("span").text();
        ;
        return extractCode.contains("Артикул");
    }

    public static Item getItemFromPage(String url, Document document) {
        Element productBlock = document.getElementById("content");
        Element navigationBlock = document.getElementById("navstr");

        String name = extractName(document);
        String code = extractCode(productBlock);
        String imageUrl = extractImageUrl(productBlock);
        String group = extractGroup(navigationBlock);
        HashMap specifications = extractSpecifications(productBlock);

        return Item.builder()
                .name(name)
                .code(code)
                .group(group)
                .url(url)
                .imageUrl(imageUrl)
                .specifications(specifications)
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
        String image = img.attr("data-zoom-image");

        return String.format("https://www.iek.ru%s", image);
    }

    private static String extractGroup(Element element) {
        Elements groupElements = element.getElementsByTag("a");
        List<String> groups = new ArrayList<>();
        for (Element el : groupElements) {
            groups.add(el.text());
        }
        return StringUtil.join(groups, "/");
    }

    private static HashMap extractSpecifications(Element element) {
        Element table = element.getElementsByTag("tbody").get(1);
        Elements tableElements = table.getElementsByTag("tr");

        HashMap<String, String> specificationMap = new HashMap<>();
        for (Element el : tableElements) {
            specificationMap.put(el.getElementsByTag("strong").text(),
                    el.getElementsByTag("td").last().text());
        }
        return specificationMap;
    }

}
