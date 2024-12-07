/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyNhanSuApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import dao.NhanVienDAOImpl;
import model.NhanVien;

public class QuanLyNhanSuApp {
    public static void main(String[] args) {
        // Khởi tạo kết nối cơ sở dữ liệu
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_nhan_su", "root", "Quanvu@2003"); // Thay đổi URL, username và password phù hợp

            // Tạo đối tượng NhanVien
            NhanVien nhanVien = new NhanVien();
            nhanVien.setHoTen("Nguyen Van A");
            nhanVien.setGioiTinh("Nam");
            nhanVien.setNgaySinh(LocalDate.of(1990, 1, 1)); // LocalDate
            nhanVien.setDiaChi("123 Đường ABC");
            nhanVien.setSdt("0123456789");
            nhanVien.setEmail("nguyenvana@example.com");
            nhanVien.setChucVuId(1); // Ví dụ ID cho chức vụ
            nhanVien.setPhongBanId(1); // Ví dụ ID cho phòng ban
            nhanVien.setLuongId(1); // Ví dụ ID cho lương

            // Tạo đối tượng NhanVienDAOImpl và chèn nhân viên vào cơ sở dữ liệu
            NhanVienDAOImpl nhanVienDAO = new NhanVienDAOImpl(conn);
            nhanVienDAO.addNhanVien(nhanVien);

            // In ra danh sách nhân viên
            List<NhanVien> nhanViens = nhanVienDAO.getAllNhanVien();
            System.out.println("Danh sách nhân viên:");
            for (NhanVien nv : nhanViens) {
                System.out.println("ID: " + nv.getMaNhanVien() + ", Ho Ten: " + nv.getHoTen() + ", Gioi Tinh: " + nv.getGioiTinh() +
                                   ", Ngay Sinh: " + nv.getNgaySinh() + ", Dia Chi: " + nv.getDiaChi() +
                                   ", SDT: " + nv.getSdt() + ", Email: " + nv.getEmail() +
                                   ", Chuc Vu ID: " + nv.getChucVuId() + ", Phong Ban ID: " + nv.getPhongBanId() +
                                   ", Luong ID: " + nv.getLuongId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối cơ sở dữ liệu
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
