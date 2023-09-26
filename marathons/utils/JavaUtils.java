package marathons.utils;

public class JavaUtils {
	public static void requireEnabledAssertions() {
		int assertOn = 0;
		//noinspection AssertWithSideEffects,ConstantConditions
		assert (assertOn = 1) > 0;
		//noinspection ConstantConditions
		if (assertOn == 0) {
			throw new AssertionError("Asserts must be on.");
		}
	}
}
