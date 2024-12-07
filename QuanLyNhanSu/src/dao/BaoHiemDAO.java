/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Các dòng trên là các thông báo tự động được NetBeans tạo ra, liên quan đến bản quyền và template mặc định. 
// Bạn có thể thay đổi thông tin bản quyền hoặc template nếu muốn.

package dao;
// Khai báo package `dao`. Tất cả các lớp trong package này sẽ thuộc về tầng Data Access Object (DAO) 
// trong ứng dụng, nơi quản lý truy xuất dữ liệu.

import model.BaoHiem;
import java.util.List;
// Import lớp `BaoHiem` từ package `model`, đại diện cho đối tượng bảo hiểm.
// Import `List` từ thư viện `java.util`, dùng để làm việc với danh sách các phần tử.

public interface BaoHiemDAO {
// Khai báo interface `BaoHiemDAO`, chứa các phương thức cần được triển khai 
// cho việc thao tác với dữ liệu bảng `BaoHiem` trong cơ sở dữ liệu.

    void addBaoHiem(BaoHiem baoHiem) throws Exception;
    // Phương thức `addBaoHiem`: Dùng để thêm một đối tượng `BaoHiem` mới vào cơ sở dữ liệu.
    // Có thể ném ra ngoại lệ (`throws Exception`) nếu có lỗi trong quá trình thêm dữ liệu.

    void updateBaoHiem(BaoHiem baoHiem) throws Exception;
    // Phương thức `updateBaoHiem`: Dùng để cập nhật thông tin của một đối tượng `BaoHiem` 
    // đã có trong cơ sở dữ liệu. Cũng có thể ném ra ngoại lệ nếu xảy ra lỗi.

    void deleteBaoHiem(int idBaoHiem) throws Exception;
    // Phương thức `deleteBaoHiem`: Dùng để xóa một đối tượng `BaoHiem` khỏi cơ sở dữ liệu 
    // dựa trên `idBaoHiem`. Ném ngoại lệ nếu có lỗi.

    BaoHiem getBaoHiemById(int idBaoHiem) throws Exception;
    // Phương thức `getBaoHiemById`: Dùng để lấy thông tin của một đối tượng `BaoHiem` từ cơ sở dữ liệu
    // dựa trên `idBaoHiem`. Ném ngoại lệ nếu có lỗi.

    List<BaoHiem> getAllBaoHiem() throws Exception;
    // Phương thức `getAllBaoHiem`: Dùng để lấy danh sách tất cả các đối tượng `BaoHiem` từ cơ sở dữ liệu.
    // Trả về một danh sách (`List`) các đối tượng `BaoHiem`. Ném ngoại lệ nếu có lỗi.
}
