/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.NhanVien;
import java.util.List;

public interface NhanVienDAO {
    void addNhanVien(NhanVien nhanVien);
    void updateNhanVien(NhanVien nhanVien);
    void deleteNhanVien(int maNhanVien); // Đổi từ id thành maNhanVien
    NhanVien getNhanVienById(int maNhanVien); // Đổi từ id thành maNhanVien
    List<NhanVien> getAllNhanVien(); // Đảm bảo phương thức này có mặt
}


