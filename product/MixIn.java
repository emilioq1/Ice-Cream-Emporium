package product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class MixIn {
    public MixIn(MixInFlavor flavor, MixInAmount amount) {
        this.flavor = flavor;
        this.amount = amount;
    }

    public MixIn(BufferedReader in) throws IOException {
        this.flavor = new MixInFlavor(in);
        this.amount = MixInAmount.valueOf(in.readLine().trim());
    }

    public void save(BufferedWriter out) throws IOException {
        flavor.save(out);
        out.write(";");
        out.write(amount.toString());
    }

    @Override
    public String toString() {
        // Defines how to print out the amount that is mix in if it is not normal.
        if(amount != MixInAmount.Normal) {
            return flavor.toString() + " (" + amount + ")";
        }
        // Print only the flavor if the mixin amount is normal.
        // This is the default and does not need to be noted.
        return flavor.toString();
    }

    public String toStringDebug() {
        return flavor.toString() + ";" + amount.toString();
    }

    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        MixIn mixIn = (MixIn)o;

        boolean flavorEquals = this.flavor.equals(mixIn.flavor);
        boolean amountEquals = this.amount.equals(mixIn.amount);
        
        return flavorEquals && amountEquals;
    }

    public int price() {
        int price = flavor.price();
        // if(amount == MixInAmount.Light) {
        // return (Integer) (price * .8);
        // }
        // if(amount == MixInAmount.Extra) {
        // return (Integer) (price * 1.2);
        // }
        // if(amount == MixInAmount.Drenched) {
        // return price * 2;
        // }

        return price;
    }

    private MixInFlavor flavor;
    private MixInAmount amount;
}
