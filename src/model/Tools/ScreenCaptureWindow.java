package model.Tools;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
/**
 * Created by jelle on 22-6-2017.
 */
public class ScreenCaptureWindow {
    private Rectangle captureRect;
    public ScreenCaptureWindow(final BufferedImage screen) {
                final BufferedImage screenCopy = new BufferedImage(
                        screen.getWidth(),
                        screen.getHeight(),
                        screen.getType());
                final JLabel screenLabel = new JLabel(new ImageIcon(screenCopy));
                JScrollPane screenScroll = new JScrollPane(screenLabel);

                screenScroll.setPreferredSize(new Dimension(
                        (int)(screen.getWidth()/2),
                        (int)(screen.getHeight()/2)));

                JPanel panel = new JPanel(new BorderLayout());
                panel.add(screenScroll, BorderLayout.CENTER);

                final JLabel selectionLabel = new JLabel(
                        "Drag a rectangle in the screen shot!");
                panel.add(selectionLabel, BorderLayout.SOUTH);

                repaint(screen, screenCopy);
                screenLabel.repaint();

                screenLabel.addMouseMotionListener(new MouseMotionAdapter()
                {

                    Point start = new Point();

                    @Override
                    public void mouseMoved(MouseEvent me) {
                        start = me.getPoint();
                        repaint(screen, screenCopy);
                        selectionLabel.setText("Start Point: " + start);
                        screenLabel.repaint();
                    }

                    @Override
                    public void mouseDragged(MouseEvent me) {
                        Point end = me.getPoint();
                        captureRect = new Rectangle(start,
                                new Dimension(end.x-start.x, end.y-start.y));
                        repaint(screen, screenCopy);
                        screenLabel.repaint();
                        selectionLabel.setText("Rectangle: " + captureRect);
                    }
                });
                JOptionPane.showMessageDialog(null, panel);
                //System.out.println("Rectangle of interest: " + captureRect);
            }

            public Rectangle getCaptureRect()
            {
                return this.captureRect;
            }

            public void repaint(BufferedImage orig, BufferedImage copy) {
                Graphics2D g = copy.createGraphics();
                g.drawImage(orig,0,0, null);
                if (captureRect!=null) {
                    g.setColor(Color.RED);
                    g.draw(captureRect);
                    g.setColor(new Color(255,255,255,150));
                    g.fill(captureRect);
                }
                g.dispose();
            }
    }
