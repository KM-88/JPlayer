/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import framew.global.ApplicationConstantsUI;
import hob.compo.engine.PlayerThreadGenerator;
import hob.compo.obj.PlayListItems;
import hob.compo.player.PlayList;


/**
 *
 * @author kranti
 */
public class MainUIComponents extends javax.swing.JFrame{

    private static JFrame frame;
    private static ViewComponent libraryPaneViewComponent;
    private static JTextField searchTextField;
    private static JLabel labelNowPlaying;
    private static JProgressBar nowPlayingProgressBar;
    private static JButton playButton;

    private static final int length = 800, width = 550;

    /**
     * Initialize the contents of the frame.
     */
    public static void initialize() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setBounds(100, 100, length, width);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("JPlayer");
        
        SpringLayout springLayout = new SpringLayout();
        frame.getContentPane().setLayout(springLayout);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setForeground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.NORTH, controlPanel, 10, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, controlPanel, 10, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, controlPanel, 44, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, controlPanel, -10, SpringLayout.EAST, frame.getContentPane());
        frame.getContentPane().add(controlPanel);

        JPanel libraryPanel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, libraryPanel, 0, SpringLayout.SOUTH, controlPanel);
        springLayout.putConstraint(SpringLayout.WEST, libraryPanel, 0, SpringLayout.WEST, controlPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, libraryPanel, -10, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, libraryPanel, 0, SpringLayout.EAST, controlPanel);
        SpringLayout sl_controlPanel = new SpringLayout();
        controlPanel.setLayout(sl_controlPanel);

        playButton = new JButton("Play");
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                JButton button = (JButton) arg0.getComponent();
                String status = button.getText();
                if (status.equals("Play")) {
                    PlayerThreadGenerator.play();
                } else {
                    PlayerThreadGenerator.pause();
                }
                button.setText(button.getText().equals("Play") ? "Pause" : "Play");
            }
        });
        sl_controlPanel.putConstraint(SpringLayout.NORTH, playButton, 10, SpringLayout.NORTH, controlPanel);
        sl_controlPanel.putConstraint(SpringLayout.SOUTH, playButton, -2, SpringLayout.SOUTH, controlPanel);
        controlPanel.add(playButton);

        JButton nextButton = new JButton("Next");
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                PlayList.playNext();
            }
        });
        sl_controlPanel.putConstraint(SpringLayout.NORTH, nextButton, 0, SpringLayout.NORTH, playButton);
        sl_controlPanel.putConstraint(SpringLayout.WEST, nextButton, 2, SpringLayout.EAST, playButton);
        sl_controlPanel.putConstraint(SpringLayout.SOUTH, nextButton, 0, SpringLayout.SOUTH, playButton);

        controlPanel.add(nextButton);

        JButton previousButton = new JButton("Previous");
        previousButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                PlayList.playPrevious();
                updatePlayingComponents();
            }
        });
        sl_controlPanel.putConstraint(SpringLayout.WEST, playButton, 2, SpringLayout.EAST, previousButton);
        sl_controlPanel.putConstraint(SpringLayout.NORTH, previousButton, 10, SpringLayout.NORTH, controlPanel);
        sl_controlPanel.putConstraint(SpringLayout.WEST, previousButton, 0, SpringLayout.WEST, controlPanel);
        sl_controlPanel.putConstraint(SpringLayout.EAST, previousButton, 73, SpringLayout.WEST, controlPanel);
        sl_controlPanel.putConstraint(SpringLayout.SOUTH, previousButton, 0, SpringLayout.SOUTH, playButton);
        controlPanel.add(previousButton);

        labelNowPlaying = new JLabel("Now Playing");
        sl_controlPanel.putConstraint(SpringLayout.WEST, labelNowPlaying, 2, SpringLayout.EAST, nextButton);
        sl_controlPanel.putConstraint(SpringLayout.SOUTH, labelNowPlaying, -2, SpringLayout.SOUTH, controlPanel);
        sl_controlPanel.putConstraint(SpringLayout.EAST, labelNowPlaying, -100, SpringLayout.EAST, controlPanel);
        controlPanel.add(labelNowPlaying);

        nowPlayingProgressBar = new JProgressBar();
        sl_controlPanel.putConstraint(SpringLayout.NORTH, labelNowPlaying, 2, SpringLayout.SOUTH, nowPlayingProgressBar);
        sl_controlPanel.putConstraint(SpringLayout.EAST, nowPlayingProgressBar, -100, SpringLayout.EAST, controlPanel);
        sl_controlPanel.putConstraint(SpringLayout.WEST, nowPlayingProgressBar, 2, SpringLayout.EAST, nextButton);
        controlPanel.add(nowPlayingProgressBar);

        searchTextField = new SearchTextField();
        sl_controlPanel.putConstraint(SpringLayout.NORTH, searchTextField, 0, SpringLayout.NORTH, playButton);
        sl_controlPanel.putConstraint(SpringLayout.WEST, searchTextField, 2, SpringLayout.EAST, labelNowPlaying);
        controlPanel.add(searchTextField);
        searchTextField.setColumns(10);
        frame.getContentPane().add(libraryPanel);
        libraryPanel.setLayout(new GridLayout(1, 0, 0, 0));

        JSplitPane libraryPanelSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        libraryPanelSplitPane.setResizeWeight(0.15);
        libraryPanel.add(libraryPanelSplitPane);

        JSplitPane playListAndVisualizationSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        //playListAndVisualizationSplitPane.setResizeWeight(0.15);
        JPanel playListPanel = (JPanel) ApplicationConstantsUI.getBean("playListPanel");
        playListAndVisualizationSplitPane.setTopComponent(playListPanel);
        JPanel visualizationPanel = (JPanel) ApplicationConstantsUI.getBean("visualizationPanel");
        visualizationPanel.setSize(300, 400);
        visualizationPanel.setForeground(Color.red);
        playListAndVisualizationSplitPane.setBottomComponent(visualizationPanel);
        
        libraryPanelSplitPane.setLeftComponent(playListAndVisualizationSplitPane);

        JScrollPane libraryPane = new JScrollPane();
        libraryPaneViewComponent = (ViewComponent) ApplicationConstantsUI.getBean("defaultViewComponent");
        if(null != libraryPaneViewComponent.getRegisteredComponent()){
        	libraryPane.add( libraryPaneViewComponent.getRegisteredComponent());
        }
        else
        	System.err.println("Panel is NULL");
        
        libraryPanelSplitPane.setRightComponent(libraryPaneViewComponent.getRegisteredComponent());
        
        
        frame.repaint();
    }

    public static void updatePlayingComponents() {
        PlayListItems item = PlayerThreadGenerator.getCurrentPlayListItem();
        if (null != item) {
            labelNowPlaying.setText(item.getResourceName());
            labelNowPlaying.repaint();
            playButton.setText("Pause");
            nowPlayingProgressBar.setIndeterminate(true);
        }
    }

}
