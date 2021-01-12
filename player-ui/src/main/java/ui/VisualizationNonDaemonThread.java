/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

import app.EventMessage;
import framew.bus.infc.Event;
import framew.bus.infc.Listener;
import framew.global.ApplicationConstantsUI;
import framew.log.Logger;
import framew.threads.api.NonDaemonThread;
import hob.compo.engine.PlayerThreadGenerator;

/**
 *
 * @author kranti
 */
public class VisualizationNonDaemonThread extends NonDaemonThread implements Listener{

	private static JPanel visualizationPanel;

	private static BufferedImage imageBuffer;

	private static int WIDTH = 200, HEIGHT = 500, FPS = 10;

	private static Source source = new Source(WIDTH, HEIGHT);

	public static Random randomGenerator = new Random();
	
	private static EventMessage eventMessage;

	public VisualizationNonDaemonThread(String name, JPanel visualizationPanel, BufferedImage imageBuffer) {
		super(name);
		VisualizationNonDaemonThread.visualizationPanel = visualizationPanel;
		VisualizationNonDaemonThread.imageBuffer = imageBuffer;
	}

	@Override
	public void performStartUpActivites() {
		eventMessage = (EventMessage) ApplicationConstantsUI.PLAYING_EVENT;
	}

	@Override
	public void execute() {
		while (true) {
			if (PlayerThreadGenerator.getCurrentStatus()) {
				displayBarGraph();
			} else {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					Logger log = (Logger) ApplicationConstantsUI.getByName("logger");
					log.error(this.getClass(), "execute()", "Visualization Sleeping");
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	public void init() {
		// Nothing to do here;
	}

	public void displayBarGraph() {
		int noofBars = 12;
		int width = WIDTH / (noofBars + 2);
		int x = width;
		for (int j = 0; j < 30; j++) {
			imageBuffer.flush();
			Graphics2D g2D = imageBuffer.createGraphics();
			int[][] pointsXY = new int[12][2];
			for (int i = 0; i < 12; i++) {
				pointsXY[i][0] = x + width * i;
				pointsXY[i][1] = Math.abs(randomGenerator.nextInt() % HEIGHT);
			}
			for (int i = 0; i < 12; i++) {
				g2D.setColor(new Color(Math.abs(randomGenerator.nextInt() % 256),
						Math.abs(randomGenerator.nextInt() % 256), Math.abs(randomGenerator.nextInt() % 256)));
				g2D.fillRect(pointsXY[i][0], HEIGHT-pointsXY[i][1], width, pointsXY[i][1]);
			}
			visualizationPanel.repaint();
			imageBuffer.flush();
			// Running at specified FPS
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			g2D.setBackground(new Color(0, 0, 0, 0));
			g2D.clearRect(0, 0, imageBuffer.getWidth(), imageBuffer.getHeight());
			g2D.fillRect(0, 0, imageBuffer.getWidth(), imageBuffer.getHeight());
			visualizationPanel.repaint();
			imageBuffer.flush();
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void displayRandomLines() {
		double oneRadian = Math.PI / 180;
		int x = Math.abs(randomGenerator.nextInt() % WIDTH), y = Math.abs(randomGenerator.nextInt() % HEIGHT),
				r = Math.abs(randomGenerator.nextInt() % WIDTH);
		for (int j = 0; j < 30; j++) {
			imageBuffer.flush();
			Graphics2D g2D = imageBuffer.createGraphics();
			double offset = oneRadian * j;
			int[][][] pointsXY = new int[12][2][2];
			for (int i = 0; i < 12; i++) {
				double finalRadian = offset + Math.PI / 12 * i;
				pointsXY[i][0][0] = (int) Math.abs(x - r * Math.sin(finalRadian));
				pointsXY[i][0][1] = (int) Math.abs(y - r * Math.cos(finalRadian));
				pointsXY[i][1][0] = (int) Math.abs(x + r * Math.sin(finalRadian));
				pointsXY[i][1][1] = (int) Math.abs(y + r * Math.cos(finalRadian));
			}
			for (int i = 0; i < 12; i++) {
				g2D.setColor(new Color(Math.abs(randomGenerator.nextInt() % 256),
						Math.abs(randomGenerator.nextInt() % 256), Math.abs(randomGenerator.nextInt() % 256)));
				g2D.drawLine(pointsXY[i][0][0], pointsXY[i][0][1], pointsXY[i][1][0], pointsXY[i][1][1]);
			}
			if (null != PlayerThreadGenerator.getCurrentPlayListItem()) {
				g2D.setColor(new Color(Math.abs(randomGenerator.nextInt() % 256),
						Math.abs(randomGenerator.nextInt() % 256), Math.abs(randomGenerator.nextInt() % 256)));
				g2D.drawString(PlayerThreadGenerator.getCurrentPlayListItem().getResourceName(),
						Math.abs(randomGenerator.nextInt() % WIDTH), Math.abs(randomGenerator.nextInt() % HEIGHT));
			}
			visualizationPanel.repaint();
			imageBuffer.flush();
			// Running at specified FPS
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			g2D.setBackground(new Color(0, 0, 0, 0));
			g2D.clearRect(0, 0, imageBuffer.getWidth(), imageBuffer.getHeight());
			visualizationPanel.repaint();
			imageBuffer.flush();
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void random() {

		int count = 0;
		Color c = new Color(Math.abs(randomGenerator.nextInt() % 256), Math.abs(randomGenerator.nextInt() % 256),
				Math.abs(randomGenerator.nextInt() % 256));
		int[][][] pointsXY = source.getDrawingObjects();
		Graphics2D g2D = imageBuffer.createGraphics();
		for (int i = 0; i < pointsXY.length; i++) {
			if (count > 100)
				c = new Color(Math.abs(randomGenerator.nextInt() % 256), Math.abs(randomGenerator.nextInt() % 256),
						Math.abs(randomGenerator.nextInt() % 256));
			g2D.setColor(c);
			if (Math.abs(randomGenerator.nextInt() % 2) == 0)
				c.brighter();
			else
				c.darker();
			g2D.drawLine(pointsXY[i][0][0], pointsXY[i][0][1], pointsXY[i][1][0], pointsXY[i][1][1]);
			count++;
		}
		visualizationPanel.repaint();

	}

	@Override
	public void listen(Event event) {
		
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		
	}
}

class Source {

	public static Random randomGenerator = new Random();

	private int width = 0, height = 0;

	public Source(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public int[][][] getDrawingObjects() {
		int i = Math.abs(randomGenerator.nextInt() % 100);
		int[][][] pointsXY = new int[i][2][2];
		for (int j = 0; j < i; j++) {
			pointsXY[j][0][0] = randomGenerator.nextInt() % width;
			pointsXY[j][0][1] = randomGenerator.nextInt() % height;
			pointsXY[j][1][0] = randomGenerator.nextInt() % width;
			pointsXY[j][1][1] = randomGenerator.nextInt() % height;
		}
		return pointsXY;
	}

}
