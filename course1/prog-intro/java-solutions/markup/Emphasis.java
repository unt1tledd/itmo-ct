package markup;

import java.util.List;

public class Emphasis extends AbstractAddMarkdown {
    public Emphasis(List<MarkdownElement> elements) {
        super(elements);
    }

    @Override
    protected String getTag() {
        return "#emph";
    }

    @Override
    protected String getSymb() {
        return "*";
    }
}