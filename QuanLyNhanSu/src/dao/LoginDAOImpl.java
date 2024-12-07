/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao; // Khai báo package dao, nơi lưu trữ các lớp liên quan đến thao tác với cơ sở dữ liệu

import model.Login; // Import lớp Login từ package model để sử dụng trong các phương thức của LoginDAOImpl
import java.sql.Connection; // Import lớp Connection để làm việc với kết nối cơ sở dữ liệu
import java.sql.PreparedStatement; // Import lớp PreparedStatement để thực thi các câu lệnh SQL có tham số
import java.sql.ResultSet; // Import lớp ResultSet để lưu trữ và thao tác với kết quả truy vấn
import java.sql.SQLException; // Import lớp SQLException để xử lý các lỗi liên quan đến SQL
import java.util.logging.Level; // Import lớp Level để định nghĩa mức độ ghi log
import java.util.logging.Logger; // Import lớp Logger để ghi log các thông tin và lỗi

public class LoginDAOImpl implements LoginDAO { // Định nghĩa lớp LoginDAOImpl thực hiện các phương thức từ giao diện LoginDAO
    private static final Logger LOGGER = Logger.getLogger(LoginDAOImpl.class.getName()); // Khai báo Logger để ghi log lỗi và thông tin
    private Connection conn; // Khai báo đối tượng Connection để kết nối đến cơ sở dữ liệu

    public LoginDAOImpl() { // Constructor để khởi tạo đối tượng LoginDAOImpl và thiết lập kết nối cơ sở dữ liệu
        try {
            conn = DatabaseConnection.getConnection(); // Lấy kết nối cơ sở dữ liệu từ lớp DatabaseConnection
        } catch (SQLException e) { // Bắt lỗi SQLException nếu không thể kết nối
            LOGGER.log(Level.SEVERE, "Failed to connect to the database", e); // Ghi log lỗi với mức độ SEVERE
            throw new RuntimeException("Failed to connect to the database", e); // Ném lỗi RuntimeException nếu có lỗi
        }
    }

    @Override
    public Login getLoginByUser(String user) { // Phương thức lấy thông tin đăng nhập dựa trên tên người dùng
        String sql = "SELECT * FROM login WHERE user = ?"; // Câu lệnh SQL để lấy thông tin đăng nhập theo tên người dùng
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            pstmt.setString(1, user); // Đặt giá trị cho tham số tên người dùng
            try (ResultSet rs = pstmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả
                if (rs.next()) { // Nếu có kết quả
                    return new Login(
                        rs.getInt("id"), // Lấy giá trị ID
                        rs.getString("user"), // Lấy giá trị tên người dùng
                        rs.getString("password") // Lấy giá trị mật khẩu (cần xử lý an toàn hơn)
                    );
                }
            }
        } catch (SQLException e) { // Bắt lỗi SQLException
            LOGGER.log(Level.SEVERE, "Error querying login by user", e); // Ghi log lỗi với mức độ SEVERE
            throw new RuntimeException("Error querying login by user", e); // Ném lỗi RuntimeException nếu có lỗi
        }
        return null; // Trả về null nếu không tìm thấy thông tin đăng nhập
    }
}
