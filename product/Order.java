package product;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import person.Customer;

public class Order {
    public Order() {
        this.customer = null;
        servings = new ArrayList<>();
    }


    public Order(Customer customer) {
        this.customer = customer;
        servings = new ArrayList<>();
    }

    public Order(BufferedReader in) throws IOException {
        Customer customer = new Customer(in);
        this(customer);
        
        int numServing = Integer.parseInt(in.readLine());
        for(int i = 0; i < numServing; i++) {
            Serving serving = new Serving(in);
            servings.add(serving);
        }
    }

    public void save(BufferedWriter out) throws IOException {
        customer.save(out);
        out.newLine();
        out.write("" + servings.size());
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
