package product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Container {
    public Container(String name, String description, int maxScoops) {
        this.name = name;
        this.description = description;
        this.maxScoops = maxScoops;
    }

    public Container(BufferedReader in) throws IOException {
        String name = "";
        String description = "";
        int maxScoops = -1;

        String line = in.readLine();
        if(!line.isBlank()) name = line;
        else throw new IOException("Line is blank when it should not be.");

        line = in.readLine();
        if(!line.isBlank()) description = line;
        else throw new IOException("Line is blank when it should not be.");

        line = in.readLine();
        if(!line.isBlank()) maxScoops = Integer.parseInt(line);
        else throw new IOException("Line is blank when it should not be.");

        this(name, description, maxScoops);
    }

    public void save(BufferedWriter out) throws IOException {
        out.write(name);
        out.newLine();

        out.write(description);
        out.newLine();

        out.write("" + maxScoops);
        out.newLine();
    }

    @Override
    public String toString() {
        return name;
    }

    public int maxScoops() {
        return maxScoops;
    }

    private String name;
    private String description;
    private int maxScoops;
}
