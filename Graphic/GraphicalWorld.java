package Graphic;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import PlanetWars.GraphicEngine;
import PlanetWars.Planet;
import PlanetWars.Soldier;
import PlanetWars.World;

public class GraphicalWorld implements GraphicEngine {

    private JFrame window;
    private World world;
    private ImagePanel mainView;
    private HashMap<Planet, JButton> planets;
    private HashMap<Soldier, JButton> soldiers;
    private String team1, team2;

    public GraphicalWorld() {
        planets = new HashMap<Planet, JButton>();
        soldiers = new HashMap<Soldier, JButton>();
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
        int width = 2 * (int) (30 * Math.atan(soldier.getStrenght())) + 30;
        int height = width;
        pl.setSize(width, height);
        pl.setLocation((int) (soldier.getPosition().getX() - width / 2), (int) (soldier.getPosition().getY() - height / 2));
        JLabel text = new JLabel();
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setText("<html><font color='white' face='Serif' size=" + pl.getX()/15 + ">" + soldier.getStrenght() + "</font></html>");
        text.setSize(pl.getWidth(), pl.getHeight() / 3);
        text.setLocation(0, pl.getHeight() / 3);
        pl.add(text);

        ImageIcon imageForOne = null;
        try {
            imageForOne = new ImageIcon(rotate(resize(ImageIO.read(getClass().getResource("/resources/Spaceship.png")), width, height), soldier.getPosition(), soldier.getDest().getPosition()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        text.setText("<html><font color='white' face='Serif' size=" + pl.getX()/15 + ">" + soldier.getStrenght() + "</font></html>");

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

        JLabel text = new JLabel("" + planet.getNumerOfSoldiers());
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setText("<html><font color='white' size=" + planet.getDiameter() / 15 + ">" + planet.getNumerOfSoldiers() + "</font></html>");
        text.setSize(pl.getWidth() , pl.getHeight() / 3);
        text.setLocation(0, pl.getHeight() / 3);
        pl.add(text);

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
        mainView.repaint();
    }

    public void setTeamNames(String team1, String team2) {
        this.team1 = team1;
        this.team2 = team2;

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.green);
        panel.setLocation(0, 0);
        panel.setSize(window.getWidth(), 50);
        window.add(panel);

        JLabel title1 = new JLabel();
        title1.setLocation(window.getWidth() / 4 - 50, 5);
        title1.setSize(100, 15);
        title1.setText("<html><font color='red'>" + team1 + "</font></html>");
        panel.add(title1);

        JLabel title2 = new JLabel();
        title2.setLocation(3 * window.getWidth() / 4 - 50, 5);
        title2.setSize(100, 15);
        title2.setText("<html><font color='blue'>" + team2 + "</font></html>");
        panel.add(title2);
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
        transform.rotate(radians, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(bufferedImage, null);
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
