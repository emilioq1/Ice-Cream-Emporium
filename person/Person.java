package person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;


public class Person {
    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Person(BufferedReader in) throws IOException {
        String line = in.readLine();
        if(line.isBlank()) {
            throw new IOException("Loading person from file failed: expected \"{name: str};{phone: str}\", got \"\".");
        }
        StringTokenizer st = new StringTokenizer(line, ";");


        String name = st.nextToken();
        String phone = st.nextToken();

        this(name, phone);
    }

    public void save(BufferedWriter out) throws IOException {
        String personStr = String.format("%s;%s", name, "" + phone);
        out.write(personStr);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    protected String name;
    protected String phone;
}
