package app;
// ResultsPanel.java
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultsPanel extends JPanel {
    private GlaucomaScreeningApp parent;
    private ScreeningModel model;

    private JLabel riskLevelLabel;
    private JTextArea resultDescription;
    private JPanel recommendationsPanel;

    public ResultsPanel(GlaucomaScreeningApp parent, ScreeningModel model) {
        this.parent = parent;
        this.model = model;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        contentPanel.setBackground(Color.WHITE);

        addResultsContent(contentPanel);
        addActionButtons(contentPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addResultsContent(JPanel contentPanel) {
        JLabel titleLabel = new JLabel("Your Test Results");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(13, 77, 156));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Result summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(new Color(240, 247, 255));
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(26, 109, 204)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        summaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPanel.setMaximumSize(new Dimension(800, 200));

        JLabel resultTitle = new JLabel("Screening Results");
        resultTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        resultTitle.setForeground(new Color(13, 77, 156));

        resultDescription = new JTextArea();
        resultDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultDescription.setLineWrap(true);
        resultDescription.setWrapStyleWord(true);
        resultDescription.setEditable(false);
        resultDescription.setBackground(new Color(240, 247, 255));
        resultDescription.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        riskLevelLabel = new JLabel();
        riskLevelLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        riskLevelLabel.setOpaque(true);
        riskLevelLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        riskLevelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        summaryPanel.add(resultTitle);
        summaryPanel.add(Box.createVerticalStrut(10));
        summaryPanel.add(resultDescription);
        summaryPanel.add(Box.createVerticalStrut(10));
        summaryPanel.add(riskLevelLabel);

        // Recommendations panel
        JPanel recContainer = new JPanel();
        recContainer.setLayout(new BoxLayout(recContainer, BoxLayout.Y_AXIS));
        recContainer.setBackground(Color.WHITE);
        recContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        recContainer.setMaximumSize(new Dimension(800, 300));

        JLabel recTitle = new JLabel("Recommendations");
        recTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        recTitle.setForeground(new Color(13, 77, 156));
        recTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        recommendationsPanel = new JPanel();
        recommendationsPanel.setLayout(new BoxLayout(recommendationsPanel, BoxLayout.Y_AXIS));
        recommendationsPanel.setBackground(Color.WHITE);
        recommendationsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        recommendationsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        recContainer.add(recTitle);
        recContainer.add(Box.createVerticalStrut(15));
        recContainer.add(recommendationsPanel);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(summaryPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(recContainer);
    }

    private void addActionButtons(JPanel contentPanel) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(800, 60));

        JButton testAgainButton = new JButton("Take Test Again");
        testAgainButton.setBackground(new Color(13, 77, 156));
        testAgainButton.setForeground(Color.WHITE);
        testAgainButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        testAgainButton.setPreferredSize(new Dimension(150, 40));
        testAgainButton.addActionListener(e -> parent.showSection("TEST"));

        JButton feedbackButton = new JButton("Give Feedback");
        feedbackButton.setBackground(new Color(13, 77, 156));
        feedbackButton.setForeground(Color.WHITE);
        feedbackButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        feedbackButton.setPreferredSize(new Dimension(150, 40));
        feedbackButton.addActionListener(e -> parent.showSection("FEEDBACK"));

        buttonPanel.add(testAgainButton);
        buttonPanel.add(feedbackButton);

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(buttonPanel);
    }

    public void updateResults() {
        String riskCategory = model.getRiskCategory();
        int score = model.calculateRiskScore();
        int totalQuestions = model.getTotalQuestions();

        // Update risk level display
        riskLevelLabel.setText(riskCategory);

        switch (riskCategory) {
            case "Low Risk":
                riskLevelLabel.setBackground(new Color(212, 237, 218));
                riskLevelLabel.setForeground(new Color(21, 87, 36));
                break;
            case "Moderate Risk":
                riskLevelLabel.setBackground(new Color(255, 243, 205));
                riskLevelLabel.setForeground(new Color(133, 100, 4));
                break;
            case "High Risk":
                riskLevelLabel.setBackground(new Color(248, 215, 218));
                riskLevelLabel.setForeground(new Color(114, 28, 36));
                break;
        }

        // Update result description
        int disorderSelections = 0;
        for (int i = 1; i <= totalQuestions; i++) {
            String selection = model.getSelection(i);
            if (selection != null && !selection.equals("normal")) {
                disorderSelections++;
            }
        }

        if (disorderSelections == 0) {
            resultDescription.setText("Based on your image comparisons, here is your glaucoma risk assessment:");
        } else {
            resultDescription.setText("Based on your image comparisons (you identified " + disorderSelections +
                    " potential glaucoma signs), here is your glaucoma risk assessment:");
        }

        // Update recommendations
        recommendationsPanel.removeAll();
        List<String> recommendations = model.getRecommendations();

        for (String recommendation : recommendations) {
            JLabel recLabel = new JLabel("• " + recommendation);
            recLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            recLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            recLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            recommendationsPanel.add(recLabel);
        }

        recommendationsPanel.revalidate();
        recommendationsPanel.repaint();
    }
}