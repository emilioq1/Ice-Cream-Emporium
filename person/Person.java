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
        String line = in.readLine().trim();
        if(line.isBlank()) {
            throw new IOException("Loading person from file failed: expected \"{name: str};{phone: str}\", got \"\".");
        }
        StringTokenizer st = new StringTokenizer(line, ";");


        String name = st.nextToken();
        String phone = "";
        try { phone = st.nextToken(); } catch(Exception e) {}

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

    public String toStringDebug() {
        String nameStr = String.format("name: \"%s\"", name);
        String phoneStr = String.format("phone: \"%s\"", phone);

        return nameStr + ", " + phoneStr;
        //return String.format("(%s, %s)", nameStr, phoneStr);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Person container = (Person)o;

        boolean nameEqual = this.name.equals(container.name);
        boolean phoneEqual = this.phone.equals(container.phone);

        return nameEqual && phoneEqual;
    }


    //@Override
    //public int hashCode() {
    //    return this.hashCode();
    //}

    protected String name;
    protected String phone;
}
