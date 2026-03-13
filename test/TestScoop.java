package test;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import product.Item;
import product.IceCreamFlavor;
import product.MixInAmount;
import product.MixInFlavor;
import product.Order;
import product.MixIn;
import product.Scoop;
import product.Serving;
import emporium.Emporium;
import person.Customer;
import product.Container;


public class TestScoop {
    public static void main(String[] args) {
        try {
            /*
            // All expected strings
            String normalString = "Vanilla, Classic flavor, 1, 2";
            String emptyDescription = "Vanilla, , 0, 0";
            String emptyNameDescription = ", , -3, -4";
            String foreignString = "Vainilla, Sabor clásico, 1, 2";
            
            String noMixIns = "\"Vanilla\"";
            String oneMixIns = "\"Vanilla with Snickers\"";
            String twoMixIns = "\"Vanilla with Snickers, Chocolate Chips\"";
            String oneMixInsAmount = "\"Vanilla with Snickers (Extra)\"";
            String twoMixInsAmount = "\"Vanilla with Snickers (Extra), Chocolate Chips (Extra)\"";
            
            // Tests Item
            IceCreamFlavor iceCreamFlavor = new IceCreamFlavor("Vanilla", "Classic flavor", 1, 2);
            failed(iceCreamFlavor, normalString);
            
            iceCreamFlavor = new IceCreamFlavor("Vanilla", "", 0, 0);
            failed(iceCreamFlavor, emptyDescription);
            
            iceCreamFlavor = new IceCreamFlavor("", "", -3, -4);
            failed(iceCreamFlavor, emptyNameDescription);
            
            iceCreamFlavor = new IceCreamFlavor("Vainilla", "Sabor clásico", 1, 2);
            failed(iceCreamFlavor, foreignString);
            
            // Tests Scoop
            // Resets iceCreamFlavor for consistency
            iceCreamFlavor = new IceCreamFlavor("Vanilla", "Classic flavor", 1, 2);
            
            MixInFlavor snickers = new MixInFlavor("Snickers", "Classic chocolate", 1, 2);
            MixInFlavor twix = new MixInFlavor("Chocolate Chips", " Chocolate with Chips", 1, 2);
            MixInAmount normal = MixInAmount.Normal;
            MixInAmount extra = MixInAmount.Extra;
            
            MixIn mixInOne = new MixIn(snickers, normal);
            MixIn mixInOneWithAmount = new MixIn(snickers, extra);
            MixIn mixInTwo = new MixIn(twix, normal);
            MixIn mixInTwoWithAmount = new MixIn(twix, extra);
            
            Scoop scoop = new Scoop(iceCreamFlavor);
            failed(scoop, noMixIns);
            
            scoop.addMixIn(mixInOne);
            failed(scoop, oneMixIns);
            
            scoop.addMixIn(mixInTwo);
            failed(scoop, twoMixIns);
            
            Scoop scoopTwo = new Scoop(iceCreamFlavor);
            
            scoopTwo.addMixIn(mixInOneWithAmount);
            failed(scoopTwo, oneMixInsAmount);
            
            scoopTwo.addMixIn(mixInTwoWithAmount);
            failed(scoopTwo, twoMixInsAmount);
            */


            Emporium emporium = new Emporium();

            IceCreamFlavor iceCream = new IceCreamFlavor("Vanilla", "Plain old Vanilla", 1, 2);
            MixInFlavor mixInFlavor = new MixInFlavor("Sprinkles", "Colorful pallets of sugar", 1, 1);
            Container container = new Container("Cup", "A plastic cup", 4);

            emporium.addContainer(container);
            emporium.addIceCreamFlavor(iceCream);
            emporium.addMixInFlavor(mixInFlavor);

            File testFile = new File("C:\\Users\\billy\\Desktop\\Programs\\Ice-Cream-Emporium\\test.mice");

            Serving serving = new Serving(container);

            Scoop scoop1 = new Scoop(iceCream);
            MixIn mixIn1 = new MixIn(mixInFlavor, MixInAmount.Light);
            scoop1.addMixIn(mixIn1);
            serving.addScoop(scoop1);

            Scoop scoop2 = new Scoop(iceCream);
            serving.addScoop(scoop2);

            Scoop scoop3 = new Scoop(iceCream);
            MixIn mixIn3 = new MixIn(mixInFlavor, MixInAmount.Normal);
            MixIn mixIn3_2 = new MixIn(mixInFlavor, MixInAmount.Light);
            scoop3.addMixIn(mixIn3);
            scoop3.addMixIn(mixIn3_2);
            serving.addScoop(scoop3);

            Scoop scoop4 = new Scoop(iceCream);
            MixIn mixIn4 = new MixIn(mixInFlavor, MixInAmount.Extra);
            scoop4.addMixIn(mixIn4);
            serving.addScoop(scoop4);

            serving.addTopping(mixIn1);

            Customer person = new Customer("Billy", "1928312213");

            Order order = new Order(person);

            order.addServing(serving);

            emporium.addCustomer(person);
            emporium.addOrder(order);

            onSave(testFile, emporium);

            Emporium emporium2 = onLoad(testFile);

            matchEmporium(emporium, emporium2);
        }
        catch(Exception e) {
            System.err.println(e);
            //errorFormat = e.getMessage();
            //System.err.println(errorFormat);
            System.exit(-1);
        }
    }

    final static String MAGIC_COOKIE = "ꬺICƐ🧊🍨";
    final static String FILE_VERSION = "1.1";

    private static void matchEmporium(Emporium a, Emporium b) throws NoMatchException {
        if(a.equals(b)) {
            System.out.println("Emporiums are equal");
        }
        else {
            String errorFormat = "NoMatchException: Emporium a does not match Emporium b\n";
            throw new NoMatchException(errorFormat);
        }
    }

    public static Emporium onLoad(File file) throws IOException {
        try(BufferedReader rw = new BufferedReader(new FileReader(file))) {
            String line = rw.readLine();
            if(!line.equals(MAGIC_COOKIE)) {
                return null;
            }

            line = rw.readLine();
            if(!(line.equals(FILE_VERSION))) {
                return null;
            }

            //rw.readLine();
            return new Emporium(rw);
        }
        //catch(IOException e) {
        //    errorFormat = e.getMessage();
        //    System.err.println(errorFormat);
        //    //System.exit(-1);
        //}
    }

    public static void onSave(File file, Emporium emporium) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(MAGIC_COOKIE);
            bw.newLine();
            bw.write(FILE_VERSION);
            bw.newLine();
            emporium.save(bw);
        }
        catch(IOException e) {
            errorFormat = e.getMessage();
            System.err.println(errorFormat);
            System.exit(-1);
        }

        return;
    }

    public static class NoMatchException extends Exception {
        public NoMatchException(String s) {
            super(s);
        }
    }

    // Makes a string for Item, then compares it to the expected string
    private static void failed(Item item, String expected) throws NoMatchException {
        String itemString = itemGoodString(item);

        if(!itemString.equals(expected)) {
            errorFormat += "NoMatchException: Item string does not match expected string\n";
            errorFormat += "Expected String: (" + expected + ")\n";
            errorFormat += "Actual String: (" + itemString + ")\n";
            throw new NoMatchException(errorFormat);
        }
    }

    // Makes a string for Scoop, then compares it to the expected string
    private static void failed(Scoop scoop, String expected) throws NoMatchException {
        String scoopString = scoop.toString();

        if(!scoopString.equals(expected)) {
            errorFormat += "NoMatchException: Scoop string does not match expected string\n";
            errorFormat += "Expected String: (" + expected + ")\n";
            errorFormat += "Actual String: (" + scoopString + ")\n";
            throw new NoMatchException(errorFormat);
        }
    }

    // Makes a string containing all elements of an Item
    private static String itemGoodString(Item item) {
        StringBuilder str = new StringBuilder();
        String seperator = ", ";

        str.append(item.name()).append(seperator).append(item.description()).append(seperator).append(item.cost())
                .append(seperator).append(item.price());

        return str.toString();
    }


    private static String errorFormat;
}
