import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

public class actionsEvent extends GGxxxx
{   

    //CONDITION TO ADD A NEW LIL VERTex
    public class lilNewVertAction extends AbstractAction
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
    
    //CONDITION TO ADD A NEW EDge
    public class lilNewEdge extends AbstractAction 
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
    
    //CONDITION TO REMOVE A VERTEX
    public class removeV extends AbstractAction
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
    
    //CONDITION TO REMOVE SELECTED EDGE
    public class removeE extends AbstractAction
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

    //REPOSITION A SELECTED VERTEX
    public class moveV extends AbstractAction
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
        
  //ACTIONEVENT TO REMOVE ALL VERTICES AND EDGES
    public class clearAll extends AbstractAction
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
    
    //CONNECT ALL VERTICES WITH EDGES
    public class addAll extends AbstractAction
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
            	
            	for(int j = 0; i < vertices.size(); j++)
            	{
            		if(j<vertices.size())
            		{
            			Node n1 = vertices.get(i);
            			Node n2 = vertices.get(j);
            		                 
            			Edge hold= new Edge(n1,n2);
                        hold.vertMembers.add(n1);
                        hold.vertMembers.add(n2);
                       // for(Edge lord: edges)
                        //{  	if(!lord.vertMembers.contains(n1) && !lord.vertMembers.contains(n2))
                        edges.add(hold);
            			
            			if(!n1.equals(n2))
            				{ 
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
            		else break;
            	}
            }
            repaint();          
        }
    }
    
    
    public class connectedCompAction extends AbstractAction
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
    
    public class cutVerticesAction extends AbstractAction
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
    
    //DISPLAY HELP BUTTON
    public class helpAction extends AbstractAction
    {
        public helpAction(String name) 
        {
            super(name);
        }   
        
        public void actionPerformed(ActionEvent e)
        {
        	showHelp.setVisible(true);	
        }
    }

}