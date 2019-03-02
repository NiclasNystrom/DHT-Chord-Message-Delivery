package dht;

import jaka
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;


public class Key {

	public static final int NUM_KEY_BITS = 32;


	public static String create_guid(String key) {
		String sha1 = null;
		try {
			MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
			msdDigest.update(key.getBytes("UTF-8"), 0, key.length());
			sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sha1;
	}


	private static int commonDigits(String a, String b) {
		int n = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				break;
			}
			n += 1;
		}
		return n;
	}


	public static Boolean inBetweenKeys(String k, String a, String b) {

		HashMap<Character, Integer> rang = new HashMap<>();
		rang.put('0', 1);
		rang.put('1', 2);
		rang.put('2', 3);
		rang.put('3', 4);
		rang.put('4', 5);
		rang.put('5', 6);
		rang.put('6', 7);
		rang.put('7', 8);
		rang.put('8', 9);
		rang.put('9', 10);
		rang.put('A', 11);
		rang.put('B', 12);
		rang.put('C', 13);
		rang.put('D', 14);
		rang.put('E', 15);
		rang.put('F', 16);


		// Eval how many common digits they share.
		int commonA = commonDigits(k, a);
		int commonB = commonDigits(k, b);
		//System.out.println("CommonA: " + commonA + " CommonB: " + commonB);

		// Grab the number they both share.
		int common = Math.min(commonA, commonB);

		if (common < 1) {


			char c = k.charAt(0);
			char _a = a.charAt(0);
			char _b = b.charAt(0);

			int ic = rang.get(c);
			int ia = rang.get(_a);
			int ib = rang.get(_b);

			int m = (ia+ib)/2;
			if (Math.abs(ic - m) <= Math.abs(ia - m))
				return true;
			return false;

		} else {
			//common = common > 0 ? common : 1;
			// Look at the first not common digit (hence common and not common-1)
			char c = k.charAt(common);
			char _a = a.charAt(common);
			char _b = b.charAt(common);

			int ic = rang.get(c);
			int ia = rang.get(_a);
			int ib = rang.get(_b);

			int m = (ia+ib)/2;
			if (Math.abs(ic - m) <= Math.abs(ia - m))
				return true;
			return false;

		}
	}


	public static String FindClosestsKey(String k, List<String> keys) {


		HashMap<Character, Integer> rang = new HashMap<>();
		rang.put('0', 1);
		rang.put('1', 2);
		rang.put('2', 3);
		rang.put('3', 4);
		rang.put('4', 5);
		rang.put('5', 6);
		rang.put('6', 7);
		rang.put('7', 8);
		rang.put('8', 9);
		rang.put('9', 10);
		rang.put('A', 11);
		rang.put('B', 12);
		rang.put('C', 13);
		rang.put('D', 14);
		rang.put('E', 15);
		rang.put('F', 16);


		if (keys.size() < 2)
			return keys.get(0);

		String closests_key = keys.get(0);

		for (int i = 0; i < keys.size(); i++) {
			for (int j = 1; j < keys.size()-i; j++) {
				String cs = FindClosestsKey(k, keys.get(i), keys.get(j));
				closests_key = FindClosestsKey(k, closests_key, cs);
			}
		}

		return closests_key;
	}

	public static String FindClosestsKey(String k, String a, String b) {


		HashMap<Character, Integer> rang = new HashMap<>();
		rang.put('0', 1);
		rang.put('1', 2);
		rang.put('2', 3);
		rang.put('3', 4);
		rang.put('4', 5);
		rang.put('5', 6);
		rang.put('6', 7);
		rang.put('7', 8);
		rang.put('8', 9);
		rang.put('9', 10);
		rang.put('A', 11);
		rang.put('B', 12);
		rang.put('C', 13);
		rang.put('D', 14);
		rang.put('E', 15);
		rang.put('F', 16);


		int commonA = commonDigits(k, a);
		int commonB = commonDigits(k, b);
		int common = Math.min(commonA, commonB);

		if (common < 1) {


			char c = k.charAt(0);
			char _a = a.charAt(0);
			char _b = b.charAt(0);

			int ic = rang.get(c);
			int ia = rang.get(_a);
			int ib = rang.get(_b);
			//System.out.println("IC: " + ic + " IA: " + ia + " IB: " + ib);

			int[] ends = {ia, ib};
			int r = Arrays.stream(ends).boxed().collect(Collectors.toList()).stream().min(Comparator.comparingInt(i -> Math.abs(i - ic))).orElseThrow(() -> new NoSuchElementException("No Such value present"));

			if (r == ia)
				return a;
			if (r == ib)
				return b;
			return "NaN";

		} else {
			char c = k.charAt(common);
			char _a = a.charAt(common);
			char _b = b.charAt(common);

			int ic = rang.get(c);
			int ia = rang.get(_a);
			int ib = rang.get(_b);
			//System.out.println("IC: " + ic + " IA: " + ia + " IB: " + ib);

			int[] ends = {ia, ib};
			int r = Arrays.stream(ends).boxed().collect(Collectors.toList()).stream().min(Comparator.comparingInt(i -> Math.abs(i - ic))).orElseThrow(() -> new NoSuchElementException("No Such value present"));

			if (r == ia)
				return a;
			if (r == ib)
				return b;
			return "NaN";

		}
	}

	// nodes == keys
	public static String[] sortStringKeysByDistance(String k, ArrayList<String> keylist) {
		HashMap<Character, Integer> rang = new HashMap<>();
		rang.put('0', 1);
		rang.put('1', 2);
		rang.put('2', 3);
		rang.put('3', 4);
		rang.put('4', 5);
		rang.put('5', 6);
		rang.put('6', 7);
		rang.put('7', 8);
		rang.put('8', 9);
		rang.put('9', 10);
		rang.put('A', 11);
		rang.put('B', 12);
		rang.put('C', 13);
		rang.put('D', 14);
		rang.put('E', 15);
		rang.put('F', 16);

		ArrayList<String> keys = new ArrayList<>();
		for (String n : keylist) {
			if (n != k)
				keys.add(n);
		}

		String[] _keys = new String[keys.size()];
		for (int i = 0; i < keys.size(); i++) {
			_keys[i] = keys.get(i);
		}


		for (int i = 0; i < _keys.length; i++) {

			for (int j = 1; j < _keys.length-i; j++) { ;

				String closerKey = FindClosestsKey(k, _keys[j-1], _keys[j]);

				if (_keys[j-1] != closerKey) {
					String tmp = _keys[j-1];
					_keys[j-1] = _keys[j];
					_keys[j] = tmp;
				}
			}
		}

		return _keys;
	}

	public static String[] sortKeysByDistance(String k, ArrayList<Node> nodes) {
		HashMap<Character, Integer> rang = new HashMap<>();
		rang.put('0', 1);
		rang.put('1', 2);
		rang.put('2', 3);
		rang.put('3', 4);
		rang.put('4', 5);
		rang.put('5', 6);
		rang.put('6', 7);
		rang.put('7', 8);
		rang.put('8', 9);
		rang.put('9', 10);
		rang.put('A', 11);
		rang.put('B', 12);
		rang.put('C', 13);
		rang.put('D', 14);
		rang.put('E', 15);
		rang.put('F', 16);

		ArrayList<String> keys = new ArrayList<>();
		for (Node n : nodes) {
			if (!n.key.equals(k))
				keys.add(n.key);
		}

		String[] _keys = new String[keys.size()];
		for (int i = 0; i < keys.size(); i++) {
			_keys[i] = keys.get(i);
		}


		for (int i = 0; i < _keys.length; i++) {

			for (int j = 1; j < _keys.length-i; j++) {

				String closerKey = FindClosestsKey(k, _keys[j-1], _keys[j]);

				if (_keys[j].equals(closerKey)) {
					String tmp = _keys[j-1];
					_keys[j-1] = _keys[j];
					_keys[j] = tmp;
				}
			}
		}

		return _keys;
	}

	public static void main(String[] args) {
		String k1 = Key.create_guid("http://localhost:10500");
		String k2 = Key.create_guid("http://localhost:10600");
		String k3 = Key.create_guid("http://localhost:10700");
		String k4 = Key.create_guid("http://localhost:10800");
		String k5 = Key.create_guid("http://localhost:10900");
		String k6 = Key.create_guid("http://localhost:10110");
		String k7 = Key.create_guid("http://localhost:10120");
		String k8 = Key.create_guid("http://localhost:10130");

		System.out.println("k1: " + k1);
		System.out.println("k2: " + k2);
		System.out.println("k3: " + k3);
		System.out.println("k4: " + k4);
		System.out.println("k5: " + k5);
		System.out.println("k6: " + k6);
		System.out.println("k7: " + k7);
		System.out.println("k8: " + k8);


		ArrayList<String> keys = new ArrayList<>();
		keys.add(k1);
		keys.add(k2);
		keys.add(k3);
		keys.add(k4);
		keys.add(k5);
		keys.add(k6);
		keys.add(k7);
		keys.add(k8);

		String[] slist = sortStringKeysByDistance(k1, keys);

		System.out.println("Sorting keys by distance from k1: " + k1);
		for (int i = 0; i < slist.length; i++) {
			System.out.println("Key " + (i+1) + ": " + slist[i]);

			String closetsKey = slist[i];

			for (int j = 1; j < slist.length - i; j++) {
				closetsKey = FindClosestsKey(k1, slist[0], slist[j]);
			}
			if (!closetsKey.equals(slist[0])) {
				System.out.println("Warning not shortest. Shortest: " + closetsKey);
			}
		}




	}


}
