/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package populate;
/**
 *
 * @author Rasika Telang
 */
import static org.json.simple.JSONObject.escape;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Populate {

    /**
     * @param args the command line arguments
     */
    public static final String businessFile = "D:\\SCU\\DB\\YelpDataset\\YelpDataset\\yelp_business.json";
    public static final String checkinFile = "D:\\SCU\\DB\\YelpDataset\\YelpDataset\\yelp_checkin.json";
    public static final String reviewFile = "D:\\SCU\\DB\\YelpDataset\\YelpDataset\\yelp_review.json";
    public static final String userFile = "D:\\SCU\\DB\\YelpDataset\\YelpDataset\\yelp_user.json";
    public static PreparedStatement preparedStmnt;
    public static Connection connect;
    public static JSONParser jsonParser;

    
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            
            jsonParser = new JSONParser();
            connect = connection();
            //yelpUserTable();
            //businessTable();
             System.out.println("Completed bus");
            //checkinTable();
             System.out.println("Completed checkin");
            reviewTable();
             System.out.println("Completed rev");
             System.out.println("Completed");
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
     public static void checkinTable() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(checkinFile))) {
            String eachline = null;
            while ((eachline = bufferedReader.readLine()) != null) {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(eachline);
                String checkinType=(String) jsonObject.get("type");
                String bId=(String) jsonObject.get("business_id");
                JSONObject checkinInfo = (JSONObject) jsonObject.get("checkin_info");
                Set keys = checkinInfo.keySet();
                Iterator a = keys.iterator();
                while (a.hasNext()) {
                    String date = (String) a.next();
                    Long times = (Long) checkinInfo.get(date);

                    String arrDay[] = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
                            "Saturday" };
                    String[] arrStr = date.split("-", 2);
                    int hour = Integer.parseInt(arrStr[0]);
                    String checkinDay = arrDay[Integer.parseInt(arrStr[1])];
                    String pss = "INSERT INTO Checkin(BId,CheckHour,CheckDay,CheckCount) VALUES (" + "'" + bId + "',"
                            + hour + "," + "'" + checkinDay + "'," + times + ") ";
                    preparedStmnt = connect.prepareStatement(pss);
                    preparedStmnt.execute();
                    preparedStmnt.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void reviewTable() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(reviewFile))) {
            String eachline = null;
            int count = 0;
            while ((eachline = bufferedReader.readLine()) != null) {

                JSONObject jsonObject = (JSONObject) jsonParser.parse(eachline);
                Long funnyVote = 0L;
                Long coolVote = 0L;
                Long usefulVote = 0L;
                JSONObject votes = (JSONObject) jsonObject.get("votes");
                Set keys = votes.keySet();
                Iterator a = keys.iterator();
                while (a.hasNext()) {
                    String votes_det = (String) a.next();
                    Long times = (Long) votes.get(votes_det);
                    if (votes_det.equals("funny")) {
                        funnyVote = times;
                    }
                    if (votes_det.equals("cool")) {
                        coolVote = times;
                    }
                    if (votes_det.equals("useful")) {
                        usefulVote = times;
                    }
                }

                String userId = (String) jsonObject.get("user_id");
                String reviewId = (String) jsonObject.get("review_id");
                Long stars = (Long) jsonObject.get("stars");
                String date = (String) jsonObject.get("date");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd"); 
                java.util.Date date1 = sdf1.parse(date); 
                 java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime());
                 System.out.println(sqlStartDate);
                String text = (String) jsonObject.get("text");
                text = text.replace("'", "''");
                String type = (String) jsonObject.get("type");
                String bId=(String)jsonObject.get("business_id");
                String sql = "INSERT INTO Review(FunnyVote,UsefulVote,CoolVote,UserId,ReviewId,Stars,ReviewDate,ReviewText,ReviewType,BId) VALUES (?,?,?,?,?,?,?,?,?,?)";
                preparedStmnt = connect.prepareStatement(sql);
                preparedStmnt.setLong(1, funnyVote);
                preparedStmnt.setLong(2, usefulVote);
                preparedStmnt.setLong(3, coolVote);
                preparedStmnt.setString(4, userId);
                preparedStmnt.setString(5, reviewId);
                preparedStmnt.setLong(6, stars);
                preparedStmnt.setDate(7,sqlStartDate);
                preparedStmnt.setString(8, escape((String) jsonObject.get("text")));
                preparedStmnt.setString(9, type);
                preparedStmnt.setString(10, bId);
                preparedStmnt.execute();
                preparedStmnt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public static void businessTable() {
        BufferedReader bufferedReader = null;
        JSONObject jsonObject = null;
        String bId = null;
        try {
            String eachline = null;
            int count = 0;
            bufferedReader = new BufferedReader(new FileReader(businessFile));
            while ((eachline = bufferedReader.readLine()) != null) {

                jsonObject = (JSONObject) jsonParser.parse(eachline);

                bId = (String) jsonObject.get("business_id");

                String fullAddress = (String) jsonObject.get("full_address");
                fullAddress = fullAddress.replace("'", "''");

                Boolean openOrNot = (Boolean) jsonObject.get("open");
                Double longi = (Double) jsonObject.get("longitude");
                String state = (String) jsonObject.get("state");
                Double stars = (Double) jsonObject.get("stars");
                Double lat = (Double) jsonObject.get("latitude");
                Long reviewCount = (Long) jsonObject.get("review_count");
                String name = (String) jsonObject.get("name");
                name = name.replace("'", "''");
                String city = (String) jsonObject.get("city");
                String type = (String) jsonObject.get("type");
                String attributes = String.valueOf(getAttributes(jsonObject));
                String neigh = String.valueOf(jsonObject.get("neighborhoods"));
                String pss = "INSERT INTO BUSINESS(BId,Address,IsOpen,City,ReviewCount,BName,Longitude,State,Stars,Latitude,BType,Attributes,neighborhoods) VALUES ("
                        + "'" + bId + "'," + "'" + fullAddress + "'," + "'" + openOrNot + "'," + "'" + city + "',"
                        + reviewCount + "," + "'" + name + "'," + longi + "," + "'" + state + "'," + stars + "," + lat
                        + "," + "'" + type + "','" +attributes+"','"+neigh+"'"+ ") ";

                preparedStmnt = connect.prepareStatement(pss);
                preparedStmnt.execute();
                preparedStmnt.close();

                JSONArray categories = (JSONArray) jsonObject.get("categories");
                Iterator<String> iterator = categories.iterator();
                String catString[];
                boolean x;
                catString = new String[] { "Active Life", "Arts & Entertainment", "Automotive", "Car Rental", "Cafes",
                        "Beauty & Spas", "Convenience Stores", "Dentists", "Doctors", "Drugstores", "Department Stores",
                        "Education", "Event Planning & Services", "Flowers & Gifts", "Food", "Health & Medical",
                        "Home Services", "Home & Garden", "Hospitals", "Hotels & Travel", "Hardware Stores", "Grocery",
                        "Medical Centers", "Nurseries & Gardening", "Nightlife", "Restaurants", "Shopping",
                        "Transportation" };
                try {
                    while (iterator.hasNext()) {
                        x = false;
                        String catname = iterator.next().replace("'", "''");
                        for (int item = 0; item < 28; item++) {
                            if (catString[item].equals(catname)) {
                                x = true;
                                break;
                            }
                        }

                        if (x == false) {
                            String cat = "INSERT INTO BUSINESSSUBCATEGORY(SubCatName,BId) VALUES (" + "'" + catname
                                    + "'," + "'" + bId + "'" + ") ";
                            preparedStmnt = connect.prepareStatement(cat);
                            preparedStmnt.execute();
                            preparedStmnt.close();
                        } else {
                            String cat = "INSERT INTO BUSINESSCATEGORY(CatName,BId) VALUES (" + "'" + catname + "',"
                                    + "'" + bId + "'" + ") ";
                            preparedStmnt = connect.prepareStatement(cat);
                            preparedStmnt.execute();
                            preparedStmnt.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
 public static void friendsTable(Connection conn2, JSONObject jsonObject, String userId) {
        JSONArray friends = (JSONArray) jsonObject.get("friends");
        Iterator<String> fri = friends.iterator();
        while (fri.hasNext()) {
            String sql2 = "INSERT INTO Friends (UserId, FriendId) VALUES (" + "'" + userId + "'," + "'" + fri.next()
                    + "'" + ") ";
            try {
                preparedStmnt = connect.prepareStatement(sql2);
                preparedStmnt.execute();
                preparedStmnt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

public static void yelpUserTable() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(userFile))) {
            String eachline = null;
            int count = 0;
            while ((eachline = bufferedReader.readLine()) != null) {

                JSONObject jsonObject = (JSONObject) jsonParser.parse(eachline);

                JSONObject votes = (JSONObject) jsonObject.get("votes");
                Set keys = votes.keySet();
                Iterator a = keys.iterator();
                Long coolVotes = 0L;
                Long funnyVotes = 0L;
                Long usefulVotes = 0L;
                Long reviewCount = 0L;
                Long fans = 0L;
                Double averageStars = 0.0;
                while (a.hasNext()) {
                    String votes_det = (String) a.next();
                    Long times = (Long) votes.get(votes_det);
                    if (votes_det.equals("funny")) {
                        funnyVotes = times;
                    }
                    if (votes_det.equals("cool")) {
                        coolVotes = times;
                    }
                    if (votes_det.equals("useful")) {
                        usefulVotes = times;
                    }
                }

                String yelpingSince = (String) jsonObject.get("yelping_since");
                //SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm"); 
                //java.util.Date date1 = sdf1.parse(yelpingSince); 
                 //java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime());
                 //System.out.println(sqlStartDate);
                String name = (String) jsonObject.get("name");
                name = name.replace("'", "''");
                reviewCount = (Long) jsonObject.get("review_count");
                String userId = (String) jsonObject.get("user_id");
                String type = (String) jsonObject.get("type");
                fans = (Long) jsonObject.get("fans");
                averageStars = (Double) jsonObject.get("average_stars");
                String compliments = String.valueOf(jsonObject.get("compliments"));


                String sql1 = "INSERT INTO yelpuser (YELPINGSINCE, FUNNYVOTES, USEFULVOTES, COOLVOTES, REVIEWCOUNT, UNAME, USERID, FANS, AVGSTARS, UTYPE,COMPLIMENTS) VALUES ("
                        + "to_date('" + yelpingSince + "','yyyy-mm')" + "," + funnyVotes + "," + usefulVotes + "," + coolVotes + ","
                        + reviewCount + "," + "'" + name + "'," + "'" + userId + "'," + "'" + fans + "'," + averageStars
                        + "," + "'" + type + "','" +compliments+"'"+ ") ";
                preparedStmnt = connect.prepareStatement(sql1);
                 //Statement statement = connect.createStatement();

                // statement.addBatch(sql1);
                preparedStmnt.execute();

                preparedStmnt.close();

                friendsTable(connect, jsonObject, userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public static void attributeTable(){
	
}

public static ArrayList<String> getAttributes(JSONObject json){
	JSONObject objAttributes = (JSONObject) json.get("attributes");
	String strKey = null;
	ArrayList<String> attributeList = new ArrayList<>();
	for (Object objKey : objAttributes.keySet()) 
	{
		 strKey = (String)objKey;
        Object objKeyValue = objAttributes.get(strKey);				      

        if (objKeyValue instanceof JSONObject)
        {
        	JSONObject objAttributes_1 = (JSONObject) objAttributes.get(objKey);
        	for (Object objKey_1 : objAttributes_1.keySet())
        	{
        		String strKey_1 = (String)objKey_1;
        		Object objKeyValue_1 = objAttributes_1.get(strKey_1);
        		if (objKeyValue_1 instanceof Integer)
        		{
        			String strAttributeValue = ((Long) objAttributes_1.get(strKey_1)).toString();
        			strKey_1 = strKey_1 + "_" +strKey+"_"+ strAttributeValue;
					attributeList.add(strKey_1);
        		}
        		else if (objKeyValue_1 instanceof String)
        		{
        			String strAttributeValue = (String) objAttributes_1.get(strKey_1);
        			strKey_1 = strKey_1 + "_" +strKey+"_"+ strAttributeValue;
					attributeList.add(strKey_1);

        		}
        		else if (objKeyValue_1 instanceof Boolean)
        		{
        			boolean a = (Boolean) objAttributes_1.get(strKey_1);
        			String strAttributeValue = String.valueOf(a);
        			strKey_1 = strKey_1 + "_" +strKey+"_"+ strAttributeValue;
					attributeList.add(strKey_1);

        		}
        	}
        }
        else
        {
        	if (objKeyValue instanceof Integer)
    		{
    			String strAttributeValue = ((Long) objAttributes.get(strKey)).toString();
    			strKey = strKey + "_" + strAttributeValue;
				attributeList.add(strKey);

    		}
    		else if (objKeyValue instanceof String)
    		{
    			String strAttributeValue = (String) objAttributes.get(strKey);
    			strKey = strKey + "_" + strAttributeValue;
				attributeList.add(strKey);

    		}
    		else if (objKeyValue instanceof Boolean)
    		{
    			boolean a = (Boolean) objAttributes.get(strKey);
    			String strAttributeValue = String.valueOf(a);
    			strKey = strKey + "_" + strAttributeValue;
				attributeList.add(strKey);

    		} 
        }
    }
    return attributeList;

}







public static Connection connection(){

    Connection connect=null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
             connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:rasikadbor", "system", "rasikarst");
    
        } catch (Exception e) {
            e.printStackTrace();
        }
                  
        return connect;

}    







}
