package ua.mainacademy;


import ua.mainacademy.model.Item;
import ua.mainacademy.service.RouterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ApplicationRun {

    private static final Logger LOGGER = Logger.getLogger(ApplicationRun.class.getName());

    public static void main(String[] args) {

        String url = "https://www.iek.ru/products/catalog/shkafy_boksy_i_prinadlezhnosti_k_nim/obolochki_metallicheskie/korpusa_metallicheskie_shchmp/";

        List<Item> items = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = Collections.synchronizedList(new ArrayList<>());

        RouterService routerService = new RouterService(items, threads, url);
        threads.add(routerService);
        routerService.start();

        do {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!threadsAreNotActive(threads));

        LOGGER.info(String.format("Items were extracted. Amount = %s", items.size()));


    }




    private static boolean threadsAreNotActive(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive() || thread.getState().equals(Thread.State.NEW)) {
                return false;
            }
        }
        return true;
    }
}










