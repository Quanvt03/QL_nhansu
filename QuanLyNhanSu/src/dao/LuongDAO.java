/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Luong;

import java.sql.SQLException;
import java.util.List;

public interface LuongDAO {
    void addLuong(Luong luong) throws SQLException;
    void updateLuong(Luong luong) throws SQLException;
    void deleteLuong(int maNhanVien) throws SQLException;
    Luong getLuongById(int maNhanVien) throws SQLException;
    List<Luong> getAllLuongs() throws SQLException;
    List<Luong> searchLuongByMaNhanVien(int maNhanVien) throws SQLException;
}

