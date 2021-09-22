public class Cities extends CitiesInfo {

    // added properties
    private boolean visited = false;
    private int bestDistance = Integer.MAX_VALUE;   //max value the integer can have
    private Cities IsNext;
    private AdjacentNodes AdjacentHead;  // links to the first node in adjacency list


    // city constructor
    Cities() {}

    //added methods
    public void setVisited(boolean v) {
        this.visited = v;
    } // end setVisited()

    public void setBestDistance(int b) {
        this.bestDistance = b;
    } // end setBestDistance()

    public void setIsNext(Cities n) {
        this.IsNext = n;
    } // end setIsNext()

    public void setAdjacentHead (AdjacentNodes h) {
        this.AdjacentHead = h;
    } // end setAdjacentHead()

    public boolean getVisited() {
        return this.visited;
    } // end getVisited()

    public int getBestDistance() {
        return this.bestDistance;
    } // end getBestDistance()

    public Cities getIsNext() {
        return this.IsNext;
    } // end getIsNext()

    public AdjacentNodes getAdjacentHead()  {
        return this.AdjacentHead;
    } // end getAdjacentHead()

    public void rescities() {
    	this.visited = false;
    	this.IsNext = null;
    	this.bestDistance = Integer.MAX_VALUE;
    	//this.AdjacentHead = null;
    	
    }

} // end class Cities

