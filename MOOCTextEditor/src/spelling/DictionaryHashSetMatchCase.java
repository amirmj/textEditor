/**
 * 
 */
package spelling;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

/**
 * A class that implements the Dictionary interface with a HashSet
 */
public class DictionaryHashSetMatchCase implements Dictionary {

	private HashSet<String> words;

	public DictionaryHashSetMatchCase() {
		words = new HashSet<>();
	}

	/**
	 * Add this word to the dictionary.
	 * 
	 * @param word The word to add
	 * @return true if the word was added to the dictionary (it wasn't already
	 *         there).
	 */
	@Override
	public boolean addWord(String word) {
		return words.add(word);
	}

	/** Return the number of words in the dictionary */
	@Override
	public int size() {
		return words.size();
	}

	/** Is this a word according to this dictionary? */
	@Override
	public boolean isWord(String s) {
		boolean allCapsWord = checkAllCaps(s);
		if (s.isEmpty()) {
			return false;
		} else if (words.contains(s)) {
			return true;
		} else if (Character.isUpperCase(s.charAt(0))) {
			s = s.substring(0, 1).toLowerCase() + s.substring(1);
			if (words.contains(s)) {
				return true;
			}
		} else if (allCapsWord) {
			s = s.toLowerCase();
			if (words.contains(s)) {
				return true;
			} else {
				s = s.substring(0, 1).toUpperCase() + s.substring(1);
				if (words.contains(s)) {
					return true;
				}
			}

		}
		return false;
	}

	private boolean checkAllCaps(String s) {
		for (Character ch : s.toCharArray()) {
			if (Character.isLowerCase(ch)) {
				return false;
			}
		}
		return true;
	}

}
