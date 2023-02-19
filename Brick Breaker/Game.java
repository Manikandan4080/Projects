import javax.swing.JFrame;

public class Game {
    public static void main(String[] args){
        JFrame frame = new JFrame("Brick Breaker");
        frame.setVisible(true);
        frame.setBounds(20, 40, 700, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePlay game = new GamePlay();
        frame.add(game);

    }
}
