package product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Item {
	public Item(String name, String description, int cost, int price) {
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.price = price;
	}
	
	public Item(BufferedReader in) throws IOException {
		this.name = in.readLine();
		this.description = in.readLine();
		this.cost = Integer.parseInt(in.readLine());
		this.price = Integer.parseInt(in.readLine());
    }
	
	public void save(BufferedWriter out) throws IOException {
		out.write(name);
		out.newLine();
		
        out.write(description);
		out.newLine();
	
        out.write("" + price);
		out.newLine();

        out.write("" + cost);
		out.newLine();
    }
	
	public String name() {
		return name;
	}
	
	public String description() {
		return description;
	}
	
	public int price() {
		return price;
	}
	
	public int cost() {
		return cost;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	private String name;
	private String description;
	private int cost;
	private int price;
}
