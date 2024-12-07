/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model; 

public class Luong { // Định nghĩa lớp Luong đại diện cho thông tin lương
    private int maNhanVien; // Mã nhân viên, liên kết với bảng nhan_vien
    private String hoTen; // Họ tên của nhân viên
    private double luong; // Lương cơ bản của nhân viên
    private double ungLuong; // Ứng lương của nhân viên

    // Constructor không có ID, khởi tạo đối tượng Luong với các thông tin lương
    public Luong(int maNhanVien, String hoTen, double luong, double ungLuong) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.luong = luong;
        this.ungLuong = ungLuong;
    }

    // Getter và setter cho thuộc tính maNhanVien
    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    // Getter và setter cho thuộc tính hoTen
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    // Getter và setter cho thuộc tính luong
    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    // Getter và setter cho thuộc tính ungLuong
    public double getUngLuong() {
        return ungLuong;
    }

    public void setUngLuong(double ungLuong) {
        this.ungLuong = ungLuong;
    }
}
