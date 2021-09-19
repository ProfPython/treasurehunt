import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.util.*;

/*
 *  The main window of the gui.
 *  Notice that it extends JFrame - so we can add our own components.
 *  Notice that it implements ActionListener - so we can handle user input.
 *  This version also implements MouseListener to show equivalent functionality (compare with the other demo).
 *  @author mhatcher
 */
public class WindowDemo extends JFrame implements ActionListener, MouseListener
{
	// gui components that are contained in this frame:
	private JPanel topPanel, bottomPanel;	// top and bottom panels in the main window
	private JLabel instructionLabel;		// a text label to tell the user what to do
	private JLabel infoLabel;            // a text label to show the coordinate of the selected square
	private JLabel infoLabel2;
    private JButton topButton;				// a 'reset' button to appear in the top panel
	private GridSquare [][] gridSquares;
    // squares to appear in grid formation in the bottom panel
	private int rows,columns;				// the size of the grid
	public boolean isFirstClick = true;
    Treasure gold = new Treasure();
    private boolean onePlaysNext = true;
	private int counter = (int)(Math.random()*2);

	/*
	 *  constructor method takes as input how many rows and columns of gridsquares to create
	 *  it then creates the panels, their subcomponents and puts them all together in the main frame
	 *  it makes sure that action listeners are added to selectable items
	 *  it makes sure that the gui will be visible
	 */
	public WindowDemo(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		this.setSize(600,600);
		
		// first create the panels
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(rows, columns));
		bottomPanel.setSize(500,500);
		
		// then create the components for each panel and add them to it
		
		// for the top panel:
		instructionLabel = new JLabel("Find the treasure!");
        infoLabel = new JLabel("No square clicked yet.");
		infoLabel2 = new JLabel("count");
		topButton = new JButton("New Game");
		topButton.addActionListener(this);			// IMPORTANT! Without this, clicking the square does nothing.
		
		topPanel.add(instructionLabel);
		topPanel.add (topButton);
        topPanel.add(infoLabel);
		topPanel.add(infoLabel2);
		
	
		// for the bottom panel:	
		// create the squares and add them to the grid
		gridSquares = new GridSquare[rows][columns];
		for ( int x = 0; x < columns; x ++)
		{
			for ( int y = 0; y < rows; y ++)
			{
				gridSquares[x][y] = new GridSquare(x, y);
				gridSquares[x][y].setSize(5, 5);
				gridSquares[x][y].setColor();
				
				gridSquares[x][y].addMouseListener(this);		// AGAIN, don't forget this line!
				
				bottomPanel.add(gridSquares[x][y]);
			}
		}
		
		// now add the top and bottom panels to the main frame
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(bottomPanel, BorderLayout.CENTER);		// needs to be center or will draw too small
		
		// housekeeping : behaviour
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
        gold.hideTreasure();
	}
	
	
	/*
	 *  handles actions performed in the gui
	 *  this method must be present to correctly implement the ActionListener interface
	 */
	public void actionPerformed(ActionEvent aevt)
	{
		// get the object that was selected in the gui
		Object selected = aevt.getSource();
		
		// if resetting the squares' colours is requested then do so
		if ( selected.equals(topButton) )
		{
			for ( int x = 0; x < columns; x ++)
			{
				for ( int y = 0; y < rows; y ++)
				{
					gridSquares [x][y].setColor();
					gridSquares [x][y].setText("");
					gridSquares [x][y].setEnabled(true);
					gold.setFound(true);
					gold.hideTreasure();
				}
			}
		}

	}
	public void allDisabled(){
		for ( int x = 0; x < columns; x ++)
			{
				for ( int y = 0; y < rows; y ++)
				{
					gridSquares [x][y].setEnabled(false);
				}
			}
	}

	
	
	public void switchPlayer() {
		onePlaysNext = !onePlaysNext;
	}
	

	// Mouse Listener events
	public void mouseClicked(MouseEvent mevt)
	{
        Object selected = mevt.getSource();
        GridSquare square = (GridSquare) selected;
			if(counter%2 == 0 && square.getBackground()!=Color.RED)
			{
				infoLabel2.setText("Player's one turn");
				if (square.getBackground()==Color.GRAY && gold.isFound()==false){
					square.switchColor();
				}
				int x = square.getXcoord();
				int y = square.getYcoord();
				int dist11 = gold.treasureStatus(x, y);
				if (dist11 == 0 ){
					gold.setFound(true);
					infoLabel.setText("Player 1 won");
					allDisabled();
					//topButton.doClick();
				}else if (gold.isFound()== false){
					square.setText(Integer.toString(dist11));
					square.setEnabled(false);
				}
			}
			else if(counter%2 == 1 && square.getBackground()!=Color.BLACK)
			{        
				infoLabel2.setText("Player's two turn");
				if (square.getBackground()==Color.GRAY && gold.isFound()== false){
					square.switchColor2();
				}
				int x = square.getXcoord();
				int y = square.getYcoord();
				int dist11 = gold.treasureStatus(x, y);
				if (dist11 == 0 ){
					gold.setFound(true) ;
					infoLabel.setText("Player 2 won");
					allDisabled();
					//topButton.doClick();
				}else if (gold.isFound()== false){
					square.setText(Integer.toString(dist11));
					square.setEnabled(false);
					
				}
			}
			counter++;
		}
		/*
		if (selected instanceof GridSquare)
		{
            GridSquare square = (GridSquare) selected;
			square.switchColor();
            square.setEnabled(false);
            int x = square.getXcoord();
            int y = square.getYcoord();
         
            
		}
	*/


	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
}
