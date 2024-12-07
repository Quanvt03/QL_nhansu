/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model; // Khai báo package model, nơi lưu trữ các lớp mô hình dữ liệu

import java.time.LocalDate; // Import lớp LocalDate để sử dụng cho ngày tháng

public class NhanVien extends CanBo { // Định nghĩa lớp NhanVien kế thừa từ lớp CanBo
    private int maNhanVien; // Mã nhân viên, sử dụng thay cho id
    private int chucVuId; // Mã chức vụ của nhân viên, liên kết với bảng chuc_vu
    private int phongBanId; // Mã phòng ban của nhân viên, liên kết với bảng phong_ban
    private int luongId; // Mã lương của nhân viên, liên kết với bảng luong

    // Constructor không tham số
    public NhanVien() {
    }

    // Constructor với tất cả các tham số, bao gồm các tham số từ lớp cha
    public NhanVien(int maNhanVien, String hoTen, String gioiTinh, LocalDate ngaySinh, String diaChi, String sdt, String email, int chucVuId, int phongBanId, int luongId) {
        super(hoTen, gioiTinh, ngaySinh, diaChi, sdt, email); // Gọi constructor của lớp cha
        this.maNhanVien = maNhanVien;
        this.chucVuId = chucVuId;
        this.phongBanId = phongBanId;
        this.luongId = luongId;
    }

    // Getter và Setter cho các thuộc tính
    public int getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(int maNhanVien) { this.maNhanVien = maNhanVien; }

    public int getChucVuId() { return chucVuId; }
    public void setChucVuId(int chucVuId) { this.chucVuId = chucVuId; }

    public int getPhongBanId() { return phongBanId; }
    public void setPhongBanId(int phongBanId) { this.phongBanId = phongBanId; }

    public int getLuongId() { return luongId; }
    public void setLuongId(int luongId) { this.luongId = luongId; }

    @Override
    public String toString() { // Phương thức toString() để hiển thị thông tin đối tượng
        return "NhanVien{" +
                "maNhanVien=" + maNhanVien +
                ", chucVuId=" + chucVuId +
                ", phongBanId=" + phongBanId +
                ", luongId=" + luongId +
                ", hoTen='" + getHoTen() + '\'' + // Lấy thông tin từ lớp cha
                ", gioiTinh='" + getGioiTinh() + '\'' +
                ", ngaySinh=" + getNgaySinh() +
                ", diaChi='" + getDiaChi() + '\'' +
                ", sdt='" + getSdt() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
