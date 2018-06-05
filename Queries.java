package swingFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Queries {

	String businessFinalQuery = "";
	String userFinalQuery = "";
	
	public ArrayList<String> getCategories(){
		 PreparedStatement preparedStmnt = null;
		 ArrayList<String> allCategories = new ArrayList<>();
		 ResultSet resultSet = null;
	        Connection connection = null;
	        try {

	            // Establish a connection
	            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:rasikadbor", "system", "rasikarst");
	        
		String query = "SELECT  DISTINCT(CatName) FROM BUSINESSCATEGORY where CatName in ('Active Life','Arts & Entertainment','Automotive','Car Rental','Cafes','Beauty & Spas','Convenience Stores','Dentists','Doctors','Drugstores','Department Stores','Education','Event Planning & Services','Flowers & Gifts','Food','Health & Medical','Home Services','Home & Garden','Hospitals','Hotels & Travel','Hardware Stores','Grocery','Medical Centers','Nurseries & Gardening','Nightlife','Restaurants','Shopping','Transportation') order by CatName ";
		 preparedStmnt = connection.prepareStatement(query);
         
         resultSet = preparedStmnt.executeQuery();

         while (resultSet.next()) {
             String subcat = resultSet.getString(1);
             allCategories.add(subcat);
             
         }
         connection.close();
         resultSet.close();
         preparedStmnt.close();
         
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
         return allCategories;
	}
	
	public ArrayList<String> getSubCategories(ArrayList<String> categories){
		 PreparedStatement preparedStmnt = null;
		 ArrayList<String> allCategories = new ArrayList<>();
		 ResultSet resultSet = null;
	        Connection connection = null;
	        try {

	            // Establish a connection
	            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:rasikadbor", "system", "rasikarst");
	        String conditions = populateArrayList(categories);
		String query = "SELECT  DISTINCT(SubCatName) FROM BUSINESSSUBCATEGORY where BID in (SELECT BID from BUSINESSCATEGORY WHERE CatName IN ("+conditions+")) ORDER BY SubCatName ";
		 preparedStmnt = connection.prepareStatement(query);
        
        resultSet = preparedStmnt.executeQuery();

        while (resultSet.next()) {
            String subcat = resultSet.getString(1);
            allCategories.add(subcat);
            
        }
        connection.close();
        resultSet.close();
        preparedStmnt.close();
        
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        return allCategories;
	}
	
	public ArrayList<String> getAttributes(ArrayList<String> categories,ArrayList<String> subCategories,int andOr){
		 PreparedStatement preparedStmnt = null;
		 ArrayList<String> allCategories = new ArrayList<>();
		 ResultSet resultSet = null;
		 String query = null;
	        Connection connection = null;
	        try {

	            // Establish a connection
	            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:rasikadbor", "system", "rasikarst");
	       String operator = "";
	            if(andOr==0)
	            	operator = " intersect ";
	            else if(andOr==1)
	            	operator = " union ";
	      
	        	for(String subCategory:subCategories){
	        		if(query == null){
	        			query = "select ATTRIBUTES from BUSINESS where BID in (SELECT BID FROM BUSINESSSUBCATEGORY WHERE SUBCATNAME = '"+subCategory+"')";
	        		}else{
	        			query = query.substring(0, query.length()-1) + " "+operator+" SELECT BID FROM BUSINESSSUBCATEGORY WHERE  SUBCATNAME = '"+subCategory+"')";
	        		}
	        	}
	        
		 preparedStmnt = connection.prepareStatement(query);
       
       resultSet = preparedStmnt.executeQuery();

       while (resultSet.next()) {
           String attributes = resultSet.getString(1);
           String[] attributesArray = attributes.split(",");
           for(String attribute:attributesArray){
        	   String attributeKey = attribute.replace("[", "").replace("]", "").trim();
        	   if(!attributeKey.equals(""))
        	   if(!allCategories.contains(attributeKey))
        		   allCategories.add(attributeKey);
           }
       }
       connection.close();
       resultSet.close();
       preparedStmnt.close();
       
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
       return allCategories;
	}
	
 	@SuppressWarnings("unchecked")
	public ArrayList<Object> businessTable(Object... args){
 		String startDate = null;
 		String endDate = null;
 		String starOperator = null;
 		int starCount = 0;
 		String votesOperator = null;
 		int votesCount = 0;
 		ArrayList<String> categories = null;
 		ArrayList<String> subCatefories = null;
 		ArrayList<String> attributes = null;
 		String businessQuery = "";
 		HashMap<String,Object> result = null;
 		ArrayList<Object> resultList = new ArrayList<>();
 		PreparedStatement preparedStmnt = null;
		 ResultSet resultSet = null;
		 Connection connection = null;
		 String mainOperator = "";
 		try{
 			categories = (ArrayList<String>) args[0];
 			subCatefories = (ArrayList<String>) args[1];
 			attributes = (ArrayList<String>) args[2];
 			startDate = (String)args[3];
 			endDate = (String)args[4];
 			starOperator = (String) args[5];
 			starCount = (int)args[6];
 			votesOperator = (String)args[7];
 			votesCount = (int)args[8];
 			int businessOperatorIndex = (int)args[9];
 			if(businessOperatorIndex==0)
 				mainOperator = " intersect ";
 			else if(businessOperatorIndex==1)
 				mainOperator = " union ";
 			
 			
 			 DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:rasikadbor", "system", "rasikarst");

 			String query1 = null;
	        for(String category:categories){
 				if(query1 == null){
 					query1 = "(SELECT BID FROM BUSINESSCATEGORY WHERE CatName = '"+category+"'";
 				}else{
 					query1 = query1 + mainOperator + "SELECT BID FROM BUSINESSCATEGORY WHERE CatName = '"+category+"'";
 				}
 			}
	         businessQuery = "("+query1+")";
	         String query2 = null;
 			if(subCatefories!=null && !subCatefories.isEmpty()){
 				
 				for(String subCategory:subCatefories){
 					if(query2 == null)
 						query2 = "SELECT BID FROM BUSINESSSUBCATEGORY WHERE SubCatName = '"+subCategory+"'";
 					else
 	 					query2 = query2 + mainOperator + "SELECT BID FROM BUSINESSSUBCATEGORY WHERE SubCatName = '"+subCategory+"'";
 					
 				} 
 				query2 = "("+query2 + ")";
 				businessQuery = businessQuery + " INTERSECT " + query2;
 			}
 			if(attributes!=null && !attributes.isEmpty()){
 	 			String query3 = null;

 				for(String attribute:attributes)
 					if(query3 == null)
 						query3 = "SELECT BID FROM BUSINESS WHERE Attributes like '%"+attribute+"%'";
 					else
 						query3 = query3 +mainOperator+ "SELECT BID FROM BUSINESS WHERE Attributes like '%"+attribute+"%'";
 				query3 = "("+query3 + ")";
 				businessQuery = businessQuery + " INTERSECT " +query3;
 			}
 			 			
 			businessQuery = businessQuery + ")";
 			if(starCount!=0){
 				String	query4 = "SELECT BID FROM BUSINESS WHERE Stars "+starOperator+" "+starCount;
 				businessQuery = businessQuery + " INTERSECT " + query4;
 			}
 			
 			if(votesCount!=0){
 				String query5 = "SELECT bus.BID FROM BUSINESS bus JOIN (select SUM(FUNNYVOTE+COOLVOTE+USEFULVOTE) VOTES ,BID from REVIEW GROUP BY BID) temp on temp.BID = bus.BID where temp.VOTES "+votesOperator+" "+votesCount;
 				businessQuery = businessQuery + " INTERSECT " + query5;
 			}
 			String query6 = null;
 			if(startDate!=null && !startDate.isEmpty() && endDate!=null && !endDate.isEmpty()){
 				query6 = "SELECT DISTINCT(bus.BID) FROM BUSINESS bus JOIN REVIEW rev ON rev.BID = bus.BID AND rev.REVIEWDATE BETWEEN '"+startDate+"' and '"+endDate+"'";
 				businessQuery = businessQuery + " INTERSECT "+query6;
 			}else if (startDate!=null && !startDate.isEmpty()){
 				 query6 = "SELECT DISTINCT(bus.BID) FROM BUSINESS bus JOIN REVIEW rev ON rev.BID = bus.BID AND rev.REVIEWDATE > '"+startDate+"'";
 				businessQuery = businessQuery + " INTERSECT "+query6;
 			}else if(endDate!=null && !endDate.isEmpty()){
 				 query6 = "SELECT DISTINCT(bus.BID) FROM BUSINESS bus JOIN REVIEW rev ON rev.BID = bus.BID AND rev.REVIEWDATE < '"+endDate+"'";
 				businessQuery = businessQuery +" INTERSECT "+query6;
 			}
 			
 			String finalQuery = "SELECT DISTINCT BID,BNAME,CITY,STATE,STARS FROM BUSINESS WHERE BID IN ("+businessQuery+")";
 			businessFinalQuery = finalQuery;
 			
 			 preparedStmnt = connection.prepareStatement(finalQuery);
 		       
 		       resultSet = preparedStmnt.executeQuery();

 		       while (resultSet.next()) {
 		    	   result = new HashMap<>();
 		    	   result.put("BusinessID", resultSet.getString("BID"));
 		    	   result.put("Business", resultSet.getString("BNAME"));
 		    	   result.put("City", resultSet.getString("CITY"));
 		    	   result.put("State", resultSet.getString("STATE"));
 		    	   result.put("Stars", resultSet.getFloat("STARS"));
 		    	   resultList.add(result);
 		       }
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 		return resultList;
 	}
 	
 	public ArrayList<Object> usersTable(Object... args){
 		String memberSince = null;
 		int reviews = 0;
 		int friends = 0;
 		float avgStars = 0;
 		int numberOfVotes = 0;
 		String mainOperator = "";
 		HashMap<String,Object> result = null;
 		ArrayList<Object> resultList = new ArrayList<>();
 		PreparedStatement preparedStmnt = null;
		 ResultSet resultSet = null;
		 Connection connection = null;
 		try{
 			 DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:rasikadbor", "system", "rasikarst");
 			memberSince = (String)args[0];
 			String reviewOperator = (String)args[1];
 			reviews = (int)args[2];
 			String friendsOperator = (String)args[3];
 			friends = (int)args[4];
 			String avgStarOperator = (String)args[5];
 			avgStars = (float)args[6];
 			String votesOperator = (String)args[7];
 			numberOfVotes = (int)args[8];
 			String andOr = (String)args[9];
 			if(andOr.equalsIgnoreCase("and"))
 				mainOperator = " intersect ";
 			else
 				mainOperator = " union ";
 			String userQuery = null;
 			if(memberSince!=null){
 				String query1 = "SELECT USERID FROM YELPUSER WHERE YELPINGSINCE >='"+memberSince+"'";
 				userQuery = query1;
 			}
 			
 			if(reviews!=0){
 				String query2 = "SELECT USERID FROM YELPUSER WHERE REVIEWCOUNT "+reviewOperator+" "+reviews;
 				if(userQuery!=null)
 					userQuery  =userQuery +"\n"+ mainOperator +"\n"+ query2;
 				else 
 					userQuery = query2;
 			}
 			
 			if(avgStars!=0){
 				String query3  = "SELECT USERID FROM YELPUSER WHERE AVGSTARS "+avgStarOperator+" "+avgStars;
 				if(userQuery!=null)
 					userQuery  =userQuery +"\n"+ mainOperator +"\n"+ query3;
 				else 
 					userQuery = query3; 			}
 			
 			if(numberOfVotes!=0){
 				String query4 = "SELECT USERID FROM YELPUSER WHERE (FUNNYVOTES+USEFULVOTES+COOLVOTES) "+votesOperator+" "+numberOfVotes;
 				if(userQuery!=null)
 					userQuery  =userQuery +"\n"+ mainOperator +"\n"+ query4;
 				else 
 					userQuery = query4; 			}
 			
 			if(friends!=0){
 				String query5 = "SELECT USERID FROM FRIENDS GROUP BY USERID HAVING COUNT(FRIENDID) "+friendsOperator+" "+friends;
 				if(userQuery!=null)
 					userQuery  =userQuery +"\n"+ mainOperator +"\n"+ query5;
 				else 
 					userQuery = query5; 			}
 			
 			String finalQuery = "SELECT USERID,UNAME, YELPINGSINCE, AVGSTARS FROM YELPUSER WHERE USERID IN ("+userQuery+")";
 			userFinalQuery = finalQuery;
 			 preparedStmnt = connection.prepareStatement(finalQuery);
		       
		       resultSet = preparedStmnt.executeQuery();

		       while (resultSet.next()) {
		    	   result = new HashMap<>();
		    	   result.put("UID", resultSet.getString("USERID"));
		    	   result.put("Name", resultSet.getString("UNAME"));
		    	   result.put("YelpingSince", resultSet.getString("YELPINGSINCE"));
		    	   result.put("AvgStars", resultSet.getString("AVGSTARS"));
		    	   
		    	   resultList.add(result);
		       }
 			
 		}catch(Exception e){
 			
 		}
 		return resultList;
 	}
 	
 	public String getUserQuery(){
 		return userFinalQuery;
 	}
 	
 	public String getBusinessQuery(){
 		return businessFinalQuery;
 	}
	
	public String populateArrayList(ArrayList<String> categories){
		String condition = null;
		for(String category:categories){
			if(condition==null)
				condition = "'"+category+"'";
			else{
				condition = condition + "," + "'"+category+"'";
			}
	}
		return condition;

}
}
