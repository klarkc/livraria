package livraria;



public class HtmlBuilder {
    StringBuilder html = new StringBuilder();

    public void append(Object str) {
        html.append(str);
    }
    public void appendLine(Object str) {
        html.append("\n");
        html.append(str);
    }

    public String toString() {
        return html.toString();
    }

    public int length() {
        return html.length();
    }
}
