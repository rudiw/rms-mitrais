package com.mitrais.rms.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 *
 */
public class Link extends SimpleTagSupport
{
    private String href;
    private String type;

    public void doTag() throws JspException, IOException
    {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String relLink = request.getContextPath()+ "/" + this.href;
        JspWriter out = getJspContext().getOut();
        out.println("<link rel=\""+type+"\" href=\""+relLink+"\">");
    }

    public String getHref()
    {
        return href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
