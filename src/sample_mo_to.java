
/*public class sample_mo_to
{
	
	 
    public void connectedComponents()
    {
    	Random rnd = new Random();
    	for(int i = 0; i < nodeComponents.size();i++)
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
            for(int j = 0; j < edgeComponents.size();j++)
            {            	
            	//if(j<edgeComponents.size())
            	{
            	int r = rnd.nextInt();
            	
            	if(edgeComponents.get(j).vertMembers.contains(nodeComponents.get(i)))
            		{
            			if(edgeComponents.get(j).node1().getKey()==0)
            			{
            				for(Node n : nodeComponents)
            				{
            					if(edgeComponents.get(j).node1().equals(n))
            					{
            						n.setKey(r);
            						edgeComponents.get(j).setKey(r);
            						for(Node m : n.adjacentVert)
            						{
            							m.setKey(r);
	            						edgeComponents.get(j).setKey(r);
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
            			if(edgeComponents.get(j).node2().getKey()==0)
            			{	            				
            				for(Node n : nodeComponents)
            				{
            					if(edgeComponents.get(j).node2().equals(n))
            					{
            						n.setKey(r);
            						edgeComponents.get(j).setKey(r);
            						for(Node m : n.adjacentVert)
            						{
            							m.setKey(r);
	            						edgeComponents.get(j).setKey(r);
	            						m.setKey(r);
	            						edgeComponents.get(j).setKey(r);
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
    }*/


