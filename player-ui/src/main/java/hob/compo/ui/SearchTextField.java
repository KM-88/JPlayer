/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import framew.global.ApplicationConstantsUI;

/**
 *
 * @author kranti
 */
public class SearchTextField extends JTextField {

    public SearchTextField() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchQuery = ((JTextField) e.getSource()).getText();
                    ViewComponent view = (ViewComponent) ApplicationConstantsUI.getByName("defaultViewModel");
                    view.searchUpdate(searchQuery);
                }
            }
        });
    }
}
