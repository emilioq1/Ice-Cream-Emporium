package emporium;

import product.IceCreamFlavor;
import product.MixInFlavor;
import product.Container;
import product.Order;

import person.Customer;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Optional;


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
        
        class DataHeader {
            String id;
            int size;
            
            private DataHeader(String id, int size) {
                this.id = id;
                this.size = size;
            }

            private DataHeader(String line, BufferedReader in) throws IOException {
                StringTokenizer st = new StringTokenizer(line, ";");
                String identifier = st.nextToken();
                int size = Optional.ofNullable(st.nextToken()).map(str -> Integer.parseInt(str)).orElse(0);
                this(identifier, size);
            }
        }


        String line = in.readLine();
        DataHeader parsed = new DataHeader(line, in);

        if(parsed.id == "IceCreamFlavors") {
            for(int i = 0; i < parsed.size; ++i) {
                iceCreamFlavors.add(new IceCreamFlavor(in));
                line = in.readLine();
            }
        }

        line = in.readLine();
        parsed = new DataHeader(line, in);

        if(parsed.id == "MixInFlavors") {
            for(int i = 0; i < parsed.size; ++i) {
                iceCreamFlavors.add(new IceCreamFlavor(in));
                line = in.readLine();
            }
        }

        line = in.readLine();
        parsed = new DataHeader(line, in);

        if(parsed.id == "Containers") {
            for(int i = 0; i < parsed.size; i++) {
                containers.add(new Container(in));
                line = in.readLine();
            }
        }

        line = in.readLine();
        parsed = new DataHeader(line, in);

        if(parsed.id == "Orders") {
            for(int i = 0; i < parsed.size; i++) {
                orders.add(new Order(in));
                line = in.readLine();
            }
        }
    }

    public void save(BufferedWriter out) throws IOException {
        // Ice Cream Flavors: Name, Description, Price, Cost
        // Mix In Flavors: Name, Description, Price, Cost
        // Containers: Name, Description, Max Scoops
        // Orders: Customer, Servings

        out.write("IceCreamFlavors;" + iceCreamFlavors.size());
        out.newLine();
        for(IceCreamFlavor i : iceCreamFlavors) {
            i.save(out);
            out.newLine();
        }

        out.write("MixInFlavors;" + mixInFlavors.size());
        out.newLine();
        for(MixInFlavor m : mixInFlavors) {
            m.save(out);
            out.newLine();
        }

        out.write("Containers;" + containers.size());
        out.newLine();
        for(Container c : containers) {
            c.save(out);
            out.newLine();
        }

        out.write("Orders;" + orders.size());
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
