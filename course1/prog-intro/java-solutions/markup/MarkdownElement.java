package markup;
import java.util.*;

public interface MarkdownElement {
    void toMarkdown(StringBuilder sb);
    void toTypst(StringBuilder sb);
}
