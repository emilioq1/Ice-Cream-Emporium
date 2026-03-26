package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

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

            testLoading();
        }
        catch(Exception e) {
            System.err.println(e);
            //errorFormat = e.getMessage();
            //System.err.println(errorFormat);
            System.exit(-1);
        }
    }

    final static String MAGIC_COOKIE = "ꬺICƐ🧊🍨";
    final static String FILE_VERSION = "1.2";

    private static void testLoading() throws Exception {
        /*
        Happy Path Tests
            - Load valid file - Load a well-formed file and verify all 5 lists are populated with the correct number of objects and correct field values.
            - Load maximum data - Load a large file to ensure no truncation or performance issues.
            - Load minimum data - Load a file with exactly one entry per list to confirm it doesn't require multiple records.
        Empty / Missing Data Tests
            - Empty file - Load a completely empty file and verify all lists are initialized as empty (not null).
            - Empty section - Load a file where one or more lists (e.g., orders) have zero entries; verify other lists still load correctly.
            - Missing file - Provide a path to a nonexistent file and verify a proper exception is thrown (not a silent failure or crash).
        Data Integrity Tests
            - Correct types - After loading, verify that objects in each list are the correct type (IceCreamFlavor, MixInFlavor, etc.), not just raw strings.
            - Field accuracy - Spot-check specific fields on loaded objects (e.g., a flavor's name/price) against known values in the test file.
            - List independence - Confirm that modifying one list after loading doesn't affect others (no shared references).
            - Order references - If Order objects reference Customer, Container, or flavor objects, verify those references are correctly resolved after loading, not left as dangling IDs or nulls.
        Malformed Input Tests
            - Corrupted data - Load a file with a malformed entry (e.g., a missing field or wrong data type) and verify graceful error handling.
            - Extra/unknown fields - Load a file with extra fields or sections and confirm it doesn't crash or corrupt the valid data.
            - Wrong file format - Pass a file of the wrong type (e.g., a .txt instead of the expected format) and verify a meaningful error.
        Boundary / Edge Case Tests
            - Duplicate entries - Load a file with duplicate records and decide/verify whether they are both added or deduplicated.
            - Special characters - Include flavor/customer names with spaces, apostrophes, or accented characters and verify they load correctly.
            - Whitespace/blank lines - Include extra blank lines or trailing whitespace in the file and verify it doesn't break parsing.
         */
        // Test Data
        String rootPath = System.getProperty("user.dir") + File.separator;

        IceCreamFlavor validIceCream1 = new IceCreamFlavor("Vanilla", "Plain old Vanilla", 1, 2);
        IceCreamFlavor validIceCream2 = new IceCreamFlavor("Chocolate", "Milk Chocolate", 3, 5);
        IceCreamFlavor validIceCream3 = new IceCreamFlavor("Strawberry", "Iced strawberries", 6, 10);
        IceCreamFlavor iceCreamMissing1 = new IceCreamFlavor("", "Iced strawberries", 6, 10);
        IceCreamFlavor iceCreamMissing2 = new IceCreamFlavor("Strawberry", "", 6, 10);
        IceCreamFlavor iceCreamMissing3 = new IceCreamFlavor("Strawberry", "Iced strawberries", -1, 10);
        IceCreamFlavor iceCreamMissing4 = new IceCreamFlavor("Strawberry", "Iced strawberries", 6, -1);

        MixInFlavor validMixInFlavor1 = new MixInFlavor("Sprinkles", "Colorful pallets of sugar", 1, 1);
        MixInFlavor validMixInFlavor2 = new MixInFlavor("Oreo bits", "Bits of sandwich cookies", 2, 3);
        MixInFlavor validMixInFlavor3 = new MixInFlavor("Caramel syrup", "Caramel syrup", 1, 10000);
        MixInFlavor mixInFlavorMissing1 = new MixInFlavor("", "Caramel syrup", 1, 10000);
        MixInFlavor mixInFlavorMissing2 = new MixInFlavor("Caramel syrup", "", 1, 10000);
        MixInFlavor mixInFlavorMissing3 = new MixInFlavor("Caramel syrup", "Caramel syrup", -1, 10000);
        MixInFlavor mixInFlavorMissing4 = new MixInFlavor("Caramel syrup", "Caramel syrup", 1, -10000);

        Container validContainer1 = new Container("Cup", "A plastic cup", 8);
        Container validContainer2 = new Container("Waffle cone", "A cone made of waffle", 4);
        Container validContainer3 = new Container("Large cup", "A large plastic cup", 10);
        Container containerMissing1 = new Container("", "A large plastic cup", 10);
        Container containerMissing2 = new Container("Large cup", "", 10);
        Container containerMissing3 = new Container("Large cup", "A large plastic cup", -1);

        Customer person1 = new Customer("Billy", "1928312213");
        Customer person2 = new Customer("John", "9381823711");
        Customer person3 = new Customer("Sally", "10938176629");
        Customer personMissing1 = new Customer("", "10938176629");
        Customer personMissing2 = new Customer("Sally", "");

        {
            System.out.print("[TEST 1] Non-existent file -> ");
            try {
                String noFilePath = rootPath + "invalid.mice";
                File noFile = new File(noFilePath);
                onLoad(noFile);
                // The block should not pass the line above
                System.out.println("FAILED");
            }
            catch(Exception e) {
                System.err.println("PASSED");
                System.err.println("\t" + e);
            }
        }

        {
            System.out.print("[TEST 2] Valid file -> ");
            Emporium emporium = new Emporium();

            emporium.addContainer(validContainer1);
            emporium.addIceCreamFlavor(validIceCream1);
            emporium.addMixInFlavor(validMixInFlavor1);

            File testFile = new File(rootPath + "test2.mice");

            Serving serving = new Serving(validContainer1);

            Scoop scoop1 = new Scoop(validIceCream1);
            MixIn mixIn1 = new MixIn(validMixInFlavor1, MixInAmount.Light);
            scoop1.addMixIn(mixIn1);
            serving.addScoop(scoop1);

            Scoop scoop2 = new Scoop(validIceCream1);
            serving.addScoop(scoop2);

            Scoop scoop3 = new Scoop(validIceCream1);
            MixIn mixIn3 = new MixIn(validMixInFlavor1, MixInAmount.Normal);
            MixIn mixIn3_2 = new MixIn(validMixInFlavor1, MixInAmount.Light);
            scoop3.addMixIn(mixIn3);
            scoop3.addMixIn(mixIn3_2);
            serving.addScoop(scoop3);

            Scoop scoop4 = new Scoop(validIceCream1);
            MixIn mixIn4 = new MixIn(validMixInFlavor1, MixInAmount.Extra);
            scoop4.addMixIn(mixIn4);
            serving.addScoop(scoop4);
            serving.addTopping(mixIn1);

            Order order = new Order(person1);
            order.addServing(serving);

            emporium.addCustomer(person1);
            emporium.addOrder(order);

            onSave(testFile, emporium);

            Emporium emporium2 = onLoad(testFile);

            if(matchEmporium(emporium, emporium2)) {
                System.out.println("PASSED");
            }
            else {
                System.out.println("FAILED");
                throw new NoMatchException("NoMatchException: Emporium a does not match Emporium b");
            }
        }

        {
            System.out.print("[TEST 3] Giant valid file -> ");
            Emporium emporium = new Emporium();

            emporium.addContainer(validContainer1);
            emporium.addContainer(validContainer2);
            emporium.addContainer(validContainer3);
            emporium.addContainer(validContainer1);
            emporium.addContainer(validContainer1);
            emporium.addContainer(validContainer1);
            emporium.addContainer(validContainer1);
            emporium.addContainer(validContainer1);
            emporium.addContainer(validContainer1);
            emporium.addContainer(validContainer1);
            emporium.addIceCreamFlavor(validIceCream1);
            emporium.addIceCreamFlavor(validIceCream1);
            emporium.addIceCreamFlavor(validIceCream2);
            emporium.addIceCreamFlavor(validIceCream1);
            emporium.addIceCreamFlavor(validIceCream1);
            emporium.addIceCreamFlavor(validIceCream2);
            emporium.addIceCreamFlavor(validIceCream1);
            emporium.addIceCreamFlavor(validIceCream1);
            emporium.addIceCreamFlavor(validIceCream3);
            emporium.addMixInFlavor(validMixInFlavor1);
            emporium.addMixInFlavor(validMixInFlavor1);
            emporium.addMixInFlavor(validMixInFlavor2);
            emporium.addMixInFlavor(validMixInFlavor1);
            emporium.addMixInFlavor(validMixInFlavor3);
            emporium.addMixInFlavor(validMixInFlavor1);
            emporium.addMixInFlavor(validMixInFlavor1);
            emporium.addMixInFlavor(validMixInFlavor1);
            emporium.addMixInFlavor(validMixInFlavor2);
            emporium.addMixInFlavor(validMixInFlavor1);
            emporium.addMixInFlavor(validMixInFlavor3);

            File testFile = new File(rootPath + "test3.mice");

            Serving serving1 = new Serving(validContainer1);
            Serving serving2 = new Serving(validContainer2);
            Serving serving3 = new Serving(validContainer3);

            Scoop scoop1 = new Scoop(validIceCream1);
            MixIn mixIn1 = new MixIn(validMixInFlavor1, MixInAmount.Light);
            scoop1.addMixIn(mixIn1);
            serving1.addScoop(scoop1);

            Scoop scoop2 = new Scoop(validIceCream2);
            serving1.addScoop(scoop2);

            Scoop scoop3 = new Scoop(validIceCream3);
            MixIn mixIn3 = new MixIn(validMixInFlavor1, MixInAmount.Normal);
            MixIn mixIn3_2 = new MixIn(validMixInFlavor1, MixInAmount.Light);
            scoop3.addMixIn(mixIn3);
            scoop3.addMixIn(mixIn3_2);
            serving1.addScoop(scoop3);

            Scoop scoop4 = new Scoop(validIceCream1);
            MixIn mixIn4 = new MixIn(validMixInFlavor1, MixInAmount.Extra);
            scoop4.addMixIn(mixIn4);
            serving1.addScoop(scoop4);
            serving1.addTopping(mixIn1);

            Order order = new Order(person1);
            order.addServing(serving1);
            order.addServing(serving1);
            order.addServing(serving1);
            order.addServing(serving2);
            order.addServing(serving2);
            order.addServing(serving2);
            order.addServing(serving2);
            order.addServing(serving3);
            order.addServing(serving3);
            order.addServing(serving3);
            order.addServing(serving3);
            order.addServing(serving3);

            emporium.addCustomer(person1);
            emporium.addCustomer(person2);
            emporium.addCustomer(person3);
            emporium.addCustomer(person1);
            emporium.addCustomer(person2);
            emporium.addOrder(order);
            emporium.addOrder(order);
            emporium.addOrder(order);

            onSave(testFile, emporium);

            Emporium emporium2 = onLoad(testFile);

            if(matchEmporium(emporium, emporium2)) {
                System.out.println("PASSED");
            }
            else {
                System.out.println("FAILED");
                throw new NoMatchException("NoMatchException: Emporium a does not match Emporium b");
            }
        }

        {
            System.out.print("[TEST 4] File with extra unknown fields -> ");
            try {
                String filePath = rootPath + "test4.mice";
                File file = new File(filePath);
                onLoad(file);
                // The block should not pass the line above
                System.out.println("FAILED");
            }
            catch(Exception e) {
                System.out.println("PASSED");
                System.err.println("\t" + e);
            }
        }

        {
            System.out.println("[TEST 5] File with missing fields -> ");
            System.out.println("IceCreamFlavor -> ");

            // Create the file
            Emporium emporium = new Emporium();
            emporium.addContainer(validContainer1);
            emporium.addIceCreamFlavor(validIceCream2);
            ///----------------------------------------------------------------------------------------------------///
            emporium.addIceCreamFlavor(iceCreamMissing1);
            ///----------------------------------------------------------------------------------------------------///
            emporium.addIceCreamFlavor(validIceCream3);
            emporium.addMixInFlavor(validMixInFlavor1);

            File testFile = new File(rootPath + "test5.mice");

            Serving serving = new Serving(validContainer1);

            Scoop scoop1 = new Scoop(validIceCream1);
            MixIn mixIn1 = new MixIn(validMixInFlavor1, MixInAmount.Light);
            scoop1.addMixIn(mixIn1);
            serving.addScoop(scoop1);

            Scoop scoop2 = new Scoop(validIceCream1);
            serving.addScoop(scoop2);

            Scoop scoop3 = new Scoop(validIceCream1);
            MixIn mixIn3 = new MixIn(validMixInFlavor1, MixInAmount.Normal);
            MixIn mixIn3_2 = new MixIn(validMixInFlavor1, MixInAmount.Light);
            scoop3.addMixIn(mixIn3);
            scoop3.addMixIn(mixIn3_2);
            serving.addScoop(scoop3);

            Scoop scoop4 = new Scoop(validIceCream1);
            MixIn mixIn4 = new MixIn(validMixInFlavor1, MixInAmount.Extra);
            scoop4.addMixIn(mixIn4);
            serving.addScoop(scoop4);
            serving.addTopping(mixIn1);

            Order order = new Order(person1);
            order.addServing(serving);

            emporium.addCustomer(person1);
            emporium.addOrder(order);

            onSave(testFile, emporium);

            try {
                Emporium emporium2 = onLoad(testFile);
            }
            catch(Exception e) {
                System.out.println("PASSED");
                System.out.println("\t" + e);
            }
            System.out.println("FAILED");
        }

    }

    private static boolean matchEmporium(Emporium a, Emporium b) throws NoMatchException {
        return a.equals(b);
        //if(!a.equals(b)) {
        //String errorFormat = "NoMatchException: Emporium a does not match Emporium b\n";
        //throw new NoMatchException(errorFormat);
        //}
    }

    // Used to mimic onOpenClick()
    public static Emporium onLoad(File file) throws IOException {
        LineNumberReader rw = new LineNumberReader(new FileReader(file));
        String line = rw.readLine();

        if(!line.equals(MAGIC_COOKIE)) {
            rw.close();
            throw new IOException("Magic cookie does not match.");
        }
        line = rw.readLine();
        if(!line.equals(FILE_VERSION)) {
            rw.close();
            throw new IOException("File version is not " + FILE_VERSION);
        }

        return new Emporium(rw);
        //System.err.println("ERROR");
        //System.err.println(e + " at line " + rw.getLineNumber());
        //rw.close();
        //return null;
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
