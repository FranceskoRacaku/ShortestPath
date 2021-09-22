public class Edges {

    protected Cities  source;		// that is the source of the vertex;
    protected Cities  destination;	// that is the destination of vertex;
    protected int length;			// that is the length of the edge;

    Edges() {
    }

    Edges(Cities source, Cities destination, int length) {
        this.source = source;
        this.destination = destination;
        this.length = length;
    } // end Edges()


    public void setSource(Cities source) {
        this.source = source;
    }  // end setSource

    public void setDestination(Cities destination) {
        this.destination = destination;
    } // end setDestination

    public void setLength(int length) {
        this.length = length;
    } // end setLength

    public Cities getSource() {
        return this.source;
    }  // end getSource

    public Cities getDestination() {
        return this.destination;
    } // end getDestination

    public int getLength() {
        return this.length;
    } // end getLength

    public String toString() {
        return "The distance from " + this.source + " to " + this.destination + " is: " + this.length;
    }

}  //end class edges