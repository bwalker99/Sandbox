
import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;
import java.io.File;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;


public class HelloWorld {

	public static void main(String[] args) {
		System.out.println("Hello World to Git from Bob with more - edit from Github");
		System.out.println("Edit from home - more edits from home...");
		System.out.println("More edits from work...123-456");
		System.out.println("Edits from home");

	}
	
	private void Test() { 
		Map<String,String> resultMap = new HashMap<String,String>();
	    resultMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
	    try { PrintWriter response = new PrintWriter(new File("dog.txt")); } catch (Exception e) { }
	    //response.setContentType("text/x-json");
	    //response.setCharacterEncoding("UTF-8");
	    JSON json = JSONSerializer.toJSON(resultMap);
	    
	    // json.write(response.getWriter());
	    
		
		
	}

}
