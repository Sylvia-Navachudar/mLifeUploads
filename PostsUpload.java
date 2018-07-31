
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.CDL;


import org.json.simple.JSONArray;
import org.json.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unused")
public class PostsUpload {

	public JSONObject Basicmodel=new JSONObject();
	
	JSONArray allPosts = new JSONArray();
	JSONParser parser = new JSONParser();
	String CSV;
	
	static String rootPath;
	static String postIdsFile;
	ArrayList<Long> postIdsArray = new ArrayList<Long>(110);
	
	public void saveIds(ArrayList<Long> postIds) throws IOException {
		FileOutputStream fout= new FileOutputStream (postIdsFile);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(postIds);
		fout.close();
		}
	

	@SuppressWarnings("unchecked")
	public ArrayList<Long> readIds() throws IOException, ClassNotFoundException {
		
		File file = new File(postIdsFile);
		ArrayList<Long> postIds = new ArrayList<Long>(110);
		if(file.length()!=0) {
			FileInputStream fin= new FileInputStream (postIdsFile);
			ObjectInputStream ois = new ObjectInputStream(fin);
			 postIds = (ArrayList<Long>)ois.readObject();
			fin.close();
		}
		
		return postIds;
		}
	
    public String insertIntoDb() throws InterruptedException {
		// TODO Auto-generated method stub
		
				
		        String result = null;
		        //59a8451e7bc696215f376907
		        // Chinju Company = 5696870ff5b6762f385aadc0
		        //http://document-service.meltwater.net:8080/documents/12345/editorial
		        //https://document-service-nrstaging.meltwater.net/documents/5ad67241efb8c09508513e53/editorial
		        //Tamar id = 5ad67241efb8c09508513e53
		        //angry bird = 5a852c55dc3f66c2e35abaf5
		        
			    String url="http://document-service.meltwater.net:8080/documents/5ad67241efb8c09508513e53/editorial";
			    
			    String[] command = {"curl", "-X", "POST", "--header","Content-Type:application/json","--header","Accept: application/json","-d", "@"+PostsUpload.rootPath+"/mLifeUploads/test.json", "charset=UTF-8",url };
			    
			    ProcessBuilder process = new ProcessBuilder(command);
			    
			        Process p;
			        try
			        {
			            p = process.start();
			      //  process.wait();
			            BufferedReader reader =  new BufferedReader(new InputStreamReader(p.getInputStream()));
			                StringBuilder builder = new StringBuilder();
			                String line = null;
			                while ( (line = reader.readLine()) != null) {
			                        builder.append(line);
			                        builder.append(System.getProperty("line.separator"));
			                }
			                result = builder.toString();
			                

			        }
			        catch (IOException e)
			        {   System.out.print("error");
			            e.printStackTrace();
			        }
			        
			        return result;
}
    
    boolean checkResult(String message)
    {
    	
    	if(message.contains("{\"httpStatus\":400,\"message\":\"Not possible to parse the JSON request, it contains errors.\"}"))
    		return false;
    	else
    		return true;
    	
    }

	
public JSONObject getJSONObject(String urlString) {
		
		JSONObject json = null;
		
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			StringBuilder sb=new StringBuilder();
			//System.out.println("Output from Server .... \n");
			
			while ((output = br.readLine()) != null) {
				sb.append(output);//now original string is changed  
			}
			
			JSONParser parser = new JSONParser();
			try {
				json = (JSONObject) parser.parse(sb.toString());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
		}
			
		
	 catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  }
		
			return json; //JSON Object response recieved through the API call. 
	}
	

	
	
	public long getCurrentEPOCHTime() throws ParseException, java.text.ParseException{
		
		Date today = Calendar.getInstance().getTime();
		 
		SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
 
	    String currentTime = crunchifyFormat.format(today);
		
	    Date date = crunchifyFormat.parse(currentTime);
 
		long epochTime = date.getTime();
		
		return epochTime;
		
	}
	
	
public long getEPOCHTime(String sDate) throws ParseException, java.text.ParseException{
		
       Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(sDate);  
	   long epoch=date1.getTime();
	   return epoch;
	   
	}  
	
		
public JSONArray getJSONArray(String urlString) {
	
	JSONArray json = null;
	try {
		
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		StringBuilder sb=new StringBuilder();
		//System.out.println("Output from Server .... \n");
		
		while ((output = br.readLine()) != null) {
			sb.append(output);//now original string is changed  
		}
		
		JSONParser parser = new JSONParser();
		try {
			
			json = (JSONArray) parser.parse(sb.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
		
	
		catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	
		return json;
}

			
	
	@SuppressWarnings("unchecked")
	public void CreateBasicModel() throws ParseException, java.text.ParseException {
		
		JSONObject metadata_data=new JSONObject();
        metadata_data.put("_quiddityType","document.DocumentMetaData");
        
        metadata_data.put("fetchingTime",getCurrentEPOCHTime());
        Basicmodel.put("metaData",metadata_data);
        
        JSONObject provider_data=new JSONObject();
        provider_data.put("_quiddityType", "document.Provider");
        provider_data.put("type", "mw");
        provider_data.put("_quiddityVersion", "1.0");
        metadata_data.put("provider",provider_data);
        metadata_data.put("mediaType","news");
        
        JSONObject source_data=new JSONObject();
        source_data.put("_quiddityType","document.Source");
        source_data.put("allowPdf",false);
        source_data.put("name","mLife");
        
        JSONObject location_data=new JSONObject();
        location_data.put("_quiddityType","common.GeoLocation");
        location_data.put("countryCode","");
        location_data.put("_quiddityVersion","1.1");
        
        source_data.put("location", location_data);
        source_data.put("_quiddityVersion","1.3");
        source_data.put("informationType","news");
        metadata_data.put("source",source_data);
        metadata_data.put("_quiddityVersion","1.3");
        metadata_data.put("url","");
        
        
        Basicmodel.put("_quiddityType","document.Document");
        JSONObject system_data=new JSONObject();
        system_data.put("_quiddityType", "system.SystemData");
        
        JSONObject policies_data=new JSONObject();
        policies_data.put("_quiddityType","system.Policies");
        JSONObject storage_data=new JSONObject();
        storage_data.put("_quiddityType","system.StoragePolicy");
        storage_data.put("overwrite", false);
        storage_data.put("_quiddityVersion","1.2");
        policies_data.put("_quiddityVersion", "1.0");
        system_data.put("_quiddityVersion","1.1");
        system_data.put("status","active");
        Basicmodel.put("systemData", system_data);
        policies_data.put("storage", storage_data);
        system_data.put("policies", policies_data);
        JSONArray attachments_data=new JSONArray();
       
        JSONObject attachments_data_obj = new JSONObject();
        
        attachments_data_obj.put("_quiddityType","common.Attachment");
        attachments_data_obj.put("link","");
        attachments_data_obj.put("_quiddityVersion","1.1");
        attachments_data.add(attachments_data_obj);
        Basicmodel.put("attachments",attachments_data);
        
        
       
        JSONObject body_data=new JSONObject();
       
        JSONObject ingress_data=new JSONObject();
        ingress_data.put("_quiddityType", "common.Text");
        ingress_data.put("text", "");
        ingress_data.put("type", "plain");
        ingress_data.put("_quiddityVersion", "1.0");
        body_data.put("_quiddityType", "document.DocumentBodyData");
      
        JSONObject byLine_data=new JSONObject();
        byLine_data.put("_quiddityType","common.Text");
        byLine_data.put("text","");
        byLine_data.put("type","plain");
        byLine_data.put("_quiddityVersion","1.0");
        Basicmodel.put("body",body_data);
        body_data.put("ingress",ingress_data);
        body_data.put("byLine", byLine_data);
  
        
       JSONObject publish_data=new JSONObject();
       publish_data.put("date"," ");
       publish_data.put("_quiddityType","common.TimeWithTimezone");
       publish_data.put("_quiddityVersion","1.0");
       JSONObject title_data=new JSONObject();
       title_data.put("_quiddityType", "common.Text");
       title_data.put("text", "");
       title_data.put("type", "plain");
       title_data.put("_quiddityVersion", "1.0");
       
       JSONObject content_data=new JSONObject();
       content_data.put("_quiddityType", "common.Text");
       content_data.put("text", "");
       content_data.put("type", "plain");
       content_data.put("_quiddityVersion", "1.0");
       
       
       body_data.put("publishDate", publish_data);
       body_data.put("title",title_data);
       body_data.put("content", content_data);
       body_data.put("_quiddityVersion","1.2");
       Basicmodel.put("_quiddityVersion","1.2");
       
        
     }
	
	
	@SuppressWarnings("unchecked")
	public void Map_posts_to_Model() throws ParseException, java.text.ParseException, ClassNotFoundException, IOException
	{
		
		//?per_page=100
        JSONObject Postobj = getJSONObject("https://public-api.wordpress.com/rest/v1.1/sites/mlife.meltwater.com/posts/");
        JSONArray PostsArray = (JSONArray) Postobj.get("posts");
        
		
        postIdsArray = readIds();
    	
    	System.out.println("the Ids in file are= "+postIdsArray);
    	
    	
    	
    	
       
      //<Postobj.size();
        for(int postNo=0;postNo<PostsArray.size();postNo++)
		{
        	
        	
        	JSONObject JObj = (JSONObject) PostsArray.get(postNo);
        	
        	long postId = (long) JObj.get("ID");
        	
        	if(!(postIdsArray.contains(postId))) {
        		
        		postIdsArray.add(postId);
        		
        		JSONObject metaObj = (JSONObject) Basicmodel.get("metaData");
    			
    			//get link
    			JSONObject terms = (JSONObject) JObj.get("terms");
    			JSONObject category = (JSONObject) terms.get("category");
    			
				Set<?> keys = category.keySet();
    			Iterator<?> keys_iter = keys.iterator();
    			String categoryName = (String) keys_iter.next();
    			JSONObject category_obj = (JSONObject) category.get(categoryName);
    			String link =  JObj.get("URL")+"/?category="+category_obj.get("ID")+"&"+"wordpressID="+postId;
    			metaObj.put("url", link);
    			
    			
    			//get Article Image
    			
    			JSONArray attachmentsArray = (JSONArray) Basicmodel.get("attachments");
    			JSONObject attachmentsObj = (JSONObject) attachmentsArray.get(0);
    			attachmentsObj.put("link",JObj.get("featured_image"));
    			
    			
    			
    			//get content 
    			JSONObject bodyObj = (JSONObject) Basicmodel.get("body");
    			//JSONObject ingressObj = (JSONObject) bodyObj.get("ingress");
    			String str1 = (String)JObj.get("excerpt");
    			String str2 =  str1.replaceAll("<p>"," ");
    			String content = str2.replaceAll("</p>"," ");
    			JSONObject contentObj = (JSONObject) bodyObj.get("content");
    			
    			contentObj.put("text",Normalizer.normalize(content, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
    			
    			
    			//get title
    			JSONObject titleObj = (JSONObject) bodyObj.get("title");
    			titleObj.put("text",Normalizer.normalize((String)JObj.get("title"), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
    			
    			
    			//get byLine (author)
    			JSONObject byLineObj = (JSONObject)bodyObj.get("byLine");
    			JSONObject authorObj = (JSONObject) JObj.get("author");
    			byLineObj.put("text",Normalizer.normalize((String)authorObj.get("name"), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "") );
    			
    			
    			//get publish date
    			JSONObject publishObj = (JSONObject) bodyObj.get("publishDate");
    			publishObj.put("date", getEPOCHTime(JObj.get("date").toString()));
    			
    			//System.out.println(getEPOCHTime(JObj.get("date").toString()));
    			
    			allPosts.add(Basicmodel.toJSONString());
    			
    			//System.out.println(Basicmodel);
    		
        	
        	}
        	
        	else {
        		System.out.println("Post is already inserted");
        	}
        	
        }
	}
        
        
    

        
 public static void main(String[] args) throws Exception{
	 
	 	PostsUpload.rootPath =  System.getenv("root_path_server");
	 	PostsUpload.postIdsFile =  rootPath+"/mLifeUploads/postIds.txt";
		
	    PostsUpload BMObj = new PostsUpload(); 
	    
	    System.out.println("Creating Basic Model.........");
	    BMObj.CreateBasicModel();
	    
	    System.out.println("Mapping posts to Model..........");
	    BMObj.Map_posts_to_Model();
	    
	    
	    for(int i=0;i<BMObj.allPosts.size();i++)
	    {
	    	
	    FileWriter writer = new FileWriter(PostsUpload.rootPath+"/mLifeUploads/test.json");
	    BufferedWriter buffer = new BufferedWriter(writer);  
	    buffer.write(( BMObj.allPosts.get(i)).toString());
	    buffer.flush();
	    String result = BMObj.insertIntoDb();
	    
	    if(!(BMObj.checkResult(result))) {
	    	
	    	BMObj.postIdsArray.remove(i);
	    }
	    
	    System.out.println(result);
	    buffer.close();  
	    
	    }
	    
System.out.println("The ids added in file are ="+BMObj.postIdsArray);
BMObj.saveIds(BMObj.postIdsArray);

			    	
}

 }
	
	
  	
	    	
	    	
	    	
	  
	    
	    
	    
	    
	    
	    
	    
	   





        

            
            
            
            








		
      
	
