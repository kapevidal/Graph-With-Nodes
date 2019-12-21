/*NAME: KIRSTEN PEVIDAL
 *CLASS: CSC313
 *PROJECT: GRAPH GUI
 *DESCRIPTION: A GUI THAT LET A USER INSERT VERTICES AND EDGES IN THE GRAPH PANEL. 
 *				IT HAS SPECIFIC FEATURES (THAT WORKS MOST OF THE PART) SUCH AS FINDING CONNECTED COMPONENTS, FINDING ARTICULATION VERTEX, ETC.
 *
 *STUDENT NOTE: THE MOST CHALLENGING PART OF THIS PROJECT IS THE LAST PHASE AS IT MADE ME MODIFY ALL MY EXISTING FUNCTIONS TO MAKE IT WORK WITH THE LAST TWO FUNCTION. 
 *(THE ADD ALL EDGES REALLY MESSED UP AFTER ADDING THE CUTVERTICES AND CONNECTED COMPONENTS)
 * */

//package GG0808;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//MAINFRAME
public class GG0808 extends JComponent
{
	private  mainMenu control = new mainMenu();  // JToolbar application of menu bar so no overlapping when plotting vertices
 
    private static  List<Node> vertices = new ArrayList<Node>();//list of vertices
    private  List<Node> selectedVert = new ArrayList<Node>();// lest selected vertices for adding edges and deleting
    
    private static List<Edge> edges = new ArrayList<Edge>(); //list of edges
    
    private static Graph bossGraph = new Graph(vertices, edges);    //connected components and cut vertices
    
    private Point mousePt = new Point(); // mouse pointer location
    
    private Rectangle mouseRect = new Rectangle();// for drag selection
    
    private static boolean selecting = false; //mouse selection


    
    //GRAPHIC GUI RUN   
    public static void main(String[] args) throws Exception 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            public void run()
            {
                JFrame f = new JFrame("GraphPanel");
                GG0808 gp = new GG0808();
                
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setBounds(100, 100, 769, 335);
                f.add(gp.control, BorderLayout.WEST);
                f.add(new JScrollPane(gp), BorderLayout.CENTER);
                f.pack(); //layout help inside JFrame
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }
    
    //PROPERTY OF THE GRAPH PANEL
    public GG0808() 
    {
       this.setOpaque(true);
       this.addMouseListener(new MouseHandler());
       this.addMouseMotionListener(new MouseMotionHandler());
    }
    
    //SET DIMENSION OF GRAPH PANEL
    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(577, 300);
    }
    
   //FOR GRAPH SKETCHING IN GRAPH PANEL
    @Override
    public void paintComponent(Graphics g) 
    {
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
    private  boolean vertAdd = false;
    private  boolean edgeAdd = false;
    private  boolean vertRemove = false;
    private  boolean edgeRemove = false;
    private  boolean vertMove = false;
    
    private static  boolean cutVertex = false;
    private static  boolean connctdComponents=false;
    
    private class MouseHandler extends MouseAdapter //MOUSE ACTIONS
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
            
            else if (Node.selectOne(vertices, mousePt))
            {
                selecting = false;
            }
           
            else
            {
                Node.selectNone(vertices);
                selecting = true;
            }
             
          //MOUSE ACTION DEPENDING ON WHAT RADIO BUTTON IS SELECTED
	        if(vertAdd==true) // ADD VERTICES
	        {
	        	
	        	Node.selectNone(vertices);
	            Point p = mousePt.getLocation();
	            Node n = new Node(p);
	            vertices.add(n);
	        }
	        
	        else if (edgeAdd==true) // ADD EDGES
            {	        		        	
                Node.getselectedVert(vertices, selectedVert);
                {                	
                    for(int i = 0; i < selectedVert.size()-1; i++) 
                    {
                                       
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
                        
                        Edge hold= new Edge(selectedVert.get(i),selectedVert.get(i+1));
                        hold.vertMembers.add(selectedVert.get(i));
                        hold.vertMembers.add(selectedVert.get(i+1));
                        edges.add(hold);                                       
                        //add bossGraph.add(edge(n1,n2);
                    }
                }
                Node.selectNone(vertices);
                Node.selectOne(vertices, mousePt);
            } 
	        
	        else if (vertRemove==true) // REMOVE VERTICES
	        {        	
	    		ListIterator<Node> iter = vertices.listIterator();
	            while (iter.hasNext())
	            {
	                Node n = iter.next();
	                if (n.isselectedVert())
	                {	                	
	                    deleteEdges(n);
	                	
	                    iter.remove();
	                }
	            }               
	        }
	        
	        else if (edgeRemove==true) // REMOVE EDGES
	        {      	
	            for(Edge n: edges)
	            {
	                if (n.isselectedEd())
	                {	     	                	 
	                	 for(Node nodes: vertices)
	                	 {
	                		 if(nodes.equals(n.n1))
	                		 {
	                			 n.n2.adjacentVert.remove(nodes);
	                			if(nodes.adjacentVert.isEmpty())
	                			{
	                				for( Node sun: vertices)
	                				{
	                					if(sun.adjacentVert.contains(nodes))
	                        			{
	                        				sun.adjacentVert.remove(nodes);
	                        			}
	                				}
	                			}
	                			else if(!nodes.same(n.n2))
	                			{
	                				for( Node sun: n.n2.adjacentVert)
	                				{
	                					if(sun.adjacentVert.contains(nodes))
	                        			{
	                        				sun.adjacentVert.remove(nodes);
	                        			}
	                				}
	                				
	                				for( Node sun: nodes.adjacentVert)
	                				{
	                					if(sun.adjacentVert.contains(n.n2))
	                        			{
	                        				sun.adjacentVert.remove(n.n2);
	                        			}
	                				}
	                			}
	                			else
	                			{
	                				nodes.adjacentVert.remove(n.n2);
	                			}
	                		 }
	                		 else if(nodes.equals(n.n2))
	                		 {
	                			 n.n1.adjacentVert.remove(nodes);
		                			if(nodes.adjacentVert.isEmpty())
		                			{
		                				for( Node sun: vertices)
		                				{
		                					if(sun.adjacentVert.contains(nodes))
		                        			{
		                        				sun.adjacentVert.remove(nodes);
		                        			}
		                				}
		                			}
		                			else if(!nodes.same(n.n1))
		                			{
		                				for( Node sun: n.n1.adjacentVert)
		                				{
		                					if(sun.adjacentVert.contains(nodes))
		                        			{
		                        				sun.adjacentVert.remove(nodes);
		                        			}
		                				}
		                				
		                				for( Node sun: nodes.adjacentVert)
		                				{
		                					if(sun.adjacentVert.contains(n.n1))
		                        			{
		                        				sun.adjacentVert.remove(n.n1);
		                        			}
		                				}
		                			}
		                			else
		                			{
		                				nodes.adjacentVert.remove(n.n1);
		                			}
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
        

        private void deleteEdges(Node n) //DELETING A VERTEX WITH EDGES WILL TRIGGER THIS. THIS FUNCTION REMOVES ALL EDGES THAT HAS THE SELECTED NODE
        {      	
            ListIterator<Edge> iter = edges.listIterator();
            while (iter.hasNext())
            {
                Edge e = iter.next();
                if (e.n1.equals(n) )
                	{
                	iter.remove();
                		for(Node p: vertices)
                		{ 			
                			if(p.adjacentVert.contains(n))
                			{
                				p.adjacentVert.remove(n);
                			}
                		}
                	}    
                else if(e.n2.equals(n))
	                {
                	iter.remove();
	                	for(Node p: vertices)
                		{
	                		if(p.adjacentVert.contains(n))
                			{
                				p.adjacentVert.remove(n);
                			}
                		}
	                }                  	  
             }
            bossGraph.componentReset();
            }
        }   
    
    private class MouseMotionHandler extends MouseMotionAdapter  // HOLD LEFT CLICK FOR DRAG SELECT
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
    
 //---------------------------------------------------------------------------------------------------------------------------------

    public JToolBar getmainMenu()
    {
        return control; // MAIN MENU
    }
    
    private class mainMenu extends JToolBar // MAIN MENU IMPLEMENTED AS JTOOLBAR
    {

    	//RADIO BUTTON ACTIONEVENTS
        private Action lilNewVert =  new lilNewVertAction("Add Vertex");    
        private Action lilNewEd = new lilNewEdge("Add Edge");
        private Action removeV = new removeV("Remove Vertex");
        private Action removeE = new removeE("Remove Edge");
        private Action moveV = new moveV("Move Vertex");   
           

        //JRADIO BUTTONS
        private ButtonGroup group = new ButtonGroup();
        private JRadioButton addVertex = new JRadioButton(lilNewVert);
        private JRadioButton addEdge = new JRadioButton(lilNewEd);
        private JRadioButton removeVertex = new JRadioButton(removeV);
        private JRadioButton removeEdge = new JRadioButton(removeE);
        private JRadioButton moveVertex = new JRadioButton(moveV);
        
        
        //JBUTTONS
        private Action clearAllact = new clearAll("Clear");
        private Action addAllact = new addAll("Add All Edges");
        private Action connectCompAct = new connectedCompAction("Connected Components");
        private Action cutVertAct = new cutVerticesAction("Show Cut Vertices");
        private Action helpme = new helpAction("HELP"); 
       
        //JTOOLBAR MAINMENU INTERFACE
        mainMenu()
        {    	 
        	//GROUP ALL JRADIOBUTTONS TOGETHER (ONLY ONE JRADIOBUTTON SHOULD BE SELECTED)
     	    group.add(addVertex);
     	    group.add(addEdge);
     	    group.add(removeVertex);
     	    group.add(removeEdge);
     	    group.add(moveVertex);
     	    
     	   this.setOrientation(SwingConstants.VERTICAL);
     	  this.setFloatable(false);
    		
    		//ADD RADIOHEAD BUTTONS 
     	this.add(addVertex);
     	this.add(addEdge);
     	this.add(removeVertex);
     	this.add(removeEdge);
     	this.add(moveVertex);
          
            
            //CLICKING A JBUTTON (OTHER THAN THE HELP BUTTON) WILL DESELECT ALL THE JRADIOBUTTONS
            JButton clearbtn = new JButton(clearAllact);
              clearbtn.addActionListener(new ActionListener()
              {
            		   @Override
            		    public void actionPerformed(ActionEvent e)
            		   {
            			   bossGraph.componentReset();
            			   cutVertex = false;
            			   group.remove(addVertex);
            		 	   group.remove(addEdge);
            		 	   group.remove(removeVertex);
            		 	   group.remove(removeEdge);
            		 	   group.remove(moveVertex);
            		 	   
    	        		   addVertex.setSelected(false);
    	        		   addEdge.setSelected(false);
    	        		   removeVertex.setSelected(false);
    	        		   removeEdge.setSelected(false);
    	        		   moveVertex.setSelected(false);
            		 	   
    	        		   group.add(addVertex);
            		 	   group.add(addEdge);
            		 	   group.add(removeVertex);
            		 	   group.add(removeEdge);
            		 	   group.add(moveVertex);
            		   }
              });
              
              JButton addAllbtn = new JButton(addAllact);
              addAllbtn.addActionListener(new ActionListener()
              {
            		   @Override
            		    public void actionPerformed(ActionEvent e)
            		   {
            			   bossGraph.componentReset();
            			   cutVertex = false;
            			   group.remove(addVertex);
            		 	   group.remove(addEdge);
            		 	   group.remove(removeVertex);
            		 	   group.remove(removeEdge);
            		 	   group.remove(moveVertex);
            		 	   
    	        		   addVertex.setSelected(false);
    	        		   addEdge.setSelected(false);
    	        		   removeVertex.setSelected(false);
    	        		   removeEdge.setSelected(false);
    	        		   moveVertex.setSelected(false);
            		 	   
    	        		   group.add(addVertex);
            		 	   group.add(addEdge);
            		 	   group.add(removeVertex);
            		 	   group.add(removeEdge);
            		 	   group.add(moveVertex);
            		   }
              });
         
              
              JButton cnnctdcmpnntsBtn = new JButton(connectCompAct);
              cnnctdcmpnntsBtn.addActionListener(new ActionListener()
              {
            		   @Override
            		    public void actionPerformed(ActionEvent e)
            		   {
            			   bossGraph.componentReset();
            			   cutVertex = false;
            			   connctdComponents = true;
            			   group.remove(addVertex);
            		 	   group.remove(addEdge);
            		 	   group.remove(removeVertex);
            		 	   group.remove(removeEdge);
            		 	   group.remove(moveVertex);
            		 	   
    	        		   addVertex.setSelected(false);
    	        		   addEdge.setSelected(false);
    	        		   removeVertex.setSelected(false);
    	        		   removeEdge.setSelected(false);
    	        		   moveVertex.setSelected(false);
            		 	   
    	        		   group.add(addVertex);
            		 	   group.add(addEdge);
            		 	   group.add(removeVertex);
            		 	   group.add(removeEdge);
            		 	   group.add(moveVertex);
            		   }
              });
              
              JButton cutverticesBtn = new JButton(cutVertAct);
              cutverticesBtn.addActionListener(new ActionListener()
              {
            		   @Override
            		    public void actionPerformed(ActionEvent e)
            		   {
            			   bossGraph.componentReset();
            			   cutVertex = true;
            			   group.remove(addVertex);
            		 	   group.remove(addEdge);
            		 	   group.remove(removeVertex);
            		 	   group.remove(removeEdge);
            		 	   group.remove(moveVertex);
            		 	   
    	        		   addVertex.setSelected(false);
    	        		   addEdge.setSelected(false);
    	        		   removeVertex.setSelected(false);
    	        		   removeEdge.setSelected(false);
    	        		   moveVertex.setSelected(false);
            		 	   
    	        		   group.add(addVertex);
            		 	   group.add(addEdge);
            		 	   group.add(removeVertex);
            		 	   group.add(removeEdge);
            		 	   group.add(moveVertex);
            		   }
              });         
              JButton helpBtn = new JButton(helpme);
                      
            //ADD THE BUTTONS
            this.add((clearbtn));
            this.add(addAllbtn);
            this.add(cnnctdcmpnntsBtn);
            this.add(cutverticesBtn);
            this.add(helpBtn);    
    }
}
    private class lilNewVertAction extends AbstractAction //  ACTION TO ADD NEW VERTEX
    {
        public lilNewVertAction(String name) 
        {
            super(name);
        }   
        
        public void actionPerformed(ActionEvent e)
        {
        	bossGraph.componentReset();
        	selecting = false;
        	if(vertAdd==false)
        	{
              	vertAdd=true;
              	edgeAdd=false;
              	vertRemove=false;
        		edgeRemove=false;
              	vertMove=false;
              	
              	cutVertex = false;
              	connctdComponents = false;
        	}
        	else
        	{
              	vertAdd=false;
              	edgeAdd=false;
              	vertRemove=false;
        		edgeRemove=false;
              	vertMove=false;
              	
              	cutVertex = false;
              	connctdComponents = false;
        	}
              	repaint();	
        }
    }
    
    
    private class lilNewEdge extends AbstractAction  // ACTION TO ADD NEW EDGE
    {
        public lilNewEdge(String name) 
        {
            super(name);
        }
        
        public void actionPerformed(ActionEvent e)    
        {
        	bossGraph.componentReset();
        	selecting = false;
        	if(edgeAdd==false)
        	{
        		vertAdd=false;
        		edgeAdd=true;
        		vertRemove=false;
        		edgeRemove=false;
        		vertMove=false;
        		
        		cutVertex = false;
        		connctdComponents = false;
        	}
        	
        	else
        	{
        		vertAdd=false;
        		edgeAdd=false;
        		vertAdd=false;
        		vertRemove=false;
    			edgeRemove=false;
        		vertMove=false;
        		
        		cutVertex = false;
        		connctdComponents = false;
        	}
		repaint();
        }
    }
    
   
    private class removeV extends AbstractAction // REMOVE VERTEX
    {
    	public removeV(String name)
    	{
    		super(name);
    	}
	
    	public void actionPerformed(ActionEvent e)
    	{
    		bossGraph.componentReset();
    		if(vertRemove==false)
    		{
    			edgeAdd=false;
    			vertAdd=false;
    			vertRemove=true;
    			edgeRemove=false;
    			vertMove=false;
    			
    			cutVertex = false;
    			connctdComponents = false;
    		}
    		else
    		{
    			edgeAdd=false;
    			vertAdd=false;
    			vertRemove=false;
    			edgeRemove=false;
    			vertMove=false;
    			
    			cutVertex = false;
    			connctdComponents = false;
    		}
    		repaint();
    	}

    }
    

    private class removeE extends AbstractAction // REMOVE SELECTED EDGE
    {
        public removeE(String name)
        {
            super(name);
        }

		public void actionPerformed(ActionEvent e)
		{
			bossGraph.componentReset();
			if(edgeRemove==false)
    		{
    			edgeAdd=false;
    			vertAdd=false;
    			vertRemove=false;
    			edgeRemove=true;
    			vertMove=false;
    			
    			cutVertex = false;
    			connctdComponents = false;
    		}
    		else
    		{
    			
    			edgeAdd=false;
    			vertAdd=false;
    			vertRemove=false;
    			edgeRemove=false;
    			vertMove=false;
    			
    			cutVertex = false;
    			connctdComponents = false;
    		}
    		repaint();
		}      
    }


    private class moveV extends AbstractAction //RELOCATE SELECTED NODE
    {
    	public moveV(String name)
    	{
    		super(name);
    	}
    	
    	public void actionPerformed(ActionEvent e)
    	{
    		bossGraph.componentReset();
    		if(vertMove==false)
    		{
    			edgeAdd=false;
    			vertAdd=false;
    			vertRemove=false;
    			vertMove=true;
    			
    			cutVertex = false;
    			connctdComponents = false;
    		}
    		else
    		{			
    			edgeAdd=false;
    			vertAdd=false;
    			vertRemove=false;
    			vertMove=false;
    			
    			cutVertex = false;
    			connctdComponents = false;
    		}
    		repaint();
    	}
    }
        

    private class clearAll extends AbstractAction  //ACTION EVENT TO REMOVE ALL VERTICES AND EDGES
    {
    	
        public clearAll(String name) 
        {
            super(name);
        }
        
        public void actionPerformed(ActionEvent e)
        {
        	bossGraph.componentReset();
        	vertAdd=false;
          	edgeAdd=false;
          	vertRemove=false;
    		edgeRemove=false;
          	vertMove=false;
          	
          	cutVertex = false;
          	connctdComponents = false;
          	
        	vertices.clear();
            edges.clear();
            repaint();
            
        }
    }
    

    private class addAll extends AbstractAction     //CONNECT ALL VERTICES WITH EDGES
    {
    	
        public addAll(String name) 
        {
            super(name);
        }
        
        public void actionPerformed(ActionEvent e)
        {
        	bossGraph.componentReset();
        	vertAdd=false;
          	edgeAdd=false;
          	vertRemove=false;
    		edgeRemove=false;
          	vertMove=false;
          	
          	cutVertex = false;
          	connctdComponents = false;
          	
          	for (int i = 0; i < vertices.size(); i++) 
            {	        	
            	for(int j = i+1; j < vertices.size(); j++)
            	{
            		Node n1 = vertices.get(i);
        			Node n2 = vertices.get(j);
            		{
            			if(!n1.equals(n2))
            			{		                 
            			Edge hold= new Edge(n1,n2);
                        hold.vertMembers.add(n1);
                        hold.vertMembers.add(n2);
                        edges.add(hold);
            			           				 
	            				vertices.get(i).adjacentVert.add(vertices.get(j));
	            				vertices.get(j).adjacentVert.add(vertices.get(i));
	            				for(Node b: vertices.get(j).adjacentVert)
	            					{
	            					if(!vertices.get(i).adjacentVert.contains(b))
	            						vertices.get(i).adjacentVert.add(b);
	            					}
					            for(Node b: vertices.get(i).adjacentVert)
					                {
					                if(!vertices.get(j).adjacentVert.contains(b))				                         
					                    vertices.get(j).adjacentVert.add(b);					                   
					                }
					                     
            				 }        		
            		}
            	}
            }
      repaint();                     
        }
    }
    
    
    private class connectedCompAction extends AbstractAction // DISPLAY CONNECTED COMPONENTS IN DIFFERENT COLORS
    {
        public connectedCompAction(String name) 
        {
            super(name);
        }   
        
        public void actionPerformed(ActionEvent e)
        {
        	bossGraph.componentReset();
        	vertAdd=false;
          	edgeAdd=false;
          	vertRemove=false;
    		edgeRemove=false;
          	vertMove=false;	
          	cutVertex = false;
          	     	
          	
          	bossGraph.connectedComponents();
          	connctdComponents = true;
          	repaint();
        }
    }
    
    private class cutVerticesAction extends AbstractAction // SHOW CUT VERTICES
    {
        public cutVerticesAction(String name) 
        {
            super(name);
        }   
        
        public void actionPerformed(ActionEvent e)
        {
        	bossGraph.componentReset();
        	vertAdd=false;
          	edgeAdd=false;
          	vertRemove=false;
    		edgeRemove=false;
          	vertMove=false;	
          	cutVertex = true;
          	connctdComponents = false;
          	Node.setArticulationVert();
          	repaint();
          	
        }
    }
    
   
    private class helpAction extends AbstractAction // DISPLAY HELP MENU
    {
        public helpAction(String name) 
        {
            super(name);
        }   
        
        public void actionPerformed(ActionEvent e)
        { 
        	JFrame help = new JFrame();
    		JPanel contentPane;
    		help.setResizable(false);
    		help.setAlwaysOnTop(true);
    		help.setTitle("HELP");
    		help.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		help.setBounds(100, 100, 516, 300);
    		contentPane = new JPanel();
    		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    		help.setContentPane(contentPane);
    		contentPane.setLayout(null);
    		
    		JTextArea txtrAdd = new JTextArea();
    		txtrAdd.setBounds(5, 5, 490, 245);
    		txtrAdd.setText("Add Vertex - add vertex to the graph \r\nAdd Edge- add edge to the graph \r\nRemove Vertex - remove vertex from the graph\r\nRemove Edge - remove edge from the graph\r\nMove Vertex - reposition the selected vertex\r\n\r\nAdd All Edges - connect all vertices with one another\r\nConnected Components - show all components in the graph\r\nShow Cut Vertices - highlights the vertices that will create separate components when \t\t\t\t\tremoved\r\nHelp - instructions how to run GUI\r\n");
    		contentPane.add(txtrAdd);
    		
    		help.setVisible(true);
    	
        }
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------
    private static class Node //NODE (VERTEX) CLASS
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
        
        public boolean same(Node n1) {
			// TODO Auto-generated method stub
			return false;
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
        
        
        public static void selectNone(List<Node> list) // NO SELECTED
        {
            for (Node n : list)
            {
                n.setselectedVert(false);
            }
        }
      
        public static boolean selectOne(List<Node> list, Point p) // ONLY SELECT ONE VERTEX
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
      
        public static void selectRect(List<Node> list, Rectangle r) // DRAG SELECT (MULTIPLE SELECT)
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
       
        public static void setArticulationVert() // SET ARTICULATION VERTEX (CUT VERTICES)
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
           
        public boolean isArticulationVert() // IF VERTEX IS A CUT VERTEX
        {
            return articulationVert;
        }

        public void thisArticulationVert(boolean isArticulation) // SET IF VERTEX IS A CUT VERTEX
        {
            this.articulationVert = isArticulation;
        }
        
        public int getKey() // GET KEY
        {
            return key;
        }
        
        public void setKey(int newKey)
        {
             key = newKey;
        }
           
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------    
    private static class Edge
    {
        public Node n1;
        public Node n2;
        
        private int key = 0;
        public List<Node> vertMembers = new ArrayList<Node>(); // VERTICES MEMBERS OF THE EDGE
         
        private boolean selectedEd = false; // if edge is selected
        
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
            
            if(selectedEd) // for testing only! Does not really shows up UNLESS DELETE ACTION IS NOT WORKING
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
        public static void selectToggle(List<Edge> list, Point p) // MULTIPLE SELECT (NOT REALLY USABLE AT THIS PROGRAM I JUST PUT IT JUST IN CASE)
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
//---------------------------------------------------------------------------------------------------------------------------------------------------------    
    private static class Graph // GRAPH CLASS
    {	
    	private static List<Edge> edgeComponents = new ArrayList<Edge>(); 
    	private static List<Node> nodeComponents = new ArrayList<Node>();
    	
    	Graph(List<Node> vertices, List<Edge> edges)
    	{ 	
    		edgeComponents = edges;
    		nodeComponents = vertices;
    	}
    		 
    	    private static int time; 	 	
    	    public static List<Node> findarticulationPoints() // FIND CUT VERTEX 
    	    { 
    	        time = 0; 
    	        Set<Node> visitedNodes = new HashSet<>(); 
    	        List<Node> articulationPoints = new ArrayList<Node>(); 
    	        
    	        Map<Node, Integer> visitedNodesTime = new HashMap<>(); 
    	        Map<Node, Integer> lowTime = new HashMap<>(); 
    	        Map<Node, Node> parentNodes = new HashMap<>(); 
    	        
    	        Node startVertex=null;
    	        for(Node e: nodeComponents)
    	        {       	
    	        if(e!=null)
    	         startVertex= e;
    	        } // FIRST ITEM IN THE LIST OF VERTICES
    	
    	        DFS(visitedNodes,articulationPoints,startVertex, visitedNodesTime, lowTime, parentNodes); 
    	        
    	        return articulationPoints; 
    	    } 
    	 
    	    public static void DFS(Set<Node> visitedNodes, //MY ATTEMPT FOR DEPTH FIRST SEARCH RECURSIVE METHOD TO FIND CUT VERTICES 
    	            List<Node> articulationPoints, Node vertex, 
    	            Map<Node, Integer> visitedNodesTime, 
    	            Map<Node, Integer> lowTime,
    	            Map<Node, Node> parentNodes) 
    	    { 
    	        visitedNodes.add(vertex); 
    	        visitedNodesTime.put(vertex, time); 
    	        lowTime.put(vertex, time); 
    	        time++; 
    	        int childCount =0; 
    	        boolean isArticulationPoint = false; 
    	        for(Node adj : vertex.adjacentVert)
    	        { 
    	            if(adj.equals(parentNodes.get(vertex)))
    	            { 
    	                continue; 
    	            } 

    	            if(!visitedNodes.contains(adj))
    	            { 
    	                parentNodes.put(adj, vertex); 
    	                childCount++; 
    	                DFS(visitedNodes, articulationPoints, adj, visitedNodesTime, lowTime, parentNodes); 
    	                if(visitedNodesTime.get(vertex) <= lowTime.get(adj))
    	                { 
    	                    isArticulationPoint = true; 
    	                }	                
    	                else
    	                { 
    	                    lowTime.computeIfPresent(vertex, (currentVertex, time) ->  Math.min(time, lowTime.get(adj))); 
    	                } 	 
    	            }             
    	            else
    	            {  
    	              lowTime.computeIfPresent(vertex, (currentVertex, time) -> Math.min(time, visitedNodesTime.get(adj))); 
    	            } 
    	        } 
    	        
    	        if((parentNodes.get(vertex) == null && childCount >= 2) || parentNodes.get(vertex) != null && isArticulationPoint )
    	        { 
    	            articulationPoints.add(vertex); 
    	        } 
    	         
    	    } 
    	    
    	    public void connectedComponents() // VERY LONG METHOD OF CONNECTED COMPONENTS
    	    {
    	    	componentReset();
    	    	Random rnd = new Random();
        		if(!edgeComponents.isEmpty())
        		{
    	    	    	for(Node nude: nodeComponents)
    	    	    	{ 		
    	    	    		for(Edge egg: edgeComponents)
    	    	    			{            	
    	    	            		{
    	    	            			int r = rnd.nextInt();    	            	
    	    	            			if(egg.vertMembers.contains(nude))
    	    	            				{
    	    	            					if(egg.node1().getKey()==0)
    	    	            						{
    	    	            							for(Node n : nodeComponents)
    	    	            								{
    	    	            									if(egg.node1().equals(n))
    	    	            										{
    	    	            											n.setKey(r);
    	    	            											
    	    	            											for(Node m : n.adjacentVert)
    	    	            											{
    	    	            												m.setKey(r);
    	    	            										
    	    	            												for(Edge p: edgeComponents)
    	    	            												{
    	    	            													if(p.vertMembers.contains(m))
    	    	            													{
    	    	            														p.setKey(r);
    	    	            													}
    	    	            												}
    	    	            											}
    	    	            										}
    	    	            								}	    							
    	    	            						}
    	    	            					if(egg.node2().getKey()==0)
    	    	            						{	            				
    	    	            							for(Node n : nodeComponents)
    	    	            							{
    	    	            								if(egg.node2().equals(n))
    	    	            								{
    	    	            									n.setKey(r);	    	            									
    	    	            									for(Node m : n.adjacentVert)
    	    	            										{
    	    	            											m.setKey(r);    	            											
    	    	            											for(Edge p: edgeComponents)
    	    	            												{
    	    	            													if(p.vertMembers.contains(m))
    	    	            													{
    	    	            														p.setKey(r);
    	    	            													}
    	    	            												}
    	    	            										}
    	    	            								}
    	    	            							}
    	    	            						}
    	    	            				}
    	    	            			else
    	    	            			{continue;}
    	    	            	}
    	    	            }
    	    	          }
    	    	   }	
    		else 
    		{	
    			for(Node b: nodeComponents)
    			{
    				b.setKey(rnd.nextInt());
    			}
    		}
        	for(Node b: nodeComponents)
        	{
        		if(b.adjacentVert.isEmpty()||b.getKey()==0)
        		{
        			b.setKey(rnd.nextInt());
        		}
        	}
        }
    	    	    	
        public void componentReset()
    	    {
    	    	for(Node n : nodeComponents)
    			{
    				n.setKey(0);
    				n.thisArticulationVert(false);
    			}	
    	    	
    	    	for(Edge n : edgeComponents)
    			{
    				n.setKey(0);
    			}	    	
    	    } 
    	
    } 
    
//---------------------------------------------------------------------------------------------------------------------------------------------------------

 
	

}
