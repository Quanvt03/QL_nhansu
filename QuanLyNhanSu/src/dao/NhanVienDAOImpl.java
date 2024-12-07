/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao; // Khai báo package dao, nơi lưu trữ các lớp liên quan đến thao tác với cơ sở dữ liệu

import model.NhanVien; // Import lớp NhanVien từ package model để sử dụng trong các phương thức của NhanVienDAOImpl

import java.sql.*; // Import các lớp cần thiết từ package java.sql để làm việc với cơ sở dữ liệu
import java.util.ArrayList; // Import lớp ArrayList để lưu trữ danh sách các đối tượng NhanVien
import java.util.List; // Import lớp List để làm việc với danh sách các đối tượng NhanVien

public class NhanVienDAOImpl implements NhanVienDAO { // Định nghĩa lớp NhanVienDAOImpl thực hiện các phương thức từ giao diện NhanVienDAO
    private Connection conn; // Khai báo đối tượng Connection để kết nối đến cơ sở dữ liệu

    public NhanVienDAOImpl(Connection conn) { // Constructor để khởi tạo đối tượng NhanVienDAOImpl và thiết lập kết nối cơ sở dữ liệu
        this.conn = conn; // Gán kết nối cơ sở dữ liệu
    }

    @Override
    public void addNhanVien(NhanVien nhanVien) { // Phương thức thêm một đối tượng NhanVien vào cơ sở dữ liệu
        String sql = "INSERT INTO nhan_vien (ho_ten, gioi_tinh, ngay_sinh, dia_chi, so_dien_thoai, email, chuc_vu_id, phong_ban_id, luong_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Câu lệnh SQL để thêm dữ liệu vào bảng nhan_vien
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setString(1, nhanVien.getHoTen()); // Đặt giá trị cho tham số ho_ten
            stmt.setString(2, nhanVien.getGioiTinh()); // Đặt giá trị cho tham số gioi_tinh
            stmt.setDate(3, Date.valueOf(nhanVien.getNgaySinh())); // Đặt giá trị cho tham số ngay_sinh
            stmt.setString(4, nhanVien.getDiaChi()); // Đặt giá trị cho tham số dia_chi
            stmt.setString(5, nhanVien.getSdt()); // Đặt giá trị cho tham số so_dien_thoai
            stmt.setString(6, nhanVien.getEmail()); // Đặt giá trị cho tham số email
            stmt.setInt(7, nhanVien.getChucVuId()); // Đặt giá trị cho tham số chuc_vu_id
            stmt.setInt(8, nhanVien.getPhongBanId()); // Đặt giá trị cho tham số phong_ban_id
            stmt.setInt(9, nhanVien.getLuongId()); // Đặt giá trị cho tham số luong_id
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            e.printStackTrace(); // In thông báo lỗi ra console
        }
    }

    @Override
    public void updateNhanVien(NhanVien nhanVien) { // Phương thức cập nhật một đối tượng NhanVien trong cơ sở dữ liệu
        String sql = "UPDATE nhan_vien SET ho_ten = ?, gioi_tinh = ?, ngay_sinh = ?, dia_chi = ?, so_dien_thoai = ?, email = ?, chuc_vu_id = ?, phong_ban_id = ?, luong_id = ? WHERE ma_nhan_vien = ?"; // Câu lệnh SQL để cập nhật dữ liệu trong bảng nhan_vien
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setString(1, nhanVien.getHoTen()); // Đặt giá trị cho tham số ho_ten
            stmt.setString(2, nhanVien.getGioiTinh()); // Đặt giá trị cho tham số gioi_tinh
            stmt.setDate(3, Date.valueOf(nhanVien.getNgaySinh())); // Đặt giá trị cho tham số ngay_sinh
            stmt.setString(4, nhanVien.getDiaChi()); // Đặt giá trị cho tham số dia_chi
            stmt.setString(5, nhanVien.getSdt()); // Đặt giá trị cho tham số so_dien_thoai
            stmt.setString(6, nhanVien.getEmail()); // Đặt giá trị cho tham số email
            stmt.setInt(7, nhanVien.getChucVuId()); // Đặt giá trị cho tham số chuc_vu_id
            stmt.setInt(8, nhanVien.getPhongBanId()); // Đặt giá trị cho tham số phong_ban_id
            stmt.setInt(9, nhanVien.getLuongId()); // Đặt giá trị cho tham số luong_id
            stmt.setInt(10, nhanVien.getMaNhanVien()); // Đặt giá trị cho tham số ma_nhan_vien
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            e.printStackTrace(); // In thông báo lỗi ra console
        }
    }

    @Override
    public void deleteNhanVien(int maNhanVien) { // Phương thức xóa một đối tượng NhanVien dựa trên ma_nhan_vien
        String sql = "DELETE FROM nhan_vien WHERE ma_nhan_vien = ?"; // Câu lệnh SQL để xóa dữ liệu từ bảng nhan_vien
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, maNhanVien); // Đặt giá trị cho tham số ma_nhan_vien
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            e.printStackTrace(); // In thông báo lỗi ra console
        }
    }

    @Override
    public NhanVien getNhanVienById(int maNhanVien) { // Phương thức lấy thông tin NhanVien dựa trên ma_nhan_vien
        String sql = "SELECT ma_nhan_vien, ho_ten, gioi_tinh, ngay_sinh, dia_chi, so_dien_thoai, email, chuc_vu_id, phong_ban_id, luong_id FROM nhan_vien WHERE ma_nhan_vien = ?"; // Câu lệnh SQL để lấy thông tin từ bảng nhan_vien
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, maNhanVien); // Đặt giá trị cho tham số ma_nhan_vien
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả
                if (rs.next()) { // Nếu có kết quả
                    return new NhanVien(
                        rs.getInt("ma_nhan_vien"), // Lấy giá trị ma_nhan_vien
                        rs.getString("ho_ten"), // Lấy giá trị ho_ten
                        rs.getString("gioi_tinh"), // Lấy giá trị gioi_tinh
                        rs.getDate("ngay_sinh").toLocalDate(), // Lấy giá trị ngay_sinh và chuyển đổi từ java.sql.Date sang java.time.LocalDate
                        rs.getString("dia_chi"), // Lấy giá trị dia_chi
                        rs.getString("so_dien_thoai"), // Lấy giá trị so_dien_thoai
                        rs.getString("email"), // Lấy giá trị email
                        rs.getInt("chuc_vu_id"), // Lấy giá trị chuc_vu_id
                        rs.getInt("phong_ban_id"), // Lấy giá trị phong_ban_id
                        rs.getInt("luong_id") // Lấy giá trị luong_id
                    );
                }
            }
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            e.printStackTrace(); // In thông báo lỗi ra console
        }
        return null; // Trả về null nếu không tìm thấy thông tin
    }

    @Override
    public List<NhanVien> getAllNhanVien() { // Phương thức lấy tất cả các đối tượng NhanVien từ cơ sở dữ liệu
        List<NhanVien> danhSachNhanVien = new ArrayList<>(); // Tạo danh sách để lưu trữ các đối tượng NhanVien
        String sql = "SELECT ma_nhan_vien, ho_ten, gioi_tinh, ngay_sinh, dia_chi, so_dien_thoai, email, chuc_vu_id, phong_ban_id, luong_id FROM nhan_vien"; // Câu lệnh SQL để lấy tất cả dữ liệu từ bảng nhan_vien

        try (Statement stmt = conn.createStatement(); // Tạo đối tượng Statement để thực thi câu lệnh SQL
             ResultSet rs = stmt.executeQuery(sql)) { // Thực thi câu lệnh SQL và lấy kết quả

            while (rs.next()) { // Duyệt qua từng bản ghi trong kết quả
                NhanVien nhanVien = new NhanVien(
                    rs.getInt("ma_nhan_vien"), // Lấy giá trị ma_nhan_vien
                    rs.getString("ho_ten"), // Lấy giá trị ho_ten
                    rs.getString("gioi_tinh"), // Lấy giá trị gioi_tinh
                    rs.getDate("ngay_sinh").toLocalDate(), // Lấy giá trị ngay_sinh và chuyển đổi từ java.sql.Date sang java.time.LocalDate
                    rs.getString("dia_chi"), // Lấy giá trị dia_chi
                    rs.getString("so_dien_thoai"), // Lấy giá trị so_dien_thoai
                    rs.getString("email"), // Lấy giá trị email
                    rs.getInt("chuc_vu_id"), // Lấy giá trị chuc_vu_id
                    rs.getInt("phong_ban_id"), // Lấy giá trị phong_ban_id
                    rs.getInt("luong_id") // Lấy giá trị luong_id
                );
                danhSachNhanVien.add(nhanVien); // Thêm đối tượng NhanVien vào danh sách
            }
        } catch (SQLException e) { // Bắt lỗi SQLException nếu có lỗi trong khi thực thi
            e.printStackTrace(); // In thông báo lỗi ra console
        }
        return danhSachNhanVien; // Trả về danh sách các đối tượng NhanVien
    }
}
