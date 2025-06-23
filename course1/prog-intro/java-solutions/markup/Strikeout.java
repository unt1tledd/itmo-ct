package markup;

import java.util.List;

public class Strikeout extends AbstractAddMarkdown {
    public Strikeout(List<MarkdownElement> elements) {
        super(elements);
    }

    @Override
    protected String getTag() {
        return "#strike";
    }
    @Override
    protected String getSymb() {
        return "~";
    }
}