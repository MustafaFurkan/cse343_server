package search.engine.cse343;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UrlTreeTest {

    @org.junit.Test
    public void insert() throws Exception {

        UrlTree tree = new UrlTree();
        UrlTree.Node root = (UrlTree.Node)tree.getRoot();

        //1-2-3-4
        assertNull(root.getLinks()); //2
        tree.insert("", 0); //1
        assertTrue(root.getLinks().size() == 1 && root.getLinks().get(0).equals(0)); //4

        //1-2-4
        assertNotNull(root.getLinks()); //2
        tree.insert("", 0); //1
        assertTrue(root.getLinks().size() == 2
                && root.getLinks().get(0).equals(0)
                && root.getLinks().get(1).equals(0)); //4

        //1-5-6-7
        assertNull(root.getChildren()[0]); //5
        tree.insert("a", 0); //1
        assertNotNull(root.getChildren()[0]); //6
        assertTrue(((UrlTree.Node)root.getChildren()[0]).getLinks().size() == 1
                && ((UrlTree.Node)root.getChildren()[0]).getLinks().get(0).equals(0)); //7

        //1-5-7
        assertNotNull(root.getChildren()[0]); //5
        tree.insert("a", 0); //1
        assertTrue(((UrlTree.Node)root.getChildren()[0]).getLinks().size() == 2
                && ((UrlTree.Node)root.getChildren()[0]).getLinks().get(0).equals(0)
                && ((UrlTree.Node)root.getChildren()[0]).getLinks().get(1).equals(0)); //7
    }

    @org.junit.Test
    public void getIndexOfUrls() throws Exception {

        // I tested insert method above, so I'm using it for testing getIndexOfUrls method.
        UrlTree tree = new UrlTree();
        UrlTree.Node root = (UrlTree.Node)tree.getRoot();

        //1-2
        assertNull(root.getIndexOfUrls(""));

        root.insert("", 0);
        assertTrue(root.getIndexOfUrls("").size() == 1
        && root.getIndexOfUrls("").get(0).equals(0));

        //1-3-4
        assertNull(root.getChildren()[0]); //3
        assertNull(root.getIndexOfUrls("a")); //1

        //1-3-5
        root.insert("a",0);

        assertNotNull(root.getChildren()[0]); //3
        ArrayList<Integer> instance = root.getIndexOfUrls("a"); //1
        assertTrue(instance.size() == 1 && instance.get(0).equals(0)); //5
    }
}