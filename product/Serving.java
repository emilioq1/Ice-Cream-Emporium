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
        String line = in.readLine();
        if(line != "Serving") throw new IOException("Wrong part of file.");

        line = in.readLine();
        if(line != "Container") throw new IOException("Not at \"Container\".");
        this.container = new Container(in);

        line = in.readLine();
        if(line != "Scoops") throw new IOException("Not at \"Scoops\".");
        line = in.readLine();

        int numScoops = Integer.parseInt(line);
        for(int i = 0; i < numScoops; i++) {
            scoops.add(new Scoop(in));
            line = in.readLine();
        }

        if(line != "Toppings") throw new IOException("Not at \"Toppings\".");
        line = in.readLine();

        int numToppings = Integer.parseInt(line);
        for(int i = 0; i < numToppings; i++) {
            toppings.add(new MixIn(in));
            line = in.readLine();
        }
    }

    public void save(BufferedWriter out) throws IOException {
        //out.write("Serving");
        //out.newLine();

        //out.write("Container");
        //out.newLine();
        container.save(out);
        out.newLine();

        out.write("Scoops;" + scoops.size());
        out.newLine();

        //out.write("Scoops");
        //out.newLine();
        //out.write("" + scoops.size());
        //out.newLine();

        for(Scoop s : scoops) {
            s.save(out);
            out.newLine();
        }

        out.write("Toppings;" + toppings.size());
        out.newLine();

        //out.write("Toppings");
        //out.newLine();
        //out.write("" + toppings.size());
        //out.newLine();

        for(MixIn m : toppings) {
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
