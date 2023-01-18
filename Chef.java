import java.util.ArrayList;
/***
 * This class models a chef making a peanut butter sandwich.
 * It has an infinite supply of one of the three ingredients needed to make the sandwich.
 * To make a sandwich, it requests two ingredients from an agent and combines the two ingredients with its own
 * to create a sandwich
 *
 * @author  Geoffery Koranteng
 * @version January 16th, 2023
 */
public class Chef implements Runnable {
    /** Maximum number of sandwiches that can be created */
    public static int MAX_SANDWICHES = 20;

    /** The ingredients needed to make the sandwich */
    public enum Ingredient {
        Bread,
        Peanut_Butter,
        Jam
    }

    /** The ingredient which the chef object has an infinite supply of */
    private final Ingredient infiniteSupply;

    /** The agent providing the other ingredients needed (Shared Resource) */
    private final Table table;

    /**
     * Constructor of Chef class.
     * Takes an ingredient representing its infinite supply and an agent which distributes ingredients.
     *
     * @param ingredient the type of ingredient which is infinite for the chef .
     * @param table The table which contains ingredients needed to make a sandwich.
     */
    public Chef(Ingredient ingredient, Table table){
        this.infiniteSupply = ingredient;
        this.table = table;
    }

    /**
     * This method attempts to get two ingredients from the agent.
     * If it succeeds, it checks if the ingredients are the right ingredients it needs to make a sandwich
     *
     * @return returns a string if it creates and consumes a sandwich or null if it fails its attempt
     */
    public String getIngredient() throws InterruptedException {
        Thread.sleep(1000);
        if(table.getSandwichCount() == MAX_SANDWICHES) return null;

        if(table.peek().contains(this.infiniteSupply)) return null;

        ArrayList<Ingredient> ingredients = table.get();
        if(ingredients == null) return null;

        return makeSandwich();
    }

    /**
     * This method represents the chef making the sandwich
     *
     * @return returns a string is saying who created and consumed the sandwich.
     */
    private String makeSandwich() {
        this.table.updateSandwichCount();
        return Thread.currentThread().getName() + " made and consumed sandwich " + this.table.getSandwichCount() + " of 20";
    }

    /**
     * Implemented from the Runnable interface,
     * this method is called  when a thread is started
     */
    @Override
    public void run() {
        do {
            try {

                String res = getIngredient();
                if (res != null) System.out.println(res);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        } while (table.getSandwichCount() < 20);
    }
}
