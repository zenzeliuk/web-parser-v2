package ua.mainacademy;


import org.jsoup.nodes.Document;
import ua.mainacademy.parser.ItemPageParser;
import ua.mainacademy.service.DocumentExtractorService;

public class ApplicationRun {
    public static void main(String[] args) {

        String url = "https://www.iek.ru/products/catalog/shkafy_boksy_i_prinadlezhnosti_k_nim/korpusa_metallicheskie_modulnye/korpusa_metallicheskie_raspredelitelnye/shchity_racpredelitelnye_serii_pro/shchrn_serii_pro_ip31/korpus_metallicheskiy_raspredelitelnyy_shchrn_2x24z_0_36_ukhl3_ip31_pro_iek";
        DocumentExtractorService documentExtractorService = new DocumentExtractorService();

        ItemPageParser aa = new ItemPageParser();

        System.out.println(aa.getItemFromPage(url, documentExtractorService.getDocument(url)));
        System.out.println(ItemPageParser.isItemPage(documentExtractorService.getDocument(url)));
    }
}
