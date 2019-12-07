import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

// NODE(VERTEX) CLASS
public class Node extends GGxxxx
{
    private Point p; // Mouse Pointer Location
    private int r=5; // Radius of the vertex
    
    public List<Node> adjacentVert = new ArrayList<Node>();
    private boolean selectedVert = false; // if selected
    public boolean articulationVert = false; // if selected
    
    private int key = 0; 
    private Rectangle b = new Rectangle(); //bounded area that can be clicked to toggle the vertex
 
    public Node(Point p)
    {
        this.p = p;
        setBoundary(b);
    }
    
    public Point getP()
    {
    	return p;
    }

    //clickability of the vertex
    private void setBoundary(Rectangle b)
    {
        b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
    }

    //IMPORTANT! FOR SKETCHING THE VERTEX( DIFFERENT COLOR TO HIGHLIGHT IF VERTEX IS SELECTED)
    public void draw(Graphics g) 
    {
    	Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
            g2.fillOval(b.x, b.y, b.width, b.height);
        
        if (selectedVert)
        {
            g2.setColor(Color.GREEN);
            g2.fillOval(b.x, b.y, b.width, b.height);
        }
        else if (connctdComponents==true)
        {
        	g2.setColor(new Color(key));
        	g2.fillOval(b.x, b.y, b.width, b.height);
        }
        
        else if (articulationVert&&cutVertex==true)
        {
            g2.setColor(Color.BLACK);
            g2.fillOval(b.x, b.y, b.width, b.height);
        }
    }
     
    //node location
    public Point getLocation()
    {
        return p;
    }

   //if location is inside rectangle
    public boolean contains(Point p)
    {
        return b.contains(p);
    }

    //return true vertex selected
    public boolean isselectedVert()
    {
        return selectedVert;
    }

    
    public void setselectedVert(boolean selectedVert)
    {
        this.selectedVert = selectedVert;
    }

    //all the multiple-selected vertices will be added to a list
    public static void getselectedVert(List<Node> list, List<Node> selectedVert)
    {
        selectedVert.clear();
        for (Node n : list)
        {
            if (n.isselectedVert())
            {
                selectedVert.add(n);
            }
        }
    }
    
    
    public static void selectNone(List<Node> list)
    {
        for (Node n : list)
        {
            n.setselectedVert(false);
        }
    }
  
    public static boolean selectOne(List<Node> list, Point p)
    {
        for (Node n : list)
        {
            if (n.contains(p))
            {
                if (!n.isselectedVert())
                {
                    Node.selectNone(list);
                    n.setselectedVert(true);
                }
                return true;
            }
        }
        return false;
    }
  
    public static void selectRect(List<Node> list, Rectangle r)
    {
        for (Node n : list)
        {
            n.setselectedVert(r.contains(n.p));
        }
    }

    //multiple selection
    public static void selectToggle(List<Node> list, Point p)
    {
        for (Node n : list)
        {
            if (n.contains(p))
            {
                n.setselectedVert(!n.isselectedVert());
            }
        }
    }

    //update position for move vertex radio button
    public static void relocateVertex(List<Node> list, Point d)
    {
        for (Node n : list)
        {
            if (n.isselectedVert())
            {
                n.p.x += d.x;
                n.p.y += d.y; 
                n.setBoundary(n.b);
            }
        }
    }
   
    public static void setArticulationVert()
    {
    	for(Node e: bossGraph.findarticulationPoints())
    	{
    		for(Node f: vertices)
    		{
    			if(f.equals(e))
    			{
    				f.thisArticulationVert(true);
    				break;
    			}
    			else
    				continue;
    		}    		
    	}   	
    }
       
    public boolean isArticulationVert()
    {
        return articulationVert;
    }

    public void thisArticulationVert(boolean isArticulation)
    {
        this.articulationVert = isArticulation;
    }
    
    public int getKey()
    {
        return key;
    }
    
    public void setKey(int haha)
    {
         key = haha;
    }
       
}