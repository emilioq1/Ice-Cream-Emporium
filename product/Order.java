package product;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Optional;

import person.Customer;


public class Order {
    public Order(Customer customer) {
        this.customer = customer;
        this.servings = new ArrayList<>();
    }

    public Order(BufferedReader in) throws IOException {
        Customer customer = new Customer(in);
        this(customer);

        String line = in.readLine();
        if(line.isBlank()) {
            throw new IOException(
                    "Loading order from file failed: Wrong header, expected \"Servings;{size: int}\", got \"\".");
        }
        StringTokenizer st = new StringTokenizer(line, ";");

        String identifier = st.nextToken();
        int numServing = Optional.ofNullable(st.nextToken()).map(str -> Integer.parseInt(str)).orElse(0);

        if(identifier == "Servings") {
            line = in.readLine();
            for(int i = 0; i < numServing; i++) {
                this.servings.add(new Serving(in));
            }
        }
    }

    public void save(BufferedWriter out) throws IOException {
        customer.save(out);

        out.write("Servings;" + servings.size());
        out.newLine();
        for(Serving s : servings) {
            s.save(out);
            out.newLine();
        }
    }

    public void addServing(Serving serving) {
        servings.add(serving);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String newLine = "<br/>";

        for(Serving s : servings) {
            str.append(s);
            str.append(newLine);
        }

        return str.toString();
    }

    public int price() {
        int price = 0;

        for(Serving s : servings) {
            price += s.price();
        }

        return price;
    }

    public Customer getCustomer() {
        return customer;
    }

    private Customer customer;
    private ArrayList<Serving> servings;
}
