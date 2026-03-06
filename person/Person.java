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
        this.name = in.readLine();
        this.phone = in.readLine();
    }

    public void save(BufferedWriter out) throws IOException {
        out.write(name);
        out.newLine();
		
        out.write(phone);
        out.newLine();
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
