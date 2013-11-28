package Graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import PlanetWars.GraphicEngine;
import PlanetWars.Planet;
import PlanetWars.Soldier;

public class GraphicalWorld implements GraphicEngine {

	private int maxClock;
	private JLabel time;
    private JFrame window;
    private ImagePanel mainView;
    private HashMap<Planet, JButton> planets;
    private HashMap<Soldier, JButton> soldiers;
    private String team1, team2;
    private BufferedImage spaceship_blue, spaceship_red;
	private JLabel team1_soldiers;
	private JLabel team2_soldiers;
	private JPanel infoBar;

    
//    private Thread turnThread;

    public GraphicalWorld() {
        planets = new HashMap<Planet, JButton>();
        soldiers = new HashMap<Soldier, JButton>();
        
        team1_soldiers = new JLabel();
        team1_soldiers.setOpaque(true);
        team1_soldiers.setBackground(Color.white);
        
        team2_soldiers = new JLabel();
        team2_soldiers.setOpaque(true);
        team2_soldiers.setBackground(Color.white);
        
        try {
        	 spaceship_blue = ImageIO.read(getClass().getResource("/resources/Spaceship_blue.png"));
        	 spaceship_red = ImageIO.read(getClass().getResource("/resources/Spaceship_red.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        turnThread = new Thread(new Runnable() {
//        	private final double theta = 0.02;
////			public void refresh () {
////				
////			}
//			@Override
//			public void run() {
//				while (true) {
//					final HashMap<Planet, JButton> planets_tmp = planets;
//					for (JButton bl : planets_tmp.values()) {
//						if (bl.getName().equals("Blackhole")) {
//							ImageIcon icon = (ImageIcon)bl.getIcon();
//							
//
//							if (icon != null) {
//								BufferedImage img = (BufferedImage)((Image) icon.getImage());
//								BufferedImage tmp = rotate(resize(img, 100, 100), new Point(0,0), new Point(10, 1));
//							}
//							
//
//						}
//					}
//					try {
//						Thread.sleep(20);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//        turnThread.start();
    }

    private void makeMainView() {
        mainView = new ImagePanel("/resources/Galaxy.jpg");
        mainView.setSize(window.getWidth(), window.getHeight() - 50);
        mainView.setLayout(null);
        mainView.setLocation(0, 50);
        window.add(mainView);
    }

    public void addSoldier(Soldier soldier) {
        JButton pl = new JButton();
        pl.setLayout(null);
        soldiers.put(soldier, pl);
        int width = 2 * (int) (30 * Math.atan(soldier.getStrenght())) +5;
        int height = width;
        pl.setSize(width, height);
        pl.setLocation((int) (soldier.getPosition().getX() - width / 2), (int) (soldier.getPosition().getY() - height / 2));
        JLabel text = new JLabel();
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setText(soldier.getStrenght() + "");
        text.setFont(new Font("Arial", Font.BOLD, 15));
        text.setSize(pl.getWidth(), 30);
        text.setLocation(0, pl.getHeight()/2 - 15);
        pl.add(text);
        BufferedImage image;
        if (soldier.getTeam().getName().equals(team1)) {
            image = spaceship_red;
        } else {
        	image = spaceship_blue;
        }
        ImageIcon imageForOne = null;
        imageForOne = new ImageIcon(rotate(resize(image, width, height), soldier.getPosition(), soldier.getDest().getPosition()));
        pl.setIcon(imageForOne);
        pl.setBorderPainted(false);
        pl.setContentAreaFilled(false);
        mainView.add(pl);
        mainView.repaint();
    }

    public void updateSoldier(Soldier soldier) {
        JButton pl = soldiers.get(soldier);
        pl.setLocation((int) (soldier.getPosition().getX() - pl.getWidth() / 2), (int) (soldier.getPosition().getY() - pl.getHeight() / 2));
        JLabel text = new JLabel();
        text.setText(soldier.getStrenght() + "");

        mainView.repaint();
    }

    public void destroySoldier (Soldier soldier){
    	JButton s = soldiers.get(soldier);
        mainView.remove(s);
        soldiers.remove(soldier);
    }

    public void addPlanet(Planet planet) {
        JButton pl = new JButton();
        pl.setLayout(null);
        planets.put(planet, pl);
        pl.setSize(planet.getDiameter(), planet.getDiameter());
        pl.setLocation((int) (planet.getPosition().getX() - planet.getDiameter() / 2), (int) (planet.getPosition().getY() - planet.getDiameter() / 2));
        pl.setName(planet.getOwner().getName());

        ImageIcon imageForOne = null;
        if (planet.getOwner().getName().equals("Blackhole")){
            try {
                imageForOne = new ImageIcon(resize(ImageIO.read(getClass().getResource("/resources/Black-hole.png")), planet.getDiameter(), planet.getDiameter()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
        	JLabel text = new JLabel("" + planet.getNumerOfSoldiers());
            text.setHorizontalAlignment(SwingConstants.CENTER);
            text.setText("<html><font color='white' size=" + planet.getDiameter() / 15 + ">" + planet.getNumerOfSoldiers() + "</font></html>");
            text.setSize(pl.getWidth() , pl.getHeight() / 3);
            text.setLocation(0, pl.getHeight() / 3);
            pl.add(text);
        	String color = "default";
            if (planet.getOwner().getName().equals(team1)) {
                color = "red";
            } else if (planet.getOwner().getName().equals(team2)) {
                color = "blue";
            }
            try {
                imageForOne = new ImageIcon(resize(ImageIO.read(getClass().getResource("/resources/Planet_" + color + ".png")), planet.getDiameter(), planet.getDiameter()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        pl.setIcon(imageForOne);
        pl.setBorderPainted(false);
        pl.setContentAreaFilled(false);
        mainView.add(pl);
        mainView.repaint();
    }

    public void updatePlanet(Planet planet) {
        JButton pl = planets.get(planet);

        JLabel text = (JLabel) pl.getComponents()[0];
        text.setText("<html><font color='white' size=" + planet.getDiameter() / 15 + ">" + planet.getNumerOfSoldiers() + "</font></html>");
        pl.add(text);

        
        if (!planet.getOwner().getName().equals(pl.getName())) {
        	ImageIcon imageForOne = null;
            String color = "default";
            if (planet.getOwner().getName().equals(team1)) {
                color = "red";
            } else if (planet.getOwner().getName().equals(team2)) {
                color = "blue";
            }
            try {
                imageForOne = new ImageIcon(resize(ImageIO.read(getClass().getResource("/resources/Planet_" + color + ".png")), planet.getDiameter(), planet.getDiameter()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            pl.setIcon(imageForOne);
            pl.setName(planet.getOwner().getName());
        }
        
        mainView.repaint();
    }

    public void setTeamNames(String team1, String team2) {
        this.team1 = team1;
        this.team2 = team2;

        infoBar = new JPanel();
        infoBar.setLayout(null);
        infoBar.setLocation(0, 0);
        infoBar.setSize(window.getWidth(), 50);
        window.add(infoBar);
        
        JLabel title1 = new JLabel();
        title1.setOpaque(true);
        title1.setFont(new Font("Serif", Font.BOLD, 17));
        title1.setLocation(0, 0);
        title1.setSize(window.getWidth()/2, 50);
        title1.setHorizontalAlignment(SwingConstants.CENTER);
        title1.setVerticalAlignment(SwingConstants.CENTER);
        title1.setBackground(Color.red);
        title1.setText("<html><font color='white'>" + team1 + "</font></html>");
        infoBar.add(title1);

        JLabel title2 = new JLabel();
        title2.setOpaque(true);
        title2.setFont(new Font("Serif", Font.BOLD, 20));
        title2.setLocation(window.getWidth()/2, 0);
        title2.setSize(window.getWidth()/2, 50);
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        title2.setVerticalAlignment(SwingConstants.CENTER);
        title2.setBackground(Color.blue);
        title2.setText("<html><font color='white'>" + team2 + "</font></html>");
        infoBar.add(title2);
        
        infoBar.add(team1_soldiers, 0);
		infoBar.add(team2_soldiers, 0);
       
        
        window.repaint();
    }

    public void setSize(int width, int height) {
        window = new JFrame();
        window.setSize(width, height+50);

        makeMainView();

        window.addWindowStateListener(new WindowStateListener() {

            public void windowStateChanged(WindowEvent arg0) {
                if (arg0.getNewState() == JFrame.ICONIFIED) {
                    window.repaint();
                    mainView.repaint();
                }

            }
        });

        window.setResizable(false);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

    public BufferedImage rotate(BufferedImage bufferedImage, Point A, Point B) {

        double radians;
        if (A.x == B.x) {
            if (A.y > B.y) {
                radians = -Math.PI / 2;
            } else {
                radians = Math.PI / 2;
            }
        } else {

            radians = Math.atan((float) (A.y - B.y) / (float) (A.x - B.x));
            if (A.x > B.x) {
            	radians += Math.PI;
            }
        }
        AffineTransform transform = new AffineTransform();
        transform.rotate(radians, bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        return op.filter(bufferedImage, null);
    }

	@Override
	public void setGameInfo(int team1Soldiers, int team2Soldiers) {
		// TODO Auto-generated method stub
		System.out.println(team1Soldiers + " " + team2Soldiers);
		double p1_d = (double)(team1Soldiers)/(team1Soldiers + team2Soldiers);
		team1_soldiers.setBounds(0, 40,(int)(window.getWidth()*p1_d/2), 10);
		
		double p2_d = (double)(team2Soldiers)/(team1Soldiers + team2Soldiers);
		team2_soldiers.setBounds(window.getWidth()/2, 40,(int)(window.getWidth()*p2_d/2), 10);
		
		infoBar.repaint();
		
	}

	@Override
	public void gameFinishedEvent(String team) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, team);
		
	}

	@Override
	public void setClock(int clock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxClock(int max) {
		// TODO Auto-generated method stub
		this.maxClock = max;
	}

}

class ImagePanel extends JPanel {
    /**
     *
     */
    public BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
    private static final long serialVersionUID = 1L;
    private BufferedImage image = null;

    public ImagePanel(String filename) {

        try {
            this.image = ImageIO.read(getClass().getResource(filename));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = resize(image, getWidth(), getHeight());
        g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }
}
