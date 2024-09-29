package a2223.hw2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ConnectFourGui extends JFrame {

    private final ConnectFour game;
    JLabel label = new JLabel("");
    JTextArea board = new JTextArea(6, 7);

    public static void main(String[] args) {
        ConnectFourGui gui = new ConnectFourGui();
        gui.setSize(700, 600);
        gui.setLocationRelativeTo(null);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }

    public ConnectFourGui() {

        this.setTitle("Connect Four");
        game = ConnectFour.newInstance();
        game.init();

        this.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        JPanel restartPanel = new JPanel(new FlowLayout());

        Border lineBorder = new LineBorder(Color.BLACK, 2);
        JButton restart = new JButton("Restart");
        JButton[] buttons = new JButton[7];
        for (int i = 0; i < 7; i++) {
            buttons[i] = new JButton(String.valueOf(i));
            buttonPanel.add(buttons[i]);
            buttons[i].addActionListener(new ButtonActionListener());
        }

        restartPanel.add(restart);
        restart.addActionListener(new ButtonActionListener());

        controlPanel.add(restartPanel);
        controlPanel.add(buttonPanel);
        controlPanel.setBorder(lineBorder);

        this.add(controlPanel, BorderLayout.SOUTH);

        board.setEditable(false);
        board.setFont(new Font("Monospaced", Font.PLAIN, 24));
        board.setBorder(lineBorder);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        boardPanel.add(board);
        boardPanel.setBorder(lineBorder);

        this.add(boardPanel, BorderLayout.CENTER);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(lineBorder);
        this.add(label, BorderLayout.NORTH);

        updateBoard();
    }

    private void updateBoard() {
        board.setText(game.toString());
        if (!game.hasWinner()) {
            label.setText("Player " + game.getTurn() + " turn");
        } else {
            label.setText("The winner is " + game.getWinner() + "!!! Please restart the game.");
        }
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("Restart".equals(command)) {
                game.init();
            } else {
                try {
                    game.drop(Integer.parseInt(command));
                } catch (IllegalArgumentException ex) {
                    label.setText("Wrong column.");
                }
            }
            updateBoard();
        }
    }
}
