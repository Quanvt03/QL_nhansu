/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.HopDong;
import java.util.List;

public interface HopDongDAO {
    void themHopDong(HopDong hopDong);
    void suaHopDong(HopDong hopDong);
    void xoaHopDong(int soHopDong); // Sửa đây để dùng int
    HopDong layHopDong(int soHopDong); // Sửa đây để dùng int
    List<HopDong> layDanhSachHopDong();
}


