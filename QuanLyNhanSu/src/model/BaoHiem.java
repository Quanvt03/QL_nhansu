/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model; // Khai báo package model, nơi lưu trữ các lớp mô hình dữ liệu

import java.time.LocalDate; // Import lớp LocalDate để làm việc với ngày tháng năm

public class BaoHiem { // Định nghĩa lớp BaoHiem đại diện cho thông tin bảo hiểm
    private int idBaoHiem; // Thuộc tính lưu trữ mã bảo hiểm
    private String soBaoHiem; // Thuộc tính lưu trữ số bảo hiểm
    private LocalDate ngayCap; // Thuộc tính lưu trữ ngày cấp bảo hiểm
    private String noiCap; // Thuộc tính lưu trữ nơi cấp bảo hiểm
    private int maNhanVien; // Thuộc tính lưu trữ mã nhân viên liên kết với bảo hiểm

    // Constructor để khởi tạo một đối tượng BaoHiem với tất cả các thuộc tính
    public BaoHiem(int idBaoHiem, String soBaoHiem, LocalDate ngayCap, String noiCap, int maNhanVien) {
        this.idBaoHiem = idBaoHiem;
        this.soBaoHiem = soBaoHiem;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
        this.maNhanVien = maNhanVien;
    }

    // Getter và setter cho thuộc tính idBaoHiem
    public int getIdBaoHiem() {
        return idBaoHiem;
    }

    public void setIdBaoHiem(int idBaoHiem) {
        this.idBaoHiem = idBaoHiem;
    }

    // Getter và setter cho thuộc tính soBaoHiem
    public String getSoBaoHiem() {
        return soBaoHiem;
    }

    public void setSoBaoHiem(String soBaoHiem) {
        this.soBaoHiem = soBaoHiem;
    }

    // Getter và setter cho thuộc tính ngayCap
    public LocalDate getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(LocalDate ngayCap) {
        this.ngayCap = ngayCap;
    }

    // Getter và setter cho thuộc tính noiCap
    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    // Getter và setter cho thuộc tính maNhanVien
    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
}
