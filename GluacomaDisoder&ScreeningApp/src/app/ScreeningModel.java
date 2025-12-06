package app;
// ScreeningModel.java
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class ScreeningModel {
    private Map<Integer, String> userSelections;
    private int totalQuestions = 8;

    public ScreeningModel() {
        userSelections = new HashMap<>();
    }

    public void setSelection(int questionId, String selection) {
        userSelections.put(questionId, selection);
    }

    public String getSelection(int questionId) {
        return userSelections.get(questionId);
    }

    public boolean isQuestionAnswered(int questionId) {
        return userSelections.containsKey(questionId);
    }

    public int calculateRiskScore() {
        int score = 0;
        for (String selection : userSelections.values()) {
            switch (selection) {
                case "normal": score += 0; break;
                case "early": score += 1; break;
                case "moderate": score += 2; break;
                case "advanced": score += 3; break;
            }
        }
        return score;
    }

    public String getRiskCategory() {
        int score = calculateRiskScore();
        int maxScore = totalQuestions * 3; // 3 points per question max

        if (score == 0) return "Low Risk";
        else if (score <= maxScore * 0.33) return "Low Risk";
        else if (score <= maxScore * 0.66) return "Moderate Risk";
        else return "High Risk";
    }

    public List<String> getRecommendations() {
        String riskCategory = getRiskCategory();
        List<String> recommendations = new ArrayList<>();

        switch (riskCategory) {
            case "Low Risk":
                recommendations.add("Your selections indicate the test images appeared normal to you");
                recommendations.add("Schedule a comprehensive eye exam with an ophthalmologist if you haven't had one in the past 2 years");
                recommendations.add("Maintain a healthy lifestyle with regular exercise and a balanced diet");
                recommendations.add("Protect your eyes from UV exposure by wearing sunglasses outdoors");
                recommendations.add("Be aware of any changes in your vision and report them to an eye care professional");
                break;

            case "Moderate Risk":
                recommendations.add("You selected some images showing potential glaucoma signs");
                recommendations.add("Schedule an appointment with an eye care specialist for a comprehensive examination");
                recommendations.add("Monitor your eye health regularly and report any vision changes immediately");
                recommendations.add("Discuss your risk factors with your primary care physician");
                recommendations.add("Consider more frequent eye exams (annually or as recommended by your eye doctor)");
                break;

            case "High Risk":
                recommendations.add("Your selections indicate you identified multiple potential glaucoma signs");
                recommendations.add("Consult an ophthalmologist as soon as possible for a comprehensive eye examination");
                recommendations.add("Discuss your test results and risk factors with a healthcare professional");
                recommendations.add("Follow any treatment plans prescribed by your eye doctor meticulously");
                recommendations.add("Schedule regular follow-up appointments to monitor your eye health");
                break;
        }

        return recommendations;
    }

    public void clearSelections() {
        userSelections.clear();
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getAnsweredQuestionsCount() {
        return userSelections.size();
    }
}