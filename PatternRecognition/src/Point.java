/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertcal;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
    	if (that == null)
    		throw new NullPointerException();
        /* YOUR CODE HERE */
    	if ((that.y == this.y) && (that.x == this.x))
    		return Double.NEGATIVE_INFINITY;
    	if (that.x == this.x)
    		return Double.POSITIVE_INFINITY;
    	if (that.y == this.y)
    		return +0.0;
    	return ((double) (that.y - this.y) / (double) (that.x - this.x));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
    	if (that == null)
    		throw new NullPointerException();
        /* YOUR CODE HERE */
    	if (this.y == that.y)
    		return (this.x - that.x);
    	else
    		return (this.y - that.y);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
    	return new PointComparator();
    }
    
    private class PointComparator implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			if ((p1 == null) || (p2 == null))
	    		throw new NullPointerException();				
			Double withP1 = Point.this.slopeTo(p1);
			Double withP2 = Point.this.slopeTo(p2);
			if (withP1 == Double.POSITIVE_INFINITY) {
				if (withP2 == Double.NEGATIVE_INFINITY)
					return +1;
				if (withP2 == Double.POSITIVE_INFINITY)
					return 0;
			}
			if (withP1 == Double.NEGATIVE_INFINITY) {
				if (withP2 == Double.POSITIVE_INFINITY)
					return -1;
				if (withP2 == Double.NEGATIVE_INFINITY)
					return 0;
			}

			double diff = (Point.this.slopeTo(p1) - Point.this.slopeTo(p2));
			if (diff == 0)
				return 0;
			else if (diff < 0)
				return -1;
			else 
				return +1;
		}    	
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    	Point p1 = new Point(1, 2);
    	Point p2 = new Point(1, 2);
    	StdOut.print("p1 : " + p1.toString() + ", p2 : " + p2.toString());
    	
    	StdOut.print("\nSlope p1-p2 : " + p1.slopeTo(p2));    	
    	int result = p1.compareTo(p2);
    	StdOut.print("\n");
    	if (result > 0)
    		StdOut.print("p1 maggiore di p2");
    	else if (result < 0)
    		StdOut.print("p1 minore di p2");
    	else
    		StdOut.print("p1 uguale a p2");
    	
    	Comparator<Point> comp = p1.slopeOrder();
    	StdOut.print("\n");
    	Point p3 = new Point(4, 1);
    	StdOut.print("p3 : " + p3.toString());
    	StdOut.print("\n");
    	result = comp.compare(p2, p3);
    	if (result > 0)
    		StdOut.print("p2-p1 maggiore di p3-p1");
    	else if (result < 0)
    		StdOut.print("p2-p1 minore di p3-p1");
    	else
    		StdOut.print("p2-p1 uguale a p3-p1");
    }
}
