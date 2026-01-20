package dev.ilovapples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	private static final String PROGRAM_NAME = "config_reader";

	private static final String USAGE_MSG = """
		usage: %1$s <config_file> <option_name>
		       %1$s <option_name>
		prints to stdout the value of 'option_name' found in 'config_file'
		(defaults to './config.txt' if unspecified)
		""";

	private static final String DEFAULT_CONFIG_PATH = "config.txt";

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.format(USAGE_MSG, PROGRAM_NAME);
			System.exit(1);
		}

		final String configFileName = (args.length > 1) ? args[0] : DEFAULT_CONFIG_PATH;
		final String optionName = (args.length > 1) ? args[1] : args[0];

		File configFile = new File(configFileName);

		try (Scanner fileScanner = new Scanner(configFile)) {
			String line = null;
			// loop to find option
			int lineNum = 1;
			while (true) {
				if (!fileScanner.hasNextLine()) {
					failWithErrorCode(3, "failed to find option '%s' in file '%s'",
							optionName, configFileName);
				}

				// line is definitely initialized after try-catch block
				try {
					line = fileScanner.nextLine();
				} catch (Exception e) {
					failWithErrorCode(2, "an error occurred while reading from file '%s'",
							configFileName);
				}

				if (line.startsWith(optionName)) {
					final char afterOption = line.charAt(optionName.length());
					if (Character.isWhitespace(afterOption) || afterOption == '=')
						break;
				}

				++lineNum;
			}

			final int foundOnLine = lineNum;
			int equalsPos = optionName.length();
			for (; equalsPos < line.length(); ++equalsPos) {
				if (!Character.isWhitespace(line.charAt(equalsPos)))
					break;
			}
			if (equalsPos == line.length()) {
				failWithErrorCode(3, "end of line encountered immediately after option name '%s' on line %d",
						optionName, foundOnLine);
			} else if (line.charAt(equalsPos) != '=') {
				failWithErrorCode(3, "expected '=', found '%c' following option name '%s' on line %d",
						line.charAt(equalsPos), optionName, foundOnLine);
			}

			int startDelimiterPos = equalsPos + 1;
			for (; startDelimiterPos < line.length(); ++startDelimiterPos) {
				if (!Character.isWhitespace(line.charAt(startDelimiterPos)))
					break;
			}
			if (startDelimiterPos == line.length()) {
				failWithErrorCode(3, "end of line encountered after '=' following option name '%s' on line %d",
						optionName, foundOnLine);
			}

			final char delimiter = line.charAt(startDelimiterPos);

			// print value
			int startSearchPos = startDelimiterPos + 1;
			while (true) {
				final int potentialEndPos = line.indexOf(delimiter, startSearchPos);
				if (potentialEndPos != -1) {
					System.out.print(line.substring(startSearchPos, potentialEndPos));
					System.exit(0);
				}

				System.out.print(line);

				startSearchPos = 0;

				if (!fileScanner.hasNextLine()) {
					failWithErrorCode(3, "unterminated value for option '%s' starts on line %d",
							optionName, foundOnLine);
				}

				try {
					line = fileScanner.nextLine();
				} catch (Exception e) {
					failWithErrorCode(2, "an error occurred while reading from file '%s'",
							configFileName);
				}

				++lineNum;
			}
		} catch (FileNotFoundException e) {
			failWithErrorCode(2, "failed to open file '%s' for reading", configFile);
		}
	}

	private static void failWithErrorCode(int code, final String fmt, Object... args) {
		System.err.format(PROGRAM_NAME + ": " + fmt + "\n", args);

		System.exit(code);
	}
}
