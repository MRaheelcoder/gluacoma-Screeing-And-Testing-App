package app;
// TestPanel.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TestPanel extends JPanel {
    private GlaucomaScreeningApp app;
    private ScreeningModel model;
    private int currentQuestion = 1;
    private final int TOTAL_QUESTIONS = 3;

    private OptionCard[] optionCards;
    private JPanel optionsGrid;
    private JLabel questionCounterLabel;
    private JLabel mainImageLabel;
    private JButton nextButton;
    private JButton previousButton;

    // Color constants for consistent theme
    private final Color BUTTON_BACKGROUND = new Color(13, 77, 156);
    private final Color BUTTON_FOREGROUND = new Color(255, 255, 255);
    private final Color BUTTON_HOVER = new Color(10, 60, 130);
    private final Color CARD_SELECTED_BG = new Color(230, 240, 255);
    private final Color CARD_SELECTED_BORDER = new Color(13, 77, 156);
    private final Color CARD_HOVER_BG = new Color(245, 248, 255);
    private final Color QUESTION_COUNTER_BG = new Color(13, 77, 156);
    private final Color TITLE_COLOR = new Color(13, 77, 156);

    // Image mapping
    private final String[][] QUESTION_OPTION_IMAGES = {
            // Question 1 options
            {
                    "images/fqi_1sto.jpg", // Normal Vision
                    "images/fqi_so.jpg",   // Early Peripheral Vision Loss
                    "images/fqi_to.jpg",   // Moderate Field Defects
                    "images/fqi_fo.jpg"    // Advanced Tunnel Vision
            },
            // Question 2 options
            {
                    "images/sqi_1sto.jpg", // Normal Vision
                    "images/sqi_so.jpg",   // Early Peripheral Vision Loss
                    "images/sqi_to.jpg",   // Moderate Field Defects
                    "images/sqi_to.jpg"    // Advanced Tunnel Vision (fallback since sqi_to.jpg is missing)
            },
            // Question 3 options
            {
                    "images/tqi_1sto.jpg", // Normal Vision
                    "images/tqi_so.jpg",   // Early Peripheral Vision Loss
                    "images/tqi_to.jpg",   // Moderate Field Defects
                    "images/tqi_fo.jpg"    // Advanced Tunnel Vision
            }
    };

    private final String[] MAIN_IMAGES = {
            "images/fqi.jpg",      // Question 1
            "images/sqi.jpg",      // Question 2
            "images/tqi.jpg"       // Question 3
    };

    public TestPanel(GlaucomaScreeningApp app, ScreeningModel model) {
        this.app = app;
        this.model = model;
        this.optionCards = new OptionCard[4];
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top section with title and question counter
        add(createTopPanel(), BorderLayout.NORTH);

        // Center section with main contento
        add(createCenterPanel(), BorderLayout.CENTER);

        // Bottom section with navigation
        add(createBottomPanel(), BorderLayout.SOUTH);

        updateQuestionDisplay();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Question title on the left
        JLabel titleLabel = new JLabel("Visual Field Test");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TITLE_COLOR);

        // Question counter on the right (rounded badge)
        questionCounterLabel = new JLabel();
        questionCounterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        questionCounterLabel.setForeground(BUTTON_FOREGROUND);
        questionCounterLabel.setBackground(QUESTION_COUNTER_BG);
        questionCounterLabel.setOpaque(true);
        questionCounterLabel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(QUESTION_COUNTER_BG, 1, true),
                new EmptyBorder(8, 15, 8, 15)
        ));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(questionCounterLabel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        centerPanel.setBackground(Color.WHITE);

        // Left section - Main image with button and caption
        centerPanel.add(createLeftPanel());

        // Right section - Options grid
        centerPanel.add(createRightPanel());

        return centerPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        // Main image panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Create main image label
        mainImageLabel = new JLabel();
        mainImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainImageLabel.setPreferredSize(new Dimension(400, 300));

        // "Observe This Image" button with consistent theme
        JButton observeButton = new JButton("Observe This Image");
        observeButton.setBackground(BUTTON_BACKGROUND);
        observeButton.setForeground(BUTTON_FOREGROUND);
        observeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        observeButton.setFocusPainted(false);
        observeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        observeButton.setOpaque(true);
        observeButton.setBorderPainted(false);
        observeButton.setMaximumSize(new Dimension(200, 40));
        observeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add hover effect for button
        observeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                observeButton.setBackground(BUTTON_BACKGROUND);
            }
            public void mouseExited(MouseEvent evt) {
                observeButton.setBackground(BUTTON_BACKGROUND);
            }
        });

        observeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Please carefully observe the visual field representation in this image. " +
                            "Compare it with the options on the right and select the one that looks most similar.",
                    "Observation Instructions",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Caption below image
        JLabel captionLabel = new JLabel("Observe the visual field representation in this image", SwingConstants.CENTER);
        captionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        captionLabel.setForeground(Color.DARK_GRAY);
        captionLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Add components to image panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        buttonPanel.add(observeButton);

        imagePanel.add(buttonPanel, BorderLayout.NORTH);
        imagePanel.add(mainImageLabel, BorderLayout.CENTER);
        imagePanel.add(captionLabel, BorderLayout.SOUTH);

        leftPanel.add(imagePanel);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        // Create options grid
        optionsGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        optionsGrid.setBackground(Color.WHITE);
        optionsGrid.setBorder(new EmptyBorder(0, 10, 0, 10));

        // Option titles (same for all questions)
        String[] optionTitles = {
                "Normal Vision",
                "Early Peripheral Vision Loss",
                "Moderate Field Defects",
                "Advanced Tunnel Vision"
        };

        String[] optionTypes = {
                "normal",
                "early",
                "moderate",
                "advanced"
        };

        // Create option cards - initially with question 1 images
        for (int i = 0; i < 4; i++) {
            OptionCard card = new OptionCard(optionTitles[i], optionTypes[i], QUESTION_OPTION_IMAGES[0][i]);
            optionCards[i] = card;
            final int index = i;

            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onOptionSelected(currentQuestion, optionTypes[index]);
                    highlightSelectedCard(card);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!card.isSelected()) {
                        card.setBackground(CARD_HOVER_BG);
                        card.setBorder(BorderFactory.createCompoundBorder(
                                new LineBorder(BUTTON_BACKGROUND, 1),
                                new EmptyBorder(10, 10, 10, 10)
                        ));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!card.isSelected()) {
                        card.setBackground(Color.WHITE);
                        card.setBorder(BorderFactory.createCompoundBorder(
                                new LineBorder(Color.LIGHT_GRAY, 1),
                                new EmptyBorder(10, 10, 10, 10)
                        ));
                    }
                }
            });

            optionsGrid.add(card);
        }

        rightPanel.add(optionsGrid, BorderLayout.CENTER);
        return rightPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Previous button with consistent theme
        previousButton = new JButton("Previous");
        styleNavigationButton(previousButton);
        previousButton.addActionListener(e -> navigateToPreviousQuestion());

        // Next button with consistent theme
        nextButton = new JButton("Next");
        styleNavigationButton(nextButton);
        nextButton.addActionListener(e -> navigateToNextQuestion());

        bottomPanel.add(previousButton);
        bottomPanel.add(nextButton);

        return bottomPanel;
    }

    private void styleNavigationButton(JButton button) {
        button.setBackground(BUTTON_BACKGROUND);
        button.setForeground(BUTTON_FOREGROUND);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BUTTON_HOVER, 1),
                new EmptyBorder(10, 25, 10, 25)
        ));
        button.setOpaque(true);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(BUTTON_BACKGROUND);
            }
        });
    }

    private void updateQuestionDisplay() {
        // Update question counter
        questionCounterLabel.setText("Question " + currentQuestion + " of " + TOTAL_QUESTIONS);

        // Update navigation buttons
        previousButton.setEnabled(currentQuestion > 1);

        if (currentQuestion == TOTAL_QUESTIONS) {
            nextButton.setText("Show Results");
        } else {
            nextButton.setText("Next");
        }

        // Check if current question is answered to enable Next button
        String currentSelection = model.getSelection(currentQuestion);
        nextButton.setEnabled(currentSelection != null || currentQuestion == TOTAL_QUESTIONS);

        // Update main image based on question
        updateMainImage();

        // Update option images for current question
        updateOptionImages();

        // Restore selection for current question
        restoreSelection();
    }

    private void updateMainImage() {
        // Load main image for current question
        if (currentQuestion >= 1 && currentQuestion <= MAIN_IMAGES.length) {
            String imagePath = MAIN_IMAGES[currentQuestion - 1];
            ImageIcon mainImage = loadImage(imagePath, 400, 300);

            if (mainImage != null) {
                mainImageLabel.setIcon(mainImage);
                mainImageLabel.setText("");
            } else {
                // Fallback to colored placeholder if image not found
                createFallbackImage(mainImageLabel, "Main Test " + currentQuestion, 400, 300, new Color(220, 230, 255));
            }
        }
    }

    private void updateOptionImages() {
        // Update option card images for current question
        if (currentQuestion >= 1 && currentQuestion <= QUESTION_OPTION_IMAGES.length) {
            String[] currentOptionImages = QUESTION_OPTION_IMAGES[currentQuestion - 1];

            for (int i = 0; i < optionCards.length && i < currentOptionImages.length; i++) {
                OptionCard card = optionCards[i];
                String imagePath = currentOptionImages[i];

                // Load and set the new image
                ImageIcon optionImage = loadImage(imagePath, 120, 80);
                if (optionImage != null) {
                    card.getImageLabel().setIcon(optionImage);
                    card.getImageLabel().setText("");
                } else {
                    // Fallback to colored placeholder
                    Color bgColor;
                    switch (card.getOptionType()) {
                        case "normal": bgColor = new Color(220, 255, 220); break;
                        case "early": bgColor = new Color(255, 255, 200); break;
                        case "moderate": bgColor = new Color(255, 220, 200); break;
                        case "advanced": bgColor = new Color(255, 200, 200); break;
                        default: bgColor = Color.LIGHT_GRAY;
                    }
                    createFallbackImage(card.getImageLabel(), card.getTitle(), 120, 80, bgColor);
                }
            }
        }
    }

    private void restoreSelection() {
        // Clear all selections first
        for (OptionCard card : optionCards) {
            card.setSelected(false);
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(Color.LIGHT_GRAY, 1),
                    new EmptyBorder(10, 10, 10, 10)
            ));
        }

        // Restore selection for current question if exists
        String currentSelection = model.getSelection(currentQuestion);
        if (currentSelection != null) {
            for (OptionCard card : optionCards) {
                if (card.getOptionType().equals(currentSelection)) {
                    card.setSelected(true);
                    card.setBackground(CARD_SELECTED_BG);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            new LineBorder(CARD_SELECTED_BORDER, 2),
                            new EmptyBorder(10, 10, 10, 10)
                    ));
                    break;
                }
            }
        }
    }

    private void navigateToPreviousQuestion() {
        if (currentQuestion > 1) {
            currentQuestion--;
            updateQuestionDisplay();
        }
    }

    private void navigateToNextQuestion() {
        if (currentQuestion < TOTAL_QUESTIONS) {
            currentQuestion++;
            updateQuestionDisplay();
        } else {
            // Show results when on last question
            app.showSection("RESULTS");
        }
    }

    public void onOptionSelected(int questionNumber, String selectedValue) {
        // Store selection in model
        model.setSelection(questionNumber, selectedValue);

        // Enable next button
        nextButton.setEnabled(true);
    }

    private void highlightSelectedCard(OptionCard selectedCard) {
        // Remove highlight from all cards
        for (OptionCard card : optionCards) {
            card.setSelected(false);
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(Color.LIGHT_GRAY, 1),
                    new EmptyBorder(10, 10, 10, 10)
            ));
        }

        // Highlight selected card
        selectedCard.setSelected(true);
        selectedCard.setBackground(CARD_SELECTED_BG);
        selectedCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CARD_SELECTED_BORDER, 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
    }

    private ImageIcon loadImage(String imagePath, int width, int height) {
        try {
            // Try to load from classpath/resources
            java.net.URL imageURL = getClass().getClassLoader().getResource(imagePath);
            if (imageURL != null) {
                ImageIcon originalIcon = new ImageIcon(imageURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                System.err.println("Image not found: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imagePath + " - " + e.getMessage());
        }
        return null;
    }

    private void createFallbackImage(JLabel label, String text, int width, int height, Color color) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fill background
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);

        // Add border
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(1, 1, width - 3, height - 3);

        // Add text
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Split long text into multiple lines
        String[] words = text.split(" ");
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();

        for (String word : words) {
            if (line1.length() + word.length() <= 15) {
                if (line1.length() > 0) line1.append(" ");
                line1.append(word);
            } else {
                if (line2.length() > 0) line2.append(" ");
                line2.append(word);
            }
        }

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth1 = fm.stringWidth(line1.toString());
        g2d.drawString(line1.toString(), (width - textWidth1) / 2, height / 2 - 5);

        if (line2.length() > 0) {
            int textWidth2 = fm.stringWidth(line2.toString());
            g2d.drawString(line2.toString(), (width - textWidth2) / 2, height / 2 + 10);
        }

        g2d.dispose();
        label.setIcon(new ImageIcon(image));
        label.setText("");
    }

    public void resetTest() {
        currentQuestion = 1;
        updateQuestionDisplay();
    }

    // Inner class for Option Cards
    private class OptionCard extends JPanel {
        private JLabel imageLabel;
        private JLabel titleLabel;
        private String optionType;
        private String title;
        private boolean isSelected = false;

        public OptionCard(String title, String optionType, String imagePath) {
            this.optionType = optionType;
            this.title = title;
            initializeCard(title, imagePath);
        }

        private void initializeCard(String title, String imagePath) {
            setLayout(new BorderLayout(5, 5));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(Color.LIGHT_GRAY, 1),
                    new EmptyBorder(10, 10, 10, 10)
            ));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Create preview image
            imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setPreferredSize(new Dimension(120, 80));

            // Load the actual image
            ImageIcon optionImage = loadImage(imagePath, 120, 80);
            if (optionImage != null) {
                imageLabel.setIcon(optionImage);
            } else {
                // Fallback to colored placeholder
                Color bgColor;
                switch (optionType) {
                    case "normal": bgColor = new Color(220, 255, 220); break;
                    case "early": bgColor = new Color(255, 255, 200); break;
                    case "moderate": bgColor = new Color(255, 220, 200); break;
                    case "advanced": bgColor = new Color(255, 200, 200); break;
                    default: bgColor = Color.LIGHT_GRAY;
                }
                createFallbackImage(imageLabel, title, 120, 80, bgColor);
            }

            // Create title label
            titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            titleLabel.setForeground(Color.DARK_GRAY);

            add(imageLabel, BorderLayout.CENTER);
            add(titleLabel, BorderLayout.SOUTH);
        }

        public String getOptionType() {
            return optionType;
        }

        public String getTitle() {
            return title;
        }

        public JLabel getImageLabel() {
            return imageLabel;
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;
        }

        public boolean isSelected() {
            return isSelected;
        }
    }
}