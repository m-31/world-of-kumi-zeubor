package com.meyling.zeubor.core.player.basis;


public interface AbstractPlayerCreator  {

    public AbstractPlayer create();
    
    public String getName();
    
    public String getFileName();
    
    public String getModificationTime();
    
    public String getMd5();

}
