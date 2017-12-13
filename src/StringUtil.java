import java.util.Collections;

public class StringUtil {
    public static String repeat(String text, int count) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < count; i++) {
            s.append(text);
        }
        return s.toString();
    }
}
