package search.engine.cse343;

import java.util.ArrayList;

public class TestForest {

    public static void main(String[] args) {

/*


        urlForest.insert("aaa", new Result("google", "https://www.google.com"));

        urlForest.saveScreenShots();*/

   /*     UrlForest urlForest2 = new UrlForest();

    */
         UrlForest urlForest = new UrlForest();
        urlForest.insert("a", new Result("www", "www"));
        urlForest.insert("aa", new Result("www", "www2"));
        urlForest.insert("aaa", new Result("www", "www"));

        ArrayList<Integer> arr = urlForest.getIndexOfUrls("a");
        ArrayList<Integer> arr2 = urlForest.getIndexOfUrls("aa");
        ArrayList<Integer> arr3 = urlForest.getIndexOfUrls("aaa");

        System.out.println(arr);
        System.out.println(arr2);
        System.out.println(arr3);


        urlForest.writeToFile("/home/user/temp.txt.txt");/*
        urlForest2.readFromFile("/home/user/temp.txt.txt");
        urlForest2.writeToFile("/home/user/temp2.txt.txt");

*/

    }
}
