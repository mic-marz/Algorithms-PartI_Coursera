import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private Point[] points = null;
	private LineSegment[] segments = null;
	
	public BruteCollinearPoints(Point[] pts) {
		if (pts == null)
			throw new NullPointerException();
		
		for (int i = 0; i < pts.length; i++) {
			if (pts[i] == null)
				throw new NullPointerException();

			for (int j = i + 1; j < pts.length; j++) {
				if ((pts[i].compareTo(pts[j]) == 0))
						throw new IllegalArgumentException();
			}
		}
		points = Arrays.copyOf(pts, pts.length);
	}
	
	public LineSegment[] segments() {
		if (segments == null) {
			List<LineSegment> segs = new ArrayList<LineSegment>();
			for (int i = 0; i < points.length; i++) {
				Comparator<Point> comp = points[i].slopeOrder();
				for (int j = i + 1; j < points.length; j++) {
					for (int k = j + 1; k < points.length; k++) {
						if (comp.compare(points[j], points[k]) == 0) {
							for (int l = k + 1; l < points.length; l++) {
								if (comp.compare(points[j], points[l]) == 0) {
									List<Point> pts = new ArrayList<Point>();
									pts.add(points[i]);
									pts.add(points[j]);
									pts.add(points[k]);
									pts.add(points[l]);
									
									Point[] minMax = findMinMaxPoints(pts);
									LineSegment seg = new LineSegment(minMax[0], minMax[1]); 
									segs.add(seg);
								}
							}
						}
					}
				}
			}
			segments = segs.toArray(new LineSegment[segs.size()]);
		}
		return Arrays.copyOf(segments, segments.length);
	}
	
	private Point[] findMinMaxPoints(List<Point> pts) {
		Point max = new Point(0, 0);
		Point min = new Point(32767, 32767);
		for (Point pt : pts) {
			if (pt.compareTo(max) > 0) {
				max = pt;
			}
			if (pt.compareTo(min) < 0) {
				min = pt;
			}
		}
		Point[] toRet = new Point[2];
		toRet[0] = min;
		toRet[1] = max;
		return toRet;
	}
	
	public int numberOfSegments() {
		if (segments == null)
			segments();
		return segments.length;
	}
	
	public static void main(String[] args) {
		
	    // read the N points from a file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    Point[] points = new Point[N];
	    for (int i = 0; i < N; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.show(0);
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    //int k = 0;
	    LineSegment[] lineSegments = collinear.segments();
	    for (LineSegment segment : lineSegments) {
	    	//k++;
	        //StdOut.println("Segment number " + k + " : " + segment);
	        StdOut.println(segment);
	        segment.draw();
	    }
	    
	    
	    StdOut.print("\nNumber of segments: " + collinear.numberOfSegments());
	    StdOut.print("\nNumber of segments: " + collinear.numberOfSegments());
	    
	    /*check for modification */
	    lineSegments[0] = null;
	    StdOut.print("\n");
	    for (LineSegment segment : collinear.segments()) {
	    	//k++;
	        //StdOut.println("Segment number " + k + " : " + segment);
	        StdOut.println(segment);
	    } 
	}
}
