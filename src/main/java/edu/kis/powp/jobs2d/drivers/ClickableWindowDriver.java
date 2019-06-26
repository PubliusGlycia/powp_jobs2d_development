package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class ClickableWindowDriver implements Job2dDriver {
    private ILine line;
    private int startX = 0, startY = 0;
    private DrawPanelController drawController;
    private JPanel panel;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ClickableWindowDriver(DrawPanelController drawController, ILine line, String name, Application app) {
        super();
        this.drawController = drawController;
        this.line = line;

        panel = app.getFreePanel();

        panel.addMouseListener(new ClickListener(panel));
    }

    @Override
    public void setPosition(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void operateTo(int x, int y) {
        line.setStartCoordinates(this.startX, this.startY);
        this.setPosition(x, y);
        line.setEndCoordinates(x, y);

        drawController.drawLine(line);
    }

    private class ClickListener extends MouseAdapter{
        JPanel panel;

        private ClickListener(JPanel panel) {
            this.panel = panel;
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            int width = panel.getWidth();
            int height = panel.getHeight();

            if(DriverFeature.getDriverManager().getCurrentDriver() instanceof ClickableWindowDriver){
                if (SwingUtilities.isLeftMouseButton(e)) {
                    DriverFeature.getDriverManager()
                                 .getCurrentDriver()
                                 .operateTo(e.getX() - width / 2, e.getY() - height / 2);
                                        logger.warning("Operate to X:" + (e.getX() - width / 2) + " Y:" + (e.getY() - height / 2));
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    DriverFeature.getDriverManager()
                                 .getCurrentDriver()
                                 .setPosition(e.getX() - width / 2, e.getY() - height / 2);
                                        logger.warning("Set Position X:" + (e.getX() - width / 2) + " Y:" + (e.getY() - height / 2));
                }
            }
        }
    }
}
