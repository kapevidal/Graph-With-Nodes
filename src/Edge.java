import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

//EDGE CLASS
public class Edge extends GGxxxx
{
    public Node n1;
    public Node n2;
    
    private int key = 0;
    public List<Node> vertMembers = new ArrayList<Node>();
    
    private boolean selectedEd = false;//if edge is selected
    
    private Rectangle b = new Rectangle();// bounded area that can be clicked to toggle the edge
    
    public Edge(Node n1, Node n2)
    {
        this.n1 = n1;
        this.n2 = n2;
        setBoundary(b);
    }
      
    public Node node1()
    {
    	return n1;
    }
    
    public Node node2()
    {
    	return n2;
    }
    
    private void setBoundary(Rectangle b)
    {
    	 Point p1 = n1.getLocation();
         Point p2 = n2.getLocation();
         
        b.setBounds(
        		Math.min(p1.x,p2.x),
        		Math.min(p1.y,p2.y),
        		Math.max(p1.x-p2.x,p2.x-p1.x),
        		Math.max(p1.y-p2.y,p2.y-p1.y)
        		);
    }

    public void draw(Graphics g)
    {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLUE);
        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        
        if(selectedEd)//for testing only! Does not really shows up
        {
        	g2.setColor(Color.GREEN);
        	g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        
        else if(connctdComponents == true)
        {
        	g2.setColor(new Color(key));
        	g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
     }
   
    public boolean contain(Point p)
    {
        return b.contains(p);
    }
    
    public boolean isselectedEd() {
        return selectedEd;
    }
    
    public static boolean selectOne(List<Edge> list, Point p)
    {
        for (Edge n : list)
        {
            if (n.contain(p))
            {
                if (!n.isselectedEd())
                {
                    Edge.selectNone(list);
                    n.setselectedEd(true);
                }
                return true;
            }
        }
        return false;
    }
    
    public void setselectedEd(boolean selectedED)
    {
        this.selectedEd = selectedED;
    }
    
    public static void selectNone(List<Edge> list)
    {
        for (Edge n : list) {
            n.setselectedEd(false);
        }
    }
    
    //multiple selection
    public static void selectToggle(List<Edge> list, Point p)
    {
        for (Edge n : list)
        {
            if (n.contain(p))
            {
                n.setselectedEd(!n.isselectedEd());
            }
        }
    }
    

	public void setKey(int r)
	{
		key=r;
	}
	
	public int getKey()
	{
		return key;
	}
	
}