package search.engine.cse343;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class UrlTree {

    //inner classes
    private interface INode {

        /**
         * @param query
         * @param index
         */
        public void insert(String query, Integer index);

        /**
         * @param query
         * @return arraylist which contains url indexes
         */
        public ArrayList<Integer> getIndexOfUrls(String query);

        /**
         * write node to file.
         *
         * @param printWriter an initilized PrintWriter object
         * @param prefix      this string will be written before arraylist content. example: aaa 1 2 12
         */
        public void writeToFile(PrintWriter printWriter, String prefix);
    }

    private class Node implements INode {

        private INode[] children = new INode[NUM_LETTERS];
        private ArrayList<Integer> links = null;

        /**
         * @param query
         * @param index
         */
        public void insert(String query, Integer index) {

            if (query.length() == 0) { //insert this node

                if (links == null)

                    links = new ArrayList<Integer>();

                links.add(index);
            } else { //insert to child

                if (children[query.charAt(0) - 'a'] == null)

                    children[query.charAt(0) - 'a'] = new Node();

                children[query.charAt(0) - 'a'].insert(query.substring(1), index);
            }
        }

        /**
         * @param query
         * @return arraylist which contains url indexes
         */
        public ArrayList<Integer> getIndexOfUrls(String query) {

            if (query.length() == 0)

                return links;

            if (children[query.charAt(0) - 'a'] == null)

                return null;

            return children[query.charAt(0) - 'a'].getIndexOfUrls(query.substring(1));
        }

        /**
         * write node to file.
         *
         * @param printWriter an initilized PrintWriter object
         * @param prefix      this string will be written before arraylist content. example: aaa 1 2 12
         */
        public void writeToFile(PrintWriter printWriter, String prefix) {

            if(links != null){ //print node's content
                printWriter.print(prefix);

                ListIterator<Integer> it = links.listIterator();

                while (it.hasNext())

                    printWriter.print(" " + it.next());

                printWriter.print("\n");
            }
            //print children content
            for (int i = 0; i < children.length; ++i)

                if (children[i] != null)

                    children[i].writeToFile(printWriter, prefix + (char) ('a' + i));
        }
    }
    //UrlTree Class

    final static int NUM_LETTERS = 26;
    private INode root = new Node();

    /**
     * @param query
     * @param index
     */
    public void insert(String query, Integer index) {

        root.insert(query, index);
    }

    /**
     * @param query
     * @return arraylist which contains url indexes
     */
    public ArrayList<Integer> getIndexOfUrls(String query) {

        return root.getIndexOfUrls(query);
    }

    /**
     * write tree structure to text file
     *
     * @param printWriter an initilized PrintWriter object
     * @param prefix      this string will be written before leaf's arraylist content. example: aaa 1 2 12
     */
    public void writeToFile(PrintWriter printWriter, String prefix) {

        root.writeToFile(printWriter, prefix);
    }

    @Override
    public String toString() {

        return root.toString();
    }
}


/*
        @Override
        public String toString(){

            StringBuilder stringBuilder = new StringBuilder();

            for(INode child : children) {
                stringBuilder.append(child.toString());
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        }
    }
*/


/*
    private class InternalNode implements INode{

        private INode[] children = new INode[NUM_LETTERS];

        public void insert(String query, Integer index){

            children[query.charAt(0) - 'a'].insert(query.substring(1), index);
        }

        public ArrayList<Integer> getIndexOfUrls(String query){

            return children[query.charAt(0) - 'a'].getIndexOfUrls(query.substring(1));
        }

        public void writeToFile(PrintWriter printWriter, String prefix){

            for(int i=0; i<children.length; ++i)

                children[i].writeToFile(printWriter, prefix + (char)('a' + i));
        }

        @Override
        public String toString(){

            StringBuilder stringBuilder = new StringBuilder();

            for(INode child : children) {
                stringBuilder.append(child.toString());
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        }
    }

    private class Leaf implements INode{

        final private ArrayList<Integer> links = new ArrayList<Integer>();

        public void insert(String query, Integer index){

            if(links.contains(index) == false)

                links.add(index);
        }

        public ArrayList<Integer> getIndexOfUrls(String query) {

            return links;
        }

        public void writeToFile(PrintWriter printWriter, String prefix){

            printWriter.print(prefix);
            ListIterator<Integer> it = links.listIterator();

            while(it.hasNext())

                printWriter.print(" " + it.next());

            printWriter.print("\n");
        }

        @Override
        public String toString(){

            return links.toString();
        }
    }*/
