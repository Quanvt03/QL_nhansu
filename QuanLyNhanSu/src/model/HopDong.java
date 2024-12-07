/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model; // Khai báo package model, nơi lưu trữ các lớp mô hình dữ liệu

import java.sql.Date; // Nhập khẩu lớp Date từ java.sql để sử dụng trong lớp HopDong

public class HopDong { // Định nghĩa lớp HopDong đại diện cho hợp đồng
    private int soHopDong; // Mã hợp đồng, sẽ được tự động tăng trong cơ sở dữ liệu
    private Date ngayBatDau; // Ngày bắt đầu hợp đồng
    private Date ngayKetThuc; // Ngày kết thúc hợp đồng
    private Date ngayKy; // Ngày ký hợp đồng
    private String noiDung; // Nội dung của hợp đồng
    private int thoiGian; // Thời gian hợp đồng (theo tháng)
    private int maNhanVien; // Mã nhân viên liên kết với bảng nhan_vien

    // Constructor không tham số
    public HopDong() {
    }

    // Constructor với tất cả các tham số để khởi tạo đối tượng HopDong
    public HopDong(int soHopDong, Date ngayBatDau, Date ngayKetThuc, Date ngayKy, String noiDung, int thoiGian, int maNhanVien) {
        this.soHopDong = soHopDong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayKy = ngayKy;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.maNhanVien = maNhanVien;
    }

    // Getter và setter cho thuộc tính soHopDong
    public int getSoHopDong() {
        return soHopDong;
    }

    public void setSoHopDong(int soHopDong) {
        this.soHopDong = soHopDong;
    }

    // Getter và setter cho thuộc tính ngayBatDau
    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    // Getter và setter cho thuộc tính ngayKetThuc
    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    // Getter và setter cho thuộc tính ngayKy
    public Date getNgayKy() {
        return ngayKy;
    }

    public void setNgayKy(Date ngayKy) {
        this.ngayKy = ngayKy;
    }

    // Getter và setter cho thuộc tính noiDung
    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    // Getter và setter cho thuộc tính thoiGian
    public int getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(int thoiGian) {
        this.thoiGian = thoiGian;
    }

    // Getter và setter cho thuộc tính maNhanVien
    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    // Phương thức toString() để trả về chuỗi mô tả thông tin của đối tượng HopDong
    @Override
    public String toString() {
        return "HopDong{" +
                "soHopDong=" + soHopDong +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", ngayKy=" + ngayKy +
                ", noiDung='" + noiDung + '\'' +
                ", thoiGian=" + thoiGian +
                ", maNhanVien=" + maNhanVien +
                '}';
    }
}
