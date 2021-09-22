public class AdjacentNodes {

    private Cities cities;
    private int distance;

    // link
    AdjacentNodes next;

    AdjacentNodes() {
    }

    AdjacentNodes(Cities c, int d) {
        this.cities = c;
        this.distance = d;

    }  // end AdjacentNodes

    public void setCity(Cities c) {
        this.cities = c;
    } // end setCitiesName()

    public void setDistance(int d) {
        this.distance = d;
    } // end setDistance()

    public void setNext(AdjacentNodes n) {
        this.next = n;
    } // end setNext()

    public Cities getCities() {
        return this.cities;
    } // end getCitiesName()

    public int getCitiesDistance() {
        return this.distance;
    } // end getCitiesDistance()

    public AdjacentNodes getNext() {
        return this.next;
    } // end getNext()

}// end class AdjacentNodes
