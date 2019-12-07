import java.util.List;
import java.util.Random;

public class test 
{

	 private List<Node> nodeComponents;
	 private List<Edge> edgeComponents;
	 
    public void connectedComponents()
    {
    	Random rnd = new Random();
    	for(Node nude: nodeComponents)
    	{
    		if(edgeComponents.isEmpty())
    		{
    			for(Node b: nodeComponents)
    			{
    				b.setKey(rnd.nextInt());
    			}
    		}
    		
    		else
    		{
    			for(Edge egg: edgeComponents)
    			{            	
            	//if(j<edgeComponents.size())
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
            						egg.setKey(r);
            						for(Node m : n.adjacentVert)
            						{
            							m.setKey(r);
	            						egg.setKey(r);
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
            						egg.setKey(r);
            						for(Node m : n.adjacentVert)
            						{
            							m.setKey(r);
	            						egg.setKey(r);
	            						m.setKey(r);
	            						egg.setKey(r);
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
            	}
            }
            }
    		for(Node e: nodeComponents)
    		{
    			int rn = rnd.nextInt();
    			if(e.adjacentVert.isEmpty())
    			{
    				e.setKey(rn);
    			}
    		}
    	}
    }	
	
	
	
	
}
