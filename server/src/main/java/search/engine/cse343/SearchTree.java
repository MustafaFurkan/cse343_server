package search.engine.cse343;

/**
 * Created by deniz on 04.11.2016.
 */
import java.util.ArrayList;

public interface SearchTree < E
        extends Comparable < E >> {
    /** Inserts item where it belongs in the tree.
     @param item The item to be inserted
     @return true If the item is inserted, false if the
     item was already in the tree.
     */
    boolean add(E item);

    /** Determine if an item is in the tree
     @param target Item being sought in tree
     @return true If the item is in the tree, false otherwise
     */
    //boolean contains(E target);

    /** Find an object in the tree
     @return A reference to the object in the tree that compares
     equal as determined by compareTo to the target. If not found
     null is returned.
      * @param target The item being sought
     */
    ArrayList<Integer> find(E target);

    /** Removes target from tree.
     @param target Item to be removed
     @return A reference to the object in the tree that compares
     equal as determined by compareTo to the target. If not found
     null is returned.
     @post target is not in the tree
     */
    //E delete(E target);
}