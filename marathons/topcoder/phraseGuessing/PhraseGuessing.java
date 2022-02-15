package marathons.topcoder.phraseGuessing;

import java.io.*;
import java.util.*;

public class PhraseGuessing {
	public static void main(String[] args) throws Exception {
		String fileName = "words_alpha_filtered.txt";
		BufferedReader file = new BufferedReader(new FileReader(fileName));

		List<String> WordList = new ArrayList<String>();

		file.readLine();    //skip the header
		while (true) {
			String line = file.readLine();
			if (line == null) break;

			WordList.add(line);
		}
		file.close();


		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(in.readLine());
		int P = Integer.parseInt(in.readLine());
		double C = Double.parseDouble(in.readLine());


		int[] primes = {113, 131, 197, 199, 311, 337, 373, 719, 733, 919};

		for (int i = 1, guesses = 1; guesses <= 20; i++) {
			String guess = "";
			for (int n = 0; n < N; n++) {
				int id = (i * primes[n]) % WordList.size();
				guess += WordList.get(id);
				if (n < N - 1) guess += " ";
			}


			//we found a valid guess
			if (guess.length() == P) {
				guesses++;
				System.out.println(guess);
				System.out.flush();

				//read elapsed time and result
				int elapsedTime = Integer.parseInt(in.readLine());
				String result = in.readLine();
			}
		}

		//terminate
		System.out.println("-1");
		System.out.flush();
	}
}
