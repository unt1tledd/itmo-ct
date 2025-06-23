package markup;

import java.util.List;

abstract class AbstractAddMarkdown implements MarkdownElement {
    private final List<MarkdownElement> elements;

    public AbstractAddMarkdown(List<MarkdownElement> elements) {
        this.elements = elements;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(getSymb());
        for (MarkdownElement el: elements) {
            el.toMarkdown(sb);
        }
        sb.append(getSymb());
    }

    @Override
    public void toTypst(StringBuilder sb) {
        sb.append(getTag());
        sb.append("[");
        for (MarkdownElement el: elements) {
            el.toTypst(sb);
        }
        sb.append("]");
    }

    protected abstract String getTag();
    protected abstract String getSymb();
}