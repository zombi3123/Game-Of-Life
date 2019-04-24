import java.awt.*;
import java.util.ArrayList;

public class Cell {
    private int cellLength,tlx,tly;
    private Color color;
    private boolean alive;
    public Cell(int tlx,int tly,int cellLength,boolean alive){
        this.cellLength=cellLength;
        this.alive=alive;
        this.color=color;
        this.tlx=tlx;
        this.tly=tly;
    }
    public int getTlx(){return this.tlx;}

    public int getTly(){return this.tly;}

    public void setAlive() {
        this.alive = true;
    }

    public void setDead() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getCellLength() {
        return cellLength;
    }

    public int totalNeighbours(Cell c1,Cell c2,Cell c3,Cell c4,Cell c5,Cell c6,Cell c7,Cell c8){
        int liveNeighbours=0;
        ArrayList<Cell> neighborslist=new ArrayList();
        neighborslist.add(c1);
        neighborslist.add(c2);
        neighborslist.add(c3);
        neighborslist.add(c4);
        neighborslist.add(c5);
        neighborslist.add(c6);
        neighborslist.add(c7);
        neighborslist.add(c8);
        for(Cell c:neighborslist){
            if(c.isAlive()){
                liveNeighbours+=1;
            }
        }
        return liveNeighbours;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(tlx,tly,cellLength,cellLength);
    }
}
