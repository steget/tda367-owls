package storagesystem.model;

import org.junit.Assert;
import org.junit.Test;

public class ModelTest {
	private static final int NUM_INCREMENTATIONS = 128;

	@Test
	public void testIncrementResult() {
		final Model model = new Model();

		for (int i = 0; i < NUM_INCREMENTATIONS; i++) {
			model.incrementPresses();
		}

		Assert.assertEquals(NUM_INCREMENTATIONS, model.getPresses());
	}
}
