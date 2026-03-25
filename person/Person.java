package person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Person {
    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Person(BufferedReader in) throws IOException {
        String line = in.readLine().strip();
        if(line.isBlank()) {
            throw new IOException("Loading person from file failed: expected \"{name: str};{phone: str}\", got \"\".");
        }

        String[] tokens = line.split(";", -1);
        if(tokens.length != 2) {
            throw new IOException("Person: 2 tokens were expected. Got " + tokens.length + " tokens instead.");
        }

        String name = tokens[0].strip();
        String phone = tokens[1].strip();

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
