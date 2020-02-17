package topcoder.tco2016.russia_regional;
public class BuffingPixie {
    public int fastestVictory(int hp, int normalAttack, int buffedAttack) {
    	if (buffedAttack <= 2 * normalAttack) {
    		return (hp + normalAttack - 1) / normalAttack;
    	}
    	if (hp <= normalAttack) {
    		return 1;
    	}
    	int a = 2 * ((hp + buffedAttack - 1) / buffedAttack);
    	int b = 1 + 2 * ((hp - normalAttack + buffedAttack - 1) / buffedAttack);
    	return Math.min(a, b);
    }

}
