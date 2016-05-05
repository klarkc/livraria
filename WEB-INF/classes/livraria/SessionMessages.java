package livraria;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class SessionMessages {
    private HttpSession session;
    private List<String> errors;

    public SessionMessages(HttpSession session) {
        this.session = session;
        errors = (List) this.session.getAttribute("errors");
        if(errors == null) {
            errors = new ArrayList<String>();
        }

        this.session.setAttribute("errors", errors);
    }

    public void addError(String errorMsg) {
        errors.add(errorMsg);
    }

    public String getAndFlush() {
        HtmlBuilder errors = new HtmlBuilder();
        for(String error: this.errors) {
            errors.appendLine("<div class=\"alert alert-danger\">");
            errors.append(error);
            errors.append("</div>");
        }

        this.errors = new ArrayList<String>();
        session.setAttribute("errors", this.errors);

        if(errors.length() > 0) System.out.println("ERROR MSGS: " + errors.toString());

        return errors.toString();
    }
}
