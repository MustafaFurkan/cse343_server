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
    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private final static int NUM_WORD_CONTENT = 10;

    private String getContent(String[] tokens, int startIndex){


      StringBuilder stringBuilder = new StringBuilder();
 /* TODO
        stringBuilder.append(tokens[startIndex]);

        for(int i=startIndex+1;
            i<tokens.length && i<NUM_WORD_CONTENT && tokens[i].length()>0
                    && (Character.isLetter(tokens[i].charAt(0)) || Character.isDigit(tokens[i].charAt(0)))
                ;++i) {

            stringBuilder.append(" ");

            for(int j=0;
                j<tokens[i].length() && (Character.isLetter(tokens[i].charAt(j)) || Character.isDigit(tokens[i].charAt(j)))
                    ;++j)

                stringBuilder.append(tokens[i].charAt(j));
        }*/
        return stringBuilder.toString();
    }

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

            String[] tokens = text.split("/([A-Za-z@.])\\w+/g");

            for(int i=0; i<tokens.length; ++i) {

                String str = tokens[i];

                if (str.length() > 0 && alphabet.contains("" + Character.toLowerCase(str.charAt(0)))) {

                    urlForest.insert(str.substring(0, 1).toLowerCase(), new Result(htmlParseData.getTitle(), url, getContent(tokens, i)));
                }
                if (str.length() > 1 && alphabet.contains("" + Character.toLowerCase(str.charAt(0))) &&
                        alphabet.contains("" + Character.toLowerCase(str.charAt(1)))) {

                    urlForest.insert(str.substring(0, 2).toLowerCase(), new Result(htmlParseData.getTitle(), url, getContent(tokens, i)));
                }
                if (str.length() > 2 && alphabet.contains("" + Character.toLowerCase(str.charAt(0))) &&
                        alphabet.contains("" + Character.toLowerCase(str.charAt(1))) &&
                        alphabet.contains("" + Character.toLowerCase(str.charAt(2)))) {

                    urlForest.insert(str.substring(0, 3).toLowerCase(), new Result(htmlParseData.getTitle(), url, getContent(tokens, i)));
                }
            }

            if(urlForest.getNumUrls() > 2000){
                String path = "search_engine_server.txt";
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
