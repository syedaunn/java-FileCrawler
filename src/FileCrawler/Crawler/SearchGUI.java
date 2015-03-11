package FileCrawler.Crawler;


import java.awt.*;
import java.awt.event.*;
import java.lang.Thread.State;

import javax.swing.*; 

public class SearchGUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 483464643080007412L;	/*Auto Generated*/


	JPanel mainPanel = new JPanel();
	JPanel indexLocations = new JPanel();
	JPanel searchFields = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel report = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel searchTable = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JButton searchButton = new JButton("Search");
	
	JLabel notifier = new JLabel("Intializing ...");
	JTextField searchBar = new JTextField(40);
	JTable results;
	//localSpider crawlers[] ={new localSpider("C:\\"),new localSpider("E:\\"),new localSpider("F:\\")};
	localSpider crawlers[] ={new localSpider("E:\\7Diagnols"),new localSpider("E:\\Breaking Bad"),new localSpider("E:\\Jobs")};
	JCheckBox driveLetters[] = {new JCheckBox("C:",true),new JCheckBox("E:",true),new JCheckBox("F:",true)};

	public SearchGUI() throws InterruptedException {
		super("Search Local Spider"); 
		//setBounds(100,100,300,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();

		con.setLayout(new BoxLayout(con	,BoxLayout.Y_AXIS));
		con.add(indexLocations);
		con.add(searchFields); 
		con.add(report);
		con.add(searchTable);
		indexLocations.add(new JLabel("Search Locations:\t"));
		for (JCheckBox box : driveLetters) {
			indexLocations.add(box);
			box.addActionListener(this);
		}

		//con.add(mainPanel);
		searchButton.addActionListener(this);
		searchButton.setMnemonic(java.awt.event.KeyEvent.VK_ENTER); 
		searchFields.add(searchBar);
		searchFields.add(searchButton);
		report.add(notifier);
		searchBar.requestFocus();




		pack();
		con.getBounds().getSize();
		setVisible(true); // make frame visible
		long c=0;
		while(c != 10000000000L)
			c++;
		notifier.setText("Indexing Locations...");

		for (localSpider crawler : crawlers) {
			crawler.setDaemon(true);
			crawler.run();
		}
		while(crawlers[0].getState()== State.RUNNABLE || crawlers[1].getState()== State.RUNNABLE ||crawlers[2].getState()== State.RUNNABLE );
		notifier.setText("Indexed. Search now!");


	}


	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		searchTable.removeAll();
		if (source == searchButton)
		{
			notifier.setText("Searching ...");
			//JOptionPane.showMessageDialog(null,"I hear you!","Message Dialog",JOptionPane.PLAIN_MESSAGE); setVisible(true);  // show something
			String look = searchBar.getText();
			String[] result = localSpider.search(look.toLowerCase());
			if(result.length==0){
				notifier.setText("No Results found!");
				return;
			}
			String boy="";
			for(int k=0; k< result.length;k++){
				boy=boy.concat(result[k]+"\n");
			}
			
		
			notifier.setText(result.length + " results found");
			String[] colNames = {"Results"};
			String[][] rowData = new String[result.length][];
			for(int c=0;c < result.length;c++){
				rowData[c]= new String[1];
				rowData[c][0]=result[c];
				
			}
			JTable tableResults = new JTable(rowData,colNames );
			
			//this.getContentPane()./
			JScrollPane scrollPane = new JScrollPane(tableResults);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			//tableResults.setSize(300,150);
			//tableResults.set
			searchTable.add(scrollPane,BorderLayout.CENTER);
		//	searchTable.setSize(30,150);
			pack();
			
		}

		

		for (JCheckBox box : driveLetters) {
			if(source == box){
				if(box.getText().equalsIgnoreCase("C:")){

				}
				if(box.getText().equalsIgnoreCase("E:")){

				}
				if(box.getText().equalsIgnoreCase("F:")){

				}


				break;
			}
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new SearchGUI();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
