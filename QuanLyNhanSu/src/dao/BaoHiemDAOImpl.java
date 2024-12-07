/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Các dòng trên là các thông báo tự động được NetBeans tạo ra, liên quan đến bản quyền và template mặc định. 
// Bạn có thể thay đổi thông tin bản quyền hoặc template nếu muốn.

package dao;
// Khai báo package `dao`, nơi chứa các lớp liên quan đến truy xuất dữ liệu.

import model.BaoHiem;
// Import lớp `BaoHiem` từ package `model`, đại diện cho đối tượng bảo hiểm.

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
// Import các thư viện cần thiết từ gói `java.sql` và `java.time` để làm việc với cơ sở dữ liệu và xử lý thời gian.
// `ArrayList` và `List` từ `java.util` dùng để tạo và quản lý danh sách.

public class BaoHiemDAOImpl implements BaoHiemDAO {
// Khai báo lớp `BaoHiemDAOImpl` cài đặt interface `BaoHiemDAO`, giúp thực hiện các chức năng liên quan đến bảng `bao_hiem`.

    private Connection connection;
    // Biến `connection` lưu trữ đối tượng kết nối cơ sở dữ liệu.

    public BaoHiemDAOImpl(Connection connection) {
        this.connection = connection;
    }
    // Constructor của lớp nhận vào một đối tượng `Connection` để thiết lập kết nối đến cơ sở dữ liệu.

    @Override
    public void addBaoHiem(BaoHiem baoHiem) throws SQLException {
        String sql = "INSERT INTO bao_hiem (so_bao_hiem, ngay_cap, noi_cap, ma_nhan_vien) VALUES (?, ?, ?, ?)";
        // Câu lệnh SQL để thêm một bản ghi mới vào bảng `bao_hiem`.
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Sử dụng `PreparedStatement` để chuẩn bị và thực thi câu lệnh SQL.
            stmt.setString(1, baoHiem.getSoBaoHiem());
            // Thiết lập giá trị cho tham số đầu tiên `so_bao_hiem`.
            stmt.setDate(2, java.sql.Date.valueOf(baoHiem.getNgayCap()));
            // Thiết lập giá trị cho tham số thứ hai `ngay_cap` (chuyển đổi `LocalDate` sang `java.sql.Date`).
            stmt.setString(3, baoHiem.getNoiCap());
            // Thiết lập giá trị cho tham số thứ ba `noi_cap`.
            stmt.setInt(4, baoHiem.getMaNhanVien());
            // Thiết lập giá trị cho tham số thứ tư `ma_nhan_vien`.
            stmt.executeUpdate();
            // Thực thi câu lệnh SQL để thêm dữ liệu vào cơ sở dữ liệu.
        }
    }

    @Override
    public void updateBaoHiem(BaoHiem baoHiem) throws SQLException {
        String sql = "UPDATE bao_hiem SET so_bao_hiem = ?, ngay_cap = ?, noi_cap = ?, ma_nhan_vien = ? WHERE id_bao_hiem = ?";
        // Câu lệnh SQL để cập nhật thông tin bản ghi trong bảng `bao_hiem`.
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, baoHiem.getSoBaoHiem());
            stmt.setDate(2, java.sql.Date.valueOf(baoHiem.getNgayCap()));
            stmt.setString(3, baoHiem.getNoiCap());
            stmt.setInt(4, baoHiem.getMaNhanVien());
            stmt.setInt(5, baoHiem.getIdBaoHiem());
            stmt.executeUpdate();
            // Thực thi câu lệnh SQL để cập nhật dữ liệu trong cơ sở dữ liệu.
        }
    }

    @Override
    public void deleteBaoHiem(int idBaoHiem) throws SQLException {
        String sql = "DELETE FROM bao_hiem WHERE id_bao_hiem = ?";
        // Câu lệnh SQL để xóa bản ghi khỏi bảng `bao_hiem`.
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idBaoHiem);
            // Thiết lập giá trị cho tham số `id_bao_hiem`.
            stmt.executeUpdate();
            // Thực thi câu lệnh SQL để xóa dữ liệu khỏi cơ sở dữ liệu.
        }
    }

    @Override
    public BaoHiem getBaoHiemById(int idBaoHiem) throws SQLException {
        String sql = "SELECT * FROM bao_hiem WHERE id_bao_hiem = ?";
        // Câu lệnh SQL để truy vấn bản ghi từ bảng `bao_hiem` dựa trên `id_bao_hiem`.
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idBaoHiem);
            // Thiết lập giá trị cho tham số `id_bao_hiem`.
            ResultSet rs = stmt.executeQuery();
            // Thực thi câu lệnh SQL và nhận kết quả trả về dưới dạng `ResultSet`.
            if (rs.next()) {
                // Nếu có bản ghi trả về, tạo đối tượng `BaoHiem` từ kết quả truy vấn.
                return new BaoHiem(
                    rs.getInt("id_bao_hiem"),
                    rs.getString("so_bao_hiem"),
                    rs.getDate("ngay_cap").toLocalDate(),
                    rs.getString("noi_cap"),
                    rs.getInt("ma_nhan_vien")
                );
            }
            return null;
            // Nếu không có bản ghi nào, trả về `null`.
        }
    }

    @Override
    public List<BaoHiem> getAllBaoHiem() throws SQLException {
        List<BaoHiem> baoHiems = new ArrayList<>();
        // Tạo danh sách `BaoHiem` để lưu trữ các kết quả truy vấn.
        String sql = "SELECT * FROM bao_hiem";
        // Câu lệnh SQL để truy vấn tất cả các bản ghi từ bảng `bao_hiem`.
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            // Thực thi câu lệnh SQL và nhận kết quả trả về dưới dạng `ResultSet`.
            while (rs.next()) {
                // Duyệt qua từng bản ghi trong kết quả truy vấn.
                baoHiems.add(new BaoHiem(
                    rs.getInt("id_bao_hiem"),
                    rs.getString("so_bao_hiem"),
                    rs.getDate("ngay_cap").toLocalDate(),
                    rs.getString("noi_cap"),
                    rs.getInt("ma_nhan_vien")
                ));
                // Thêm từng đối tượng `BaoHiem` vào danh sách `baoHiems`.
            }
        }
        return baoHiems;
        // Trả về danh sách các đối tượng `BaoHiem`.
    }
}
