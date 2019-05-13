import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Frame extends JPanel implements MouseListener, ActionListener, MouseMotionListener {
    private ArrayList<Cell> columns;
    private ArrayList<ArrayList<Cell>> rows;
    private int cellLength,JFrameWidth,JFrameHeight;
    private Color dead,killDefaultColor;
    private Timer tm;
    private int border;
    private JButton Start,stop,randomize,kill,step,reset,drawLine;
    private Random randNum;
    private boolean killCells,drawLineMode;
    public Frame(int JFrameWidth,int JFrameHeight){
        tm=new Timer(1,this);
        rows=new ArrayList<>();
        columns= new ArrayList<>();
        this.cellLength=3;
        this.dead=Color.BLACK;
        this.border=1;
        this.JFrameWidth=JFrameWidth;
        this.JFrameHeight=JFrameHeight;
        this.killCells=false;
        this.drawLineMode=false;
        this.setSize(new Dimension(this.JFrameWidth, this.JFrameHeight));
        randNum=new Random();
        Start=new JButton("Start");
        stop=new JButton("Stop");
        randomize=new JButton("Randomize");
        kill=new JButton("Kill Cells");
        step=new JButton("Step");
        reset=new JButton("Reset");
        drawLine =new JButton("Line");
        ArrayList<JButton> JB = new ArrayList<>();
        JB.add(Start);
        JB.add(stop);
        JB.add(randomize);
        JB.add(kill);
        JB.add(step);
        JB.add(reset);
        JB.add(drawLine);
        int i=50;
        for (JButton j:JB){
            j.setBounds(i*2,0,50,20);
            this.add(j);
            i+=10;
        }
        killDefaultColor=kill.getBackground();
        Color killActiveColour=Color.green;
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
                for (int i = 1; i < rows.size()-1; i++) {
                    for (int j = 1; j < rows.get(0).size()-1; j++){
                        Cell c=rows.get(i).get(j);
                        float a=randNum.nextFloat();
                        if (a>0.90) {
                            c.setAlive();
                        }
                    }
                }
                repaint();
            }
        });
        kill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              killCells=!killCells;
              if (!killCells) {
                    kill.setBackground(killDefaultColor);
                }
              else kill.setBackground(killActiveColour);
            }
        });
        step.addActionListener(new ActionListener() {
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
                            if(c.totalNeighbours(cE,cW,cN,cS,cNE,cNW,cSE,cSW)>=4){deadCells.add(c);}
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
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < rows.size()-1; i++) {
                    for (int j = 0; j < rows.get(0).size()-1; j++){
                        Cell c=rows.get(i).get(j);
                        c.setDead();
                    }
                }
                repaint();
            }
        });
        drawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLineMode = !drawLineMode;
                if (drawLineMode) {
                    killCells=false;
                    drawLine.setBackground(killActiveColour);
                }
                else{
                    drawLine.setBackground(killDefaultColor);
                }
            }
        });
        repaint();
        createMap();
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
    }

    private void initComponents() {
    }

    public void createMap(){
        //Draw grid. Initialize all cells to dead
            for(int columsi=0;columsi<getHeight()/cellLength;columsi++){
                ArrayList<Cell> columns = new ArrayList<>();
                for(int rowsi=0;rowsi<getWidth()/cellLength;rowsi++){

                    Cell c=new Cell(rowsi*(cellLength+border),columsi*(cellLength+border),cellLength,false);
                    columns.add(c);
                    repaint();
                }
                rows.add(columns);
        }

        System.out.println(rows.size()*rows.get(0).size());
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(0).size(); j++){
                Cell c=rows.get(i).get(j);
                if (c.isAlive()) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.white);
                }
            c.draw(g);
        }
    }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (killCells){
        for (int i = 1; i < rows.size()-1; i++) {
            for (int j = 1; j < rows.get(0).size()-1; j++){
                Cell c=rows.get(i).get(j);
                if(e.getX()>=c.getTlx()&&e.getX()<c.getTlx()+c.getCellLength()&&e.getY()>= c.getTly()&&e.getY()<c.getTly()+c.getCellLength()) {
                    c.setDead();
                    repaint();
                }
                }
            }
        }
        else if(!killCells && !drawLineMode){
            outerLoop:
            for (int i = 1; i < rows.size()-1; i++) {
                for (int j = 1; j < rows.get(0).size()-1; j++){
                    Cell c=rows.get(i).get(j);
                    if(e.getX()>=c.getTlx()&&e.getX()<c.getTlx()+c.getCellLength()&&e.getY()>= c.getTly()&&e.getY()<c.getTly()+c.getCellLength()) {
                        c.setAlive();
                        repaint();
                        break outerLoop;
                    }
                }
            }
        }
        else if(drawLineMode=true){
            outerLoop:
            for (int i = 0; i < rows.size()-1; i++) {
                for (int j = 0; j < rows.get(0).size()-1; j++){
                    Cell c=rows.get(i).get(j);
                    if(e.getX()>=c.getTlx()&&e.getX()<c.getTlx()+c.getCellLength()&&e.getY()>= c.getTly()&&e.getY()<c.getTly()+c.getCellLength()) {
                        for (int k = rows.indexOf(c); k < rows.get(0).size()-1; k++){
                            if(k+j>rows.get(0).size()-1){break outerLoop;}
                            else{rows.get(i).get(j+k).setAlive();}
                            repaint();

                        }
                        //break outerLoop;
                    }
                }
            }

        }
        }



    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    //When the timer ticks, action is performed
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
                if(c.totalNeighbours(cE,cW,cN,cS,cNE,cNW,cSE,cSW)>=4){deadCells.add(c);}
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
    //When mouse dragged across screen, draw cells according to mouses position
    public void mouseDragged(MouseEvent e) {
    if(killCells){
        outerloop:
        for (int i = 1; i < rows.size()-1; i++) {
            for (int j = 1; j < rows.get(0).size()-1; j++){
                Cell c=rows.get(i).get(j);
                if(c.isAlive()){
                if(e.getX()>=c.getTlx()&&e.getX()<c.getTlx()+c.getCellLength()&&e.getY()>= c.getTly()&&e.getY()<c.getTly()+c.getCellLength()) {
                    c.setDead();
                    repaint();
                    break outerloop;
                }
                }
            }
        }
    }
          else{
            for (int i = 1; i < rows.size()-1; i++) {
                for (int j = 1; j < rows.get(0).size()-1; j++){
                    Cell c=rows.get(i).get(j);
                    if(e.getX()>=c.getTlx()&&e.getX()<c.getTlx()+c.getCellLength()&&e.getY()>= c.getTly()&&e.getY()<c.getTly()+c.getCellLength()) {
                        c.setAlive();
                        repaint();
                    }
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
