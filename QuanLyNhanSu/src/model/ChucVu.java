/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model; // Khai báo package model, nơi lưu trữ các lớp mô hình dữ liệu

public class ChucVu { // Định nghĩa lớp ChucVu đại diện cho chức vụ của nhân viên
    private int id; // Thuộc tính lưu trữ mã chức vụ
    private String tenChucVu; // Thuộc tính lưu trữ tên chức vụ

    // Constructor để khởi tạo đối tượng ChucVu với mã và tên chức vụ
    public ChucVu(int id, String tenChucVu) {
        this.id = id;
        this.tenChucVu = tenChucVu;
    }

    // Getter và setter cho thuộc tính id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và setter cho thuộc tính tenChucVu
    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    // Phương thức toString() để trả về chuỗi mô tả thông tin của đối tượng ChucVu
    @Override
    public String toString() {
        return "ChucVu{" +
                "id=" + id +
                ", tenChucVu='" + tenChucVu + '\'' +
                '}';
    }
}
