package es.ubu.lsi.avrela.css.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rubric {

  public static Integer getExpectedRubricValue(Map<String, String> teamWorkEvaluationRow) {
    String[] ratingScaleValues = {"0", "1", "2"};
    Integer expectedTeamWorkRubricValue = 0;
    for (String ratingScaleValue : ratingScaleValues) {
      if ("X".equals(teamWorkEvaluationRow.get(ratingScaleValue))) {
        break;
      }
      expectedTeamWorkRubricValue++;
    }
    return expectedTeamWorkRubricValue;
  }

  public static List<Double> toCriteriaScale(Map<String, String> dataTableRow) {
    final String[] ratingScaleValues = {"0", "1", "2"};
    List<Double> result = new ArrayList<>();
    for(String ratingScaleValue: ratingScaleValues){
      if (!"None".equals(dataTableRow.get(ratingScaleValue))){
        result.add(Double.parseDouble(dataTableRow.get(ratingScaleValue)));
      } else {
        result.add(Double.MIN_VALUE);
      }
    }
    return result;
  }

  /**
   *
   * @param criteriaScale
   * @param criteriaValue
   * @return evaluation as criteria scale position.
   */
  public static Integer evaluateCriteria(List<Double> criteriaScale, Double criteriaValue) {
    Integer result = 0;
    Integer scaleValueCurrent = 0;
    Boolean finish = false;
    //Get scale value
    while(!finish && scaleValueCurrent <= criteriaScale.size()-1){
      if(criteriaValue >= criteriaScale.get(scaleValueCurrent)){
        if(Double.MIN_VALUE != criteriaScale.get(scaleValueCurrent)){
          result = scaleValueCurrent;
          if (scaleValueCurrent == criteriaScale.size()-1 ){
            finish = true;
          }else{
            scaleValueCurrent++;
          }
        }else{
          //None value detected
          scaleValueCurrent++;
        }
      }else{
        finish = true;
      }
    }
    return result;
  }
}
