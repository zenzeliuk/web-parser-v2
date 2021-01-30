package ua.mainacademy.parser;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.mainacademy.model.Item;
import ua.mainacademy.service.RouterService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NavigationPageParser extends Thread {

    private List<Item> items;
    private List<Thread> threads;
    private Document document;
    private String url;

    @Override
    public void run() {
        parsePage(url);
    }

    public static boolean isNavigationPage(Document document) {

        return document.getElementsByClass("iek-prodcat-catalog-detail-article-wrap").isEmpty();
    }

    private void parsePage(String url) {

        List<String> itemLinks = new ArrayList<>();

        int numbersOfLink = document.getElementsByClass("iek-prodcat-catalog-section-item__name").size();
        for (int i = 0; i < numbersOfLink; i++) {
            Element element = document.getElementById("cat_tab_helb5C1_cont").getElementsByClass("iek-prodcat-catalog-section-item__name").get(i);
            String itemLink = String.format("https://www.iek.ru%s", element.getElementsByTag("a").attr("href"));
            itemLinks.add(itemLink);
        }

        for (String itemLink : itemLinks) {
            RouterService routerService = new RouterService(items, threads, itemLink);
            threads.add(routerService);
            routerService.start();
        }


        if (!url.contains("?pagen=")) {
            int lastPage = document.getElementsByClass("iek-prodcat-catalog-section-pager__pagen").size();
            for (int i = 2; i <= lastPage; i++) {
                String nextPageUrl = url + "?pagen=" + i;
                RouterService routerService = new RouterService(items, threads, nextPageUrl);
                threads.add(routerService);
                routerService.start();
            }
        }

    }

}




















