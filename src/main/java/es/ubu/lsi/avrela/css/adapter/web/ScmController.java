package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.css.port.ScmEvaluationService;
import es.ubu.lsi.avrela.css.util.ScmCssDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ScmController {

  @GetMapping("/scm-css")
  public String index(Model model){
    WebScmCaseStudySimulation result = ScmCssDataGenerator.getWebScmCaseStudySimulation();
    model.addAttribute("webScmCaseStudySimulation", result);
    return "pages/scm-css";
  }

  @PostMapping("/scm-css")
  public String create(@ModelAttribute WebScmCaseStudySimulation sim, Model model){
    ScmEvaluationService scmCssEvaluationService = new ScmEvaluationService();
    try {
      WebScmCaseStudySimulation result = scmCssEvaluationService.evaluate(sim);
      model.addAttribute("webScmCaseStudySimulation", result);
    } catch (Exception exception){
      log.error("During SCM evaluation", exception);
      model.addAttribute("exception", exception);
    }
    return "pages/scm-css";
  }

}
