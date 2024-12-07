/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao; // Khai báo package dao, nơi lưu trữ các lớp liên quan đến thao tác với cơ sở dữ liệu

import model.PhongBan; // Import lớp PhongBan từ package model để sử dụng trong các phương thức của PhongBanDAOImpl

import java.sql.Connection; // Import lớp Connection để kết nối đến cơ sở dữ liệu
import java.sql.PreparedStatement; // Import lớp PreparedStatement để thực thi các câu lệnh SQL
import java.sql.ResultSet; // Import lớp ResultSet để làm việc với kết quả truy vấn
import java.sql.SQLException; // Import lớp SQLException để xử lý lỗi liên quan đến cơ sở dữ liệu
import java.util.ArrayList; // Import lớp ArrayList để lưu trữ danh sách các đối tượng PhongBan
import java.util.List; // Import lớp List để làm việc với danh sách các đối tượng PhongBan

public class PhongBanDAOImpl implements PhongBanDAO { // Định nghĩa lớp PhongBanDAOImpl thực hiện các phương thức từ giao diện PhongBanDAO
    private Connection connection; // Khai báo đối tượng Connection để kết nối đến cơ sở dữ liệu

    public PhongBanDAOImpl(Connection connection) { // Constructor để khởi tạo đối tượng PhongBanDAOImpl và thiết lập kết nối cơ sở dữ liệu
        this.connection = connection; // Gán kết nối cơ sở dữ liệu
    }

    @Override
    public void addPhongBan(PhongBan phongBan) throws SQLException { // Phương thức thêm một đối tượng PhongBan vào cơ sở dữ liệu
        String query = "INSERT INTO phong_ban (ten_phong_ban) VALUES (?)"; // Câu lệnh SQL để thêm dữ liệu vào bảng phong_ban
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setString(1, phongBan.getTenPhongBan()); // Đặt giá trị cho tham số ten_phong_ban
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        }
    }

    @Override
    public void updatePhongBan(PhongBan phongBan) throws SQLException { // Phương thức cập nhật một đối tượng PhongBan trong cơ sở dữ liệu
        String query = "UPDATE phong_ban SET ten_phong_ban = ? WHERE id_phong_ban = ?"; // Câu lệnh SQL để cập nhật dữ liệu trong bảng phong_ban
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setString(1, phongBan.getTenPhongBan()); // Đặt giá trị cho tham số ten_phong_ban
            stmt.setInt(2, phongBan.getIdPhongBan()); // Đặt giá trị cho tham số id_phong_ban
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        }
    }

    @Override
    public void deletePhongBan(int idPhongBan) throws SQLException { // Phương thức xóa một đối tượng PhongBan dựa trên id_phong_ban
        String query = "DELETE FROM phong_ban WHERE id_phong_ban = ?"; // Câu lệnh SQL để xóa dữ liệu từ bảng phong_ban
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, idPhongBan); // Đặt giá trị cho tham số id_phong_ban
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        }
    }

    @Override
    public PhongBan getPhongBanById(int idPhongBan) throws SQLException { // Phương thức lấy thông tin PhongBan dựa trên id_phong_ban
        String query = "SELECT * FROM phong_ban WHERE id_phong_ban = ?"; // Câu lệnh SQL để lấy thông tin từ bảng phong_ban
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, idPhongBan); // Đặt giá trị cho tham số id_phong_ban
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả
                if (rs.next()) { // Nếu có kết quả
                    return new PhongBan(
                        rs.getInt("id_phong_ban"), // Lấy giá trị id_phong_ban
                        rs.getString("ten_phong_ban") // Lấy giá trị ten_phong_ban
                    );
                }
            }
        }
        return null; // Trả về null nếu không tìm thấy thông tin
    }

    @Override
    public List<PhongBan> getAllPhongBans() throws SQLException { // Phương thức lấy tất cả các đối tượng PhongBan từ cơ sở dữ liệu
        List<PhongBan> phongBans = new ArrayList<>(); // Tạo danh sách để lưu trữ các đối tượng PhongBan
        String query = "SELECT * FROM phong_ban"; // Câu lệnh SQL để lấy tất cả dữ liệu từ bảng phong_ban
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả
            while (rs.next()) { // Duyệt qua từng bản ghi trong kết quả
                phongBans.add(new PhongBan(
                    rs.getInt("id_phong_ban"), // Lấy giá trị id_phong_ban
                    rs.getString("ten_phong_ban") // Lấy giá trị ten_phong_ban
                ));
            }
        }
        return phongBans; // Trả về danh sách các đối tượng PhongBan
    }

    @Override
    public List<PhongBan> searchPhongBanByName(String tenPhongBan) throws SQLException { // Phương thức tìm kiếm các đối tượng PhongBan theo tên
        List<PhongBan> phongBans = new ArrayList<>(); // Tạo danh sách để lưu trữ các đối tượng PhongBan
        String query = "SELECT * FROM phong_ban WHERE ten_phong_ban LIKE ?"; // Câu lệnh SQL để tìm kiếm dữ liệu trong bảng phong_ban theo tên
        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setString(1, "%" + tenPhongBan + "%"); // Đặt giá trị cho tham số ten_phong_ban với ký tự đại diện % để tìm kiếm theo mẫu
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả
                while (rs.next()) { // Duyệt qua từng bản ghi trong kết quả
                    phongBans.add(new PhongBan(
                        rs.getInt("id_phong_ban"), // Lấy giá trị id_phong_ban
                        rs.getString("ten_phong_ban") // Lấy giá trị ten_phong_ban
                    ));
                }
            }
        }
        return phongBans; // Trả về danh sách các đối tượng PhongBan tìm được
    }
}
