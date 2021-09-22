import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


public class Main {
    // main method for the project
	public static Cities msource;
	public static Cities mdestination;
	public static int sd;
	public static int sfm;
	//this is source from map and have value 0 if the source is from map and value of one if the source is from search
	public static int dfm;
    public static void main(String[] args){

    	sfm = 0;
    	dfm = 0;
    	sd = 0;
    	msource = null;
    	mdestination = null;
        Cities[] cities = new Cities[170]; //array of cities (Vertices) max = 170
        for (int i = 0; i < cities.length; i++) {
            cities[i] = new Cities();
        }

        Edges[] links = new Edges[1750];// array of links  (Edges)  max = 1750
        for (int i = 0; i < links.length; i++) {
            links[i] = new Edges();
        }

        int countOfCities; //number of cities
        int countOfLinks; //number of links


        // load cities from the cvs file
        countOfCities = loadCities(cities);

        // load links from the cvs file
        countOfLinks = loadLinks(links, cities);

        // create adjacency list for each city base on the array of links
        createAdjacencyLists(countOfCities, cities, countOfLinks, links);

        // a new scrollable map of the city and their corresponding links
        MapOfCities map = DrawTheMap(countOfCities, cities, countOfLinks, links);

        // user input, SOURCE, DEST
        SearchField source = new SearchField(6, 1, 85, 2, new Font("Georgia", Font.PLAIN, 14), "ADD", "#90ee90");
        SearchField dest = new SearchField(6, 1, 85, 2, new Font("Georgia", Font.PLAIN, 14), "ADD", "#90ee90");
        SearchField(cities, countOfCities,  map, source, dest);

        // get the users input (starting point and destination)
        //InputOfTheUser(cities, countOfCities);

    } // end main
    //************************************************************************

    // method to read city data into an array from a data file
    public static int loadCities(Cities[] cities) {

        int count = 0; // count for the number of cities

        String[][] citiesData = new String[170][3]; // datas in our file
        String delimiter = ",";                     // the delimiter in cvs file
        String line;                                // this string holds each line from the file


        // our file with the list of cities
        String fileName = "citiess.csv";

        try {
            // Create a Scanner to read the input from a file
            Scanner infile = new Scanner(new File(fileName));

            /* This while loop reads lines of text into an array. it uses a Scanner class
             * boolean function hasNextLine() to see if there is another line in the file.
             */
            while (infile.hasNextLine()) {
                // read the line
                line = infile.nextLine();

                // split the line into separate objects and store them in a row in the array
                citiesData[count] = line.split(delimiter);

                // read data from the 2D array into an array of Cities objects
                cities[count].setName(citiesData[count][0]);
                cities[count].setX(Integer.parseInt(citiesData[count][1]));
                cities[count].setY(Integer.parseInt(citiesData[count][2]));

                count++;
            }// end while

            infile.close();

        } catch (IOException e) {
            // error message dialog box with custom title and the error icon
            JOptionPane.showMessageDialog(null, "File not found: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return count;

    } // end loadCities()

    //    *************************************************************************

    static int loadLinks(Edges[] links, Cities[] cities) {

      // Counting links the number
        int count = 0;

        String[][] CitiesLinksArray = new String[1200][3]; // datas in our file
        String delimiter = ",";                        // the delimiter in cvs file
        String line;				                   // this string holds each line from the file


        // thats our file withe the links
        String fileName = "linkss.csv";

        try {
            // Create a Scanner to read the input from a file
            Scanner infile = new Scanner(new File(fileName));

            /* This while loop reads lines of text into an array. it uses a Scanner class
             * boolean function hasNextLine() to see if there another line in the file.
             */
            while (infile.hasNextLine()) {
                // read the line
                line = infile.nextLine();

                // split the line into separate objects and store them in a row in the array
                CitiesLinksArray[count] = line.split(delimiter);

                // read link data from the 2D array into an array of Edges objects
                // set the source for the vertex which is 1st column in the file
                links[count].setSource(findCities(cities, CitiesLinksArray[count][0]));
                // set the destination which will be the 2nd column in the file
                links[count].setDestination(findCities(cities, CitiesLinksArray[count][1]));
                //set the length between each other which is the 3rd column in the file
                links[count].setLength(Integer.parseInt(CitiesLinksArray[count][2]));

                count++;

            }// end while

        } catch (FileNotFoundException e) {
            // error message dialog box with custom title and the error icon
            JOptionPane.showMessageDialog(null, "File not found: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return count;
    } // end loadLinks()

    //    *******************************************************************************
    static Cities findCities(Cities[] cities, String c) {
        int index = 0;  // loop counter
        // This will go through the cities array until the name of the city is found
        while (cities[index].getName().compareTo(c) != 0) {
            index++;
        }// end while()

        return cities[index];

    } // end findCities()

    //    *******************************************************************************

    static void createAdjacencyLists(int countOfCities, Cities[] cities, int linkCount, Edges[] links) {

        AdjacentNodes temp = new AdjacentNodes();

        // iterate cities array
        for (int i = 0; i < countOfCities; i++) {

            /* Iterate the link array in reverse order.
               each new link will be placed at the head of the list
               resulting in a list in alphabetical order.*/
            for (int j = linkCount-1; j >= 0; j--) {

                /* if the current link's source is the current city, then
                   create a node for the link and insert it into the
                   adjancency list as the new head of the list. */
                if (links[j].getSource() == cities[i]) {

                    // temporarily store the current value of the list's head
                    temp = cities[i].getAdjacentHead();

                    //create a new node
                    AdjacentNodes newNode = new AdjacentNodes();

                    // add city and distance data
                    newNode.setCity(links[j].getDestination());
                    newNode.setDistance(links[j].getLength());

                    // point newNode to the previous list head
                    newNode.setNext(temp);

                    // set the new head of the list to newNode
                    cities[i].setAdjacentHead(newNode);

                }  // end if
            } // end for j  (iterate links)
        } // end for i (iterate cities)

    } // end createAdjacencyLists() ********************************************

    /*
    public static void InputOfTheUser(Cities[] cities, int countOfCities) {
        String currentPosition;   // current position of the user
        String destination;    // destination where the user wants to go
        int currentIndex;  // current index of unchecked city to be worked with
        int nextIndex = 0;  // next index to be worked with
        AdjacentNodes currentAdjacent;  // current node in the list

        Scanner user = new Scanner(System.in);

        System.out.println("\n- This program will find the shortest path between two cities.");

        System.out.println("\nPlease enter name of the city followed by the state you want to start from: ");
        currentPosition = user.nextLine();
        System.out.println("Please enter name of the city followed by the state you want to go to: ");
        destination = user.nextLine();

        for (currentIndex = 0; currentIndex < countOfCities; currentIndex++) {
            if (cities[currentIndex].getName().equalsIgnoreCase(currentPosition)) {
                cities[currentIndex].setBestDistance(0);
                break;
            }// end if
        }// end for

        while (notFullyVisited(cities, countOfCities)) {

            Cities temp = new Cities();
            currentAdjacent = cities[currentIndex].getAdjacentHead();

            // Dijkstra's Algorithm
            while (!(currentAdjacent == null)) {

                // adds current city's best distance to one of it's adjacent
                // city's best distance
                int currentDistance = cities[currentIndex].getBestDistance() +
                        currentAdjacent.getCitiesDistance();

                if (currentDistance < currentAdjacent.getCities().getBestDistance()) {
                    currentAdjacent.getCities().setBestDistance(currentDistance);
                    currentAdjacent.getCities().setIsNext(cities[currentIndex]);
                }// end if

                currentAdjacent = currentAdjacent.next;

            }// end while

            // Set the current city's adjacent city as visited
            cities[currentIndex].setVisited(true);

            temp.setBestDistance(Integer.MAX_VALUE);

            // loop to find the next city with lowest distance
            for (currentIndex = 0; currentIndex < countOfCities; currentIndex++){
                if((cities[currentIndex].getBestDistance() < temp.getBestDistance())
                        && (cities[currentIndex].getVisited() == false))
                {
                    temp.setBestDistance(cities[currentIndex].getBestDistance());
                    nextIndex = currentIndex;
                }// end if
            }// end for

            currentIndex = nextIndex;

        }// end while

            System.out.println("The shortest path from " + currentPosition + " to " + destination + ":");
            shortestPathPoints(cities, countOfCities, destination);


    } //end InputOfTheUser




    //printing shortest way between the source and destination
    public static void shortestPathPoints(Cities[] cities, int countOfCities, String s){

        Cities current = null;
        String[] points = new String[150]; //max points including the starting point

        int pointsCount = -1;

        for (int i = 0; i < countOfCities; i++){
            if(cities[i].getName().equalsIgnoreCase(s)){
                current = cities[i];
                break;
            }// end if
        }// end for

        while (current != null){
            points[++pointsCount] = current.getName();
            current = current.getIsNext();
        }// end while
        System.out.println("\nThe path including starting point: ");
        for (int i = pointsCount; i > 0; i--){

            System.out.println("- " + points[i] );

            pointsCount--;
        }// end for

        System.out.println("- " +points[pointsCount--] );

        System.out.println("Path created");

    }// end shortestPath


    // The function above checks if all of the cities (array of object of class Cities) are visited or not.
    // The function returns true if any city is not visited or else returns false
    // (means all cities are visited). Where getVisited() is function inside Cities
    // class which mentions if city (single city) is visited or not.
    public static boolean notFullyVisited(Cities[] cities, int countOfCities){
        // initializing notVisited to false
        boolean notVisited = false;

        //loop to iterate the array elements (objects)
        for (int i = 0; i < countOfCities; i++){

            //calling getVisited function for each object and inversing the result using '!'
            //statements inside will be executed if city is not visited (getVisited returns false)
            if (!(cities[i].getVisited())){

                //set the notVisited to true as any one city from array if Cities is not visited
                notVisited = true;
                break;
            }// end if
        }// end for

        return notVisited;

    }// end notFullyVisited
    */

    static MapOfCities DrawTheMap(int countOfCities, Cities[] cities, int countOfLinks, Edges[] links){
        // using Jframe to create a frame for the map
        JFrame mapFrame = new JFrame();

        // set the frame's properties
        mapFrame.setTitle("Our list of U.S Cities (Not All)");
        mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mapFrame.setLayout(new BorderLayout());
        mapFrame.setSize(800, 600);
        mapFrame.setResizable(true);

        // create an instance of CityMap
        MapOfCities map = new MapOfCities(countOfCities, cities, countOfLinks, links);

        // put the map on a ScrollPane in the frame
        mapFrame.add(new JScrollPane(map), BorderLayout.CENTER);

        map.addMouseListener(new MouseAdapter() {// provides empty implementation of all
            // MouseListener`s methods, allowing us to
            // override only those which interests us
            @Override //I override only one method for presentation
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());
                Cities city = chosencity(countOfCities, cities, e.getX(), e.getY());
                if(city!=null) {
                	if(sd==0) {
                    	System.out.println(city.toString()+" source\n");
                    	msource = city;
                        map.setSource(city.getX(), city.getY());
                        map.repaint();
                    	sd=1;
                    	sfm = 1;
                	}
                	else {
                    	System.out.println(city.toString()+" destination");
                    	mdestination = city;
                        map.setDest(city.getX(), city.getY());
                        map.repaint(); 
                    	sd=0;
                    	dfm = 1;
                	}
               	
                }
            }
        });

        // show the map
        mapFrame.setVisible(true);
        mapFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        return map;
    }// end DrawTheMap()

    public static Cities chosencity(int countofcities, Cities[] cities, int x, int y) {
    	Cities city = new Cities();
    	city = null;
    	for(int i=0;i<countofcities;i++) {
    		if(cities[i].getX()<x+5&&cities[i].getX()>x-5) {
    			if(cities[i].getY()<y+5&&cities[i].getY()>y-5) {
    				city = cities[i];
    				i=countofcities;
    			}
    		}
    	}
    	return city;
    }

// *****************************************************************************************************
    static void SearchField(Cities[] cities, int countOfCities, MapOfCities map, SearchField source, SearchField dest) {
        // Four Fields for user input: SOURCE input "city, state" && DEST input "city, state"

        JFrame search_f = new JFrame();
        search_f.setTitle("SEARCH FIELD (ALL CITIES)");
        search_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        search_f.setLayout(new BorderLayout());
        search_f.setSize(750, 400);
        search_f.setResizable(true);

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel(gbl);

        Dijkstra dijkstra = new Dijkstra();
        JLabel distLabel = new JLabel("Total distance: ...");

        JButton resetBtn = new JButton("RESET");
        resetBtn.setBackground(Color.decode("#ee9090"));
        resetBtn.setFont(new Font("Georgia", Font.BOLD, 12));

        source.btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // read SOURCE input

                String city = source.getCityInput();
                String state = source.getStateSelection();

                // Make sure field not null
                if(city.isEmpty() || state.isEmpty()){
                    source.resultsLabel.setText("missing source field");
                    return;
                }

                // check valid input valid (City, State)
                String msg = source.checkValidInput(city, state);

                // Search string for CSV's (if present)
                String searchString = city + " " + state;

                int found = checkCityCSV(cities, countOfCities, searchString);

                if(found != -1 && msg.length() == 0){
                    // use existing data from CSV files
                    // HIGHLIGHT PT ON MAP

                    map.setSource(cities[found].getX(), cities[found].getY());
                    map.setShowPath(false);
                    map.repaint();
                    source.resultsLabel.setText("");
                    dijkstra.path.setText("");
                    sfm = 0;
                }

                else if(msg.length() != 0){
                    // print err message
                    source.resultsLabel.setText(msg);
                }

                else{
                    // City not on the map, represent with geocode coordinates

                    double[] retval = geocodeHandler(city, state);
                    if(isInUSA(retval[0], retval[1])) source.resultsLabel.setText(Arrays.toString(retval)); // within the bounds of the US? display (lat, lng)
                    else dest.resultsLabel.setText("NOT IN USA");
                    map.setSource(0, 0);
                    map.setShowPath(false);
                }
            }
        });


        dest.btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // read DEST input

                String city = dest.getCityInput();
                String state = dest.getStateSelection();

                if(city.isEmpty() || state.isEmpty()){
                    dest.resultsLabel.setText("missing dest field");
                    return;
                }

                String msg2 = dest.checkValidInput(city, state);
                String searchString = city + " " + state;

                int found2 = checkCityCSV(cities, countOfCities, searchString);

                if(found2 != -1 && msg2.length() == 0)   {
                    // HIGHLIGHT ON MAP

                    map.setDest(cities[found2].getX(), cities[found2].getY());
                    map.setShowPath(false);
                    map.repaint();
                    dest.resultsLabel.setText("");
                    dijkstra.path.setText("");
                    dfm = 0;
                }
                else if(msg2.length() != 0){
                    dest.resultsLabel.setText(msg2);
                }
                else{
                    // represent as geocode coordinates

                    double[] retval = geocodeHandler(city, state);
                    if(isInUSA(retval[0], retval[1])) dest.resultsLabel.setText(Arrays.toString(retval));
                    else dest.resultsLabel.setText("NOT IN USA");
                    map.setDest(0,0);
                    map.setShowPath(false);
                }
            }
        });


        dijkstra.btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "GO" button, valid when both SOURCE and DEST are filled

                // Validate input again, as ADD just maps the city to a pt on the map
                String sourceCity = source.getCityInput();
                String sourceState = source.getStateSelection();

                String destCity = dest.getCityInput();
                String destState = dest.getStateSelection();


                // Make sure field not null
                String sourc;
                String des;
                StringBuilder sb = new StringBuilder("");
                if (sfm == 1) {
            		String sourcelist[] = msource.getName().split(" ",10);
            		sourceCity = sourcelist[0];
            		for(int i =1;i<sourcelist.length-1;i++) {
            			sourceCity += " " + sourcelist[i];
            		}
            		sourceState = sourcelist[sourcelist.length-1];
                }
                else if(sourceCity.isEmpty() || sourceState.isEmpty()) {
                    dijkstra.info.setText("missing fields");
                    return;
                }
                sourc = sourceCity + " " + sourceState;
           		if (dfm == 1) {
           			String destlist[] = mdestination.getName().split(" ",10);
           			destCity = destlist[0];
           			for(int i =1;i<destlist.length-1;i++) {
           				destCity += " " + destlist[i];
           			}	
           			destState = destlist[destlist.length-1];
           		}
           		else if(destCity.isEmpty() || destState.isEmpty()) {
                	if (mdestination != null) {
                		String destlist[] = mdestination.getName().split(" ",10);
                		destCity = destlist[0];
                		for(int i =1;i<destlist.length-1;i++) {
                			destCity += " " + destlist[i];
                		}
                		destState = destlist[destlist.length-1];
                	}
                	else {
                        dijkstra.info.setText("missing fields");
                    	return;
                	}
                }
                des = destCity + " " +  destState;
                source.states.setSelectedItem(sourceState);
                dest.states.setSelectedItem(destState);
                source.cityInput.setText(sourceCity);
                dest.cityInput.setText(destCity);

                int found = checkCityCSV(cities, countOfCities, sourc);

                dijkstra.info.setText("");
                source.resultsLabel.setText("");
                dijkstra.path.setText("");

                int found2 = checkCityCSV(cities,countOfCities, des);

                dest.resultsLabel.setText("");
                dijkstra.path.setText("");
           	    dfm = 0;
           	    sfm  = 0;

                if(found == -1 || found2 == -1) {
                    // one of the cities not in csv, only calculate distance between the two cities
                    // as geocode coordinates

                    double[] sourceGeocode = geocodeHandler(sourceCity, sourceState);
                    double[] destGeocode = geocodeHandler(destCity, destState);
                    source.resultsLabel.setText(Arrays.toString(sourceGeocode));
                    dest.resultsLabel.setText(Arrays.toString(destGeocode));

                    double distMiles = calcDistTemp(sourceGeocode[0], sourceGeocode[1], destGeocode[0], destGeocode[1]);
                    distLabel.setText("Distance: " + Double.toString(distMiles) + " (mi)");
                    dijkstra.path.setText("");
                }

                else {
                    // Both valid, compute shortestPath (returns a string of cities taken
                    // to get from source to dest)
                    String path = dijkstra.shortestPath(cities, countOfCities, sourc, des);
                    dijkstra.path.setText("The path from \"" + sourc.toUpperCase() + "\" to \"" + des.toUpperCase() + "\" ...\n\n" + path);
                    distLabel.setText("Distance: " + dijkstra.getDistance() + "(mi)");

                    // redraw map, to show path!
                    ArrayList<String> rPath = dijkstra.getPathRaw();
                    map.setPath(rPath);
                    map.setShowPath(true);
                    map.repaint();
                    for (int i = 0;i < countOfCities;i++) {
                    	cities[i].rescities();
                    }
                }
            }
        });


        JCheckBox showLinks = new JCheckBox("show links");
        showLinks.setSelected(true);

        showLinks.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // check or un-check to show/not-show links on map

                if(e.getStateChange() == 1) map.setShowLinks(true);
                else map.setShowLinks(false);

                map.repaint();
            }
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset all fields
                map.setDest(0,0);
                map.setSource(0, 0);
                map.setShowPath(false);
                source.clearAll();
                dest.clearAll();
                dijkstra.path.setText("");
                distLabel.setText("Distance: ...");
                dfm = 0;
                sfm  = 0;
                map.repaint();
            }
        });

        // LAYOUT
        Insets isLeft = new Insets(0, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridx = 0;
        gbc.ipadx = 20;
        gbc.ipady = 20;
        panel.add(new JLabel(""), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 20;
        gbc.ipady = 20;
        gbc.gridwidth = 2;
        panel.add(new JLabel("CITY"), gbc);

        gbc.insets = (new Insets(0, 30, 0, 0));
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(new JLabel("STATE"), gbc);

        gbc.insets = (isLeft);
        gbc.gridx = 4;
        gbc.gridy = 0;
        panel.add(showLinks, gbc);

        gbc.insets = (new Insets(0, 0, 0, 0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("SOURCE"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(source.cityInput, gbc);

        gbc.insets = (new Insets(0, 10, 5, 0));
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.ipadx = 60;
        gbc.gridwidth = 1;
        panel.add(source.states, gbc);

        gbc.insets = (isLeft);
        gbc.gridx = 4;
        gbc.gridy = 1;
        panel.add(source.btn, gbc);

        gbc.insets = (isLeft);
        gbc.gridx = 5;
        gbc.gridy = 1;
        panel.add(source.resultsLabel, gbc);

        gbc.insets = (new Insets(0, 0, 0, 0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("DEST"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(dest.cityInput, gbc);

        gbc.insets = (new Insets(0, 10, 0, 0));
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel.add(dest.states, gbc);

        gbc.insets = (isLeft);
        gbc.gridx = 4;
        gbc.gridy = 2;
        panel.add(dest.btn, gbc);

        gbc.insets = (isLeft);
        gbc.gridx = 5;
        gbc.gridy = 2;
        panel.add(dest.resultsLabel, gbc);

        gbc.insets = (new Insets(10, 0, 0, 0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        panel.add(resetBtn, gbc);

        gbc.insets = (new Insets(10, 0, 0, 0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        panel.add(dijkstra.btn, gbc);

        gbc.insets = (new Insets(8, 10, 0, 0));
        gbc.gridx = 5;
        gbc.gridy = 4;
        panel.add(dijkstra.info, gbc);

        gbc.insets = (new Insets(15, 0, 0, 0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(dijkstra.path, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        distLabel.setForeground(Color.decode("#5E77F9"));
        panel.add(distLabel, gbc);

        panel.setVisible(true);
        search_f.add(panel);
        search_f.setVisible(true);
    }

    static double[] geocodeHandler(String city, String state){
        // at this point, error handling detected no errors, and the
        // user's request is for a city, state not located in ours CSV files.

        if(!city.isEmpty()){
            // still works with just the city, but both would be better

            Map<String, String> location = new HashMap<>();
            location.put("city", city);
            location.put("state", state);

            try{
                Search search = new Search(8000);

                try{
                    double[] geocode = search.geocoding(new HashMap<>(location));
                    //System.out.println("lat: " + geocode[0]);
                    //System.out.println("lon: " + geocode[1]);

                    return geocode;

                }catch (ParseException pe){
                    pe.printStackTrace();
                }

            }catch (IOException err){
                err.printStackTrace();
            }
        }
        return null;
    }

    static int checkCityCSV(Cities[] cities, int countOfCities, String location){
        // check if city searched is on map (and in CSV files)
        // if no, go different path

        location = location.replaceAll("\\s", ""); // remove all spaces to perform search. ex. "ST.PAUL" vs "ST. PAUL"

        for(int i = 0; i<countOfCities; ++i){
            String name = cities[i].getName();
            name = name.replaceAll("\\s", "");
            if(name.compareToIgnoreCase(location) == 0) return i;
        }

        return -1;
    }

    static double calcDistTemp(double lat1, double lng1, double lat2, double lng2){
        // Haversine formula ... (greater circle distance between two geocode coordinates)
        // dist = 2r arcsin sqrt(sin^2((lat2-lat1)/2)) + cos(lat1)cos(lat2) * sin^2((lng2-lng1)/2)))
        // r is Earth's radius in km: 6371

        double distLat = Math.toRadians(lat2 - lat1);
        double distLng = Math.toRadians(lng2 - lng1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // formula defined above
        double a = Math.pow(Math.sin(distLat / 2), 2) +
                Math.pow(Math.sin(distLng / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);

        double earthRadius = 6371; // in km
        double c = 2 * Math.asin(Math.sqrt(a));
        double temp = earthRadius * c;

        double distKm = earthRadius * c;

        return distKm * 0.621371; // in miles (mi)

    }

    static boolean isInUSA(double lat, double lng){
        // are the received coordinates within the bounds of the USA (don't include HI, or AK)

        double MIN_LAT = 24.9493;
        double MAX_LAT = 49.5904;
        double MIN_LNG = -125.0011;
        double MAX_LNG = -66.9326;

        boolean latUS = lat >= MIN_LAT && lat <= MAX_LAT ? true : false;
        boolean lngUS = lng >= MIN_LNG && lng <= MAX_LNG ? true : false;

        return (latUS && lngUS);
    }
}
