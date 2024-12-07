/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

// Import các lớp cần thiết cho chức năng của lớp LoginForm
import dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Lớp đại diện cho giao diện đăng nhập
public class LoginForm extends JFrame {
    // Khai báo các thành phần giao diện
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    // Constructor của lớp LoginForm
    public LoginForm() {
        setTitle("Đăng nhập"); // Đặt tiêu đề cửa sổ
        setSize(300, 150); // Đặt kích thước cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng cửa sổ khi nhấn nút đóng
        setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình

        // Tạo JPanel để chứa các thành phần giao diện
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Sắp xếp các thành phần theo chiều dọc

        // Khởi tạo các thành phần giao diện
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Đăng nhập");

        // Thêm các thành phần vào panel
        panel.add(new JLabel("Tên đăng nhập:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(txtPassword);
        panel.add(btnLogin);

        // Thêm panel vào cửa sổ
        add(panel);

        // Thêm sự kiện cho nút Đăng nhập
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy tên đăng nhập và mật khẩu từ các trường nhập liệu
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                // Kiểm tra thông tin đăng nhập
                if (kiemTraDangNhap(username, password)) {
                    // Nếu thông tin đăng nhập hợp lệ, hiển thị MainFrame và đóng cửa sổ đăng nhập
                    SwingUtilities.invokeLater(() -> {
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.setVisible(true);
                        dispose(); // Đóng cửa sổ đăng nhập
                    });
                } else {
                    // Nếu thông tin đăng nhập không hợp lệ, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(LoginForm.this, "Sai tên đăng nhập hoặc mật khẩu.");
                }
            }
        });
    }

    // Kiểm tra thông tin đăng nhập với cơ sở dữ liệu
    private boolean kiemTraDangNhap(String username, String password) {
        boolean isValid = false;

        try (Connection conn = DatabaseConnection.getConnection()) { // Kết nối đến cơ sở dữ liệu
            String sql = "SELECT * FROM login WHERE user = ? AND password = ?"; // Câu lệnh SQL để kiểm tra thông tin đăng nhập
            PreparedStatement stmt = conn.prepareStatement(sql); // Chuẩn bị câu lệnh SQL
            stmt.setString(1, username); // Gán giá trị cho tham số đầu tiên
            stmt.setString(2, password); // Gán giá trị cho tham số thứ hai

            ResultSet rs = stmt.executeQuery(); // Thực hiện câu lệnh SQL và lấy kết quả
            if (rs.next()) { // Kiểm tra nếu có kết quả
                isValid = true; // Đăng nhập hợp lệ
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // In lỗi ra console nếu có lỗi xảy ra
        }

        return isValid; // Trả về kết quả kiểm tra
    }

    // Phương thức main để chạy ứng dụng
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm(); // Tạo đối tượng LoginForm
            loginForm.setVisible(true); // Hiển thị cửa sổ đăng nhập
        });
    }
}
