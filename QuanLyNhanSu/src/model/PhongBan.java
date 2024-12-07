/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model; // Khai báo package model, nơi lưu trữ các lớp mô hình dữ liệu

public class PhongBan { // Định nghĩa lớp PhongBan đại diện cho phòng ban
    private int idPhongBan; // ID của phòng ban, sử dụng làm khóa chính
    private String tenPhongBan; // Tên của phòng ban

    // Constructor cho việc thêm phòng ban
    public PhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    // Constructor cho việc lấy dữ liệu từ cơ sở dữ liệu
    public PhongBan(int idPhongBan, String tenPhongBan) {
        this.idPhongBan = idPhongBan;
        this.tenPhongBan = tenPhongBan;
    }

    // Getter và Setter cho các thuộc tính
    public int getIdPhongBan() {
        return idPhongBan;
    }

    public void setIdPhongBan(int idPhongBan) {
        this.idPhongBan = idPhongBan;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }
}
