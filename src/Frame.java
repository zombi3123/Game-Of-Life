import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Frame extends JPanel implements MouseListener, ActionListener, MouseMotionListener {
    private ArrayList<Cell> columns;
    private ArrayList<ArrayList<Cell>> rows;
    private int cellLength;
    private Color dead;
    private Timer tm;
    private JButton Start,stop,randomize;
    private Random randNum;
    public Frame(){
        tm=new Timer(10,this);
        rows=new ArrayList<>();
        columns= new ArrayList<>();
        this.setSize(new Dimension(1750, 1000));
        this.cellLength=10;
        this.dead=Color.BLACK;

        randNum=new Random();
        Start=new JButton("Start");
        stop=new JButton("Stop");
        randomize=new JButton("Randomize");
        add(Start);
        add(stop);
        add(randomize);
        Start.setBounds(0,0,50,25);
        stop.setBounds(0,50,50,25);
        randomize.setBounds(50,0,50,25);
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tm.start();
                Start.setVisible(false);
               // Start.setEnabled(false);
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tm.stop();
                Start.setEnabled(true);
                Start.setVisible(true);
            }
        });
        randomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < rows.size(); i++) {
                    for (int j = 0; j < rows.get(0).size(); j++){
                        Cell c=rows.get(i).get(j);
                        float a=randNum.nextFloat();
                        if (a>0.9) {
                            c.setAlive();
                        }
                    }
                }
                repaint();
            }
        });
        repaint();
        createMap();
        //startLife()
        //addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);

        //initComponents();
    }

    private void initComponents() {
    }

    public void createMap(){
            for(int columsi=0;columsi<getHeight()/cellLength;columsi++){
                ArrayList<Cell> columns = new ArrayList<>();
                for(int rowsi=0;rowsi<getWidth()/cellLength;rowsi++){

                    Cell c=new Cell(rowsi*(cellLength+1),columsi*(cellLength+1),cellLength,false);
                    columns.add(c);
                    repaint();
                }
                rows.add(columns);
        }
        System.out.println(columns.size());


        System.out.println(rows.get(3).size());
    }
    public void startLife(){



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(0).size(); j++){
                Cell c=rows.get(i).get(j);
                if (c.isAlive()) {
                    g.setColor(Color.yellow);
                } else {
                    g.setColor(Color.magenta);
                }
            c.draw(g);
        }
    }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Cell> deadCells=new ArrayList<Cell>();
        ArrayList<Cell> bornCells=new ArrayList<Cell>();
        for (int i = 1; i < rows.size()-1; i++) {
            for (int j = 1; j < rows.get(0).size()-1; j++){
                Cell c=rows.get(i).get(j);
                Cell cE=rows.get(i).get(j+1);
                Cell cW=rows.get(i).get(j-1);
                Cell cN=rows.get(i-1).get(j);
                Cell cS=rows.get(i+1).get(j);
                Cell cNE=rows.get(i-1).get(j+1);
                Cell cNW=rows.get(i-1).get(j-1);
                Cell cSE=rows.get(i+1).get(j+1);
                Cell cSW=rows.get(i+1).get(j-1);
                if(c.isAlive()){
                if(c.totalNeighbours(cE,cW,cN,cS,cNE,cNW,cSE,cSW)>3){deadCells.add(c);}
                if(c.totalNeighbours(cE,cW,cN,cS,cNE,cNW,cSE,cSW)<2){deadCells.add(c);}
            }
                else{
                    if(c.totalNeighbours(cE,cW,cN,cS,cNE,cNW,cSE,cSW)==3){bornCells.add(c);}
                }
                repaint();

            }

        }
        for(Cell c:deadCells){c.setDead();}
        for(Cell c:bornCells){c.setAlive();}



    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (int i = 1; i < rows.size()-1; i++) {
            for (int j = 1; j < rows.get(0).size()-1; j++){
                Cell c=rows.get(i).get(j);
                if(e.getX()>=c.getTlx()&&e.getX()<c.getTlx()+c.getCellLength()&&e.getY()>= c.getTly()&&e.getY()<c.getTly()+c.getCellLength()){
                    c.setAlive();
                    repaint();
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
