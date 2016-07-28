package cy.utility.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by cychen on 7/12/16.
 * Code borrowed and modified from http://geekonjava.blogspot.com/2015/07/create-round-jbutton-in-swing.html.
 */
class RoundButton extends JButton {
    public RoundButton () {
        this (null, null);
    }
    public RoundButton (Icon icon) {
        this (null, icon);
    }
    public RoundButton (String text) {
        this (text, null);
    }
    public RoundButton (Action a) {
        this ();
        setAction (a);
    }
    public RoundButton (String text, Icon icon) {
        setModel (new DefaultButtonModel ());
        init (text, icon);
        if (icon == null) {
            return;
        }
        setBorder (BorderFactory.createEmptyBorder (1, 1, 1, 1));
        setBackground (Color.BLACK);
        setContentAreaFilled (false);
        setFocusPainted (false);
        //setVerticalAlignment(SwingConstants.TOP);
        setAlignmentY (Component.TOP_ALIGNMENT);
        initShape ();
    }
    protected Shape shape, base;
    protected void initShape () {
        if (! getBounds (). equals (base)) {
            Dimension s = getPreferredSize ();
            base = getBounds ();
            shape = new Ellipse2D.Float (0, 0, s.width - 1, s.height - 1);
        }
    }

    public Dimension getPreferredSize() {
        Icon icon = getIcon ();
        Insets i = getInsets ();
        int iw = Math.max (icon.getIconWidth (), icon.getIconHeight ());
        return new Dimension (iw + i.right + i.left, iw + i.top + i.bottom);
    }
    protected void paintBorder(Graphics g) {
        initShape ();
        Graphics2D g2 = (Graphics2D) g.create ();
        g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor (getBackground ());
        //g2.setStroke(new BasicStroke (1f));
        g2.draw (shape);
        g2.dispose ();
    }
    public boolean contains (int x, int y) {
        initShape ();
        return shape.contains (x, y);
        // Following, example of a case in which the transparent color is to click No 0
        // Or return super.contains (x, y) && ((image.getRGB (x, y) >> 24) & 0xff)> 0;
    }
}
