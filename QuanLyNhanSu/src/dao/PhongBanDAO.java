/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.PhongBan;
import java.sql.SQLException;
import java.util.List;

public interface PhongBanDAO {
    void addPhongBan(PhongBan phongBan) throws SQLException;
    void updatePhongBan(PhongBan phongBan) throws SQLException;
    void deletePhongBan(int idPhongBan) throws SQLException;
    PhongBan getPhongBanById(int idPhongBan) throws SQLException;
    List<PhongBan> getAllPhongBans() throws SQLException;
    List<PhongBan> searchPhongBanByName(String tenPhongBan) throws SQLException; // Thêm phương thức tìm kiếm
}



