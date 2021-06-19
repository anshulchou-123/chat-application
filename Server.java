import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.net.ServerSocket;
import java.io.*;
import java.util.Calendar;
import java.text.*;
import java.net.*;

public class Server extends JFrame implements ActionListener{
    JFrame first;
    JPanel p1;
    TextField t;
    JButton b;
    static JPanel T;
    static Box vertical=Box.createVerticalBox();
    static DataInputStream input;
    static DataOutputStream output;
    static ServerSocket server_socket;
    static Socket server;
    boolean typing=false;
    Server(){
        p1=new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        add(p1);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("Image/1.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1=new JLabel(i3);
        l1.setBounds(5,18,30,30);
        p1.add(l1);
        setLayout(null);

        l1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });

        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("Image/2.jpg"));
        Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel l2=new JLabel(i6);
        l2.setBounds(40,10,60,60);
        p1.add(l2);

        JLabel label=new JLabel("Anshul");
        label.setFont(new Font("Verdana",Font.BOLD,18));
        label.setForeground(Color.white);
        label.setBounds(110,15,100,15);
        p1.add(label);

        JLabel active=new JLabel("Active now");
        active.setFont(new Font("Verdana",Font.PLAIN,10));
        active.setLayout(null);
        active.setBounds(110,35,70,20);
        active.setForeground(Color.white);
        p1.add(active);

        Timer tim=new Timer(1,new ActionListener(){
            public  void actionPerformed(ActionEvent e){
                if(!typing){
                    active.setText("Action now");
                }
            }
        });

        tim.setInitialDelay(2000);
        ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("Image/video.png"));
        Image i8 = i7.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel l3=new JLabel(i9);
        l3.setBounds(280,8,50,50);
        p1.add(l3);

        ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("Image/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel l4=new JLabel(i12);
        l4.setBounds(350,10,45,45);
        p1.add(l4);

        ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("Image/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15, 25, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel l5=new JLabel(i15);
        l5.setBounds(410,17,15,25);
        p1.add(l5);

        t=new TextField();
        t.setBounds(6,650,300,45);
        add(t);

        t.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ae){
                active.setText("typing...");
                tim.stop();
                typing=true;
            }
            public void keyReleased(KeyEvent ae){
                typing =false;
                if(!tim.isRunning()){
                    tim.start();
                }
            }
        });



        b=new JButton("Sent");
        b.setBounds(310,650,130,45);
        b.setVisible(true);
        b.setBackground(new Color(7,94,84));
        b.setForeground(Color.white);
        b.setFont(new Font("Verdana",Font.PLAIN,18));
        add(b);

         b.addActionListener(this);

         T=new JPanel();
         T.setBounds(2,70,446,580);
         T.setFont(new Font("Verdana",Font.PLAIN,18));
         T.setBackground(Color.white);
         T.setVisible(true);
         add(T);


        setSize(450,700);
        setLocation(300,100);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        try{
            String out=t.getText();
            savechat(out);
            JPanel p=func(out);
            T.setLayout(new BorderLayout());
            JPanel r=new JPanel(new BorderLayout());
            r.add(p,BorderLayout.LINE_END);
            vertical.add(r);
            T.add(vertical,BorderLayout.PAGE_START);
            t.setText("");
            output.writeUTF(out);
          }catch(Exception ae){
   
          }
    }

    public void savechat(String chat){
        try(FileWriter files = new FileWriter("chats.txt",true);
        PrintWriter p=new PrintWriter(new BufferedWriter(files));){
            p.println("Anshul :"+ chat);

        }catch(Exception e){
            
        }
    }

    public static JPanel func(String out){
        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        JLabel jl=new JLabel();
        jl.setText("<html><p style=\"width:170px\">"+out+"</p></html>");
        jl.setBackground(Color.green);
        jl.setOpaque(true);
        jl.setFont(new Font("Verdana",Font.PLAIN,18));
        jl.setBorder(new EmptyBorder(5,5,5,10));
        

        JLabel j=new JLabel();
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:MM");
        j.setText(sdf.format(cal.getTime()));
        p.add(jl);
        p.add(j);
        return p;




    }

    public static void main(String[] args) throws Exception {
        new Server();
        String message="";
        try{
            server_socket=new ServerSocket(80);
            server=server_socket.accept();
        }catch(Exception e){

        }
        while(server.isConnected()){
          input=new DataInputStream(server.getInputStream());
          output=new DataOutputStream(server.getOutputStream());
          message=input.readUTF();
          JPanel p=func(message);
          T.setLayout(new BorderLayout());
          JPanel l=new JPanel(new BorderLayout());
          l.add(p,BorderLayout.LINE_START);
          vertical.add(l);
          T.add(vertical,BorderLayout.PAGE_START);
        }
        server_socket.close();
        server.close();
      }
}
