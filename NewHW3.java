package swingFrame;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class NewHW3 extends JPanel implements ItemListener{
    int count1 = 0;
    int count2 = 0;
	 private JFrame frame;
	 ArrayList<String> keys = new ArrayList<>();
	 ArrayList<String> subKeys = new ArrayList<>();
	 ArrayList<String> attributeKeys = new ArrayList<>();
	 File file = null;
	 JPanel subCatScrollPanel = null;
	 JPanel attributeScrollPanel = null;
	 JTextField checkinTextField;
	 DefaultTableModel tableToDisplay;
	 DefaultTableModel userTableValues;
	 JTable UserReviewTable;
	 DefaultTableModel table1;
	 public HashMap<Integer, String> bisunessContainer = new HashMap();
	 public HashMap<Integer, String> userContainer = new HashMap();
	 JTable mainTableBusiness;
	 JTable tableBusinessDisplay;
	 private JTextField votesTextField;
	 private JTextField reviewTexts;
	 private JTextField businessQuery;
	 private JTextField ratingValues;
	 private JTextField starTexts;
	 private JTextField friendTexts;
	 private JTextField votesTexts;
	 private JTextField userQuery;
	 private JComboBox businessAndBox; 
	 JButton buttonForSubmitting = null;
	 Queries queries = new Queries();
	  

	
	 public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	       try {
	          NewHW3 window = new NewHW3();
	          window.frame.setVisible(true);
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    });
	  }
	 
	  /**
	   * Create the application.
	   */
	  public NewHW3() {
	    initialize();
	  }
	 
	  /**
	   * Initialize the contents of the frame.
	   */
	  private void initialize() {
		  
	        JTabbedPane MainPan = new JTabbedPane();

	    ArrayList<String> categoryList = queries.getCategories();     
	    frame = new JFrame();
	    frame.setBounds(100, 100, 1024, 768);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel_body = new JPanel();
        panel_body.setBackground(new Color(100,100,100));
        panel_body.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel_body.setBounds(712, 16, 643, 343);
        
        

        frame.getContentPane().add(MainPan);
        MainPan.add(panel_body);
        panel_body.setLayout(null);
        
        JLabel businessLabel = new JLabel("Business");
        businessLabel.setBounds(0, 0, 700, 30);
        
        panel_body.add(businessLabel);
        
        TitledBorder border = new TitledBorder("Category");
	    border.setTitleJustification(TitledBorder.CENTER);
	    border.setTitlePosition(TitledBorder.TOP);  
	    
	    TitledBorder border2 = new TitledBorder("Sub Category");
	    border2.setTitleJustification(TitledBorder.CENTER);
	    border2.setTitlePosition(TitledBorder.TOP);
	   
	    TitledBorder border3 = new TitledBorder("Attributes");
	    border3.setTitleJustification(TitledBorder.CENTER);
	    border3.setTitlePosition(TitledBorder.TOP);
	    
	    businessAndBox = new JComboBox();
	    businessAndBox.setModel(new DefaultComboBoxModel(new String[] { "AND", "OR" }));
	    businessAndBox.setBounds(115, 500, 108, 27);
	      panel_body.add(businessAndBox);
        
	    JScrollPane catScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    catScroll.setBounds(10, 30, 200, 400);
        panel_body.add(catScroll);
	    
        
	    JPanel catScrollPanel = new JPanel();
	    catScrollPanel.setLayout(new BoxLayout(catScrollPanel, BoxLayout.Y_AXIS));
	    catScrollPanel.setBorder(border);
        
	    catScroll.setViewportView(catScrollPanel);
        
        JScrollPane subCatScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        subCatScroll.setBounds(200, 30, 200, 400);
        panel_body.add(subCatScroll);
	    
	   
        subCatScrollPanel = new JPanel();
        subCatScrollPanel.setLayout(new BoxLayout(subCatScrollPanel, BoxLayout.Y_AXIS));
        subCatScrollPanel.setBorder(border2);
        
        subCatScroll.setViewportView(subCatScrollPanel);
        
        JScrollPane attributeScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        attributeScroll.setBounds(400, 30, 300, 400);
        panel_body.add(attributeScroll);
	    
	   
        attributeScrollPanel = new JPanel();
        attributeScrollPanel.setLayout(new BoxLayout(attributeScrollPanel, BoxLayout.Y_AXIS));
        attributeScrollPanel.setBorder(border3);
        
        attributeScroll.setViewportView(attributeScrollPanel);
        
        
        int h = 20;
	    JCheckBox[] boxArray = new JCheckBox[categoryList.size()];
	    for(int i=0;i<categoryList.size();i++){
	    	
	    	boxArray[i] = new JCheckBox(categoryList.get(i));
	    	boxArray[i].addItemListener(this);
	    	boxArray[i].setBounds(10, h, 150, 20);
	    	catScrollPanel.add(boxArray[i]);
            h += 20;
	    }
      
        JPanel panForReview = new JPanel();
        panForReview.setLayout(null);
        panForReview.setBorder(new LineBorder(new Color(0, 0, 0)));
        panForReview.setBounds(700, 30, 171, 400);
        panel_body.add(panForReview);

        JLabel label = new JLabel("From");
        label.setBounds(10, 11, 45, 13);
        panForReview.add(label);
        JComboBox rBoxSel = new JComboBox();
        rBoxSel.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
        rBoxSel.setBounds(10, 148, 84, 21);
        panForReview.add(rBoxSel);
        JPanel userPanel = new JPanel();
        userPanel.setBackground(new Color(245, 222, 179));
        userPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        userPanel.setForeground(Color.BLACK);
        MainPan.addTab("User", null, userPanel, null);
        MainPan.addTab("Business", null, panel_body, null);

        JDateChooser fromDate = new JDateChooser();
        fromDate.setDateFormatString("dd-MM-yy");
        fromDate.setBounds(10, 25, 119, 27);
        panForReview.add(fromDate);



        JLabel label_1 = new JLabel("To");
        label_1.setBounds(10, 62, 45, 13);
        panForReview.add(label_1);
        // Adding the date picker starts
        
        
        JLabel lblStarRating = new JLabel("Star Rating");
        lblStarRating.setBounds(10, 128, 84, 13);
        panForReview.add(lblStarRating);

        


        ratingValues = new JTextField();
        ratingValues.setColumns(10);
        ratingValues.setBounds(10, 191, 96, 19);
        panForReview.add(ratingValues);

        JDateChooser endDate = new JDateChooser();
        endDate.setDateFormatString("dd-MM-yy");
        endDate.setBounds(10, 85, 119, 27);
        panForReview.add(endDate);

        JComboBox comboBox_10 = new JComboBox();
        comboBox_10.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
        comboBox_10.setBounds(10, 248, 84, 21);
        panForReview.add(comboBox_10);

        votesTextField = new JTextField();
        votesTextField.setColumns(10);
        votesTextField.setBounds(10, 294, 96, 19);
        panForReview.add(votesTextField);
        

        JLabel lblVotes = new JLabel("Votes");
        lblVotes.setBounds(10, 225, 45, 13);
        panForReview.add(lblVotes);

        JLabel lblValues = new JLabel("Values");
        lblValues.setBounds(10, 279, 45, 13);
        panForReview.add(lblValues);
        
        businessQuery = new JTextField();
        businessQuery.setBounds(84, 578, 459, 78);
        panel_body.add(businessQuery);
        businessQuery.setColumns(10);

        buttonForSubmitting = new JButton("Submit");
        buttonForSubmitting.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
				String startDate = null;
				String lastDate = null;
				int starCount = 0;
				int voteCount = 0;
				
				if(fromDate.getDate()!=null)
					startDate = formatter.format(fromDate.getDate());
				
				if(endDate.getDate()!=null)
					lastDate = formatter.format(endDate.getDate());
				
				int starOperatorIndex = rBoxSel.getSelectedIndex();
				String starOperator = "";
				if(starOperatorIndex==0)
					starOperator = ">";
				else if(starOperatorIndex==1)
					starOperator = "<";
				else if(starOperatorIndex==2)
					starOperator = "=";
				if(!ratingValues.getText().isEmpty() && ratingValues.getText()!=null )
					starCount = Integer.parseInt(ratingValues.getText());
				int votesOperatorIndex = comboBox_10.getSelectedIndex();
				String votesOperator = "";
				if(starOperatorIndex==0)
					votesOperator = ">";
				else if(starOperatorIndex==1)
					votesOperator = "<";
				else if(starOperatorIndex==2)
					votesOperator = "=";			
				if(!votesTextField.getText().isEmpty() && votesTextField.getText()!=null)
					voteCount = Integer.parseInt(votesTextField.getText());
				
				ArrayList<Object> businesses = queries.businessTable(keys,subKeys,attributeKeys,startDate,lastDate,starOperator,starCount,votesOperator,voteCount,businessAndBox.getSelectedIndex());
				String[] rowObj = new String[4];
                businessQuery.setText("");
                businessQuery.setText(businessQuery.getText()+queries.getBusinessQuery());
				int i = 0;
                int k = tableToDisplay.getRowCount();
                if (mainTableBusiness.getRowCount() > 0) {
                    for (int j = mainTableBusiness.getRowCount() - 1; j > -1; j--) {
                        tableToDisplay.removeRow(j);
                    }
                }
                System.out.println(tableToDisplay.getRowCount());
				for(Object business:businesses){
					HashMap<String,Object> businessMap = (HashMap<String, Object>) business;
					rowObj = new String[]{businessMap.get("Business").toString(),businessMap.get("City").toString(),businessMap.get("State").toString(),String.valueOf(businessMap.get("Stars"))};
					bisunessContainer.put(i++, businessMap.get("BusinessID").toString());
                    tableToDisplay.addRow(rowObj);
				}
				openReviewFrame(count1);
                count1++;
			}
		});
        buttonForSubmitting.setBounds(400, 500, 115, 41);
        panel_body.add(buttonForSubmitting);
        
        tableToDisplay = new DefaultTableModel();
        tableToDisplay.addColumn("Business Name");
        tableToDisplay.addColumn("City");
        tableToDisplay.addColumn("State");
        tableToDisplay.addColumn("Stars");
        JPanel panelBusinessTables = new JPanel();
        panelBusinessTables.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
        panel_body.add(panelBusinessTables);
        panelBusinessTables.setVisible(true);
        panelBusinessTables.setBounds(900, 30, 430, 600);
        userPanel.setLayout(null);
        panelBusinessTables.setLayout(null);

        JScrollPane panelForBusiness_scrollable = new JScrollPane();
        panelForBusiness_scrollable.setBounds(10, 10, 410, 580);
        panelBusinessTables.add(panelForBusiness_scrollable);
        
        mainTableBusiness = new JTable();
        panelForBusiness_scrollable.setViewportView(mainTableBusiness);
        mainTableBusiness.setModel(tableToDisplay);
        mainTableBusiness.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        JScrollPane scrollPane_5 = new JScrollPane();
        scrollPane_5.setBounds(10, 10, 410, 580);
        tableBusinessDisplay = new JTable();
        scrollPane_5.setViewportView(tableBusinessDisplay);
        tableBusinessDisplay.setBorder(new LineBorder(new Color(0, 0, 0)));
        
      JPanel userDetailPanel = new JPanel();
      userDetailPanel.setBackground(Color.GRAY);
      userDetailPanel.setBounds(84, 100, 433, 400);
      userPanel.add(userDetailPanel);
      userDetailPanel.setLayout(null);
      
      JDateChooser Member_Since = new JDateChooser();
      Member_Since.setDateFormatString("dd-MM-yy");
      Member_Since.setBounds(124, 32, 129, 27);
      userDetailPanel.add(Member_Since);
      
      JComboBox reviewBox = new JComboBox();
      reviewBox.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
      reviewBox.setBounds(115, 90, 108, 27);
      userDetailPanel.add(reviewBox);

      JComboBox friendDown = new JComboBox();
      friendDown.setBackground(Color.LIGHT_GRAY);
      friendDown.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
      friendDown.setBounds(115, 138, 108, 27);
      userDetailPanel.add(friendDown);

      JComboBox starBox = new JComboBox();
      starBox.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
      starBox.setBounds(115, 186, 108, 32);
      userDetailPanel.add(starBox);
      
      JComboBox votesBox = new JComboBox();
      votesBox.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
      votesBox.setBounds(115, 236, 108, 32);
      userDetailPanel.add(votesBox);

      JComboBox andBox = new JComboBox();
      andBox.setModel(new DefaultComboBoxModel(new String[] { "AND", "OR" }));
      andBox.setBounds(115, 350, 108, 27);
      userDetailPanel.add(andBox);
      JPanel userPanel_2 = new JPanel();
      userPanel_2.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
      userPanel.add(userPanel_2);
      userPanel_2.setVisible(true);
      userPanel_2.setBounds(650, 21, 430, 600);
      userPanel_2.setLayout(null);
      userPanel_2.add(scrollPane_5);
      
      userTableValues = new DefaultTableModel();
      userTableValues.addColumn("Name");
      userTableValues.addColumn("Yelping Date");
      userTableValues.addColumn("Stars");
      tableBusinessDisplay = new JTable();
      scrollPane_5.setViewportView(tableBusinessDisplay);
      tableBusinessDisplay.setModel(userTableValues);
      tableBusinessDisplay.setBorder(new LineBorder(new Color(0, 0, 0)));
      
      reviewTexts = new JTextField(0);
      reviewTexts.setBounds(291, 91, 108, 20);
      userDetailPanel.add(reviewTexts);
      reviewTexts.setColumns(10);
      friendTexts = new JTextField();
      friendTexts.setColumns(10);
      friendTexts.setBounds(291, 139, 108, 20);
      userDetailPanel.add(friendTexts);
      starTexts = new JTextField();
      starTexts.setColumns(10);
      starTexts.setBounds(291, 190, 108, 20);
      userDetailPanel.add(starTexts);
      votesTexts = new JTextField();
      votesTexts.setColumns(10);
      votesTexts.setBounds(291, 244, 108, 20);
      userDetailPanel.add(votesTexts);
      JLabel label_3 = new JLabel("Value");
      label_3.setBounds(257, 142, 45, 13);
      userDetailPanel.add(label_3);
      JLabel lblNewLabel_1 = new JLabel("Value");
      lblNewLabel_1.setBounds(257, 94, 45, 13);
      userDetailPanel.add(lblNewLabel_1);
      JLabel lblNewLabel_2 = new JLabel("Member Since");
      lblNewLabel_2.setBounds(10, 32, 95, 27);
      userDetailPanel.add(lblNewLabel_2);
      JLabel label_4 = new JLabel("Value");
      label_4.setBounds(257, 193, 45, 13);
      userDetailPanel.add(label_4);
      JLabel lblNoOfFriends = new JLabel("Friends");
      lblNoOfFriends.setBounds(10, 142, 95, 13);
      userDetailPanel.add(lblNoOfFriends);
      JLabel lblReviewCount = new JLabel("Reviews");
      lblReviewCount.setBounds(10, 94, 95, 13);
      userDetailPanel.add(lblReviewCount);
      JLabel lblAvgStar = new JLabel("Average Stars");
      lblAvgStar.setBounds(10, 193, 75, 13);
      userDetailPanel.add(lblAvgStar);
      JLabel lblvotes = new JLabel("Number of Votes");
      lblvotes.setBounds(10, 244, 75, 13);
      userDetailPanel.add(lblvotes);
      JLabel label_5 = new JLabel("Value");
      label_5.setBounds(257, 244, 45, 13);
      userDetailPanel.add(label_5);
      JButton userMainButton = new JButton("Execute Query");
      userMainButton.setBounds(84, 520, 278, 39);
      userPanel.add(userMainButton);
      JLabel selectedLabel = new JLabel("Select");
      selectedLabel.setBounds(10, 360, 45, 13);
      userDetailPanel.add(selectedLabel);
      userQuery = new JTextField();
      userQuery.setBounds(84, 578, 459, 78);
      userPanel.add(userQuery);
      userQuery.setColumns(10);
      userMainButton.addActionListener(new ActionListener() {
		
		
		public void actionPerformed(ActionEvent e) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
			String memberSince = null;
			String reviewOperator = null;
			int reviews = 0;
			String friendsOperator = null;
			int friends = 0;
			String avgStarsOperator = null;
			float avgStars = 0;
			String numberOfVotesOperator = null;
			int numberOfVotes = 0;
			
			try{
				if(Member_Since.getDate()!=null)
					{memberSince = formatter.format(Member_Since.getDate());
					System.out.println(memberSince);
					}
				if(reviewTexts.getText()!=null && !reviewTexts.getText().isEmpty())
					
					reviews = Integer.parseInt(reviewTexts.getText());
				
				if(friendTexts.getText()!=null && !friendTexts.getText().isEmpty())
					friends = Integer.parseInt(friendTexts.getText());
				
				if(starTexts.getText()!=null && !starTexts.getText().isEmpty())
					avgStars = Float.parseFloat(starTexts.getText());
				
				if(votesTexts.getText()!=null && !votesTexts.getText().isEmpty()){
					numberOfVotes = Integer.parseInt(votesTexts.getText());
				}
				
				int reviewBoxIndex = reviewBox.getSelectedIndex();
				if(reviewBoxIndex==0)
					reviewOperator = ">";
				else if(reviewBoxIndex==1)
					reviewOperator = "<";
				else if(reviewBoxIndex==2)
					reviewOperator = "=";
				
				int friendBoxIndex = friendDown.getSelectedIndex();
				if(friendBoxIndex==0)
					friendsOperator = ">";
				else if(friendBoxIndex==1)
					friendsOperator = "<";
				else if(friendBoxIndex==2)
					friendsOperator = "=";
				
				int starBoxIndex = starBox.getSelectedIndex();
				if(starBoxIndex==0)
					avgStarsOperator = ">";
				else if(starBoxIndex==1)
					avgStarsOperator = "<";
				else if(starBoxIndex==2)
					avgStarsOperator = "=";
				
				int votesBoxIndex = votesBox.getSelectedIndex();
				if(votesBoxIndex==0)
					numberOfVotesOperator = ">";
				else if(votesBoxIndex==1)
					numberOfVotesOperator = "<";
				else if(votesBoxIndex==2)
					numberOfVotesOperator = "=";
				
				int andBoxIndex = andBox.getSelectedIndex();
				String mainOperator  ="";
				if(andBoxIndex==0)
					mainOperator  = "AND";
				else if(andBoxIndex ==1)
					mainOperator = "OR";
				
				if (tableBusinessDisplay.getRowCount() > 0) {
                    for (int i = tableBusinessDisplay.getRowCount() - 1; i > -1; i--) {
                        userTableValues.removeRow(i);
                    }
                }
				
				ArrayList<Object> users = queries.usersTable(memberSince,reviewOperator,reviews,friendsOperator,friends,avgStarsOperator,avgStars,numberOfVotesOperator,numberOfVotes,mainOperator);
				userQuery.setText("");
				String abc = queries.getUserQuery();
                userQuery.setText(userQuery.getText()+queries.getUserQuery());
				String[] rowObj = new String[4];
                int i = 0;

				for(Object user:users){
					HashMap<String,Object> userMap = (HashMap<String, Object>) user;
					rowObj = new String[]{userMap.get("Name").toString(),userMap.get("YelpingSince").toString(),userMap.get("AvgStars").toString()};
					userContainer.put(i++, userMap.get("UID").toString());
					userTableValues.addRow(rowObj);
				}
				openUserReviewFrame(count2);
                count2++;
                
                
                userPanel.add(userQuery);
                
				
			}catch(Exception ex){
				
			}
		}
	});
      
  
	  }
	  
	  public void itemStateChanged(ItemEvent e) {
	        subCatScrollPanel.removeAll();

	        JCheckBox source = (JCheckBox) e.getItemSelectable();
	        if (e.getStateChange() == ItemEvent.DESELECTED) {
	            keys.remove(source.getText());
	            subCatScrollPanel.removeAll();
	            subCatScrollPanel.revalidate();
	            subCatScrollPanel.repaint();
	            attributeScrollPanel.removeAll();
	            attributeScrollPanel.revalidate();
	            attributeScrollPanel.repaint();
	            subKeys.clear();
	            attributeKeys.clear();
	        }else{ 
	        	keys.add(source.getText());
	      
	        ArrayList<String> subCategoryList = queries.getSubCategories(keys);
	       
	        	int h = 20;
	        	JCheckBox[] boxArray = new JCheckBox[subCategoryList.size()];
	        	for(int i=0;i<subCategoryList.size();i++){
	    	    	
	    	    	boxArray[i] = new JCheckBox(subCategoryList.get(i));
	    	    	boxArray[i].addItemListener((ItemListener) new ItemListener() {
	                    public void itemStateChanged(ItemEvent e) {

	                        if (e.getStateChange() == ItemEvent.SELECTED) {
	                        	attributeScrollPanel.removeAll();
	                	        JCheckBox source = (JCheckBox) e.getItemSelectable();
	                	        subKeys.add(source.getText().trim());
	                	        ArrayList<String> attributesList = queries.getAttributes(keys,subKeys,businessAndBox.getSelectedIndex());
	                	        if(attributesList.isEmpty()){
	                	        	attributeKeys.clear();
		                        	attributeScrollPanel.removeAll(); 
		                        	attributeScrollPanel.revalidate();
		            	             attributeScrollPanel.repaint();
	                	        }
	                	        int h = 20;
	                	        JCheckBox[] boxArray = new JCheckBox[subCategoryList.size()];
	            	        	for(int i=0;i<attributesList.size();i++){
	            	        		boxArray[i] = new JCheckBox(attributesList.get(i));
	            	        		boxArray[i].addItemListener((ItemListener) new ItemListener() {
	            	                    public void itemStateChanged(ItemEvent e) {
            	                	        JCheckBox selectedAttribute = (JCheckBox) e.getItemSelectable();

	            	                        if (e.getStateChange() == ItemEvent.SELECTED) {
	            	                        	attributeKeys.add(selectedAttribute.getText());
	            	                        }else if(e.getStateChange() == ItemEvent.DESELECTED){
	            	                        	attributeKeys.remove(selectedAttribute.getText());
	            	                        }
	            	                    }    
	            	    	    	
	            	        		});
	            	    	    	boxArray[i].setBounds(10, h, 150, 20);
	            	    	    	attributeScrollPanel.add(boxArray[i]);
	            	                h += 20;
	            	                attributeScrollPanel.revalidate();
	            	                attributeScrollPanel.repaint();
	            	    	    }
	                        } else if(e.getStateChange() == ItemEvent.DESELECTED){
	                        	JCheckBox deselect = (JCheckBox) e.getItemSelectable();
	                        	subKeys.remove(deselect.getText().trim());
	                        	attributeKeys.clear();
	                        	attributeScrollPanel.removeAll(); 
	                        	attributeScrollPanel.revalidate();
	            	             attributeScrollPanel.repaint();
	                        }
	                    }
	                });
	    	    	boxArray[i].setBounds(10, h, 150, 20);
	    	    	subCatScrollPanel.add(boxArray[i]);
	                h += 20;
	    	    }
	       
	        	
	      
	       subCatScrollPanel.revalidate();
	       subCatScrollPanel.repaint();
	    }
	  }
	  
	  private void openReviewFrame(int n) {

	        mainTableBusiness.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                ResultSet resultSet;
	                Connection connection = null;
	                try {

	                    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	                    connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:rasikadbor", "system",
	                            "rasikarst");
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	                if (e.getClickCount() == 1 && n == count1 - 1) {
	                    JFrame frame = new JFrame("Business Review");
	                    JPanel mainWindow = new JPanel();
	                    mainWindow.setLayout(new FlowLayout());
	                    JTable target = (JTable) e.getSource();
	                    int row = target.getSelectedRow();

	                    JTable bTable = new JTable();
	                    bTable.setBounds(952, 75, 938, 691);

	                    DefaultTableModel tableR = new DefaultTableModel();
	                    bTable.setModel(tableR);
	                    tableR.addColumn("Business Id");
	                    tableR.addColumn("Text");
	                    tableR.addColumn("Stars");
	                    JScrollPane reviewResultPane = new JScrollPane(bTable);
	                    mainWindow.add(reviewResultPane);

	                    String[] rowDetail = new String[3];
	                    try {
	                        CallableStatement call = connection
	                                .prepareCall("SELECT bid,reviewtext,stars FROM review where BId=?");
	                        call.setString(1, bisunessContainer.get(row));
	                        resultSet = call.executeQuery();
	                        while (resultSet.next()) {
	                            rowDetail = new String[] { resultSet.getString("bid"), resultSet.getString("reviewtext"),
	                                    resultSet.getString("STARS") };

	                            tableR.addRow(rowDetail);
	                        }
	                    } catch (SQLException e1) {
	                        e1.printStackTrace();
	                    }
	                    frame.getContentPane().add(mainWindow);
	                    frame.setSize(1000, 1000);
	                    frame.setLocationRelativeTo(null);
	                    frame.setVisible(true);
	                }
	            }
	        });

	    }
	  
	  private void openUserReviewFrame(int n) {

	        tableBusinessDisplay.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                ResultSet resultSet;
	                Connection connection = null;
	                try {

	                    // Establish a connection
	                    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	                    connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:rasikadbor", "system",
	                            "rasikarst");
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	                if (e.getClickCount() == 1 && n == count2 - 1) {
	                    JFrame mainWindowDisplay = new JFrame("Review");
	                    JPanel subPanel = new JPanel();
	                    subPanel.setLayout(new FlowLayout());
	                    JTable tableVersion = (JTable) e.getSource();
	                    int row = tableVersion.getSelectedRow();

	                    UserReviewTable = new JTable();
	                    UserReviewTable.setBounds(952, 75, 938, 691);

	                    table1 = new DefaultTableModel();
	                    UserReviewTable.setModel(table1);
	                    table1.addColumn("Username");
	                    table1.addColumn("Text");
	                    table1.addColumn("Star");
	                    JScrollPane reviewResultPane = new JScrollPane(UserReviewTable);
	                    subPanel.add(reviewResultPane);

	                    String[] rowObj = new String[3];
	                    try {
	                        CallableStatement call = connection
	                                .prepareCall("select USERID,REVIEWTEXT,STARS from REVIEW where USERID=?");
	                        call.setString(1, userContainer.get(row));
	                        resultSet = call.executeQuery();
	                        while (resultSet.next()) {
	                            rowObj = new String[] { resultSet.getString("USERID"), resultSet.getString("REVIEWTEXT"),
	                                    resultSet.getString("STARS") };
	                            table1.addRow(rowObj);
	                        }
	                    } catch (SQLException e1) {
	                        e1.printStackTrace();
	                    }
	                    mainWindowDisplay.getContentPane().add(subPanel);
	                    mainWindowDisplay.setSize(987, 1020);
	                    mainWindowDisplay.setLocationRelativeTo(null);
	                    mainWindowDisplay.setVisible(true);
	                }
	            }
	        });

	    }
	  
}
