import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.*;

//MAINFRAME

/**
 * 
 * fix vertex remove*/
public class GGxxxx extends JPanel
{

	private static mainMenu control = new mainMenu();  
    
    public static List<Node> vertices = new ArrayList<Node>();
    public static List<Node> selectedVert = new ArrayList<Node>();
    
    public static List<Edge> edges = new ArrayList<Edge>();
    
    //connected components and cut vertices
    public static Graph bossGraph = new Graph(vertices, edges);
    
    public Point mousePt = new Point();
    
    public Rectangle mouseRect = new Rectangle();
    
    public boolean selecting = false;

    public help showHelp = new help();
    
    //GRAPHIC GUI RUN   
    public static void main(String[] args) throws Exception 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            public void run()
            {
                JFrame f = new JFrame("GraphPanel");
                f.setBounds(100, 100, 769, 335);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GGxxxx gp = new GGxxxx();
                f.add(control.getToolsy(), BorderLayout.WEST);
                f.add(new JScrollPane(gp));
                f.pack(); //layout help inside JFrame
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }
    //MAIN CLASS
    public GGxxxx() 
    {
        this.setOpaque(true);
        this.addMouseListener(new MouseHandler());
       this.addMouseMotionListener(new MouseMotionHandler());
    }
    //SET DIMENSION OF GUI
    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(577, 300);
    }
    
   //FOR GRAPHING
    @Override
    public void paintComponent(Graphics g) 
    {
    	super.paintComponent(g);
    	repaint();
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Edge e : edges)
        {
            e.draw(g);
        }
        for(Node n : vertices)
        {
            n.draw(g);
        }
        
        if(selecting) 
        {
            g.setColor(Color.PINK);
            g.drawRect(mouseRect.x, mouseRect.y,
                mouseRect.width, mouseRect.height);
        }
    }
    
    //CONDITIONS FOR MOUSE ACTIONS (radio buttons)
    public static boolean vertAdd = false;
    public static boolean edgeAdd = false;
    public static boolean vertRemove = false;
    public static boolean edgeRemove = false;
    public static boolean vertMove = false;
    
    public static boolean cutVertex = false;
    public static boolean connctdComponents=false;
    //MOUSE ACTIONS
    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {
            selecting = true;
            mouseRect.setBounds(0, 0, 0, 0);
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            mousePt = e.getPoint();
            
            if (edgeAdd==true)
            {
                Node.selectToggle(vertices, mousePt);
            } 
            
            if (edgeRemove==true)
            {
                Edge.selectOne(edges, mousePt);
            } 
            
             if (e.isPopupTrigger())
            {
                Node.selectOne(vertices, mousePt);
            }
             
            else if (Node.selectOne(vertices, mousePt))
            {
                selecting = false;
            }
           
            else
            {
                Node.selectNone(vertices);
                selecting = true;
            }
             
            //mouse action depending on what radiobutton is active
	        if(vertAdd==true)
	        {
	        	
	        	Node.selectNone(vertices);
	            Point p = mousePt.getLocation();
	            Node n = new Node(p);
	           // n.setselectedVert(true);
	            vertices.add(n);
	        }
	        
	        else if (edgeAdd==true)
            {	        		        	
                Node.getselectedVert(vertices, selectedVert);
                //if (selectedVert.size() > 1) 
                {
                	//Iterator<Node> n = selectedVert.listIterator();
                	
                    for(int i = 0; i < selectedVert.size()-1; i++) 
                    {
                        Node n1 = selectedVert.get(i);
                        
                        Node n2 = selectedVert.get(i + 1);
                        
                        selectedVert.get(i).adjacentVert.add(selectedVert.get(i+1));
                        selectedVert.get(i+1).adjacentVert.add(selectedVert.get(i));
                        for(Node b:  selectedVert.get(i+1).adjacentVert)
                        {
                        	if(! selectedVert.get(i).adjacentVert.contains(b))
                        		 selectedVert.get(i).adjacentVert.add(b);
                        }
                        for(Node b:  selectedVert.get(i).adjacentVert)
                        {
                        	if(! selectedVert.get(i+1).adjacentVert.contains(b))
                            
                        		 selectedVert.get(i+1).adjacentVert.add(b);
                        }
                        
                        Edge hold= new Edge(n1,n2);
                        hold.vertMembers.add(n1);
                        hold.vertMembers.add(n2);
                        edges.add(hold);                                       
                        //add bossGraph.add(edge(n1,n2);
                    }
                }
                Node.selectNone(vertices);
                Node.selectOne(vertices, mousePt);
            } 
	        
	        else if (vertRemove==true)
	        {        	
	    		ListIterator<Node> iter = vertices.listIterator();
	            while (iter.hasNext())
	            {
	                Node n = iter.next();
	                if (n.isselectedVert())
	                {	                	
	                    deleteEdges(n);
	                	
	                    iter.remove();
	                  //remove bossGraph.remove(edge(n1,n2);
	                }
	            } 
	                
	        }
	        
	        else if (edgeRemove==true)
	        {      	
	            for(Edge n: edges)
	            {
	                if (n.isselectedEd())
	                {	     
	                	 
	                	 for(Node nodes: vertices)
	                	 {
	                		 if(nodes.equals(n.n1))
	                		 {
	                			 if(nodes.adjacentVert.isEmpty())
	                					 nodes.adjacentVert.remove(n.n2);
	                			 break;
	                		 }
	                		 else if(nodes.equals(n.n2))
	                		 {
	                			 if(nodes.adjacentVert.isEmpty())
	                			 nodes.adjacentVert.remove(n.n1);
	                			 break;
	                		 }
	                	 }   
	                	 n.vertMembers.remove(n.n1);
	                	 n.vertMembers.remove(n.n2); 
	                	 edges.remove(n);
	                   break;	                  	                   
	                }
	            } 
	        }	    
	        e.getComponent().repaint(); 
        }

        private void deleteEdges(Node n)
        {      	
            ListIterator<Edge> iter = edges.listIterator();
            while (iter.hasNext())
            {
                Edge e = iter.next();
                if (e.node1().equals(n) )
                	{
                		iter.remove();
                		for(Node p: vertices)
                		{
                			if(p.equals(e.node1()))
                			{
                				p.adjacentVert.remove(e.node2());
                			}
                		}
                	}
                	//e.node();             
                else if(e.node2().equals(n))
	                {
	                	iter.remove();
	                	for(Node p: vertices)
                		{
                			if(p.equals(e.node2()))
                			{
                				p.adjacentVert.remove(e.node1());
                			}
                		}
	                }                  	  
             }
            bossGraph.componentReset();
            }
        }   
    
    //hold click button then drag then releaSe
    private class MouseMotionHandler extends MouseMotionAdapter
    {
        Point newP = new Point();      
        @Override
        public void mouseDragged(MouseEvent e) 
        {
            if (selecting)
            {
                mouseRect.setBounds(
                    Math.min(mousePt.x, e.getX()),
                    Math.min(mousePt.y, e.getY()),
                    Math.abs(mousePt.x - e.getX()),
                    Math.abs(mousePt.y - e.getY()));
                
                Node.selectRect(vertices, mouseRect);
            }
            //for moving vertices
            else if(vertMove==true)
            {
                newP.setLocation(
                    e.getX() - mousePt.x,
                    e.getY() - mousePt.y);
                Node.relocateVertex(vertices, newP);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }   
}