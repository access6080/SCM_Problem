import java.util.ArrayList;

/**
 * This class models an agent which creates and infinite amount of  ingredients,
 * which is placed on a table for chefs to construct and consume.
 *
 * @author  Geoffery Koranteng
 * @version January 17th, 2023
 */
public class Agent implements Runnable {
    /** An array of all ingredients that can be produced*/
    private final Chef.Ingredient[] ingredients = {
            Chef.Ingredient.Bread,
            Chef.Ingredient.Jam,
            Chef.Ingredient.Peanut_Butter
    };

    /** The table where produced ingredients are placed*/
    private final Table table;

    /**
     * Constructor for the Agent Class
     *
     * @param table The table where produced ingredients are placed
     */
    public Agent(Table table) {
        this.table = table;
    }

    /**
     * This method produces two random ingredients that can be placed on a table
     *
     * @return an arraylist of the ingredients produced.
     */
    public ArrayList<Chef.Ingredient> generateIngredients() {
        if(table.getSandwichCount() == Chef.MAX_SANDWICHES) return  null;
        ArrayList<Chef.Ingredient> tableIngredients =  new ArrayList<>();

        while (tableIngredients.size() != 2){
            Chef.Ingredient ingr = this.ingredients[(int) (Math.random() * 3)];
            if(!tableIngredients.contains(ingr)) tableIngredients.add(ingr);
        }

        return tableIngredients;
    }

    /**
     * Implemented from the Runnable interface,
     * this method is called  when a thread is started
     */
    @Override
    public void run() {
        do {
            try {
                table.put(generateIngredients());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (table.getSandwichCount() < 20);
    }
}
