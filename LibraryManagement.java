import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LibraryManagement extends JFrame implements ActionListener {
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    private JButton addButton, viewButton, editButton, deleteButton, clearButton, exitButton;

    public LibraryManagement() {
        setTitle("📚 Library Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Library Book Entry Form", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        headerLabel.setForeground(new Color(0x1A237E));
        add(headerLabel, BorderLayout.NORTH);

        // === Book Detail Panel with Image Inside the Bordered Box ===
        JPanel bookDetailsPanel = new JPanel(new BorderLayout(10, 10));
        bookDetailsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Book Details", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16)));

        // Load and scale the image
        ImageIcon icon = new ImageIcon("book.png");
        Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Input fields panel
        JPanel fieldsPanel = new JPanel(new GridLayout(7, 2, 6, 6));
        textField1 = createInputField("Book ID", fieldsPanel);
        textField2 = createInputField("Book Title", fieldsPanel);
        textField3 = createInputField("Author", fieldsPanel);
        textField4 = createInputField("Publisher", fieldsPanel);
        textField5 = createInputField("Year of Publication", fieldsPanel);
        textField6 = createInputField("ISBN", fieldsPanel);
        textField7 = createInputField("Number of Copies", fieldsPanel);

        // Combine inside the same titled border
        JPanel innerPanel = new JPanel(new BorderLayout(10, 0));
        innerPanel.add(imageLabel, BorderLayout.WEST);
        innerPanel.add(fieldsPanel, BorderLayout.CENTER);
        bookDetailsPanel.add(innerPanel, BorderLayout.CENTER);

        add(bookDetailsPanel, BorderLayout.CENTER);

        // === Buttons ===
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        addButton = createButton("Add");
        viewButton = createButton("View");
        editButton = createButton("Edit");
        deleteButton = createButton("Delete");
        clearButton = createButton("Clear");
        exitButton = createButton("Exit");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JTextField createInputField(String label, JPanel panel) {
        panel.add(new JLabel(label, JLabel.RIGHT));
        JTextField field = new JTextField();
        panel.add(field);
        return field;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0x3949AB));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        try (Connection conn = DBConnection.getConnection()) {
            if (e.getSource() == addButton) {
                String sql = "INSERT INTO books VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, textField1.getText());
                pstmt.setString(2, textField2.getText());
                pstmt.setString(3, textField3.getText());
                pstmt.setString(4, textField4.getText());
                pstmt.setInt(5, Integer.parseInt(textField5.getText()));
                pstmt.setString(6, textField6.getText());
                pstmt.setInt(7, Integer.parseInt(textField7.getText()));
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Book added successfully!");
                clearFields();
            } else if (e.getSource() == viewButton) {
                String sql = "SELECT * FROM books";
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(sql);

                String[] columns = {"Book ID", "Title", "Author", "Publisher", "Year", "ISBN", "Copies"};
                rs.last();
                int rows = rs.getRow();
                rs.beforeFirst();
                Object[][] data = new Object[rows][7];
                int i = 0;
                while (rs.next()) {
                    data[i][0] = rs.getString("book_id");
                    data[i][1] = rs.getString("title");
                    data[i][2] = rs.getString("author");
                    data[i][3] = rs.getString("publisher");
                    data[i][4] = rs.getInt("year_of_publication");
                    data[i][5] = rs.getString("isbn");
                    data[i][6] = rs.getInt("number_of_copies");
                    i++;
                }

                JTable table = new JTable(data, columns);
                table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                table.setRowHeight(25);
                JScrollPane scrollPane = new JScrollPane(table);
                JFrame frame = new JFrame("📖 Book Records");
                frame.add(scrollPane);
                frame.setSize(900, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else if (e.getSource() == editButton) {
                String bookID = JOptionPane.showInputDialog(this, "Enter Book ID to edit:");
                String sql = "UPDATE books SET title=?, author=?, publisher=?, year_of_publication=?, isbn=?, number_of_copies=? WHERE book_id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, textField2.getText());
                pstmt.setString(2, textField3.getText());
                pstmt.setString(3, textField4.getText());
                pstmt.setInt(4, Integer.parseInt(textField5.getText()));
                pstmt.setString(5, textField6.getText());
                pstmt.setInt(6, Integer.parseInt(textField7.getText()));
                pstmt.setString(7, bookID);
                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "✏️ Book updated successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Book not found.");
                }
            } else if (e.getSource() == deleteButton) {
                String bookID = JOptionPane.showInputDialog(this, "Enter Book ID to delete:");
                String sql = "DELETE FROM books WHERE book_id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, bookID);
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "🗑️ Book deleted successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Book not found.");
                }
            } else if (e.getSource() == clearButton) {
                clearFields();
            } else if (e.getSource() == exitButton) {
                System.exit(0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagement());
    }
}
