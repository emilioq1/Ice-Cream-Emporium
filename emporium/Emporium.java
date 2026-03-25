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

        String line = in.readLine().strip();

        String[] tokens = line.split(";", -1);
        if(tokens.length != 2) {
            throw new IOException("IceCreamFlavors: 2 tokens were expected. Got " + tokens.length + " tokens instead.");
        }
        int size = Optional.ofNullable(tokens[1].strip()).map(str -> Integer.parseInt(str)).orElse(0);
        for(int i = 0; i < size; ++i) {
            iceCreamFlavors.add(new IceCreamFlavor(in));
        }
        line = in.readLine().strip();


        tokens = line.split(";", -1);
        if(tokens.length != 2) {
            throw new IOException("MixInFlavors: 2 tokens were expected. Got " + tokens.length + " tokens instead.");
        }
        size = Optional.ofNullable(tokens[1].strip()).map(str -> Integer.parseInt(str)).orElse(0);
        for(int i = 0; i < size; ++i) {
            mixInFlavors.add(new MixInFlavor(in));
        }
        line = in.readLine().strip();


        tokens = line.split(";", -1);
        if(tokens.length != 2) {
            throw new IOException("Containers: 2 tokens were expected. Got " + tokens.length + " tokens instead.");
        }
        size = Optional.ofNullable(tokens[1].strip()).map(str -> Integer.parseInt(str)).orElse(0);
        for(int i = 0; i < size; i++) {
            containers.add(new Container(in));
        }
        line = in.readLine().strip();

        tokens = line.split(";", -1);
        if(tokens.length != 2) {
            throw new IOException("Customers: 2 tokens were expected. Got " + tokens.length + " tokens instead.");
        }
        size = Optional.ofNullable(tokens[1].strip()).map(str -> Integer.parseInt(str)).orElse(0);
        for(int i = 0; i < size; i++) {
            customers.add(new Customer(in));
        }
        line = in.readLine().strip();

        tokens = line.split(";", -1);
        if(tokens.length != 2) {
            throw new IOException("Orders: 2 tokens were expected. Got " + tokens.length + " tokens instead.");
        }
        size = Optional.ofNullable(tokens[1].strip()).map(str -> Integer.parseInt(str)).orElse(0);
        for(int i = 0; i < size; i++) {
            orders.add(new Order(in));
            line = in.readLine().strip();
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

        out.write("Customers;" + customers.size());
        out.newLine();
        for(Customer c : customers) {
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
    public String toString() {
        ArrayList<String> iceCreamList = new ArrayList<>();
        iceCreamFlavors.forEach(ice -> iceCreamList.add("(" + ice.toStringDebug() + ")"));
        String iceCreamOut = String.join(", ", iceCreamList);

        ArrayList<String> mixInList = new ArrayList<>();
        mixInFlavors.forEach(mix -> mixInList.add("(" + mix.toStringDebug() + ")"));
        String mixInOut = String.join(", ", mixInList);

        ArrayList<String> containersList = new ArrayList<>();
        containers.forEach(cont -> containersList.add("(" + cont.toStringDebug() + ")"));
        String containersOut = String.join(", ", containersList);

        ArrayList<String> customersList = new ArrayList<>();
        customers.forEach(cust -> mixInList.add("(" + cust.toStringDebug() + ")"));
        String customersOut = String.join(", ", customersList);

        ArrayList<String> ordersList = new ArrayList<>();
        orders.forEach(ord -> ordersList.add("(" + ord.toStringDebug() + ")"));
        String ordersOut = String.join(", ", ordersList);

        String output =  "Emporium{" +
                "iceCreamFlavors={" + iceCreamOut +
                "}, mixInFlavors={" + mixInOut +
                "}, containers={" + containersOut +
                "}, customers={" + customersOut +
                "}, orders={" + ordersOut +
                '}';

        return output;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Emporium emp = (Emporium)o;

        boolean iceCreamFlavorsEquals = this.iceCreamFlavors.equals(emp.iceCreamFlavors);
        if(!iceCreamFlavorsEquals) {
            System.err.println("Member iceCreamFlavors do not match.");

            System.err.print("this.iceCreamFlavors: [");
            ArrayList<String> iceList = new ArrayList<>();
            this.iceCreamFlavors.forEach(ice -> iceList.add(ice.toStringDebug()));
            System.err.print(String.join(", ", iceList));
            System.err.println("]");

            System.err.print("o.iceCreamFlavors: [");
            ArrayList<String> iceOList = new ArrayList<>();
            emp.iceCreamFlavors.forEach(ice -> iceOList.add(ice.toStringDebug()));
            System.err.print(String.join(", ", iceOList));
            System.err.println("]\n");
        }

        boolean mixInFlavorsEquals = this.mixInFlavors.equals(emp.mixInFlavors);
        if(!mixInFlavorsEquals) {
            System.err.println("Member mixInFlavors do not match.");

            System.err.print("this.mixInFlavors: [");
            ArrayList<String> mixinsTList = new ArrayList<>();
            this.mixInFlavors.forEach(mix -> mixinsTList.add(mix.toStringDebug()));
            System.err.print(String.join(", ", mixinsTList));
            System.err.println("]");

            System.err.print("o.mixInFlavors: [");
            ArrayList<String> mixinsOList = new ArrayList<>();
            emp.mixInFlavors.forEach(mix -> mixinsOList.add(mix.toStringDebug()));
            System.err.print(String.join(", ", mixinsOList));
            System.err.println("]\n");
        }

        boolean containersEquals = this.containers.equals(emp.containers);
        if(!containersEquals) {
            System.err.println("Member containers do not match.");

            System.err.print("this.containers: [");
            ArrayList<String> containersTList = new ArrayList<>();
            this.containers.forEach(container -> containersTList.add(container.toStringDebug()));
            System.err.print(String.join(", ", containersTList));
            System.err.println("]");

            System.err.print("o.containers: [");
            ArrayList<String> containersOList = new ArrayList<>();
            emp.containers.forEach(container -> containersOList.add(container.toStringDebug()));
            System.err.print(String.join(", ", containersOList));
            System.err.println("]\n");
        }

        boolean ordersEquals = this.orders.equals(emp.orders);
        if(!ordersEquals) {
            System.err.println("Member orders does not match.");

            System.err.print("this.orders: [");
            ArrayList<String> ordersTList = new ArrayList<>();
            this.orders.forEach(order -> ordersTList.add(order.toStringDebug()));
            System.err.print(String.join(", ", ordersTList));
            System.err.println("]");

            System.err.print("o.orders: [");
            ArrayList<String> ordersOList = new ArrayList<>();
            emp.orders.forEach(order -> ordersOList.add(order.toStringDebug()));
            System.err.print(String.join(", ", ordersOList));
            System.err.println("]\n");
        }

        boolean customersEquals = this.customers.equals(emp.customers);
        if(!customersEquals) {
            System.err.println("Member customers does not match.");

            System.err.print("this.customers: [");
            ArrayList<String> customersTList = new ArrayList<>();
            this.customers.forEach(customer -> customersTList.add("(" + customer.toStringDebug() + ")"));
            System.err.print(String.join(", ", customersTList));
            System.err.println("]");

            System.err.print("o.customers: [");
            ArrayList<String> customersOList = new ArrayList<>();
            emp.customers.forEach(customer -> customersOList.add("(" + customer.toStringDebug() + ")"));
            System.err.print(String.join(", ", customersOList));
            System.err.println("]\n");
        }

        return iceCreamFlavorsEquals && mixInFlavorsEquals && containersEquals && ordersEquals && customersEquals;
    }

    private ArrayList<IceCreamFlavor> iceCreamFlavors;
    private ArrayList<MixInFlavor> mixInFlavors;
    private ArrayList<Container> containers;
    private ArrayList<Order> orders;
    private ArrayList<Customer> customers;
}
