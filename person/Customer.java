package person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Customer extends Person {
    public Customer(String name, String phone) {
        super(name, phone);
    }

    public Customer(BufferedReader in) throws IOException {
        super(in);
    }

    public void save(BufferedWriter out) throws IOException {
        super.save(out);
        // out.write(name);
        // out.newLine();

        // out.write(phone);
        // out.newLine();
    }

}
