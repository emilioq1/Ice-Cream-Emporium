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

        for(int i = 0; i < parsed.size; ++i) {
            iceCreamFlavors.add(new IceCreamFlavor(in));
            line = in.readLine().trim();
        }

        parsed = new DataHeader(line, in);
        for(int i = 0; i < parsed.size; ++i) {
            mixInFlavors.add(new MixInFlavor(in));
            line = in.readLine().trim();
        }
        parsed = new DataHeader(line, in);
        for(int i = 0; i < parsed.size; i++) {
            containers.add(new Container(in));
            line = in.readLine().trim();
        }

        parsed = new DataHeader(line, in);
        for(int i = 0; i < parsed.size; i++) {
            orders.add(new Order(in));
            line = in.readLine().trim();
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
            out.write("\t");
            i.save(out);
            out.newLine();
        }

        out.write("MixInFlavors;" + mixInFlavors.size());
        out.newLine();
        for(MixInFlavor m : mixInFlavors) {
            out.write("\t");
            m.save(out);
            out.newLine();
        }

        out.write("Containers;" + containers.size());
        out.newLine();
        for(Container c : containers) {
            out.write("\t");
            c.save(out);
            out.newLine();
        }

        out.write("Orders;" + orders.size());
        out.newLine();
        for(Order o : orders) {
            out.write("\t");
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

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Emporium e = (Emporium)o;

        boolean iceCreamFlavorsEquals = this.iceCreamFlavors.equals(e.iceCreamFlavors);
        if(!iceCreamFlavorsEquals) {
            for(IceCreamFlavor ice : this.iceCreamFlavors) {
                System.err.println(ice.name());
                System.err.println(ice.description());
                System.err.println("" + ice.cost());
                System.err.println("" + ice.price());
            }

            System.err.println("");
            for(IceCreamFlavor ice : e.iceCreamFlavors) {
                System.err.println(ice.name());
                System.err.println(ice.description());
                System.err.println("" + ice.cost());
                System.err.println("" + ice.price());
            }
            return false;
        }
        boolean mixInFlavorsEquals = this.mixInFlavors.equals(e.mixInFlavors);
        if(!mixInFlavorsEquals) {
            for(MixInFlavor ice : this.mixInFlavors) {
                System.err.println(ice.name());
                System.err.println(ice.description());
                System.err.println("" + ice.cost());
                System.err.println("" + ice.price());
            }

            System.err.println("");
            for(MixInFlavor ice : e.mixInFlavors) {
                System.err.println(ice.name());
                System.err.println(ice.description());
                System.err.println("" + ice.cost());
                System.err.println("" + ice.price());
            }

            return false;
        }
        boolean containersEquals = this.containers.equals(e.containers);
        if(!containersEquals) {
            System.err.println(this.containers);
            System.err.println("");
            System.err.println(e.containers);

            return false;
        }
        boolean ordersEquals = this.orders.equals(e.orders);
        if(!ordersEquals) {
            this.orders.forEach(order -> System.err.println(order.debugToString()));
            e.orders.forEach(order -> System.err.println(order.debugToString()));
            
            //return false;
        }
        boolean customersEquals = this.customers.equals(e.customers);
        if(!customersEquals) {
            System.err.println(this.customers);
            System.err.println(e.customers);

            return false;
        }

        return iceCreamFlavorsEquals && mixInFlavorsEquals && containersEquals && ordersEquals && customersEquals;
    }

    private ArrayList<IceCreamFlavor> iceCreamFlavors;
    private ArrayList<MixInFlavor> mixInFlavors;
    private ArrayList<Container> containers;
    private ArrayList<Order> orders;
    private ArrayList<Customer> customers;
}
