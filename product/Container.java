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
        String line = in.readLine().strip();
        if(line.isBlank()) {
            throw new IOException(
                    "Loading container from file failed: expected \"{name: str};{description: str};{maxScoops: int}\", got \"\".");
        }

        String[] tokens = line.split(";", -1);
        if(tokens.length != 3) {
            throw new IOException("Container: 3 tokens were expected. Got " + tokens.length + " tokens instead.");
        }

        String name = tokens[0].strip();
        String description = tokens[1].strip();
        int maxScoops = Integer.parseInt(tokens[2].strip());

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

    public String toStringDebug() {
        String nameStr = String.format("name: \"%s\"", name);
        String descriptionStr = String.format("description: \"%s\"", description);
        String maxScoopsStr = String.format("maxScoops: %d", maxScoops);

        return String.join(", ", nameStr, descriptionStr, maxScoopsStr);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Container container = (Container)o;

        boolean nameEqual = this.name.equals(container.name);
        boolean descriptionEqual = this.description.equals(container.description);
        boolean maxScoopsEqual = this.maxScoops == container.maxScoops;

        return nameEqual && descriptionEqual && maxScoopsEqual;
    }

    public int maxScoops() {
        return maxScoops;
    }

    private String name;
    private String description;
    private int maxScoops;
}
