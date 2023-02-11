package com.example.simplehttpserver.config;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static ConfigurationManager myCurrentConfiguration;
    private ConfigurationManager getInstance(){
        if(myConfigurationManager==null){
            myConfigurationManager= new ConfigurationManager();
        }
        return myConfigurationManager;
    }
    /*
    Used to load a configuration file by the path provided
     */
    public void loadConfigurationFile(String FilePath){

    }
    /*
    *Returns the current loaded configuration
     */
}
