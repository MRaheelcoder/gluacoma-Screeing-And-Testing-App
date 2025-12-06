package app;
// HomePanel.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private GlaucomaScreeningApp parent;

    public HomePanel(GlaucomaScreeningApp parent) {
        this.parent = parent;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        addContentSections(contentPanel);
        addTestButton(contentPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addContentSections(JPanel contentPanel) {
        String[] sections = {
                "What is Glaucoma?",
                "Glaucoma is a group of eye diseases that gradually damage the optic nerve - the part of the eye that transmits visual information to the brain. Often, this damage is caused by abnormally high pressure inside the eye (intraocular pressure). If left untreated, glaucoma can cause permanent vision loss and even blindness.",

                "Types of Glaucoma",
                "• Open-angle Glaucoma: The most common type. It develops slowly and painlessly, often with no symptoms until significant vision loss has occurred.\n" +
                        "• Angle-closure Glaucoma: Less common but more severe. It occurs suddenly and may cause eye pain, nausea, blurred vision, and halos around lights.\n" +
                        "• Normal-tension Glaucoma: Optic nerve damage occurs even though eye pressure is within the normal range.\n" +
                        "• Congenital Glaucoma: A rare form present at birth, often detected due to cloudy eyes or excessive tearing in infants.",

                "Symptoms",
                "Glaucoma is often called the \"silent thief of sight\" because early stages usually have no symptoms. However, advanced stages may show:\n" +
                        "• Gradual loss of peripheral (side) vision\n" +
                        "• Tunnel vision (in advanced stages)\n" +
                        "• Severe eye pain (in acute cases)\n" +
                        "• Headaches, nausea, and blurred vision",

                "Risk Factors",
                "• Age above 40 (risk increases with age)\n" +
                        "• Family history of glaucoma\n" +
                        "• High eye pressure (intraocular pressure)\n" +
                        "• Medical conditions such as diabetes or hypertension\n" +
                        "• Severe eye injuries or prolonged steroid use",

                "Treatment & Management",
                "While glaucoma damage cannot be reversed, early detection and treatment can slow or prevent further vision loss. Treatments include:\n" +
                        "• Eye drops: To reduce intraocular pressure.\n" +
                        "• Oral medications: Prescribed if eye drops are not sufficient.\n" +
                        "• Laser therapy: Helps improve eye fluid drainage.\n" +
                        "• Surgery: Creates new drainage pathways to lower eye pressure."
        };

        for (int i = 0; i < sections.length; i += 2) {
            JLabel titleLabel = new JLabel(sections[i]);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            titleLabel.setForeground(new Color(13, 77, 156));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

            JTextArea contentArea = new JTextArea(sections[i + 1]);
            contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false);
            contentArea.setBackground(Color.WHITE);
            contentArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);

            contentPanel.add(titleLabel);
            contentPanel.add(contentArea);
        }

        JLabel reminderLabel = new JLabel("➡️ Regular eye checkups are crucial for early detection of glaucoma, especially if you fall in the high-risk category.");
        reminderLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        reminderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        reminderLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contentPanel.add(reminderLabel);
    }

    private void addTestButton(JPanel contentPanel) {
        JButton testButton = new JButton("Go for a Test!");
        testButton.setBackground(new Color(255, 255, 255));
        testButton.setForeground(new Color(13,77,156));
        testButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        testButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        testButton.setPreferredSize(new Dimension(200, 45));
        testButton.setMaximumSize(new Dimension(200, 45));
        testButton.setFocusPainted(false);

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showSection("TEST");
            }
        });

        contentPanel.add(testButton);
    }
}