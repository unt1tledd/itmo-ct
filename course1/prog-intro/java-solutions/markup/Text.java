package markup;
import markup.MarkdownElement;

public class Text implements MarkdownElement {
    private final String str;

    public Text(String str) {
        this.str = str;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(str);
    }

    @Override
    public void toTypst(StringBuilder sb) {
        sb.append(str);
    }
}