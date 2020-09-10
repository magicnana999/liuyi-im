import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * @author magicnana
 * @date 2020/7/30 下午3:37
 */
public class RoundButton extends JButton {
    public static final Color BUTTON_COLOR1 = Color.white;
    public static final Color BUTTON_COLOR2 = new Color(1, 155, 227);
    public static final Color BUTTON_FOREGROUND_COLOR = Color.WHITE;
    public static final int ROUND_RECT = 0;
    private static final long serialVersionUID = 39082560987930759L;
    private boolean hover;
    private int style;

    public RoundButton() {
        this(ROUND_RECT);
    }

    public RoundButton(int style) {
        this.style = style;
        setFont(new Font("system", Font.PLAIN, 12));
        setBorderPainted(false);
        setForeground(BUTTON_COLOR2);
        setFocusPainted(false);
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(BUTTON_FOREGROUND_COLOR);
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(BUTTON_COLOR2);
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int h = getHeight();
        int w = getWidth();
        float tran = 1F;
        if(!hover) {
            tran = 0.3F;
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint p1;
        GradientPaint p2;
        if(getModel().isPressed()) {
            p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1, new Color(100, 100, 100));
            p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3, new Color(255, 255, 255, 100));
        } else {
            p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1, new Color(0, 0, 0));
            p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0, h - 3, new Color(0, 0, 0, 50));
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tran));
        GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F, h, BUTTON_COLOR2, true);
        g2d.setPaint(gp);
        switch (style) {
            case ROUND_RECT: {
                RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 20, 20);
                Shape clip = g2d.getClip();
                g2d.clip(r2d);
                g2d.fillRect(0, 0, w, h);
                g2d.setClip(clip);
                g2d.setPaint(p1);
                g2d.drawRoundRect(0, 0, w - 1, h - 1, 20, 20);
                g2d.setPaint(p2);
                g2d.drawRoundRect(1, 1, w - 3, h - 3, 18, 18);
                break;
            }
            default:
                break;
        }
        g2d.dispose();
        super.paintComponent(g);
    }
}
