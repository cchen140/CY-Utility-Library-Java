package cy.utility;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by cychen on 7/13/16.
 */
public class Gui {
    /**
     * Configure the Enabled value for the child components of the given container (component).
     * Note that this doesn't include the root itself.
     * @param inContainer   Root component whose children's enabled values are configured.
     * @param inEnabled     The desired Enabled value.
     */
    public static void setEnabledForAllChildren(Container inContainer, Boolean inEnabled) {
        for (Component thisComponent : inContainer.getComponents()) {
            thisComponent.setEnabled(inEnabled);
            setEnabledForAllChildren((Container) thisComponent, inEnabled);
        }
    }

    public static void addHorizontalSpace(Container inContainer, int inWidth) {
        inContainer.add(Box.createRigidArea(new Dimension(inWidth, 0)));    // Space
    }

    public static void addVerticalSpace(Container inContainer, int inHeight) {
        inContainer.add(Box.createRigidArea(new Dimension(0, inHeight)));    // Space
    }

    /**
     * Create an empty line border surrounding the component.
     * @param inComponent
     * @param top
     * @param left
     * @param bottom
     * @param right
     */
    public static void setComponentBorder(JComponent inComponent, int top, int left, int bottom, int right) {  // top, left, bottom, right
        Border paddingBorder = BorderFactory.createEmptyBorder(top, left, bottom, right); // top, left, bottom, right
        Border border = BorderFactory.createLineBorder(Color.WHITE);
        inComponent.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
    }
}
