package test;
import product.Item;
import product.IceCreamFlavor;
import product.MixInAmount;
import product.MixInFlavor;
import product.MixIn;
import product.Scoop;

public class TestScoop {
	public static void main(String[] args) {
		try {
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
			IceCreamFlavor iceCreamFlavor = new IceCreamFlavor("Vanilla", "Classic flavor",1,2);
			failed(iceCreamFlavor,normalString);
	
			iceCreamFlavor = new IceCreamFlavor("Vanilla", "",0,0);
			failed(iceCreamFlavor,emptyDescription);
			
			iceCreamFlavor = new IceCreamFlavor("", "",-3,-4);
			failed(iceCreamFlavor,emptyNameDescription);
			
			iceCreamFlavor = new IceCreamFlavor("Vainilla", "Sabor clásico",1,2);
			failed(iceCreamFlavor,foreignString);
			
			// Tests Scoop
			  // Resets iceCreamFlavor for consistency
			iceCreamFlavor = new IceCreamFlavor("Vanilla", "Classic flavor",1,2);
			
			MixInFlavor snickers = new MixInFlavor("Snickers", "Classic chocolate",1,2);
			MixInFlavor twix = new MixInFlavor("Chocolate Chips", " Chocolate with Chips",1,2);
			MixInAmount normal = MixInAmount.Normal;
			MixInAmount extra = MixInAmount.Extra;
			
			MixIn mixInOne = new MixIn(snickers, normal);
			MixIn mixInOneWithAmount = new MixIn(snickers, extra);
			MixIn mixInTwo = new MixIn(twix, normal);
			MixIn mixInTwoWithAmount = new MixIn(twix, extra);
			
			Scoop scoop = new Scoop(iceCreamFlavor);
			failed(scoop,noMixIns);
			
			scoop.addMixIn(mixInOne);
			failed(scoop,oneMixIns);
			
			scoop.addMixIn(mixInTwo);
			failed(scoop,twoMixIns);
			
			Scoop scoopTwo = new Scoop(iceCreamFlavor);
			
			scoopTwo.addMixIn(mixInOneWithAmount);
			failed(scoopTwo,oneMixInsAmount);
			
			scoopTwo.addMixIn(mixInTwoWithAmount);
			failed(scoopTwo,twoMixInsAmount);
		}
		catch(Exception e) {
			errorFormat = e.getMessage();
			System.err.println(errorFormat);
			System.exit(-1);
		}
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
		
		str.append(item.name()).
			append(seperator).
				append(item.description()).
					append(seperator).
						append(item.cost()).
							append(seperator).
								append(item.price());
		
		return str.toString();
	}
	
	
	private static String errorFormat;
}
