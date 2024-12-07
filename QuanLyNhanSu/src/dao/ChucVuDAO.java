/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Thông báo tự động của NetBeans liên quan đến bản quyền và template mặc định.

package dao;
// Khai báo package `dao`, nơi chứa các lớp liên quan đến truy xuất dữ liệu.

import model.ChucVu;
// Import lớp `ChucVu` từ package `model`, đại diện cho đối tượng chức vụ.

import java.sql.SQLException;
import java.util.List;
// Import các thư viện cần thiết từ gói `java.sql` và `java.util`. 
// `SQLException` được sử dụng để xử lý các lỗi liên quan đến truy vấn SQL.
// `List` từ `java.util` dùng để làm việc với danh sách đối tượng.

public interface ChucVuDAO {
// Khai báo interface `ChucVuDAO`, định nghĩa các phương thức thao tác với dữ liệu liên quan đến chức vụ.

    void addChucVu(ChucVu chucVu) throws SQLException;
    // Phương thức `addChucVu` được dùng để thêm một đối tượng `ChucVu` vào cơ sở dữ liệu.
    // Phương thức có thể ném ra ngoại lệ `SQLException` nếu có lỗi xảy ra khi thực hiện truy vấn SQL.

    void updateChucVu(ChucVu chucVu) throws SQLException;
    // Phương thức `updateChucVu` được dùng để cập nhật thông tin của một đối tượng `ChucVu` trong cơ sở dữ liệu.
    // Phương thức có thể ném ra ngoại lệ `SQLException` nếu có lỗi xảy ra khi thực hiện truy vấn SQL.

    void deleteChucVu(int id) throws SQLException;
    // Phương thức `deleteChucVu` được dùng để xóa một chức vụ khỏi cơ sở dữ liệu dựa trên `id`.
    // Phương thức có thể ném ra ngoại lệ `SQLException` nếu có lỗi xảy ra khi thực hiện truy vấn SQL.

    List<ChucVu> searchChucVu(String keyword) throws SQLException;
    // Phương thức `searchChucVu` được dùng để tìm kiếm các chức vụ trong cơ sở dữ liệu dựa trên từ khóa `keyword`.
    // Phương thức trả về danh sách các đối tượng `ChucVu` khớp với từ khóa tìm kiếm.
    // Phương thức có thể ném ra ngoại lệ `SQLException` nếu có lỗi xảy ra khi thực hiện truy vấn SQL.

    List<ChucVu> getAllChucVu() throws SQLException;
    // Phương thức `getAllChucVu` được dùng để lấy danh sách tất cả các chức vụ từ cơ sở dữ liệu.
    // Phương thức trả về danh sách các đối tượng `ChucVu`.
    // Phương thức có thể ném ra ngoại lệ `SQLException` nếu có lỗi xảy ra khi thực hiện truy vấn SQL.

    ChucVu getChucVuById(int id) throws SQLException;
    // Phương thức `getChucVuById` được dùng để lấy thông tin của một chức vụ từ cơ sở dữ liệu dựa trên `id`.
    // Phương thức trả về một đối tượng `ChucVu`.
    // Phương thức có thể ném ra ngoại lệ `SQLException` nếu có lỗi xảy ra khi thực hiện truy vấn SQL.
}
