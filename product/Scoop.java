package product;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Optional;


public class Scoop {
    public Scoop(IceCreamFlavor flavor) {
        this.flavor = flavor;
        this.mixins = new ArrayList<MixIn>();
    }

    public Scoop(BufferedReader in) throws IOException {
        this.flavor = new IceCreamFlavor(in);
        this.mixins = new ArrayList<MixIn>();

        String line = in.readLine().trim();

        StringTokenizer st = new StringTokenizer(line, ";");
        st.nextToken(); // Skip identifier
        int size = Optional.ofNullable(st.nextToken()).map(str -> Integer.parseInt(str)).orElse(0);

        for(int i = 0; i < size; ++i) {
            this.mixins.add(new MixIn(in));
        }
    }

    public void save(BufferedWriter out) throws IOException {
        flavor.save(out);
        out.newLine();

        out.write("\t\t\t\tMixins;" + mixins.size());
        out.newLine();

        for(MixIn m : mixins) {
            out.write("\t\t\t\t\t");
            m.save(out);
            out.newLine();
        }
    }

    public void addMixIn(MixIn mixin) {
        this.mixins.add(mixin);
    }

    @Override
    public String toString() {
        // if there are no mix in's, then just print the flavor
        if(this.mixins.size() == 0) {
            return flavor.toString();
        }

        StringBuilder s = new StringBuilder();
        String delimiter = ", ";

        s.append(flavor).append(" with ");
        for(MixIn m : this.mixins) {
            s.append(m).append(delimiter);
        }
        s.deleteCharAt(s.length() - delimiter.length());

        return s.toString();
    }

    public String toStringDebug() {
        String flavorStr = String.format("flavor: (%s)", flavor.toStringDebug());
        
        StringBuilder mixinsStr = new StringBuilder();
        mixinsStr.append("mixins: [");
        ArrayList<String> mixinsList = new ArrayList<>();
        for(MixIn m: this.mixins) {
            mixinsList.add("(" + m.toStringDebug() + ")");
        }
        mixinsStr.append(String.join(", ", mixinsList));
        mixinsStr.append("]");

        return String.join(", ", flavorStr, mixinsStr.toString());
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Scoop scoop = (Scoop)o;

        boolean flavorEquals = this.flavor.equals(scoop.flavor);
        boolean mixinsEquals = this.mixins.equals(scoop.mixins);
        
        return flavorEquals && mixinsEquals;
    }

    public int price() {
        int price = 0;

        price += flavor.price();
        for(MixIn m : mixins) {
            price += m.price();
        }

        return price;
    }


    private IceCreamFlavor flavor;
    private ArrayList<MixIn> mixins;
}
