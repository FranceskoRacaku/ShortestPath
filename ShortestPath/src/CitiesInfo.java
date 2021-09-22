public class CitiesInfo {

    private String name; 	// name of the city
    private int x;  		// city's x-coordinate for drawing
    private int y;  		// city's y-coordinate for drawing

    CitiesInfo() {
    }

    CitiesInfo(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }  // end Cities()



    public void setName(String name) {
        this.name = name;
    } // end setName()

    public void setX(int x) {
        this.x = x;
    } // end setX()

    public void setY(int y) {
        this.y = y;
    } // end setY()

    public String getName() {
        return this.name;
    } // end getName()

    public int getX() {
        return this.x;
    } // end getX()

    public int getY() {
        return this.y;
    } // end getY()

    public String toString() {
        return (this.name + " " + " " + this.x + " " + this.y);
    } // end toString()

}