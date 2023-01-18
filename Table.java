import java.util.ArrayList;
/**
 * This class model a table which can contain ingredients.
 * The table is a shared resource and therefore thread-safe.
 *
 * @author  Geoffery Koranteng
 * @version January 17th, 2023
 */
public class Table {
    /** The number of sandwiches created at any given time*/
    private int sandwichCount = 0;

    /** The ingredients the table has at any given time*/
    private ArrayList<Chef.Ingredient> ingredients;

    /** The state of table, whether it is empty or not*/
    private boolean empty;

    /**
     * Constructor of the table class.
     */
    public Table() {
        this.empty = true;
    }

    /**
     * This method puts two ingredients on the table. (Thread-Safe)
     *
     * @param ingredients the ingredients being placed on the table.
     */
    public synchronized void put(ArrayList<Chef.Ingredient>  ingredients) throws InterruptedException {
        while(!empty) wait();

        this.ingredients = ingredients;
        this.empty = false;
        notifyAll();
    }

    /**
     * This method removes the ingredients from the table. (Thread-Safe)
     *
     * @return the ingredients on the table
     */
    public synchronized ArrayList<Chef.Ingredient> get() throws InterruptedException {
        while(empty) wait();

        ArrayList<Chef.Ingredient> contents = this.ingredients;
        this.ingredients = null;
        this.empty = true;
        notifyAll();
        return contents;
    }

    /**
     * This method allows the chef to see what ingredients are on the table. (Thread-Safe)
     *
     * @return the ingredients on the table
     */
    public synchronized ArrayList<Chef.Ingredient> peek() throws InterruptedException {
        while(empty) wait();

        return this.ingredients;
    }

    /**
     * This method updates the number of sandwiches created. (Thread-Safe)
     */
    public synchronized void updateSandwichCount() {
        this.sandwichCount += 1;
    }

    /**
     * This method queries the number of sandwiches created. (Thread-Safe)
     *
     * @return the number of sandwiches created
     */
    public synchronized int getSandwichCount() {
        return sandwichCount;
    }
}
