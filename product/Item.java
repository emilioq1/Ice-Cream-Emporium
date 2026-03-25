package product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Optional;


public class Item {
    public Item(String name, String description, int cost, int price) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.price = price;
    }

    public Item(BufferedReader in) throws IOException {
        String line = in.readLine().strip();
        // Handles the blank line after each item.
        while(line.isBlank()) {
            line = in.readLine().strip();
        }

        String[] tokens = line.split(";", -1);
        if(tokens.length != 4) {
            throw new IOException("Item: 4 tokens were expected. Got " + tokens.length + " tokens instead.");
        }

        String name = tokens[0].strip();
        String description = tokens[1].strip();
        int cost = Optional.ofNullable(tokens[2].strip()).map(str -> Integer.parseInt(str)).orElse(0);
        int price = Optional.ofNullable(tokens[3].strip()).map(str -> Integer.parseInt(str)).orElse(0);

        this(name, description, cost, price);
    }

    public void save(BufferedWriter out) throws IOException {
        String itemStr = String.format("%s;%s;%s;%s", name, description, "" + cost, "" + price);
        out.write(itemStr);
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public int price() {
        return price;
    }

    public int cost() {
        return cost;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toStringDebug() {
        String nameStr = String.format("name: \"%s\"", name);
        String descriptionStr = String.format("description: \"%s\"", description);
        String costStr = String.format("cost: %d", cost);
        String priceStr = String.format("price: %d", price);

        return String.join(", ", nameStr, descriptionStr, costStr, priceStr);
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item)o;

        boolean nameEqual = this.name.equals(item.name);
        boolean descriptionEqual = this.description.equals(item.description);
        boolean costEqual = this.cost == item.cost;
        boolean priceEqual = this.price == item.price;

        return nameEqual && descriptionEqual && costEqual && priceEqual;
    }

    private String name;
    private String description;
    private int cost;
    private int price;
}
