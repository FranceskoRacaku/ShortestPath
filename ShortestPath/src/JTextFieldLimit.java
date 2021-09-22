import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.JTextField;

public class JTextFieldLimit extends PlainDocument {
    // Want State input to be limited to 2 characters
    // Want City input to be limited to 85 chars

    private int maxChars;


    JTextFieldLimit(int maxChars) {
        super();
        this.maxChars = maxChars;
    }


    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= maxChars) {
            super.insertString(offset, str, attr);
        }
    }
}
