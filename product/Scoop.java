package product;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Scoop {
    public Scoop(IceCreamFlavor flavor) {
        this.flavor = flavor;
        mixins = new ArrayList<MixIn>();
    }

    public Scoop(BufferedReader in) throws IOException {
        this.flavor = new IceCreamFlavor(in);
        mixins = new ArrayList<MixIn>();
        MixIn mixin;

        in.readLine();
        int numMixIn = Integer.parseInt(in.readLine());
        for(int i = 0; i < numMixIn; i++) {
            mixin = new MixIn(in);
            mixins.add(mixin);
        }
        in.readLine();
    }

    public void save(BufferedWriter out) throws IOException {
        flavor.save(out);
        out.newLine();

        out.write("" + mixins.size());
        out.newLine();

        for(MixIn m : mixins) {
            m.save(out);
            out.newLine();
        }
    }

    public void addMixIn(MixIn mixin) {
        mixins.add(mixin);
    }

    @Override
    public String toString() {
        // if there are no mix in's, then just print the flavor
        if(mixins.size() == 0) {
            return flavor.toString();
        }

        StringBuilder s = new StringBuilder();
        String delimiter = ", ";

        s.append(flavor).append(" with ");
        for(MixIn m : mixins) {
            s.append(m).append(delimiter);
        }
        s.deleteCharAt(s.length() - delimiter.length());

        return s.toString();
    }

    public int price() {
        int icePrice = 0;
        int mixInPrice = 0;
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
