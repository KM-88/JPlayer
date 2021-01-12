/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.ui;

import java.awt.Component;

/**
 *
 * @author kranti
 */
public interface ViewComponent {
    
    void initialize();
    
    void refreshData();
    
    Component getRegisteredComponent();
    
    void searchUpdate(String searchText);
    
}
