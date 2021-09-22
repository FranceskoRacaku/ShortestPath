import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dijkstra {

    public JButton btn;
    public JLabel info;
    public JTextArea path;
    private int distance;
    private ArrayList<String> pathRaw;

    Dijkstra(){
        this.btn = new JButton("GO");
        this.btn.setBackground(Color.decode("#49B8C5"));
        this.btn.setFont(new Font("Georgia", Font.BOLD, 16));
        this.info = new JLabel("");
        this.info.setFont(new Font("Georgia", Font.BOLD, 12));
        this.info.setForeground(Color.decode("#ff4c4c"));
        this.path = new JTextArea("");
        this.path.setEditable(false);
        this.path.setFont(new Font("Georgia", Font.BOLD, 14));
        this.path.setForeground(Color.decode("#707070"));
        this.distance = 0;
        this.pathRaw = new ArrayList<String>();
    }

    public int getDistance(){
        return distance;
    }

    public ArrayList<String> getPathRaw(){
        return this.pathRaw;
    }

    public String shortestPath(Cities[] cities, int countOfCities, String currentPosition, String destination){
        // SOURCE AND DEST already highlighted on map
        // Calc and display shortest path from SOURCE to DEST
        int currentIndex;  // current index of unchecked city to be worked with
        int nextIndex = 0;  // next index to be worked with
        AdjacentNodes currentAdjacent;  // current node in the list

        //System.out.println("hello: " + currentPosition + "\t" + destination);

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

        String retval = shortestPathPoints(cities, countOfCities, destination);

        return retval;
    }

    private boolean notFullyVisited(Cities[] cities, int countOfCities){
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

    private String shortestPathPoints(Cities[] cities, int countOfCities, String s){
        // to print the shortest way between the source and destination

        StringBuilder sb = new StringBuilder("");
        this.pathRaw = new ArrayList<String>(); // reset

        Cities current = null;
        String[] points = new String[150]; //max points including the starting point
        int[] currentDistances = new int[150]; // displays current distance taken so far next to city name

        int pointsCount = -1;
        int distCount = -1;

        for (int i = 0; i < countOfCities; i++){
            if(cities[i].getName().equalsIgnoreCase(s)){
                current = cities[i];
                break;
            }// end if
        }// end for

        distance = current.getBestDistance(); // this is the DEST node, its best distance is total distance from SOURCE to DEST

        while (current != null){
            points[++pointsCount] = current.getName();
            currentDistances[++distCount] = current.getBestDistance();

            current = current.getIsNext();
        }// end while

        for (int i = pointsCount; i > 0; i--){

            sb.append("- (" + Integer.toString(currentDistances[i]) + ") "  + points[i] + "\n");
            pathRaw.add(points[i]);

            pointsCount--;
            distCount--;
        }// end for


        pathRaw.add(points[pointsCount]);
        sb.append("- (" + Integer.toString(currentDistances[distCount--]) + ") " + points[pointsCount--]);

        return sb.toString();
    }
}