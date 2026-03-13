package product;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Serving {
    public Serving(Container container) {
        this.container = container;
        scoops = new ArrayList<Scoop>();
        toppings = new ArrayList<MixIn>();
    }

    public Serving(BufferedReader in) throws IOException {
        scoops = new ArrayList<Scoop>();
        toppings = new ArrayList<MixIn>();

        // Read Serving
        String line = in.readLine().trim();
        if(line != "Serving") throw new IOException("Wrong part of file.");

        line = in.readLine().trim();
        if(line != "Container") throw new IOException("Not at \"Container\".");
        this.container = new Container(in);

        line = in.readLine().trim();
        if(line != "Scoops") throw new IOException("Not at \"Scoops\".");

        line = in.readLine().trim();
        int numScoops = Integer.parseInt(line);
        for(int i = 0; i < numScoops; i++) {
            scoops.add(new Scoop(in));
            line = in.readLine().trim();
        }

        if(line != "Toppings") throw new IOException("Not at \"Toppings\".");
        line = in.readLine().trim();

        int numToppings = Integer.parseInt(line);
        for(int i = 0; i < numToppings; i++) {
            toppings.add(new MixIn(in));
            line = in.readLine().trim();
        }
    }

    public void save(BufferedWriter out) throws IOException {
        // \t\t\t
        container.save(out);
        out.newLine();

        out.write("\t\t\t");
        out.write("Scoops;" + scoops.size());
        out.newLine();
        for(Scoop s : scoops) {
            out.write("\t\t\t\t");
            s.save(out);
            out.newLine();
        }

        out.write("\t\t\t");
        out.write("Toppings;" + toppings.size());
        out.newLine();

        for(MixIn m : toppings) {
            out.write("\t\t\t\t");
            m.save(out);
            out.newLine();
        }
    }

    public void addScoop(Scoop scoop) {
        scoops.add(scoop);
    }

    public void addTopping(MixIn topping) {
        toppings.add(topping);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String delimiter = ", ";

        str.append(container.toString());

        if(scoops.size() > 1) {
            str.append(" with scoops of ");
        }
        else {
            str.append(" with a scoop of ");
        }

        for(Scoop s : scoops) {
            str.append(s);
            str.append(delimiter);
        }
        str.deleteCharAt(str.length() - delimiter.length());

        if(toppings.size() > 0) {
            str.append(" and topped with ");
            for(MixIn t : toppings) {
                str.append(t);
                str.append(delimiter);
            }
            str.deleteCharAt(str.length() - delimiter.length());
        }

        return str.toString();
    }

    public String toStringDebug() {
        StringBuilder str = new StringBuilder();

        str.append("{" + container.toStringDebug() + "}, ");

        str.append("[");
        for(Scoop s: scoops) {
            str.append(s.toStringDebug()).append(";");
        }
        str.append("], ");

        str.append("[");
        for(MixIn m: toppings) {
            str.append(m.toStringDebug()).append(";");
        }
        str.append("]");

        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Serving serving = (Serving)o;

        boolean containerEquals = this.container.equals(serving.container);
        boolean scoopsEquals = this.scoops.equals(serving.scoops);
        boolean toppingsEquals = this.toppings.equals(serving.toppings);

        return containerEquals && scoopsEquals && toppingsEquals;
    }

    public int price() {
        int price = 0;

        for(Scoop s : scoops) {
            price += s.price();
        }

        for(MixIn t : toppings) {
            price += t.price();
        }

        return price;
    }

    public int numScoops() {
        return scoops.size();
    }

    private Container container;
    private ArrayList<Scoop> scoops;
    private ArrayList<MixIn> toppings;
}
