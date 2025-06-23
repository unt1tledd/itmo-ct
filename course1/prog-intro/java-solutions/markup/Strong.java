package markup;

import java.util.List;

public class Strong extends AbstractAddMarkdown {
    public Strong(List<MarkdownElement> elements) {
        super(elements);
    }

    @Override
    protected String getTag() {
        return "#strong";
    }

    @Override
    protected String getSymb() {
        return "__";
    }
}