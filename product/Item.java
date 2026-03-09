package product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Optional;


public class Item {
    public Item(String name, String description, int cost, int price) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.price = price;
    }

    public Item(BufferedReader in) throws IOException {
        String line = in.readLine();
        if(line.isBlank()) {
            throw new IOException(
                    "Loading item from file failed: expected \"{name: str};{description: str};{price: int};{cost: int}\", got \"\".");
        }
        StringTokenizer st = new StringTokenizer(line, ";");


        String name = st.nextToken();
        String description = st.nextToken();
        int cost = Optional.ofNullable(st.nextToken()).map(str -> Integer.parseInt(str)).orElse(0);
        int price = Optional.ofNullable(st.nextToken()).map(str -> Integer.parseInt(str)).orElse(0);

        this(name, description, cost, price);
    }

    public void save(BufferedWriter out) throws IOException {
        String itemStr = String.format("%s;%s;%s;%s", name, description, "" + price, "" + cost);
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

    private String name;
    private String description;
    private int cost;
    private int price;
}
