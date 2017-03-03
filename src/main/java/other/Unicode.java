package other;

import java.io.PrintStream;

public class Unicode {
    private static PrintStream out = System.out;

    public static void stuff() {
        int codePoint = 0x10000;
        char[] sPair = Character.toChars(codePoint);
        String s = new String(sPair);
        out.println("An example of a supplementary (i.e. not basic) unicode character, " +
                "i.e. a character with a code point of a higher value than afforded by 16 bits: " + s);
        out.format("Note that the string denoting this character has length %s. That's because we need a surrogate pair" +
                " to represent this single unicode character%n", s.length());
        out.format("Here is the surrogate pair for this unicode character: (%s, %s).%n",
                Integer.toHexString(s.charAt(0)), Integer.toHexString(s.charAt(1)));
        out.println();

        char ch1 = 0xD800;
        char ch2 = 0xDC00;
        out.print("Using a surrogate pair (0xd800, 0xdc01) to print a single character: ");
        out.println(new char[]{ch1, ch2});
        out.println();

        char a = '\u1234';
        out.println("A literal unicode character '\\u1234' is printed as: " + a);
    }
}
