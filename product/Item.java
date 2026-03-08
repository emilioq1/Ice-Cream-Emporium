package product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Item {
    public Item(String name, String description, int cost, int price) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.price = price;
    }

    public Item(BufferedReader in) throws IOException {
        String name = "";
        String description = "";
        int cost = -1;
        int price = -1;

        String line = in.readLine();
        if(!line.isBlank()) name = line;
        else throw new IOException("Line is blank when it should not be.");

        line = in.readLine();
        if(!line.isBlank()) description = line;
        else throw new IOException("Line is blank when it should not be.");

        line = in.readLine();
        if(!line.isBlank()) cost = Integer.parseInt(line);
        else throw new IOException("Line is blank when it should not be.");

        line = in.readLine();
        if(!line.isBlank()) price = Integer.parseInt(line);
        else throw new IOException("Line is blank when it should not be.");

        this(name, description, cost, price);
    }

    public void save(BufferedWriter out) throws IOException {
        out.write(name);
        out.newLine();

        out.write(description);
        out.newLine();

        out.write("" + price);
        out.newLine();

        out.write("" + cost);
        out.newLine();
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

    private String name;
    private String description;
    private int cost;
    private int price;
}
