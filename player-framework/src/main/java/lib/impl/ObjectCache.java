/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.impl;

import lib.inf.Cache;
import lib.inf.DataSource;

/**
 *
 * @author kranti
 */
public abstract class ObjectCache implements Cache{

    protected String cacheName;

    protected DataSource dataSource;
    
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }



}
