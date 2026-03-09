package product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.StringTokenizer;


public class Container {
    public Container(String name, String description, int maxScoops) {
        this.name = name;
        this.description = description;
        this.maxScoops = maxScoops;
    }

    public Container(BufferedReader in) throws IOException {
        String line = in.readLine();
        if(line.isBlank()) {
            throw new IOException(
                    "Loading container from file failed: expected \"{name: str};{description: str};{maxScoops: int}\", got \"\".");
        }
        StringTokenizer st = new StringTokenizer(line, ";");


        String name = st.nextToken();
        String description = st.nextToken();
        int maxScoops = Optional.ofNullable(st.nextToken()).map(str -> Integer.parseInt(str)).orElse(0);

        this(name, description, maxScoops);
    }

    public void save(BufferedWriter out) throws IOException {
        String containerStr = String.format("%s;%s;%s", name, description, "" + maxScoops);
        out.write(containerStr);
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
