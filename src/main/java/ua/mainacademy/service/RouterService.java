package ua.mainacademy.service;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import ua.mainacademy.model.Item;
import ua.mainacademy.parser.ItemPageParser;
import ua.mainacademy.parser.NavigationPageParser;

import java.util.List;

@AllArgsConstructor
public class RouterService extends Thread {

    private List<Item> items;
    private List<Thread> threads;
    private String url;

    @Override
    public void run() {
        parsePage(url);
    }

    public void parsePage(String url) {
        Document document = DocumentExtractorService.getDocument(url);
        if (ItemPageParser.isItemPage(document)) {
            ItemPageParser itemPageParser = new ItemPageParser(items, document, url);
            threads.add(itemPageParser);
            itemPageParser.start();
        }

        if (NavigationPageParser.isNavigationPage(document)) {
            NavigationPageParser navigationPageParser = new NavigationPageParser(items, threads, document, url);
            navigationPageParser.start();
        }
    }
}
