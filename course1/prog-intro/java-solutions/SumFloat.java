public class SumFloat {
	public static void main(String[] args) {
		float sum=0;

		for (int i = 0; i < args.length; i++) {
			
			String str_nums = args[i] + " ";
			StringBuilder num = new StringBuilder();

			for (char symb: str_nums.toCharArray()) {
				if (Character.isWhitespace(symb)) {
					if (!(num.isEmpty())) {
						sum += Float.parseFloat(num.toString());
					}
					num = new StringBuilder();
				} else {
					num.append(symb);
				}
			}
		}
		System.out.println(sum);
	}
}