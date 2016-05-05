package livraria;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class SessionMessages {
    private HttpSession session;
    private List<String> errors;
    private List<String> success;

    public SessionMessages(HttpSession session) {
        this.session = session;
        errors = (List) this.session.getAttribute("errors");
        success = (List) this.session.getAttribute("success");

        if(errors == null) {
            errors = new ArrayList<String>();
        }

        if(success == null) {
            success = new ArrayList<String>();
        }

        this.session.setAttribute("errors", errors);
        this.session.setAttribute("success", success);
    }

    public void addError(String errorMsg) {
        errors.add(errorMsg);
    }

    public void addSuccess(String successMsg) {
        success.add(successMsg);
    }

    public String getAndFlush() {
        HtmlBuilder errors = new HtmlBuilder();
        HtmlBuilder success = new HtmlBuilder();
        HtmlBuilder messages = new HtmlBuilder();

        for(String msg: this.errors) {
            errors.appendLine("<div class=\"alert alert-danger\">");
            errors.append(msg);
            errors.append("</div>");
        }

        for(String msg: this.success) {
            success.appendLine("<div class=\"alert alert-success\">");
            success.append(msg);
            success.append("</div>");
        }

        this.errors = new ArrayList<String>();
        this.success = new ArrayList<String>();
        session.setAttribute("errors", this.errors);
        session.setAttribute("success", this.success);

        if(errors.length() > 0) System.out.println("ERROR MSGS: " + errors.toString());

        messages.append(errors.toString());
        messages.append(success.toString());
        return messages.toString();
    }
}
