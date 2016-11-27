package search.engine.cse343;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.Set;
import java.util.regex.Pattern;

public class Crawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");

    private UrlForest urlForest = new UrlForest();
    private final static String abc = "abcdefghijklmnopqrstuvwxyz";

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
                //&& href.startsWith("http://www.ics.uci.edu/");
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            String[] tokens = text.split(" ");

            for(String str : tokens) {

                if (abc.contains("" + Character.toLowerCase(str.charAt(0))) &&
                        abc.contains("" + Character.toLowerCase(str.charAt(1))) &&
                        abc.contains("" + Character.toLowerCase(str.charAt(2)))) {
                    urlForest.insert(str.substring(0, 3).toLowerCase(), new Result(htmlParseData.getTitle(), url));
                }
            }

            if(urlForest.getNumUrls() > 1000){
                String path = "/home/user/search_engine_server.txt";
                urlForest.writeToFile(path);
                System.out.println("Tree saved at " + path);
                System.exit(0);
            }

            System.err.println("size: " + urlForest.getNumUrls());


//            System.out.println("Text length: " + text.length());
//            System.out.println("Html length: " + html.length());
//            System.out.println("Number of outgoing links: " + links.size());
        }
    }
}
