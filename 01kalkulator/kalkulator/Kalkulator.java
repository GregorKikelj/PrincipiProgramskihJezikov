import java.util.*;

public class Kalkulator {
	public static void main(String[] args) throws Exception {

		HashMap<String, Double> env = new HashMap<String, Double>();
		env.put("x", 42.0);
		env.put("y", 3.14);
		env.put("z", 1.0);

		try {
			System.out.print("Izraz:2 ");
			String s = "(2 + 3) * (x + y)";
			Izraz2 e = Parser2.parse(s);
			var pe = e.poenostavi();
			System.out.println();
			System.out.println("Poenostavljeno " +pe);
			double v = pe.eval(env);
			System.out.println(e.toASTString());
			System.out.println("Vrednost: " + v);
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
