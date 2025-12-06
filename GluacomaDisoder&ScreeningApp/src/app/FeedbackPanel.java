package app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackPanel extends JPanel {

    private GlaucomaScreeningApp parent;

    // GOOGLE SHEETS SCRIPT URL & SECRET
    private static final String GOOGLE_SCRIPT_URL =
            "https://script.google.com/macros/s/AKfycbw5dednDNoFcPXPvKfULynV-TpXn0RTrNsYw6auCCJ-cV5c4AgYxwkSkttp1BdZChdh/exec";
    private static final String SECRET_KEY = "s3cr3t_abc_2025";

    private JComboBox<String> ratingComboBox;
    private JTextArea feedbackArea;
    private JTextField nameField;
    private JTextField emailField;
    private JButton submitButton;

    public FeedbackPanel(GlaucomaScreeningApp parent) {
        this.parent = parent;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 60, 25, 60));
        contentPanel.setBackground(Color.WHITE);

        addFeedbackForm(contentPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addFeedbackForm(JPanel panel) {

        JLabel titleLabel = new JLabel("Submit Your Feedback");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 90, 160));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.setMaximumSize(new Dimension(700, 600));

        // Form fields
        nameField = createTextField();
        emailField = createTextField();

        String[] ratings = {"Select Rating", "Excellent", "Very Good", "Good", "Fair", "Poor"};
        ratingComboBox = new JComboBox<>(ratings);
        ratingComboBox.setMaximumSize(new Dimension(600, 40));
        ratingComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        feedbackArea = new JTextArea(5, 20);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane feedbackScroll = new JScrollPane(feedbackArea);
        feedbackScroll.setMaximumSize(new Dimension(600, 140));

        submitButton = createSubmitButton();

        // Add fields
        addLabelAndField(form, "Your Name *", nameField);
        addLabelAndField(form, "Your Email *", emailField);
        addLabelAndField(form, "Rating *", ratingComboBox);
        addLabelAndField(form, "Your Feedback *", feedbackScroll);

        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(Box.createVerticalStrut(20));
        form.add(submitButton);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(form);
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(600, 40));
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private void addLabelAndField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panel.add(label);
        panel.add(field);
    }

    private JButton createSubmitButton() {
        JButton btn = new JButton("Submit Feedback");

        btn.setBackground(new Color(255, 255, 255));
        btn.setForeground(new Color(13,77,156));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 140, 250));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0, 120, 215));
            }
        });

        btn.addActionListener(new SubmitListener());

        return btn;
    }

    // ---------------- FORM VALIDATION ----------------

    private boolean validateForm() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String feedback = feedbackArea.getText().trim();

        if (name.isEmpty() || name.length() < 2) {
            showError("Enter valid name");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("Enter valid email");
            return false;
        }
        if (ratingComboBox.getSelectedIndex() == 0) {
            showError("Select a rating");
            return false;
        }
        if (feedback.length() < 5) {
            showError("Feedback is too short");
            return false;
        }
        return true;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ---------------- SUBMIT HANDLER ----------------

    private class SubmitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!validateForm()) return;
            submitToGoogleSheets();
        }
    }

    private void submitToGoogleSheets() {
        submitButton.setText("Submitting...");
        submitButton.setEnabled(false);

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String rating = (String) ratingComboBox.getSelectedItem();
                    String feedback = feedbackArea.getText().trim();

                    String payload = "secret=" + SECRET_KEY +
                            "&name=" + name +
                            "&email=" + email +
                            "&rating=" + rating +
                            "&message=" + feedback;

                    URL url = new URL(GOOGLE_SCRIPT_URL);
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                    c.setRequestMethod("POST");
                    c.setDoOutput(true);
                    c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    c.getOutputStream().write(payload.getBytes(StandardCharsets.UTF_8));

                    return c.getResponseCode() == 200;

                } catch (Exception ex) {
                    return false;
                }
            }

            @Override
            protected void done() {
                submitButton.setEnabled(true);
                submitButton.setText("Submit Feedback");

                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(FeedbackPanel.this,
                                "Feedback submitted successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        resetForm();
                    } else {
                        JOptionPane.showMessageDialog(FeedbackPanel.this,
                                "Error submitting feedback.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ignored) {}
            }
        };
        worker.execute();
    }

    private void resetForm() {
        nameField.setText("");
        emailField.setText("");
        ratingComboBox.setSelectedIndex(0);
        feedbackArea.setText("");
    }
}
