import java.util.Random; //this lets us to use random class for random numbers.
import java.util.Scanner;

public class ChipotleChatbot { //defines the main class 

    public static void main(String[] args) {
        ChipotleChatbot chatbot = new ChipotleChatbot(); //creates instance of the ChipotleChatbot class.

        chatbot.run(); //runs method to start the chatbot.
    }

    public void run() {
        Scanner scanner = new Scanner(System.in); //creates a Scanner to read user words
        double totalCost = 0.0; //price

        //confused
        String[] confusedReplies = {
            "Hmm, I'm not sure I understand that.",
            "Can you rephrase your question?",
            "I didn't understand that. Could you try again?",
            "Sorry! I don't have an answer for that.",
            "Let's try something else. What would you like to do?"
        };

        //creates food objects ( which are instances of the Food class) with the attributes
        Food burrito = new Food("Burrito", 8.50, "Medium");
        Food tacos = new Food("Tacos", 7.50, "Medium");
        Food lifestyleBowl = new Food("Lifestyle Bowl", 9.50, "Healthy");
        Food salad = new Food("Salad", 7.00, "Healthy");
        Food quesadilla = new Food("Quesadilla", 8.00, "Medium");

        System.out.println("Welcome to the Chipotle Chatbot! My name is Torti");
        System.out.println("I can help you place an order, answer questions, or surprise you with a random color!");
        System.out.println("Type 'exit' anytime to quit.");
        System.out.println("What would you like to do? (Order, Ask, Surprise)");

        while (true) {
            String action = scanner.nextLine().toLowerCase();
            //starts an infinite loop to continuously talk to the user
        

            if (action.equals("exit")) {
                System.out.println("Goodbye! Thanks for visiting.");
                break;
            }

            if (action.equals("order")) { // calls the placeOrder method to do orders
                totalCost = placeOrder(scanner, totalCost, burrito, tacos, lifestyleBowl, salad, quesadilla);
            } else if (action.equals("ask")) { //calls handleQuestion to answer user ?s
                System.out.println("What would you like to know?");
                System.out.println("Try asking about Chipotle's history, food items, or nutrition.");
                String question = scanner.nextLine().toLowerCase();
                handleQuestion(question, confusedReplies);
            } else if (action.equals("surprise")) { //calls giveSurprise to change color
                giveSurprise();
            } else {
                // randome response
                Random random = new Random();
                System.out.println(confusedReplies[random.nextInt(confusedReplies.length)]);
                //^^^handles unrecognized input by picking a random response from confusedReplies.
                System.out.println("Options: Order, Ask, Surprise, Exit.");
            }
        }

        System.out.println("Your final total is: $" + totalCost);
        System.out.println("Thank you for using Chipotle Chatbot!");
    }

    // method to handle placing orders
    private double placeOrder(Scanner scanner, double totalCost, Food burrito, Food tacos, Food lifestyleBowl, Food salad, Food quesadilla) {
        //the parameters have a Scanner object for input, the running totalCost, a list of available Food options

        System.out.println("What would you like to order?");
        System.out.println("Options: Burrito, Tacos, Lifestyle Bowl, Salad, Quesadilla.");
        String orderType = scanner.nextLine().toLowerCase(); //reads user input
        Food selectedFood = null;
        //setting it to null means that no food has been selected yet.

        if (orderType.equals("burrito")) {
            selectedFood = burrito;
        } else if (orderType.equals("tacos")) {
            selectedFood = tacos;
        } else if (orderType.equals("lifestyle bowl")) {
            selectedFood = lifestyleBowl;
        } else if (orderType.equals("salad")) {
            selectedFood = salad;
        } else if (orderType.equals("quesadilla")) {
            selectedFood = quesadilla;
        } else {
            System.out.println("We don't have that item.");
            return totalCost;
    //this matches the user's input to a Food object
    //if no match, it tells user


        }

        System.out.println("What type of protein would you like?");
        System.out.println("Options: Chicken, Steak, Barbacoa, Carnitas, Sofritas, Veggie.");
        String protein = scanner.nextLine();

        System.out.println("Would you like white rice, brown rice, or no rice?");
        String rice = scanner.nextLine();

        System.out.println("Would you like black beans, pinto beans, or no beans?");
        String beans = scanner.nextLine();

        System.out.println("What toppings would you like?");
        System.out.println("Options: Lettuce, Tomato Salsa, Cheese, Sour Cream, Guac ($2.50 extra).");
        String toppings = scanner.nextLine();

        System.out.println("Would you like to add queso? ($1.50 extra)");
        String queso = scanner.nextLine();

        double basePrice = selectedFood.getPrice();
        if (toppings.toLowerCase().contains("guac")) {
            basePrice += 2.50;
        }
        if (queso.equals("yes")) {
            basePrice += 1.50;
        }

        System.out.println("You've ordered a " + selectedFood.getName() + " with " + protein + ", " + rice + ", " + beans + ", and " + toppings + ".");
        System.out.println("Queso added: " + queso);
        double orderCost = basePrice;
        totalCost += orderCost;

        // uses handleSidesAndDrinks to add any sides or drinks to the order.
        totalCost = handleSidesAndDrinks(scanner, totalCost);

        System.out.println("This will cost: $" + orderCost);
        System.out.println("Your current total is: $" + totalCost);
        System.out.println("Anything else? (Order, Ask, Surprise, Exit)");

        return totalCost;
    }

    // method to handle sides and drinks
    private double handleSidesAndDrinks(Scanner scanner, double totalCost) {
        //asks the user to select sides and drinks and updates totalCost

        System.out.println("Would you like any sides? Options: Chips & Guac ($3.50), Chips & Queso ($3.50), Chips ($2.00), None.");
        String side = scanner.nextLine().toLowerCase();
        if (side.contains("chips & guac")) {
            totalCost += 3.50;
        } else if (side.contains("chips & queso")) {
            totalCost += 3.50;
        } else if (side.contains("chips")) {
            totalCost += 2.00;
        }

        System.out.println("Would you like a drink? Options: Fountain Drink ($2.00), Bottled Drink ($2.50), None.");
        String drink = scanner.nextLine().toLowerCase();
        if (drink.contains("fountain drink")) {
            totalCost += 2.00;
        } else if (drink.contains("bottled drink")) {
            totalCost += 2.50;
        }

        return totalCost;
    }

    // method to handle questions
    private void handleQuestion(String question, String[] confusedReplies) {
        Random random = new Random();
        // answers user questions based on keywords like "history" or "nutrition."
//If no keywords are matched, it gives a random confused response.

        // handles specific keywords in the question
        if (question.contains("chipotle")) {
            System.out.println("Chipotle is a fast-casual restaurant chain known for its made-to-order burritos, bowls, tacos, and more. Founded in 1993, it has become famous for its simple, fresh ingredients and customizable menu items.");
        } else if (question.contains("history")) {
            System.out.println("Chipotle was founded in 1993 by Steve Ells, a trained chef, in Denver, Colorado. Initially, it was a small Mexican restaurant, but it quickly gained popularity due to its focus on quality ingredients and a customizable menu. Chipotle's expansion was fueled by a simple mission: to serve food that is both delicious and made with high-quality ingredients.");
        } else if (question.contains("nutrition")) {
            System.out.println("Chipotle offers a wide range of nutritional options. You can customize your order to fit different dietary needs, whether you're looking for a low-calorie option, a vegetarian meal, or a protein-packed dish. For example, you can choose a Lifestyle Bowl with fresh ingredients or go for a protein-heavy burrito with chicken or steak. Chipotle provides nutritional information on its website to help customers make informed choices.");
        } else if (question.contains("food")) {
            System.out.println("Chipotle offers a variety of food items, including burritos, burrito bowls, tacos, quesadillas, salads, and Lifestyle Bowls. Each item can be customized with a selection of proteins (like Chicken, Steak, Barbacoa, and Sofritas), rice, beans, and a variety of toppings such as salsa, cheese, and guacamole.");
        } else if (question.contains("guacamole")) {
            System.out.println("Guacamole is made fresh daily at Chipotle using high-quality Hass avocados, lime juice, cilantro, and other ingredients. It's one of the most popular toppings at Chipotle, and while it does cost extra ($2.50), many customers consider it a must-have for their meals.");
        } else {
            // random unrecognized response
            System.out.println(confusedReplies[random.nextInt(confusedReplies.length)]);
            System.out.println("Try asking about Chipotle's history, nutrition, or food items!");
        }
    }

    // method to give a surprise 
    private void giveSurprise() {
        //Generates a random color using ANSI escape codes 
    
        // ANSI color codes
        final String[] colors = {
            "\u001B[31m", // Red
            "\u001B[32m", // Green
            "\u001B[33m", // Yellow
            "\u001B[34m", // Blue
            "\u001B[35m", // Purple
            "\u001B[36m"  // Cyan or light blue
        };
        final String resetColor = "\u001B[0m"; // resets to default color

        // picks random color
        Random random = new Random();
        String randomColor = colors[random.nextInt(colors.length)];

        // displays suprise message in color
        System.out.println(randomColor + "Surprise! The console text color has changed!" + resetColor);
    }

    // food class
    class Food {

        // represents food items with properties for the name, price, and category.
//insstance variabless
        private String name;
        private double price;
        private String category;
    
        //constructors 
        public Food(String name, double price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }

        //getters

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }
    }
}
