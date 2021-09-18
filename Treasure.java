public class Treasure {

    public int POSITIONS_PER_ROW = 4;
    private String name;
    private int xLocation;
    private int yLocation;
    private Boolean found;


    public Treasure(){
     name = "";
     xLocation = 0;
     yLocation = 0;
     found = false;
    }
/*
    public Treasure(String name){
        this.name = name;
        xLocation = 0;
        yLocation = 0;
        found = false;
    }*/

    public int getXLocation(){
        return xLocation;
    }

    public int getYLocation(){
        return yLocation;
    }

    
    public void hideTreasure(){
        this.found = false;
        xLocation = (1+(int)(Math.random()*POSITIONS_PER_ROW));
        yLocation = (1+(int)(Math.random()*POSITIONS_PER_ROW));
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public boolean isFound(){
        return found; 
    }

      
    public int treasureStatus(int xStick , int yStick){
     if (xStick == xLocation && yStick == yLocation){
        return 0;
    } else if ((xStick != xLocation || yStick != yLocation)||(xStick != xLocation && yStick != yLocation)) {
        return Math.abs((yStick-yLocation)+(xStick-xLocation));  
        }     
    else{return 0;}}



}





