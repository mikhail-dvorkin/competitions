package topcoder.srm374;
import java.util.*;

public class SyllableSorting {
	class Word implements Comparable<Word> {
		ArrayList<String> nov = new ArrayList<String>();
		ArrayList<String> old = new ArrayList<String>();
		String orig;

		public Word(String s) {
			orig = s;
			while (s.length() > 0) {
				int i = 0;
				while (i < s.length() && !isVowel(s.charAt(i)))
					i++;
				while (i < s.length() && isVowel(s.charAt(i)))
					i++;
				old.add(s.substring(0, i));
				s = s.substring(i);
			}
			nov = new ArrayList<String>(old);
			Collections.sort(nov);
			char f = 'a' - 1;
			old.add("" + f);
			nov.add("" + f);
		}

		private boolean isVowel(char c) {
			return "aeiou".contains("" + c);
		}

		@Override
		public int compareTo(Word o) {
			if (orig.equals(o.orig))
				return 0;
			for (int i = 0; i < nov.size(); i++) {
				int cmp = nov.get(i).compareTo(o.nov.get(i));
				if (cmp != 0)
					return cmp;
			}
			for (int i = 0; i < nov.size(); i++) {
				int cmp = old.get(i).compareTo(o.old.get(i));
				if (cmp != 0)
					return cmp;
			}
			return 0;
		}

	}

	public String[] sortWords(String[] words) {
		int n = words.length;
		Word[] w = new Word[n];
		for (int i = 0; i < n; i++) {
			w[i] = new Word(words[i]);
		}
		Arrays.sort(w);
		for (int i = 0; i < n; i++) {
			words[i] = w[i].orig;
		}
		return words;
	}

	public static void main(String[] args) {
		new SyllableSorting().sortWords(new String[]{"yamagawa", "gawayama"});
	}
}
