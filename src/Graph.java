import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
 
public class Graph //extends GGxxxx
{	
	private static List<Edge> edgeComponents = new ArrayList<Edge>();
	private static List<Node> nodeComponents = new ArrayList<Node>();
	
	Graph(List<Node> vertices, List<Edge> edges)
	{ 	
		edgeComponents = edges;
		nodeComponents = vertices;
	}
		 
	    private static int time; 	 	
	    public static List<Node> findarticulationPoints()
	    { 
	        time = 0; 
	        Set<Node> visitedNodes = new HashSet<>(); 
	        List<Node> articulationPoints = new ArrayList<Node>(); 
	        
	        Map<Node, Integer> visitedNodesTime = new HashMap<>(); 
	        Map<Node, Integer> lowTime = new HashMap<>(); 
	        Map<Node, Node> parentNodes = new HashMap<>(); 
	        
	       // Node startVertex=nodeComponents.get(1);
	        		Node startVertex=null;
	        for(Node e: nodeComponents)
	        {       	
	        if(e!=null)
	         startVertex= e;
	        }
	        	
	        DFS(visitedNodes,articulationPoints,startVertex, visitedNodesTime, lowTime, parentNodes); 
	        
	        visitedNodesTime.clear();
        	lowTime.clear();
        	parentNodes.clear();
	        
	        return articulationPoints; 
	    } 
	 
	    public static void DFS(Set<Node> visitedNodes, 
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
	    
	    public void connectedComponents()
	    {
	    	componentReset();
	    	Random rnd = new Random();
    		if(!edgeComponents.isEmpty())
    		{
	    	    	for(Node nude: nodeComponents)
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
	    	            											//egg.setKey(r);
	    	            											for(Node m : n.adjacentVert)
	    	            											{
	    	            												m.setKey(r);
	    	            												//egg.setKey(r);
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