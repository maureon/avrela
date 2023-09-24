package com.github.maureon.avrela.css.adapter.web;

import com.github.maureon.avrela.css.port.ApmCssEvaluationService;
import com.github.maureon.avrela.css.util.ApmCssDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ApmController {

  @GetMapping("/apm-css")
  public String index(Model model){
    WebApmCaseStudySimulation result = ApmCssDataGenerator.webApmCaseStudySimulationBeforeEvaluation();
    model.addAttribute("webApmCaseStudySimulation", result);
    return "pages/apm-css";
  }

  @PostMapping("/apm-css")
  public String create(@ModelAttribute WebApmCaseStudySimulation sim, Model model){
    ApmCssEvaluationService apmCssEvaluationService = new ApmCssEvaluationService();
    try{
      WebApmCaseStudySimulation result = apmCssEvaluationService.evaluate(sim);
      model.addAttribute("webApmCaseStudySimulation", result);
    }catch (Exception exception){
      log.error("During APM evaluation", exception);
      model.addAttribute("exception", exception);
    }
    return "pages/apm-css";
  }

}
