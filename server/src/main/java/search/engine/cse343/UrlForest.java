package search.engine.cse343;

import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class UrlForest {

    //data field
    //path of the folder that contains screenshots
    final private String screenshotsFolder = "screenshots";
    private UrlTree[] trees = new UrlTree[UrlTree.NUM_LETTERS];
    final private ArrayList<Result> results = new ArrayList<Result>();

    public UrlForest(){

        for(int i=0; i<UrlTree.NUM_LETTERS; ++i)

            trees[i] = new UrlTree();
    }

    /**
     * @return number of results
     */
    public int getNumUrls(){

        return results.size();
    }

    /**
     * @param query
     * @param result
     */
    public void insert(String query, Result result) {

        if(results.contains(result) == false)

            results.add(result);

        trees[query.charAt(0) - 'a'].insert(query.substring(1), results.indexOf(result));
    }

    /**
     * @param query
     * @return arraylist which contains url indexes
     */
    public ArrayList<Integer> getIndexOfUrls(String query){

        return trees[query.charAt(0)-'a'].getIndexOfUrls(query.substring(1));
    }

    /**
     * @param index
     * @return result at "index"
     */
    public Result getResult(int index){

        return results.get(index);
    }

    /**
     * write forest structure to text file
     * @param filename path
     */
    public void writeToFile(String filename){

        try {
            PrintWriter printWriter = new PrintWriter(filename);

            printWriter.println(results.size());

            //write results
            ListIterator<Result> it = results.listIterator();

            while(it.hasNext()) {

                Result result = it.next();

                printWriter.println(result.getUrl());
                printWriter.println(result.getTitle());
              //  printWriter.println(result.getContent()); TODO
            }

            for(int i=0; i<trees.length; ++i)

                trees[i].writeToFile(printWriter, "" + (char)('a' + i));

            printWriter.close();

        }catch (Exception e){

            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * restore forest structure from text file
     * @param filename path
     */
    public void readFromFile(String filename){

        try{

            FileInputStream fis = new FileInputStream(filename);
            Scanner scanner = new Scanner(fis);

            //restore results
            int numResults = scanner.nextInt();
            scanner.nextLine();

            for(int i=0; i<numResults; ++i){

                String url = scanner.nextLine();
                String title = scanner.nextLine();
//                String content = scanner.nextLine();

                results.add(new Result(title, url, ""));
            }

            while(scanner.hasNextLine()){

                String query = scanner.next();

                while(scanner.hasNextInt()){

                    int index = scanner.nextInt();

                    this.insert(query, getResult(index));
                }

                scanner.nextLine();
            }

            scanner.close();
            fis.close();
        }catch (Exception e){

            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public String toString(){

        StringBuilder stringBuilder = new StringBuilder();

        for(UrlTree tree : trees){

            stringBuilder.append("\n~--~\n");
            stringBuilder.append(tree.toString());
        }

        return stringBuilder.toString();
    }
}
