package emporium;

import product.IceCreamFlavor;
import product.MixInFlavor;
import product.Container;
import product.Order;

import person.Customer;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Emporium {
    public Emporium() {
        mixInFlavors = new ArrayList<MixInFlavor>();
        iceCreamFlavors = new ArrayList<IceCreamFlavor>();
        containers = new ArrayList<Container>();
        orders = new ArrayList<Order>();
        customers = new ArrayList<Customer>();
    }

    public Emporium(BufferedReader in) throws IOException {
        this();
        String line = new String();
        int num = -1;

        while((line = in.readLine()) != null) {
            switch(line) {
                case "IceCreamFlavors":
                    line = in.readLine();
                    num = Integer.parseInt(line);

                    for(int i = 0; i < num; i++) {
                        iceCreamFlavors.add(new IceCreamFlavor(in));
                        line = in.readLine();
                    }

                    break;
                case "MixInFlavors":
                    line = in.readLine();
                    num = Integer.parseInt(line);

                    for(int i = 0; i < num; i++) {
                        mixInFlavors.add(new MixInFlavor(in));
                        line = in.readLine();
                    }

                    break;
                case "Containers":
                    line = in.readLine();
                    num = Integer.parseInt(line);

                    for(int i = 0; i < num; i++) {
                        containers.add(new Container(in));
                        line = in.readLine();
                    }

                    break;
                case "Orders":
                    line = in.readLine();
                    num = Integer.parseInt(line);

                    for(int i = 0; i < num; i++) {
                        orders.add(new Order(in));
                        line = in.readLine();
                    }

                    break;
                default:
                    break;
            }
        }
    }

    public void save(BufferedWriter out) throws IOException {
        // Ice Cream Flavors: Name, Description, Price, Cost
        // Mix In Flavors: Name, Description, Price, Cost
        // Containers: Name, Description, Max Scoops
        // Orders: Customer, Servings
        out.write("IceCreamFlavors");
        out.newLine();
        out.write("" + iceCreamFlavors.size());
        out.newLine();
        for(IceCreamFlavor i : iceCreamFlavors) {
            i.save(out);
            out.newLine();
        }

        out.write("MixInFlavors");
        out.newLine();
        out.write("" + mixInFlavors.size());
        out.newLine();
        for(MixInFlavor m : mixInFlavors) {
            m.save(out);
            out.newLine();
        }

        out.write("Containers");
        out.newLine();
        out.write("" + containers.size());
        out.newLine();
        for(Container c : containers) {
            c.save(out);
            out.newLine();
        }

        out.write("Orders");
        out.newLine();
        out.write("" + orders.size());
        out.newLine();
        for(Order o : orders) {
            o.save(out);
            out.newLine();
        }
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addIceCreamFlavor(IceCreamFlavor flavor) {
        iceCreamFlavors.add(flavor);
    }

    public void addMixInFlavor(MixInFlavor flavor) {
        mixInFlavors.add(flavor);
    }

    public void addContainer(Container container) {
        containers.add(container);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Object[] iceCreamFlavors() {
        return iceCreamFlavors.toArray();
    }

    public Object[] mixInFlavors() {
        return mixInFlavors.toArray();
    }

    public Object[] containers() {
        return containers.toArray();
    }

    public Object[] customers() {
        return customers.toArray();
    }

    public Object[] orders() {
        return orders.toArray();
    }

    private ArrayList<IceCreamFlavor> iceCreamFlavors;
    private ArrayList<MixInFlavor> mixInFlavors;
    private ArrayList<Container> containers;
    private ArrayList<Order> orders;
    private ArrayList<Customer> customers;
}
