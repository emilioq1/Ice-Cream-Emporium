package emporium;

import product.Item;
import product.IceCreamFlavor;
import product.MixInAmount;
import product.MixInFlavor;
import product.MixIn;
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
        IceCreamFlavor ice;
        MixInFlavor mix;
        Container con;
        Order ord;

        in.readLine();
        int num = Integer.parseInt(in.readLine());
        for (int i = 0; i < num; i++) {
            ice = new IceCreamFlavor(in);
            iceCreamFlavors.add(ice);
            in.readLine();
        }

        in.readLine();
        num = Integer.parseInt(in.readLine());
        for (int i = 0; i < num; i++) {
            mix = new MixInFlavor(in);
            mixInFlavors.add(mix);
            in.readLine();
        }

        in.readLine();
        in.readLine();
        num = Integer.parseInt(in.readLine());
        for (int i = 0; i < num; i++) {
            con = new Container(in);
            containers.add(con);
            in.readLine();
        }

        in.readLine();
        in.readLine();
        num = Integer.parseInt(in.readLine());
        for (int i = 0; i < num; i++) {
            ord = new Order(in);
            orders.add(ord);
            in.readLine();
        }

    }

    public void save(BufferedWriter out) throws IOException {
        out.write("IceCreamFlavors");
        out.newLine();
        out.write("" + iceCreamFlavors.size());
        out.newLine();

        for (IceCreamFlavor i : iceCreamFlavors) {
            i.save(out);
            out.newLine();
            out.write("---ICECREAMFLAVOREND---");
            out.newLine();
        }
        out.newLine();

        out.write("MixInFlavors");
        out.newLine();
        out.write("" + mixInFlavors.size());
        out.newLine();
        for (MixInFlavor m : mixInFlavors) {
            m.save(out);
            out.newLine();
            out.write("---MIXINFLAVOREND---");
            out.newLine();
        }
        out.newLine();

        out.write("Containers");
        out.newLine();
        out.write("" + containers.size());
        out.newLine();
        for (Container c : containers) {
            c.save(out);
            out.newLine();
            out.write("---CONTAINEREND---");
            out.newLine();
        }
        out.newLine();

        out.write("Orders");
        out.newLine();
        out.write("" + orders.size());
        out.newLine();
        for (Order o : orders) {
            o.save(out);
            out.newLine();
            out.write("---ORDEREND---");
            out.newLine();
        }
        out.newLine();
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
