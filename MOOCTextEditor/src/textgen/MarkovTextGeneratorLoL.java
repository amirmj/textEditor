package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	// trained or not
	private boolean isTrained = false;

	public MarkovTextGeneratorLoL(Random generator) {
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}

	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) {
		if (isTrained) {
			return;
		}
		String[] words = sourceText.split("[ ]+");
		starter = words[0];
		String prevWord = starter;
		for (int i = 1; i < words.length; i++) {
			ListNode prevNode;
			if (getListNode(prevWord) == null) {
				prevNode = new ListNode(prevWord);
				wordList.add(prevNode);
			} else {
				prevNode = getListNode(prevWord);
			}
			prevNode.addNextWord(words[i]);

			prevWord = words[i];
		}
		ListNode lastNode = new ListNode(words[words.length - 1]);
		lastNode.addNextWord(starter);
		wordList.add(lastNode);
		isTrained = true;
	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		if (wordList.isEmpty() || numWords <= 0) {
			return "";
		}
		String currentWord = starter;
		String outPut = "";
		int wordCounter = 1;
		outPut += currentWord;
		while (wordCounter < numWords) {
			ListNode currentNode = getListNode(currentWord);
			String nextWord = currentNode.getRandomNextWord(rnGenerator);
			outPut += " " + nextWord;
			currentWord = nextWord;
			wordCounter++;
		}
		return outPut;
	}

	// Can be helpful for debugging
	@Override
	public String toString() {
		String toReturn = "";
		for (ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText) {
		isTrained = false;
		wordList.clear();
		train(sourceText);
	}

	private ListNode getListNode(String word) {
		ListNode searchNode = null;
		for (ListNode listNode : wordList) {
			if (listNode.getWord().equals(word)) {
				searchNode = listNode;
			}
		}
		return searchNode;
	}

	/**
	 * This is a minimal set of tests. Note that it can be difficult to test
	 * methods/classes with randomized behavior.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(12));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";

		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));

		String textString2 = "You say yes, I say no, " + "You say stop, and I say go, go, go, "
				+ "Oh no. You say goodbye and I say hello, hello, hello, " + "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "I say high, you say low, " + "You say why, and I say I don't know. "
				+ "Oh no. " + "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, " + "I don't know why you say goodbye, I say hello. "
				+ "Why, why, why, why, why, why, " + "Do you say goodbye. " + "Oh no. " + "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, " + "I don't know why you say goodbye, I say hello. "
				+ "You say yes, I say no, " + "You say stop and I say go, go, go. " + "Oh, oh no. "
				+ "You say goodbye and I say hello, hello, hello. " + "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));

	}
}

/**
 * Links a word to the next words in the list You should use this class in your
 * implementation.
 */
class ListNode {
	// The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord() {
		return word;
	}

	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator) {
		int randomIndex = generator.nextInt(nextWords.size());
		String randomWord = nextWords.get(randomIndex);
		return randomWord;
	}

	public String toString() {
		String toReturn = word + ": ";
		int counter = 0;
		for (String s : nextWords) {
			toReturn += (counter == nextWords.size() - 1) ? s : s + "->";
			counter++;
		}
		toReturn += "\n";
		return toReturn;
	}

}
