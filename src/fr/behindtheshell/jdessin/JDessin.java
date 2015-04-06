package fr.behindtheshell.jdessin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JDessin extends JPanel implements ActionListener {

    protected final LinkedList<Figure> f;
    protected final JPanel drawArea;
    protected String selectedFig;
    protected Color selectedColor;
    protected boolean deleteMode;

    public JDessin() {
        f = new LinkedList<>();
        selectedFig = "Rectangle";
        selectedColor = Color.BLACK;
        deleteMode = false;

        setLayout(new BorderLayout());

        JPanel controls = new JPanel();
        controls.setLayout(new BorderLayout());
        add(controls, BorderLayout.NORTH);

        JPanel topControls = new JPanel();
        topControls.setLayout(new FlowLayout());
        controls.add(topControls, BorderLayout.NORTH);

        JButton bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(this);
        topControls.add(bSupprimer);

        JButton bAnnuler = new JButton("Annuler");
        bAnnuler.addActionListener(this);
        topControls.add(bAnnuler);

        JButton bEffacer = new JButton("Effacer");
        bEffacer.addActionListener(this);
        topControls.add(bEffacer);

        JButton bEnregistrer = new JButton("Enregistrer");
        bEnregistrer.addActionListener(this);
        topControls.add(bEnregistrer);

        JButton bNoir = new JButton();
        bNoir.setActionCommand("Noir");
        bNoir.setPreferredSize(new Dimension(30, 30));
        bNoir.setBackground(Color.BLACK);
        bNoir.addActionListener(this);
        topControls.add(bNoir);

        JButton bRouge = new JButton();
        bRouge.setActionCommand("Rouge");
        bRouge.setPreferredSize(new Dimension(30, 30));
        bRouge.setBackground(Color.RED);
        bRouge.addActionListener(this);
        topControls.add(bRouge);

        JButton bVert = new JButton();
        bVert.setActionCommand("Vert");
        bVert.setPreferredSize(new Dimension(30, 30));
        bVert.setBackground(Color.GREEN);
        bVert.addActionListener(this);
        topControls.add(bVert);

        JButton bBleu = new JButton();
        bBleu.setActionCommand("Bleu");
        bBleu.setPreferredSize(new Dimension(30, 30));
        bBleu.setBackground(Color.BLUE);
        bBleu.addActionListener(this);
        topControls.add(bBleu);

        JButton bJaune = new JButton();
        bJaune.setActionCommand("Jaune");
        bJaune.setPreferredSize(new Dimension(30, 30));
        bJaune.setBackground(Color.YELLOW);
        bJaune.addActionListener(this);
        topControls.add(bJaune);

        JPanel bottomControls = new JPanel();
        bottomControls.setLayout(new FlowLayout());
        controls.add(bottomControls, BorderLayout.SOUTH);

        JButton bRectangle = new JButton("Rectangle");
        bRectangle.addActionListener(this);
        bottomControls.add(bRectangle);

        JButton bEllipse = new JButton("Ellipse");
        bEllipse.addActionListener(this);
        bottomControls.add(bEllipse);

        JButton bTriangle = new JButton("Triangle");
        bTriangle.addActionListener(this);
        bottomControls.add(bTriangle);

        JButton bDamier = new JButton("Damier");
        bDamier.addActionListener(this);
        bottomControls.add(bDamier);

        JButton bEtoile = new JButton("Etoile");
        bEtoile.addActionListener(this);
        bottomControls.add(bEtoile);

        JButton bBatman = new JButton("Batman");
        bBatman.addActionListener(this);
        bottomControls.add(bBatman);

        JButton bBonhomme = new JButton("Bonhomme");
        bBonhomme.addActionListener(this);
        bottomControls.add(bBonhomme);

        drawArea = new ZoneDessin();
        drawArea.setBackground(Color.WHITE);
        add(drawArea, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame fr = new JFrame("Dessin");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.add(new JDessin());
        fr.pack();
        fr.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String el = e.getActionCommand();
        JButton b = (JButton) e.getSource();

        switch (el) {
            case "Supprimer":
                deleteMode = !deleteMode;
                if (deleteMode) {
                    b.setBackground(Color.GRAY);
                } else {
                    b.setBackground(null);
                }
                break;
            case "Annuler":
                if (f.size() != 0) {
                    f.removeLast();
                    drawArea.repaint();
                }
                break;
            case "Effacer":
                f.clear();
                drawArea.repaint();
                break;
            case "Enregistrer":
                Dimension size = drawArea.getSize();
                BufferedImage image = (BufferedImage) drawArea.createImage(size.width, size.height);
                drawArea.paint(image.getGraphics());
                try {
                    ImageIO.write(image, "png", new File("image.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "Rectangle":
                selectedFig = "Rectangle";
                break;
            case "Ellipse":
                selectedFig = "Ellipse";
                break;
            case "Triangle":
                selectedFig = "Triangle";
                break;
            case "Damier":
                selectedFig = "Damier";
                break;
            case "Etoile":
                selectedFig = "Etoile";
                break;
            case "Batman":
                selectedFig = "Batman";
                break;
            case "Bonhomme":
                selectedFig = "Bonhomme";
                break;
            case "Noir":
                selectedColor = Color.BLACK;
                break;
            case "Rouge":
                selectedColor = Color.RED;
                break;
            case "Vert":
                selectedColor = Color.GREEN;
                break;
            case "Bleu":
                selectedColor = Color.BLUE;
                break;
            case "Jaune":
                selectedColor = Color.YELLOW;
                break;
        }
    }

    protected abstract class Figure {

        protected int x;
        protected int y;
        protected int w;
        protected int h;
        protected final Color c;

        public Figure(int x, int y, Color c) {
            this.x = x;
            this.y = y;
            this.w = 0;
            this.h = 0;
            this.c = c;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setWidth(int w) {
            this.w = w;
        }

        public void setHeight(int h) {
            this.h = h;
        }

        public boolean isInside(int x, int y) {
            return (x >= this.x && x <= this.x + w && y >= this.y && y <= this.y + h);
        }

        public abstract void drawFigure(Graphics g);
    }

    protected class Rectangle extends Figure {

        public Rectangle(int x, int y, Color c) {
            super(x, y, c);
        }

        @Override
        public void drawFigure(Graphics g) {
            g.setColor(c);
            g.drawRect(x, y, w, h);
        }
    }

    protected class Ellipse extends Figure {

        public Ellipse(int x, int y, Color c) {
            super(x, y, c);
        }

        @Override
        public void drawFigure(Graphics g) {
            g.setColor(c);
            g.drawOval(x, y, w, h);
        }
    }

    protected class Triangle extends Figure {

        public Triangle(int x, int y, Color c) {
            super(x, y, c);
        }

        @Override
        public void drawFigure(Graphics g) {
            int[] xPts = {x, x + w, x + (w / 2)};
            int[] yPts = {y + h, y + h, y};
            g.setColor(c);
            g.drawPolygon(xPts, yPts, 3);
        }
    }

    protected class Damier extends Figure {

        public Damier(int x, int y, Color c) {
            super(x, y, c);
        }

        @Override
        public void drawFigure(Graphics g) {
            g.setColor(c);
            g.drawRect(x, y, w, h);
            g.fillRect(x + (w / 2), y, w / 2 + 1, h / 2 + 1);
            g.fillRect(x, y + (h / 2), w / 2 + 1, h / 2 + 1);
        }
    }

    protected class Etoile extends Figure {

        public Etoile(int x, int y, Color c) {
            super(x, y, c);
        }

        @Override
        public void drawFigure(Graphics g) {
            int[] xPts = {x, x + w, x + w / 2};
            int[] yPts1 = {y + (3 * h / 4), y + (3 * h / 4), y};
            int[] yPts2 = {y + (h / 4), y + (h / 4), y + h};
            g.setColor(c);
            g.drawPolygon(xPts, yPts1, 3);
            g.drawPolygon(xPts, yPts2, 3);
        }
    }

    protected class Batman extends Figure {

        public Batman(int x, int y, Color c) {
            super(x, y, c);
        }

        @Override
        public void drawFigure(Graphics g) {
            g.setColor(c);

            g.drawArc(
                (int) Math.round(25 / 100.0 * w + x),
                (int) Math.round(0 / 40.0 * h + y),
                (int) Math.round(20 / 100.0 * w),
                (int) Math.round(15 / 40.0 * h),
                100, 215);
            g.drawArc(
                (int) Math.round(0 / 100.0 * w + x),
                (int) Math.round(0 / 40.0 * h + y),
                (int) Math.round(70 / 100.0 * w),
                (int) Math.round(40 / 40.0 * h),
                93, 143);
            g.drawArc(
                (int) Math.round(15 / 100.0 * w + x),
                (int) Math.round(30 / 40.0 * h + y),
                (int) Math.round(20 / 100.0 * w),
                (int) Math.round(10 / 40.0 * h),
                30, 170);
            g.drawArc(
                (int) Math.round(30 / 100.0 * w + x),
                (int) Math.round(30 / 40.0 * h + y),
                (int) Math.round(20 / 100.0 * w),
                (int) Math.round(20 / 40.0 * h),
                0, 130);

            g.drawArc(
                (int) Math.round(50 / 100.0 * w + x),
                (int) Math.round(30 / 40.0 * h + y),
                (int) Math.round(20 / 100.0 * w),
                (int) Math.round(20 / 40.0 * h),
                50, 130);
            g.drawArc(
                (int) Math.round(65 / 100.0 * w + x),
                (int) Math.round(30 / 40.0 * h + y),
                (int) Math.round(20 / 100.0 * w),
                (int) Math.round(10 / 40.0 * h),
                340, 170);
            g.drawArc(
                (int) Math.round(30 / 100.0 * w + x),
                (int) Math.round(0 / 40.0 * h + y),
                (int) Math.round(70 / 100.0 * w),
                (int) Math.round(40 / 40.0 * h),
                304, 143);
            g.drawArc(
                (int) Math.round(55 / 100.0 * w + x),
                (int) Math.round(0 / 40.0 * h + y),
                (int) Math.round(20 / 100.0 * w),
                (int) Math.round(15 / 40.0 * h),
                225, 215);

            g.drawLine(
                (int) Math.round(58 / 100.0 * w + x),
                (int) Math.round(13 / 40.0 * h + y),
                (int) Math.round(55 / 100.0 * w + x),
                (int) Math.round(0 / 40.0 * h + y));
            g.drawLine(
                (int) Math.round(55 / 100.0 * w + x),
                (int) Math.round(0 / 40.0 * h + y),
                (int) Math.round(53 / 100.0 * w + x),
                (int) Math.round(5 / 40.0 * h + y));
            g.drawLine(
                (int) Math.round(53 / 100.0 * w + x),
                (int) Math.round(5 / 40.0 * h + y),
                (int) Math.round(47 / 100.0 * w + x),
                (int) Math.round(5 / 40.0 * h + y));
            g.drawLine(
                (int) Math.round(47 / 100.0 * w + x),
                (int) Math.round(5 / 40.0 * h + y),
                (int) Math.round(45 / 100.0 * w + x),
                (int) Math.round(0 / 40.0 * h + y));
            g.drawLine(
                (int) Math.round(45 / 100.0 * w + x),
                (int) Math.round(0 / 40.0 * h + y),
                (int) Math.round(42 / 100.0 * w + x),
                (int) Math.round(13 / 40.0 * h + y));
        }
    }

    protected class Bonhomme extends Figure {

        public Bonhomme(int x, int y, Color c) {
            super(x, y, c);
        }

        @Override
        public void drawFigure(Graphics g) {
            g.setColor(c);

            g.drawOval(
                (int) Math.round(1 / 4.0 * w + x),
                (int) Math.round(0 / 8.0 * h + y),
                (int) Math.round(2 / 4.0 * w),
                (int) Math.round(2 / 8.0 * h));

            g.drawLine(
                (int) Math.round(2 / 4.0 * w + x),
                (int) Math.round(2 / 8.0 * h + y),
                (int) Math.round(2 / 4.0 * w + x),
                (int) Math.round(5 / 8.0 * h + y));

            g.drawLine(
                (int) Math.round(0 / 4.0 * w + x),
                (int) Math.round(3 / 8.0 * h + y),
                (int) Math.round(4 / 4.0 * w + x),
                (int) Math.round(3 / 8.0 * h + y));

            g.drawLine(
                (int) Math.round(2 / 4.0 * w + x),
                (int) Math.round(5 / 8.0 * h + y),
                (int) Math.round(1 / 4.0 * w + x),
                (int) Math.round(8 / 8.0 * h + y));

            g.drawLine(
                (int) Math.round(2 / 4.0 * w + x),
                (int) Math.round(5 / 8.0 * h + y),
                (int) Math.round(3 / 4.0 * w + x),
                (int) Math.round(8 / 8.0 * h + y));
        }
    }

    protected class MyAdapter extends MouseAdapter {

        protected final Container c;
        protected int baseX;
        protected int baseY;
        protected Figure currentFigure;

        public MyAdapter(Container c) {
            this.c = c;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (!deleteMode) {
                baseX = e.getX();
                baseY = e.getY();

                switch (selectedFig) {
                    case "Rectangle":
                        currentFigure = new Rectangle(baseX, baseY, selectedColor);
                        break;
                    case "Ellipse":
                        currentFigure = new Ellipse(baseX, baseY, selectedColor);
                        break;
                    case "Triangle":
                        currentFigure = new Triangle(baseX, baseY, selectedColor);
                        break;
                    case "Damier":
                        currentFigure = new Damier(baseX, baseY, selectedColor);
                        break;
                    case "Etoile":
                        currentFigure = new Etoile(baseX, baseY, selectedColor);
                        break;
                    case "Batman":
                        currentFigure = new Batman(baseX, baseY, selectedColor);
                        break;
                    case "Bonhomme":
                        currentFigure = new Bonhomme(baseX, baseY, selectedColor);
                        break;
                }

                f.add(currentFigure);
            } else {
                Figure fig = null;

                for (Figure daFigure : f) {
                    if (daFigure.isInside(e.getX(), e.getY())) {
                        fig = daFigure;
                    }
                }

                if (fig != null) {
                    f.remove(fig);
                    c.repaint();
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!deleteMode) {
                currentFigure.setWidth(Math.abs(baseX - e.getX()));
                currentFigure.setHeight(Math.abs(baseY - e.getY()));

                if (e.getX() < baseX) {
                    currentFigure.setX(e.getX());
                } else {
                    currentFigure.setX(baseX);
                }

                if (e.getY() < baseY) {
                    currentFigure.setY(e.getY());
                } else {
                    currentFigure.setY(baseY);
                }

                c.repaint();
            }
        }
    }

    protected class ZoneDessin extends JPanel {

        ZoneDessin() {
            setPreferredSize(new Dimension(800, 600));
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            setOpaque(true);
            MyAdapter adp = new MyAdapter(this);
            addMouseMotionListener(adp);
            addMouseListener(adp);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (Figure daFigure : f) {
                Graphics gg = g.create();
                daFigure.drawFigure(gg);
            }
        }
    }
}
