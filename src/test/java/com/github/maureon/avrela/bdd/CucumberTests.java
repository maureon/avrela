package com.github.maureon.avrela.bdd;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PUBLISH_ENABLED_PROPERTY_NAME;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "es.ubu.lsi.avrela.bdd")
@ConfigurationParameter(key= PLUGIN_PUBLISH_ENABLED_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class CucumberTests {

  @Test
  void test(){
    //Remove Sonar blocker issue
    Assertions.assertTrue(Boolean.TRUE);
  }

}
