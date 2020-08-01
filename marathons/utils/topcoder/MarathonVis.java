package marathons.utils.topcoder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Base class for Topcoder Marathon testers with visualization. Should be
 * extended directly for problems with a visual representation, but no
 * animation, i.e. only a single (final) state is shown.
 *
 * Updated: 2020/07/30
 */
public abstract class MarathonVis extends MarathonTester {
    protected final Object updateLock = new Object();
    protected JFrame frame;
    private boolean vis = true;
    private JPanel panel;
    private Font infoFontPlain, infoFontBold;
    private final Map<Object, Object> infoMap = new HashMap<Object, Object>();
    private final Map<Object, Boolean> infoChecked = new HashMap<Object, Boolean>();
    private final Map<Object, Rectangle2D> infoRects = new HashMap<Object, Rectangle2D>();
    private final List<Object> infoSequence = new ArrayList<Object>();
    private int border, infoFontWidth, infoFontHeight, infoColumns, infoLines;
    private double size = -1;
    private Rectangle2D contentRect = new Rectangle2D.Double(0, 0, 100, 100);
    private Rectangle2D contentScreen = new Rectangle2D.Double();
    private static final double lineSpacing = 1.25;
    private RenderingHints hints;
    private long paintTime;
    private int paintCnt;

    protected abstract void paintContent(Graphics2D g);

    static {
        System.setProperty("sun.java2d.uiScale", "1");
        System.setProperty("sun.java2d.dpiaware", "true");
    }

    public void setParameters(Parameters parameters) {
        super.setParameters(parameters);
        if (parameters.isDefined(Parameters.noVis)) {
            System.setProperty("java.awt.headless", "true");
            vis = false;
        }
        if (parameters.isDefined(Parameters.size)) size = parameters.getIntValue(Parameters.size);
    }

    protected final void setInfoMaxDimension(int infoColumns, int infoLines) {
        if (!vis) return;
        this.infoColumns = infoColumns;
        this.infoLines = infoLines;
    }

    protected final void setContentRect(double xLeft, double yTop, double xRight, double yBottom) {
        if (!vis) return;
        contentRect.setRect(xLeft, yTop, xRight - xLeft, yBottom - yTop);
    }

    protected final void setDefaultSize(int size) {
        if (this.size == -1) this.size = size;
    }

    protected final boolean hasVis() {
        return vis;
    }

    protected void update() {
        if (!vis) return;
        synchronized (updateLock) {
            if (frame == null) {
                String className = getClass().getName();
                Map<RenderingHints.Key, Object> hintsMap = new HashMap<RenderingHints.Key, Object>();
                hintsMap.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                hintsMap.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                hintsMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                hintsMap.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
                if (parameters.isDefined(Parameters.noAntialiasing)) {
                    hintsMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                    hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
                } else {
                    hintsMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                }
                hints = new RenderingHints(hintsMap);

                frame = new JFrame();
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        end();
                    }
                });

                panel = new JPanel() {
                    private static final long serialVersionUID = -1008231133177413855L;

                    protected void paintComponent(Graphics g) {
                        paintVis(g, getWidth(), getHeight());
                    }
                };

                panel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (contentScreen != null && contentScreen.contains(e.getPoint())) {
                            if (contentScreen.getWidth() > 0 && contentScreen.getHeight() > 0) {
                                double x = (e.getX() - contentScreen.getX()) / contentScreen.getWidth() * contentRect.getWidth() + contentRect.getX();
                                double y = (e.getY() - contentScreen.getY()) / contentScreen.getHeight() * contentRect.getHeight() + contentRect.getY();
                                contentClicked(x, y, e.getButton(), e.getClickCount());
                            }
                            return;
                        }
                        for (Object key : infoRects.keySet()) {
                            Rectangle2D rc = infoRects.get(key);
                            if (rc != null && rc.contains(e.getPoint())) {
                                Boolean checked = infoChecked.get(key);
                                if (checked != null) {
                                    infoChecked.put(key, !checked);
                                    checkChanged(key, !checked);
                                }
                                break;
                            }
                        }
                    }
                });

                final int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
                infoFontPlain = new Font(Font.SANS_SERIF, Font.PLAIN, resolution / 8);
                infoFontBold = new Font(Font.SANS_SERIF, Font.BOLD, infoFontPlain.getSize());

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        frame.setSize(1000, 1000);
                        frame.setTitle(className + " - Seed: " + seed);
                        frame.setIconImage(getIcon());
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        frame.setContentPane(panel);

                        FontRenderContext frc = new FontRenderContext(null, true, true);
                        Rectangle2D rc = infoFontBold.getStringBounds("0", frc);
                        infoFontWidth = (int) Math.ceil(rc.getWidth());
                        infoFontHeight = (int) Math.ceil(rc.getHeight());

                        border = resolution / 7;

                        frame.setVisible(true);
                        if (size <= 0) {
                            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                            Rectangle bounds = new Rectangle(0, 0, screenSize.width, screenSize.height);
                            try {
                                GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
                                bounds = new Rectangle(gc.getBounds());
                                Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
                                bounds.x += insets.left;
                                bounds.y += insets.top;
                                bounds.width -= insets.left + insets.right;
                                bounds.height -= insets.top + insets.bottom;
                            } catch (Throwable t) {
                            }
                            Insets fi = frame.getInsets();
                            int fw = bounds.width - fi.left - fi.right;
                            int fh = bounds.height - fi.top - fi.bottom;
                            double sw = (fw - 3 * border - infoColumns * infoFontWidth) / contentRect.getWidth();
                            double sh = (fh - 2 * border) / contentRect.getHeight();
                            size = Math.min(sw, sh);
                        }
                        int width = 3 * border + infoColumns * infoFontWidth + (int) (contentRect.getWidth() * size);
                        int height = 2 * border + (int) Math.max(infoLines * infoFontHeight * lineSpacing, contentRect.getHeight() * size);
                        panel.setPreferredSize(new Dimension(width, height));
                        frame.pack();
                    }
                });
            }
        }
        panel.repaint();
    }

    @SuppressWarnings("unused")
    protected void checkChanged(Object key, boolean newValue) {
        panel.repaint();
    }

    @SuppressWarnings("unused")
    protected void contentClicked(double x, double y, int mouseButton, int clickCount) {
    }

    protected void end() {
        if (ending) return;
        if (paintCnt > 0 && parameters.isDefined(Parameters.paintInfo)) {
            System.out.println("    Paint Count: " + paintCnt);
            System.out.println("Paint Avg. Time: " + paintTime / paintCnt + " ms");
        }
        super.end();
    }

    protected final void addInfo(Object key, Object value) {
        if (!vis) return;
        if (!infoMap.containsKey(key)) infoSequence.add(key);
        infoMap.put(key, value);
    }

    protected final void addInfo(Object key) {
        if (!vis) return;
        if (!infoMap.containsKey(key)) infoSequence.add(key);
        infoMap.put(key, null);
    }

    protected final void addInfo(Object key, Object value, boolean checked) {
        if (!vis) return;
        if (!infoMap.containsKey(key)) infoSequence.add(key);
        infoMap.put(key, value);
        infoChecked.put(key, checked);
    }

    protected final void addInfo(Object key, boolean checked) {
        if (!vis) return;
        if (!infoMap.containsKey(key)) infoSequence.add(key);
        infoMap.put(key, null);
        infoChecked.put(key, checked);
    }

    protected final void addInfoBreak() {
        if (!vis) return;
        infoSequence.add(null);
    }

    protected final boolean isInfoChecked(Object key) {
        Boolean checked = infoChecked.get(key);
        if (checked != null) return checked.booleanValue();
        return false;
    }

    protected final Rectangle2D getPaintRect() {
        return contentRect;
    }

    private void paintVis(Graphics g, int w, int h) {
        long t = System.currentTimeMillis();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(230, 230, 232));
        g2.fillRect(0, 0, w, h);
        g2.setRenderingHints(hints);

        synchronized (updateLock) {
            if (infoColumns > 0) paintInfo(g2, w);
            paintCenter(g2, w - infoFontWidth * infoColumns - border, h);
            paintTime += System.currentTimeMillis() - t;
            paintCnt++;
        }
    }

    private void paintCenter(Graphics2D g, int w, int h) {
        int pw = w - 2 * border;
        int ph = h - 2 * border;
        if (pw <= 0 || ph <= 0) return;
        int px = border;
        int py = border;
        if (contentRect.getWidth() * ph > contentRect.getHeight() * pw) {
            ph = (int) (contentRect.getHeight() * pw / contentRect.getWidth());
        } else {
            int nw = (int) (contentRect.getWidth() * ph / contentRect.getHeight());
            px += (pw - nw) / 2;
            pw = nw;
        }
        AffineTransform nt = new AffineTransform();
        nt.translate(px, py);
        nt.scale(pw / contentRect.getWidth(), ph / contentRect.getHeight());
        nt.translate(-contentRect.getX(), -contentRect.getY());
        AffineTransform ct = g.getTransform();
        contentScreen.setRect(px, py, pw, ph);
        g.setTransform(nt);
        paintContent(g);
        g.setTransform(ct);
    }

    private void paintInfo(Graphics2D g, int w) {
        int x = w - infoFontWidth * infoColumns - border;
        int y = border;
        int maxKey = 0;
        g.setFont(infoFontBold);
        FontMetrics metrics = g.getFontMetrics();
        for (Object key : infoSequence) {
            if (key != null) {
                String s = "";
                if (key instanceof Color) {
                    s = "##";
                } else {
                    s = key.toString();
                }
                boolean hasValue = infoMap.get(key) != null;
                if (hasValue) s += ": ";
                if (infoChecked.get(key) != null) s += "##";
                Rectangle2D rect = metrics.getStringBounds(s, g);
                int width = (int) rect.getWidth();
                if (!hasValue) width /= 2;
                maxKey = Math.max(maxKey, width);
            }
        }

        int lineHeight = (int) (lineSpacing * infoFontHeight);
        g.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(Color.black);
        for (Object key : infoSequence) {
            if (key != null) {
                Object value = infoMap.get(key);
                g.setFont(infoFontBold);
                int xc = 0;
                if (value == null) {
                    xc = drawString(g, key.toString(), x + maxKey, y, 0);
                } else {
                    if (key instanceof Color) {
                        xc = drawColor(g, (Color) key, x + maxKey, y);
                    } else {
                        xc = drawString(g, key + ": ", x + maxKey, y, -1);
                    }
                    g.setFont(infoFontPlain);
                    drawString(g, value.toString(), x + maxKey, y, 1);
                }
                Boolean checked = infoChecked.get(key);
                if (checked != null) drawChecked(g, key, checked, xc, y);
            }
            y += lineHeight;
        }
    }

    private int drawColor(Graphics2D g, Color color, int x, int y) {
        FontMetrics metrics = g.getFontMetrics();
        int size = metrics.getHeight() - metrics.getDescent();
        g.setColor(color);
        Rectangle2D rc = new Rectangle2D.Double(x - size - infoFontWidth, y + metrics.getDescent() / 2, size, size);
        g.fill(rc);
        g.setColor(Color.black);
        g.draw(rc);
        return (int)rc.getMinX();
    }

    private void drawChecked(Graphics2D g, Object key, boolean checked, int x, int y) {
        FontMetrics metrics = g.getFontMetrics();
        int size = metrics.getHeight() - metrics.getDescent();
        Rectangle2D rc = new Rectangle2D.Double(x - size - infoFontWidth, y + metrics.getDescent() / 2, size, size);
        g.setColor(Color.black);
        g.draw(rc);
        synchronized (infoRects) {
            infoRects.put(key, rc);
        }
        if (checked) {
            g.draw(new Line2D.Double(rc.getMinX(), rc.getMinY(), rc.getMaxX(), rc.getMaxY()));
            g.draw(new Line2D.Double(rc.getMinX(), rc.getMaxY(), rc.getMaxX(), rc.getMinY()));
        }
    }

    private int drawString(Graphics2D g, String s, int x, int y, int align) {
        FontMetrics metrics = g.getFontMetrics();
        Rectangle2D rect = metrics.getStringBounds(s, g);
        if (align < 0) x -= (int) rect.getWidth();
        else if (align == 0) x -= (int) rect.getWidth() / 2;
        g.drawString(s, x, y + metrics.getAscent());
        return x;
    }

    protected void adjustFont(Graphics2D g, String fontName, int fontStyle, String largestStr, Rectangle2D rcToFit) {
        g.setFont(new Font(fontName, fontStyle, 32));
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(largestStr, g);
        double f = Math.min(rcToFit.getWidth() / bounds.getWidth(), rcToFit.getHeight() / bounds.getHeight());
        AffineTransform transformation = AffineTransform.getScaleInstance(f, f);
        g.setFont(g.getFont().deriveFont(transformation));
    }

    protected void drawString(Graphics2D g, String str, Rectangle2D rc) {
        GlyphVector v = g.getFont().createGlyphVector(g.getFontRenderContext(), str);
        Shape s = v.getOutline();
        Rectangle2D rs = s.getBounds2D();
        double x = rc.getX() - rs.getX() + (rc.getWidth() - rs.getWidth()) / 2.0;
        double y = rc.getY() - rs.getY() + (rc.getHeight() - rs.getHeight()) / 2.0;
        s = AffineTransform.getTranslateInstance(x, y).createTransformedShape(s);
        g.fill(s);
    }

    private BufferedImage getIcon() {
        int size = 256;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHints(hints);
        AffineTransform nt = new AffineTransform();
        nt.scale(size, size);
        g.setTransform(nt);
        g.setStroke(new BasicStroke(0.06f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        GradientPaint gradient = new GradientPaint(0.5f, 0, new Color(150, 75, 200), 0.5f, 1, new Color(90, 160, 230));
        g.setPaint(gradient);
        Ellipse2D e0 = new Ellipse2D.Double(0, 0, 1, 1);
        g.fill(e0);
        Ellipse2D e1 = new Ellipse2D.Double(0.05, 0.45, 0.2, 0.2);
        Ellipse2D e2 = new Ellipse2D.Double(0.30, 0.05, 0.2, 0.2);
        Ellipse2D e3 = new Ellipse2D.Double(0.75, 0.25, 0.2, 0.2);
        Ellipse2D e4 = new Ellipse2D.Double(0.70, 0.60, 0.2, 0.2);
        Ellipse2D e5 = new Ellipse2D.Double(0.35, 0.75, 0.2, 0.2);
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(0.06f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(new Line2D.Double(e1.getCenterX(), e1.getCenterY(), e2.getCenterX(), e2.getCenterY()));
        g.draw(new Line2D.Double(e1.getCenterX(), e1.getCenterY(), e3.getCenterX(), e3.getCenterY()));
        g.draw(new Line2D.Double(e4.getCenterX(), e4.getCenterY(), e3.getCenterX(), e3.getCenterY()));
        g.draw(new Line2D.Double(e4.getCenterX(), e4.getCenterY(), e5.getCenterX(), e5.getCenterY()));
        g.draw(new Line2D.Double(e2.getCenterX(), e2.getCenterY(), e5.getCenterX(), e5.getCenterY()));
        g.draw(new Line2D.Double(e2.getCenterX(), e2.getCenterY(), e4.getCenterX(), e4.getCenterY()));
        g.setPaint(gradient);
        g.fill(e1);
        g.fill(e2);
        g.fill(e3);
        g.fill(e4);
        g.fill(e5);
        g.setColor(Color.white);
        g.draw(e1);
        g.draw(e2);
        g.draw(e3);
        g.draw(e4);
        g.draw(e5);
        g.dispose();
        float[] blurKernel = {0.1f,0.1f,0.1f,0.1f,0.2f,0.1f,0.1f,0.1f,0.1f};
        BufferedImageOp blurFilter = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
        blurFilter.filter(img, null);
        return img;
    }
}
