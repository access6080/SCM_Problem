/**
 * The main entry point of the Sandwich making Chefs Problem program.
 * It instantiates all the threads required to solve the problem, and the shared resources.
 *
 * @author  Geoffery Konranteng
 * @version January 17th, 2023
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        /* The table object, which is shared among all threads */
        Table table = new Table();

        /* Instantiate thread Objects */
        Thread breadChefThread = new Thread(new Chef(Chef.Ingredient.Bread, table), "Bread Chef");
        Thread jamChefThread = new Thread(new Chef(Chef.Ingredient.Jam, table), "Jam Chef");
        Thread peanutButterChefThread = new Thread(
                new Chef(Chef.Ingredient.Peanut_Butter, table),
                "Peanut Butter Chef"
        );
        Thread agentThread = new Thread(new Agent(table), "Agent");

        /* Start thread Objects */
        breadChefThread.start();
        jamChefThread.start();
        peanutButterChefThread.start();
        agentThread.start();

        /* Join thread Objects so main program waits for their completion */
        breadChefThread.join();
        jamChefThread.join();
        peanutButterChefThread.join();
        agentThread.join();


        System.out.println("Program ended - " + table.getSandwichCount() + " sandwiches created and consumed");
    }
}
