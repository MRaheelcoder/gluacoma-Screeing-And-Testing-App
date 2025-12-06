package app;
// GlaucomaScreeningApp.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GlaucomaScreeningApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ScreeningModel model;

    private HomePanel homePanel;
    private TestPanel testPanel;
    private ResultsPanel resultsPanel;
    private FeedbackPanel feedbackPanel;

    public GlaucomaScreeningApp() {
        initializeApp();
        setupUI();
    }

    private void initializeApp() {
        model = new ScreeningModel();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        homePanel = new HomePanel(this);
        testPanel = new TestPanel(this, model);
        resultsPanel = new ResultsPanel(this, model);
        feedbackPanel = new FeedbackPanel(this);
    }

    private void setupUI() {
        setTitle("Glaucoma Screening & Testing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        mainPanel.add(homePanel, "HOME");
        mainPanel.add(testPanel, "TEST");
        mainPanel.add(resultsPanel, "RESULTS");
        mainPanel.add(feedbackPanel, "FEEDBACK");

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

        showSection("HOME");
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(13, 77, 156));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setSize(1000, 120);

        JLabel titleLabel = new JLabel("Glaucoma Screening & Testing", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        JLabel subtitleLabel = new JLabel("Educate yourself, take a quick test, and share your feedback", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);

        JPanel navPanel = createNavigationPanel();

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        headerPanel.add(navPanel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(13, 77, 156));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton homeBtn = createNavButton("Home", "HOME");
        JButton testBtn = createNavButton("Screening Test", "TEST");
        JButton feedbackBtn = createNavButton("Feedback", "FEEDBACK");

        navPanel.add(homeBtn);
        navPanel.add(testBtn);
        navPanel.add(feedbackBtn);

        return navPanel;
    }

    private JButton createNavButton(String text, String section) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(13, 77, 156));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(140, 35));
        button.setOpaque(true);
        button.setBorderPainted(true);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });

        button.addActionListener(e -> showSection(section));
        return button;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(13, 77, 156));
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setPreferredSize(new Dimension(1000, 60));

        JLabel copyrightLabel = new JLabel("© 2025 Glaucoma Screening & Testing | Educational Purposes Only", JLabel.CENTER);
        copyrightLabel.setForeground(Color.WHITE);
        copyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel disclaimerLabel = new JLabel("This app is not a substitute for professional medical advice.", JLabel.CENTER);
        disclaimerLabel.setForeground(Color.WHITE);
        disclaimerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        disclaimerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));

        footerPanel.add(copyrightLabel);
        footerPanel.add(disclaimerLabel);

        return footerPanel;
    }

    public void showSection(String sectionName) {
        cardLayout.show(mainPanel, sectionName);

        if ("TEST".equals(sectionName)) {
            testPanel.resetTest();
        } else if ("RESULTS".equals(sectionName)) {
            resultsPanel.updateResults();
        }
    }

    public ScreeningModel getModel() {
        return model;
    }

    public static void main(String[] args) {

            new GlaucomaScreeningApp().setVisible(true);

    }
}