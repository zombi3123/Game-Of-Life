public class Main {
    public static void main(String[] args){
        Window w=new Window(1800,1000);
        Frame f=new Frame(w.getWidth(),w.getHeight());
        w.add(f);
        w.setVisible(true);

    }
}
