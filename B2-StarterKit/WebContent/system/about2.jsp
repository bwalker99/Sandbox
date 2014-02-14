<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.spvsoftwareproducts.blackboard.utils.B2Context, 
				blackboard.platform.plugin.*,
				java.text.*,
				java.util.*"
				errorPage="../error.jsp"%>
<%@taglib uri="/bbNG" prefix="bbNG" %>
<bbNG:genericPage title="This is the &lt;bbNG:genericPage&gt; title property" entitlement="system.admin.VIEW">
<bbNG:cssFile href="../css/b2sk.css" />
<%
  B2Context b2Context = new B2Context(request);
  String cancelUrl = b2Context.getNavigationItem("admin_plugin_manage").getHref();
  pageContext.setAttribute("bundle", b2Context.getResourceStrings());
  PlugIn p = b2Context.getPlugIn();  
 
%>
<bbNG:pageHeader instructions="${bundle['page.system.about.instructions']}">
		<bbNG:breadcrumbBar environment="SYS_ADMIN_PANEL" navItem="admin_plugin_manage">
      		<bbNG:breadcrumb href="index.jsp" title="${bundle['plugin.name']}" />
      		<bbNG:breadcrumb title="${bundle['page.system.about.breadcrumb']}" />
    	</bbNG:breadcrumbBar>	    
	    <bbNG:pageTitleBar iconUrl="${bundle['plugin.logo']}" showIcon="true" showTitleBar="true" title="${bundle['page.system.about.title']}"/>
</bbNG:pageHeader>
<h2>About this Building Block</h2>
<% if (p != null)  {%>
<table border="1" cellpadding="2" cellspacing="0">
	<tr><th><b>Property</b></th><th><b>Value</b></th></tr>
	<tr><td>Id</td><td><%=p.getId().toExternalString() %></td></tr>	
	<tr><td>Name</td><td><%=p.getName() %></td></tr>
	<tr><td>Description</td><td><%=p.getDescription() %></td></tr>
	<tr><td>Version</td><td><%=p.getVersion() %></td></tr>	
	<tr><td>VendorId</td><td><%=p.getVendorId() %></td></tr>
	<tr><td>VendorName</td><td><%=p.getVendorName() %></td></tr>
	<tr><td>VendorUrl</td><td><%=p.getVendorURL() %></td></tr>
	<tr><td>VendorDescription</td><td><%=p.getVendorDescription() %></td></tr>
</table>
You are logged as: <%=bbContext.getUser().getUserName() %>.<br/>
<h2>License</h2>
<pre>

This is an additional page added by Bob. 


</pre>
<% } //end if %>
</bbNG:genericPage>
