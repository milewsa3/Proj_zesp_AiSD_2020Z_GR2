package lab.aisd.algorithm.model;

import lab.aisd.model.Coordinate;

import java.util.*;

public class ConvexHull {
	
	ArrayList<Coordinate> points;
	ArrayList<Coordinate> hull;
	Coordinate p0;
	int n;
	
	private ArrayList<Coordinate> rotate(ArrayList<Coordinate> in, int offset){
	    ArrayList<Coordinate> out = new ArrayList<>();
	    for(int i = 0; i < in.size(); i++){
	        out.add(in.get((i + offset) % in.size()));
	    }
	    return out;
	}
	
	int sgn(int val){
		return Integer.compare(val, 0);
	}
    
    private boolean clockwise(Coordinate a, Coordinate b, Coordinate c) {
        return a.getX()*(b.getY()-c.getY())+b.getX()*(c.getY()-a.getY())+c.getX()*(a.getY()-b.getY()) < 0;
    }

    private boolean cntrclockwise(Coordinate a, Coordinate b, Coordinate c) {
        return a.getX()*(b.getY()-c.getY())+b.getX()*(c.getY()-a.getY())+c.getX()*(a.getY()-b.getY()) > 0;
    }
	
	private void prepare(){
	    
	    n = points.size();
	    
	    if (n == 1)
            return;
        
        Collections.sort(points);
        Coordinate p1 = points.get(0), p2 = points.get(n - 1);
        ArrayList<Coordinate> up = new ArrayList<>();
        ArrayList<Coordinate> down = new ArrayList<>();
        up.add(p1);
        down.add(p1);
        for(int i = 1; i < n; i++) {
            if(i == n - 1 || clockwise(p1, points.get(i), p2)) {
                while (up.size() >= 2 && !clockwise(up.get(up.size() - 2), up.get(up.size() - 1), points.get(i)))
                    up.remove(up.size() - 1);
                up.add(points.get(i));
            }
            if(i == n - 1 || cntrclockwise(p1, points.get(i), p2)) {
                while(down.size() >= 2 && !cntrclockwise(down.get(down.size() - 2), down.get(down.size() - 1), points.get(i)))
                    down.remove(down.size() - 1);
                down.add(points.get(i));
            }
        }
    
        points.clear();
        points.addAll(up);
        for (int i = down.size() - 2; i > 0; i--)
            points.add(down.get(i));
        
        Collections.reverse(points);

		n = points.size();
        int pos = 0;
        for(int i = 1; i < n; i++)
            if(points.get(i).compareTo(points.get(pos)) < 0)
                pos = i;
        
        points = rotate(points, pos);
        n--;
        hull = new ArrayList<>();
        for(int i = 0; i < n; i++)
            hull.add(points.get(i + 1).minus(points.get(0)));
        p0 = points.get(0);
	}
	
	private boolean pointInConvexPolygon(Coordinate point){
	    point = point.minus(p0);
        if(hull.get(0).cross(point) != 0 && sgn(hull.get(0).cross(point)) != sgn(hull.get(0).cross(hull.get(n - 1))))
            return false;
        if(hull.get(n - 1).cross(point) != 0 && sgn(hull.get(n - 1).cross(point)) != sgn(hull.get(n - 1).cross(hull.get(0))))
            return false;
    
        if(hull.get(0).cross(point) == 0)
            return hull.get(0).sqrLen() >= point.sqrLen();
    
        int l = 0, r = n - 1;
        while(r - l > 1){
            int mid = (l + r)/2;
            if(hull.get(mid).cross(point) >= 0){
                l = mid;
            }else{
                r = mid;
            }
        }
        int pos = l;
        return point.pointInTriangle(hull.get(pos), hull.get(pos + 1), new Coordinate(0, 0));
    }
    
    
    public void test(){
        //dodajemy punkty
        Coordinate A = new Coordinate(4, 8);
        Coordinate B = new Coordinate(8, 8);
        Coordinate C = new Coordinate(9, 6);
        Coordinate D = new Coordinate(7, 4);
        Coordinate E = new Coordinate(4, 4);
        Coordinate F = new Coordinate(5, 6);
        Coordinate G = new Coordinate(6, 2);
        points = new ArrayList<>();
        points.add(A);
        points.add(B);
        points.add(C);
        points.add(D);
        points.add(E);
        points.add(F);
        points.add(G);
		
	    //uruchamiamy prepare zeby zostala sama otoczka wypukla
        prepare();
        
        //point in convex polygon zwraca true jak jest w srodku lub na brzegu otoczki, a false w przeciwnym wypadku
        System.out.println(pointInConvexPolygon(new Coordinate(5, 3)));

        //elo
    }
	
}
