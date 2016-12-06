package search.engine.cse343;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class UrlTree {

    //inner classes
    interface INode {

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

    class Node implements INode {

        final private INode[] children = new INode[NUM_LETTERS];
        private ArrayList<Integer> links = null;

        /**
         * this method for testing
         * @return children array
         */
        public INode[] getChildren(){

            return children;
        }

        /**
         * this method for testing
         * @return links arraylist
         */
        public ArrayList<Integer> getLinks(){

            return links;
        }

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
    final private INode root = new Node();

    /**
     * this method for testing
     * @return root node
     */
    public INode getRoot(){

        return root;
    }

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