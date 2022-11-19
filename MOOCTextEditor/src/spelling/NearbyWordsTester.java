package spelling;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NearbyWordsTester {
	private String dictFile = "data/words.small.txt";

	NearbyWords emptyWords;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		Dictionary largeDict = new DictionaryHashSet();
		DictionaryLoader.loadDictionary(largeDict, dictFile);

		emptyWords = new NearbyWords(largeDict);

	}

	// test for producing correct number of suggestions
	@Test
	public void testSize() {
		String empty = "";
		String small = "am";
		String big = "desk";
		
		assertEquals("Testing size of spell suggestions empty string", 0, emptyWords.suggestions(empty, 10).size());
		assertEquals("Testing size of spell suggestions small string", 5, emptyWords.suggestions(small, 5).size());
		assertEquals("Testing size of spell suggestions big string", 12, emptyWords.suggestions(big, 12).size());
	}

}
