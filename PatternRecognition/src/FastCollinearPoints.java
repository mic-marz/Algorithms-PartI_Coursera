import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/* L'implementazione sotto riportata gestisce i punti in modalità ricorsiva in base ai segmenti ritrovati di volta in volta, guadagnando in tempo
 *  di esecuzione, poichè ogni punto si ritrova a trovare segmenti su una lista ridotta di punti (quella originale alla quale è stata sotratta la 
 *  lista di punti che costituiscono il segmento trovato precedentemente). Lo svantaggio di questo metodo è che comunque ci si potrebbe trovare
 *  di fronte a dei segmenti duplicati, quindi prima di inserirli nell'array definitivo, occorre verificare che non siano stati già inseriti.
 *  Un'implementazione alternativa (sicuramente più semplice) potrebbe lavorare su liste di punti non ridotte e inserire il segmento 
 *  soltanto se il punto corrente che ha trovato il segmento rappresenta un punto particolare (ad esempio il min oppure il max).
 *  
 *   N.B. L'implementazione in modalità ricorsiva potrebbe portare ad uno stack overflow!!!
 * 
 */
public class FastCollinearPoints {
	private Point[] points = null;
	private LineSegment[] segments = null;
	
	public FastCollinearPoints(Point[] pts) {
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
	
	/*
	private List<List<Point>> findSegments(Point origin, List<Point> toExclude) {
		List<List<Point>> segments = new ArrayList<List<Point>>();
		Comparator<Point> comp = origin.slopeOrder();
		List<Point> reducedList = new ArrayList<Point>(Arrays.asList(points));
		reducedList.removeAll(toExclude);
		Point[] sortingArray = reducedList.toArray(new Point[reducedList.size()]); 
		Arrays.sort(sortingArray, comp);

		int j = 0;
		while (j < sortingArray.length) {
			int count = 0;
			int k = j + 1;
			while (k < sortingArray.length && comp.compare(sortingArray[j], sortingArray[k]) == 0) {
				k++;
				count++;
			}
			if (count >= 2) {
				List<Point> pts = new ArrayList<Point>();
				pts.add(origin);
				for (int s = k - 1; s >= k - 1 - count; s--) {
					pts.add(sortingArray[s]);
				}
				segments.add(pts);
			}
			j = k;
		}
		return segments;
	}
	
	private void workOnPoint(Point currentPoint, List<LineSegment> segs, Map<Double, List<Point>> segsInfo, List<Point> generalList, List<Point> toExclude) {
		generalList.remove(generalList.indexOf(currentPoint));
		for (List<Point> segmentFound : findSegments(currentPoint, toExclude)) {
			Point[] minMax = findMinMaxPoints(segmentFound);
			//StdOut.print("\nSegment found with min: " + minMax[0].toString() + " and max: " + minMax[1].toString() + " for current point: " + currentPoint.toString());
			Double slope = minMax[0].slopeTo(minMax[1]);
			List<Point> segsWithFoundSlope = segsInfo.get(slope);
			if (segsWithFoundSlope == null) {
				List<Point> pts = new ArrayList<Point>(Arrays.asList(minMax[0]));
				segsInfo.put(slope, pts);
				LineSegment seg = new LineSegment(minMax[0], minMax[1]);
				segs.add(seg);				
			} else {
				boolean found = false;
				for (Point minWithFoundSlope : segsWithFoundSlope) {
					if (minMax[0].compareTo(minWithFoundSlope) == 0) {
						found = true;
						break;
					}
				}
				if (!found) {
					segsWithFoundSlope.add(minMax[0]);
					LineSegment seg = new LineSegment(minMax[0], minMax[1]);
					segs.add(seg);
				}
			}

			List<Point> collPts = new ArrayList<Point>(segmentFound);
			collPts.remove(segmentFound.indexOf(currentPoint));
			for (Point collPt : collPts) {
				if (generalList.contains(collPt)) {
					//StdOut.print("\nCollinear point: " + collPt.toString());
					workOnPoint(collPt, segs, segsInfo, generalList, segmentFound);
				}
			}
		}
	}		

	public LineSegment[] segments() {
		if (segments == null) {
			Map<Double, List<Point>> segsInfo = new HashMap<Double, List<Point>>();  // For duplicate segments detection
			List<LineSegment> segs = new ArrayList<LineSegment>();			
			List<Point> generalList = new ArrayList<Point>(Arrays.asList(points));
			Iterator<Point> generalIt = generalList.iterator();
			while (generalIt.hasNext()) {
				Point currentPoint = generalIt.next();
				//StdOut.print("\nCURRENT POINT: " + currentPoint.toString());
				workOnPoint(currentPoint, segs, segsInfo, generalList, Arrays.asList(currentPoint));	
				generalIt = generalList.iterator();
			}
			//StdOut.print("\n");
			segments = segs.toArray(new LineSegment[segs.size()]);			
		}
		return Arrays.copyOf(segments, segments.length);		
	}  */
	
	
	
	public LineSegment[] segments() {
		if (segments == null) {
			List<LineSegment> segs = new ArrayList<LineSegment>();			
			Iterator<Point> ptIt = Arrays.asList(points).iterator();
			while (ptIt.hasNext()) {
				Point origin = ptIt.next();
				Comparator<Point> comp = origin.slopeOrder();
				Point[] sortingArray = Arrays.asList(points).toArray(new Point[0]); 
				Arrays.sort(sortingArray, comp);

				int j = 0;
				while (j < sortingArray.length) {
					int count = 0;
					int k = j + 1;
					while (k < sortingArray.length && comp.compare(sortingArray[j], sortingArray[k]) == 0) {
						k++;
						count++;
					}
					if (count >= 2) {
						List<Point> pts = new ArrayList<Point>();
						pts.add(origin);
						for (int s = k - 1; s >= k - 1 - count; s--) {
							pts.add(sortingArray[s]);
						}
						Point[] minMax = findMinMaxPoints(pts);
						Point min = minMax[0];
						Point max = minMax[1];
						if (origin.compareTo(min) == 0) { // To avoid duplicate add line segment only by mininum point */ 
							segs.add(new LineSegment(min, max));
						}						
					}
					j = k;
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	}
}
