public class RPSMagicTrick {
	public String guess(String exampleGuess, String exampleResponse, String volunteersActions) {
		StringBuilder ans = new StringBuilder();
		String correct = exampleGuess;
		if (exampleResponse.startsWith("W")) correct = reverse(correct);
		char a = exampleGuess.charAt(0);
		char b = exampleGuess.charAt(1);
		for (int i = 0; i < volunteersActions.length(); i++) {
			char c = volunteersActions.charAt(i);
			if (c == '?') {
				ans.append(correct);
				continue;
			}
			correct = reverse(correct);
		}
		return ans.toString();
	}

	String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}
}
