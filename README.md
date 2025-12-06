======  Glaucoma Screening App - README =====
Overview
Java Swing desktop application for glaucoma risk assessment and education. Provides interactive visual field testing and personalized risk feedback.

Features
Educational Content: Glaucoma information, symptoms, risk factors

Visual Field Test: 3-question image comparison test

Risk Assessment: Automatic scoring with Low/Moderate/High risk categories

Personalized Recommendations: Medical advice based on results

Feedback System: Submit feedback to Google Sheets

Professional UI: Clean, medical-themed interface

Architecture
text
app/
├── GlaucomaScreeningApp.java  # Main controller
├── ScreeningModel.java        # Data model & business logic
├── HomePanel.java            # Education panel
├── TestPanel.java           # Interactive test
├── ResultsPanel.java        # Results display
├── FeedbackPanel.java       # Feedback form
└── ImageLoader.java         # Image handler
Quick Start
bash
# Compile
javac app/*.java

# Run
java app.GlaucomaScreeningApp
Requirements
Java JDK 8+

Image files in images/ folder

Internet connection (for feedback submission)

⚙️ Configuration
Update Google Sheets credentials in FeedbackPanel.java:

java
private static final String GOOGLE_SCRIPT_URL = "your_url_here";
private static final String SECRET_KEY = "your_key_here";
Usage Flow
Learn: Read glaucoma information on Home page

Test: Complete 3-question visual comparison test

Results: View risk assessment and recommendations

Feedback: Submit optional feedback

Important Notes
Medical Disclaimer: Educational tool only - NOT for diagnosis

Image Requirements: Place JPG images in images/ folder

Test Data: 8 questions total, scores calculated automatically

Key Components
CardLayout: Panel switching

SwingWorker: Background tasks

HTTP POST: Google Sheets integration

Model-View-Controller: Clean architecture

Support
Check console for error messages

Verify image file paths

Ensure Google Script URL is accessible

For educational purposes only. Consult medical professionals for health concerns.
