/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

// Import các lớp cần thiết từ thư viện Java
import model.ChucVu; // Import lớp ChucVu từ gói model
import java.sql.*; // Import toàn bộ các lớp liên quan đến JDBC
import java.util.ArrayList; // Import lớp ArrayList
import java.util.List; // Import giao diện List

// Khai báo lớp ChucVuDAOImpl và implement giao diện ChucVuDAO
public class ChucVuDAOImpl implements ChucVuDAO {
    private Connection conn; // Khai báo đối tượng Connection để kết nối với cơ sở dữ liệu

    // Constructor của lớp nhận vào một đối tượng Connection và gán cho biến conn
    public ChucVuDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // Triển khai phương thức addChucVu từ giao diện ChucVuDAO để thêm mới chức vụ vào cơ sở dữ liệu
    @Override
    public void addChucVu(ChucVu chucVu) throws SQLException {
        String sql = "INSERT INTO chuc_vu (ten_chuc_vu) VALUES (?)"; // Chuỗi SQL để thêm mới một chức vụ
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Chuẩn bị câu lệnh PreparedStatement từ đối tượng Connection
            stmt.setString(1, chucVu.getTenChucVu()); // Gán giá trị tên chức vụ vào câu lệnh SQL
            stmt.executeUpdate(); // Thực thi câu lệnh SQL để thêm dữ liệu vào cơ sở dữ liệu
        }
    }

    // Triển khai phương thức updateChucVu từ giao diện ChucVuDAO để cập nhật thông tin chức vụ trong cơ sở dữ liệu
    @Override
    public void updateChucVu(ChucVu chucVu) throws SQLException {
        String sql = "UPDATE chuc_vu SET ten_chuc_vu = ? WHERE id_chuc_vu = ?"; // Chuỗi SQL để cập nhật tên chức vụ dựa trên ID
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Chuẩn bị câu lệnh PreparedStatement
            stmt.setString(1, chucVu.getTenChucVu()); // Gán giá trị tên chức vụ mới vào câu lệnh SQL
            stmt.setInt(2, chucVu.getId()); // Gán giá trị ID chức vụ cần cập nhật vào câu lệnh SQL
            stmt.executeUpdate(); // Thực thi câu lệnh SQL để cập nhật dữ liệu trong cơ sở dữ liệu
        }
    }

    // Triển khai phương thức deleteChucVu từ giao diện ChucVuDAO để xóa chức vụ khỏi cơ sở dữ liệu
    @Override
    public void deleteChucVu(int id) throws SQLException {
        String sql = "DELETE FROM chuc_vu WHERE id_chuc_vu = ?"; // Chuỗi SQL để xóa chức vụ dựa trên ID
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Chuẩn bị câu lệnh PreparedStatement
            stmt.setInt(1, id); // Gán giá trị ID chức vụ cần xóa vào câu lệnh SQL
            stmt.executeUpdate(); // Thực thi câu lệnh SQL để xóa dữ liệu khỏi cơ sở dữ liệu
        }
    }

    // Triển khai phương thức searchChucVu từ giao diện ChucVuDAO để tìm kiếm chức vụ theo từ khóa
    @Override
    public List<ChucVu> searchChucVu(String keyword) throws SQLException {
        List<ChucVu> result = new ArrayList<>(); // Tạo danh sách kết quả để lưu các đối tượng ChucVu tìm được
        String sql = "SELECT * FROM chuc_vu WHERE ten_chuc_vu LIKE ?"; // Chuỗi SQL để tìm kiếm chức vụ theo tên chứa từ khóa
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Chuẩn bị câu lệnh PreparedStatement
            stmt.setString(1, "%" + keyword + "%"); // Gán giá trị từ khóa tìm kiếm vào câu lệnh SQL với ký tự đại diện
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả trả về dưới dạng ResultSet
                while (rs.next()) { // Duyệt qua các hàng dữ liệu trong ResultSet
                    int id = rs.getInt("id_chuc_vu"); // Lấy giá trị ID chức vụ từ cột "id_chuc_vu"
                    String tenChucVu = rs.getString("ten_chuc_vu"); // Lấy giá trị tên chức vụ từ cột "ten_chuc_vu"
                    result.add(new ChucVu(id, tenChucVu)); // Thêm đối tượng ChucVu vào danh sách kết quả
                }
            }
        }
        return result; // Trả về danh sách kết quả tìm kiếm
    }

    // Triển khai phương thức getAllChucVu từ giao diện ChucVuDAO để lấy toàn bộ danh sách chức vụ từ cơ sở dữ liệu
    @Override
    public List<ChucVu> getAllChucVu() throws SQLException {
        List<ChucVu> result = new ArrayList<>(); // Tạo danh sách kết quả để lưu các đối tượng ChucVu
        String sql = "SELECT * FROM chuc_vu"; // Chuỗi SQL để lấy toàn bộ dữ liệu từ bảng chuc_vu
        try (Statement stmt = conn.createStatement(); // Tạo đối tượng Statement từ Connection
             ResultSet rs = stmt.executeQuery(sql)) { // Thực thi câu lệnh SQL và lấy kết quả trả về dưới dạng ResultSet
            while (rs.next()) { // Duyệt qua các hàng dữ liệu trong ResultSet
                int id = rs.getInt("id_chuc_vu"); // Lấy giá trị ID chức vụ từ cột "id_chuc_vu"
                String tenChucVu = rs.getString("ten_chuc_vu"); // Lấy giá trị tên chức vụ từ cột "ten_chuc_vu"
                result.add(new ChucVu(id, tenChucVu)); // Thêm đối tượng ChucVu vào danh sách kết quả
            }
        }
        return result; // Trả về danh sách tất cả các chức vụ
    }

    // Triển khai phương thức getChucVuById từ giao diện ChucVuDAO để lấy thông tin chức vụ dựa trên ID
    @Override
    public ChucVu getChucVuById(int id) throws SQLException {
        String sql = "SELECT * FROM chuc_vu WHERE id_chuc_vu = ?"; // Chuỗi SQL để lấy dữ liệu chức vụ theo ID
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Chuẩn bị câu lệnh PreparedStatement
            stmt.setInt(1, id); // Gán giá trị ID chức vụ cần tìm vào câu lệnh SQL
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả trả về dưới dạng ResultSet
                if (rs.next()) { // Kiểm tra nếu có dữ liệu trả về
                    return new ChucVu(
                        rs.getInt("id_chuc_vu"), // Lấy giá trị ID chức vụ từ cột "id_chuc_vu"
                        rs.getString("ten_chuc_vu") // Lấy giá trị tên chức vụ từ cột "ten_chuc_vu"
                    );
                } else {
                    return null; // Trả về null nếu không tìm thấy chức vụ
                }
            }
        }
    }
}
