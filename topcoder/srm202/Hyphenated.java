package topcoder.srm202;
public class Hyphenated {
    public double avgLength(String[] lines) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("  ");
    	for (String s : lines) {
    		char y = sb.charAt(sb.length() - 2);
    		char z = sb.charAt(sb.length() - 1);
    		char a = s.charAt(0);
    		if (Character.isLetter(y) && z == '-' && Character.isLetter(a)) {
    			sb.setLength(sb.length() - 1);
    		} else {
    			sb.append(" ");
    		}
    		sb.append(s);
    	}
    	String s = sb.toString();
    	int count = 0;
    	int sumLen = 0;
    	for (int i = 0; i < s.length(); i++) {
    		if (Character.isLetter(s.charAt(i))) {
    			int j = i;
    			while (j < s.length() && Character.isLetter(s.charAt(j))) {
    				j++;
    			}
    			count++;
    			sumLen += j - i;
    			i = j - 1;
    		}
    	}
    	return sumLen * 1.0 / count;
    }

}
