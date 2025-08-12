/*
 * threadIDE.java
 *
 * Created on 31 de Julho de 2010, 15:14
 */

package megasena2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 *
 * @author  Jefferson
 */
public class megasena2 extends javax.swing.JFrame implements Runnable {
    
    /** Creates new form threadIDE */
    public megasena2() throws IOException {
        initComponents();     
        this.setMinimumSize(new Dimension(400,250));
        this.setSize(new Dimension(430,300));
        roleta1 = new roleta();        
        /*****************************/        
        totaljogos=0;
        anos=0;
        semanas=0;
        quina=0;
        quadra=0;
        label_estatistica.setVisible(false);
        apostou = false;        
        confFrame.setVisible(false);
        confFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);        
        qtdeVezes = 10;
        continuarAposta = false;
        quadrahistorico = new Vector();
        quinahistorico = new Vector();
        tempstr = new String();

        jProgressBar1.setValue(Integer.parseInt(qtdejogos.getValue().toString()));

        /**********ARQUIVOS**************/        
        dbmegasena = new arquivos("megasena.txt");                
        InputStream f;

        f = this.getClass().getResourceAsStream("/imagens/megasena.png");
        //File f = new File("/imagens/megasena.png");        
        //System.out.println("arquivo existe: "+f.exists()+"\n"+f.getAbsolutePath()+"\n"+f.getPath());
        if (f != null){
            img = ImageIO.read(f);
            renderizacao logo = new renderizacao();
            /*  logo.getGraphics().setColor(Color.BLACK);
                logo.getGraphics().drawLine(0,5,50,10);
            */
            logo.setImage(img);
            logo.setBounds(30, 5, 150, 50);
            logo.setBackground(Color.WHITE);
            this.add(logo);
            this.repaint();

            logo.addMouseListener( new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    
                }

                public void mousePressed(MouseEvent e) {
                    creditosFrame.setVisible(true);
                }

                public void mouseReleased(MouseEvent e) {
                    
                }

                public void mouseEntered(MouseEvent e) {
                    
                }

                public void mouseExited(MouseEvent e) {
                    
                }
            });

            System.out.println("w:"+img.getWidth()+" h:"+img.getHeight());
        }

        f = this.getClass().getResourceAsStream("/imagens/dinheiros.png");

        if (f != null){
            img = ImageIO.read(f);
            renderizacao logo2 = new renderizacao();
            logo2.setImage(img);            
            logo2.setBounds(2, 2, 250, 150);
            logo2.setOpaque(false);
            logo2.setBackground(this.getBackground());
            creditosFrame.add(logo2);
            //creditosFrame.repaint();
        }
        /**********ARQUIVOS**************/
        /***********THREAD ***************/
        t1 = new Thread(this);
        t1.start();
        /***********THREAD ***************/
        
        
        this.addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                int wdesk;
                int hdesk;
                int hcomp;
                int wcomp;
                int ybuttons;
                int fsize;
                int xtext;
                int ytext;
                                                
                wdesk = jDesktopPane1.getSize().width/7;
                hdesk = jDesktopPane1.getSize().height/7;
                jLabel1.setSize(wdesk, hdesk);                
                jLabel2.setSize(wdesk, hdesk);
                jLabel2.setLocation(30 + wdesk, jLabel2.getLocation().y );
                jLabel3.setSize(wdesk, hdesk);
                jLabel3.setLocation(30 + wdesk*2, jLabel3.getLocation().y);
                jLabel4.setSize(wdesk, hdesk);
                jLabel4.setLocation(30 + wdesk*3, jLabel4.getLocation().y);
                jLabel5.setSize(wdesk, hdesk);
                jLabel5.setLocation(30 + wdesk*4, jLabel5.getLocation().y);
                jLabel6.setSize(wdesk, hdesk);
                jLabel6.setLocation(30 + wdesk*5, jLabel6.getLocation().y);
                
                if (wdesk > 100){
                    fsize = 70;
                    ybuttons = jLabel1.getLocation().y + hdesk + 50;                    
                    hcomp = 50;
                    wcomp = wdesk + 30;
                    xtext = 30;
                    ytext = 210;
                }else{
                    fsize = 36;
                    hcomp = 20;
                    ybuttons = 150;
                    wcomp = 70;
                    xtext = 30;
                    ytext = 210;
                }
                
                    jLabel1.setFont(new java.awt.Font("Tahoma", 0, fsize)); // NOI18N
                    jLabel2.setFont(new java.awt.Font("Tahoma", 0, fsize)); // NOI18N
                    jLabel3.setFont(new java.awt.Font("Tahoma", 0, fsize)); // NOI18N
                    jLabel4.setFont(new java.awt.Font("Tahoma", 0, fsize)); // NOI18N
                    jLabel5.setFont(new java.awt.Font("Tahoma", 0, fsize)); // NOI18N
                    jLabel6.setFont(new java.awt.Font("Tahoma", 0, fsize)); // NOI18N               
                                
                locatComp(jButton1, 30 + wcomp, ybuttons);
                locatComp(jButton2, 30 + wcomp*2, ybuttons);                
                locatComp(confbt,30 + wcomp*3,ybuttons);
                locatComp(btapostar,30 + wcomp*4,ybuttons);
                
                resizeComp(jButton1, wcomp, hcomp);
                resizeComp(jButton2, wcomp, hcomp);                
                resizeComp(confbt,wcomp,hcomp);
                resizeComp(btapostar,wcomp,hcomp);
                
                resizeComp(jProgressBar1, 280 + wdesk*2, 20);
                locatComp(jProgressBar1, xtext, ybuttons+30);
                
                ytext = jProgressBar1.getLocation().y;
                
                locatComp(lbljogos, xtext, ytext+20);
                locatComp(lblquadra, xtext, ytext+50+20);
                locatComp(lblquina, xtext*5, ytext+20);
                locatComp(lbltempo, xtext*5, ytext+50+20);
                locatComp(lblgasto, xtext*8, ytext+20);
                                              
                    
                                              
                jDesktopPane1.paintComponents(getGraphics());
            }
        });
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        apostaFrame = new javax.swing.JInternalFrame();
        dezena1 = new javax.swing.JTextField();
        dezena2 = new javax.swing.JTextField();
        dezena3 = new javax.swing.JTextField();
        dezena4 = new javax.swing.JTextField();
        dezena5 = new javax.swing.JTextField();
        dezena6 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btapostar_frame = new javax.swing.JButton();
        btacertos = new javax.swing.JButton();
        confFrame = new javax.swing.JInternalFrame();
        jSlider1 = new javax.swing.JSlider();
        qtdejogos = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        qtdedezenas = new javax.swing.JSpinner();
        chkmenossorteados = new javax.swing.JCheckBox();
        historicoFrame = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        quinalist = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        quadralist = new javax.swing.JList();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        creditosFrame = new javax.swing.JInternalFrame();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        confbt = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btapostar = new javax.swing.JButton();
        label_estatistica = new javax.swing.JLabel();
        lblquadra = new javax.swing.JLabel();
        lblquina = new javax.swing.JLabel();
        lbltempo = new javax.swing.JLabel();
        lbljogos = new javax.swing.JLabel();
        lblgasto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(420, 260));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(null);

        jDesktopPane1.setBackground(new java.awt.Color(212, 208, 200));
        jDesktopPane1.setDoubleBuffered(true);
        jDesktopPane1.setMinimumSize(new java.awt.Dimension(420, 260));
        jDesktopPane1.setOpaque(false);
        jDesktopPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jDesktopPane1ComponentResized(evt);
            }
        });

        apostaFrame.setClosable(true);
        apostaFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        apostaFrame.setFrameIcon(null);
        try {
            apostaFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        apostaFrame.getContentPane().setLayout(null);

        dezena1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        apostaFrame.getContentPane().add(dezena1);
        dezena1.setBounds(60, 70, 30, 20);

        dezena2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        apostaFrame.getContentPane().add(dezena2);
        dezena2.setBounds(100, 70, 30, 20);

        dezena3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        apostaFrame.getContentPane().add(dezena3);
        dezena3.setBounds(140, 70, 30, 20);

        dezena4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        apostaFrame.getContentPane().add(dezena4);
        dezena4.setBounds(180, 70, 30, 20);

        dezena5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        apostaFrame.getContentPane().add(dezena5);
        dezena5.setBounds(220, 70, 30, 20);

        dezena6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        apostaFrame.getContentPane().add(dezena6);
        dezena6.setBounds(260, 70, 30, 20);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Faça sua aposta!");
        apostaFrame.getContentPane().add(jLabel13);
        jLabel13.setBounds(60, 40, 230, 14);

        btapostar_frame.setText("Apostar");
        btapostar_frame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btapostar_frameActionPerformed(evt);
            }
        });
        apostaFrame.getContentPane().add(btapostar_frame);
        btapostar_frame.setBounds(90, 110, 80, 23);

        btacertos.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btacertos.setText("Acertos");
        btacertos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btacertosActionPerformed(evt);
            }
        });
        apostaFrame.getContentPane().add(btacertos);
        btacertos.setBounds(190, 110, 90, 23);

        jDesktopPane1.add(apostaFrame);
        apostaFrame.setBounds(40, 30, 340, 190);

        confFrame.setClosable(true);
        confFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        try {
            confFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        confFrame.getContentPane().setLayout(null);

        jSlider1.setOrientation(javax.swing.JSlider.VERTICAL);
        jSlider1.setValue(0);
        confFrame.getContentPane().add(jSlider1);
        jSlider1.setBounds(140, 30, 20, 100);

        qtdejogos.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        qtdejogos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qtdejogosMouseClicked(evt);
            }
        });
        confFrame.getContentPane().add(qtdejogos);
        qtdejogos.setBounds(20, 40, 40, 20);

        jLabel16.setText("Velocidade");
        confFrame.getContentPane().add(jLabel16);
        jLabel16.setBounds(120, 10, 80, 14);

        jLabel17.setText("Qtde Jogos");
        confFrame.getContentPane().add(jLabel17);
        jLabel17.setBounds(20, 20, 70, 14);

        jLabel18.setText("Qtde Números");
        confFrame.getContentPane().add(jLabel18);
        jLabel18.setBounds(20, 70, 90, 14);

        qtdedezenas.setModel(new javax.swing.SpinnerNumberModel(6, 6, 15, 1));
        confFrame.getContentPane().add(qtdedezenas);
        qtdedezenas.setBounds(20, 90, 40, 20);

        chkmenossorteados.setLabel("Menos sorteados");
        confFrame.getContentPane().add(chkmenossorteados);
        chkmenossorteados.setBounds(180, 30, 130, 30);

        jDesktopPane1.add(confFrame);
        confFrame.setBounds(40, 50, 350, 180);

        historicoFrame.setClosable(true);
        historicoFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        try {
            historicoFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        historicoFrame.getContentPane().setLayout(null);

        jScrollPane1.setEnabled(false);

        jScrollPane1.setViewportView(quinalist);

        historicoFrame.getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(200, 50, 100, 100);

        jScrollPane2.setEnabled(false);

        jScrollPane2.setViewportView(quadralist);

        historicoFrame.getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(50, 50, 90, 100);

        jLabel14.setText("Quadras");
        historicoFrame.getContentPane().add(jLabel14);
        jLabel14.setBounds(70, 30, 60, 14);

        jLabel15.setText("Quinas");
        historicoFrame.getContentPane().add(jLabel15);
        jLabel15.setBounds(220, 30, 60, 14);

        jDesktopPane1.add(historicoFrame);
        historicoFrame.setBounds(40, 30, 340, 190);

        creditosFrame.setClosable(true);
        creditosFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        creditosFrame.getContentPane().setLayout(null);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Agradecimentos:");
        jLabel19.setOpaque(true);
        creditosFrame.getContentPane().add(jLabel19);
        jLabel19.setBounds(20, 20, 110, 14);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Trapp");
        jLabel20.setOpaque(true);
        creditosFrame.getContentPane().add(jLabel20);
        jLabel20.setBounds(30, 60, 100, 14);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Qpira");
        jLabel21.setOpaque(true);
        creditosFrame.getContentPane().add(jLabel21);
        jLabel21.setBounds(30, 100, 100, 14);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Kurama");
        jLabel22.setOpaque(true);
        creditosFrame.getContentPane().add(jLabel22);
        jLabel22.setBounds(30, 40, 100, 14);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Brandes (chico bento)");
        jLabel23.setOpaque(true);
        creditosFrame.getContentPane().add(jLabel23);
        jLabel23.setBounds(30, 80, 140, 14);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Bolivia");
        jLabel25.setOpaque(true);
        creditosFrame.getContentPane().add(jLabel25);
        jLabel25.setBounds(170, 40, 70, 14);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Ze Colmeia");
        jLabel26.setOpaque(true);
        creditosFrame.getContentPane().add(jLabel26);
        jLabel26.setBounds(170, 60, 70, 14);

        jDesktopPane1.add(creditosFrame);
        creditosFrame.setBounds(60, 50, 280, 170);

        jProgressBar1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jProgressBar1.setName("jProgressBar"); // NOI18N
        jProgressBar1.setStringPainted(true);
        jProgressBar1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBar1StateChanged(evt);
            }
        });
        jDesktopPane1.add(jProgressBar1);
        jProgressBar1.setBounds(60, 190, 280, 10);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setOpaque(true);
        jDesktopPane1.add(jLabel1);
        jLabel1.setBounds(20, 70, 45, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setOpaque(true);
        jDesktopPane1.add(jLabel2);
        jLabel2.setBounds(80, 70, 45, 50);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setOpaque(true);
        jDesktopPane1.add(jLabel3);
        jLabel3.setBounds(140, 70, 45, 50);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setOpaque(true);
        jDesktopPane1.add(jLabel4);
        jLabel4.setBounds(200, 70, 45, 50);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setOpaque(true);
        jDesktopPane1.add(jLabel5);
        jLabel5.setBounds(260, 70, 45, 50);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel6.setOpaque(true);
        jDesktopPane1.add(jLabel6);
        jLabel6.setBounds(320, 70, 45, 50);
        jDesktopPane1.add(jLabel7);
        jLabel7.setBounds(20, 120, 40, 20);
        jDesktopPane1.add(jLabel8);
        jLabel8.setBounds(80, 120, 40, 20);
        jDesktopPane1.add(jLabel9);
        jLabel9.setBounds(140, 120, 40, 20);
        jDesktopPane1.add(jLabel10);
        jLabel10.setBounds(200, 120, 40, 20);
        jDesktopPane1.add(jLabel11);
        jLabel11.setBounds(260, 120, 40, 20);
        jDesktopPane1.add(jLabel12);
        jLabel12.setBounds(320, 120, 40, 20);

        jButton1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton1.setText("Gerar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jDesktopPane1.add(jButton1);
        jButton1.setBounds(30, 150, 70, 20);

        confbt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        confbt.setText("configuração");
        confbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confbtActionPerformed(evt);
            }
        });
        jDesktopPane1.add(confbt);
        confbt.setBounds(300, 150, 100, 20);

        jButton2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton2.setText("Estatística");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jDesktopPane1.add(jButton2);
        jButton2.setBounds(110, 150, 90, 20);

        btapostar.setText("Apostar");
        btapostar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btapostarActionPerformed(evt);
            }
        });
        jDesktopPane1.add(btapostar);
        btapostar.setBounds(210, 150, 80, 20);

        label_estatistica.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_estatistica.setText("Números mais sorteados em cada dezena!");
        jDesktopPane1.add(label_estatistica);
        label_estatistica.setBounds(40, 50, 340, 14);

        lblquadra.setText("Quadras:");
        jDesktopPane1.add(lblquadra);
        lblquadra.setBounds(30, 210, 100, 14);

        lblquina.setText("Quinas:");
        jDesktopPane1.add(lblquina);
        lblquina.setBounds(140, 210, 70, 14);

        lbltempo.setText("Tempo:");
        jDesktopPane1.add(lbltempo);
        lbltempo.setBounds(210, 230, 190, 14);

        lbljogos.setText("Jogos:");
        jDesktopPane1.add(lbljogos);
        lbljogos.setBounds(30, 230, 170, 14);

        lblgasto.setText("Gasto:");
        lblgasto.setName(""); // NOI18N
        jDesktopPane1.add(lblgasto);
        lblgasto.setBounds(250, 210, 100, 14);

        getContentPane().add(jDesktopPane1);
        jDesktopPane1.setBounds(0, 0, 420, 260);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btapostar_frameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btapostar_frameActionPerformed
// TODO add your handling code here:
        if (dezena1.getText().length() == 0)
            dezena1.requestFocus();
        else if (dezena2.getText().length() == 0)
            dezena2.requestFocus();
        else if (dezena3.getText().length() == 0)
            dezena3.requestFocus();
        else if (dezena4.getText().length() == 0)
            dezena4.requestFocus();
        else if (dezena5.getText().length() == 0)
            dezena5.requestFocus();
        else if (dezena6.getText().length() == 0)
            dezena6.requestFocus();
        else{
            apostaFrame.hide();
            this.getContentPane().repaint();
            continuarAposta = true;
            btapostar.setForeground(Color.RED);
            btapostar.setText("Parar!");
        }
        this.getContentPane().repaint();
        
    }//GEN-LAST:event_btapostar_frameActionPerformed

    private void btapostarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btapostarActionPerformed
// TODO add your handling code here:
        if (evt.getSource() == btapostar){
            apostou = true;
            if (!continuarAposta){           
                apostaFrame.setVisible(true);
            }else{
                btapostar.setForeground(Color.BLACK);
                btapostar.setText("Apostar");
                continuarAposta = false;
                jProgressBar1.setValue(100);
            }
        }
                
    }//GEN-LAST:event_btapostarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:                
        String dados;
        int d1[],d2[],d3[],d4[],d5[],d6[];
        
        label_estatistica.setVisible(true);
        
        d1 = new int[60];
        d2 = new int[60];
        d3 = new int[60];
        d4 = new int[60];
        d5 = new int[60];
        d6 = new int[60];
        
        dados = dbmegasena.LeArquivo2();
        
        //System.err.println(dados);
        
        for (String temp: dados.split("\n")) {
            
            //System.err.println(temp);
            
            d1[Integer.parseInt(temp.split(" ")[0])-1]++;
            d2[Integer.parseInt(temp.split(" ")[1])-1]++;
            d3[Integer.parseInt(temp.split(" ")[2])-1]++;
            d4[Integer.parseInt(temp.split(" ")[3])-1]++;
            d5[Integer.parseInt(temp.split(" ")[4])-1]++;
            d6[Integer.parseInt(temp.split(" ")[5])-1]++;
        }
        int maximo1=d1[0];
        int maximo2=d2[0];
        int maximo3=d3[0];
        int maximo4=d4[0];
        int maximo5=d5[0];
        int maximo6=d6[0];
        
        int pos1=0;
        int pos2=0;
        int pos3=0;
        int pos4=0;
        int pos5=0;
        int pos6=0; 
        
        for(int x=0; x < 60; x++){

            if (chkmenossorteados.isSelected()){
                if ( maximo1 > d1[x] ){
                    maximo1 = d1[x];
                    pos1 = x;
                    System.err.println("min1="+maximo1+" pos="+x);
                }
                if ( maximo2 > d2[x] ){
                    maximo2 = d2[x];
                    pos2 = x;
                }
                if ( maximo3 > d3[x] ){
                    maximo3 = d3[x];
                    pos3 = x;
                }
                if ( maximo4 > d4[x] ){
                    maximo4 = d4[x];
                    pos4 = x;
                }
                if ( maximo5 > d5[x] ){
                    maximo5 = d5[x];
                    pos5 = x;
                }
                if ( maximo6 > d6[x] ){
                    maximo6 = d6[x];
                    pos6 = x;
                }
            }else{
                if ( maximo1 < d1[x] ){
                    maximo1 = d1[x];
                    pos1 = x;
                    System.err.println("max1="+maximo1+" pos="+x);
                }
                if ( maximo2 < d2[x] ){
                    maximo2 = d2[x];
                    pos2 = x;
                }
                if ( maximo3 < d3[x] ){
                    maximo3 = d3[x];
                    pos3 = x;
                }
                if ( maximo4 < d4[x] ){
                    maximo4 = d4[x];
                    pos4 = x;
                }
                if ( maximo5 < d5[x] ){
                    maximo5 = d5[x];
                    pos5 = x;
                }
                if ( maximo6 < d6[x] ){
                    maximo6 = d6[x];
                    pos6 = x;
                }
            }
        }
                        
        jLabel1.setForeground(Color.BLUE);
        jLabel2.setForeground(Color.BLUE);
        jLabel3.setForeground(Color.BLUE);
        jLabel4.setForeground(Color.BLUE);
        jLabel5.setForeground(Color.BLUE);
        jLabel6.setForeground(Color.BLUE);
        
        jLabel1.setText(String.valueOf(pos1+1));
        jLabel2.setText(String.valueOf(pos2+1));
        jLabel3.setText(String.valueOf(pos3+1));
        jLabel4.setText(String.valueOf(pos4+1));
        jLabel5.setText(String.valueOf(pos5+1));
        jLabel6.setText(String.valueOf(pos6+1));
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void qtdejogosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qtdejogosMouseClicked
// TODO add your handling code here:
        /*if (evt.getSource() == qtdeVezes){
            if (qtdeVezes.getValue() <= 0 ){
                qtdeVezes.setValue()
            }
        }*/ 
    }//GEN-LAST:event_qtdejogosMouseClicked

    private void confbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confbtActionPerformed
// TODO add your handling code here:
        if (evt.getSource() == confbt){
            confFrame.setVisible(true);
            confFrame.repaint();
        }
    }//GEN-LAST:event_confbtActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
// TODO add your handling code here:
        jProgressBar1.setValue(0);
    }//GEN-LAST:event_formMouseClicked
    
    private void jProgressBar1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBar1StateChanged
// TODO add your handling code here:
        if (evt.getSource() == jProgressBar1){
           /* if (jProgressBar1.getValue()/jProgressBar1.getMaximum() < 1 && (new Date().getTime() - tempoInicial.getTime() < 1000*5)){
                //roleta(tempoInicial);
            }*/
            paintComponents(getContentPane().getGraphics());
        }
    }//GEN-LAST:event_jProgressBar1StateChanged
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        if ( evt.getSource() == jButton1 ){
            jProgressBar1.setMaximum(100);
            jProgressBar1.setMinimum(0);            
            //tempoInicial = new Date();
            
            label_estatistica.setVisible(false);
            jLabel1.setForeground(Color.black);
            jLabel2.setForeground(Color.black);
            jLabel3.setForeground(Color.black);
            jLabel4.setForeground(Color.black);
            jLabel5.setForeground(Color.black);
            jLabel6.setForeground(Color.black);
            //semanas = 0;
            //anos=0;
            jogos = 0;
            jProgressBar1.setValue(0);
            label_estatistica.setVisible(false);
            //quadra = 0;
            //quina = 0;
            //continuarAposta = false;
            
            /*while (jProgressBar1.getPercentComplete()< 1 && (new Date().getTime() - t1.getTime() < 1500) ){
            } */
            //roleta(tempoInicial);
            System.out.println("isAlive:"+t1.isAlive()+"  isInt:"+t1.isInterrupted());
            //t1.dumpStack();
            synchronized ( this ){
                if (t1.holdsLock(this))
                    this.notify();
            }
                
        }
        /*if (jProgressBar1.getValue() == 100){
            jProgressBar1.setValue(0);
        }*/
        //this.repaint();
        System.out.println("|");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btacertosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btacertosActionPerformed
        // TODO add your handling code here:
        if ( evt.getSource() == btacertos ){
            quadralist.setListData(quadrahistorico);
            quinalist.setListData(quinahistorico);
            historicoFrame.setVisible(true);
        }
    }//GEN-LAST:event_btacertosActionPerformed

    private void jDesktopPane1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDesktopPane1ComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_jDesktopPane1ComponentResized

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        jDesktopPane1.setSize(this.getSize());        
    }//GEN-LAST:event_formComponentResized
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {                
                try {
                    new megasena2().setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public void paintComponents(Graphics g) {        
        //super.paint(g);
        //paint(g);
        //jProgressBar1.setValue(jProgressBar1.getValue()+1);
        //jProgressBar1.repaint();
        //System.out.println("<>");
    }

    private boolean verificaAposta(String numero){
        int num;
        num = 0;
        if (!numero.equals("")){
            num = Integer.parseInt(numero);
        }
        if (Integer.parseInt(jLabel1.getText()) == num ||
            Integer.parseInt(jLabel2.getText()) == num ||
            Integer.parseInt(jLabel3.getText()) == num ||
            Integer.parseInt(jLabel4.getText()) == num ||
            Integer.parseInt(jLabel5.getText()) == num ||
            Integer.parseInt(jLabel6.getText()) == num){

            return true;
        }else
            return false;

    }
    
    
    public void run() {
        
        try {
            while(true){
                
                //canvas1.setBackground(new Color( (int) Math.round(Math.random()*255),(int) Math.round(Math.random()*255),(int) Math.round(Math.random()*255)));
                

                if (continuarAposta && jProgressBar1.getValue() > 50){
                    jProgressBar1.setValue(0);
                }
                if (jProgressBar1.getValue()/jProgressBar1.getMaximum() < 1 || continuarAposta){
                    jProgressBar1.setValue(jProgressBar1.getValue()+(10/qtdeVezes));
                }else{
                    //jProgressBar1.setValue(0);
                    //dbmegasena.closefile();
                    synchronized ( this ){
                        this.wait(0);
                    }
                }
                
                Thread.sleep(10+jSlider1.getValue());
                //Print a message
                roleta1.sorteia();                
                jLabel1.setText(String.valueOf(roleta1.getResultado()[0]));
                jLabel2.setText(String.valueOf(roleta1.getResultado()[1]));
                jLabel3.setText(String.valueOf(roleta1.getResultado()[2]));
                jLabel4.setText(String.valueOf(roleta1.getResultado()[3]));
                jLabel5.setText(String.valueOf(roleta1.getResultado()[4]));
                jLabel6.setText(String.valueOf(roleta1.getResultado()[5]));                
                dbmegasena.gravaArquivo(roleta1.getResultado()[0]+" "+roleta1.getResultado()[1]+" "+roleta1.getResultado()[2]+" "+roleta1.getResultado()[3]+" "+roleta1.getResultado()[4]+" "+roleta1.getResultado()[5]+"\r\n");
                
                totaljogos++;
                jogos++;
                lbljogos.setText("Jogos:"+String.valueOf(totaljogos));
                semanas = jogos/2;
                if (semanas>52){
                    anos++;
                    semanas = 0;
                    jogos = 0;
                }
                lbltempo.setText("Tempo: "+anos+" anos e "+String.valueOf(semanas)+" semanas");
                
                lblgasto.setText("Gasto: R$ "+String.valueOf(totaljogos*5));
                
                acerto=0;

                if (verificaAposta(dezena1.getText())){
                    acerto++;
                }
                if (verificaAposta(dezena2.getText())){
                    acerto++;
                }
                if (verificaAposta(dezena3.getText())){
                    acerto++;
                }
                if (verificaAposta(dezena4.getText())){
                    acerto++;
                }
                if (verificaAposta(dezena5.getText())){
                    acerto++;
                }
                if (verificaAposta(dezena6.getText())){
                    acerto++;
                }

                if (acerto == 4){
                    quadra++;
                    lblquadra.setText("Quadras: "+String.valueOf(quadra));
                    tempstr = "";
                    if (verificaAposta(dezena1.getText())){
                        dezena1.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena1.getText());
                    }
                    if (verificaAposta(dezena2.getText())){
                        dezena2.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena2.getText());
                    }
                    if (verificaAposta(dezena3.getText())){
                        dezena3.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena3.getText());
                    }
                    if (verificaAposta(dezena4.getText())){
                        dezena4.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena4.getText());
                    }
                    if (verificaAposta(dezena5.getText())){
                        dezena5.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena5.getText());
                    }
                    if (verificaAposta(dezena6.getText())){
                        dezena6.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena6.getText());
                    }
                    quadrahistorico.add(tempstr);
                }

                if (acerto == 5){
                    quina++;
                    lblquina.setText("Quinas: "+String.valueOf(quina));
                    tempstr = "";
                    if (verificaAposta(dezena1.getText())){
                        dezena1.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena1.getText());
                    }
                    if (verificaAposta(dezena2.getText())){
                        dezena2.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena2.getText());
                    }
                    if (verificaAposta(dezena3.getText())){
                        dezena3.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena3.getText());
                    }
                    if (verificaAposta(dezena4.getText())){
                        dezena4.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena4.getText());
                    }
                    if (verificaAposta(dezena5.getText())){
                        dezena5.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena5.getText());
                    }
                    if (verificaAposta(dezena6.getText())){
                        dezena6.setForeground(Color.ORANGE);
                        tempstr = tempstr.concat(" "+dezena6.getText());
                    }
                    quinahistorico.add(tempstr);
                }

                /*
                 *  ACERTOU A MEGASENA
                 */
                if (acerto == 6){
                    continuarAposta = false;
                    jProgressBar1.setValue(100);
                    if (verificaAposta(dezena1.getText())){
                        dezena1.setForeground(Color.BLUE);
                    }
                    if (verificaAposta(dezena2.getText())){
                        dezena2.setForeground(Color.BLUE);
                    }
                    if (verificaAposta(dezena3.getText())){
                        dezena3.setForeground(Color.BLUE);
                    }
                    if (verificaAposta(dezena4.getText())){
                        dezena4.setForeground(Color.BLUE);
                    }
                    if (verificaAposta(dezena5.getText())){
                        dezena5.setForeground(Color.BLUE);
                    }
                    if (verificaAposta(dezena6.getText())){
                        dezena6.setForeground(Color.BLUE);
                    }
                }
                /*
                 *  ACERTOU A MEGASENA
                 */
               
            }
        } catch (InterruptedException e) {
            System.out.println("não deu porra!");
        }
    }
    
    private void locatComp(Object o, int w, int h){
        JComponent c;
        
        c = null;
        
        if (o instanceof JButton)
            c = (JButton)o;
        else if (o instanceof JLabel)
            c = (JLabel)o;
        else if (o instanceof JTextField)
            c = (JTextField)o;
        else if (o instanceof JProgressBar)
            c = (JProgressBar)o;
        
        
        try{            
            if (!Objects.isNull(c)){
                c.setLocation(w ,h);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    private void resizeComp(Object o, int w, int h){
        JComponent c;
        
        c = null;
        
        if (o instanceof JButton)
            c = (JButton)o;
        else if (o instanceof JLabel)
            c = (JLabel)o;
        else if (o instanceof JTextField)
            c = (JTextField)o;
        else if (o instanceof JProgressBar)
            c = (JProgressBar)o;
        
        
        try{            
            if (!Objects.isNull(c)){
                c.setSize(w ,h);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
 public void subimages(){
            
            InputStream is = this.getClass().getResourceAsStream("/imagens/1real.png");
            BufferedImage image = ImageIO.read(is);

            // initalizing rows and columns
            int rows = 1;
            int columns = 4;

            // initializing array to hold subimages
            BufferedImage imgs[] = new BufferedImage[16];

            // Equally dividing original image into subimages
            int subimage_Width = image.getWidth() / columns;
            int subimage_Height = image.getHeight() / rows;

            int current_img = 0;

            // iterating over rows and columns for each sub-image
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < columns; j++)
                {
                    // Creating sub image
                    imgs[current_img] = new BufferedImage(subimage_Width, subimage_Height, image.getType());
                    Graphics2D img_creator = imgs[current_img].createGraphics();

                    // coordinates of source image
                    int src_first_x = subimage_Width * j;
                    int src_first_y = subimage_Height * i;

                    // coordinates of sub-image
                    int dst_corner_x = subimage_Width * j + subimage_Width;
                    int dst_corner_y = subimage_Height * i + subimage_Height;

                    img_creator.drawImage(image, 0, 0, subimage_Width, subimage_Height, src_first_x, src_first_y, dst_corner_x, dst_corner_y, null);
                    current_img++;
                }
            }

            //writing sub-images into image files
            for (int i = 0; i < 16; i++)
            {
                //File outputFile = new File("img" + i + ".jpg");
                //ImageIO.write(imgs[i], "jpg", outputFile);
                
            }
            System.out.println("Sub-images have been created.");
        
 }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame apostaFrame;
    private javax.swing.JButton btacertos;
    private javax.swing.JButton btapostar;
    private javax.swing.JButton btapostar_frame;
    private javax.swing.JCheckBox chkmenossorteados;
    private javax.swing.JInternalFrame confFrame;
    private javax.swing.JButton confbt;
    private javax.swing.JInternalFrame creditosFrame;
    private javax.swing.JTextField dezena1;
    private javax.swing.JTextField dezena2;
    private javax.swing.JTextField dezena3;
    private javax.swing.JTextField dezena4;
    private javax.swing.JTextField dezena5;
    private javax.swing.JTextField dezena6;
    private javax.swing.JInternalFrame historicoFrame;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel label_estatistica;
    private javax.swing.JLabel lblgasto;
    private javax.swing.JLabel lbljogos;
    private javax.swing.JLabel lblquadra;
    private javax.swing.JLabel lblquina;
    private javax.swing.JLabel lbltempo;
    private javax.swing.JSpinner qtdedezenas;
    private javax.swing.JSpinner qtdejogos;
    private javax.swing.JList quadralist;
    private javax.swing.JList quinalist;
    // End of variables declaration//GEN-END:variables

    private roleta roleta1;
    private Thread t1;
    private BufferedImage img;
    private int qtdeVezes;
    private arquivos dbmegasena;
    private boolean apostou;
    private int semanas;
    private int jogos;
    private boolean continuarAposta;
    private int quadra;
    private int quina;
    private int anos;
    private int totaljogos;
    private int acerto;
    private Vector <String> quadrahistorico;
    private Vector <String> quinahistorico;
    private String tempstr;
}
