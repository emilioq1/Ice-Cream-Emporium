package product;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Optional;


public class Serving {
    public Serving(Container container) {
        this.container = container;
        scoops = new ArrayList<Scoop>();
        toppings = new ArrayList<MixIn>();
    }

    public Serving(BufferedReader in) throws IOException {
        scoops = new ArrayList<Scoop>();
        toppings = new ArrayList<MixIn>();

        this.container = new Container(in);

        String line = in.readLine().trim();

        StringTokenizer st = new StringTokenizer(line, ";");
        st.nextToken(); // Skip identifier
        int size = Optional.ofNullable(st.nextToken()).map(str -> Integer.parseInt(str)).orElse(0);

        for(int i = 0; i < size; ++i) {
            this.scoops.add(new Scoop(in));
        }

        // Skip blank line
        in.readLine();
        line = in.readLine();
        st = new StringTokenizer(line, ";");
        st.nextToken(); // Skip identifier
        size = Optional.ofNullable(st.nextToken()).map(str -> Integer.parseInt(str)).orElse(0);

        for(int i = 0; i < size; ++i) {
            this.toppings.add(new MixIn(in));
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
        String containerStr = String.format("container: (%s)", container.toStringDebug());

        StringBuilder scoopsStr = new StringBuilder();
        scoopsStr.append("scoops: [");
        ArrayList<String> scoopsList = new ArrayList<>();
        for(Scoop s : scoops) {
            scoopsList.add("(" + s.toStringDebug() + ")");
        }
        scoopsStr.append(String.join(", ", scoopsList));
        scoopsStr.append("]");

        StringBuilder toppingsStr = new StringBuilder();
        toppingsStr.append("toppings: [");
        ArrayList<String> toppingsList = new ArrayList<>();
        toppings.forEach(m -> toppingsList.add("(" + m.toStringDebug() + ")"));
        toppingsStr.append(String.join(", ", toppingsList));
        toppingsStr.append("]");

        return String.join(", ", containerStr, scoopsStr.toString(), toppingsStr.toString());
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
