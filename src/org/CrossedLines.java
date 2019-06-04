package org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CrossedLines extends JComponent{

    private static class Line{
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }
    private static class Point{
        final int x;
        final int y;
        final int width;
        final int height;
        final Color color;

        public Point(int x,int y, int width, int height, Color color){
            this.x = x;
            this.width = width;
            this.y = y;
            this.height = height;
            this.color = color;
        }
    }
    private static class Dash{
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Dash(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private final LinkedList<Line> lines = new LinkedList<Line>();
    private final LinkedList<Point> points = new LinkedList<Point>();
    private final LinkedList<Dash> dashes = new LinkedList<Dash>();

    public void addLine(int x1, int x2, int x3, int x4) {
        addLine(x1, x2, x3, x4, Color.black);
    }
    public void addPoint(int x,int y, int width, int height){
        addPoint(x, y, width,height, Color.black);
    }
    public void addDash(int x1, int x2, int x3, int x4){
        addDash(x1, x2, x3, x4, Color.black);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color) {
        lines.add(new Line(x1,x2,x3,x4, color));
        repaint();
    }
    public void addPoint(int x,int y, int width, int height, Color color){
        points.add(new Point(x,  y, width,height,color));
        repaint();
    }
    public void addDash(int x1, int x2, int x3, int x4, Color color){
        dashes.add(new Dash(x1, x2, x3, x4, color));
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }
    public void clearPoints(){
        points.clear();
        repaint();
    }
    public void clearDashes() {
        dashes.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D b = (Graphics2D)g;
        for (Line line : lines) {
            g.setColor(line.color);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
        for(Point point:points){
            g.setColor(point.color);
            g.fillOval(point.x, point.y, point.width, point.height);
        }
        for (Dash dash: dashes){
            float[] Style = {10, 10, 10, 10};
            b.setColor(dash.color);
            BasicStroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10,Style,0);
            b.setStroke(dashed);
            b.drawLine(dash.x1, dash.y1, dash.x2,dash.y2);
        }
    }

    public static void main(String[] args) {
        JFrame Window = new JFrame("CrossedLines");
        Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final CrossedLines CL = new CrossedLines();
        CL.setPreferredSize(new Dimension(1240, 770));
        Window.getContentPane().add(CL, BorderLayout.CENTER);
        JPanel PanelForButtons = new JPanel();
        JButton NewLine = new JButton("Добавить новые прямые");
        JButton Clear = new JButton("Очистить");
        PanelForButtons.add(NewLine);
        PanelForButtons.add(Clear);
        Window.getContentPane().add(PanelForButtons, BorderLayout.SOUTH);
        NewLine.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color RandomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());

                int x11 = (int) (Math.random() * 1240);
                int x21 = (int) (Math.random() * 1240);
                int y11 = (int) (Math.random() * 770);
                int y21 = (int) (Math.random() * 770);

                int x12 = (int) (Math.random() * 1240);
                int x22 = (int) (Math.random() * 1240);
                int y12 = (int) (Math.random() * 770);
                int y22 = (int) (Math.random() * 770);

                int a1, a2, b1, b2, c1, c2, d;
                int xi, yi;
                a1 = y11 - y21;
                b1 = x21 - x11;
                a2 = y12 - y22;
                b2 = x22 - x12;

                d = a1 * b2 - a2 * b1;
                if (d != 0) {
                    c1 = y21 * x11 - x21 * y11;
                    c2 = y22 * x12 - x22 * y12;
                    xi = (b1 * c2 - b2 * c1) / d;
                    yi = (a2 * c1 - a1 * c2) / d;
                    CL.addPoint(xi, yi, 4,4);
                    if (xi>0&&xi<1240&&yi>0&&yi<770){
                        CL.addDash(x11, y11, x21, y21, RandomColor);
                        CL.addDash(x12, y12, x22, y22, RandomColor);
                    }
                    else {
                        CL.addLine(x11, y11, x21, y21, RandomColor);
                        CL.addLine(x12, y12, x22, y22, RandomColor);
                    }
                }
            }
        });
        Clear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CL.clearLines();
                CL.clearPoints();
                CL.clearDashes();
            }
        });
        Window.pack();
        Window.setVisible(true);
    }
}
