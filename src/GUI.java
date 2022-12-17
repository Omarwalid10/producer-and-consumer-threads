/*
 * Ahmed Mohamed Sallam 20210614
 * Mohamed Gamal Abdelaziz 20201146
 * Omar Walid Ahmed 20201126
 * Ahmed Adel Ali 20201009
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GUI {

    JFrame f;
    public static JLabel l4p2, l5p2, l6p2;
    public GUI() {
        f = new JFrame("Prime Producer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton b = new JButton("Produce");
        b.setBounds(20, 150, 100, 40);


        JTextField tf1, tf2, tf3;
        tf1 = new JTextField();
        tf1.setBounds(20, 50, 150, 20);
        tf2 = new JTextField();
        tf2.setBounds(20, 80, 150, 20);
        tf3 = new JTextField();
        tf3.setBounds(20, 110, 150, 20);

        f.add(tf1);
        f.add(tf2);
        f.add(tf3);


        JLabel l1, l2, l3;
        l1 = new JLabel("N");
        l1.setBounds(200, 50, 100, 30);
        l2 = new JLabel("Buffer Size");
        l2.setBounds(200, 80, 100, 30);
        l3 = new JLabel("Output File");
        l3.setBounds(200, 110, 100, 30);


        JPanel panel = new JPanel();

        panel.setBounds(15, 25, 480, 200);
        panel.setBackground(Color.gray);
        panel.setLayout(new GridLayout(4, 2, 20, 25));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(tf1);
        panel.add(l1);

        panel.add(tf2);
        panel.add(l2);

        panel.add(tf3);
        panel.add(l3);
        panel.add(b);//adding button in JFrame
        f.add(panel);

        JLabel l1p2, l2p2, l3p2;
        l1p2 = new JLabel("The Largest Prime Number : ");
        l1p2.setBounds(200, 50, 100, 30);
        l2p2 = new JLabel("Number Of Prime Number Generated : ");
        l2p2.setBounds(200, 80, 100, 30);
        l3p2 = new JLabel("Time Of Running : ");
        l3p2.setBounds(200, 110, 100, 30);



        l4p2 = new JLabel(" "); //عدد الارقام
        l4p2.setBounds(200, 50, 100, 30);
        l5p2 = new JLabel(" ");//اكبر رقم
        l5p2.setBounds(200, 80, 100, 30);
        l6p2 = new JLabel("Output File");//الوقت
        l6p2.setBounds(200, 110, 100, 30);


        JPanel panel2 = new JPanel();

        panel2.setBounds(15, 230, 480, 200);
        panel2.setBackground(Color.gray);
        panel2.setLayout(new GridLayout(4, 2, 20, 25));
        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel2.add(l1p2);
        panel2.add(l5p2);
        panel2.add(l2p2);
        panel2.add(l4p2);
        panel2.add(l3p2);
        panel2.add(l6p2);

        f.add(panel2);


        f.setSize(600, 500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String N = tf1.getText();
                String Buffer = tf2.getText();
                String outputFile = tf3.getText();
                long n = Integer.parseInt(N);
                long buffer = Integer.parseInt(Buffer);
                PC pc = new PC(n,buffer,outputFile);
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            try {
                                pc.produce();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });

                // Create consumer thread
                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            pc.consume();
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                // Start both threads
                t2.start();
                t1.start();



            }
        });


    }
    public static void setBigRamy(String big){
            l4p2.setText(big);
    }
    public static void setPrimeNums(String primeNums){
        l5p2.setText(primeNums);
    }
    public static void setTime(String Time){
        l6p2.setText(Time);
    }
}
