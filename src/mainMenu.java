import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;


public class mainMenu extends actionsEvent
{
	private JToolBar groupy = new JToolBar();
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
 	    
 	    
       // this.setLayout(new FlowLayout(FlowLayout.LEFT));
       // this.setBackground(Color.lightGray);
      	groupy.setOrientation(SwingConstants.VERTICAL);
		groupy.setFloatable(false);
		
		//ADD RADIOHEAD BUTTONS 
        groupy.add(addVertex);
        groupy.add(addEdge);
        groupy.add(removeVertex);
        groupy.add(removeEdge);
        groupy.add(moveVertex);
      
        
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
        groupy.add((clearbtn));
        groupy.add(addAllbtn);
        groupy.add(cnnctdcmpnntsBtn);
        groupy.add(cutverticesBtn);
        groupy.add(helpBtn);    
    }
    
    //RETURN JTOOLBAR TO THE MAINFRAME
    public JToolBar getToolsy()
    {
    	return groupy;
    }
    
}