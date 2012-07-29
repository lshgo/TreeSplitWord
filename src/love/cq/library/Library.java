package love.cq.library;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import love.cq.domain.Branch;
import love.cq.domain.Forest;
import love.cq.domain.WoodInterface;
import love.cq.util.IOUtil;

public class Library {
	private static final String charEncoding = "UTF-8";

	public static Forest makeForest(String path) throws Exception {
		return makeForest(new FileInputStream(path));
	}

	public static Forest makeForest(InputStream inputStream) throws Exception {
		return makeForest(IOUtil.getReader(inputStream, "UTF-8"));
	}

	public static Forest makeForest(BufferedReader br) throws Exception {
		return makeLibrary(br, new Forest());
	}

	private static Forest makeLibrary(BufferedReader br, Forest forest) throws Exception {
		try {
			String temp = null;
			while ((temp = br.readLine()) != null) {
				insertWord(forest, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
		return forest;
	}

	/**
	 * 插入一个词
	 * 
	 * @param forest
	 * @param temp
	 */
	public static void insertWord(Forest forest, String temp) {

		String[] param = temp.split("\t");

		temp = param[0];

		boolean hasNext = true;

		boolean isWords = true;

		WoodInterface branch = forest;
		char[] chars = temp.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars.length == i + 1) {
				isWords = true;
				hasNext = false;
			} else {
				isWords = false;
				hasNext = true;
			}
			int status = 1;
			if ((isWords) && (hasNext)) {
				status = 2;
			}

			if ((!isWords) && (hasNext)) {
				status = 1;
			}

			if ((isWords) && (!hasNext)) {
				status = 3;
			}
			if ((status == 2) || (status == 3))
				branch.add(new Branch(chars[i], status, param));
			else {
				branch.add(new Branch(chars[i], status, null));
			}
			branch = branch.get(chars[i]);
		}
	}

	/**
	 * 删除一个词
	 * 
	 * @param forest
	 * @param temp
	 */
	public static void removeWord(Forest forest, String word) {

		boolean hasNext = true;

		boolean isWords = true;

		WoodInterface branch = forest;
		char[] chars = word.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars.length == i + 1) {
				isWords = true;
				hasNext = false;
			} else {
				isWords = false;
				hasNext = true;
			}
			int status = 1;
			if ((isWords) && (hasNext)) {
				status = 2;
			}

			if ((!isWords) && (hasNext)) {
				status = 1;
			}

			if ((isWords) && (!hasNext)) {
				status = 3;
			}
			if ((status == 2) || (status == 3))
				branch.add(new Branch(chars[i], -1, null));
			else {
				branch.add(new Branch(chars[i], status, null));
			}
			branch = branch.get(chars[i]);
		}
	}
}