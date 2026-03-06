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
        Scoop scoop;
        MixIn topping;
        
        in.readLine();
        in.readLine();
        this.container = new Container(in);
        
        in.readLine();
        in.readLine();
        in.readLine();
        int numScoops = Integer.parseInt(in.readLine());
        for(int i = 0; i < numScoops; i++) {
            scoop = new Scoop(in);
            scoops.add(scoop);
            in.readLine();
        }
        
        in.readLine();
        int numToppings = Integer.parseInt(in.readLine());
        for(int i = 0; i < numToppings; i++) {
            topping = new MixIn(in);
            toppings.add(topping);
            in.readLine();
        }
    }
    
    public void save(BufferedWriter out) throws IOException {
        out.write("SERVING");
        out.newLine();
        
        out.write("Container");
        out.newLine();
        container.save(out);
        out.newLine();
        out.write("--CONTAINEREND--");
        out.newLine();

        out.write("Scoop");
        out.newLine();
        out.write("" + scoops.size());
        out.newLine();

        for(Scoop s : scoops) {
            s.save(out);
            out.newLine();
            out.write("--SCOOPEND--");
            out.newLine();
        }
        
        out.write("Topping");
        out.newLine();
        out.write("" + toppings.size());
        out.newLine();

        for(MixIn m : toppings) {
            m.save(out);
            out.newLine();
            out.write("--TOPPINGEND--");
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
